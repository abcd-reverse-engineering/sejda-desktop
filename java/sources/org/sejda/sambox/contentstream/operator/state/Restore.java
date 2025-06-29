package org.sejda.sambox.contentstream.operator.state;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/state/Restore.class */
public class Restore extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        if (getContext().getGraphicsStackSize() > 1) {
            getContext().restoreGraphicsState();
            return;
        }
        throw new EmptyGraphicsStackException();
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.RESTORE;
    }
}
