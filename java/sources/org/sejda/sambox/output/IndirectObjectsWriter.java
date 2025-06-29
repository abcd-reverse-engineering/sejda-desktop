package org.sejda.sambox.output;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.BufferedCountingChannelWriter;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.cos.IndirectCOSObjectReference;
import org.sejda.sambox.input.BaseCOSParser;
import org.sejda.sambox.input.SourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/IndirectObjectsWriter.class */
class IndirectObjectsWriter implements Closeable {
    private static final byte[] OBJ = SourceReader.OBJ.getBytes(StandardCharsets.US_ASCII);
    private static final byte[] ENDOBJ = BaseCOSParser.ENDOBJ.getBytes(StandardCharsets.US_ASCII);
    private static final Logger LOG = LoggerFactory.getLogger(IndirectObjectsWriter.class);
    private COSWriter writer;
    private PDFWriteContext context;

    IndirectObjectsWriter(CountingWritableByteChannel channel, PDFWriteContext context) {
        this(new BufferedCountingChannelWriter(channel), context);
    }

    IndirectObjectsWriter(BufferedCountingChannelWriter writer, PDFWriteContext context) {
        RequireUtils.requireNotNullArg(writer, "Writer cannot be null");
        RequireUtils.requireNotNullArg(context, "Write context cannot be null");
        this.writer = new EncryptingIndirectReferencesAwareCOSWriter(writer, context);
        this.context = context;
    }

    PDFWriteContext context() {
        return this.context;
    }

    public void writeObjectIfNotWritten(IndirectCOSObjectReference object) throws IOException {
        if (!this.context.hasWritten(object.xrefEntry())) {
            writeObject(object);
        }
    }

    public void writeObject(IndirectCOSObjectReference object) throws IOException {
        this.context.writing(object.xrefEntry().key());
        doWriteObject(object);
        this.context.addWritten(object.xrefEntry());
        onWritten(object);
    }

    protected void onWritten(IndirectCOSObjectReference ref) {
        ref.releaseCOSObject();
        LOG.trace("Released {}", ref);
    }

    private void doWriteObject(IndirectCOSObjectReference object) throws IOException {
        object.xrefEntry().setByteOffset(this.writer.writer().offset());
        this.writer.writer().write(Long.toString(object.xrefEntry().getObjectNumber()));
        this.writer.writer().write((byte) 32);
        this.writer.writer().write(Integer.toString(object.xrefEntry().getGenerationNumber()));
        this.writer.writer().write((byte) 32);
        this.writer.writer().write(OBJ);
        this.writer.writer().writeEOL();
        object.getCOSObject().accept(this.writer);
        this.writer.writer().writeEOL();
        this.writer.writer().write(ENDOBJ);
        this.writer.writer().writeEOL();
        LOG.trace("Written object {}", object.xrefEntry());
    }

    COSWriter writer() {
        return this.writer;
    }

    public void writeEOL() throws IOException {
        this.writer.writer().writeEOL();
    }

    public void write(byte[] bytes) throws IOException {
        this.writer.writer().write(bytes);
    }

    public void write(String string) throws IOException {
        this.writer.writer().write(string);
    }

    public void write(byte b) throws IOException {
        this.writer.writer().write(b);
    }

    public void write(InputStream stream) throws IOException {
        this.writer.writer().write(stream);
    }

    public long offset() {
        return this.writer.writer().offset();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtils.close(this.writer);
        this.context = null;
    }
}
