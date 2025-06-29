package org.sejda.model.notification.event;

import org.sejda.model.task.NotifiableTaskMetadata;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/notification/event/TaskExecutionWarningEvent.class */
public class TaskExecutionWarningEvent extends AbstractNotificationEvent {
    private static final long serialVersionUID = -8919940675758916451L;
    private String warning;

    public TaskExecutionWarningEvent(String warning, NotifiableTaskMetadata taskMetadata) {
        super(taskMetadata);
        this.warning = warning;
    }

    public String getWarning() {
        return this.warning;
    }
}
