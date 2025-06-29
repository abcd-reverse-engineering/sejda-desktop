package org.sejda.model.notification.event;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.sejda.model.task.NotifiableTaskMetadata;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/notification/event/AbstractNotificationEvent.class */
public abstract class AbstractNotificationEvent implements NotificationEvent, Serializable {
    private static final long serialVersionUID = 3392179202226082364L;
    private Long eventTimestamp = Long.valueOf(System.currentTimeMillis());
    private NotifiableTaskMetadata notifiableTaskMetadata;

    AbstractNotificationEvent(NotifiableTaskMetadata notifiableTaskMetadata) {
        this.notifiableTaskMetadata = notifiableTaskMetadata;
    }

    @Override // org.sejda.model.notification.event.NotificationEvent
    public Long getEventTimestamp() {
        return this.eventTimestamp;
    }

    public NotifiableTaskMetadata getNotifiableTaskMetadata() {
        return this.notifiableTaskMetadata;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("eventTimestamp", this.eventTimestamp).append("taskMetadata", this.notifiableTaskMetadata).toString();
    }
}
