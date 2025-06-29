package org.sejda.model.parameter.base;

import org.sejda.model.output.ExistingOutputPolicy;
import org.sejda.model.output.TaskOutput;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/TaskParameters.class */
public interface TaskParameters {
    TaskOutput getOutput();

    ExistingOutputPolicy getExistingOutputPolicy();

    void setExistingOutputPolicy(ExistingOutputPolicy existingOutputPolicy);

    boolean isLenient();

    void setLenient(boolean lenient);
}
