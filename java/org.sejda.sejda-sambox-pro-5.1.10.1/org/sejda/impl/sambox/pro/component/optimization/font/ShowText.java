package org.sejda.impl.sambox.pro.component.optimization.font;

import java.util.List;
import org.sejda.impl.sambox.pro.component.optimization.ContentStreamOptimizationOperator;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/ShowText.class */
public class ShowText extends ContentStreamOptimizationOperator {
    public void process(Operator operator, List<COSBase> arguments) {
        if (!arguments.isEmpty()) {
            COSString cOSString = arguments.get(0);
            if (cOSString instanceof COSString) {
                COSString text = cOSString;
                optimizationContext().fontSubsettingContext().currentSubsettableFont().ifPresent(f -> {
                    f.addToSubset(text.getBytes());
                });
            }
        }
    }

    public String getName() {
        return "Tj";
    }
}
