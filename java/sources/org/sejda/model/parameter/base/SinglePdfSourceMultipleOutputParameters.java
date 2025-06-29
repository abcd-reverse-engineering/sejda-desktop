package org.sejda.model.parameter.base;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.input.PdfSource;
import org.sejda.model.output.MultipleTaskOutput;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/SinglePdfSourceMultipleOutputParameters.class */
public abstract class SinglePdfSourceMultipleOutputParameters extends SinglePdfSourceParameters implements MultipleOutputTaskParameters {
    private String outputPrefix = "";

    @Valid
    @NotNull
    private MultipleTaskOutput output;

    @Override // org.sejda.model.parameter.base.SinglePdfSourceParameters, org.sejda.model.parameter.base.SinglePdfSourceTaskParameters
    public /* bridge */ /* synthetic */ void setSource(PdfSource source) {
        super.setSource(source);
    }

    @Override // org.sejda.model.parameter.base.SinglePdfSourceParameters, org.sejda.model.parameter.base.SinglePdfSourceTaskParameters
    public /* bridge */ /* synthetic */ PdfSource getSource() {
        return super.getSource();
    }

    @Override // org.sejda.model.parameter.base.PrefixableTaskParameters
    public String getOutputPrefix() {
        return this.outputPrefix;
    }

    @Override // org.sejda.model.parameter.base.PrefixableTaskParameters
    public void setOutputPrefix(String outputPrefix) {
        this.outputPrefix = outputPrefix;
    }

    @Override // org.sejda.model.parameter.base.TaskParameters, org.sejda.model.parameter.base.SingleOutputTaskParameters
    public MultipleTaskOutput getOutput() {
        return this.output;
    }

    @Override // org.sejda.model.parameter.base.MultipleOutputTaskParameters
    public void setOutput(MultipleTaskOutput output) {
        this.output = output;
    }

    @Override // org.sejda.model.parameter.base.SinglePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.outputPrefix).append(this.output).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.SinglePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SinglePdfSourceMultipleOutputParameters)) {
            return false;
        }
        SinglePdfSourceMultipleOutputParameters parameter = (SinglePdfSourceMultipleOutputParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.outputPrefix, parameter.outputPrefix).append(this.output, parameter.output).isEquals();
    }
}
