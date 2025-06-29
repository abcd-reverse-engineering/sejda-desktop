package org.sejda.sambox.contentstream.operator.text;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/text/ShowTextAdjusted.class */
public class ShowTextAdjusted extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        if (!arguments.isEmpty()) {
            COSBase base = arguments.get(0);
            if ((base instanceof COSArray) && Objects.nonNull(getContext().getTextMatrix())) {
                getContext().showTextStrings((COSArray) base);
            }
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.SHOW_TEXT_ADJUSTED;
    }
}
