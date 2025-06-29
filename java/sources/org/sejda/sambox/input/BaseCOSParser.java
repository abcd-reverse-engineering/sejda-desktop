package org.sejda.sambox.input;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.sejda.io.SeekableSource;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSBoolean;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.pdmodel.encryption.PDEncryption;
import org.sejda.sambox.util.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/BaseCOSParser.class */
abstract class BaseCOSParser extends SourceReader {
    private static final Logger LOG = LoggerFactory.getLogger(BaseCOSParser.class);
    public static final String ENDOBJ = "endobj";
    public static final String STREAM = "stream";
    public static final String ENDSTREAM = "endstream";

    public abstract COSBase nextParsedToken() throws IOException;

    BaseCOSParser(SeekableSource source) {
        super(source);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0089, code lost:
    
        skipExpected(">>");
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0090, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.sejda.sambox.cos.COSDictionary nextDictionary() throws java.io.IOException {
        /*
            r6 = this;
            r0 = r6
            java.lang.String r1 = "<<"
            r0.skipExpected(r1)
            r0 = r6
            r0.skipSpaces()
            org.sejda.sambox.cos.COSDictionary r0 = new org.sejda.sambox.cos.COSDictionary
            r1 = r0
            r1.<init>()
            r7 = r0
        L12:
            r0 = r6
            org.sejda.io.SeekableSource r0 = r0.source()
            int r0 = r0.peek()
            r1 = r0
            r8 = r1
            r1 = -1
            if (r0 == r1) goto L89
            r0 = r8
            r1 = 62
            if (r0 == r1) goto L89
            r0 = r8
            r1 = 47
            if (r0 == r1) goto L4c
            org.slf4j.Logger r0 = org.sejda.sambox.input.BaseCOSParser.LOG
            java.lang.String r1 = "Invalid dictionary key, expected '/' but was '{}' around position {}"
            r2 = r8
            char r2 = (char) r2
            java.lang.Character r2 = java.lang.Character.valueOf(r2)
            r3 = r6
            long r3 = r3.position()
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r0.warn(r1, r2, r3)
            r0 = r6
            boolean r0 = r0.consumeInvalidDictionaryKey()
            if (r0 != 0) goto L82
            r0 = r7
            return r0
        L4c:
            r0 = r6
            org.sejda.sambox.cos.COSName r0 = r0.nextName()
            r9 = r0
            r0 = r6
            org.sejda.sambox.cos.COSBase r0 = r0.nextParsedToken()
            r10 = r0
            r0 = r9
            boolean r0 = java.util.Objects.isNull(r0)
            if (r0 != 0) goto L66
            r0 = r10
            boolean r0 = java.util.Objects.isNull(r0)
            if (r0 == 0) goto L7b
        L66:
            org.slf4j.Logger r0 = org.sejda.sambox.input.BaseCOSParser.LOG
            java.lang.String r1 = "Bad dictionary declaration for key '{}' around position {}"
            r2 = r9
            r3 = r6
            long r3 = r3.position()
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r0.warn(r1, r2, r3)
            goto L82
        L7b:
            r0 = r7
            r1 = r9
            r2 = r10
            r0.setItem(r1, r2)
        L82:
            r0 = r6
            r0.skipSpaces()
            goto L12
        L89:
            r0 = r6
            java.lang.String r1 = ">>"
            r0.skipExpected(r1)
            r0 = r7
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.sejda.sambox.input.BaseCOSParser.nextDictionary():org.sejda.sambox.cos.COSDictionary");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0052, code lost:
    
        if (r0 == (-1)) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0055, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0059, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean consumeInvalidDictionaryKey() throws java.io.IOException {
        /*
            r6 = this;
        L0:
            r0 = r6
            org.sejda.io.SeekableSource r0 = r0.source()
            int r0 = r0.peek()
            r1 = r0
            r7 = r1
            r1 = -1
            if (r0 == r1) goto L50
            r0 = r7
            r1 = 62
            if (r0 == r1) goto L50
            r0 = r7
            r1 = 47
            if (r0 == r1) goto L50
            r0 = r6
            r1 = 2
            java.lang.String[] r1 = new java.lang.String[r1]
            r2 = r1
            r3 = 0
            java.lang.String r4 = "endobj"
            r2[r3] = r4
            r2 = r1
            r3 = 1
            java.lang.String r4 = "endstream"
            r2[r3] = r4
            boolean r0 = r0.isNextToken(r1)
            if (r0 == 0) goto L43
            org.slf4j.Logger r0 = org.sejda.sambox.input.BaseCOSParser.LOG
            java.lang.String r1 = "Found unexpected 'endobj or 'endstream' at position {}, assuming end of dictionary"
            r2 = r6
            long r2 = r2.position()
            java.lang.Long r2 = java.lang.Long.valueOf(r2)
            r0.warn(r1, r2)
            r0 = 0
            return r0
        L43:
            r0 = r6
            org.sejda.io.SeekableSource r0 = r0.source()
            int r0 = r0.read()
            goto L0
        L50:
            r0 = r7
            r1 = -1
            if (r0 == r1) goto L59
            r0 = 1
            goto L5a
        L59:
            r0 = 0
        L5a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.sejda.sambox.input.BaseCOSParser.consumeInvalidDictionaryKey():boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00dd, code lost:
    
        skipExpected(']');
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00e4, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.sejda.sambox.cos.COSArray nextArray() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.sejda.sambox.input.BaseCOSParser.nextArray():org.sejda.sambox.cos.COSArray");
    }

    public COSBoolean nextBoolean() throws IOException {
        char c = (char) source().peek();
        if (c == 't') {
            skipExpected(Boolean.TRUE.toString());
            return COSBoolean.TRUE;
        }
        skipExpected(Boolean.FALSE.toString());
        return COSBoolean.FALSE;
    }

    public COSNumber nextNumber() throws IOException {
        return COSNumber.get(readNumber());
    }

    public COSNull nextNull() throws IOException {
        skipExpected("null");
        return COSNull.NULL;
    }

    public COSName nextName() throws IOException {
        return COSName.getPDFName(readName());
    }

    public COSString nextLiteralString() throws IOException {
        return COSString.newInstance(readLiteralString().getBytes(StandardCharsets.ISO_8859_1));
    }

    public COSString nextHexadecimalString() throws IOException {
        return COSString.parseHex(readHexString());
    }

    public COSString nextString() throws IOException {
        char next = (char) source().peek();
        switch (next) {
            case PDEncryption.DEFAULT_LENGTH /* 40 */:
                return nextLiteralString();
            case '<':
                return nextHexadecimalString();
            default:
                throw new IOException(String.format("Expected '(' or '<' at offset %d but was '%c'", Long.valueOf(position()), Character.valueOf(next)));
        }
    }

