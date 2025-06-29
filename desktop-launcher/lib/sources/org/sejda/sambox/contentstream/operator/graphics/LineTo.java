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

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/graphics/LineTo.class */
public class LineTo extends GraphicsOperatorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(LineTo.class);

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
        if (getContext().getCurrentPoint() == null) {
            LOG.warn("LineTo (" + pos.x + "," + pos.y + ") without initial MoveTo");
            getContext().moveTo(pos.x, pos.y);
        } else {
            getContext().lineTo(pos.x, pos.y);
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.LINE_TO;
    }
}
