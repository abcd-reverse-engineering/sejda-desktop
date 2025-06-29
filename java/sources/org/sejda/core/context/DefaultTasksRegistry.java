package org.sejda.core.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/context/DefaultTasksRegistry.class */
class DefaultTasksRegistry implements TasksRegistry {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultTasksRegistry.class);
    private final Map<Class<? extends TaskParameters>, Class<? extends Task>> tasksMap = new HashMap();

    DefaultTasksRegistry() {
    }

    @Override // org.sejda.core.context.TasksRegistry
    public Class<? extends Task> getTask(Class<? extends TaskParameters> parametersClass) {
        Class<? extends Task> retVal = this.tasksMap.get(parametersClass);
        if (retVal == null) {
            LOG.info("Unable to find a match for the input parameter class {}, searching for an assignable one", parametersClass);
            retVal = findNearestTask(parametersClass);
        }
        return retVal;
    }

    private Class<? extends Task> findNearestTask(Class<? extends TaskParameters> parametersClass) {
        for (Map.Entry<Class<? extends TaskParameters>, Class<? extends Task>> entry : this.tasksMap.entrySet()) {
            if (entry.getKey().isAssignableFrom(parametersClass)) {
                return entry.getValue();
            }
        }
        LOG.warn("Unable to find an assignable match for the input parameter class {}", parametersClass);
        return null;
    }

    @Override // org.sejda.core.context.TasksRegistry
    public void addTask(Class<? extends TaskParameters> parameterClass, Class<? extends Task> taskClass) {
        synchronized (this.tasksMap) {
            this.tasksMap.put(parameterClass, taskClass);
        }
    }

    @Override // org.sejda.core.context.TasksRegistry
    public Map<Class<? extends TaskParameters>, Class<? extends Task>> getTasks() {
        return Collections.unmodifiableMap(this.tasksMap);
    }
}
