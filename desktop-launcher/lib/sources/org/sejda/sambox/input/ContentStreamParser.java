package org.sejda.sambox.input;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.commons.util.IOUtils;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.contentstream.PDContentStream;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.util.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/ContentStreamParser.class */
public class ContentStreamParser extends SourceReader {
    private final ContentStreamCOSParser cosParser;
    private final List<Object> tokens;
    private static final Logger LOG = LoggerFactory.getLogger(ContentStreamParser.class);
    private static final int MAX_BIN_CHAR_TEST_LENGTH = 10;
    private final byte[] binCharTestArr;

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ void unreadIfValid(int i) throws IOException {
        super.unreadIfValid(i);
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ void skipSpaces() throws IOException {
        super.skipSpaces();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ String readLiteralString() throws IOException {
        return super.readLiteralString();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ long readLong() throws IOException {
        return super.readLong();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ int readInt() throws IOException {
        return super.readInt();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ String readName() throws IOException {
        return super.readName();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ int readGenerationNumber() throws IOException {
        return super.readGenerationNumber();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ long readObjectNumber() throws IOException {
        return super.readObjectNumber();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ String readLine() throws IOException {
        return super.readLine();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ boolean isNextToken(String[] strArr) throws IOException {
        return super.isNextToken(strArr);
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ void unreadUntilSpaces() throws IOException {
        super.unreadUntilSpaces();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ void unreadSpaces() throws IOException {
        super.unreadSpaces();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ String readToken() throws IOException {
        return super.readToken();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ void skipExpectedIndirectObjectDefinition(COSObjectKey cOSObjectKey) throws IOException {
        super.skipExpectedIndirectObjectDefinition(cOSObjectKey);
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ void skipIndirectObjectDefinition() throws IOException {
        super.skipIndirectObjectDefinition();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ boolean skipTokenIfValue(String str) throws IOException {
        return super.skipTokenIfValue(str);
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ void skipExpected(char c) throws IOException {
        super.skipExpected(c);
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ long length() {
        return super.length();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ void position(long j) throws IOException {
        super.position(j);
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ void offset(long j) throws IOException {
        super.offset(j);
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ long position() throws IOException {
        return super.position();
    }

    @Override // org.sejda.sambox.input.SourceReader
    public /* bridge */ /* synthetic */ SeekableSource source() {
        return super.source();
    }

    public ContentStreamParser(PDContentStream stream) throws IOException {
        this(SeekableSources.inMemorySeekableSourceFrom(stream.getContents()));
    }

    public ContentStreamParser(SeekableSource source) {
        super(source);
        this.tokens = new ArrayList();
        this.binCharTestArr = new byte[10];
        this.cosParser = new ContentStreamCOSParser(source());
    }

    public List<Object> tokens() throws IOException {
        this.tokens.clear();
        while (true) {
            Object token = nextParsedToken();
            if (token != null) {
                this.tokens.add(token);
            } else {
                return Collections.unmodifiableList(this.tokens);
            }
        }
    }

    public Object nextParsedToken() throws IOException {
        skipSpaces();
        long pos = position();
        COSBase token = this.cosParser.nextParsedToken();
        if (token != null) {
            return token;
        }
        position(pos);
        return nextOperator();
    }

    private Object nextOperator() throws IOException {
        if ('B' == ((char) source().peek())) {
            Operator operator = Operator.getOperator(readToken());
            if (OperatorName.BEGIN_INLINE_IMAGE.equals(operator.getName())) {
                nextInlineImage(operator);
            }
            return operator;
        }
        return Optional.ofNullable(readToken()).filter(s -> {
            return s.length() > 0;
        }).map(Operator::getOperator).orElse(null);
    }

    private void nextInlineImage(Operator operator) throws IOException {
        COSDictionary imageParams = new COSDictionary();
        operator.setImageParameters(imageParams);
        long jPosition = position();
        while (true) {
            long position = jPosition;
            COSBase nextToken = this.cosParser.nextParsedToken();
            if (nextToken instanceof COSName) {
                imageParams.setItem((COSName) nextToken, this.cosParser.nextParsedToken());
                jPosition = position();
            } else {
                position(position);
                operator.setImageData(nextImageData());
                return;
            }
        }
    }

    private byte[] nextImageData() throws IOException {
        skipSpaces();
        skipExpected(OperatorName.BEGIN_INLINE_IMAGE_DATA);
        if (!CharUtils.isWhitespace(source().read())) {
            source().back();
        }
        FastByteArrayOutputStream imageData = new FastByteArrayOutputStream();
        while (true) {
            try {
                int current = source().read();
                if (current == -1) {
                    break;
                }
                long position = source().position();
                if ((current == 69 && isEndOfImageFrom(position - 1)) || (CharUtils.isWhitespace(current) && isEndOfImageFrom(position))) {
                    break;
                }
                imageData.write(current);
            } catch (Throwable th) {
                try {
                    imageData.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        byte[] byteArray = imageData.toByteArray();
        imageData.close();
        return byteArray;
    }

    private boolean isEndOfImageFrom(long position) throws IOException {
        long currentPosition = source().position();
        source().position(position);
        int current = source().read();
        if (current == 69) {
            int current2 = source().read();
            if (current2 == 73 && ((isEndOfImage() || CharUtils.isEOF(source().peek())) && hasNoFollowingBinData())) {
                return true;
            }
        }
        source().position(currentPosition);
        return false;
    }

    private boolean isEndOfImage() throws IOException {
        long currentPosition = source().position();
        try {
            int current = source().read();
            if (!CharUtils.isSpace(current) && !CharUtils.isEOL(current)) {
                source().position(currentPosition);
                return false;
            }
            for (int i = 0; i < 10; i++) {
                int current2 = source().read();
                if ((!CharUtils.isNul(current2) || CharUtils.isNul(source().peek())) && !CharUtils.isEOF(current2) && !CharUtils.isEOL(current2) && (current2 < 32 || current2 > 127)) {
                    return false;
                }
            }
            source().position(currentPosition);
            return true;
        } finally {
            source().position(currentPosition);
        }
    }

    private boolean hasNoFollowingBinData() throws IOException {
        long originalPosition = source().position();
        try {
            int readBytes = source().read(ByteBuffer.wrap(this.binCharTestArr));
            boolean noBinData = true;
            int startOpIdx = -1;
            int endOpIdx = -1;
            if (readBytes > 0) {
                for (int bIdx = 0; bIdx < readBytes; bIdx++) {
                    byte b = this.binCharTestArr[bIdx];
                    if ((b != 0 && b < 9) || (b > 10 && b < 32 && b != 13)) {
                        noBinData = false;
                        break;
                    }
                    if (startOpIdx == -1 && b != 0 && b != 9 && b != 32 && b != 10 && b != 13) {
                        startOpIdx = bIdx;
                    } else if (startOpIdx != -1 && endOpIdx == -1 && (b == 0 || b == 9 || b == 32 || b == 10 || b == 13)) {
                        endOpIdx = bIdx;
                    }
                }
                if (endOpIdx != -1 && startOpIdx != -1) {
                    String s = new String(this.binCharTestArr, startOpIdx, endOpIdx - startOpIdx);
                    if (!OperatorName.SAVE.equals(s) && !OperatorName.RESTORE.equals(s) && !OperatorName.END_MARKED_CONTENT.equals(s) && !"S".equals(s)) {
                        noBinData = false;
                    }
                }
                if (readBytes == 10) {
                    if (startOpIdx != -1 && endOpIdx == -1) {
                        endOpIdx = 10;
                    }
                    if (endOpIdx != -1 && startOpIdx != -1 && endOpIdx - startOpIdx > 3) {
                        noBinData = false;
                    }
                }
            }
            if (!noBinData) {
                LOG.warn("ignoring 'EI' assumed to be in the middle of inline image at stream offset " + originalPosition);
            }
            return noBinData;
        } finally {
            source().position(originalPosition);
        }
    }

    @Override // org.sejda.sambox.input.SourceReader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        IOUtils.closeQuietly(this.cosParser);
    }
}
