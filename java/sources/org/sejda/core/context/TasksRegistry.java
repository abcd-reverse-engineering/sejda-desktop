package org.sejda.core.context;

import java.util.Map;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.task.Task;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/context/TasksRegistry.class */
interface TasksRegistry {
    Class<? extends Task> getTask(Class<? extends TaskParameters> cls);

    void addTask(Class<? extends TaskParameters> cls, Class<? extends Task> cls2);

    Map<Class<? extends TaskParameters>, Class<? extends Task>> getTasks();
}
