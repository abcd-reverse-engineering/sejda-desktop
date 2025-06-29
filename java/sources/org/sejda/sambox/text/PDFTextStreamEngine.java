package org.sejda.sambox.text;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.fontbox.util.BoundingBox;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.contentstream.PDFStreamEngine;
import org.sejda.sambox.contentstream.operator.DrawObject;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColorN;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColorSpace;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingDeviceCMYKColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingDeviceGrayColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingDeviceRGBColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColorN;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColorSpace;
import org.sejda.sambox.contentstream.operator.color.SetStrokingDeviceCMYKColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingDeviceGrayColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingDeviceRGBColor;
import org.sejda.sambox.contentstream.operator.state.Concatenate;
import org.sejda.sambox.contentstream.operator.state.Restore;
import org.sejda.sambox.contentstream.operator.state.Save;
import org.sejda.sambox.contentstream.operator.state.SetGraphicsStateParameters;
import org.sejda.sambox.contentstream.operator.state.SetMatrix;
import org.sejda.sambox.contentstream.operator.text.BeginText;
import org.sejda.sambox.contentstream.operator.text.EndText;
import org.sejda.sambox.contentstream.operator.text.MoveText;
import org.sejda.sambox.contentstream.operator.text.MoveTextSetLeading;
import org.sejda.sambox.contentstream.operator.text.NextLine;
import org.sejda.sambox.contentstream.operator.text.SetCharSpacing;
import org.sejda.sambox.contentstream.operator.text.SetFontAndSize;
import org.sejda.sambox.contentstream.operator.text.SetTextHorizontalScaling;
import org.sejda.sambox.contentstream.operator.text.SetTextLeading;
import org.sejda.sambox.contentstream.operator.text.SetTextRenderingMode;
import org.sejda.sambox.contentstream.operator.text.SetTextRise;
import org.sejda.sambox.contentstream.operator.text.SetWordSpacing;
import org.sejda.sambox.contentstream.operator.text.ShowText;
import org.sejda.sambox.contentstream.operator.text.ShowTextAdjusted;
import org.sejda.sambox.contentstream.operator.text.ShowTextLine;
import org.sejda.sambox.contentstream.operator.text.ShowTextLineAndSpace;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.PDCIDFont;
import org.sejda.sambox.pdmodel.font.PDCIDFontType2;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDFontDescriptor;
import org.sejda.sambox.pdmodel.font.PDTrueTypeFont;
import org.sejda.sambox.pdmodel.font.PDType0Font;
import org.sejda.sambox.pdmodel.font.PDType3Font;
import org.sejda.sambox.pdmodel.font.encoding.GlyphList;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.state.PDGraphicsState;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;
import org.sejda.sambox.util.Matrix;
import org.sejda.sambox.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/text/PDFTextStreamEngine.class */
public class PDFTextStreamEngine extends PDFStreamEngine {
    private static final Logger LOG = LoggerFactory.getLogger(PDFTextStreamEngine.class);
    private int pageRotation;
    private PDRectangle cropBox;
    private Matrix translateMatrix;
    private static final GlyphList GLYPHLIST;
    private final Map<COSDictionary, Float> fontHeightMap = new WeakHashMap();

