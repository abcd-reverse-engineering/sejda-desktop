package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDCaretAppearanceHandler.class */
public class PDCaretAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDCaretAppearanceHandler.class);

    public PDCaretAppearanceHandler(PDAnnotation annotation) {
        super(annotation);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateNormalAppearance() {
        PDAnnotationMarkup annotation = (PDAnnotationMarkup) getAnnotation();
        try {
            PDAppearanceContentStream contentStream = getNormalAppearanceAsContentStream();
            try {
                contentStream.setStrokingColor(getColor());
                contentStream.setNonStrokingColor(getColor());
                setOpacity(contentStream, annotation.getConstantOpacity());
                PDRectangle rect = getRectangle();
                PDRectangle bbox = new PDRectangle(rect.getWidth(), rect.getHeight());
                PDAppearanceStream pdAppearanceStream = annotation.getNormalAppearanceStream();
                if (!annotation.getCOSObject().containsKey(COSName.RD)) {
                    float rd = Math.min(rect.getHeight() / 10.0f, 5.0f);
                    annotation.setRectDifferences(rd);
                    bbox = new PDRectangle(-rd, -rd, rect.getWidth() + (2.0f * rd), rect.getHeight() + (2.0f * rd));
                    Matrix matrix = pdAppearanceStream.getMatrix();
                    matrix.transformPoint(rd, rd);
                    pdAppearanceStream.setMatrix(matrix.createAffineTransform());
                    PDRectangle rect2 = new PDRectangle(rect.getLowerLeftX() - rd, rect.getLowerLeftY() - rd, rect.getWidth() + (2.0f * rd), rect.getHeight() + (2.0f * rd));
                    annotation.setRectangle(rect2);
                }
                pdAppearanceStream.setBBox(bbox);
                float halfX = rect.getWidth() / 2.0f;
                float halfY = rect.getHeight() / 2.0f;
                contentStream.moveTo(0.0f, 0.0f);
                contentStream.curveTo(halfX, 0.0f, halfX, halfY, halfX, rect.getHeight());
                contentStream.curveTo(halfX, halfY, halfX, 0.0f, rect.getWidth(), 0.0f);
                contentStream.closePath();
                contentStream.fill();
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
}
