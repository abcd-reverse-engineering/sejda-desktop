package org.sejda.sambox.contentstream.operator.graphics;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/graphics/CurveToReplicateFinalPoint.class */
public final class CurveToReplicateFinalPoint extends GraphicsOperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> operands) throws IOException {
        if (operands.size() < 4) {
            throw new MissingOperandException(operator, operands);
        }
        if (!checkArrayTypesClass(operands, COSNumber.class)) {
            return;
        }
        COSNumber x1 = (COSNumber) operands.get(0);
        COSNumber y1 = (COSNumber) operands.get(1);
        COSNumber x3 = (COSNumber) operands.get(2);
        COSNumber y3 = (COSNumber) operands.get(3);
        Point2D.Float point1 = getContext().transformedPoint(x1.floatValue(), y1.floatValue());
        Point2D.Float point3 = getContext().transformedPoint(x3.floatValue(), y3.floatValue());
        getContext().curveTo(point1.x, point1.y, point3.x, point3.y, point3.x, point3.y);
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.CURVE_TO_REPLICATE_FINAL_POINT;
    }
}
