package org.sejda.core.notification.strategy;

import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.AbstractNotificationEvent;

@FunctionalInterface
/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/strategy/NotificationStrategy.class */
public interface NotificationStrategy {
    void notifyListener(EventListener eventListener, AbstractNotificationEvent abstractNotificationEvent);
}
