package org.sejda.model.pro.parameter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/ConvertToGrayscaleParameters.class */
public class ConvertToGrayscaleParameters extends MultiplePdfSourceMultipleOutputParameters {
    private boolean convertImages = true;
    private boolean convertTextToBlack = false;
    private boolean compressImages = false;

    public boolean isConvertImages() {
        return this.convertImages;
    }

    public void setConvertImages(boolean convertImages) {
        this.convertImages = convertImages;
    }

    public boolean isConvertTextToBlack() {
        return this.convertTextToBlack;
    }

    public void setConvertTextToBlack(boolean convertTextToBlack) {
        this.convertTextToBlack = convertTextToBlack;
    }

    public boolean isCompressImages() {
        return this.compressImages;
    }

    public void setCompressImages(boolean compressImages) {
        this.compressImages = compressImages;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.convertImages).append(this.convertTextToBlack).append(this.compressImages).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConvertToGrayscaleParameters)) {
            return false;
        }
        ConvertToGrayscaleParameters parameter = (ConvertToGrayscaleParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(isConvertImages(), parameter.isConvertImages()).append(isConvertTextToBlack(), parameter.isConvertTextToBlack()).append(isCompressImages(), parameter.isCompressImages()).isEquals();
    }
}
