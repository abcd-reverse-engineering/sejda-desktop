package org.sejda.sambox.output;

import java.io.IOException;
import org.sejda.commons.util.IOUtils;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.io.DevNullWritableByteChannel;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSDocument;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.IndirectCOSObjectReference;
import org.sejda.sambox.input.IncrementablePDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/ExistingPagesSizePredictor.class */
public class ExistingPagesSizePredictor extends PDFBodyWriter {
    private static final Logger LOG = LoggerFactory.getLogger(ExistingPagesSizePredictor.class);
    private static final int STREAM_WRAPPING_SIZE = 19;
    private final CountingWritableByteChannel channel;
    private final IndirectObjectsWriter writer;
    private long pages;

    @Override // org.sejda.sambox.output.PDFBodyWriter
    public /* bridge */ /* synthetic */ void onPotentialIndirectObject(COSBase cOSBase) throws IOException {
        super.onPotentialIndirectObject(cOSBase);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSStream cOSStream) throws IOException {
        super.visit(cOSStream);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSDictionary cOSDictionary) throws IOException {
        super.visit(cOSDictionary);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSArray cOSArray) throws IOException {
        super.visit(cOSArray);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSDocument cOSDocument) throws IOException {
        super.visit(cOSDocument);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter
    public /* bridge */ /* synthetic */ void write(COSDocument cOSDocument) throws IOException {
        super.write(cOSDocument);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter
    public /* bridge */ /* synthetic */ void write(IncrementablePDDocument incrementablePDDocument) throws IOException {
        super.write(incrementablePDDocument);
    }

    private ExistingPagesSizePredictor(PDFWriteContext context, IndirectObjectsWriter writer, CountingWritableByteChannel channel) {
        super(context, new BodyObjectsWriter(context, writer));
        this.channel = channel;
        this.writer = writer;
    }

    public void addPage(PDPage page) throws IOException {
        if (page != null) {
            this.pages++;
            COSDictionary pageCopy = page.getCOSObject().duplicate();
            pageCopy.removeItem(COSName.PARENT);
            createIndirectReferenceIfNeededFor(pageCopy);
            startWriting();
            LOG.debug("Page {} addition simulated, now at {} body bytes and {} xref bytes", new Object[]{page, Long.valueOf(predictedPagesSize()), Long.valueOf(predictedXrefTableSize())});
        }
    }

    public void addIndirectReferenceFor(COSObjectable value) throws IOException {
        if (value != null) {
            createIndirectReferenceIfNeededFor(value.getCOSObject());
            startWriting();
            LOG.debug("{} addition simulated, now at {} body bytes and {} xref bytes", new Object[]{value.getCOSObject(), Long.valueOf(predictedPagesSize()), Long.valueOf(predictedXrefTableSize())});
        }
    }

    public long predictedPagesSize() throws IOException {
        this.writer.writer().writer().flush();
        return ((BodyObjectsWriter) this.objectsWriter).streamsSize + this.channel.count();
    }

    public long predictedXrefTableSize() {
        return (21 * (context().written() + 1)) + 10;
    }

    public boolean hasPages() {
        return this.pages > 0;
    }

    public long pages() {
        return this.pages;
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        IOUtils.close(this.writer);
    }

    public static ExistingPagesSizePredictor instance(WriteOption... opts) {
        CountingWritableByteChannel channel = CountingWritableByteChannel.from(new DevNullWritableByteChannel());
        PDFWriteContext context = new PDFWriteContext(null, opts);
        IndirectObjectsWriter writer = new IndirectObjectsWriter(channel, context) { // from class: org.sejda.sambox.output.ExistingPagesSizePredictor.1
            @Override // org.sejda.sambox.output.IndirectObjectsWriter
            protected void onWritten(IndirectCOSObjectReference ref) {
            }
        };
        return new ExistingPagesSizePredictor(context, writer, channel);
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/ExistingPagesSizePredictor$BodyObjectsWriter.class */
    private static class BodyObjectsWriter implements PDFBodyObjectsWriter {
        long streamsSize;
        private final PDFWriteContext context;
        private final IndirectObjectsWriter writer;

        public BodyObjectsWriter(PDFWriteContext context, IndirectObjectsWriter writer) {
            this.context = context;
            this.writer = writer;
        }

        @Override // org.sejda.sambox.output.PDFBodyObjectsWriter
        public void writeObject(IndirectCOSObjectReference ref) throws IOException {
            if (!this.context.hasWritten(ref.xrefEntry())) {
                COSBase wrapped = ref.getCOSObject().getCOSObject();
                if (wrapped instanceof COSStream) {
                    COSStream stream = (COSStream) wrapped;
                    this.streamsSize += stream.getFilteredLength();
                    this.streamsSize += 19;
                    ref.setValue(stream.duplicate());
                }
            }
            this.writer.writeObject(ref);
        }

        @Override // org.sejda.sambox.output.PDFBodyObjectsWriter
        public void onWriteCompletion() {
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }
    }
}
