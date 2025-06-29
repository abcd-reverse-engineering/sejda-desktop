package org.sejda.impl.sambox;

import java.io.Closeable;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.sejda.commons.LookupTable;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.SingleOutputWriter;
import org.sejda.impl.sambox.component.AcroFormsMerger;
import org.sejda.impl.sambox.component.AnnotationsDistiller;
import org.sejda.impl.sambox.component.CatalogPageLabelsMerger;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.FilenameFooterWriter;
import org.sejda.impl.sambox.component.OutlineMerger;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.PdfRotator;
import org.sejda.impl.sambox.component.PdfScaler;
import org.sejda.impl.sambox.component.SignatureClipper;
import org.sejda.impl.sambox.component.TableOfContentsCreator;
import org.sejda.impl.sambox.component.image.ImagesToPdfDocumentConverter;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfMergeInput;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.MergeParameters;
import org.sejda.model.rotation.Rotation;
import org.sejda.model.scale.ScaleType;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.toc.ToCPolicy;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PageNotFoundException;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/MergeTask.class */
public class MergeTask extends BaseTask<MergeParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(MergeTask.class);
    private SingleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> sourceOpener;
    private int totalSteps;
    private PDDocumentHandler destinationDocument;
    private OutlineMerger outlineMerger;
    private CatalogPageLabelsMerger catalogPageLabelsMerger;
    private AcroFormsMerger acroFormsMerger;
    private TableOfContentsCreator tocCreator;
    private FilenameFooterWriter footerWriter;
    private Queue<Closeable> toClose = new LinkedList();
    private PDRectangle currentPageSize = PDRectangle.A4;
    private int pagesCounter = 0;
    private int inputsCounter = 0;
    private int firstInputNumberOfPages = 0;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(MergeParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((MergeTask) parameters, executionContext);
        this.totalSteps = parameters.getInputList().size();
        this.sourceOpener = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newSingleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
        this.outlineMerger = new OutlineMerger(parameters.getOutlinePolicy());
    }

    @Override // org.sejda.model.task.Task
    public void execute(MergeParameters parameters) throws IllegalStateException, TaskException {
        int currentStep = 0;
        File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
        this.outputWriter.taskOutput(tmpFile);
        LOG.debug("Temporary output set to {}", tmpFile);
        this.destinationDocument = new PDDocumentHandler();
        this.destinationDocument.setCreatorOnPDDocument();
        this.destinationDocument.setVersionOnPDDocument(parameters.getVersion());
        this.destinationDocument.setCompress(parameters.isCompress());
        this.acroFormsMerger = new AcroFormsMerger(parameters.getAcroFormPolicy(), this.destinationDocument.getUnderlyingPDDocument());
        this.tocCreator = new TableOfContentsCreator(parameters, this.destinationDocument.getUnderlyingPDDocument());
        this.footerWriter = new FilenameFooterWriter(parameters.isFilenameFooter(), this.destinationDocument.getUnderlyingPDDocument());
        this.catalogPageLabelsMerger = new CatalogPageLabelsMerger(parameters.getCatalogPageLabelsPolicy());
        ImagesToPdfDocumentConverter.convertImageMergeInputToPdf(parameters, executionContext());
        List<FooterWriterEntry> footerWriterEntries = new ArrayList<>();
        for (PdfMergeInput input : parameters.getPdfInputList()) {
            this.inputsCounter++;
            LOG.debug("Opening {}", input.getSource());
            executionContext().notifiableTaskMetadata().setCurrentSource(input.getSource());
            PDDocumentHandler sourceDocumentHandler = (PDDocumentHandler) input.getSource().open(this.sourceOpener);
            this.toClose.add(sourceDocumentHandler);
            if (this.inputsCounter == 1) {
                this.firstInputNumberOfPages = sourceDocumentHandler.getNumberOfPages();
            }
            LOG.debug("Adding pages");
            LookupTable<PDPage> pagesLookup = new LookupTable<>();
            long relativePagesCounter = 0;
            Set<Integer> pagesToImport = input.getPages(sourceDocumentHandler.getNumberOfPages());
            for (Integer currentPage : pagesToImport) {
                this.pagesCounter++;
                relativePagesCounter++;
                try {
                    PDPage page = sourceDocumentHandler.getPage(currentPage.intValue());
                    this.currentPageSize = page.getMediaBox().rotate(page.getRotation());
                    PDPage importedPage = this.destinationDocument.importPage(page);
                    pagesLookup.addLookupEntry(page, importedPage);
                    Rotation rotation = parameters.getRotation(this.inputsCounter - 1);
                    if (rotation != Rotation.DEGREES_0) {
                        PdfRotator.rotate(importedPage, rotation);
                    }
                    String sourceBaseName = FilenameUtils.getBaseName(input.getSource().getName());
                    if (this.tocCreator.shouldGenerateToC() && relativePagesCounter == 1 && (!parameters.isFirstInputCoverTitle() || this.inputsCounter != 1)) {
                        this.tocCreator.pageSizeIfNotSet(this.currentPageSize);
                        String tocText = sourceBaseName;
                        if (ToCPolicy.DOC_TITLES == parameters.getTableOfContentsPolicy()) {
                            tocText = (String) Optional.ofNullable(sourceDocumentHandler.getUnderlyingPDDocument().getDocumentInformation()).map((v0) -> {
                                return v0.getTitle();
                            }).filter((v0) -> {
                                return StringUtils.isNotBlank(v0);
                            }).orElse(sourceBaseName);
                        }
                        if (tocText == null || tocText.isBlank()) {
                            tocText = "Untitled " + this.inputsCounter;
                        }
                        this.tocCreator.appendItem(tocText, this.pagesCounter, importedPage);
                    }
                    boolean isPlacedAfterToc = (parameters.isFirstInputCoverTitle() && this.inputsCounter == 1) ? false : true;
                    footerWriterEntries.add(new FooterWriterEntry(importedPage, sourceBaseName, this.pagesCounter, isPlacedAfterToc));
                    LOG.trace("Added imported page");
                } catch (PageNotFoundException e) {
                    executionContext().assertTaskIsLenient(e);
                    ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Page %d was skipped, could not be processed", currentPage), e);
                }
            }
            this.outlineMerger.updateOutline(sourceDocumentHandler.getUnderlyingPDDocument(), input.getSource().getName(), pagesLookup);
            LookupTable<PDAnnotation> annotationsLookup = new AnnotationsDistiller(sourceDocumentHandler.getUnderlyingPDDocument()).retainRelevantAnnotations(pagesLookup);
            SignatureClipper.clipSignatures(annotationsLookup.values());
            this.acroFormsMerger.mergeForm(sourceDocumentHandler.getUnderlyingPDDocument().getDocumentCatalog().getAcroForm(), annotationsLookup);
            if (parameters.isBlankPageIfOdd()) {
                Optional.ofNullable(this.destinationDocument.addBlankPageIfOdd(this.currentPageSize)).ifPresent(p -> {
                    this.pagesCounter++;
                });
            }
            this.catalogPageLabelsMerger.add(sourceDocumentHandler.getUnderlyingPDDocument(), pagesToImport);
            currentStep++;
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(this.totalSteps);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        if (this.outlineMerger.hasOutline()) {
            LOG.debug("Adding generated outline");
            this.destinationDocument.setDocumentOutline(this.outlineMerger.getOutline());
        }
        Optional.ofNullable(this.acroFormsMerger.getForm()).filter(f -> {
            return !f.getFields().isEmpty();
        }).ifPresent(f2 -> {
            LOG.debug("Adding generated AcroForm");
            this.destinationDocument.setDocumentAcroForm(f2);
        });
        new PdfScaler(ScaleType.PAGE).scalePages(this.destinationDocument.getUnderlyingPDDocument(), parameters.getPageNormalizationPolicy());
        int tocNumberOfPages = 0;
        if (this.tocCreator.hasToc()) {
            LOG.debug("Adding generated ToC");
            try {
                int beforePageNumber = parameters.isFirstInputCoverTitle() ? this.firstInputNumberOfPages : 0;
                tocNumberOfPages = this.tocCreator.addToC(beforePageNumber);
            } catch (TaskException e2) {
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning("Unable to create the Table of Contents", e2);
            }
        }
        LOG.debug("Writing page footers");
        for (FooterWriterEntry entry : footerWriterEntries) {
            long finalPageNumber = entry.isPlacedAfterToc ? entry.pageNumber + tocNumberOfPages : entry.pageNumber;
            this.footerWriter.addFooter(entry.page, entry.fileName, finalPageNumber);
        }
        if (this.catalogPageLabelsMerger.hasPageLabels()) {
            LOG.debug("Adding merged /Catalog /PageLabels");
            this.destinationDocument.getUnderlyingPDDocument().getDocumentCatalog().setPageLabels(this.catalogPageLabelsMerger.getMergedPageLabels());
        }
        this.destinationDocument.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
        closeResources();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input documents merged correctly and written to {}", parameters.getOutput());
    }

    private void closeResources() {
        while (true) {
            Closeable current = this.toClose.poll();
            if (current != null) {
                org.sejda.commons.util.IOUtils.closeQuietly(current);
            } else {
                org.sejda.commons.util.IOUtils.closeQuietly(this.destinationDocument);
                return;
            }
        }
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        closeResources();
        this.outputWriter = null;
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/MergeTask$FooterWriterEntry.class */
    private static final class FooterWriterEntry extends Record {
        private final PDPage page;
        private final String fileName;
        private final long pageNumber;
        private final boolean isPlacedAfterToc;

        private FooterWriterEntry(PDPage page, String fileName, long pageNumber, boolean isPlacedAfterToc) {
            this.page = page;
            this.fileName = fileName;
            this.pageNumber = pageNumber;
            this.isPlacedAfterToc = isPlacedAfterToc;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FooterWriterEntry.class), FooterWriterEntry.class, "page;fileName;pageNumber;isPlacedAfterToc", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->page:Lorg/sejda/sambox/pdmodel/PDPage;", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->fileName:Ljava/lang/String;", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->pageNumber:J", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->isPlacedAfterToc:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FooterWriterEntry.class), FooterWriterEntry.class, "page;fileName;pageNumber;isPlacedAfterToc", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->page:Lorg/sejda/sambox/pdmodel/PDPage;", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->fileName:Ljava/lang/String;", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->pageNumber:J", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->isPlacedAfterToc:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FooterWriterEntry.class, Object.class), FooterWriterEntry.class, "page;fileName;pageNumber;isPlacedAfterToc", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->page:Lorg/sejda/sambox/pdmodel/PDPage;", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->fileName:Ljava/lang/String;", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->pageNumber:J", "FIELD:Lorg/sejda/impl/sambox/MergeTask$FooterWriterEntry;->isPlacedAfterToc:Z").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public PDPage page() {
            return this.page;
        }

        public String fileName() {
            return this.fileName;
        }

        public long pageNumber() {
            return this.pageNumber;
        }

        public boolean isPlacedAfterToc() {
            return this.isPlacedAfterToc;
        }
    }
}
