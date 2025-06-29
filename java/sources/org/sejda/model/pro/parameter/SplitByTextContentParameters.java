package org.sejda.model.pro.parameter;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.model.optimization.OptimizationPolicy;
import org.sejda.model.parameter.base.DiscardableOutlineTaskParameters;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.parameter.base.OptimizableOutputTaskParameters;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/SplitByTextContentParameters.class */
public class SplitByTextContentParameters extends MultiplePdfSourceMultipleOutputParameters implements OptimizableOutputTaskParameters, DiscardableOutlineTaskParameters {

    @NotNull
    private final TopLeftRectangularBox textArea;
    private String startsWith = "";
    private String endsWith = "";

    @NotNull
    private OptimizationPolicy optimizationPolicy = OptimizationPolicy.NO;
    private boolean discardOutline = false;

    public SplitByTextContentParameters(TopLeftRectangularBox textArea) {
        this.textArea = textArea;
    }

    public TopLeftRectangularBox getTextArea() {
        return this.textArea;
    }

    public String getStartsWith() {
        return this.startsWith;
    }

    public String getEndsWith() {
        return this.endsWith;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    public void setEndsWith(String endsWith) {
        this.endsWith = endsWith;
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

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append(this.textArea).append(this.startsWith).append(this.endsWith).toString();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.optimizationPolicy).append(this.discardOutline).append(this.textArea).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SplitByTextContentParameters)) {
            return false;
        }
        SplitByTextContentParameters parameter = (SplitByTextContentParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.optimizationPolicy, parameter.optimizationPolicy).append(this.discardOutline, parameter.discardOutline).append(this.textArea, parameter.textArea).append(this.startsWith, parameter.startsWith).append(this.endsWith, parameter.endsWith).isEquals();
    }
}
