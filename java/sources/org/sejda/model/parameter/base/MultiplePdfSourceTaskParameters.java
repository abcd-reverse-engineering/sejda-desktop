package org.sejda.model.parameter.base;

import java.util.List;
import org.sejda.model.input.PdfSource;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/MultiplePdfSourceTaskParameters.class */
public interface MultiplePdfSourceTaskParameters extends TaskParameters {
    void addSource(PdfSource<?> input);

    List<PdfSource<?>> getSourceList();
}
