package org.sejda.impl.sambox.pro;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.sejda.commons.LookupTable;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.SingleOutputWriter;
import org.sejda.impl.sambox.component.AcroFormsMerger;
import org.sejda.impl.sambox.component.AnnotationsDistiller;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.OutlineMerger;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.PdfRotator;
import org.sejda.impl.sambox.component.SignatureClipper;
import org.sejda.impl.sambox.component.image.ImagesToPdfDocumentConverter;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.FileIndexAndPage;
import org.sejda.model.input.PdfMergeInput;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pro.parameter.CombineReorderParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PageNotFoundException;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/CombineReorderTask.class */
public class CombineReorderTask extends BaseTask<CombineReorderParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(CombineReorderTask.class);
    private SingleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> sourceOpener;
    private PDDocumentHandler destinationDocument;
    private List<DocumentHolder> documents;
    private Map<PDDocumentHandler, LookupTable<PDPage>> pagesLookup = new HashMap();
    private OutlineMerger outlineMerger;

    public void before(CombineReorderParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before(parameters, executionContext);
        this.sourceOpener = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newSingleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
        this.outlineMerger = new OutlineMerger(parameters.getOutlinePolicy());
    }

    public void execute(CombineReorderParameters parameters) throws TaskException {
        File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
        this.outputWriter.taskOutput(tmpFile);
        LOG.debug("Temporary output set to {}", tmpFile);
        this.destinationDocument = new PDDocumentHandler();
        this.destinationDocument.setCreatorOnPDDocument();
        this.destinationDocument.setVersionOnPDDocument(parameters.getVersion());
        this.destinationDocument.setCompress(parameters.isCompress());
        AcroFormsMerger acroFormsMerger = new AcroFormsMerger(parameters.getAcroFormPolicy(), this.destinationDocument.getUnderlyingPDDocument());
        ImagesToPdfDocumentConverter.convertImageMergeInputToPdf(parameters, executionContext());
        int totalPages = parameters.getPages().size();
        int totalInputs = parameters.getPdfInputList().size();
        this.documents = Arrays.asList(new DocumentHolder[totalInputs]);
        int currentStep = 0;
        int totalSteps = totalPages + totalInputs;
        PDPage lastPage = null;
        LookupTable<PDPage> currentIndexLookup = new LookupTable<>();
        DocumentHolder lastHolder = null;
        for (int j = 0; j < totalPages; j++) {
            FileIndexAndPage filePage = (FileIndexAndPage) parameters.getPages().get(j);
            int fileIndex = filePage.getFileIndex();
            if (fileIndex >= 0 && this.documents.get(fileIndex) == null) {
                PdfSource<?> input = ((PdfMergeInput) parameters.getPdfInputList().get(fileIndex)).getSource();
                LOG.debug("Opening {}", input);
                executionContext().notifiableTaskMetadata().setCurrentSource(input);
                PDDocumentHandler sourceDocumentHandler = (PDDocumentHandler) input.open(this.sourceOpener);
                this.documents.set(fileIndex, new DocumentHolder(sourceDocumentHandler, input));
                this.pagesLookup.put(sourceDocumentHandler, new LookupTable<>());
            }
            if (filePage.isAddBlankPage()) {
                this.destinationDocument.addBlankPage((PDRectangle) Optional.ofNullable(lastPage).map((v0) -> {
                    return v0.getMediaBox();
                }).orElse(PDRectangle.A4));
            } else {
                try {
                    DocumentHolder documentHolder = this.documents.get(fileIndex);
                    executionContext().notifiableTaskMetadata().setCurrentSource(documentHolder.source);
                    PDPage page = documentHolder.handler.getPage(filePage.getPage());
                    PDPage newPage = this.destinationDocument.importPage(page);
                    PdfRotator.rotate(newPage, filePage.getRotation());
                    lastPage = newPage;
                    this.pagesLookup.get(documentHolder.handler).addLookupEntry(page, newPage);
                    if (!documentHolder.equals(lastHolder)) {
                        updateOutline(lastHolder, currentIndexLookup);
                        currentIndexLookup = new LookupTable<>();
                        lastHolder = documentHolder;
                    }
                    currentIndexLookup.addLookupEntry(page, newPage);
                } catch (PageNotFoundException e) {
                    executionContext().assertTaskIsLenient(e);
                    ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Page %d was skipped, could not be processed", Integer.valueOf(filePage.getPage())), e);
                }
            }
            currentStep++;
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(totalSteps);
        }
        updateOutline(lastHolder, currentIndexLookup);
        for (DocumentHolder holder : this.documents) {
            if (holder != null) {
                LookupTable<PDAnnotation> annotationsLookup = new AnnotationsDistiller(holder.handler.getUnderlyingPDDocument()).retainRelevantAnnotations(this.pagesLookup.get(holder.handler));
                SignatureClipper.clipSignatures(annotationsLookup.values());
                acroFormsMerger.mergeForm(holder.handler.getUnderlyingPDDocument().getDocumentCatalog().getAcroForm(), annotationsLookup);
            }
            currentStep++;
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(totalSteps);
        }
        if (this.outlineMerger.hasOutline()) {
            LOG.debug("Adding generated outline");
            this.destinationDocument.setDocumentOutline(this.outlineMerger.getOutline());
        }
        Optional.ofNullable(acroFormsMerger.getForm()).filter(f -> {
            return !f.getFields().isEmpty();
        }).ifPresent(f2 -> {
            LOG.debug("Adding generated AcroForm");
            this.destinationDocument.setDocumentAcroForm(f2);
        });
        this.destinationDocument.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
        closeResources();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input documents merged correctly and written to {}", parameters.getOutput());
    }

    private void updateOutline(DocumentHolder holder, LookupTable<PDPage> lookup) {
        if (Objects.nonNull(holder)) {
            this.outlineMerger.updateOutline(holder.handler.getUnderlyingPDDocument(), holder.source.getName(), lookup);
        }
    }

    /* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/CombineReorderTask$DocumentHolder.class */
    private static class DocumentHolder {
        PDDocumentHandler handler;
        PdfSource<?> source;

        public DocumentHolder(PDDocumentHandler handler, PdfSource<?> source) {
            this.handler = handler;
            this.source = source;
        }
    }

    public void after() {
        closeResources();
        this.outputWriter = null;
        this.documents = null;
        this.pagesLookup = null;
    }

    private void closeResources() {
        for (DocumentHolder holder : this.documents) {
            if (holder != null) {
                org.sejda.commons.util.IOUtils.closeQuietly(holder.handler);
            }
        }
        org.sejda.commons.util.IOUtils.closeQuietly(this.destinationDocument);
    }
}
