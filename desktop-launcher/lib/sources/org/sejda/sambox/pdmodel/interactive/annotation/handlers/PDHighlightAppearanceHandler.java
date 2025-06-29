package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.PDFormContentStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.blend.BlendMode;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDHighlightAppearanceHandler.class */
public class PDHighlightAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDHighlightAppearanceHandler.class);

    public PDHighlightAppearanceHandler(PDAnnotation annotation) {
        super(annotation);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateNormalAppearance() {
        PDColor color;
        PDRectangle rect;
        PDAnnotationTextMarkup annotation = (PDAnnotationTextMarkup) getAnnotation();
        float[] pathsArray = annotation.getQuadPoints();
        if (pathsArray == null || (color = annotation.getColor()) == null || color.getComponents().length == 0 || (rect = annotation.getRectangle()) == null) {
            return;
        }
        AnnotationBorder ab = AnnotationBorder.getAnnotationBorder(annotation, annotation.getBorderStyle());
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
        float maxDelta = 0.0f;
        for (int i2 = 0; i2 < pathsArray.length / 8; i2++) {
            maxDelta = Math.max(Math.max((pathsArray[i2 + 0] - pathsArray[i2 + 4]) / 4.0f, (pathsArray[i2 + 1] - pathsArray[i2 + 5]) / 4.0f), maxDelta);
        }
        rect.setLowerLeftX(Math.min((minX - (ab.width / 2.0f)) - maxDelta, rect.getLowerLeftX()));
        rect.setLowerLeftY(Math.min((minY - (ab.width / 2.0f)) - maxDelta, rect.getLowerLeftY()));
        rect.setUpperRightX(Math.max(maxX + ab.width + maxDelta, rect.getUpperRightX()));
        rect.setUpperRightY(Math.max(maxY + ab.width + maxDelta, rect.getUpperRightY()));
        annotation.setRectangle(rect);
        try {
            PDAppearanceContentStream cs = getNormalAppearanceAsContentStream();
            try {
                PDExtendedGraphicsState r0 = new PDExtendedGraphicsState();
                PDExtendedGraphicsState r1 = new PDExtendedGraphicsState();
                r0.setAlphaSourceFlag(false);
                r0.setStrokingAlphaConstant(Float.valueOf(annotation.getConstantOpacity()));
                r0.setNonStrokingAlphaConstant(Float.valueOf(annotation.getConstantOpacity()));
                r1.setAlphaSourceFlag(false);
                r1.setBlendMode(BlendMode.MULTIPLY);
                cs.setGraphicsStateParameters(r0);
                cs.setGraphicsStateParameters(r1);
                PDFormXObject frm1 = new PDFormXObject(new COSStream());
                PDFormXObject frm2 = new PDFormXObject(new COSStream());
                frm1.setResources(new PDResources());
                PDFormContentStream frm2CS = new PDFormContentStream(frm1);
                try {
                    frm2CS.drawForm(frm2);
                    frm2CS.close();
                    frm1.setBBox(annotation.getRectangle());
                    COSDictionary groupDict = new COSDictionary();
                    groupDict.setItem(COSName.S, (COSBase) COSName.TRANSPARENCY);
                    frm1.getCOSObject().setItem(COSName.GROUP, (COSBase) groupDict);
                    cs.drawForm(frm1);
                    frm2.setBBox(annotation.getRectangle());
                    frm2CS = new PDFormContentStream(frm2);
                    try {
                        frm2CS.setNonStrokingColor(color);
                        for (int of = 0; of + 7 < pathsArray.length; of += 8) {
                            float delta = 0.0f;
                            if (Float.compare(pathsArray[of + 0], pathsArray[of + 4]) == 0 && Float.compare(pathsArray[of + 1], pathsArray[of + 3]) == 0 && Float.compare(pathsArray[of + 2], pathsArray[of + 6]) == 0 && Float.compare(pathsArray[of + 5], pathsArray[of + 7]) == 0) {
                                delta = (pathsArray[of + 1] - pathsArray[of + 5]) / 4.0f;
                            } else if (Float.compare(pathsArray[of + 1], pathsArray[of + 5]) == 0 && Float.compare(pathsArray[of + 0], pathsArray[of + 2]) == 0 && Float.compare(pathsArray[of + 3], pathsArray[of + 7]) == 0 && Float.compare(pathsArray[of + 4], pathsArray[of + 6]) == 0) {
                                delta = (pathsArray[of + 0] - pathsArray[of + 4]) / 4.0f;
                            }
                            frm2CS.moveTo(pathsArray[of + 4], pathsArray[of + 5]);
                            if (Float.compare(pathsArray[of + 0], pathsArray[of + 4]) == 0) {
                                frm2CS.curveTo(pathsArray[of + 4] - delta, pathsArray[of + 5] + delta, pathsArray[of + 0] - delta, pathsArray[of + 1] - delta, pathsArray[of + 0], pathsArray[of + 1]);
                            } else if (Float.compare(pathsArray[of + 5], pathsArray[of + 1]) == 0) {
                                frm2CS.curveTo(pathsArray[of + 4] + delta, pathsArray[of + 5] + delta, pathsArray[of + 0] - delta, pathsArray[of + 1] + delta, pathsArray[of + 0], pathsArray[of + 1]);
                            } else {
                                frm2CS.lineTo(pathsArray[of + 0], pathsArray[of + 1]);
                            }
                            frm2CS.lineTo(pathsArray[of + 2], pathsArray[of + 3]);
                            if (Float.compare(pathsArray[of + 2], pathsArray[of + 6]) == 0) {
                                frm2CS.curveTo(pathsArray[of + 2] + delta, pathsArray[of + 3] - delta, pathsArray[of + 6] + delta, pathsArray[of + 7] + delta, pathsArray[of + 6], pathsArray[of + 7]);
                            } else if (Float.compare(pathsArray[of + 3], pathsArray[of + 7]) == 0) {
                                frm2CS.curveTo(pathsArray[of + 2] - delta, pathsArray[of + 3] - delta, pathsArray[of + 6] + delta, pathsArray[of + 7] - delta, pathsArray[of + 6], pathsArray[of + 7]);
                            } else {
                                frm2CS.lineTo(pathsArray[of + 6], pathsArray[of + 7]);
                            }
                            frm2CS.fill();
                        }
                        frm2CS.close();
                        if (cs != null) {
                            cs.close();
                        }
                    } finally {
                    }
                } finally {
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
