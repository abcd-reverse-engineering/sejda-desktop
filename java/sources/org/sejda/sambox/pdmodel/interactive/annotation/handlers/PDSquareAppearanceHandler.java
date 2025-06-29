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

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDSquareAppearanceHandler.class */
public class PDSquareAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDSquareAppearanceHandler.class);

    public PDSquareAppearanceHandler(PDAnnotation annotation) {
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
                    cloudyBorder.createCloudyRectangle(annotation.getRectDifference());
                    annotation.setRectangle(cloudyBorder.getRectangle());
                    annotation.setRectDifference(cloudyBorder.getRectDifference());
                    PDAppearanceStream appearanceStream = annotation.getNormalAppearanceStream();
                    appearanceStream.setBBox(cloudyBorder.getBBox());
                    appearanceStream.setMatrix(cloudyBorder.getMatrix());
                } else {
                    PDRectangle borderBox = handleBorderBox(annotation, lineWidth);
                    if (borderBox != null) {
                        contentStream.addRect(borderBox.getLowerLeftX(), borderBox.getLowerLeftY(), borderBox.getWidth(), borderBox.getHeight());
                    }
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
