package org.sejda.sambox.contentstream.operator.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSFloat;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/text/NextLine.class */
public class NextLine extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        ArrayList<COSBase> args = new ArrayList<>(2);
        args.add(new COSFloat(0.0f));
        args.add(new COSFloat(-getContext().getGraphicsState().getTextState().getLeading()));
        getContext().processOperator(OperatorName.MOVE_TEXT, args);
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.NEXT_LINE;
    }
}
