package org.sejda.model.parameter.base;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.input.PdfSource;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/SinglePdfSourceParameters.class */
abstract class SinglePdfSourceParameters extends AbstractPdfOutputParameters implements SinglePdfSourceTaskParameters {

    @Valid
    @NotNull
    private PdfSource<?> source;

    SinglePdfSourceParameters() {
    }

    public PdfSource<?> getSource() {
        return this.source;
    }

    public void setSource(PdfSource<?> source) {
        this.source = source;
    }

    @Override // org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.source).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SinglePdfSourceParameters)) {
            return false;
        }
        SinglePdfSourceParameters parameter = (SinglePdfSourceParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.source, parameter.getSource()).isEquals();
    }
}
