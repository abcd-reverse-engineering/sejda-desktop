package org.sejda.impl.sambox.component;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import org.sejda.commons.LookupTable;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.exception.TaskPermissionsException;
import org.sejda.model.input.PdfMixInput;
import org.sejda.model.input.PdfSource;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PdfMixFragment.class */
class PdfMixFragment implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(PdfMixFragment.class);
    private PDDocumentHandler handler;
    private PdfMixInput input;
    private LinkedList<Integer> pages;
    private LookupTable<PDPage> lookups = new LookupTable<>();
    private boolean hasNotReachedTheEnd = true;

    private PdfMixFragment(PdfMixInput input, PDDocumentHandler handler) {
        this.handler = handler;
        this.input = input;
        populatePages();
    }

    private void populatePages() {
        this.pages = new LinkedList<>(this.input.getPages(this.handler.getNumberOfPages()));
    }

    private void populatePagesIfRequired() {
        if (this.pages.isEmpty()) {
            this.hasNotReachedTheEnd = false;
            if (this.input.isRepeatForever()) {
                populatePages();
            }
        }
    }

    public PDPage nextPage() {
        PDPage result;
        if (this.input.isReverse()) {
            result = this.handler.getPage(this.pages.removeLast().intValue());
        } else {
            result = this.handler.getPage(this.pages.removeFirst().intValue());
        }
        populatePagesIfRequired();
        return result;
    }

    public boolean hasNextPage() {
        return !this.pages.isEmpty();
    }

    public boolean hasNotReachedTheEnd() {
        return this.hasNotReachedTheEnd;
    }

    public int getNumberOfPages() {
        return this.handler.getNumberOfPages();
    }

    public int getStep() {
        return this.input.getStep();
    }

    public PdfSource<?> source() {
        return this.input.getSource();
    }

    public void addLookupEntry(PDPage current, PDPage importPage) {
        this.lookups.addLookupEntry(current, importPage);
    }

    public void saintizeAnnotations() {
        SignatureClipper.clipSignatures(new AnnotationsDistiller(this.handler.getUnderlyingPDDocument()).retainRelevantAnnotations(this.lookups).values());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.handler.close();
        this.lookups.clear();
    }

    public static PdfMixFragment newInstance(PdfMixInput input, TaskExecutionContext executionContext) throws TaskPermissionsException, TaskIOException {
        LOG.debug("Opening input {} with step {} and reverse {}", new Object[]{input.getSource(), Integer.valueOf(input.getStep()), Boolean.valueOf(input.isReverse())});
        PDDocumentHandler documentHandler = (PDDocumentHandler) input.getSource().open(new DefaultPdfSourceOpener(executionContext));
        documentHandler.getPermissions().ensurePermission(PdfAccessPermission.ASSEMBLE);
        return new PdfMixFragment(input, documentHandler);
    }
}
