package org.sejda.model.parameter.base;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.sejda.model.output.ExistingOutputPolicy;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/AbstractParameters.class */
public abstract class AbstractParameters implements TaskParameters {

    @NotNull
    private ExistingOutputPolicy existingOutputPolicy = ExistingOutputPolicy.FAIL;
    private boolean lenient = false;

    @Override // org.sejda.model.parameter.base.TaskParameters
    public ExistingOutputPolicy getExistingOutputPolicy() {
        return this.existingOutputPolicy;
    }

    @Override // org.sejda.model.parameter.base.TaskParameters
    public void setExistingOutputPolicy(ExistingOutputPolicy existingOutputPolicy) {
        this.existingOutputPolicy = existingOutputPolicy;
    }

    @Override // org.sejda.model.parameter.base.TaskParameters
    public boolean isLenient() {
        return this.lenient;
    }

    @Override // org.sejda.model.parameter.base.TaskParameters
    public void setLenient(boolean lenient) {
        this.lenient = lenient;
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.existingOutputPolicy).append(this.lenient).toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractParameters)) {
            return false;
        }
        AbstractParameters parameter = (AbstractParameters) other;
        return new EqualsBuilder().append(this.existingOutputPolicy, parameter.existingOutputPolicy).append(this.lenient, parameter.lenient).isEquals();
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
