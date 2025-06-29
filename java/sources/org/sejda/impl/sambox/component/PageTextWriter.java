package org.sejda.impl.sambox.component;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.sejda.core.support.util.StringUtils;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.model.HorizontalAlign;
import org.sejda.model.VerticalAlign;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.exception.UnsupportedTextException;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;
import org.sejda.sambox.util.BidiUtils;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PageTextWriter.class */
public class PageTextWriter {
    private PDDocument document;
    private static final Logger LOG = LoggerFactory.getLogger(PageTextWriter.class);
    public static final Float DEFAULT_MARGIN = Float.valueOf(20.0f);

    public PageTextWriter(PDDocument document) {
        this.document = document;
    }

    public void write(PDPage page, HorizontalAlign hAlign, VerticalAlign vAlign, String rawLabel, PDFont font, Double fontSize, Color color) throws TaskIOException {
        try {
            String label = BidiUtils.visualToLogical(StringUtils.shapeArabicIf(org.sejda.commons.util.StringUtils.normalizeWhitespace(rawLabel)));
            List<TextWithFont> resolvedStringsToFonts = FontUtils.resolveFonts(label, font, this.document);
            float stringWidth = 0.0f;
            for (TextWithFont stringAndFont : resolvedStringsToFonts) {
                String s = stringAndFont.getText();
                PDFont f = stringAndFont.getFont();
                stringWidth += (f.getStringWidth(s) * fontSize.floatValue()) / 1000.0f;
            }
            PDRectangle pageSize = page.getCropBox().rotate(page.getRotation());
            write(page, (Point2D) new Point2D.Double(hAlign.position(pageSize.getWidth(), stringWidth, DEFAULT_MARGIN.floatValue()), vAlign.position(pageSize.getHeight(), DEFAULT_MARGIN.floatValue(), fontSize.floatValue())), label, font, fontSize, color);
        } catch (IOException e) {
            throw new TaskIOException("An error occurred writing the header or footer of the page.", e);
        }
    }

    public void write(PDPage page, Point2D position, String rawLabel, PDFont font, Double fontSize, Color color) throws TaskIOException, IOException {
        write(page, position, rawLabel, font, fontSize, toPDColor(color));
    }

    public static PDColor toPDColor(Color color) {
        float[] components = {color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f};
        return new PDColor(components, PDDeviceRGB.INSTANCE);
    }

    public void write(PDPage page, Point2D position, String rawLabel, PDFont font, Double fontSize, PDColor color) throws TaskIOException, IOException {
        write(page, position, rawLabel, font, fontSize, color, RenderingMode.FILL, false);
    }

