package org.sejda.model.notification.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.sejda.model.task.NotifiableTaskMetadata;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/notification/event/TaskExecutionCompletedEvent.class */
public class TaskExecutionCompletedEvent extends AbstractNotificationEvent {
    private static final long serialVersionUID = -2839444329684682481L;
    private long executionTime;

    public TaskExecutionCompletedEvent(long executionTime, NotifiableTaskMetadata taskMetadata) {
        super(taskMetadata);
        this.executionTime = -1L;
        this.executionTime = executionTime;
    }

    public long getExecutionTime() {
        return this.executionTime;
    }

    @Override // org.sejda.model.notification.event.AbstractNotificationEvent
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("executionTime", this.executionTime).toString();
    }
}
