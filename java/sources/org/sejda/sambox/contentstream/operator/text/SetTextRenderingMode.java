package org.sejda.sambox.contentstream.operator.text;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/text/SetTextRenderingMode.class */
public class SetTextRenderingMode extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        if (arguments.isEmpty()) {
            throw new MissingOperandException(operator, arguments);
        }
        COSBase base0 = arguments.get(0);
        if (base0 instanceof COSNumber) {
            COSNumber mode = (COSNumber) base0;
            int val = mode.intValue();
            if (val >= 0 && val < RenderingMode.values().length) {
                getContext().getGraphicsState().getTextState().setRenderingMode(RenderingMode.fromInt(val));
            }
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.SET_TEXT_RENDERINGMODE;
    }
}
