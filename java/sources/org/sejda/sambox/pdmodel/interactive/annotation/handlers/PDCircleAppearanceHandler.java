package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationSquareCircle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderEffectDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDCircleAppearanceHandler.class */
public class PDCircleAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDCircleAppearanceHandler.class);

    public PDCircleAppearanceHandler(PDAnnotation annotation) {
        super(annotation);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateNormalAppearance() {
        float lineWidth = getLineWidth();
        PDAnnotationSquareCircle annotation = (PDAnnotationSquareCircle) getAnnotation();
        try {
            PDAppearanceContentStream contentStream = getNormalAppearanceAsContentStream();
            try {
                boolean hasStroke = contentStream.setStrokingColorOnDemand(getColor());
                boolean hasBackground = contentStream.setNonStrokingColorOnDemand(annotation.getInteriorColor());
                setOpacity(contentStream, annotation.getConstantOpacity());
                contentStream.setBorderLine(lineWidth, annotation.getBorderStyle(), annotation.getBorder());
                PDBorderEffectDictionary borderEffect = annotation.getBorderEffect();
                if (borderEffect != null && borderEffect.getStyle().equals(PDBorderEffectDictionary.STYLE_CLOUDY)) {
                    CloudyBorder cloudyBorder = new CloudyBorder(contentStream, borderEffect.getIntensity(), lineWidth, getRectangle());
                    cloudyBorder.createCloudyEllipse(annotation.getRectDifference());
                    annotation.setRectangle(cloudyBorder.getRectangle());
                    annotation.setRectDifference(cloudyBorder.getRectDifference());
                    PDAppearanceStream appearanceStream = annotation.getNormalAppearanceStream();
                    appearanceStream.setBBox(cloudyBorder.getBBox());
                    appearanceStream.setMatrix(cloudyBorder.getMatrix());
                } else {
                    PDRectangle borderBox = handleBorderBox(annotation, lineWidth);
                    float x0 = borderBox.getLowerLeftX();
                    float y0 = borderBox.getLowerLeftY();
                    float x1 = borderBox.getUpperRightX();
                    float y1 = borderBox.getUpperRightY();
                    float xm = x0 + (borderBox.getWidth() / 2.0f);
                    float ym = y0 + (borderBox.getHeight() / 2.0f);
                    float vOffset = (borderBox.getHeight() / 2.0f) * 0.55555415f;
                    float hOffset = (borderBox.getWidth() / 2.0f) * 0.55555415f;
                    contentStream.moveTo(xm, y1);
                    contentStream.curveTo(xm + hOffset, y1, x1, ym + vOffset, x1, ym);
                    contentStream.curveTo(x1, ym - vOffset, xm + hOffset, y0, xm, y0);
                    contentStream.curveTo(xm - hOffset, y0, x0, ym - vOffset, x0, ym);
                    contentStream.curveTo(x0, ym + vOffset, xm - hOffset, y1, xm, y1);
                    contentStream.closePath();
                }
                contentStream.drawShape(lineWidth, hasStroke, hasBackground);
                if (contentStream != null) {
                    contentStream.close();
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
