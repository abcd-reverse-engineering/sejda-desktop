package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDInkAppearanceHandler.class */
public class PDInkAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDInkAppearanceHandler.class);

    public PDInkAppearanceHandler(PDAnnotation annotation) {
        super(annotation);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateNormalAppearance() {
        PDAnnotationMarkup ink = (PDAnnotationMarkup) getAnnotation();
        AnnotationBorder ab = AnnotationBorder.getAnnotationBorder(ink, ink.getBorderStyle());
        PDColor color = ink.getColor();
        if (color == null || color.getComponents().length == 0 || Float.compare(ab.width, 0.0f) == 0) {
            return;
        }
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;
        for (float[] pathArray : ink.getInkList()) {
            int nPoints = pathArray.length / 2;
            for (int i = 0; i < nPoints; i++) {
                float x = pathArray[i * 2];
                float y = pathArray[(i * 2) + 1];
                minX = Math.min(minX, x);
                minY = Math.min(minY, y);
                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);
            }
        }
        PDRectangle rect = ink.getRectangle();
        if (rect == null) {
            return;
        }
        rect.setLowerLeftX(Math.min(minX - (ab.width * 2.0f), rect.getLowerLeftX()));
        rect.setLowerLeftY(Math.min(minY - (ab.width * 2.0f), rect.getLowerLeftY()));
        rect.setUpperRightX(Math.max(maxX + (ab.width * 2.0f), rect.getUpperRightX()));
        rect.setUpperRightY(Math.max(maxY + (ab.width * 2.0f), rect.getUpperRightY()));
        ink.setRectangle(rect);
        try {
            PDAppearanceContentStream cs = getNormalAppearanceAsContentStream();
            try {
                setOpacity(cs, ink.getConstantOpacity());
                cs.setStrokingColor(color);
                if (ab.dashArray != null) {
                    cs.setLineDashPattern(ab.dashArray, 0.0f);
                }
                cs.setLineWidth(ab.width);
                for (float[] pathArray2 : ink.getInkList()) {
                    int nPoints2 = pathArray2.length / 2;
                    for (int i2 = 0; i2 < nPoints2; i2++) {
                        float x2 = pathArray2[i2 * 2];
                        float y2 = pathArray2[(i2 * 2) + 1];
                        if (i2 == 0) {
                            cs.moveTo(x2, y2);
                        } else {
                            cs.lineTo(x2, y2);
                        }
                    }
                    cs.stroke();
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
}
