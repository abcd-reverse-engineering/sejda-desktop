package org.sejda.core.notification.strategy;

import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.AbstractNotificationEvent;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/strategy/SyncNotificationStrategy.class */
public final class SyncNotificationStrategy implements NotificationStrategy {
    @Override // org.sejda.core.notification.strategy.NotificationStrategy
    public void notifyListener(EventListener listener, AbstractNotificationEvent event) {
        if (listener != null) {
            listener.onEvent(event);
        }
    }
}
