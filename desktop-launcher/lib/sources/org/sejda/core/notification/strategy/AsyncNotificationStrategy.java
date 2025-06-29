package org.sejda.core.notification.strategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.AbstractNotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/strategy/AsyncNotificationStrategy.class */
public final class AsyncNotificationStrategy implements NotificationStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncNotificationStrategy.class);
    private static final ThreadLocal<ExecutorService> THREAD_LOCAL = new ThreadLocal<ExecutorService>() { // from class: org.sejda.core.notification.strategy.AsyncNotificationStrategy.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public ExecutorService initialValue() {
            return Executors.newSingleThreadExecutor();
        }
    };

    @Override // org.sejda.core.notification.strategy.NotificationStrategy
    public void notifyListener(EventListener listener, AbstractNotificationEvent event) {
        if (listener != null) {
            THREAD_LOCAL.get().execute(new NotifyRunnable(listener, event));
        }
    }

    /* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/strategy/AsyncNotificationStrategy$NotifyRunnable.class */
    private static final class NotifyRunnable implements Runnable {
        private final EventListener listener;
        private final AbstractNotificationEvent event;

        private NotifyRunnable(EventListener listener, AbstractNotificationEvent event) {
            this.listener = listener;
            this.event = event;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.listener.onEvent(this.event);
            } catch (RuntimeException e) {
                AsyncNotificationStrategy.LOG.error(String.format("An error occurred notifying event %s", this.event), e);
                throw e;
            }
        }
    }
}
