package org.sejda.model.pro.parameter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.scale.ScaleType;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/ScaleParameters.class */
public class ScaleParameters extends MultiplePdfSourceMultipleOutputParameters {

    @Positive
    public final double scale;

    @NotNull
    private ScaleType scaleType = ScaleType.CONTENT;

    public ScaleParameters(double scale) {
        this.scale = scale;
    }

    public ScaleType getScaleType() {
        return this.scaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.scale).append(this.scaleType).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScaleParameters)) {
            return false;
        }
        ScaleParameters parameter = (ScaleParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.scale, parameter.scale).append(this.scaleType, parameter.scaleType).isEquals();
    }
}
