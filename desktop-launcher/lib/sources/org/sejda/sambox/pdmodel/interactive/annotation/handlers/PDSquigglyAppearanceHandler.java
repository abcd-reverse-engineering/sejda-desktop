package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.PDFormContentStream;
import org.sejda.sambox.pdmodel.PDPatternContentStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.sejda.sambox.pdmodel.graphics.color.PDPattern;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.pattern.PDTilingPattern;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDSquigglyAppearanceHandler.class */
public class PDSquigglyAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDSquigglyAppearanceHandler.class);

    public PDSquigglyAppearanceHandler(PDAnnotation annotation) {
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
                for (int i2 = 0; i2 < pathsArray.length / 8; i2++) {
                    float height = pathsArray[(i2 * 8) + 1] - pathsArray[(i2 * 8) + 5];
                    cs.transform(new Matrix(height / 40.0f, 0.0f, 0.0f, (height / 40.0f) / 1.8f, pathsArray[(i2 * 8) + 4], pathsArray[(i2 * 8) + 5]));
                    PDFormXObject form = new PDFormXObject(new COSStream());
                    form.setBBox(new PDRectangle(-0.5f, -0.5f, (((pathsArray[(i2 * 8) + 2] - pathsArray[i2 * 8]) / height) * 40.0f) + 0.5f, 13.0f));
                    form.setResources(new PDResources());
                    form.setMatrix(AffineTransform.getTranslateInstance(0.5d, 0.5d));
                    cs.drawForm(form);
                    PDFormContentStream formCS = new PDFormContentStream(form);
                    try {
                        PDTilingPattern pattern = new PDTilingPattern();
                        pattern.setBBox(new PDRectangle(0.0f, 0.0f, 10.0f, 12.0f));
                        pattern.setXStep(10.0f);
                        pattern.setYStep(13.0f);
                        pattern.setTilingType(3);
                        pattern.setPaintType(2);
                        PDPatternContentStream patternCS = new PDPatternContentStream(pattern);
                        try {
                            patternCS.setLineCapStyle(1);
                            patternCS.setLineJoinStyle(1);
                            patternCS.setLineWidth(1.0f);
                            patternCS.setMiterLimit(10.0f);
                            patternCS.moveTo(0.0f, 1.0f);
                            patternCS.lineTo(5.0f, 11.0f);
                            patternCS.lineTo(10.0f, 1.0f);
                            patternCS.stroke();
                            patternCS.close();
                            COSName patternName = form.getResources().add(pattern);
                            PDColorSpace patternColorSpace = new PDPattern(null, PDDeviceRGB.INSTANCE);
                            PDColor patternColor = new PDColor(color.getComponents(), patternName, patternColorSpace);
                            formCS.setNonStrokingColor(patternColor);
                            formCS.addRect(0.0f, 0.0f, ((pathsArray[(i2 * 8) + 2] - pathsArray[i2 * 8]) / height) * 40.0f, 12.0f);
                            formCS.fill();
                            formCS.close();
                        } catch (Throwable th) {
                            try {
                                patternCS.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        try {
                            formCS.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                        throw th3;
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
