package org.sejda.impl.sambox.component;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.sejda.commons.util.RequireUtils;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.scale.Margins;
import org.sejda.model.scale.PageNormalizationPolicy;
import org.sejda.model.scale.ScaleType;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLine;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PdfScaler.class */
public class PdfScaler {
    private static final Logger LOG = LoggerFactory.getLogger(PdfScaler.class);
    private final ScaleType type;

    public PdfScaler(ScaleType type) {
        RequireUtils.requireNotNullArg(type, "Scale type cannot be null");
        this.type = type;
    }

    @Deprecated
    public void scalePages(PDDocument doc) throws TaskIOException {
        scalePages(doc, PageNormalizationPolicy.SAME_WIDTH_ORIENTATION_BASED);
    }

    public void scalePages(PDDocument doc, Iterable<PDPage> pages, PDRectangle targetBox) throws TaskIOException {
        scalePages(doc, pages, targetBox, PageNormalizationPolicy.SAME_WIDTH_ORIENTATION_BASED);
    }

    public void scalePages(PDDocument doc, PageNormalizationPolicy pageNormalization) throws TaskIOException {
        if (PageNormalizationPolicy.NONE != pageNormalization) {
            PDPage firstPage = doc.getPage(0);
            PDRectangle targetBox = firstPage.getCropBox().rotate(firstPage.getRotation());
            scalePages(doc, doc.getPages(), targetBox, pageNormalization);
        }
    }

    public void scalePages(PDDocument doc, Iterable<PDPage> pages, PDRectangle targetBox, PageNormalizationPolicy pageNormalization) throws TaskIOException {
        if (PageNormalizationPolicy.NONE != pageNormalization) {
            LOG.debug("Normalizing pages with policy {}", pageNormalization);
            for (PDPage page : pages) {
                PDRectangle cropBox = page.getCropBox().rotate(page.getRotation());
                double scale = getScalingFactor(targetBox, cropBox, pageNormalization);
                LOG.debug("Scaling page from {} to {}, factor of {}", new Object[]{cropBox, targetBox, Double.valueOf(scale)});
                scale(doc, page, scale);
            }
        }
    }

    public void scale(PDDocument doc, double scale) throws TaskIOException {
        scale(doc, doc.getPages(), scale);
    }

    public void scale(PDDocument doc, PDPage page, double scale) throws TaskIOException {
        scale(doc, Collections.singletonList(page), scale);
    }

    public void scale(PDDocument doc, Iterable<PDPage> pages, double scale) throws TaskIOException {
        if (scale != 1.0d) {
            doScale(doc, pages, scale);
        }
    }

