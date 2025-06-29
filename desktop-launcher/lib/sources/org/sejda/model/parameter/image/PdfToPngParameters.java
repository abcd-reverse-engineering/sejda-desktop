package org.sejda.model.parameter.image;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.sejda.model.image.ImageColorType;
import org.sejda.model.image.ImageType;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/image/PdfToPngParameters.class */
public class PdfToPngParameters extends AbstractPdfToMultipleImageParameters {
    public PdfToPngParameters(ImageColorType type) {
        super(type);
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToMultipleImageParameters
    public ImageType getOutputImageType() {
        return ImageType.PNG;
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToMultipleImageParameters, org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PdfToPngParameters)) {
            return false;
        }
        return new EqualsBuilder().appendSuper(super.equals(other)).isEquals();
    }
}
