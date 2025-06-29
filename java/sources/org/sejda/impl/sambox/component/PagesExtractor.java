package org.sejda.impl.sambox.component;

import java.io.Closeable;
import java.io.File;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import org.sejda.commons.LookupTable;
import org.sejda.commons.util.IOUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.impl.sambox.component.optimization.NameResourcesDuplicator;
import org.sejda.impl.sambox.component.optimization.ResourceDictionaryCleaner;
import org.sejda.impl.sambox.component.optimization.ResourcesHitter;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.pdf.PdfVersion;
import org.sejda.model.pdf.form.AcroFormPolicy;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PageNotFoundException;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PagesExtractor.class */
public class PagesExtractor implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(PagesExtractor.class);
    private OutlineDistiller outlineMerger;
    private AcroFormsMerger acroFormsMerger;
    private PDDocument origin;
    private PDDocumentHandler destinationDocument;
    private LookupTable<PDPage> pagesLookup = new LookupTable<>();

    public PagesExtractor(PDDocument origin) throws IllegalStateException {
        this.origin = origin;
        init();
    }

    private void init() throws IllegalStateException {
        this.outlineMerger = new OutlineDistiller(this.origin);
        this.destinationDocument = new PDDocumentHandler();
        this.destinationDocument.initialiseBasedOn(this.origin);
        this.acroFormsMerger = new AcroFormsMerger(AcroFormPolicy.MERGE, this.destinationDocument.getUnderlyingPDDocument());
    }

    public void retain(Set<Integer> pages, TaskExecutionContext executionContext) throws TaskExecutionException {
        int currentStep = 0;
        for (Integer page : pages) {
            retain(page.intValue(), executionContext);
            currentStep++;
            ApplicationEventsNotifier.notifyEvent(executionContext.notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(pages.size());
        }
    }

    public void retain(int page, TaskExecutionContext executionContext) throws TaskExecutionException {
        try {
            PDPage existingPage = this.origin.getPage(page - 1);
            this.pagesLookup.addLookupEntry(existingPage, this.destinationDocument.importPage(existingPage));
            LOG.trace("Imported page number {}", Integer.valueOf(page));
        } catch (PageNotFoundException e) {
            executionContext.assertTaskIsLenient(e);
            ApplicationEventsNotifier.notifyEvent(executionContext.notifiableTaskMetadata()).taskWarning(String.format("Page %d was skipped, could not be processed", Integer.valueOf(page)), e);
        }
    }

    public void setVersion(PdfVersion version) throws IllegalStateException {
        this.destinationDocument.setVersionOnPDDocument(version);
    }

    public void setCompress(boolean compress) {
        this.destinationDocument.setCompress(compress);
    }

    public void optimize() {
        LOG.trace("Optimizing document");
        Consumer<PDPage> hitter = new NameResourcesDuplicator().andThen(new ResourcesHitter());
        Collection<PDPage> collectionValues = this.pagesLookup.values();
        Objects.requireNonNull(hitter);
        collectionValues.forEach((v1) -> {
            r1.accept(v1);
        });
        new ResourceDictionaryCleaner().accept(this.destinationDocument.getUnderlyingPDDocument());
    }

    public void save(File file, boolean discardOutline, EncryptionAtRestPolicy encryptionAtRestSecurity) throws IllegalStateException, TaskException {
        if (!discardOutline) {
            createOutline();
        }
        LookupTable<PDAnnotation> annotations = new AnnotationsDistiller(this.origin).retainRelevantAnnotations(this.pagesLookup);
        SignatureClipper.clipSignatures(annotations.values());
        this.acroFormsMerger.mergeForm(this.origin.getDocumentCatalog().getAcroForm(), annotations);
        Optional.ofNullable(this.acroFormsMerger.getForm()).filter(f -> {
            return !f.getFields().isEmpty();
        }).ifPresent(f2 -> {
            LOG.debug("Adding generated AcroForm");
            this.destinationDocument.setDocumentAcroForm(f2);
        });
        this.destinationDocument.savePDDocument(file, encryptionAtRestSecurity);
    }

    private void createOutline() {
        PDDocumentOutline outline = new PDDocumentOutline();
        this.outlineMerger.appendRelevantOutlineTo(outline, this.pagesLookup);
        if (outline.hasChildren()) {
            this.destinationDocument.setDocumentOutline(outline);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        IOUtils.closeQuietly(this.destinationDocument);
        this.pagesLookup.clear();
        this.outlineMerger = null;
    }

    protected PDDocumentHandler destinationDocument() {
        return this.destinationDocument;
    }

    public void reset() throws IllegalStateException {
        close();
        init();
    }
}
