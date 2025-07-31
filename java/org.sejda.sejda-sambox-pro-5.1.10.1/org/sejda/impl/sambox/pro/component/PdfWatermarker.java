package org.sejda.impl.sambox.pro.component;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Optional;
import org.sejda.impl.sambox.component.PageImageWriter;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.pro.watermark.Location;
import org.sejda.model.pro.watermark.Watermark;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDTransparencyGroupAttributes;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/PdfWatermarker.class */
public class PdfWatermarker {
    private PageImageWriter imageWriter;
    private PDFormXObject form;
    private Watermark watermark;

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.sejda.model.exception.TaskIOException */
    public PdfWatermarker(Watermark watermark, PDDocument document) throws TaskIOException {
        this.imageWriter = new PageImageWriter(document);
        this.watermark = watermark;
        PDImageXObject watermarkXObject = PageImageWriter.toPDXImageObject(watermark.getSource());
        this.form = new PDFormXObject();
        this.form.setResources(new PDResources());
        PDTransparencyGroupAttributes group = new PDTransparencyGroupAttributes();
        group.setKnockout();
        this.form.setGroup(group);
        PDRectangle bbox = (PDRectangle) Optional.ofNullable(this.watermark.getDimension()).map(d -> {
            return new PDRectangle((float) d.getWidth(), (float) d.getHeight());
        }).orElseGet(() -> {
            return new PDRectangle(watermarkXObject.getWidth(), watermarkXObject.getHeight());
        });
        this.form.setBBox(bbox);
        int degrees = this.watermark.getRotationDegrees() % 360;
        degrees = degrees < 0 ? degrees + 360 : degrees;
        if (degrees != 0) {
            AffineTransform at = this.form.getMatrix().createAffineTransform();
            at.rotate(Math.toRadians(degrees), bbox.getWidth() / 2.0f, bbox.getHeight() / 2.0f);
            this.form.setMatrix(at);
        }
        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, this.form);
            try {
                contentStream.drawImage(watermarkXObject, 0.0f, 0.0f, this.form.getBBox().getWidth(), this.form.getBBox().getHeight());
                contentStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new TaskIOException("An error occurred writing form xobject stream.", e);
        }
    }

    public void mark(PDPage page) throws TaskIOException {
        PDExtendedGraphicsState gs = null;
        if (this.watermark.getOpacity() != 100) {
            gs = new PDExtendedGraphicsState();
            float alpha = this.watermark.getOpacity() / 100.0f;
            gs.setStrokingAlphaConstant(Float.valueOf(alpha));
            gs.setNonStrokingAlphaConstant(Float.valueOf(alpha));
        }
        if (this.watermark.getLocation() == Location.BEHIND) {
            this.imageWriter.prepend(page, this.form, page.cropBoxCoordinatesToDraw(this.watermark.getPosition()), 1.0f, 1.0f, gs, page.getRotation());
        } else {
            this.imageWriter.append(page, this.form, page.cropBoxCoordinatesToDraw(this.watermark.getPosition()), 1.0f, 1.0f, gs, page.getRotation());
        }
    }
}
