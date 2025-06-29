package org.sejda.core.notification.context;

import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.AbstractNotificationEvent;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/context/NotificationContext.class */
public interface NotificationContext {
    <T extends AbstractNotificationEvent> void addListener(EventListener<T> eventListener);

    <T extends AbstractNotificationEvent> void addListener(Class<T> cls, EventListener<T> eventListener);

    <T extends AbstractNotificationEvent> boolean removeListener(EventListener<T> eventListener);

    void clearListeners();

    void notifyListeners(AbstractNotificationEvent abstractNotificationEvent);

    int size();
}
