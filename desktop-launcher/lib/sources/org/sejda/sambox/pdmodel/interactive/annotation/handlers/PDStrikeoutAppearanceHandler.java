package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDStrikeoutAppearanceHandler.class */
public class PDStrikeoutAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDStrikeoutAppearanceHandler.class);

    public PDStrikeoutAppearanceHandler(PDAnnotation annotation) {
        super(annotation);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateNormalAppearance() {
        float[] pathsArray;
        PDAnnotationTextMarkup annotation = (PDAnnotationTextMarkup) getAnnotation();
        PDRectangle rect = annotation.getRectangle();
        if (rect == null || (pathsArray = annotation.getQuadPoints()) == null) {
            return;
        }
        AnnotationBorder ab = AnnotationBorder.getAnnotationBorder(annotation, annotation.getBorderStyle());
        PDColor color = annotation.getColor();
        if (color == null || color.getComponents().length == 0) {
            return;
        }
        if (Float.compare(ab.width, 0.0f) == 0) {
            ab.width = 1.5f;
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
        rect.setLowerLeftX(Math.min(minX - (ab.width / 2.0f), rect.getLowerLeftX()));
        rect.setLowerLeftY(Math.min(minY - (ab.width / 2.0f), rect.getLowerLeftY()));
        rect.setUpperRightX(Math.max(maxX + (ab.width / 2.0f), rect.getUpperRightX()));
        rect.setUpperRightY(Math.max(maxY + (ab.width / 2.0f), rect.getUpperRightY()));
        annotation.setRectangle(rect);
        try {
            PDAppearanceContentStream cs = getNormalAppearanceAsContentStream();
            try {
                setOpacity(cs, annotation.getConstantOpacity());
                cs.setStrokingColor(color);
                if (ab.dashArray != null) {
                    cs.setLineDashPattern(ab.dashArray, 0.0f);
                }
                cs.setLineWidth(ab.width);
                for (int i2 = 0; i2 < pathsArray.length / 8; i2++) {
                    float len0 = (float) Math.sqrt(Math.pow(pathsArray[i2 * 8] - pathsArray[(i2 * 8) + 4], 2.0d) + Math.pow(pathsArray[(i2 * 8) + 1] - pathsArray[(i2 * 8) + 5], 2.0d));
                    float x0 = pathsArray[(i2 * 8) + 4];
                    float y0 = pathsArray[(i2 * 8) + 5];
                    if (Float.compare(len0, 0.0f) != 0) {
                        x0 += ((pathsArray[i2 * 8] - pathsArray[(i2 * 8) + 4]) / len0) * ((len0 / 2.0f) - ab.width);
                        y0 += ((pathsArray[(i2 * 8) + 1] - pathsArray[(i2 * 8) + 5]) / len0) * ((len0 / 2.0f) - ab.width);
                    }
                    float len1 = (float) Math.sqrt(Math.pow(pathsArray[(i2 * 8) + 2] - pathsArray[(i2 * 8) + 6], 2.0d) + Math.pow(pathsArray[(i2 * 8) + 3] - pathsArray[(i2 * 8) + 7], 2.0d));
                    float x1 = pathsArray[(i2 * 8) + 6];
                    float y1 = pathsArray[(i2 * 8) + 7];
                    if (Float.compare(len1, 0.0f) != 0) {
                        x1 += ((pathsArray[(i2 * 8) + 2] - pathsArray[(i2 * 8) + 6]) / len1) * ((len1 / 2.0f) - ab.width);
                        y1 += ((pathsArray[(i2 * 8) + 3] - pathsArray[(i2 * 8) + 7]) / len1) * ((len1 / 2.0f) - ab.width);
                    }
                    cs.moveTo(x0, y0);
                    cs.lineTo(x1, y1);
                }
                cs.stroke();
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
}
