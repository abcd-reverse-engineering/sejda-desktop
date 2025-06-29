package org.sejda.model.parameter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.model.optimization.OptimizationPolicy;
import org.sejda.model.parameter.base.DiscardableOutlineTaskParameters;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.parameter.base.OptimizableOutputTaskParameters;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/ExtractByOutlineParameters.class */
public class ExtractByOutlineParameters extends MultiplePdfSourceMultipleOutputParameters implements OptimizableOutputTaskParameters, DiscardableOutlineTaskParameters {

    @Min(1)
    private int level;
    private String matchingTitleRegEx;

    @NotNull
    private OptimizationPolicy optimizationPolicy = OptimizationPolicy.NO;
    private boolean discardOutline = false;
    private boolean includePageAfter = false;

    public ExtractByOutlineParameters(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public String getMatchingTitleRegEx() {
        return this.matchingTitleRegEx;
    }

    public void setMatchingTitleRegEx(String matchingTitleRegEx) {
        this.matchingTitleRegEx = matchingTitleRegEx;
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

    public boolean isIncludePageAfter() {
        return this.includePageAfter;
    }

    public void setIncludePageAfter(boolean includePageAfter) {
        this.includePageAfter = includePageAfter;
    }

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("level", this.level).append("matchingTitleRegEx", this.matchingTitleRegEx).append("includePageAfter", this.includePageAfter).toString();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.optimizationPolicy).append(this.discardOutline).append(this.level).append(this.matchingTitleRegEx).append(this.includePageAfter).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExtractByOutlineParameters)) {
            return false;
        }
        ExtractByOutlineParameters parameter = (ExtractByOutlineParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.optimizationPolicy, parameter.optimizationPolicy).append(this.discardOutline, parameter.discardOutline).append(this.level, parameter.getLevel()).append(this.matchingTitleRegEx, parameter.getMatchingTitleRegEx()).append(this.includePageAfter, parameter.isIncludePageAfter()).isEquals();
    }
}
