package org.sejda.sambox.contentstream.operator.graphics;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/graphics/StrokePath.class */
public final class StrokePath extends GraphicsOperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> operands) throws IOException {
        getContext().strokePath();
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return "S";
    }
}
