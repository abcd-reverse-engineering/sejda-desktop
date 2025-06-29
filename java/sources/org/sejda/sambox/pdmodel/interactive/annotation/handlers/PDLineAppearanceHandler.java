package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLine;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDLineAppearanceHandler.class */
public class PDLineAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDLineAppearanceHandler.class);
    static final int FONT_SIZE = 9;

    public PDLineAppearanceHandler(PDAnnotation annotation) {
        super(annotation);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateNormalAppearance() {
        float[] pathsArray;
        float yOffset;
        PDAnnotationLine annotation = (PDAnnotationLine) getAnnotation();
        PDRectangle rect = annotation.getRectangle();
        if (rect == null || (pathsArray = annotation.getLine()) == null) {
            return;
        }
        AnnotationBorder ab = AnnotationBorder.getAnnotationBorder(annotation, annotation.getBorderStyle());
        PDColor color = annotation.getColor();
        if (color == null || color.getComponents().length == 0) {
            return;
        }
        float ll = annotation.getLeaderLineLength();
        float lle = annotation.getLeaderLineExtensionLength();
        float llo = annotation.getLeaderLineOffsetLength();
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
        if (ll < 0.0f) {
            llo = -llo;
            lle = -lle;
        }
        float lineEndingSize = ((double) ab.width) < 1.0E-5d ? 1.0f : ab.width;
        float max = Math.max(lineEndingSize * 10.0f, Math.abs(llo + ll + lle));
        rect.setLowerLeftX(Math.min(minX - max, rect.getLowerLeftX()));
        rect.setLowerLeftY(Math.min(minY - max, rect.getLowerLeftY()));
        rect.setUpperRightX(Math.max(maxX + max, rect.getUpperRightX()));
        rect.setUpperRightY(Math.max(maxY + max, rect.getUpperRightY()));
        annotation.setRectangle(rect);
        try {
            PDAppearanceContentStream cs = getNormalAppearanceAsContentStream();
            try {
                setOpacity(cs, annotation.getConstantOpacity());
                boolean hasStroke = cs.setStrokingColorOnDemand(color);
                if (ab.dashArray != null) {
                    cs.setLineDashPattern(ab.dashArray, 0.0f);
                }
                cs.setLineWidth(ab.width);
                float x1 = pathsArray[0];
                float y1 = pathsArray[1];
                float x2 = pathsArray[2];
                float y2 = pathsArray[3];
                float y3 = llo + ll;
                String contents = annotation.getContents();
                if (contents == null) {
                    contents = "";
                }
                cs.saveGraphicsState();
                double angle = Math.atan2(y2 - y1, x2 - x1);
                cs.transform(Matrix.getRotateInstance(angle, x1, y1));
                float lineLength = (float) Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
                cs.moveTo(0.0f, llo);
                cs.lineTo(0.0f, llo + ll + lle);
                cs.moveTo(lineLength, llo);
                cs.lineTo(lineLength, llo + ll + lle);
                if (annotation.getCaption() && !contents.isEmpty()) {
                    PDType1Font font = PDType1Font.HELVETICA();
                    float contentLength = 0.0f;
                    try {
                        contentLength = (font.getStringWidth(annotation.getContents()) / 1000.0f) * 9.0f;
                    } catch (IllegalArgumentException ex) {
                        LOG.error("line text '" + annotation.getContents() + "' can't be shown", ex);
                    }
                    float xOffset = (lineLength - contentLength) / 2.0f;
                    String captionPositioning = annotation.getCaptionPositioning();
                    if (SHORT_STYLES.contains(annotation.getStartPointEndingStyle())) {
                        cs.moveTo(lineEndingSize, y3);
                    } else {
                        cs.moveTo(0.0f, y3);
                    }
                    if ("Top".equals(captionPositioning)) {
                        yOffset = 1.908f;
                    } else {
                        yOffset = -2.6f;
                        cs.lineTo(xOffset - lineEndingSize, y3);
                        cs.moveTo((lineLength - xOffset) + lineEndingSize, y3);
                    }
                    if (SHORT_STYLES.contains(annotation.getEndPointEndingStyle())) {
                        cs.lineTo(lineLength - lineEndingSize, y3);
                    } else {
                        cs.lineTo(lineLength, y3);
                    }
                    cs.drawShape(lineEndingSize, hasStroke, false);
                    float captionHorizontalOffset = annotation.getCaptionHorizontalOffset();
                    float captionVerticalOffset = annotation.getCaptionVerticalOffset();
                    if (contentLength > 0.0f) {
                        cs.beginText();
                        cs.setFont(font, 9.0f);
                        cs.newLineAtOffset(xOffset + captionHorizontalOffset, y3 + yOffset + captionVerticalOffset);
                        cs.showText(annotation.getContents());
                        cs.endText();
                    }
                    if (Float.compare(captionVerticalOffset, 0.0f) != 0) {
                        cs.moveTo(0.0f + (lineLength / 2.0f), y3);
                        cs.lineTo(0.0f + (lineLength / 2.0f), y3 + captionVerticalOffset);
                        cs.drawShape(lineEndingSize, hasStroke, false);
                    }
                } else {
                    if (SHORT_STYLES.contains(annotation.getStartPointEndingStyle())) {
                        cs.moveTo(lineEndingSize, y3);
                    } else {
                        cs.moveTo(0.0f, y3);
                    }
                    if (SHORT_STYLES.contains(annotation.getEndPointEndingStyle())) {
                        cs.lineTo(lineLength - lineEndingSize, y3);
                    } else {
                        cs.lineTo(lineLength, y3);
                    }
                    cs.drawShape(lineEndingSize, hasStroke, false);
                }
                cs.restoreGraphicsState();
                boolean hasBackground = cs.setNonStrokingColorOnDemand(annotation.getInteriorColor());
                if (ab.width < 1.0E-5d) {
                    hasStroke = false;
                }
                if (!"None".equals(annotation.getStartPointEndingStyle())) {
                    cs.saveGraphicsState();
                    if (ANGLED_STYLES.contains(annotation.getStartPointEndingStyle())) {
                        cs.transform(Matrix.getRotateInstance(angle, x1, y1));
                        drawStyle(annotation.getStartPointEndingStyle(), cs, 0.0f, y3, lineEndingSize, hasStroke, hasBackground, false);
                    } else {
                        float xx1 = x1 - ((float) (y3 * Math.sin(angle)));
                        float yy1 = y1 + ((float) (y3 * Math.cos(angle)));
                        drawStyle(annotation.getStartPointEndingStyle(), cs, xx1, yy1, lineEndingSize, hasStroke, hasBackground, false);
                    }
                    cs.restoreGraphicsState();
                }
                if (!"None".equals(annotation.getEndPointEndingStyle())) {
                    if (ANGLED_STYLES.contains(annotation.getEndPointEndingStyle())) {
                        cs.transform(Matrix.getRotateInstance(angle, x2, y2));
                        drawStyle(annotation.getEndPointEndingStyle(), cs, 0.0f, y3, lineEndingSize, hasStroke, hasBackground, true);
                    } else {
                        float xx2 = x2 - ((float) (y3 * Math.sin(angle)));
                        float yy2 = y2 + ((float) (y3 * Math.cos(angle)));
                        drawStyle(annotation.getEndPointEndingStyle(), cs, xx2, yy2, lineEndingSize, hasStroke, hasBackground, true);
                    }
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
