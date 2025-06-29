package org.sejda.impl.sambox.pro;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.sejda.commons.LookupTable;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.AcroFormsMerger;
import org.sejda.impl.sambox.component.AnnotationsDistiller;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.OutlineDistiller;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.SignatureClipper;
import org.sejda.impl.sambox.pro.component.crop.AutoCrop;
import org.sejda.model.RectangularBox;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pro.parameter.AutoCropMode;
import org.sejda.model.pro.parameter.CropParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/CropTask.class */
public class CropTask extends BaseTask<CropParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(CropTask.class);
    private PDDocumentHandler sourceDocumentHandler = null;
    private PDDocumentHandler destinationDocument = null;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;
    private AcroFormsMerger acroFormsMerger;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(CropParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((CropTask) parameters, executionContext);
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(CropParameters parameters) throws IllegalStateException, TaskException {
        RectangularBox cropBox;
        int totalSteps = parameters.getSourceList().size();
        int croppedFiles = 0;
        for (PdfSource<?> source : parameters.getSourceList()) {
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            LookupTable<PDPage> pagesLookup = new LookupTable<>();
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.sourceDocumentHandler = (PDDocumentHandler) source.open(this.documentLoader);
            File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
            LOG.debug("Created output on temporary buffer {}", tmpFile);
            this.destinationDocument = new PDDocumentHandler();
            this.destinationDocument.setVersionOnPDDocument(parameters.getVersion());
            this.destinationDocument.initialiseBasedOn(this.sourceDocumentHandler.getUnderlyingPDDocument());
            this.destinationDocument.setCompress(parameters.isCompress());
            this.destinationDocument.getUnderlyingPDDocument().getDocumentCatalog().setOCProperties(this.sourceDocumentHandler.getUnderlyingPDDocument().getDocumentCatalog().getOCProperties());
            this.acroFormsMerger = new AcroFormsMerger(parameters.getAcroFormPolicy(), this.destinationDocument.getUnderlyingPDDocument());
            Set<Integer> excludedPages = parameters.getExcludedPages(this.sourceDocumentHandler.getNumberOfPages());
            RectangularBox automaticCropArea = null;
            Map<Integer, RectangularBox> individualAutomaticCropAreas = null;
            try {
                if (parameters.getAutoCropMode() == AutoCropMode.AUTOMATIC_CONSISTENT) {
                    automaticCropArea = new AutoCrop(this.sourceDocumentHandler).getCropArea();
                } else if (parameters.getAutoCropMode() == AutoCropMode.AUTOMATIC_MAXIMUM) {
                    individualAutomaticCropAreas = new AutoCrop(this.sourceDocumentHandler).getIndividualCropAreas();
                }
                int pageNum = 0;
                int croppedPages = 0;
                Iterator<PDPage> it = this.sourceDocumentHandler.getUnderlyingPDDocument().getPages().iterator();
                while (it.hasNext()) {
                    PDPage page = it.next();
                    pageNum++;
                    Set<RectangularBox> cropRectangularBoxes = parameters.getCropAreas(pageNum);
                    if (automaticCropArea != null) {
                        cropRectangularBoxes = Collections.singleton(automaticCropArea);
                    } else if (individualAutomaticCropAreas != null && (cropBox = individualAutomaticCropAreas.get(Integer.valueOf(pageNum))) != null) {
                        cropRectangularBoxes = Collections.singleton(cropBox);
                    }
                    List<PDRectangle> cropAreas = cropRectangularBoxes.stream().map(r -> {
                        return new PDRectangle(r.getLeft(), r.getBottom(), r.getRight() - r.getLeft(), r.getTop() - r.getBottom());
                    }).toList();
                    LOG.debug("Found {} crop boxes to apply on page {}", Integer.valueOf(cropAreas.size()), Integer.valueOf(pageNum));
                    if (excludedPages.contains(Integer.valueOf(pageNum)) || cropAreas.isEmpty()) {
                        LOG.debug("Not cropping page {}", Integer.valueOf(pageNum));
                        pagesLookup.addLookupEntry(page, this.destinationDocument.importPage(page));
                    } else {
                        Iterator<PDRectangle> it2 = cropAreas.iterator();
                        while (it2.hasNext()) {
                            PDRectangle box = unrotate(page, it2.next());
                            PDPage newPage = this.destinationDocument.importPage(page);
                            pagesLookup.addLookupEntry(page, newPage);
                            PDRectangle cropBox2 = page.getCropBox();
                            PDRectangle newCropBox = new PDRectangle(cropBox2.getLowerLeftX() + box.getLowerLeftX(), cropBox2.getLowerLeftY() + box.getLowerLeftY(), box.getWidth(), box.getHeight());
                            newPage.setCropBox(newCropBox);
                            newPage.setTrimBox(newCropBox);
                            croppedPages++;
                        }
                    }
                }
                if (croppedPages == 0) {
                    String message = "No pages cropped: " + source.getName();
                    if (parameters.getAutoCropMode() != AutoCropMode.NONE) {
                        message = "Could not crop automatically: " + source.getName();
                    }
                    TaskException e = new TaskException(message);
                    executionContext().assertTaskIsLenient(e);
                    ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(e.getMessage(), e);
                } else {
                    croppedFiles++;
                }
                LookupTable<PDAnnotation> annotations = new AnnotationsDistiller(this.sourceDocumentHandler.getUnderlyingPDDocument()).retainRelevantAnnotations(pagesLookup);
                SignatureClipper.clipSignatures(annotations.values());
                try {
                    this.acroFormsMerger.mergeForm(this.sourceDocumentHandler.getUnderlyingPDDocument().getDocumentCatalog().getAcroForm(), annotations);
                } catch (IllegalArgumentException e2) {
                    executionContext().assertTaskIsLenient(e2);
                    ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning("Failed to handle AcroForms", e2);
                }
                Optional.ofNullable(this.acroFormsMerger.getForm()).filter(f -> {
                    return !f.getFields().isEmpty();
                }).ifPresent(f2 -> {
                    LOG.debug("Adding generated AcroForm");
                    this.destinationDocument.setDocumentAcroForm(f2);
                });
                PDDocumentOutline destinationOutline = new PDDocumentOutline();
                new OutlineDistiller(this.sourceDocumentHandler.getUnderlyingPDDocument()).appendRelevantOutlineTo(destinationOutline, pagesLookup);
                this.destinationDocument.getUnderlyingPDDocument().getDocumentCatalog().setDocumentOutline(destinationOutline);
                this.destinationDocument.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
                String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                    return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(fileNumber));
                });
                this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(executionContext().outputDocumentsCounter()).outOf(totalSteps);
                org.sejda.commons.util.IOUtils.closeQuietly(this.sourceDocumentHandler);
                pagesLookup.clear();
            } catch (Throwable err) {
                throw new TaskException("Could not determine automatic crop areas", err);
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        if (croppedFiles == 0) {
            String message2 = "No pages cropped";
            if (parameters.getAutoCropMode() != AutoCropMode.NONE) {
                message2 = "Could not crop automatically";
            }
            throw new TaskException(message2);
        }
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input documents cropped and written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.sourceDocumentHandler);
        org.sejda.commons.util.IOUtils.closeQuietly(this.destinationDocument);
    }

    private PDRectangle unrotate(PDPage page, PDRectangle rotated) {
        if (page.getRotation() == 90) {
            return new PDRectangle(page.getCropBox().getWidth() - rotated.getUpperRightY(), rotated.getLowerLeftX(), rotated.getHeight(), rotated.getWidth());
        }
        if (page.getRotation() == 180) {
            return new PDRectangle(page.getCropBox().getWidth() - rotated.getUpperRightX(), page.getCropBox().getHeight() - rotated.getUpperRightY(), rotated.getWidth(), rotated.getHeight());
        }
        if (page.getRotation() == 270) {
            return new PDRectangle(rotated.getLowerLeftY(), page.getCropBox().getHeight() - rotated.getUpperRightX(), rotated.getHeight(), rotated.getWidth());
        }
        return rotated;
    }
}
