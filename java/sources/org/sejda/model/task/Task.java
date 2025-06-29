package org.sejda.model.task;

import org.sejda.model.exception.TaskException;
import org.sejda.model.parameter.base.TaskParameters;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/task/Task.class */
public interface Task<T extends TaskParameters> {
    void before(T parameters, TaskExecutionContext context) throws TaskException;

    void execute(T parameters) throws TaskException;

    void after();
}
