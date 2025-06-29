package org.sejda.sambox.contentstream.operator.state;

import java.util.Iterator;
import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/state/SetLineDashPattern.class */
public class SetLineDashPattern extends OperatorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(SetLineDashPattern.class);

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws MissingOperandException {
        if (arguments.size() < 2) {
            throw new MissingOperandException(operator, arguments);
        }
        COSBase base0 = arguments.get(0);
        if (!(base0 instanceof COSArray)) {
            return;
        }
        COSArray dashArray = (COSArray) base0;
        COSBase base1 = arguments.get(1);
        if (!(base1 instanceof COSNumber)) {
            return;
        }
        int dashPhase = ((COSNumber) base1).intValue();
        Iterator<COSBase> it = dashArray.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            COSBase base = it.next();
            if (!(base instanceof COSNumber)) {
                LOG.warn("dash array has non number element " + base + ", ignored");
                dashArray = new COSArray();
                break;
            } else {
                COSNumber num = (COSNumber) base;
                if (num.floatValue() != 0.0f) {
                    break;
                }
            }
        }
        getContext().setLineDashPattern(dashArray, dashPhase);
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.SET_LINE_DASHPATTERN;
    }
}
