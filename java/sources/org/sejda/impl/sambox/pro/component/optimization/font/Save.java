package org.sejda.impl.sambox.pro.component.optimization.font;

import org.sejda.impl.sambox.pro.component.optimization.ContentStreamOptimizationContext;
import org.sejda.sambox.contentstream.operator.OperatorProcessorDecorator;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/Save.class */
public class Save extends OperatorProcessorDecorator {
    public Save(ContentStreamOptimizationContext optimizationContext) {
        super(new org.sejda.sambox.contentstream.operator.state.Save());
        setConsumer((operator, operands) -> {
            optimizationContext.fontSubsettingContext().saveState();
        });
    }
}
