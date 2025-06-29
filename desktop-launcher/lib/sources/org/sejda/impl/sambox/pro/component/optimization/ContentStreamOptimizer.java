package org.sejda.impl.sambox.pro.component.optimization;

import org.sejda.commons.util.RequireUtils;
import org.sejda.impl.sambox.component.ContentStreamProcessor;
import org.sejda.impl.sambox.component.optimization.ResourcesHitter;
import org.sejda.sambox.contentstream.operator.state.Concatenate;
import org.sejda.sambox.contentstream.operator.state.Restore;
import org.sejda.sambox.contentstream.operator.state.Save;
import org.sejda.sambox.contentstream.operator.state.SetMatrix;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/ContentStreamOptimizer.class */
public class ContentStreamOptimizer extends ContentStreamProcessor {
    private final ContentStreamOptimizationContext context;

    public ContentStreamOptimizer(ContentStreamOptimizationContext context) {
        RequireUtils.requireNotNullArg(context, "Optimization context cannot be null");
        this.context = context;
        addOperator(new Concatenate());
        addOperator(new ResourcesHitter.SetGraphicState());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new SetMatrix());
    }

    public final void addOptimizationOperator(ContentStreamOptimizationOperator op) {
        op.setOptimizationContext(this.context);
        addOperator(op);
    }

    public final boolean addOptimizationOperatorIfAbsent(ContentStreamOptimizationOperator op) {
        op.setOptimizationContext(this.context);
        return addOperatorIfAbsent(op);
    }
}
