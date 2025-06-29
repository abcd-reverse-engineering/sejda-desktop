package org.sejda.sambox.output;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterInputStream;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.SAMBox;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.DirectCOSObject;
import org.sejda.sambox.cos.DisposableCOSObject;
import org.sejda.sambox.cos.IndirectCOSObjectReference;
import org.sejda.sambox.cos.NonStorableInObjectStreams;
import org.sejda.sambox.xref.CompressedXrefEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/ObjectsStreamPDFBodyObjectsWriter.class */
public class ObjectsStreamPDFBodyObjectsWriter implements PDFBodyObjectsWriter {
    private static final Logger LOG = LoggerFactory.getLogger(ObjectsStreamPDFBodyObjectsWriter.class);
    private PDFWriteContext context;
    private PDFBodyObjectsWriter delegate;
    private ObjectsStream currentStream;

    public ObjectsStreamPDFBodyObjectsWriter(PDFWriteContext context, PDFBodyObjectsWriter delegate) {
        RequireUtils.requireNotNullArg(context, "Write context cannot be null");
        RequireUtils.requireNotNullArg(delegate, "Delegate writer cannot be null");
        this.context = context;
        this.delegate = delegate;
        this.currentStream = new ObjectsStream(context);
    }

    @Override // org.sejda.sambox.output.PDFBodyObjectsWriter
    public void writeObject(IndirectCOSObjectReference ref) throws IOException {
        if ((ref instanceof NonStorableInObjectStreams) || (ref.getCOSObject().getCOSObject() instanceof COSStream)) {
            this.delegate.writeObject(ref);
        } else {
            this.context.addWritten(CompressedXrefEntry.compressedEntry(ref.xrefEntry().getObjectNumber(), this.currentStream.reference().xrefEntry().getObjectNumber(), this.currentStream.counter));
            this.currentStream.addItem(ref);
            LOG.trace("Added ref {} to object stream {}", ref, this.currentStream.reference());
        }
        if (this.currentStream.isFull()) {
            doWriteObjectsStream();
            this.currentStream = new ObjectsStream(this.context);
        }
    }

    private void doWriteObjectsStream() throws IOException {
        LOG.debug("Writing object stream {}", this.currentStream.reference());
        this.currentStream.prepareForWriting();
        IndirectCOSObjectReference length = this.context.createNonStorableInObjectStreamIndirectReference();
        this.currentStream.setItem(COSName.LENGTH, (COSBase) length);
        this.delegate.writeObject(this.currentStream.reference());
        LOG.trace("Writing object stream length {}", length);
        this.delegate.writeObject(length);
    }

    @Override // org.sejda.sambox.output.PDFBodyObjectsWriter
    public void onWriteCompletion() throws IOException {
        if (this.currentStream.hasItems()) {
            doWriteObjectsStream();
        }
        this.delegate.onWriteCompletion();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtils.close(this.delegate);
        this.currentStream = null;
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/ObjectsStreamPDFBodyObjectsWriter$ObjectsStream.class */
    static class ObjectsStream extends COSStream implements DisposableCOSObject {
        private int counter;
        private FastByteArrayOutputStream header = new FastByteArrayOutputStream();
        private FastByteArrayOutputStream data = new FastByteArrayOutputStream();
        private DefaultCOSWriter dataWriter;
        private InputStream filtered;
        private IndirectCOSObjectReference reference;

        public ObjectsStream(PDFWriteContext context) {
            setName(COSName.TYPE, COSName.OBJ_STM.getName());
            this.dataWriter = new IndirectReferencesAwareCOSWriter(CountingWritableByteChannel.from(this.data), context) { // from class: org.sejda.sambox.output.ObjectsStreamPDFBodyObjectsWriter.ObjectsStream.1
                @Override // org.sejda.sambox.output.COSWriter
                public void writeComplexObjectSeparator() {
                }

                @Override // org.sejda.sambox.output.COSWriter
                public void writeDictionaryItemsSeparator() {
                }
            };
            this.reference = context.createIndirectReferenceFor(this);
        }

        public boolean hasItems() {
            return this.counter > 0;
        }

        void addItem(IndirectCOSObjectReference ref) throws IOException {
            this.counter++;
            this.header.write(Long.toUnsignedString(ref.xrefEntry().getObjectNumber()).getBytes(StandardCharsets.US_ASCII));
            this.header.write(32);
            this.header.write(Long.toUnsignedString(this.dataWriter.writer().offset()).getBytes(StandardCharsets.US_ASCII));
            this.header.write(32);
            ref.getCOSObject().accept(this.dataWriter);
            this.dataWriter.writer().write((byte) 32);
            ref.releaseCOSObject();
        }

        boolean isFull() {
            return this.counter >= Integer.getInteger(SAMBox.OBJECTS_STREAM_SIZE_PROPERTY, 100).intValue();
        }

        @Override // org.sejda.sambox.cos.COSStream
        public InputStream doGetFilteredStream() {
            return this.filtered;
        }

        void prepareForWriting() {
            IOUtils.closeQuietly(this.dataWriter);
            setItem(COSName.N, (COSBase) DirectCOSObject.asDirectObject(COSInteger.get(this.counter)));
            setItem(COSName.FIRST, (COSBase) DirectCOSObject.asDirectObject(COSInteger.get(this.header.size())));
            setItem(COSName.FILTER, (COSBase) DirectCOSObject.asDirectObject(COSName.FLATE_DECODE));
            this.filtered = new DeflaterInputStream(new SequenceInputStream(new ByteArrayInputStream(this.header.toByteArray()), new ByteArrayInputStream(this.data.toByteArray())));
            this.header = null;
            this.data = null;
        }

        IndirectCOSObjectReference reference() {
            return this.reference;
        }

        @Override // org.sejda.sambox.cos.COSStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            IOUtils.closeQuietly(this.filtered);
            super.close();
        }

        @Override // org.sejda.sambox.cos.DisposableCOSObject
        public void releaseCOSObject() {
            this.filtered = null;
        }
    }
}
