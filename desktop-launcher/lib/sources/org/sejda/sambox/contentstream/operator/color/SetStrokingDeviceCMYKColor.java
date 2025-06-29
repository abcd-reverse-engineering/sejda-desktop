package org.sejda.sambox.contentstream.operator.color;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/color/SetStrokingDeviceCMYKColor.class */
public class SetStrokingDeviceCMYKColor extends SetStrokingColor {
    @Override // org.sejda.sambox.contentstream.operator.color.SetColor, org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        PDColorSpace cs = getContext().getResources().getColorSpace(COSName.DEVICECMYK);
        getContext().getGraphicsState().setStrokingColorSpace(cs);
        super.process(operator, arguments);
    }

    @Override // org.sejda.sambox.contentstream.operator.color.SetStrokingColor, org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.STROKING_COLOR_CMYK;
    }
}
