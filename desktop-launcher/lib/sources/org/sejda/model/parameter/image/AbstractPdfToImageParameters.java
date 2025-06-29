package org.sejda.model.parameter.image;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.image.ImageColorType;
import org.sejda.model.image.ImageType;
import org.sejda.model.input.PdfSource;
import org.sejda.model.parameter.base.AbstractParameters;
import org.sejda.model.parameter.base.SinglePdfSourceTaskParameters;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/image/AbstractPdfToImageParameters.class */
public abstract class AbstractPdfToImageParameters extends AbstractParameters implements SinglePdfSourceTaskParameters, PdfToImageParameters {
    public static final int DEFAULT_DPI = 72;

    @Min(1)
    private int resolutionInDpi = 72;

    @NotNull
    private ImageColorType outputImageColorType;

    @Valid
    @NotNull
    private PdfSource<?> source;

    @NotNull
    public abstract ImageType getOutputImageType();

    @Override // org.sejda.model.parameter.base.SinglePdfSourceTaskParameters
    public PdfSource<?> getSource() {
        return this.source;
    }

    @Override // org.sejda.model.parameter.base.SinglePdfSourceTaskParameters
    public void setSource(PdfSource<?> source) {
        this.source = source;
    }

    AbstractPdfToImageParameters(ImageColorType outputImageColorType) {
        this.outputImageColorType = outputImageColorType;
    }

    @Override // org.sejda.model.parameter.image.PdfToImageParameters
    public ImageColorType getOutputImageColorType() {
        return this.outputImageColorType;
    }

    @Override // org.sejda.model.parameter.image.PdfToImageParameters
    public void setOutputImageColorType(ImageColorType outputImageColorType) {
        this.outputImageColorType = outputImageColorType;
    }

    @Override // org.sejda.model.parameter.image.PdfToImageParameters
    public int getResolutionInDpi() {
        return this.resolutionInDpi;
    }

    @Override // org.sejda.model.parameter.image.PdfToImageParameters
    public void setResolutionInDpi(int resolutionInDpi) {
        this.resolutionInDpi = resolutionInDpi;
    }

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.resolutionInDpi).append(this.outputImageColorType).append(getOutputImageType()).append(this.source).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractPdfToImageParameters)) {
            return false;
        }
        AbstractPdfToImageParameters parameter = (AbstractPdfToImageParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.resolutionInDpi, parameter.getResolutionInDpi()).append(this.outputImageColorType, parameter.getOutputImageColorType()).append(getOutputImageType(), parameter.getOutputImageType()).append(this.source, parameter.getSource()).isEquals();
    }
}
