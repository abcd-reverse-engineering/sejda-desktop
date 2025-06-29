package org.sejda.sambox.output;

import java.io.IOException;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.IndirectCOSObjectReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/SyncPDFBodyObjectsWriter.class */
class SyncPDFBodyObjectsWriter implements PDFBodyObjectsWriter {
    private static final Logger LOG = LoggerFactory.getLogger(SyncPDFBodyObjectsWriter.class);
    private IndirectObjectsWriter writer;

    SyncPDFBodyObjectsWriter(IndirectObjectsWriter writer) {
        RequireUtils.requireNotNullArg(writer, "Cannot write to a null writer");
        this.writer = writer;
    }

    @Override // org.sejda.sambox.output.PDFBodyObjectsWriter
    public void writeObject(IndirectCOSObjectReference ref) throws IOException {
        this.writer.writeObjectIfNotWritten(ref);
    }

    @Override // org.sejda.sambox.output.PDFBodyObjectsWriter
    public void onWriteCompletion() {
        LOG.debug("Written document body");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }
}
