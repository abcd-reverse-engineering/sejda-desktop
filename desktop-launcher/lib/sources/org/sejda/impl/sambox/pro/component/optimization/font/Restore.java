package org.sejda.impl.sambox.pro.component.optimization.font;

import org.sejda.impl.sambox.pro.component.optimization.ContentStreamOptimizationContext;
import org.sejda.sambox.contentstream.operator.OperatorProcessorDecorator;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/Restore.class */
public class Restore extends OperatorProcessorDecorator {
    public Restore(ContentStreamOptimizationContext optimizationContext) {
        super(new org.sejda.sambox.contentstream.operator.state.Restore());
        setConsumer((operator, operands) -> {
            optimizationContext.fontSubsettingContext().restoreState();
        });
    }
}
