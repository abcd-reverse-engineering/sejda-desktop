package org.sejda.core.notification.context;

import java.util.List;
import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.AbstractNotificationEvent;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/context/EventListenerHoldingStrategy.class */
interface EventListenerHoldingStrategy {
    <T extends AbstractNotificationEvent> void add(EventListener<T> eventListener);

    <T extends AbstractNotificationEvent> void add(Class<T> cls, EventListener<T> eventListener);

    <T extends AbstractNotificationEvent> boolean remove(EventListener<T> eventListener);

    void clear();

    List<EventListener<? extends AbstractNotificationEvent>> get(AbstractNotificationEvent abstractNotificationEvent);

    int size();
}
