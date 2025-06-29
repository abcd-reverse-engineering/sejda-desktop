package org.sejda.sambox.contentstream.operator.graphics;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/graphics/MoveTo.class */
public final class MoveTo extends GraphicsOperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> operands) throws IOException {
        if (operands.size() < 2) {
            throw new MissingOperandException(operator, operands);
        }
        COSBase base0 = operands.get(0);
        if (!(base0 instanceof COSNumber)) {
            return;
        }
        COSNumber x = (COSNumber) base0;
        COSBase base1 = operands.get(1);
        if (!(base1 instanceof COSNumber)) {
            return;
        }
        COSNumber y = (COSNumber) base1;
        Point2D.Float pos = getContext().transformedPoint(x.floatValue(), y.floatValue());
        getContext().moveTo(pos.x, pos.y);
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.MOVE_TO;
    }
}
