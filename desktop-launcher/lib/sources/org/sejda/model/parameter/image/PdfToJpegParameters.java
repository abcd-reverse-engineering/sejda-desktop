package org.sejda.model.parameter.image;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.image.ImageColorType;
import org.sejda.model.image.ImageType;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/image/PdfToJpegParameters.class */
public class PdfToJpegParameters extends AbstractPdfToMultipleImageParameters {

    @Max(100)
    @Min(0)
    private int quality;

    public PdfToJpegParameters(ImageColorType type) {
        super(type);
        this.quality = 100;
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToMultipleImageParameters
    public ImageType getOutputImageType() {
        return ImageType.JPEG;
    }

    public int getQuality() {
        return this.quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToMultipleImageParameters, org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.quality).toHashCode();
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToMultipleImageParameters, org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PdfToJpegParameters)) {
            return false;
        }
        PdfToJpegParameters parameter = (PdfToJpegParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.quality, parameter.quality).isEquals();
    }
}