    public void write(PDPage page, Point2D position, String rawLabel, PDFont font, Double fontSize, PDColor color, RenderingMode renderingMode, boolean fauxItalic) throws TaskIOException, IOException {
        Matrix textMatrix;
        String label = org.sejda.commons.util.StringUtils.normalizeWhitespace(rawLabel);
        List<TextWithFont> resolvedStringsToFonts = FontUtils.resolveFonts(StringUtils.shapeArabicIf(label), font, this.document);
        int offset = 0;
        PDRectangle pageSize = page.getMediaBox().rotate(page.getRotation());
        PDRectangle mediaSize = page.getMediaBox();
        PDRectangle cropSize = page.getCropBox();
        double cropOffsetX = cropSize.getLowerLeftX();
        double cropOffsetY = cropSize.getLowerLeftY();
        if (page.getRotation() == 90) {
            cropOffsetX = cropSize.getLowerLeftY();
            cropOffsetY = mediaSize.getUpperRightX() - cropSize.getUpperRightX();
        } else if (page.getRotation() == 180) {
            cropOffsetX = mediaSize.getUpperRightX() - cropSize.getUpperRightX();
            cropOffsetY = mediaSize.getUpperRightY() - cropSize.getUpperRightY();
        } else if (page.getRotation() == 270) {
            cropOffsetX = mediaSize.getUpperRightY() - cropSize.getUpperRightY();
            cropOffsetY = cropSize.getLowerLeftX();
        }
        LOG.trace("media: {} crop: {}", mediaSize, cropSize);
        LOG.trace("offsets: {}, {} and rotation {}", new Object[]{Double.valueOf(cropOffsetX), Double.valueOf(cropOffsetY), Integer.valueOf(page.getRotation())});
        Point point = new Point(((int) position.getX()) + ((int) cropOffsetX), ((int) position.getY()) + ((int) cropOffsetY));
        try {
            PDPageContentStream contentStream = new PDPageContentStream(this.document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            try {
                contentStream.beginText();
                contentStream.setTextRenderingMode(renderingMode);
                contentStream.setNonStrokingColor(color);
                for (TextWithFont stringAndFont : resolvedStringsToFonts) {
                    try {
                        PDFont resolvedFont = stringAndFont.getFont();
                        double resolvedFontSize = fontSize.doubleValue();
                        String resolvedLabel = stringAndFont.getText();
                        if (resolvedFont == null) {
                            throw new UnsupportedTextException("Unable to find suitable font for string \"" + org.sejda.commons.util.StringUtils.asUnicodes(resolvedLabel) + "\"", resolvedLabel);
                        }
                        for (String textFragment : FontUtils.resolveTextFragments(resolvedLabel, resolvedFont)) {
                            Point point2 = new Point(((int) point.getX()) + offset, (int) point.getY());
                            contentStream.setFont(resolvedFont, (float) resolvedFontSize);
                            if (page.getRotation() > 0) {
                                LOG.trace("Unrotated position {}", point2);
                                Point2D rotatedPosition = findPositionInRotatedPage(page.getRotation(), pageSize, point2);
                                LOG.trace("Will write string '{}' using font {} at position {}", new Object[]{textFragment, resolvedFont.getName(), rotatedPosition});
                                AffineTransform tx = AffineTransform.getTranslateInstance(rotatedPosition.getX(), rotatedPosition.getY());
                                tx.rotate(Math.toRadians(page.getRotation()));
                                textMatrix = new Matrix(tx);
                            } else {
                                LOG.trace("Will write string '{}' using font {} at position {}", new Object[]{textFragment, resolvedFont.getName(), point2});
                                textMatrix = new Matrix(AffineTransform.getTranslateInstance(point2.getX(), point2.getY()));
                            }
                            if (fauxItalic) {
                                AffineTransform at = AffineTransform.getShearInstance(0.35d, 0.0d);
                                textMatrix.concatenate(new Matrix(at));
                            }
                            contentStream.setTextMatrix(textMatrix);
                            LOG.trace("Text position {}", point2);
                            contentStream.showText(textFragment);
                            double textWidth = FontUtils.getSimpleStringWidth(textFragment, resolvedFont, fontSize.doubleValue());
                            offset = (int) (offset + textWidth);
                        }
                    } catch (IOException e) {
                        throw new TaskIOException("An error occurred writing text to the page.", e);
                    }
                }
                contentStream.setTextRenderingMode(RenderingMode.FILL);
                contentStream.endText();
                contentStream.close();
            } finally {
            }
        } catch (IOException e2) {
            throw new TaskIOException("An error occurred writing the header or footer of the page.", e2);
        }
    }

    public int getStringWidth(String rawLabel, PDFont font, float fontSize) throws TaskIOException, IOException {
        String label = org.sejda.commons.util.StringUtils.normalizeWhitespace(rawLabel);
        List<TextWithFont> resolvedStringsToFonts = FontUtils.resolveFonts(label, font, this.document);
        int offset = 0;
        for (TextWithFont stringAndFont : resolvedStringsToFonts) {
            try {
                PDFont resolvedFont = stringAndFont.getFont();
                String resolvedLabel = stringAndFont.getText();
                if (Objects.nonNull(resolvedFont)) {
                    double textWidth = FontUtils.getSimpleStringWidth(resolvedLabel, resolvedFont, fontSize);
                    offset = (int) (offset + textWidth);
                }
            } catch (IOException e) {
                throw new TaskIOException("An error occurred writing text to the page.", e);
            }
        }
        return offset;
    }

    private Point2D findPositionInRotatedPage(int rotation, PDRectangle pageSize, Point2D position) {
        LOG.debug("Found rotation {}", Integer.valueOf(rotation));
        AffineTransform transform = AffineTransform.getScaleInstance(1.0d, -1.0d);
        if (rotation == 90) {
            transform.translate(pageSize.getHeight(), 0.0d);
        }
        if (rotation == 180) {
            transform.translate(pageSize.getWidth(), -pageSize.getHeight());
        }
        if (rotation == 270) {
            transform.translate(0.0d, -pageSize.getWidth());
        }
        transform.rotate(Math.toRadians(-rotation));
        transform.scale(1.0d, -1.0d);
        return transform.transform(position, (Point2D) null);
    }

    public static void writeHeader(PDDocument doc, PDPage page, String text) throws TaskIOException {
        PageTextWriter writer = new PageTextWriter(doc);
        writer.write(page, HorizontalAlign.CENTER, VerticalAlign.TOP, text, PDType1Font.HELVETICA(), Double.valueOf(10.0d), Color.black);
    }
}
