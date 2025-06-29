package org.sejda.sambox.contentstream.operator.color;

import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/color/SetStrokingColor.class */
public class SetStrokingColor extends SetColor {
    @Override // org.sejda.sambox.contentstream.operator.color.SetColor
    public PDColor getColor() {
        return getContext().getGraphicsState().getStrokingColor();
    }

    @Override // org.sejda.sambox.contentstream.operator.color.SetColor
    protected void setColor(PDColor color) {
        getContext().getGraphicsState().setStrokingColor(color);
    }

    @Override // org.sejda.sambox.contentstream.operator.color.SetColor
    public PDColorSpace getColorSpace() {
        return getContext().getGraphicsState().getStrokingColorSpace();
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.STROKING_COLOR;
    }
}
