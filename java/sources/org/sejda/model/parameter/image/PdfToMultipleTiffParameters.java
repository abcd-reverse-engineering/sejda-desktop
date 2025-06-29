package org.sejda.model.parameter.image;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.image.ImageColorType;
import org.sejda.model.image.ImageType;
import org.sejda.model.image.TiffCompressionType;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/image/PdfToMultipleTiffParameters.class */
public class PdfToMultipleTiffParameters extends AbstractPdfToMultipleImageParameters implements PdfToTiffParameters {

    @NotNull
    private TiffCompressionType compressionType;

    public PdfToMultipleTiffParameters(ImageColorType outputImageColorType) {
        this(outputImageColorType, TiffCompressionType.NONE);
    }

    public PdfToMultipleTiffParameters(ImageColorType outputImageColorType, TiffCompressionType compressionType) {
        super(outputImageColorType);
        this.compressionType = TiffCompressionType.NONE;
        this.compressionType = compressionType;
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToMultipleImageParameters
    public ImageType getOutputImageType() {
        return ImageType.TIFF;
    }

    @Override // org.sejda.model.parameter.image.PdfToTiffParameters
    public TiffCompressionType getCompressionType() {
        return this.compressionType;
    }

    public void setCompressionType(TiffCompressionType compressionType) {
        this.compressionType = compressionType;
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToMultipleImageParameters, org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.compressionType).toHashCode();
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToMultipleImageParameters, org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (!(other instanceof PdfToMultipleTiffParameters)) {
            return false;
        }
        PdfToMultipleTiffParameters parameter = (PdfToMultipleTiffParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.compressionType, parameter.getCompressionType()).isEquals();
    }
}
