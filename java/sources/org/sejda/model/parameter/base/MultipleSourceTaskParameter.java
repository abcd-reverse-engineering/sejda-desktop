package org.sejda.model.parameter.base;

import java.util.List;
import org.sejda.model.input.Source;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/MultipleSourceTaskParameter.class */
public interface MultipleSourceTaskParameter {
    void addSource(Source<?> input);

    List<Source<?>> getSourceList();
}
