package org.sejda.sambox.contentstream.operator.graphics;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/graphics/CloseFillEvenOddAndStrokePath.class */
public final class CloseFillEvenOddAndStrokePath extends GraphicsOperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> operands) throws IOException {
        getContext().processOperator(OperatorName.CLOSE_PATH, operands);
        getContext().processOperator(OperatorName.FILL_EVEN_ODD_AND_STROKE, operands);
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.CLOSE_FILL_EVEN_ODD_AND_STROKE;
    }
}
