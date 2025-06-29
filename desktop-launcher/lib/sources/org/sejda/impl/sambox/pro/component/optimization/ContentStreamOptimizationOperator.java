package org.sejda.impl.sambox.pro.component.optimization;

import org.sejda.sambox.contentstream.operator.OperatorProcessor;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/ContentStreamOptimizationOperator.class */
public abstract class ContentStreamOptimizationOperator extends OperatorProcessor {
    private ContentStreamOptimizationContext optimizationContext;

    public ContentStreamOptimizationContext optimizationContext() {
        return this.optimizationContext;
    }

    public void setOptimizationContext(ContentStreamOptimizationContext optimizationContext) {
        this.optimizationContext = optimizationContext;
    }
}
