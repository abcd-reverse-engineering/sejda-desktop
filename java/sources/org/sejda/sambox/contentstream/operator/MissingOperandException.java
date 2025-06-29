package org.sejda.sambox.contentstream.operator;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/MissingOperandException.class */
public final class MissingOperandException extends IOException {
    public MissingOperandException(Operator operator, List<COSBase> operands) {
        super("Operator " + operator.getName() + " has too few operands: " + operands);
    }
}
