package org.sejda.model.pro.parameter;

import jakarta.validation.constraints.NotNull;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.PdfVersion;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.pro.pdf.encryption.PdfEncryption;
import org.sejda.model.pro.validation.constraint.HasAPassword;

@HasAPassword
/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/EncryptParameters.class */
public class EncryptParameters extends MultiplePdfSourceMultipleOutputParameters {

    @NotNull
    private PdfEncryption encryptionAlgorithm;
    private String ownerPassword = "";
    private String userPassword = "";
    private final Set<PdfAccessPermission> permissions = EnumSet.noneOf(PdfAccessPermission.class);

    public EncryptParameters(PdfEncryption encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public String getOwnerPassword() {
        return this.ownerPassword;
    }

    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public PdfEncryption getEncryptionAlgorithm() {
        return this.encryptionAlgorithm;
    }

    public Set<PdfAccessPermission> getPermissions() {
        return Collections.unmodifiableSet(this.permissions);
    }

    public void clearPermissions() {
        this.permissions.clear();
    }

    public void addPermission(PdfAccessPermission permission) {
        this.permissions.add(permission);
    }

    @Override // org.sejda.model.parameter.base.AbstractPdfOutputParameters
    public PdfVersion getMinRequiredPdfVersion() {
        return PdfVersion.getMax(super.getMinRequiredPdfVersion(), this.encryptionAlgorithm.getMinVersion());
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.userPassword).append(this.ownerPassword).append(this.encryptionAlgorithm).append(this.permissions).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EncryptParameters)) {
            return false;
        }
        EncryptParameters parameter = (EncryptParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.userPassword, parameter.getUserPassword()).append(this.ownerPassword, parameter.getOwnerPassword()).append(this.encryptionAlgorithm, parameter.getEncryptionAlgorithm()).append(this.permissions, parameter.getPermissions()).isEquals();
    }
}
