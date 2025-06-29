package org.sejda.core.notification.context;

import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.AbstractNotificationEvent;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/context/GlobalNotificationContext.class */
public final class GlobalNotificationContext extends AbstractNotificationContext {
    @Override // org.sejda.core.notification.context.AbstractNotificationContext, org.sejda.core.notification.context.NotificationContext
    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    @Override // org.sejda.core.notification.context.AbstractNotificationContext, org.sejda.core.notification.context.NotificationContext
    public /* bridge */ /* synthetic */ void clearListeners() {
        super.clearListeners();
    }

    @Override // org.sejda.core.notification.context.AbstractNotificationContext, org.sejda.core.notification.context.NotificationContext
    public /* bridge */ /* synthetic */ boolean removeListener(EventListener eventListener) {
        return super.removeListener(eventListener);
    }

    @Override // org.sejda.core.notification.context.AbstractNotificationContext, org.sejda.core.notification.context.NotificationContext
    public /* bridge */ /* synthetic */ void addListener(Class cls, EventListener eventListener) {
        super.addListener(cls, eventListener);
    }

    @Override // org.sejda.core.notification.context.AbstractNotificationContext, org.sejda.core.notification.context.NotificationContext
    public /* bridge */ /* synthetic */ void addListener(EventListener eventListener) {
        super.addListener(eventListener);
    }

    @Override // org.sejda.core.notification.context.AbstractNotificationContext, org.sejda.core.notification.context.NotificationContext
    public /* bridge */ /* synthetic */ void notifyListeners(AbstractNotificationEvent abstractNotificationEvent) {
        super.notifyListeners(abstractNotificationEvent);
    }

    private GlobalNotificationContext() {
        super(new SimpleEventListenerHoldingStrategy());
    }

    public static NotificationContext getContext() {
        return GlobalNotificationContextHolder.NOTIFICATION_CONTEXT;
    }

    /* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/context/GlobalNotificationContext$GlobalNotificationContextHolder.class */
    private static final class GlobalNotificationContextHolder {
        static final GlobalNotificationContext NOTIFICATION_CONTEXT = new GlobalNotificationContext();

        private GlobalNotificationContextHolder() {
        }
    }
}
