package org.sejda.sambox.output;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.BufferedCountingChannelWriter;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSBoolean;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.cos.IndirectCOSObjectReference;
import org.sejda.sambox.input.BaseCOSParser;
import org.sejda.sambox.util.CharUtils;
import org.sejda.sambox.util.Hex;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/DefaultCOSWriter.class */
class DefaultCOSWriter implements COSWriter {
    protected static final byte SPACE = 32;
    private static final byte SOLIDUS = 47;
    private static final byte REVERSE_SOLIDUS = 92;
    private static final byte NUMBER_SIGN = 35;
    protected static final byte PERCENT_SIGN = 37;
    private static final byte LESS_THEN = 60;
    private static final byte GREATER_THEN = 62;
    private static final byte LEFT_PARENTHESIS = 40;
    private static final byte RIGHT_PARENTHESIS = 41;
    private static final byte LEFT_SQUARE_BRACKET = 91;
    private static final byte RIGHT_SQUARE_BRACKET = 93;
    private final BufferedCountingChannelWriter writer;
    private static final byte[] CRLF = {13, 10};
    private static final byte[] STREAM = BaseCOSParser.STREAM.getBytes(StandardCharsets.US_ASCII);
    private static final byte[] ENDSTREAM = BaseCOSParser.ENDSTREAM.getBytes(StandardCharsets.US_ASCII);

    public DefaultCOSWriter(CountingWritableByteChannel channel) {
        RequireUtils.requireNotNullArg(channel, "Cannot write to a null channel");
        this.writer = new BufferedCountingChannelWriter(channel);
    }

    public DefaultCOSWriter(BufferedCountingChannelWriter writer) {
        RequireUtils.requireNotNullArg(writer, "Cannot write to a null writer");
        this.writer = writer;
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSArray value) throws IOException {
        this.writer.write((byte) 91);
        Iterator<COSBase> i = value.iterator();
        while (i.hasNext()) {
            COSBase current = i.next();
            writeValue((COSBase) Optional.ofNullable(current).orElse(COSNull.NULL));
            if (i.hasNext()) {
                this.writer.write((byte) 32);
            }
        }
        this.writer.write((byte) 93);
        writeComplexObjectSeparator();
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSBoolean value) throws IOException {
        this.writer.write(value.toString());
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSDictionary dictionary) throws IOException {
        this.writer.write((byte) 60);
        this.writer.write((byte) 60);
        writeDictionaryItemsSeparator();
        for (Map.Entry<COSName, COSBase> entry : dictionary.entrySet()) {
            COSBase value = entry.getValue();
            if (value != null) {
                entry.getKey().accept(this);
                this.writer.write((byte) 32);
                writeValue(entry.getValue());
                writeDictionaryItemsSeparator();
            }
        }
        this.writer.write((byte) 62);
        this.writer.write((byte) 62);
        writeComplexObjectSeparator();
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSFloat value) throws IOException {
        this.writer.write(value.toString());
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSInteger value) throws IOException {
        this.writer.write(value.toString());
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSName value) throws IOException {
        this.writer.write((byte) 47);
        byte[] bytes = value.getName().getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < bytes.length; i++) {
            int current = bytes[i] & 255;
            if (CharUtils.isLetter(current) || CharUtils.isDigit(current)) {
                this.writer.write(bytes[i]);
            } else {
                this.writer.write((byte) 35);
                this.writer.write(Hex.getBytes(bytes[i]));
            }
        }
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSNull value) throws IOException {
        this.writer.write("null".getBytes(StandardCharsets.US_ASCII));
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSStream value) throws IOException {
        try {
            COSBase length = value.getItem(COSName.LENGTH);
            if (Objects.isNull(length)) {
                value.setLong(COSName.LENGTH, value.getFilteredLength());
            }
            visit((COSDictionary) value);
            this.writer.write(STREAM);
            this.writer.write(CRLF);
            long streamStartingPosition = this.writer.offset();
            InputStream stream = value.getFilteredStream();
            try {
                this.writer.write(stream);
                if (stream != null) {
                    stream.close();
                }
                if (length instanceof IndirectCOSObjectReference) {
                    ((IndirectCOSObjectReference) length).setValue(new COSInteger(this.writer.offset() - streamStartingPosition));
                }
                this.writer.write(CRLF);
                this.writer.write(ENDSTREAM);
                writeComplexObjectSeparator();
                IOUtils.closeQuietly(value);
            } finally {
            }
        } catch (Throwable th) {
            IOUtils.closeQuietly(value);
            throw th;
        }
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSString value) throws IOException {
        if (value.isForceHexForm()) {
            this.writer.write((byte) 60);
            this.writer.write(value.toHexString());
            this.writer.write((byte) 62);
            return;
        }
        this.writer.write((byte) 40);
        for (byte b : value.getBytes()) {
            switch (b) {
                case 40:
                case RIGHT_PARENTHESIS /* 41 */:
                case REVERSE_SOLIDUS /* 92 */:
                    this.writer.write((byte) 92);
                    break;
            }
            this.writer.write(b);
        }
        this.writer.write((byte) 41);
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(IndirectCOSObjectReference value) throws IOException {
        this.writer.write(value.toString());
    }

    void writeValue(COSBase value) throws IOException {
        value.accept(this);
    }

    @Override // org.sejda.sambox.output.COSWriter
    public BufferedCountingChannelWriter writer() {
        return this.writer;
    }
}
