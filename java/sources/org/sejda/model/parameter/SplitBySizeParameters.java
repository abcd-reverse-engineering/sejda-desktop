package org.sejda.model.parameter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.optimization.OptimizationPolicy;
import org.sejda.model.parameter.base.DiscardableOutlineTaskParameters;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.parameter.base.OptimizableOutputTaskParameters;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/SplitBySizeParameters.class */
public class SplitBySizeParameters extends MultiplePdfSourceMultipleOutputParameters implements OptimizableOutputTaskParameters, DiscardableOutlineTaskParameters {

    @Min(1)
    private long sizeToSplitAt;

    @NotNull
    private OptimizationPolicy optimizationPolicy = OptimizationPolicy.NO;
    private boolean discardOutline = false;

    public SplitBySizeParameters(long sizeToSplitAt) {
        this.sizeToSplitAt = sizeToSplitAt;
    }

    public long getSizeToSplitAt() {
        return this.sizeToSplitAt;
    }

    @Override // org.sejda.model.parameter.base.OptimizableOutputTaskParameters
    public OptimizationPolicy getOptimizationPolicy() {
        return this.optimizationPolicy;
    }

    @Override // org.sejda.model.parameter.base.OptimizableOutputTaskParameters
    public void setOptimizationPolicy(OptimizationPolicy optimizationPolicy) {
        this.optimizationPolicy = optimizationPolicy;
    }

    @Override // org.sejda.model.parameter.base.DiscardableOutlineTaskParameters
    public boolean discardOutline() {
        return this.discardOutline;
    }

    @Override // org.sejda.model.parameter.base.DiscardableOutlineTaskParameters
    public void discardOutline(boolean discardOutline) {
        this.discardOutline = discardOutline;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.optimizationPolicy).append(this.sizeToSplitAt).append(this.discardOutline).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SplitBySizeParameters)) {
            return false;
        }
        SplitBySizeParameters parameter = (SplitBySizeParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.optimizationPolicy, parameter.getOptimizationPolicy()).append(this.sizeToSplitAt, parameter.getSizeToSplitAt()).append(this.discardOutline, parameter.discardOutline).isEquals();
    }
}
