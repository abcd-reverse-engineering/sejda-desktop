package org.sejda.sambox.contentstream.operator.text;

import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/text/SetWordSpacing.class */
public class SetWordSpacing extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) {
        if (!arguments.isEmpty()) {
            COSBase base = arguments.get(0);
            if (base instanceof COSNumber) {
                getContext().getGraphicsState().getTextState().setWordSpacing(((COSNumber) base).floatValue());
            }
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.SET_WORD_SPACING;
    }
}