    static {
        try {
            InputStream input = GlyphList.class.getResourceAsStream("/org/sejda/sambox/resources/glyphlist/additional.txt");
            RequireUtils.requireNotNullArg(input, "Unable to load org/sejda/sambox/resources/glyphlist/additional.txt");
            GLYPHLIST = new GlyphList(GlyphList.getAdobeGlyphList(), input);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public PDFTextStreamEngine() throws IOException {
        addOperator(new BeginText());
        addOperator(new Concatenate());
        addOperator(new DrawObject());
        addOperator(new EndText());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new NextLine());
        addOperator(new SetCharSpacing());
        addOperator(new MoveText());
        addOperator(new MoveTextSetLeading());
        addOperator(new SetFontAndSize());
        addOperator(new ShowText());
        addOperator(new ShowTextAdjusted());
        addOperator(new SetTextLeading());
        addOperator(new SetMatrix());
        addOperator(new SetTextRenderingMode());
        addOperator(new SetTextRise());
        addOperator(new SetWordSpacing());
        addOperator(new SetTextHorizontalScaling());
        addOperator(new ShowTextLine());
        addOperator(new ShowTextLineAndSpace());
        addOperator(new SetStrokingColorSpace());
        addOperator(new SetNonStrokingColorSpace());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new SetStrokingColor());
        addOperator(new SetStrokingDeviceRGBColor());
        addOperator(new SetStrokingDeviceCMYKColor());
        addOperator(new SetStrokingDeviceGrayColor());
        addOperator(new SetStrokingColorN());
        addOperator(new SetNonStrokingColor());
        addOperator(new SetNonStrokingDeviceRGBColor());
        addOperator(new SetNonStrokingDeviceCMYKColor());
        addOperator(new SetNonStrokingDeviceGrayColor());
        addOperator(new SetNonStrokingColorN());
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void processPage(PDPage page) throws IOException {
        this.pageRotation = page.getRotation();
        this.cropBox = page.getCropBox();
        if (this.cropBox.getLowerLeftX() == 0.0f && this.cropBox.getLowerLeftY() == 0.0f) {
            this.translateMatrix = null;
        } else {
            this.translateMatrix = Matrix.getTranslateInstance(-this.cropBox.getLowerLeftX(), -this.cropBox.getLowerLeftY());
        }
        super.processPage(page);
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    protected void showGlyph(Matrix textRenderingMatrix, PDFont font, int code2, Vector displacement) throws IOException {
        Matrix translatedTextRenderingMatrix;
        PDGraphicsState state = getGraphicsState();
        Matrix ctm = state.getCurrentTransformationMatrix();
        float fontSize = state.getTextState().getFontSize();
        float horizontalScaling = state.getTextState().getHorizontalScaling() / 100.0f;
        Matrix textMatrix = getTextMatrix();
        float displacementX = displacement.getX();
        if (font.isVertical()) {
            displacementX = font.getWidth(code2) / 1000.0f;
            TrueTypeFont ttf = null;
            if (font instanceof PDTrueTypeFont) {
                ttf = ((PDTrueTypeFont) font).getTrueTypeFont();
            } else if (font instanceof PDType0Font) {
                PDCIDFont cidFont = ((PDType0Font) font).getDescendantFont();
                if (cidFont instanceof PDCIDFontType2) {
                    ttf = ((PDCIDFontType2) cidFont).getTrueTypeFont();
                }
            }
            if (ttf != null && ttf.getUnitsPerEm() != 1000) {
                displacementX *= 1000.0f / ttf.getUnitsPerEm();
            }
        }
        float tx = displacementX * fontSize * horizontalScaling;
        float ty = displacement.getY() * fontSize;
        Matrix td = Matrix.getTranslateInstance(tx, ty);
        Matrix nextTextRenderingMatrix = td.multiply(textMatrix).multiply(ctm);
        float nextX = nextTextRenderingMatrix.getTranslateX();
        float nextY = nextTextRenderingMatrix.getTranslateY();
        float dxDisplay = nextX - textRenderingMatrix.getTranslateX();
        Float fontHeight = this.fontHeightMap.get(font.getCOSObject());
        if (fontHeight == null) {
            fontHeight = Float.valueOf(computeFontHeight(font));
            this.fontHeightMap.put(font.getCOSObject(), fontHeight);
        }
        float dyDisplay = fontHeight.floatValue() * textRenderingMatrix.getScalingFactorY();
        float glyphSpaceToTextSpaceFactor = 0.001f;
        if (font instanceof PDType3Font) {
            glyphSpaceToTextSpaceFactor = font.getFontMatrix().getScaleX();
        }
        float spaceWidthText = 0.0f;
        try {
            spaceWidthText = font.getSpaceWidth() * glyphSpaceToTextSpaceFactor;
        } catch (Throwable exception) {
            LOG.warn(exception.getMessage(), exception);
        }
        if (spaceWidthText == 0.0f) {
            float spaceWidthText2 = font.getAverageFontWidth() * glyphSpaceToTextSpaceFactor;
            spaceWidthText = spaceWidthText2 * 0.8f;
        }
        if (spaceWidthText == 0.0f) {
            spaceWidthText = 1.0f;
        }
        float spaceWidthDisplay = spaceWidthText * textRenderingMatrix.getScalingFactorX();
        String unicode = font.toUnicode(code2, GLYPHLIST);
        if (unicode == null) {
            char c = (char) code2;
            unicode = String.valueOf(c);
        }
        if (this.translateMatrix == null) {
            translatedTextRenderingMatrix = textRenderingMatrix;
        } else {
            translatedTextRenderingMatrix = Matrix.concatenate(this.translateMatrix, textRenderingMatrix);
            nextX -= this.cropBox.getLowerLeftX();
            nextY -= this.cropBox.getLowerLeftY();
        }
        PDColor color = null;
        RenderingMode renderingMode = state.getTextState().getRenderingMode();
        if (renderingMode.isFill()) {
            color = state.getNonStrokingColor();
        } else if (renderingMode.isStroke()) {
            color = state.getStrokingColor();
        }
        processTextPosition(new TextPosition(this.pageRotation, this.cropBox.getWidth(), this.cropBox.getHeight(), translatedTextRenderingMatrix, nextX, nextY, Math.abs(dyDisplay), dxDisplay, Math.abs(spaceWidthDisplay), unicode, new int[]{code2}, font, fontSize, (int) (fontSize * textMatrix.getScalingFactorX()), color, renderingMode));
    }

    protected float computeFontHeight(PDFont font) throws IOException {
        float height;
        BoundingBox bbox = font.getBoundingBox();
        if (bbox.getLowerLeftY() < -32768.0f) {
            bbox.setLowerLeftY(-(bbox.getLowerLeftY() + 65536.0f));
        }
        float glyphHeight = bbox.getHeight() / 2.0f;
        PDFontDescriptor fontDescriptor = font.getFontDescriptor();
        if (fontDescriptor != null) {
            float capHeight = fontDescriptor.getCapHeight();
            if (Float.compare(capHeight, 0.0f) != 0 && (capHeight < glyphHeight || Float.compare(glyphHeight, 0.0f) == 0)) {
                glyphHeight = capHeight;
            }
            float ascent = fontDescriptor.getAscent();
            float descent = fontDescriptor.getDescent();
            if (capHeight > ascent && ascent > 0.0f && descent < 0.0f && ((ascent - descent) / 2.0f < glyphHeight || Float.compare(glyphHeight, 0.0f) == 0)) {
                glyphHeight = (ascent - descent) / 2.0f;
            }
        }
        if (font instanceof PDType3Font) {
            height = font.getFontMatrix().transformPoint(0.0f, glyphHeight).y;
        } else {
            height = glyphHeight / 1000.0f;
        }
        return height;
    }

    protected void processTextPosition(TextPosition text) {
    }
}
