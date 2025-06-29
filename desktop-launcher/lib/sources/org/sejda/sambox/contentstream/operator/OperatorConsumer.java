package org.sejda.sambox.contentstream.operator;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.cos.COSBase;

@FunctionalInterface
/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/OperatorConsumer.class */
public interface OperatorConsumer {
    public static final OperatorConsumer NO_OP = (operator, operands) -> {
    };

    void apply(Operator operator, List<COSBase> list) throws IOException;
}
