package org.sejda.sambox.contentstream.operator.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSNumber;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/text/MoveTextSetLeading.class */
public class MoveTextSetLeading extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        if (arguments.size() < 2) {
            throw new MissingOperandException(operator, arguments);
        }
        COSBase base1 = arguments.get(1);
        if (base1 instanceof COSNumber) {
            COSNumber y = (COSNumber) base1;
            ArrayList<COSBase> args = new ArrayList<>();
            args.add(new COSFloat(-y.floatValue()));
            getContext().processOperator(OperatorName.SET_TEXT_LEADING, args);
            getContext().processOperator(OperatorName.MOVE_TEXT, arguments);
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return "TD";
    }
}
