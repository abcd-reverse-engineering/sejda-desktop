package org.sejda.model.parameter.image;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.SejdaFileExtensions;
import org.sejda.model.image.ImageColorType;
import org.sejda.model.image.ImageType;
import org.sejda.model.image.TiffCompressionType;
import org.sejda.model.validation.constraint.SingleOutputAllowedExtensions;

@SingleOutputAllowedExtensions(extensions = {SejdaFileExtensions.TIFF_EXTENSION, SejdaFileExtensions.TIF_EXTENSION})
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/image/PdfToSingleTiffParameters.class */
public class PdfToSingleTiffParameters extends AbstractPdfToSingleImageParameters implements PdfToTiffParameters {

    @NotNull
    private TiffCompressionType compressionType;

    public PdfToSingleTiffParameters(ImageColorType outputImageColorType) {
        super(outputImageColorType);
        this.compressionType = TiffCompressionType.NONE;
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToImageParameters
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

    @Override // org.sejda.model.parameter.image.AbstractPdfToSingleImageParameters, org.sejda.model.parameter.image.AbstractPdfToImageParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.compressionType).toHashCode();
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToSingleImageParameters, org.sejda.model.parameter.image.AbstractPdfToImageParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (!(other instanceof PdfToSingleTiffParameters)) {
            return false;
        }
        PdfToSingleTiffParameters parameter = (PdfToSingleTiffParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.compressionType, parameter.getCompressionType()).isEquals();
    }
}
