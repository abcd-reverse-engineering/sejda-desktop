package org.sejda.model.parameter.base;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.output.SingleOrMultipleTaskOutput;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/MultipleSourceMultipleOutputParameters.class */
public class MultipleSourceMultipleOutputParameters extends MultipleSourceParameters implements SingleOrMultipleOutputTaskParameters {
    private String outputPrefix = "";

    @Valid
    @NotNull
    private SingleOrMultipleTaskOutput output;

    @Override // org.sejda.model.parameter.base.PrefixableTaskParameters
    public String getOutputPrefix() {
        return this.outputPrefix;
    }

    @Override // org.sejda.model.parameter.base.PrefixableTaskParameters
    public void setOutputPrefix(String outputPrefix) {
        this.outputPrefix = outputPrefix;
    }

    @Override // org.sejda.model.parameter.base.TaskParameters, org.sejda.model.parameter.base.SingleOutputTaskParameters
    public SingleOrMultipleTaskOutput getOutput() {
        return this.output;
    }

    @Override // org.sejda.model.parameter.base.SingleOrMultipleOutputTaskParameters
    public void setOutput(SingleOrMultipleTaskOutput output) {
        this.output = output;
    }

    @Override // org.sejda.model.parameter.base.MultipleSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.outputPrefix).append(this.output).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultipleSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MultipleSourceMultipleOutputParameters)) {
            return false;
        }
        MultipleSourceMultipleOutputParameters parameter = (MultipleSourceMultipleOutputParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.outputPrefix, parameter.outputPrefix).append(this.output, parameter.output).isEquals();
    }
}
