package org.sejda.core.notification.context;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/context/ThreadLocalNotificationContext.class */
public final class ThreadLocalNotificationContext {
    private static final ThreadLocal<? extends AbstractNotificationContext> THREAD_LOCAL_CONTEXT = new ThreadLocal<SimpleNotificationContext>() { // from class: org.sejda.core.notification.context.ThreadLocalNotificationContext.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public SimpleNotificationContext initialValue() {
            return new SimpleNotificationContext();
        }
    };

    private ThreadLocalNotificationContext() {
    }

    public static NotificationContext getContext() {
        return THREAD_LOCAL_CONTEXT.get();
    }

    /* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/context/ThreadLocalNotificationContext$SimpleNotificationContext.class */
    private static class SimpleNotificationContext extends AbstractNotificationContext {
        SimpleNotificationContext() {
            super(new SimpleEventListenerHoldingStrategy());
        }
    }
}
