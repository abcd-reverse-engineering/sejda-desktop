package org.sejda.sambox.contentstream.operator.color;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/color/SetNonStrokingDeviceGrayColor.class */
public class SetNonStrokingDeviceGrayColor extends SetNonStrokingColor {
    @Override // org.sejda.sambox.contentstream.operator.color.SetColor, org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        PDColorSpace cs = getContext().getResources().getColorSpace(COSName.DEVICEGRAY);
        getContext().getGraphicsState().setNonStrokingColorSpace(cs);
        super.process(operator, arguments);
    }

    @Override // org.sejda.sambox.contentstream.operator.color.SetNonStrokingColor, org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.NON_STROKING_GRAY;
    }
}
