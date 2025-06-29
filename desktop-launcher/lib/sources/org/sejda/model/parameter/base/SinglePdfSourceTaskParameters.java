package org.sejda.model.parameter.base;

import org.sejda.model.input.PdfSource;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/SinglePdfSourceTaskParameters.class */
public interface SinglePdfSourceTaskParameters extends TaskParameters {
    PdfSource<?> getSource();

    void setSource(PdfSource<?> source);
}
