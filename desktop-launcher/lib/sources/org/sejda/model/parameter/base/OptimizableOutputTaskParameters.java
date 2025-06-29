package org.sejda.model.parameter.base;

import org.sejda.model.optimization.OptimizationPolicy;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/OptimizableOutputTaskParameters.class */
public interface OptimizableOutputTaskParameters extends TaskParameters {
    OptimizationPolicy getOptimizationPolicy();

    void setOptimizationPolicy(OptimizationPolicy optimizationPolicy);
}
