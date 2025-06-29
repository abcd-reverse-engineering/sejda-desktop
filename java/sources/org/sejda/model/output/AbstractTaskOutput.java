package org.sejda.model.output;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.encryption.NoEncryptionAtRest;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/output/AbstractTaskOutput.class */
public abstract class AbstractTaskOutput implements TaskOutput {
    private EncryptionAtRestPolicy encryptionAtRestPolicy = NoEncryptionAtRest.INSTANCE;

    @Override // org.sejda.model.output.TaskOutput
    public EncryptionAtRestPolicy getEncryptionAtRestPolicy() {
        return this.encryptionAtRestPolicy;
    }

    @Override // org.sejda.model.output.TaskOutput
    public void setEncryptionAtRestPolicy(EncryptionAtRestPolicy encryptionAtRestSecurity) {
        this.encryptionAtRestPolicy = encryptionAtRestSecurity;
    }

    public String toString() {
        return new ToStringBuilder(this).append(getEncryptionAtRestPolicy()).toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getEncryptionAtRestPolicy()).toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractTaskOutput)) {
            return false;
        }
        AbstractTaskOutput output = (AbstractTaskOutput) other;
        return new EqualsBuilder().append(this.encryptionAtRestPolicy, output.encryptionAtRestPolicy).isEquals();
    }
}
