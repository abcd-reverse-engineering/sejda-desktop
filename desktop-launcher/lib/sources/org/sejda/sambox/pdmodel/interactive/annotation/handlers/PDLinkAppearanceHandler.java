package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDLinkAppearanceHandler.class */
public class PDLinkAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDLinkAppearanceHandler.class);

    public PDLinkAppearanceHandler(PDAnnotation annotation) {
        super(annotation);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateNormalAppearance() {
        PDBorderStyleDictionary borderStyleDic;
        PDAnnotationLink annotation = (PDAnnotationLink) getAnnotation();
        PDRectangle rect = annotation.getRectangle();
        if (rect == null) {
            return;
        }
        float lineWidth = getLineWidth();
        try {
            PDAppearanceContentStream contentStream = getNormalAppearanceAsContentStream();
            try {
                PDColor color = annotation.getColor();
                if (color == null) {
                    color = new PDColor(new float[]{0.0f}, PDDeviceGray.INSTANCE);
                }
                boolean hasStroke = contentStream.setStrokingColorOnDemand(color);
                contentStream.setBorderLine(lineWidth, annotation.getBorderStyle(), annotation.getBorder());
                PDRectangle borderEdge = getPaddedRectangle(getRectangle(), lineWidth / 2.0f);
                float[] pathsArray = annotation.getQuadPoints();
                if (pathsArray != null) {
                    int i = 0;
                    while (true) {
                        if (i >= pathsArray.length / 2) {
                            break;
                        }
                        if (rect.contains(pathsArray[i * 2], pathsArray[(i * 2) + 1])) {
                            i++;
                        } else {
                            LOG.warn("At least one /QuadPoints entry (" + pathsArray[i * 2] + ";" + pathsArray[(i * 2) + 1] + ") is outside of rectangle, " + rect + ", /QuadPoints are ignored and /Rect is used instead");
                            pathsArray = null;
                            break;
                        }
                    }
                }
                if (pathsArray == null) {
                    pathsArray = new float[]{borderEdge.getLowerLeftX(), borderEdge.getLowerLeftY(), borderEdge.getUpperRightX(), borderEdge.getLowerLeftY(), borderEdge.getUpperRightX(), borderEdge.getUpperRightY(), borderEdge.getLowerLeftX(), borderEdge.getUpperRightY()};
                }
                boolean underline = false;
                if (pathsArray.length >= 8 && (borderStyleDic = annotation.getBorderStyle()) != null) {
                    underline = PDBorderStyleDictionary.STYLE_UNDERLINE.equals(borderStyleDic.getStyle());
                }
                for (int of = 0; of + 7 < pathsArray.length; of += 8) {
                    contentStream.moveTo(pathsArray[of], pathsArray[of + 1]);
                    contentStream.lineTo(pathsArray[of + 2], pathsArray[of + 3]);
                    if (!underline) {
                        contentStream.lineTo(pathsArray[of + 4], pathsArray[of + 5]);
                        contentStream.lineTo(pathsArray[of + 6], pathsArray[of + 7]);
                        contentStream.closePath();
                    }
                }
                contentStream.drawShape(lineWidth, hasStroke, false);
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
        PDAnnotationLink annotation = (PDAnnotationLink) getAnnotation();
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
