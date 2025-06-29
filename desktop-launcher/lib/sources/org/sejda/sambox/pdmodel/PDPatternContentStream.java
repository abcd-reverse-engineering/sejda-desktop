package org.sejda.sambox.pdmodel;

import java.awt.Color;
import java.io.IOException;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.sejda.sambox.pdmodel.graphics.image.PDInlineImage;
import org.sejda.sambox.pdmodel.graphics.pattern.PDTilingPattern;
import org.sejda.sambox.pdmodel.graphics.shading.PDShading;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDPatternContentStream.class */
public final class PDPatternContentStream extends PDAbstractContentStream {
    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setTextRise(float f) throws IOException {
        super.setTextRise(f);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setRenderingMode(RenderingMode renderingMode) throws IOException {
        super.setRenderingMode(renderingMode);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setHorizontalScaling(float f) throws IOException {
        super.setHorizontalScaling(f);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setWordSpacing(float f) throws IOException {
        super.setWordSpacing(f);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setCharacterSpacing(float f) throws IOException {
        super.setCharacterSpacing(f);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream, java.io.Closeable, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() throws IOException {
        super.close();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void addComment(String str) throws IOException {
        super.addComment(str);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setGraphicsStateParameters(PDExtendedGraphicsState pDExtendedGraphicsState) throws IOException {
        super.setGraphicsStateParameters(pDExtendedGraphicsState);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void endMarkedContent() throws IOException {
        super.endMarkedContent();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void beginMarkedContent(COSName cOSName, PDPropertyList pDPropertyList) throws IOException {
        super.beginMarkedContent(cOSName, pDPropertyList);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void beginMarkedContent(COSName cOSName) throws IOException {
        super.beginMarkedContent(cOSName);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setMiterLimit(float f) throws IOException {
        super.setMiterLimit(f);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setLineDashPattern(float[] fArr, float f) throws IOException {
        super.setLineDashPattern(fArr, f);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setLineCapStyle(int i) throws IOException {
        super.setLineCapStyle(i);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setLineJoinStyle(int i) throws IOException {
        super.setLineJoinStyle(i);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setLineWidth(float f) throws IOException {
        super.setLineWidth(f);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void clipEvenOdd() throws IOException {
        super.clipEvenOdd();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void clip() throws IOException {
        super.clip();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void closePath() throws IOException {
        super.closePath();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void shadingFill(PDShading pDShading) throws IOException {
        super.shadingFill(pDShading);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void closeAndFillAndStrokeEvenOdd() throws IOException {
        super.closeAndFillAndStrokeEvenOdd();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void closeAndFillAndStroke() throws IOException {
        super.closeAndFillAndStroke();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void fillAndStrokeEvenOdd() throws IOException {
        super.fillAndStrokeEvenOdd();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void fillAndStroke() throws IOException {
        super.fillAndStroke();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void fillEvenOdd() throws IOException {
        super.fillEvenOdd();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void fill() throws IOException {
        super.fill();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void closeAndStroke() throws IOException {
        super.closeAndStroke();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void stroke() throws IOException {
        super.stroke();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void lineTo(float f, float f2) throws IOException {
        super.lineTo(f, f2);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void moveTo(float f, float f2) throws IOException {
        super.moveTo(f, f2);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void curveTo1(float f, float f2, float f3, float f4) throws IOException {
        super.curveTo1(f, f2, f3, f4);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void curveTo2(float f, float f2, float f3, float f4) throws IOException {
        super.curveTo2(f, f2, f3, f4);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void curveTo(float f, float f2, float f3, float f4, float f5, float f6) throws IOException {
        super.curveTo(f, f2, f3, f4, f5, f6);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void addRect(float f, float f2, float f3, float f4) throws IOException {
        super.addRect(f, f2, f3, f4);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setNonStrokingColor(float f) throws IOException {
        super.setNonStrokingColor(f);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    @Deprecated
    public /* bridge */ /* synthetic */ void setNonStrokingColor(int i) throws IOException {
        super.setNonStrokingColor(i);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setNonStrokingColor(float f, float f2, float f3, float f4) throws IOException {
        super.setNonStrokingColor(f, f2, f3, f4);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setNonStrokingColor(int i, int i2, int i3, int i4) throws IOException {
        super.setNonStrokingColor(i, i2, i3, i4);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    @Deprecated
    public /* bridge */ /* synthetic */ void setNonStrokingColor(int i, int i2, int i3) throws IOException {
        super.setNonStrokingColor(i, i2, i3);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setNonStrokingColor(float f, float f2, float f3) throws IOException {
        super.setNonStrokingColor(f, f2, f3);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setNonStrokingColor(Color color) throws IOException {
        super.setNonStrokingColor(color);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setNonStrokingColor(PDColor pDColor) throws IOException {
        super.setNonStrokingColor(pDColor);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setStrokingColor(float f) throws IOException {
        super.setStrokingColor(f);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setStrokingColor(float f, float f2, float f3, float f4) throws IOException {
        super.setStrokingColor(f, f2, f3, f4);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    @Deprecated
    public /* bridge */ /* synthetic */ void setStrokingColor(int i, int i2, int i3) throws IOException {
        super.setStrokingColor(i, i2, i3);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setStrokingColor(float f, float f2, float f3) throws IOException {
        super.setStrokingColor(f, f2, f3);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setStrokingColor(Color color) throws IOException {
        super.setStrokingColor(color);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setStrokingColor(PDColor pDColor) throws IOException {
        super.setStrokingColor(pDColor);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void restoreGraphicsState() throws IOException {
        super.restoreGraphicsState();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void saveGraphicsState() throws IOException {
        super.saveGraphicsState();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void transform(Matrix matrix) throws IOException {
        super.transform(matrix);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void drawForm(PDFormXObject pDFormXObject) throws IOException {
        super.drawForm(pDFormXObject);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void drawImage(PDInlineImage pDInlineImage, float f, float f2, float f3, float f4) throws IOException {
        super.drawImage(pDInlineImage, f, f2, f3, f4);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void drawImage(PDInlineImage pDInlineImage, float f, float f2) throws IOException {
        super.drawImage(pDInlineImage, f, f2);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void drawImage(PDImageXObject pDImageXObject, Matrix matrix) throws IOException {
        super.drawImage(pDImageXObject, matrix);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void drawImage(PDImageXObject pDImageXObject, float f, float f2, float f3, float f4) throws IOException {
        super.drawImage(pDImageXObject, f, f2, f3, f4);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void drawImage(PDImageXObject pDImageXObject, float f, float f2) throws IOException {
        super.drawImage(pDImageXObject, f, f2);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setTextMatrix(Matrix matrix) throws IOException {
        super.setTextMatrix(matrix);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void newLineAtOffset(float f, float f2) throws IOException {
        super.newLineAtOffset(f, f2);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void newLine() throws IOException {
        super.newLine();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setLeading(float f) throws IOException {
        super.setLeading(f);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void showText(String str) throws IOException {
        super.showText(str);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void showTextWithPositioning(Object[] objArr) throws IOException {
        super.showTextWithPositioning(objArr);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void setFont(PDFont pDFont, float f) throws IOException {
        super.setFont(pDFont, f);
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void endText() throws IOException {
        super.endText();
    }

    @Override // org.sejda.sambox.pdmodel.PDAbstractContentStream
    public /* bridge */ /* synthetic */ void beginText() throws IOException {
        super.beginText();
    }

    public PDPatternContentStream(PDTilingPattern pattern) throws IOException {
        super(null, pattern.getContentStream().createOutputStream(), pattern.getResources());
    }
}
