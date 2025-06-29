package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderEffectDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDPolygonAppearanceHandler.class */
public class PDPolygonAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDPolygonAppearanceHandler.class);

    public PDPolygonAppearanceHandler(PDAnnotation annotation) {
        super(annotation);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateNormalAppearance() {
        PDAnnotationMarkup annotation = (PDAnnotationMarkup) getAnnotation();
        float lineWidth = getLineWidth();
        PDRectangle rect = annotation.getRectangle();
        if (rect == null) {
            return;
        }
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;
        float[][] pathArray = getPathArray(annotation);
        if (pathArray == null) {
            return;
        }
        for (int i = 0; i < pathArray.length; i++) {
            for (int j = 0; j < pathArray[i].length / 2; j++) {
                float x = pathArray[i][j * 2];
                float y = pathArray[i][(j * 2) + 1];
                minX = Math.min(minX, x);
                minY = Math.min(minY, y);
                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);
            }
        }
        rect.setLowerLeftX(Math.min(minX - lineWidth, rect.getLowerLeftX()));
        rect.setLowerLeftY(Math.min(minY - lineWidth, rect.getLowerLeftY()));
        rect.setUpperRightX(Math.max(maxX + lineWidth, rect.getUpperRightX()));
        rect.setUpperRightY(Math.max(maxY + lineWidth, rect.getUpperRightY()));
        annotation.setRectangle(rect);
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
                    cloudyBorder.createCloudyPolygon(pathArray);
                    annotation.setRectangle(cloudyBorder.getRectangle());
                    PDAppearanceStream appearanceStream = annotation.getNormalAppearanceStream();
                    appearanceStream.setBBox(cloudyBorder.getBBox());
                    appearanceStream.setMatrix(cloudyBorder.getMatrix());
                } else {
                    for (int i2 = 0; i2 < pathArray.length; i2++) {
                        float[] pointsArray = pathArray[i2];
                        if (i2 == 0 && pointsArray.length == 2) {
                            contentStream.moveTo(pointsArray[0], pointsArray[1]);
                        } else if (pointsArray.length == 2) {
                            contentStream.lineTo(pointsArray[0], pointsArray[1]);
                        } else if (pointsArray.length == 6) {
                            contentStream.curveTo(pointsArray[0], pointsArray[1], pointsArray[2], pointsArray[3], pointsArray[4], pointsArray[5]);
                        }
                    }
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

    private float[][] getPathArray(PDAnnotationMarkup annotation) {
        float[][] pathArray = annotation.getPath();
        if (pathArray == null) {
            float[] verticesArray = annotation.getVertices();
            if (verticesArray == null) {
                return null;
            }
            int points = verticesArray.length / 2;
            pathArray = new float[points][2];
            for (int i = 0; i < points; i++) {
                pathArray[i][0] = verticesArray[i * 2];
                pathArray[i][1] = verticesArray[(i * 2) + 1];
            }
        }
        return pathArray;
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
