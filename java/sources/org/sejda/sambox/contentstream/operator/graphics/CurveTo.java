package org.sejda.sambox.contentstream.operator.graphics;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/graphics/CurveTo.class */
public class CurveTo extends GraphicsOperatorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(CurveTo.class);

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> operands) throws IOException {
        if (operands.size() < 6) {
            throw new MissingOperandException(operator, operands);
        }
        if (!checkArrayTypesClass(operands, COSNumber.class)) {
            return;
        }
        COSNumber x1 = (COSNumber) operands.get(0);
        COSNumber y1 = (COSNumber) operands.get(1);
        COSNumber x2 = (COSNumber) operands.get(2);
        COSNumber y2 = (COSNumber) operands.get(3);
        COSNumber x3 = (COSNumber) operands.get(4);
        COSNumber y3 = (COSNumber) operands.get(5);
        Point2D.Float point1 = getContext().transformedPoint(x1.floatValue(), y1.floatValue());
        Point2D.Float point2 = getContext().transformedPoint(x2.floatValue(), y2.floatValue());
        Point2D.Float point3 = getContext().transformedPoint(x3.floatValue(), y3.floatValue());
        if (getContext().getCurrentPoint() == null) {
            LOG.warn("curveTo (" + point3.x + "," + point3.y + ") without initial MoveTo");
            getContext().moveTo(point3.x, point3.y);
        } else {
            getContext().curveTo(point1.x, point1.y, point2.x, point2.y, point3.x, point3.y);
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.CURVE_TO;
    }
}
