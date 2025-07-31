package org.sejda.impl.sambox.pro;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.sejda.commons.LookupTable;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.AnnotationsDistiller;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.OutlineDistiller;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.SignatureClipper;
import org.sejda.impl.sambox.pro.component.PageToFormXObject;
import org.sejda.impl.sambox.util.RectangleUtils;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.pro.parameter.SplitDownTheMiddleParameters;
import org.sejda.model.pro.split.SplitDownTheMiddleMode;
import org.sejda.model.repaginate.Repagination;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.PageNotFoundException;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/SplitDownTheMiddleTask.class */
public class SplitDownTheMiddleTask extends BaseTask<SplitDownTheMiddleParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(SplitDownTheMiddleTask.class);
    private int totalSteps;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;
    private PDDocumentHandler sourceHandler = null;
    private PDDocumentHandler destinationHandler = null;
    private LookupTable<PDPage> pagesLookup = new LookupTable<>();
    private LookupTable<PDPage> secondPagesLookup = new LookupTable<>();
    private Map<PDPage, PDFormXObject> cache = new HashMap();
    private Map<PDPage, Offsets> offsetsMap = new HashMap();

    public void before(SplitDownTheMiddleParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before(parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    public void execute(SplitDownTheMiddleParameters parameters) throws TaskIOException, TaskException {
        for (PdfSource<?> source : parameters.getSourceList()) {
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.sourceHandler = (PDDocumentHandler) source.open(this.documentLoader);
            this.sourceHandler.getPermissions().ensurePermission(PdfAccessPermission.COPY_AND_EXTRACT);
            this.destinationHandler = new PDDocumentHandler();
            this.destinationHandler.setVersionOnPDDocument(parameters.getVersion());
            this.destinationHandler.initialiseBasedOn(this.sourceHandler.getUnderlyingPDDocument());
            this.destinationHandler.setCompress(parameters.isCompress());
            File tmpFile = IOUtils.createTemporaryBuffer();
            LOG.debug("Created output on temporary buffer {}", tmpFile);
            Set<Integer> excludedPages = parameters.getExcludedPages(this.sourceHandler.getNumberOfPages());
            if (parameters.isAutoDetectExcludedPages()) {
                excludedPages = autoDetectExcludedPages(this.sourceHandler);
                LOG.debug("Auto-detected pages to exclude as: {}", excludedPages.stream().map((v0) -> {
                    return String.valueOf(v0);
                }).collect(Collectors.joining()));
            }
            this.pagesLookup = new LookupTable<>();
            this.secondPagesLookup = new LookupTable<>();
            for (int pageNumber = 1; pageNumber <= this.sourceHandler.getNumberOfPages(); pageNumber++) {
                PDPage page = this.sourceHandler.getPage(pageNumber);
                if (excludedPages.contains(Integer.valueOf(pageNumber))) {
                    LOG.debug("Not splitting down the middle page {}", Integer.valueOf(pageNumber));
                    PDPage newPage = this.destinationHandler.importPage(page);
                    this.pagesLookup.addLookupEntry(page, newPage);
                } else {
                    try {
                        double ratio = parameters.getRatio();
                        if (isLandscape(parameters.getMode(), page)) {
                            if (parameters.isRightToLeft()) {
                                importRightPage(page, ratio);
                                importLeftPage(page, ratio);
                            } else {
                                importLeftPage(page, ratio);
                                importRightPage(page, ratio);
                            }
                        } else {
                            importTopPage(page, ratio);
                            importBottomPage(page, ratio);
                        }
                    } catch (PageNotFoundException e) {
                        executionContext().assertTaskIsLenient(e);
                        ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Page %d was skipped, could not be processed", Integer.valueOf(pageNumber)), e);
                    }
                }
            }
            processAnnotations(this.pagesLookup);
            processAnnotations(this.secondPagesLookup);
            if (parameters.getRepagination() == Repagination.LAST_FIRST) {
                int pages = this.destinationHandler.getNumberOfPages();
                int startStep = (pages / 2) % 2;
                int step = startStep == 0 ? 3 : 1;
                int i = pages - startStep;
                while (i > 0) {
                    this.destinationHandler.movePageToDocumentEnd(i);
                    i -= step;
                    if (step == 1) {
                        step = 3;
                    } else {
                        step = 1;
                    }
                }
            }
            PDDocumentOutline destinationOutline = new PDDocumentOutline();
            new OutlineDistiller(this.sourceHandler.getUnderlyingPDDocument()).appendRelevantOutlineTo(destinationOutline, this.pagesLookup);
            this.destinationHandler.getUnderlyingPDDocument().getDocumentCatalog().setDocumentOutline(destinationOutline);
            this.destinationHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
            String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(fileNumber));
            });
            this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
            closeResources();
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(executionContext().outputDocumentsCounter()).outOf(this.totalSteps);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Text extracted from input documents and written to {}", parameters.getOutput());
    }

    private boolean isLandscape(PDPage p) {
        PDRectangle r = p.getCropBox().rotate(p.getRotation());
        return r.getWidth() > r.getHeight();
    }

    private Set<Integer> autoDetectExcludedPages(PDDocumentHandler sourceHandler) {
        Set<Integer> excludedPages = new HashSet<>();
        if (sourceHandler.getNumberOfPages() > 2) {
            int landscapePagesCount = 0;
            for (int i = 1; i <= sourceHandler.getNumberOfPages(); i++) {
                if (isLandscape(sourceHandler.getPage(i))) {
                    landscapePagesCount++;
                }
            }
            boolean mostPagesAreLandscape = sourceHandler.getNumberOfPages() / 2 < landscapePagesCount;
            for (int i2 = 1; i2 <= sourceHandler.getNumberOfPages(); i2++) {
                if (isLandscape(sourceHandler.getPage(i2)) != mostPagesAreLandscape) {
                    excludedPages.add(Integer.valueOf(i2));
                }
            }
        }
        return excludedPages;
    }

    private boolean isLandscape(SplitDownTheMiddleMode mode, PDPage page) {
        if (mode == SplitDownTheMiddleMode.HORIZONTAL) {
            return false;
        }
        if (mode == SplitDownTheMiddleMode.VERTICAL) {
            return true;
        }
        PDRectangle cropBox = page.getCropBox().rotate(page.getRotation());
        return cropBox.getHeight() <= cropBox.getWidth();
    }

    private PDFormXObject getPageAsFormXObject(PDPage page) throws IOException {
        if (!this.cache.containsKey(page)) {
            this.cache.put(page, new PageToFormXObject().apply(page));
        }
        return this.cache.get(page);
    }

    private void importLeftPage(PDPage page, double ratio) throws TaskIOException {
        PDRectangle cropBox = page.getCropBox().rotate(page.getRotation());
        float w = cropBox.getWidth();
        float r = (float) ratio;
        float rightSideWidth = w / (r + 1.0f);
        float leftSideWidth = w - rightSideWidth;
        importPage(page, leftSideWidth, cropBox.getHeight(), 0.0f, 0.0f);
    }

    private void importRightPage(PDPage page, double ratio) throws TaskIOException {
        PDRectangle cropBox = page.getCropBox().rotate(page.getRotation());
        float w = cropBox.getWidth();
        float r = (float) ratio;
        float rightSideWidth = w / (r + 1.0f);
        float leftSideWidth = w - rightSideWidth;
        importPage(page, rightSideWidth, cropBox.getHeight(), -leftSideWidth, 0.0f);
    }

    private void importTopPage(PDPage page, double ratio) throws TaskIOException {
        PDRectangle cropBox = page.getCropBox().rotate(page.getRotation());
        float h = cropBox.getHeight();
        float r = (float) ratio;
        float bottomSideHeight = h / (r + 1.0f);
        float topSideHeight = h - bottomSideHeight;
        importPage(page, cropBox.getWidth(), topSideHeight, 0.0f, -bottomSideHeight);
    }

    private void importBottomPage(PDPage page, double ratio) throws TaskIOException {
        PDRectangle cropBox = page.getCropBox().rotate(page.getRotation());
        float h = cropBox.getHeight();
        float r = (float) ratio;
        float bottomSideHeight = h / (r + 1.0f);
        importPage(page, cropBox.getWidth(), bottomSideHeight, 0.0f, 0.0f);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.sejda.model.exception.TaskIOException */
    private void importPage(PDPage sourcePage, float width, float height, float xOffset, float yOffset) throws TaskIOException {
        PDRectangle newMediaBox = new PDRectangle(width, height);
        PDRectangle mediaBox = sourcePage.getMediaBox().rotate(sourcePage.getRotation());
        PDRectangle cropBox = sourcePage.getCropBox().rotate(sourcePage.getRotation());
        float cropLeftMargin = cropBox.getLowerLeftX() - mediaBox.getLowerLeftX();
        float cropBottomMargin = cropBox.getLowerLeftY() - mediaBox.getLowerLeftY();
        PDPage newPage = this.destinationHandler.addBlankPage(newMediaBox);
        if (this.pagesLookup.hasLookupFor(sourcePage)) {
            this.secondPagesLookup.addLookupEntry(sourcePage, newPage);
        } else {
            this.pagesLookup.addLookupEntry(sourcePage, newPage);
        }
        this.offsetsMap.put(newPage, new Offsets(xOffset, yOffset, width, height));
        try {
            PDFormXObject pageAsFormObject = getPageAsFormXObject(sourcePage);
            if (pageAsFormObject == null) {
                int pageNumber = this.sourceHandler.getPages().indexOf(sourcePage) + 1;
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Page %d was skipped, could not be processed", Integer.valueOf(pageNumber)));
            }
            PDPageContentStream currentContentStream = new PDPageContentStream(this.destinationHandler.getUnderlyingPDDocument(), newPage, PDPageContentStream.AppendMode.APPEND, true, true);
            AffineTransform at = new AffineTransform();
            at.translate(xOffset + cropLeftMargin, yOffset + cropBottomMargin);
            Matrix matrix = new Matrix(at);
            currentContentStream.transform(matrix);
            currentContentStream.drawForm(pageAsFormObject);
            currentContentStream.close();
        } catch (IOException ex) {
            throw new TaskIOException(ex);
        }
    }

    private void processAnnotations(LookupTable<PDPage> lookup) {
        LookupTable<PDAnnotation> annotations = new AnnotationsDistiller(this.sourceHandler.getUnderlyingPDDocument()).retainRelevantAnnotations(lookup);
        SignatureClipper.clipSignatures(annotations.values());
        for (int i = 1; i <= this.sourceHandler.getNumberOfPages(); i++) {
            PDPage oldPage = this.sourceHandler.getPage(i);
            List<PDAnnotation> oldPageAnnotations = oldPage.getAnnotations();
            for (PDAnnotation oldAnnotation : oldPageAnnotations) {
                PDAnnotation newAnnotation = (PDAnnotation) annotations.lookup(oldAnnotation);
                PDPage newPage = (PDPage) lookup.lookup(oldPage);
                if (newPage != null && newAnnotation != null) {
                    PDRectangle oldMediaBox = oldPage.getMediaBox();
                    PDRectangle oldCropBox = oldPage.getCropBox();
                    PDRectangle rotatedOldMediaBox = oldMediaBox.rotate(oldPage.getRotation());
                    PDRectangle rotatedOldCropBox = oldCropBox.rotate(oldPage.getRotation());
                    float rotatedCropLeftMargin = rotatedOldCropBox.getLowerLeftX() - rotatedOldMediaBox.getLowerLeftX();
                    float rotatedCropBottomMargin = rotatedOldCropBox.getLowerLeftY() - rotatedOldMediaBox.getLowerLeftY();
                    Offsets offsets = this.offsetsMap.get(newPage);
                    if (offsets != null) {
                        PDRectangle newPageBoundsInOldPage = new PDRectangle(-offsets.xOffset, -offsets.yOffset, offsets.newWidth, offsets.newHeight);
                        PDRectangle newPageBoundsInOldPage2 = RectangleUtils.rotate(-oldPage.getRotation(), newPageBoundsInOldPage, rotatedOldMediaBox);
                        PDRectangle oldRectangle = newAnnotation.getRectangle();
                        if (oldRectangle != null) {
                            if (RectangleUtils.intersect(newPageBoundsInOldPage2, oldRectangle)) {
                                PDRectangle mediaBox = oldPage.getMediaBox();
                                PDRectangle boundingBox = (PDRectangle) Optional.ofNullable(oldPage.getCropBox()).orElse(mediaBox);
                                AffineTransform at = new AffineTransform();
                                switch (oldPage.getRotation()) {
                                    case 90:
                                        at.translate(0.0d, boundingBox.getWidth());
                                        at.rotate(-1.5707963267948966d);
                                        break;
                                    case 180:
                                        at.translate(boundingBox.getWidth(), boundingBox.getHeight());
                                        at.rotate(-3.141592653589793d);
                                        break;
                                    case 270:
                                        at.translate(boundingBox.getHeight(), 0.0d);
                                        at.rotate(-4.71238898038469d);
                                        break;
                                }
                                Rectangle2D transformedRect = at.createTransformedShape(oldRectangle.toGeneralPath()).getBounds2D();
                                PDRectangle newRect = new PDRectangle((float) transformedRect.getX(), (float) transformedRect.getY(), (float) transformedRect.getWidth(), (float) transformedRect.getHeight());
                                newAnnotation.setRectangle(RectangleUtils.translate(offsets.xOffset - rotatedCropLeftMargin, offsets.yOffset - rotatedCropBottomMargin, newRect));
                                LOG.debug("Updating annotation {} to page {}", newAnnotation, Integer.valueOf(this.destinationHandler.getPages().indexOf(newPage)));
                                int idx = newPage.getAnnotations().indexOf(newAnnotation);
                                if (idx >= 0) {
                                    newPage.getAnnotations().set(idx, newAnnotation);
                                }
                            } else {
                                LOG.debug("Removing annotation {} to page {}", newAnnotation, Integer.valueOf(this.destinationHandler.getPages().indexOf(newPage)));
                                newPage.getAnnotations().remove(newAnnotation);
                            }
                        }
                    }
                }
            }
        }
    }

    public void after() {
        closeResources();
        this.pagesLookup.clear();
        this.secondPagesLookup.clear();
    }

    private void closeResources() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.sourceHandler);
        org.sejda.commons.util.IOUtils.closeQuietly(this.destinationHandler);
    }

    /* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets.class */
    static final class Offsets extends Record {
        private final float xOffset;
        private final float yOffset;
        private final float newWidth;
        private final float newHeight;

        Offsets(float xOffset, float yOffset, float newWidth, float newHeight) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.newWidth = newWidth;
            this.newHeight = newHeight;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Offsets.class), Offsets.class, "xOffset;yOffset;newWidth;newHeight", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->xOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->yOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->newWidth:F", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->newHeight:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Offsets.class), Offsets.class, "xOffset;yOffset;newWidth;newHeight", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->xOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->yOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->newWidth:F", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->newHeight:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Offsets.class, Object.class), Offsets.class, "xOffset;yOffset;newWidth;newHeight", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->xOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->yOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->newWidth:F", "FIELD:Lorg/sejda/impl/sambox/pro/SplitDownTheMiddleTask$Offsets;->newHeight:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public float xOffset() {
            return this.xOffset;
        }

        public float yOffset() {
            return this.yOffset;
        }

        public float newWidth() {
            return this.newWidth;
        }

        public float newHeight() {
            return this.newHeight;
        }
    }
}
