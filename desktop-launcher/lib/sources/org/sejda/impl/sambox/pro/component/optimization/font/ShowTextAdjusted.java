package org.sejda.impl.sambox.pro.component.optimization.font;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.sejda.impl.sambox.pro.component.optimization.ContentStreamOptimizationOperator;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/ShowTextAdjusted.class */
public class ShowTextAdjusted extends ContentStreamOptimizationOperator {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) {
        if (!arguments.isEmpty()) {
            COSBase base = arguments.get(0);
            if (base instanceof COSArray) {
                optimizationContext().fontSubsettingContext().currentSubsettableFont().ifPresent(f -> {
                    Stream streamFilter = ((COSArray) base).stream().filter(v -> {
                        return v instanceof COSString;
                    });
                    Class<COSString> cls = COSString.class;
                    Objects.requireNonNull(COSString.class);
                    Stream map = streamFilter.map((v1) -> {
                        return r1.cast(v1);
                    }).map((v0) -> {
                        return v0.getBytes();
                    });
                    Objects.requireNonNull(f);
                    map.forEach(f::addToSubset);
                });
            }
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.SHOW_TEXT_ADJUSTED;
    }
}