    public COSStream nextStream(COSDictionary streamDictionary) throws IOException {
        int c;
        COSStream stream;
        skipSpaces();
        skipExpected(STREAM);
        int i = source().read();
        while (true) {
            c = i;
            if (!CharUtils.isSpace(c)) {
                break;
            }
            LOG.warn("Found unexpected space character after 'stream' keyword");
            i = source().read();
        }
        if (CharUtils.isCarriageReturn(c)) {
            if (!CharUtils.isLineFeed(source().read())) {
                source().back();
                LOG.warn("Couldn't find expected LF following CR after 'stream' keyword at " + position());
            }
        } else if (!CharUtils.isLineFeed(c)) {
            source().back();
        }
        long length = streamLength(streamDictionary);
        if (length > 0) {
            stream = new COSStream(streamDictionary, source(), position(), length);
        } else {
            stream = new COSStream(streamDictionary);
        }
        source().forward(stream.getFilteredLength());
        if (!skipTokenIfValue(ENDSTREAM) && isNextToken(ENDOBJ)) {
            LOG.warn("Expected 'endstream' at " + position() + " but was 'endobj'");
        }
        return stream;
    }

    private long streamLength(COSDictionary streamDictionary) throws IOException {
        long length = streamLengthFrom(streamDictionary);
        if (length <= 0) {
            LOG.info("Using fallback strategy reading until 'endstream' or 'endobj' is found. Starting at offset " + position());
            length = findStreamLength();
        }
        return length;
    }

    private long streamLengthFrom(COSDictionary streamDictionary) throws IOException {
        long start = position();
        COSBase lengthBaseObj = streamDictionary.getItem(COSName.LENGTH);
        try {
            long jDoStreamLengthFrom = doStreamLengthFrom(lengthBaseObj);
            position(start);
            return jDoStreamLengthFrom;
        } catch (Throwable th) {
            position(start);
            throw th;
        }
    }

    private long doStreamLengthFrom(COSBase lengthBaseObj) throws IOException {
        long startingOffset = position();
        if (lengthBaseObj == null) {
            LOG.warn("Invalid stream length. No length provided");
            return -1L;
        }
        COSBase retVal = lengthBaseObj.getCOSObject();
        if (!(retVal instanceof COSNumber)) {
            LOG.warn("Invalid stream length. Expected number instance but was " + retVal.getClass().getSimpleName());
            return -1L;
        }
        long length = ((COSNumber) retVal).longValue();
        long endStreamOffset = startingOffset + length;
        if (endStreamOffset > length()) {
            LOG.warn("Invalid stream length. Out of range");
            return -1L;
        }
        position(endStreamOffset);
        if (!isNextToken(ENDSTREAM)) {
            LOG.warn("Invalid stream length. Expected 'endstream' at " + endStreamOffset);
            return -1L;
        }
        return length;
    }

    private long findStreamLength() throws IOException {
        long start = position();
        try {
            long jDoFindStreamLength = doFindStreamLength(start);
            position(start);
            return jDoFindStreamLength;
        } catch (Throwable th) {
            position(start);
            throw th;
        }
    }

    private long doFindStreamLength(long start) throws IOException {
        long currentPosition;
        Matcher matcher;
        Pattern pattern = Pattern.compile("endstream|endobj");
        do {
            currentPosition = position();
            String currentLine = readLine();
            matcher = pattern.matcher(currentLine);
        } while (!matcher.find());
        position(currentPosition + matcher.start());
        long length = position() - start;
        int prevChar = source().back().peek();
        if (CharUtils.isCarriageReturn(prevChar)) {
            return length - 1;
        }
        if (CharUtils.isLineFeed(prevChar)) {
            if (CharUtils.isCarriageReturn(source().back().peek())) {
                return length - 2;
            }
            return length - 1;
        }
        return length;
    }
}
