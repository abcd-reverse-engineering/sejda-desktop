package org.sejda.model.parameter.base;

import org.sejda.model.output.SingleTaskOutput;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/SingleOutputTaskParameters.class */
public interface SingleOutputTaskParameters extends TaskParameters {
    SingleTaskOutput getOutput();

    void setOutput(SingleTaskOutput output);
}
