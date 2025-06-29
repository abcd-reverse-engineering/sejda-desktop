package org.sejda.sambox.contentstream;

import java.awt.geom.Point2D;
import java.io.IOException;
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
import org.sejda.sambox.contentstream.operator.graphics.AppendRectangleToPath;
import org.sejda.sambox.contentstream.operator.graphics.BeginInlineImage;
import org.sejda.sambox.contentstream.operator.graphics.ClipEvenOddRule;
import org.sejda.sambox.contentstream.operator.graphics.ClipNonZeroRule;
import org.sejda.sambox.contentstream.operator.graphics.CloseAndStrokePath;
import org.sejda.sambox.contentstream.operator.graphics.CloseFillEvenOddAndStrokePath;
import org.sejda.sambox.contentstream.operator.graphics.CloseFillNonZeroAndStrokePath;
import org.sejda.sambox.contentstream.operator.graphics.ClosePath;
import org.sejda.sambox.contentstream.operator.graphics.CurveTo;
import org.sejda.sambox.contentstream.operator.graphics.CurveToReplicateFinalPoint;
import org.sejda.sambox.contentstream.operator.graphics.CurveToReplicateInitialPoint;
import org.sejda.sambox.contentstream.operator.graphics.DrawObject;
import org.sejda.sambox.contentstream.operator.graphics.EndPath;
import org.sejda.sambox.contentstream.operator.graphics.FillEvenOddAndStrokePath;
import org.sejda.sambox.contentstream.operator.graphics.FillEvenOddRule;
import org.sejda.sambox.contentstream.operator.graphics.FillNonZeroAndStrokePath;
import org.sejda.sambox.contentstream.operator.graphics.FillNonZeroRule;
import org.sejda.sambox.contentstream.operator.graphics.LegacyFillNonZeroRule;
import org.sejda.sambox.contentstream.operator.graphics.LineTo;
import org.sejda.sambox.contentstream.operator.graphics.MoveTo;
import org.sejda.sambox.contentstream.operator.graphics.ShadingFill;
import org.sejda.sambox.contentstream.operator.graphics.StrokePath;
import org.sejda.sambox.contentstream.operator.markedcontent.BeginMarkedContentSequence;
import org.sejda.sambox.contentstream.operator.markedcontent.BeginMarkedContentSequenceWithProperties;
import org.sejda.sambox.contentstream.operator.markedcontent.EndMarkedContentSequence;
import org.sejda.sambox.contentstream.operator.state.Concatenate;
import org.sejda.sambox.contentstream.operator.state.Restore;
import org.sejda.sambox.contentstream.operator.state.Save;
import org.sejda.sambox.contentstream.operator.state.SetFlatness;
import org.sejda.sambox.contentstream.operator.state.SetGraphicsStateParameters;
import org.sejda.sambox.contentstream.operator.state.SetLineCapStyle;
import org.sejda.sambox.contentstream.operator.state.SetLineDashPattern;
import org.sejda.sambox.contentstream.operator.state.SetLineJoinStyle;
import org.sejda.sambox.contentstream.operator.state.SetLineMiterLimit;
import org.sejda.sambox.contentstream.operator.state.SetLineWidth;
import org.sejda.sambox.contentstream.operator.state.SetMatrix;
import org.sejda.sambox.contentstream.operator.state.SetRenderingIntent;
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
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.graphics.image.PDImage;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/PDFGraphicsStreamEngine.class */
public abstract class PDFGraphicsStreamEngine extends PDFStreamEngine {
    private final PDPage page;

    public abstract void appendRectangle(Point2D point2D, Point2D point2D2, Point2D point2D3, Point2D point2D4) throws IOException;

    public abstract void drawImage(PDImage pDImage) throws IOException;

    public abstract void clip(int i) throws IOException;

    public abstract void moveTo(float f, float f2) throws IOException;

    public abstract void lineTo(float f, float f2) throws IOException;

    public abstract void curveTo(float f, float f2, float f3, float f4, float f5, float f6) throws IOException;

    public abstract Point2D getCurrentPoint() throws IOException;

    public abstract void closePath() throws IOException;

    public abstract void endPath() throws IOException;

    public abstract void strokePath() throws IOException;

    public abstract void fillPath(int i) throws IOException;

    public abstract void fillAndStrokePath(int i) throws IOException;

    public abstract void shadingFill(COSName cOSName) throws IOException;

    protected PDFGraphicsStreamEngine(PDPage page) {
        this.page = page;
        addOperator(new CloseFillNonZeroAndStrokePath());
        addOperator(new FillNonZeroAndStrokePath());
        addOperator(new CloseFillEvenOddAndStrokePath());
        addOperator(new FillEvenOddAndStrokePath());
        addOperator(new BeginInlineImage());
        addOperator(new BeginText());
        addOperator(new CurveTo());
        addOperator(new Concatenate());
        addOperator(new SetStrokingColorSpace());
        addOperator(new SetNonStrokingColorSpace());
        addOperator(new SetLineDashPattern());
        addOperator(new DrawObject());
        addOperator(new EndText());
        addOperator(new FillNonZeroRule());
        addOperator(new LegacyFillNonZeroRule());
        addOperator(new FillEvenOddRule());
        addOperator(new SetStrokingDeviceGrayColor());
        addOperator(new SetNonStrokingDeviceGrayColor());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new ClosePath());
        addOperator(new SetFlatness());
        addOperator(new SetLineJoinStyle());
        addOperator(new SetLineCapStyle());
        addOperator(new SetStrokingDeviceCMYKColor());
        addOperator(new SetNonStrokingDeviceCMYKColor());
        addOperator(new LineTo());
        addOperator(new MoveTo());
        addOperator(new SetLineMiterLimit());
        addOperator(new EndPath());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new AppendRectangleToPath());
        addOperator(new SetStrokingDeviceRGBColor());
        addOperator(new SetNonStrokingDeviceRGBColor());
        addOperator(new SetRenderingIntent());
        addOperator(new CloseAndStrokePath());
        addOperator(new StrokePath());
        addOperator(new SetStrokingColor());
        addOperator(new SetNonStrokingColor());
        addOperator(new SetStrokingColorN());
        addOperator(new SetNonStrokingColorN());
        addOperator(new ShadingFill());
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
        addOperator(new CurveToReplicateInitialPoint());
        addOperator(new SetLineWidth());
        addOperator(new ClipNonZeroRule());
        addOperator(new ClipEvenOddRule());
        addOperator(new CurveToReplicateFinalPoint());
        addOperator(new ShowTextLine());
        addOperator(new ShowTextLineAndSpace());
        addOperator(new BeginMarkedContentSequence());
        addOperator(new BeginMarkedContentSequenceWithProperties());
        addOperator(new EndMarkedContentSequence());
    }

    protected final PDPage getPage() {
        return this.page;
    }
}
