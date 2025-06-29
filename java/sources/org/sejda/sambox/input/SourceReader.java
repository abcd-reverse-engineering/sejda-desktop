package org.sejda.sambox.input;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.OffsettableSeekableSource;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.pdmodel.encryption.PDEncryption;
import org.sejda.sambox.util.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/SourceReader.class */
class SourceReader implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(SourceReader.class);
    private static final long OBJECT_NUMBER_THRESHOLD = 10000000000L;
    private static final int GENERATION_NUMBER_THRESHOLD = 65535;
    public static final String OBJ = "obj";
    private SeekableSource source;

    public SourceReader(SeekableSource source) {
        RequireUtils.requireNotNullArg(source, "Cannot read a null source");
        this.source = source;
    }

    public SeekableSource source() {
        return this.source;
    }

    public long position() throws IOException {
        return this.source.position();
    }

    public void offset(long offset) throws IOException {
        if (offset > 0) {
            OffsettableSeekableSource offsettable = SeekableSources.asOffsettable(this.source);
            offsettable.offset(offset);
            this.source = offsettable;
        }
    }

    public void position(long offset) throws IOException {
        this.source.position(offset);
    }

    public long length() {
        return this.source.size();
    }

    public final void skipExpected(String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            skipExpected(expected.charAt(i));
        }
    }

    public void skipExpected(char ec) throws IOException {
        char c = (char) this.source.read();
        if (c != ec) {
            throw new IOException("expected='" + ec + "' actual='" + c + "' at offset " + (position() - 1));
        }
    }

    public boolean skipTokenIfValue(String value) throws IOException {
        long pos = position();
        String token = readToken();
        if (!value.equals(token)) {
            this.source.position(pos);
            return false;
        }
        return true;
    }

    public void skipIndirectObjectDefinition() throws IOException {
        readObjectNumber();
        readGenerationNumber();
        skipSpaces();
        skipExpected(OBJ);
    }

    public void skipExpectedIndirectObjectDefinition(COSObjectKey expected) throws IOException {
        long objNumOffset = position();
        long number = readObjectNumber();
        if (number != expected.objectNumber()) {
            throw new IOException(String.format("Expected '%d' object number at offset %d but was '%d'", Long.valueOf(expected.objectNumber()), Long.valueOf(objNumOffset), Long.valueOf(number)));
        }
        long genNumOffset = position();
        long generation = readGenerationNumber();
        if (generation != expected.generation()) {
            throw new IOException(String.format("Expected '%d' generation number at offset %d but was '%d'", Integer.valueOf(expected.generation()), Long.valueOf(genNumOffset), Long.valueOf(number)));
        }
        skipSpaces();
        skipExpected(OBJ);
    }

    public String readToken() throws IOException {
        int c;
        skipSpaces();
        StringBuilder builder = new StringBuilder(8);
        while (true) {
            c = this.source.read();
            if (c == -1 || CharUtils.isEndOfName(c)) {
                break;
            }
            builder.append((char) c);
        }
        unreadIfValid(c);
        return builder.toString();
    }

    public void unreadSpaces() throws IOException {
        while (true) {
            int c = this.source.peekBack();
            if (c != -1 && CharUtils.isWhitespace(c)) {
                this.source.back();
            } else {
                return;
            }
        }
    }

    public void unreadUntilSpaces() throws IOException {
        while (true) {
            int c = this.source.peekBack();
            if (c != -1 && !CharUtils.isWhitespace(c)) {
                this.source.back();
            } else {
                return;
            }
        }
    }

    public boolean isNextToken(String... values) throws IOException {
        long pos = position();
        String token = readToken();
        position(pos);
        return Arrays.asList(values).contains(token);
    }

    public String readLine() throws IOException {
        int c;
        RequireUtils.requireIOCondition(this.source.peek() != -1, "Expected line but was end of file");
        StringBuilder builder = new StringBuilder(16);
        while (true) {
            c = this.source.read();
            if (c == -1 || CharUtils.isEOL(c)) {
                break;
            }
            builder.append((char) c);
        }
        if (CharUtils.isCarriageReturn(c) && CharUtils.isLineFeed(this.source.peek())) {
            this.source.read();
        }
        return builder.toString();
    }

    public long readObjectNumber() throws IOException {
        long retval = readLong();
        if (retval < 0 || retval >= OBJECT_NUMBER_THRESHOLD) {
            throw new IOException("Object Number '" + retval + "' has more than 10 digits or is negative");
        }
        return retval;
    }

    public int readGenerationNumber() throws IOException {
        int retval = readInt();
        if (retval < 0 || retval > GENERATION_NUMBER_THRESHOLD) {
            throw new IOException("Generation Number '" + retval + "' has more than 5 digits");
        }
        return retval;
    }

    public String readName() throws IOException, NumberFormatException {
        int i;
        skipExpected('/');
        FastByteArrayOutputStream buffer = new FastByteArrayOutputStream();
        while (true) {
            int i2 = this.source.read();
            i = i2;
            if (i2 == -1 || CharUtils.isEndOfName(i)) {
                break;
            }
            if (i == 35) {
                int ch1 = this.source.read();
                int ch2 = this.source.read();
                RequireUtils.requireIOCondition((ch2 == -1 || ch1 == -1) ? false : true, "Expected 2-digit hexadecimal code but was end of file");
                if (CharUtils.isHexDigit((char) ch1) && CharUtils.isHexDigit((char) ch2)) {
                    String hex = Character.toString((char) ch1) + ((char) ch2);
                    i = Integer.parseInt(hex, 16);
                } else {
                    this.source.back(2L);
                    LOG.warn("Found NUMBER SIGN (#) not used as escaping char while reading name at " + position());
                }
            }
            buffer.write(i);
        }
        unreadIfValid(i);
        byte[] bytes = buffer.toByteArray();
        try {
            StandardCharsets.UTF_8.newDecoder().decode(ByteBuffer.wrap(bytes));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (CharacterCodingException e) {
            return new String(bytes, Charset.forName("Windows-1252"));
        }
    }

    public int readInt() throws IOException {
        String intBuffer = readIntegerNumber();
        try {
            return Integer.parseInt(intBuffer);
        } catch (NumberFormatException e) {
            this.source.back(intBuffer.getBytes(StandardCharsets.ISO_8859_1).length);
            throw new IOException(String.format("Expected an integer type at offset %d but was '%s'", Long.valueOf(position()), intBuffer), e);
        }
    }

    public long readLong() throws IOException {
        String longBuffer = readIntegerNumber();
        try {
            return Long.parseLong(longBuffer);
        } catch (NumberFormatException e) {
            this.source.back(longBuffer.getBytes(StandardCharsets.ISO_8859_1).length);
            throw new IOException(String.format("Expected a long type at offset %d but was '%s'", Long.valueOf(position()), longBuffer), e);
        }
    }

    public final String readIntegerNumber() throws IOException {
        skipSpaces();
        StringBuilder builder = new StringBuilder(8);
        int c = this.source.read();
        if (c != -1 && (CharUtils.isDigit(c) || c == 43 || c == 45)) {
            builder.append((char) c);
            while (true) {
                int i = this.source.read();
                c = i;
                if (i == -1 || !CharUtils.isDigit(c)) {
                    break;
                }
                builder.append((char) c);
            }
        }
        unreadIfValid(c);
        return builder.toString();
    }

    public final String readNumber() throws IOException {
        StringBuilder builder = new StringBuilder(8);
        int c = this.source.read();
        if (c != -1 && (CharUtils.isDigit(c) || c == 43 || c == 45 || c == 46)) {
            builder.append((char) c);
            int lastAppended = c;
            if (c == 45 && this.source.peek() == c) {
                this.source.read();
            }
            while (true) {
                int i = this.source.read();
                c = i;
                if (i == -1 || !(CharUtils.isDigit(c) || c == 46 || c == 69 || c == 101 || c == 43 || c == 45)) {
                    break;
                }
                if (c != 45 || lastAppended == 101 || lastAppended == 69) {
                    builder.append((char) c);
                    lastAppended = c;
                }
            }
        }
        unreadIfValid(c);
        return builder.toString();
    }

    public final String readHexString() throws IOException {
        int c;
        skipExpected('<');
        StringBuilder builder = new StringBuilder(16);
        while (true) {
            c = this.source.read();
            if (c == -1 || c == 62) {
                break;
            }
            if (CharUtils.isHexDigit(c)) {
                builder.append((char) c);
            } else if (!CharUtils.isWhitespace(c)) {
                LOG.warn(String.format("Expected an hexadecimal char at offset %d but was '%c'. Replaced with default 0.", Long.valueOf(position() - 1), Integer.valueOf(c)));
                builder.append('0');
            }
        }
        RequireUtils.requireIOCondition(c != -1, "Unexpected EOF. Missing closing bracket for hexadecimal string.");
        return builder.toString();
    }

    public String readLiteralString() throws IOException {
        int i;
        char c;
        skipExpected('(');
        int bracesCounter = 1;
        StringBuilder builder = new StringBuilder(16);
        while (true) {
            i = this.source.read();
            if (i != -1 && bracesCounter > 0) {
                char c2 = (char) i;
                switch (c2) {
                    case '\n':
                        builder.append('\n');
                        break;
                    case CharUtils.ASCII_CARRIAGE_RETURN /* 13 */:
                        builder.append('\n');
                        if (!CharUtils.isLineFeed(this.source.read())) {
                            unreadIfValid(c2);
                            break;
                        } else {
                            break;
                        }
                    case PDEncryption.DEFAULT_LENGTH /* 40 */:
                        bracesCounter++;
                        builder.append(c2);
                        break;
                    case ')':
                        bracesCounter--;
                        if (bracesCounter <= 0) {
                            break;
                        } else {
                            builder.append(c2);
                            break;
                        }
                    case '\\':
                        char next = (char) this.source.read();
                        switch (next) {
                            case '\n':
                            case CharUtils.ASCII_CARRIAGE_RETURN /* 13 */:
                                do {
                                    c = (char) this.source.read();
                                    if (c != 65535) {
                                    }
                                    unreadIfValid(c);
                                    break;
                                } while (CharUtils.isEOL(c));
                                unreadIfValid(c);
                            case PDEncryption.DEFAULT_LENGTH /* 40 */:
                            case ')':
                            case '\\':
                                builder.append(next);
                                break;
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                                StringBuilder octal = new StringBuilder(8);
                                octal.append(next);
                                char next2 = (char) this.source.read();
                                if (CharUtils.isOctalDigit(next2)) {
                                    octal.append(next2);
                                    char next3 = (char) this.source.read();
                                    if (CharUtils.isOctalDigit(next3)) {
                                        octal.append(next3);
                                    } else {
                                        unreadIfValid(next3);
                                    }
                                } else {
                                    unreadIfValid(next2);
                                }
                                builder.append((char) Integer.parseInt(octal.toString(), 8));
                                break;
                            case 'b':
                                builder.append('\b');
                                break;
                            case 'f':
                                builder.append('\f');
                                break;
                            case 'n':
                                builder.append('\n');
                                break;
                            case 'r':
                                builder.append('\r');
                                break;
                            case 't':
                                builder.append('\t');
                                break;
                            default:
                                unreadIfValid(c2);
                                break;
                        }
                    default:
                        builder.append(c2);
                        break;
                }
            }
        }
        unreadIfValid(i);
        return builder.toString();
    }

    public void skipSpaces() throws IOException {
        int c = this.source.read();
        while (true) {
            if (CharUtils.isWhitespace(c) || c == 37) {
                if (c == 37) {
                    do {
                        int i = this.source.read();
                        c = i;
                        if (i != -1) {
                        }
                    } while (!CharUtils.isEOL(c));
                } else {
                    c = this.source.read();
                }
            } else {
                unreadIfValid(c);
                return;
            }
        }
    }

    public void unreadIfValid(int c) throws IOException {
        if (c != -1) {
            this.source.back();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtils.close(this.source);
    }
}
