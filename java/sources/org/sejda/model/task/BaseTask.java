package org.sejda.model.task;

import org.sejda.model.exception.TaskException;
import org.sejda.model.parameter.base.TaskParameters;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/task/BaseTask.class */
public abstract class BaseTask<T extends TaskParameters> implements Task<T> {
    private TaskExecutionContext executionContext;

    @Override // org.sejda.model.task.Task
    public void before(T parameters, TaskExecutionContext context) throws TaskException {
        this.executionContext = context;
    }

    protected TaskExecutionContext executionContext() {
        return this.executionContext;
    }
}
