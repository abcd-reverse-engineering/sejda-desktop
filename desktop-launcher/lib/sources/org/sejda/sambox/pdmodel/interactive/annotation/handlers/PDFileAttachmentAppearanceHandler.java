package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDFileAttachmentAppearanceHandler.class */
public class PDFileAttachmentAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDFileAttachmentAppearanceHandler.class);

    public PDFileAttachmentAppearanceHandler(PDAnnotation annotation) {
        super(annotation);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateNormalAppearance() {
        PDAnnotationFileAttachment annotation = (PDAnnotationFileAttachment) getAnnotation();
        PDRectangle rect = getRectangle();
        if (rect == null) {
            return;
        }
        try {
            PDAppearanceContentStream contentStream = getNormalAppearanceAsContentStream();
            try {
                setOpacity(contentStream, annotation.getConstantOpacity());
                rect.setUpperRightX(rect.getLowerLeftX() + 18);
                rect.setLowerLeftY(rect.getUpperRightY() - 18);
                annotation.setRectangle(rect);
                annotation.getNormalAppearanceStream().setBBox(new PDRectangle(18, 18));
                drawPaperclip(contentStream);
                if (contentStream != null) {
                    contentStream.close();
                }
            } finally {
            }
        } catch (IOException e) {
            LOG.error("Unable to generate normal appearance", e);
        }
    }

    private void drawPaperclip(PDAppearanceContentStream contentStream) throws IOException {
        contentStream.moveTo(13.574f, 9.301f);
        contentStream.lineTo(8.926f, 13.949f);
        contentStream.curveTo(7.648f, 15.227f, 5.625f, 15.227f, 4.426f, 13.949f);
        contentStream.curveTo(3.148f, 12.676f, 3.148f, 10.648f, 4.426f, 9.449f);
        contentStream.lineTo(10.426f, 3.449f);
        contentStream.curveTo(11.176f, 2.773f, 12.301f, 2.773f, 13.051f, 3.449f);
        contentStream.curveTo(13.801f, 4.199f, 13.801f, 5.398f, 13.051f, 6.074f);
        contentStream.lineTo(7.875f, 11.25f);
        contentStream.curveTo(7.648f, 11.477f, 7.273f, 11.477f, 7.051f, 11.25f);
        contentStream.curveTo(6.824f, 11.023f, 6.824f, 10.648f, 7.051f, 10.426f);
        contentStream.lineTo(10.875f, 6.602f);
        contentStream.curveTo(11.176f, 6.301f, 11.176f, 5.852f, 10.875f, 5.551f);
        contentStream.curveTo(10.574f, 5.25f, 10.125f, 5.25f, 9.824f, 5.551f);
        contentStream.lineTo(6.0f, 9.449f);
        contentStream.curveTo(5.176f, 10.273f, 5.176f, 11.551f, 6.0f, 12.375f);
        contentStream.curveTo(6.824f, 13.125f, 8.102f, 13.125f, 8.926f, 12.375f);
        contentStream.lineTo(14.102f, 7.199f);
        contentStream.curveTo(15.449f, 5.852f, 15.449f, 3.75f, 14.102f, 2.398f);
        contentStream.curveTo(12.75f, 1.051f, 10.648f, 1.051f, 9.301f, 2.398f);
        contentStream.lineTo(3.301f, 8.398f);
        contentStream.curveTo(2.398f, 9.301f, 1.949f, 10.5f, 1.949f, 11.699f);
        contentStream.curveTo(1.949f, 14.324f, 4.051f, 16.352f, 6.676f, 16.352f);
        contentStream.curveTo(7.949f, 16.352f, 9.074f, 15.824f, 9.977f, 15.0f);
        contentStream.lineTo(14.625f, 10.352f);
        contentStream.curveTo(14.926f, 10.051f, 14.926f, 9.602f, 14.625f, 9.301f);
        contentStream.curveTo(14.324f, 9.0f, 13.875f, 9.0f, 13.574f, 9.301f);
        contentStream.closePath();
        contentStream.fill();
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateRolloverAppearance() {
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateDownAppearance() {
    }
}
