package org.sejda.sambox.contentstream.operator.markedcontent;

import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/markedcontent/EndMarkedContentSequence.class */
public class EndMarkedContentSequence extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) {
        getContext().endMarkedContentSequence();
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.END_MARKED_CONTENT;
    }
}
