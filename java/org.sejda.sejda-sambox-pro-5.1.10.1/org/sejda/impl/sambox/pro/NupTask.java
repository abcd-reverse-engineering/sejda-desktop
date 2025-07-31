package org.sejda.impl.sambox.pro;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.sejda.commons.LookupTable;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.AnnotationsDistiller;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.pro.component.PageToFormXObject;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pro.nup.PageOrder;
import org.sejda.model.pro.parameter.NupParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/NupTask.class */
public class NupTask extends BaseTask<NupParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(NupTask.class);
    private PDDocumentHandler sourceDocumentHandler = null;
    private PDDocumentHandler destinationDocument = null;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    public void before(NupParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before(parameters, executionContext);
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.sejda.model.exception.TaskException */
    public void execute(NupParameters parameters) throws TaskException {
        PDRectangle pDRectangle;
        int totalSteps = parameters.getSourceList().size();
        for (PdfSource<?> source : parameters.getSourceList()) {
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.sourceDocumentHandler = (PDDocumentHandler) source.open(this.documentLoader);
            File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
            LOG.debug("Created output on temporary buffer {}", tmpFile);
            this.destinationDocument = new PDDocumentHandler();
            this.destinationDocument.setVersionOnPDDocument(parameters.getVersion());
            this.destinationDocument.initialiseBasedOn(this.sourceDocumentHandler.getUnderlyingPDDocument());
            this.destinationDocument.setCompress(parameters.isCompress());
            LookupTable<PDPage> pagesLookup = new LookupTable<>();
            int numberOfPages = this.sourceDocumentHandler.getNumberOfPages();
            int documentRotation = this.sourceDocumentHandler.getPage(1).getRotation();
            int i = 1;
            while (true) {
                if (i > numberOfPages) {
                    break;
                }
                int pageRotation = this.sourceDocumentHandler.getPage(i).getRotation();
                if (pageRotation == documentRotation) {
                    i++;
                } else {
                    documentRotation = 0;
                    break;
                }
            }
            if (documentRotation != 0) {
                LOG.debug("Document rotation is " + documentRotation);
            }
            int n = parameters.getN();
            int pow = (int) (Math.log(n) / Math.log(2.0d));
            PDRectangle pageSize = this.sourceDocumentHandler.getPage(1).getMediaBox();
            if (documentRotation == 90 || documentRotation == 270) {
                pageSize = pageSize.rotate(documentRotation);
            }
            PDRectangle newSize = new PDRectangle(pageSize.getWidth(), pageSize.getHeight());
            Map<PDPage, Offsets> offsetsMap = new HashMap<>();
            int columns = 1;
            int rows = 1;
            for (int i2 = 0; i2 < pow; i2++) {
                boolean landscape = newSize.getWidth() > newSize.getHeight();
                if (landscape) {
                    rows *= 2;
                    pDRectangle = new PDRectangle(newSize.getWidth(), newSize.getHeight() * 2.0f);
                } else {
                    columns *= 2;
                    pDRectangle = new PDRectangle(newSize.getWidth() * 2.0f, newSize.getHeight());
                }
                newSize = pDRectangle;
                LOG.debug(String.format("Landscape? %s, cols: %s, rows: %s, size: %s x %s", Boolean.valueOf(landscape), Integer.valueOf(columns), Integer.valueOf(rows), Float.valueOf(newSize.getWidth()), Float.valueOf(newSize.getHeight())));
            }
            if (parameters.isPreservePageSize()) {
                boolean landscape2 = newSize.getWidth() > newSize.getHeight();
                newSize = new PDRectangle(pageSize.getWidth(), pageSize.getHeight());
                boolean originalLandscape = pageSize.getWidth() > pageSize.getHeight();
                if (landscape2 && !originalLandscape) {
                    newSize = newSize.rotate(90);
                }
            }
            try {
                int currentRow = 0;
                int currentColumn = 0;
                PDPage currentPage = this.destinationDocument.addBlankPage(newSize);
                LOG.debug("Original page size: " + pageSize.getWidth() + "x" + pageSize.getHeight() + ", new page size: " + newSize.getWidth() + "x" + newSize.getHeight() + ", columns: " + columns + " rows: " + rows);
                List<Integer> pageNumbers = new ArrayList<>(numberOfPages);
                for (int i3 = 1; i3 <= numberOfPages; i3++) {
                    pageNumbers.add(Integer.valueOf(i3));
                }
                if (parameters.isRightToLeft()) {
                    for (int i4 = 0; i4 < pageNumbers.size(); i4 += 2) {
                        if (i4 + 1 < pageNumbers.size()) {
                            int pageNum1 = pageNumbers.get(i4).intValue();
                            int pageNum2 = pageNumbers.get(i4 + 1).intValue();
                            pageNumbers.set(i4, Integer.valueOf(pageNum2));
                            pageNumbers.set(i4 + 1, Integer.valueOf(pageNum1));
                        }
                    }
                }
                int j = 0;
                while (j < pageNumbers.size()) {
                    int pageNum = pageNumbers.get(j).intValue();
                    boolean lastPage = j == pageNumbers.size() - 1;
                    PDPage sourcePage = this.sourceDocumentHandler.getPage(pageNum);
                    PDFormXObject pageAsFormObject = new PageToFormXObject().apply(sourcePage);
                    float xOffset = pageSize.getWidth() * currentColumn;
                    float yOffset = newSize.getHeight() - (pageSize.getHeight() * (currentRow + 1));
                    float xScale = 1.0f;
                    PDRectangle mediaBox = sourcePage.getMediaBox();
                    PDRectangle boundingBox = (PDRectangle) Optional.ofNullable(sourcePage.getCropBox()).orElse(mediaBox);
                    float xOffset2 = xOffset + ((boundingBox.getLowerLeftX() - mediaBox.getLowerLeftX()) - (boundingBox.getUpperRightX() - mediaBox.getUpperRightX()));
                    float yOffset2 = yOffset + ((boundingBox.getLowerLeftY() - mediaBox.getLowerLeftY()) - (boundingBox.getUpperRightY() - mediaBox.getUpperRightY()));
                    if (parameters.isPreservePageSize()) {
                        xOffset2 = (newSize.getWidth() / columns) * currentColumn;
                        yOffset2 = newSize.getHeight() - ((newSize.getHeight() / rows) * (currentRow + 1));
                        xScale = (newSize.getWidth() / columns) / pageSize.getWidth();
                    }
                    LOG.debug("Column: " + currentColumn + ", row: " + currentRow + ", xOffset: " + xOffset2 + " yOffset: " + yOffset2 + " xScale: " + xScale);
                    offsetsMap.put(sourcePage, new Offsets(xOffset2, yOffset2, xScale));
                    if (pageAsFormObject != null) {
                        PDPageContentStream currentContentStream = new PDPageContentStream(this.destinationDocument.getUnderlyingPDDocument(), currentPage, PDPageContentStream.AppendMode.APPEND, true, true);
                        AffineTransform at = new AffineTransform();
                        at.translate(xOffset2, yOffset2);
                        at.scale(xScale, xScale);
                        currentContentStream.transform(new Matrix(at));
                        currentContentStream.drawForm(pageAsFormObject);
                        currentContentStream.close();
                        pagesLookup.addLookupEntry(sourcePage, currentPage);
                    }
                    if (parameters.getPageOrder() == PageOrder.HORIZONTAL) {
                        currentColumn++;
                        if (currentColumn >= columns) {
                            currentColumn = 0;
                            currentRow++;
                        }
                        if (currentRow >= rows && !lastPage) {
                            currentRow = 0;
                            currentColumn = 0;
                            currentPage = this.destinationDocument.addBlankPage(newSize);
                        }
                    } else if (parameters.getPageOrder() == PageOrder.VERTICAL) {
                        currentRow++;
                        if (currentRow >= rows) {
                            currentRow = 0;
                            currentColumn++;
                        }
                        if (currentColumn >= columns && !lastPage) {
                            currentColumn = 0;
                            currentRow = 0;
                            currentPage = this.destinationDocument.addBlankPage(newSize);
                        }
                    }
                    j++;
                }
                LookupTable<PDAnnotation> oldToNewAnnotations = new AnnotationsDistiller(this.sourceDocumentHandler.getUnderlyingPDDocument()).retainRelevantAnnotations(pagesLookup);
                LOG.debug("Copying over {} annotations to the new doc", Integer.valueOf(oldToNewAnnotations.values().size()));
                for (int i5 = 1; i5 <= this.sourceDocumentHandler.getNumberOfPages(); i5++) {
                    PDPage oldPage = this.sourceDocumentHandler.getPage(i5);
                    List<PDAnnotation> oldPageAnnotations = oldPage.getAnnotations();
                    for (PDAnnotation oldAnnotation : oldPageAnnotations) {
                        PDAnnotation newAnnotation = (PDAnnotation) oldToNewAnnotations.lookup(oldAnnotation);
                        PDPage newPage = (PDPage) pagesLookup.lookup(oldPage);
                        if (newPage != null && newAnnotation != null) {
                            Offsets offsets = offsetsMap.get(oldPage);
                            PDRectangle oldRectangle = newAnnotation.getRectangle();
                            if (oldRectangle != null) {
                                AffineTransform at2 = new AffineTransform();
                                at2.translate(offsets.xOffset, offsets.yOffset);
                                at2.scale(offsets.xScale, offsets.xScale);
                                Matrix matrix = new Matrix(at2);
                                float x1 = oldRectangle.getLowerLeftX();
                                float y1 = oldRectangle.getLowerLeftY();
                                float x2 = oldRectangle.getUpperRightX();
                                float y2 = oldRectangle.getUpperRightY();
                                Point2D.Float p0 = matrix.transformPoint(x1, y1);
                                Point2D.Float p1 = matrix.transformPoint(x2, y1);
                                Point2D.Float p2 = matrix.transformPoint(x2, y2);
                                float width = (float) (p1.getX() - p0.getX());
                                float height = (float) (p2.getY() - p1.getY());
                                PDRectangle newRect = new PDRectangle((float) p0.getX(), (float) p0.getY(), width, height);
                                newAnnotation.setRectangle(newRect);
                                LOG.debug("Copying over annotation to page {}", Integer.valueOf(this.destinationDocument.getPages().indexOf(newPage)));
                                newPage.getAnnotations().add(newAnnotation);
                            }
                        }
                    }
                }
                this.destinationDocument.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
                org.sejda.commons.util.IOUtils.closeQuietly(this.sourceDocumentHandler);
                String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                    return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(fileNumber));
                });
                this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(executionContext().outputDocumentsCounter()).outOf(totalSteps);
            } catch (IOException e) {
                throw new TaskException(e);
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input documents cropped and written to {}", parameters.getOutput());
    }

    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.sourceDocumentHandler);
        org.sejda.commons.util.IOUtils.closeQuietly(this.destinationDocument);
    }

    /* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/NupTask$Offsets.class */
    static final class Offsets extends Record {
        private final float xOffset;
        private final float yOffset;
        private final float xScale;

        Offsets(float xOffset, float yOffset, float xScale) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.xScale = xScale;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Offsets.class), Offsets.class, "xOffset;yOffset;xScale", "FIELD:Lorg/sejda/impl/sambox/pro/NupTask$Offsets;->xOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/NupTask$Offsets;->yOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/NupTask$Offsets;->xScale:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Offsets.class), Offsets.class, "xOffset;yOffset;xScale", "FIELD:Lorg/sejda/impl/sambox/pro/NupTask$Offsets;->xOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/NupTask$Offsets;->yOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/NupTask$Offsets;->xScale:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Offsets.class, Object.class), Offsets.class, "xOffset;yOffset;xScale", "FIELD:Lorg/sejda/impl/sambox/pro/NupTask$Offsets;->xOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/NupTask$Offsets;->yOffset:F", "FIELD:Lorg/sejda/impl/sambox/pro/NupTask$Offsets;->xScale:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public float xOffset() {
            return this.xOffset;
        }

        public float yOffset() {
            return this.yOffset;
        }

        public float xScale() {
            return this.xScale;
        }
    }
}
