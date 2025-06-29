package org.sejda.core.context;

import java.util.Map;
import org.sejda.core.notification.strategy.NotificationStrategy;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.task.Task;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/context/ConfigurationStrategy.class */
interface ConfigurationStrategy {
    Class<? extends NotificationStrategy> getNotificationStrategy();

    Map<Class<? extends TaskParameters>, Class<? extends Task>> getTasksMap();

    boolean isValidation();

    boolean isIgnoreXmlConfiguration();
}
