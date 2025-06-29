package org.sejda.model.parameter.base;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.output.SingleTaskOutput;
import org.sejda.model.validation.constraint.ValidSingleOutput;

@ValidSingleOutput
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/MultiplePdfSourceSingleOutputParameters.class */
public abstract class MultiplePdfSourceSingleOutputParameters extends MultiplePdfSourceParameters implements SingleOutputTaskParameters {

    @Valid
    @NotNull
    private SingleTaskOutput output;

    @Override // org.sejda.model.parameter.base.TaskParameters, org.sejda.model.parameter.base.SingleOutputTaskParameters
    public SingleTaskOutput getOutput() {
        return this.output;
    }

    @Override // org.sejda.model.parameter.base.SingleOutputTaskParameters
    public void setOutput(SingleTaskOutput output) {
        this.output = output;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.output).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MultiplePdfSourceSingleOutputParameters)) {
            return false;
        }
        MultiplePdfSourceSingleOutputParameters parameter = (MultiplePdfSourceSingleOutputParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.output, parameter.output).isEquals();
    }
}
