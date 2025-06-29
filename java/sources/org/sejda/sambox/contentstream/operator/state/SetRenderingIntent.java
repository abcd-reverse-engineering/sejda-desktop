package org.sejda.sambox.contentstream.operator.state;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.graphics.state.RenderingIntent;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/state/SetRenderingIntent.class */
public class SetRenderingIntent extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> operands) throws IOException {
        if (operands.isEmpty()) {
            throw new MissingOperandException(operator, operands);
        }
        COSBase base = operands.get(0);
        if (base instanceof COSName) {
            getContext().getGraphicsState().setRenderingIntent(RenderingIntent.fromString(((COSName) base).getName()));
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.SET_RENDERINGINTENT;
    }
}
