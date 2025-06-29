package org.sejda.sambox.contentstream.operator.text;

import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/text/MoveText.class */
public class MoveText extends OperatorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(MoveText.class);

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws MissingOperandException {
        if (arguments.size() < 2) {
            throw new MissingOperandException(operator, arguments);
        }
        Matrix textLineMatrix = getContext().getTextLineMatrix();
        if (textLineMatrix == null) {
            LOG.warn("TextLineMatrix is null, " + getName() + " operator will be ignored");
            return;
        }
        COSBase base0 = arguments.get(0);
        COSBase base1 = arguments.get(1);
        if (!(base0 instanceof COSNumber)) {
            return;
        }
        COSNumber x = (COSNumber) base0;
        if (!(base1 instanceof COSNumber)) {
            return;
        }
        COSNumber y = (COSNumber) base1;
        Matrix matrix = new Matrix(1.0f, 0.0f, 0.0f, 1.0f, x.floatValue(), y.floatValue());
        textLineMatrix.concatenate(matrix);
        getContext().setTextMatrix(textLineMatrix.m587clone());
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.MOVE_TEXT;
    }
}
