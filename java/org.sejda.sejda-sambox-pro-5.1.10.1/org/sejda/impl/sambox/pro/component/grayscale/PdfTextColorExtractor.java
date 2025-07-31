package org.sejda.impl.sambox.pro.component.grayscale;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColorN;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColorSpace;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingDeviceCMYKColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingDeviceGrayColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingDeviceRGBColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColorSpace;
import org.sejda.sambox.contentstream.operator.color.SetStrokingDeviceCMYKColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingDeviceGrayColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingDeviceRGBColor;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.text.PDFTextStreamEngine;
import org.sejda.sambox.text.TextPosition;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/grayscale/PdfTextColorExtractor.class */
public class PdfTextColorExtractor extends PDFTextStreamEngine {
    private Set<Integer> colors = new HashSet();

    public PdfTextColorExtractor() throws IOException {
        addOperator(new SetStrokingColorSpace());
        addOperator(new SetStrokingColor());
        addOperator(new SetStrokingDeviceCMYKColor());
        addOperator(new SetStrokingDeviceGrayColor());
        addOperator(new SetStrokingDeviceRGBColor());
        addOperator(new SetNonStrokingColor());
        addOperator(new SetNonStrokingColorN());
        addOperator(new SetNonStrokingColorSpace());
        addOperator(new SetNonStrokingDeviceCMYKColor());
        addOperator(new SetNonStrokingDeviceGrayColor());
        addOperator(new SetNonStrokingDeviceRGBColor());
    }

    protected void processTextPosition(TextPosition text) {
        PDColor color = getGraphicsState().getNonStrokingColor();
        try {
            this.colors.add(Integer.valueOf(color.toRGB()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Integer> getColors() {
        return this.colors;
    }
}
