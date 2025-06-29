package org.sejda.sambox.input;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.sejda.io.SeekableSource;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.pdmodel.encryption.PDEncryption;
import org.sejda.sambox.util.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/COSParser.class */
class COSParser extends BaseCOSParser {
    private static final Logger LOG = LoggerFactory.getLogger(COSParser.class);
    private final IndirectObjectsProvider provider;

    COSParser(SeekableSource source) {
        super(source);
        this.provider = new LazyIndirectObjectsProvider().initializeWith(this);
    }

    COSParser(SeekableSource source, IndirectObjectsProvider provider) {
        super(source);
        this.provider = provider;
    }

    @Override // org.sejda.sambox.input.BaseCOSParser
    public COSBase nextParsedToken() throws IOException {
        skipSpaces();
        char c = (char) source().peek();
        switch (c) {
            case PDEncryption.DEFAULT_LENGTH /* 40 */:
                return nextLiteralString();
            case '+':
            case '-':
            case '.':
                return nextNumber();
            case '/':
                return nextName();
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return nextNumberOrIndirectReference();
            case '<':
                source().read();
                char c2 = (char) source().peek();
                source().back();
                if (c2 == '<') {
                    return nextDictionary();
                }
                return nextHexadecimalString();
            case '[':
                return nextArray();
            case 'f':
            case 't':
                return nextBoolean();
            case 'n':
                return nextNull();
            case 65535:
                return null;
            default:
                String badString = readToken();
                if (BaseCOSParser.ENDOBJ.equals(badString) || BaseCOSParser.ENDSTREAM.equals(badString) || SourceReader.OBJ.equals(badString)) {
                    source().back(badString.getBytes(StandardCharsets.ISO_8859_1).length);
                    return null;
                }
                LOG.warn(String.format("Unknown token with value '%s' ending at offset %d", badString, Long.valueOf(position())));
                if (badString.length() <= 0) {
                    source().read();
                    return null;
                }
                return null;
        }
    }

    public COSBase nextNumberOrIndirectReference() throws IOException {
        String first = readNumber();
        long offset = position();
        skipSpaces();
        if (CharUtils.isDigit(source().peek())) {
            String second = readIntegerNumber();
            skipSpaces();
            if (82 == source().read()) {
                try {
                    return new ExistingIndirectCOSObject(Long.parseLong(first), Integer.parseInt(second), this.provider);
                } catch (NumberFormatException nfe) {
                    throw new IOException(String.format("Unable to parse an object indirect reference with object number '%s' and generation number '%s'", first, second), nfe);
                }
            }
        }
        position(offset);
        return COSNumber.get(first);
    }

    public IndirectObjectsProvider provider() {
        return this.provider;
    }

    @Override // org.sejda.sambox.input.SourceReader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
    }
}
