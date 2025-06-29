package org.sejda.core.context;

import org.sejda.core.notification.strategy.NotificationStrategy;
import org.sejda.model.exception.TaskException;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.task.Task;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/context/SejdaConfiguration.class */
public interface SejdaConfiguration {
    Class<? extends NotificationStrategy> getNotificationStrategy();

    Task<? extends TaskParameters> getTask(TaskParameters taskParameters) throws TaskException;

    Class<? extends Task> getTaskClass(TaskParameters taskParameters) throws TaskException;

    boolean isValidation();

    boolean isValidationIgnoringXmlConfiguration();
}
