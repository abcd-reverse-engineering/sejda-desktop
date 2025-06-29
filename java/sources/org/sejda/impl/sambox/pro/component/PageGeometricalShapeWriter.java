package org.sejda.impl.sambox.pro.component;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.parameter.edit.Shape;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageContentStream;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/PageGeometricalShapeWriter.class */
public class PageGeometricalShapeWriter {
    private PDDocument document;

    public PageGeometricalShapeWriter(PDDocument document) {
        this.document = document;
    }

    public void drawShape(Shape shape, PDPage page, Point2D position, float width, float height, Color borderColor, Color backgroundColor, float borderWidth) throws TaskIOException {
        switch (shape) {
            case RECTANGLE:
                drawRectangle(page, position, width, height, borderColor, backgroundColor, borderWidth);
                break;
            case ELLIPSE:
                drawEllipse(page, position, width, height, borderColor, backgroundColor, borderWidth);
                break;
        }
    }

    public void drawRectangle(PDPage page, Point2D position, float width, float height, Color borderColor, Color backgroundColor, float borderWidth) throws TaskIOException {
        try {
            PDPageContentStream contentStream = new PDPageContentStream(this.document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            try {
                contentStream.setLineWidth(borderWidth);
                if (backgroundColor != null) {
                    contentStream.setNonStrokingColor(backgroundColor);
                }
                contentStream.setStrokingColor(borderColor);
                contentStream.addRect((float) position.getX(), (float) position.getY(), width, height);
                if (backgroundColor != null) {
                    contentStream.closeAndFillAndStroke();
                } else {
                    contentStream.closeAndStroke();
                }
                contentStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new TaskIOException("An error occurred writing image to the page.", e);
        }
    }

    public void drawEllipse(PDPage page, Point2D position, float width, float height, Color borderColor, Color backgroundColor, float borderWidth) throws TaskIOException {
        try {
            PDPageContentStream contentStream = new PDPageContentStream(this.document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            try {
                contentStream.setLineWidth(borderWidth);
                if (backgroundColor != null) {
                    contentStream.setNonStrokingColor(backgroundColor);
                }
                contentStream.setStrokingColor(borderColor);
                float x = (float) position.getX();
                float y = (float) position.getY();
                float ox = (width / 2.0f) * 0.5522848f;
                float oy = (height / 2.0f) * 0.5522848f;
                float xe = x + width;
                float ye = y + height;
                float xm = x + (width / 2.0f);
                float ym = y + (height / 2.0f);
                contentStream.moveTo(x, ym);
                contentStream.curveTo(x, ym - oy, xm - ox, y, xm, y);
                contentStream.curveTo(xm + ox, y, xe, ym - oy, xe, ym);
                contentStream.curveTo(xe, ym + oy, xm + ox, ye, xm, ye);
                contentStream.curveTo(xm - ox, ye, x, ym + oy, x, ym);
                if (backgroundColor != null) {
                    contentStream.closeAndFillAndStroke();
                } else {
                    contentStream.closeAndStroke();
                }
                contentStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new TaskIOException("An error occurred writing image to the page.", e);
        }
    }
}
