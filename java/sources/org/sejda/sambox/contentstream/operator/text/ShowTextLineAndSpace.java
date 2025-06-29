package org.sejda.sambox.contentstream.operator.text;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/text/ShowTextLineAndSpace.class */
public class ShowTextLineAndSpace extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        if (arguments.size() < 3) {
            throw new MissingOperandException(operator, arguments);
        }
        getContext().processOperator(OperatorName.SET_WORD_SPACING, arguments.subList(0, 1));
        getContext().processOperator(OperatorName.SET_CHAR_SPACING, arguments.subList(1, 2));
        getContext().processOperator(OperatorName.SHOW_TEXT_LINE, arguments.subList(2, 3));
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.SHOW_TEXT_LINE_AND_SPACE;
    }
}
