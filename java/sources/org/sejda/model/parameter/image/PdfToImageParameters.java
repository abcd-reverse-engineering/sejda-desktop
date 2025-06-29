package org.sejda.model.parameter.image;

import org.sejda.model.image.ImageColorType;
import org.sejda.model.parameter.base.TaskParameters;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/image/PdfToImageParameters.class */
public interface PdfToImageParameters extends TaskParameters {
    ImageColorType getOutputImageColorType();

    void setOutputImageColorType(ImageColorType outputImageColorType);

    int getResolutionInDpi();

    void setResolutionInDpi(int resolutionInDpi);
}
