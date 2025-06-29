package org.sejda.model.parameter.base;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.pdf.PdfVersion;
import org.sejda.model.validation.constraint.ValidPdfVersion;

@ValidPdfVersion
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/AbstractPdfOutputParameters.class */
public abstract class AbstractPdfOutputParameters extends AbstractParameters {
    private boolean compress = true;
    private PdfVersion version;

    public boolean isCompress() {
        return this.compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public PdfVersion getVersion() {
        return this.version;
    }

    public void setVersion(PdfVersion version) {
        this.version = version;
    }

    public PdfVersion getMinRequiredPdfVersion() {
        return isCompress() ? PdfVersion.VERSION_1_5 : PdfVersion.VERSION_1_0;
    }

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.compress).append(this.version).append(getOutput()).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractPdfOutputParameters)) {
            return false;
        }
        AbstractPdfOutputParameters parameter = (AbstractPdfOutputParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.compress, parameter.isCompress()).append(this.version, parameter.getVersion()).append(getOutput(), parameter.getOutput()).isEquals();
    }
}
