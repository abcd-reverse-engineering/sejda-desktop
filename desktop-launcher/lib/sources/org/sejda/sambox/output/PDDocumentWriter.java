package org.sejda.sambox.output;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.cos.COSDocument;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.encryption.EncryptionContext;
import org.sejda.sambox.encryption.GeneralEncryptionAlgorithm;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.util.SpecVersionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/PDDocumentWriter.class */
public class PDDocumentWriter implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(PDDocumentWriter.class);
    private DefaultPDFWriter writer;
    private PDFWriteContext context;
    private Optional<EncryptionContext> encryptionContext;

    public PDDocumentWriter(CountingWritableByteChannel channel, Optional<EncryptionContext> encryptionContext, WriteOption... options) {
        RequireUtils.requireNotNullArg(channel, "Cannot write to a null channel");
        this.encryptionContext = (Optional) Optional.ofNullable(encryptionContext).orElseGet(Optional::empty);
        this.context = new PDFWriteContext((GeneralEncryptionAlgorithm) this.encryptionContext.map((v0) -> {
            return v0.encryptionAlgorithm();
        }).orElse(null), options);
        this.writer = new DefaultPDFWriter(new IndirectObjectsWriter(channel, this.context));
    }

    public void write(PDDocument document) throws IllegalStateException, IOException {
        RequireUtils.requireNotNullArg(document, "PDDocument cannot be null");
        if (this.context.hasWriteOption(WriteOption.XREF_STREAM) || this.context.hasWriteOption(WriteOption.OBJECT_STREAMS)) {
            document.requireMinVersion(SpecVersionUtils.V1_5);
        }
        Optional.ofNullable(document.getDocument().getTrailer()).map((v0) -> {
            return v0.getCOSObject();
        }).ifPresent(t -> {
            t.removeItem(COSName.ENCRYPT);
        });
        this.encryptionContext.ifPresent(c -> {
            document.getDocument().setEncryptionDictionary(c.security.encryption.generateEncryptionDictionary(c));
            LOG.debug("Generated encryption dictionary");
            Optional.ofNullable(document.getDocumentCatalog().getMetadata()).map((v0) -> {
                return v0.getCOSObject();
            }).ifPresent(str -> {
                str.encryptable(c.security.encryptMetadata);
            });
        });
        this.writer.writeHeader(document.getDocument().getHeaderVersion());
        writeBody(document.getDocument());
        writeXref(document);
    }

    private void writeBody(COSDocument document) throws IOException {
        PDFBodyWriter bodyWriter = new PDFBodyWriter(this.context, objectStreamWriter(objectsWriter()));
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

    private void writeXref(PDDocument document) throws IOException {
        if (this.context.hasWriteOption(WriteOption.XREF_STREAM) || this.context.hasWriteOption(WriteOption.OBJECT_STREAMS)) {
            this.writer.writeXrefStream(document.getDocument().getTrailer().getCOSObject());
        } else {
            long startxref = this.writer.writeXrefTable();
            this.writer.writeTrailer(document.getDocument().getTrailer().getCOSObject(), startxref);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtils.close(this.writer);
    }
}
