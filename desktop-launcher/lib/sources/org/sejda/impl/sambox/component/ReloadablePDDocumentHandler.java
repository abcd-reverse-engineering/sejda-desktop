package org.sejda.impl.sambox.component;

import java.io.IOException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/ReloadablePDDocumentHandler.class */
public class ReloadablePDDocumentHandler {
    private final PdfSource<?> source;
    private final PdfSourceOpener<PDDocumentHandler> documentLoader;
    private PDDocumentHandler documentHandler;

    public ReloadablePDDocumentHandler(PdfSource<?> source, PdfSourceOpener<PDDocumentHandler> documentLoader) throws TaskIOException {
        this.source = source;
        this.documentLoader = documentLoader;
        this.documentHandler = (PDDocumentHandler) this.source.open(documentLoader);
    }

    public PDDocumentHandler reload() throws TaskIOException {
        try {
            this.documentHandler.close();
        } catch (IOException e) {
        }
        this.documentHandler = (PDDocumentHandler) this.source.open(this.documentLoader);
        return this.documentHandler;
    }

    public PDDocumentHandler getDocumentHandler() {
        return this.documentHandler;
    }
}
