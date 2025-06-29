package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDPolylineAppearanceHandler.class */
public class PDPolylineAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDPolylineAppearanceHandler.class);

    public PDPolylineAppearanceHandler(PDAnnotation annotation) {
        super(annotation);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateNormalAppearance() {
        float[] pathsArray;
        PDAnnotationMarkup annotation = (PDAnnotationMarkup) getAnnotation();
        PDRectangle rect = annotation.getRectangle();
        if (rect == null || (pathsArray = annotation.getVertices()) == null || pathsArray.length < 4) {
            return;
        }
        AnnotationBorder ab = AnnotationBorder.getAnnotationBorder(annotation, annotation.getBorderStyle());
        PDColor color = annotation.getColor();
        if (color == null || color.getComponents().length == 0 || Float.compare(ab.width, 0.0f) == 0) {
            return;
        }
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;
        for (int i = 0; i < pathsArray.length / 2; i++) {
            float x = pathsArray[i * 2];
            float y = pathsArray[(i * 2) + 1];
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }
        rect.setLowerLeftX(Math.min(minX - (ab.width * 10.0f), rect.getLowerLeftX()));
        rect.setLowerLeftY(Math.min(minY - (ab.width * 10.0f), rect.getLowerLeftY()));
        rect.setUpperRightX(Math.max(maxX + (ab.width * 10.0f), rect.getUpperRightX()));
        rect.setUpperRightY(Math.max(maxY + (ab.width * 10.0f), rect.getUpperRightY()));
        annotation.setRectangle(rect);
        try {
            PDAppearanceContentStream cs = getNormalAppearanceAsContentStream();
            try {
                boolean hasBackground = cs.setNonStrokingColorOnDemand(annotation.getInteriorColor());
                setOpacity(cs, annotation.getConstantOpacity());
                boolean hasStroke = cs.setStrokingColorOnDemand(color);
                if (ab.dashArray != null) {
                    cs.setLineDashPattern(ab.dashArray, 0.0f);
                }
                cs.setLineWidth(ab.width);
                for (int i2 = 0; i2 < pathsArray.length / 2; i2++) {
                    float x2 = pathsArray[i2 * 2];
                    float y2 = pathsArray[(i2 * 2) + 1];
                    if (i2 == 0) {
                        if (SHORT_STYLES.contains(annotation.getStartPointEndingStyle())) {
                            float x1 = pathsArray[2];
                            float y1 = pathsArray[3];
                            float len = (float) Math.sqrt(Math.pow(x2 - x1, 2.0d) + Math.pow(y2 - y1, 2.0d));
                            if (Float.compare(len, 0.0f) != 0) {
                                x2 += ((x1 - x2) / len) * ab.width;
                                y2 += ((y1 - y2) / len) * ab.width;
                            }
                        }
                        cs.moveTo(x2, y2);
                    } else {
                        if (i2 == (pathsArray.length / 2) - 1 && SHORT_STYLES.contains(annotation.getEndPointEndingStyle())) {
                            float x0 = pathsArray[pathsArray.length - 4];
                            float y0 = pathsArray[pathsArray.length - 3];
                            float len2 = (float) Math.sqrt(Math.pow(x0 - x2, 2.0d) + Math.pow(y0 - y2, 2.0d));
                            if (Float.compare(len2, 0.0f) != 0) {
                                x2 -= ((x2 - x0) / len2) * ab.width;
                                y2 -= ((y2 - y0) / len2) * ab.width;
                            }
                        }
                        cs.lineTo(x2, y2);
                    }
                }
                cs.stroke();
                if (!"None".equals(annotation.getStartPointEndingStyle())) {
                    float x22 = pathsArray[2];
                    float y22 = pathsArray[3];
                    float x12 = pathsArray[0];
                    float y12 = pathsArray[1];
                    cs.saveGraphicsState();
                    if (ANGLED_STYLES.contains(annotation.getStartPointEndingStyle())) {
                        double angle = Math.atan2(y22 - y12, x22 - x12);
                        cs.transform(Matrix.getRotateInstance(angle, x12, y12));
                    } else {
                        cs.transform(Matrix.getTranslateInstance(x12, y12));
                    }
                    drawStyle(annotation.getStartPointEndingStyle(), cs, 0.0f, 0.0f, ab.width, hasStroke, hasBackground, false);
                    cs.restoreGraphicsState();
                }
                if (!"None".equals(annotation.getEndPointEndingStyle())) {
                    float x13 = pathsArray[pathsArray.length - 4];
                    float y13 = pathsArray[pathsArray.length - 3];
                    float x23 = pathsArray[pathsArray.length - 2];
                    float y23 = pathsArray[pathsArray.length - 1];
                    if (ANGLED_STYLES.contains(annotation.getEndPointEndingStyle())) {
                        double angle2 = Math.atan2(y23 - y13, x23 - x13);
                        cs.transform(Matrix.getRotateInstance(angle2, x23, y23));
                    } else {
                        cs.transform(Matrix.getTranslateInstance(x23, y23));
                    }
                    drawStyle(annotation.getEndPointEndingStyle(), cs, 0.0f, 0.0f, ab.width, hasStroke, hasBackground, true);
                }
                if (cs != null) {
                    cs.close();
                }
            } finally {
            }
        } catch (IOException e) {
            LOG.error("Error writing to the content stream", e);
        }
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateRolloverAppearance() {
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateDownAppearance() {
    }

    float getLineWidth() {
        PDAnnotationMarkup annotation = (PDAnnotationMarkup) getAnnotation();
        PDBorderStyleDictionary bs = annotation.getBorderStyle();
        if (bs != null) {
            return bs.getWidth();
        }
        COSArray borderCharacteristics = annotation.getBorder();
        if (borderCharacteristics.size() >= 3) {
            COSBase base = borderCharacteristics.getObject(2);
            if (base instanceof COSNumber) {
                return ((COSNumber) base).floatValue();
            }
            return 1.0f;
        }
        return 1.0f;
    }
}
