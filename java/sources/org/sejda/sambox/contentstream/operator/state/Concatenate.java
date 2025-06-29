package org.sejda.sambox.contentstream.operator.state;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/state/Concatenate.class */
public class Concatenate extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        if (arguments.size() < 6) {
            throw new MissingOperandException(operator, arguments);
        }
        if (!checkArrayTypesClass(arguments, COSNumber.class)) {
            return;
        }
        COSNumber a = (COSNumber) arguments.get(0);
        COSNumber b = (COSNumber) arguments.get(1);
        COSNumber c = (COSNumber) arguments.get(2);
        COSNumber d = (COSNumber) arguments.get(3);
        COSNumber e = (COSNumber) arguments.get(4);
        COSNumber f = (COSNumber) arguments.get(5);
        Matrix matrix = new Matrix(a.floatValue(), b.floatValue(), c.floatValue(), d.floatValue(), e.floatValue(), f.floatValue());
        getContext().getGraphicsState().getCurrentTransformationMatrix().concatenate(matrix);
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.CONCAT;
    }
}
