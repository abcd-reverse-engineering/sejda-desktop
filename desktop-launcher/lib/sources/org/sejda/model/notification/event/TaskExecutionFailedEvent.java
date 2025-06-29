package org.sejda.model.notification.event;

import org.sejda.model.task.NotifiableTaskMetadata;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/notification/event/TaskExecutionFailedEvent.class */
public class TaskExecutionFailedEvent extends AbstractNotificationEvent {
    private static final long serialVersionUID = -8919940675758916451L;
    private Exception failingCause;

    public TaskExecutionFailedEvent(Exception failingCause, NotifiableTaskMetadata taskMetadata) {
        super(taskMetadata);
        this.failingCause = failingCause;
    }

    public Exception getFailingCause() {
        return this.failingCause;
    }
}