    private void doScale(PDDocument doc, Iterable<PDPage> pages, double scale) throws TaskIOException {
        Set<COSDictionary> processedAnnots = new HashSet<>();
        Map<PDPage, Set<PDPageDestination>> groupedOutline = OutlineUtils.pageGroupedOutlinePageDestinations(doc);
        for (PDPage page : pages) {
            try {
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.PREPEND, true);
                try {
                    Matrix matrix = getMatrix(scale, page.getCropBox(), page.getCropBox());
                    contentStream.transform(matrix);
                    if (ScaleType.PAGE == this.type) {
                        scalePageBoxes(scale, page);
                    } else {
                        scaleContentBoxes(scale, page);
                    }
                    transformAnnotations(page, matrix, processedAnnots, doc);
                    Optional.ofNullable(groupedOutline.get(page)).ifPresent(g -> {
                        g.forEach(d -> {
                            d.transform(matrix);
                        });
                    });
                    contentStream.close();
                } finally {
                }
            } catch (IOException e) {
                throw new TaskIOException("An error occurred writing scaling the page.", e);
            }
        }
    }

    private static void transformAnnotations(PDPage page, Matrix transform, Set<COSDictionary> processedAnnots, PDDocument doc) {
        page.getAnnotations().stream().filter(a -> {
            return !processedAnnots.contains(a.getCOSObject());
        }).forEach(a2 -> {
            Optional map = Optional.ofNullable(a2.getRectangle()).map(r -> {
                return r.transform(transform).getBounds2D();
            }).map(PDRectangle::new);
            Objects.requireNonNull(a2);
            map.ifPresent(a2::setRectangle);
            Optional.ofNullable((COSArray) a2.getCOSObject().getDictionaryObject(COSName.QUADPOINTS, COSArray.class)).filter(p -> {
                return p.size() == 8;
            }).map((v0) -> {
                return v0.toFloatArray();
            }).ifPresent(f -> {
                a2.getCOSObject().setItem(COSName.QUADPOINTS, (COSBase) transformPoints(f, transform));
            });
            if (a2 instanceof PDAnnotationLink) {
                try {
                    ((PDAnnotationLink) a2).resolveToPageDestination(doc.getDocumentCatalog()).ifPresent(d -> {
                        d.transform(transform);
                    });
                } catch (IOException e) {
                    LOG.warn("Unable to process link annotation destination", e);
                }
            }
            if (a2 instanceof PDAnnotationLine) {
                Optional.ofNullable(((PDAnnotationLine) a2).getLine()).filter(p2 -> {
                    return p2.length == 4;
                }).ifPresent(f2 -> {
                    a2.getCOSObject().setItem(COSName.L, (COSBase) transformPoints(f2, transform));
                });
            }
            Optional.ofNullable((COSArray) a2.getCOSObject().getDictionaryObject(COSName.CL, COSArray.class)).filter(p3 -> {
                return p3.size() % 2 == 0;
            }).map((v0) -> {
                return v0.toFloatArray();
            }).ifPresent(f3 -> {
                a2.getCOSObject().setItem(COSName.CL, (COSBase) transformPoints(f3, transform));
            });
            Optional.ofNullable((COSArray) a2.getCOSObject().getDictionaryObject(COSName.VERTICES, COSArray.class)).filter(p4 -> {
                return p4.size() % 2 == 0;
            }).map((v0) -> {
                return v0.toFloatArray();
            }).ifPresent(f4 -> {
                a2.getCOSObject().setItem(COSName.VERTICES, (COSBase) transformPoints(f4, transform));
            });
            processedAnnots.add(a2.getCOSObject());
        });
    }

    private static COSArray transformPoints(float[] points, Matrix transform) {
        COSArray newPoints = new COSArray();
        int i = 0;
        while (i < points.length) {
            float f = points[i];
            int i2 = i + 1;
            Point2D.Float newPoint = transform.transformPoint(f, points[i2]);
            newPoints.add((COSBase) new COSFloat(newPoint.x));
            newPoints.add((COSBase) new COSFloat(newPoint.y));
            i = i2 + 1;
        }
        return newPoints;
    }

    public static void margin(PDDocument doc, Iterable<PDPage> pages, Margins margins) throws TaskIOException {
        if (Objects.nonNull(margins)) {
            Map<PDPage, Set<PDPageDestination>> groupedOutline = OutlineUtils.pageGroupedOutlinePageDestinations(doc);
            Set<COSDictionary> processedAnnots = new HashSet<>();
            for (PDPage page : pages) {
                try {
                    PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.PREPEND, true);
                    try {
                        page.setCropBox(addMargins(page.getCropBox().rotate(page.getRotation()), margins).rotate(-page.getRotation()));
                        page.setMediaBox(addMargins(page.getMediaBox().rotate(page.getRotation()), margins).rotate(-page.getRotation()));
                        Optional.ofNullable(page.getBleedBoxRaw()).ifPresent(r -> {
                            page.setBleedBox(addMargins(r.rotate(page.getRotation()), margins).rotate(-page.getRotation()));
                        });
                        Optional.ofNullable(page.getTrimBoxRaw()).ifPresent(r2 -> {
                            page.setTrimBox(addMargins(r2.rotate(page.getRotation()), margins).rotate(-page.getRotation()));
                        });
                        Optional.ofNullable(page.getArtBoxRaw()).ifPresent(r3 -> {
                            page.setArtBox(addMargins(r3.rotate(page.getRotation()), margins).rotate(-page.getRotation()));
                        });
                        Matrix matrix = new Matrix(AffineTransform.getTranslateInstance(Margins.inchesToPoints(margins.left), Margins.inchesToPoints(margins.bottom)));
                        contentStream.transform(matrix);
                        transformAnnotations(page, matrix, processedAnnots, doc);
                        Optional.ofNullable(groupedOutline.get(page)).ifPresent(g -> {
                            g.forEach(d -> {
                                d.transform(matrix);
                            });
                        });
                        contentStream.close();
                    } catch (Throwable th) {
                        try {
                            contentStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (IOException e) {
                    throw new TaskIOException("An error occurred adding margins to the page.", e);
                }
            }
        }
    }

    private static PDRectangle addMargins(PDRectangle rect, Margins margins) {
        return new PDRectangle(rect.getLowerLeftX(), rect.getLowerLeftY(), (float) (rect.getWidth() + Margins.inchesToPoints(margins.left) + Margins.inchesToPoints(margins.right)), (float) (rect.getHeight() + Margins.inchesToPoints(margins.top) + Margins.inchesToPoints(margins.bottom)));
    }

    private Matrix getMatrix(double scale, PDRectangle crop, PDRectangle toScale) {
        if (ScaleType.CONTENT == this.type) {
            AffineTransform transform = AffineTransform.getTranslateInstance((crop.getWidth() - (toScale.getWidth() * scale)) / 2.0d, (crop.getHeight() - (toScale.getHeight() * scale)) / 2.0d);
            transform.scale(scale, scale);
            return new Matrix(transform);
        }
        return new Matrix(AffineTransform.getScaleInstance(scale, scale));
    }

    private void scaleContentBoxes(double scale, PDPage page) {
        PDRectangle cropBox = page.getCropBox();
        if (scale > 1.0d) {
            page.setBleedBox(cropBox);
            page.setTrimBox(cropBox);
        } else {
            page.setBleedBox(new PDRectangle(page.getBleedBox().transform(getMatrix(scale, page.getCropBox(), page.getBleedBox())).getBounds2D()));
            page.setTrimBox(new PDRectangle(page.getTrimBox().transform(getMatrix(scale, page.getCropBox(), page.getTrimBox())).getBounds2D()));
        }
        Rectangle2D newArt = page.getArtBox().transform(getMatrix(scale, page.getCropBox(), page.getArtBox())).getBounds2D();
        if (newArt.getX() < cropBox.getLowerLeftX() || newArt.getY() < cropBox.getLowerLeftX()) {
            page.setArtBox(page.getCropBox());
        } else {
            page.setArtBox(new PDRectangle(newArt));
        }
    }

    private void scalePageBoxes(double scale, PDPage page) {
        page.setArtBox(new PDRectangle(page.getArtBox().transform(getMatrix(scale, page.getCropBox(), page.getArtBox())).getBounds2D()));
        page.setBleedBox(new PDRectangle(page.getBleedBox().transform(getMatrix(scale, page.getCropBox(), page.getBleedBox())).getBounds2D()));
        page.setTrimBox(new PDRectangle(page.getTrimBox().transform(getMatrix(scale, page.getCropBox(), page.getTrimBox())).getBounds2D()));
        page.setCropBox(new PDRectangle(page.getCropBox().transform(getMatrix(scale, page.getCropBox(), page.getCropBox())).getBounds2D()));
        page.setMediaBox(new PDRectangle(page.getMediaBox().transform(getMatrix(scale, page.getMediaBox(), page.getMediaBox())).getBounds2D()));
    }

    private double getScalingFactor(PDRectangle targetBox, PDRectangle pageBox, PageNormalizationPolicy pageNormalization) {
        switch (pageNormalization) {
            case NONE:
                return 1.0d;
            case SAME_WIDTH:
                return targetBox.getWidth() / pageBox.getWidth();
            case SAME_WIDTH_ORIENTATION_BASED:
                if (isLandscape(targetBox) == isLandscape(pageBox)) {
                    return targetBox.getWidth() / pageBox.getWidth();
                }
                return targetBox.getHeight() / pageBox.getWidth();
            default:
                throw new IncompatibleClassChangeError();
        }
    }

    public static boolean isLandscape(PDRectangle box) {
        return box.getWidth() > box.getHeight();
    }
}
