package org.sejda.impl.sambox.pro.component.optimization.font;

import java.util.List;
import org.sejda.impl.sambox.pro.component.optimization.ContentStreamOptimizationOperator;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/ShowText.class */
public class ShowText extends ContentStreamOptimizationOperator {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) {
        if (!arguments.isEmpty()) {
            COSBase cOSBase = arguments.get(0);
            if (cOSBase instanceof COSString) {
                COSString text = (COSString) cOSBase;
                optimizationContext().fontSubsettingContext().currentSubsettableFont().ifPresent(f -> {
                    f.addToSubset(text.getBytes());
                });
            }
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.SHOW_TEXT;
    }
}
