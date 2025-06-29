package org.sejda.model.notification;

import org.sejda.model.notification.event.AbstractNotificationEvent;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/notification/EventListener.class */
public interface EventListener<T extends AbstractNotificationEvent> {
    void onEvent(T event);
}
