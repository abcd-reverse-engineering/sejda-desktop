package org.sejda.model.notification.event;

import org.sejda.model.task.NotifiableTaskMetadata;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/notification/event/TaskExecutionStartedEvent.class */
public class TaskExecutionStartedEvent extends AbstractNotificationEvent {
    private static final long serialVersionUID = -8143994205216959322L;

    public TaskExecutionStartedEvent(NotifiableTaskMetadata taskMetadata) {
        super(taskMetadata);
    }
}
