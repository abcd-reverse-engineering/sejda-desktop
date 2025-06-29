package org.sejda.sambox.output;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.encryption.EncryptionContext;
import org.sejda.sambox.input.IncrementablePDDocument;
import org.sejda.sambox.util.SpecVersionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/IncrementablePDDocumentWriter.class */
public class IncrementablePDDocumentWriter implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(IncrementablePDDocumentWriter.class);
    private DefaultPDFWriter writer;
    private PDFWriteContext context;
    private CountingWritableByteChannel channel;
    private Set<WriteOption> options;

    public IncrementablePDDocumentWriter(CountingWritableByteChannel channel, WriteOption... options) {
        RequireUtils.requireNotNullArg(channel, "Cannot write to a null channel");
        this.channel = channel;
        this.options = (Set) Optional.ofNullable(options).map((v0) -> {
            return Arrays.asList(v0);
        }).map((v1) -> {
            return new HashSet(v1);
        }).orElseGet(HashSet::new);
    }

    public void write(IncrementablePDDocument document) throws IOException {
        RequireUtils.requireNotNullArg(document, "Incremented document cannot be null");
        RequireUtils.requireState(document.trailer().xrefOffset() != -1, "The incremented document has errors and its xref table couldn't be found");
        sanitizeWriteOptions(document);
        this.context = new PDFWriteContext(document.highestExistingReference().objectNumber(), EncryptionContext.encryptionAlgorithmFromEncryptionDictionary(document.encryptionDictionary(), document.encryptionKey()), (WriteOption[]) this.options.toArray(x$0 -> {
            return new WriteOption[x$0];
        }));
        this.writer = new DefaultPDFWriter(new IndirectObjectsWriter(this.channel, this.context));
        InputStream stream = document.incrementedAsStream();
        try {
            this.writer.writer().write(stream);
            if (stream != null) {
                stream.close();
            }
            this.writer.writer().writeEOL();
            writeBody(document);
            writeXref(document);
        } catch (Throwable th) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private void sanitizeWriteOptions(IncrementablePDDocument document) {
        if (document.trailer().isXrefStream()) {
            this.options.add(WriteOption.XREF_STREAM);
        } else {
            this.options.remove(WriteOption.XREF_STREAM);
            this.options.remove(WriteOption.OBJECT_STREAMS);
        }
        if (!SpecVersionUtils.isAtLeast(document.incremented().getVersion(), SpecVersionUtils.V1_5)) {
            this.options.remove(WriteOption.OBJECT_STREAMS);
        }
    }

    private void writeBody(IncrementablePDDocument document) throws IOException {
        PDFBodyWriter bodyWriter = new IncrementalPDFBodyWriter(this.context, objectStreamWriter(objectsWriter()));
        try {
            LOG.debug("Writing body using " + bodyWriter.objectsWriter.getClass());
            bodyWriter.write(document);
            bodyWriter.close();
        } catch (Throwable th) {
            try {
                bodyWriter.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private PDFBodyObjectsWriter objectsWriter() {
        if (this.context.hasWriteOption(WriteOption.ASYNC_BODY_WRITE)) {
            return new AsyncPDFBodyObjectsWriter(this.writer.writer());
        }
        return new SyncPDFBodyObjectsWriter(this.writer.writer());
    }

    private PDFBodyObjectsWriter objectStreamWriter(PDFBodyObjectsWriter wrapped) {
        if (this.context.hasWriteOption(WriteOption.OBJECT_STREAMS)) {
            return new ObjectsStreamPDFBodyObjectsWriter(this.context, wrapped);
        }
        return wrapped;
    }

    private void writeXref(IncrementablePDDocument document) throws IOException {
        if (this.context.hasWriteOption(WriteOption.XREF_STREAM) || this.context.hasWriteOption(WriteOption.OBJECT_STREAMS)) {
            this.writer.writeXrefStream(document.trailer().getCOSObject(), document.trailer().xrefOffset());
        } else {
            long startxref = this.writer.writeXrefTable();
            this.writer.writeTrailer(document.trailer().getCOSObject(), startxref, document.trailer().xrefOffset());
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtils.close(this.writer);
    }
}
