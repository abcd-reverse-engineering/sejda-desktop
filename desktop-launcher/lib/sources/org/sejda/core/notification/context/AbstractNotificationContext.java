package org.sejda.core.notification.context;

import java.lang.reflect.InvocationTargetException;
import org.sejda.core.context.DefaultSejdaConfiguration;
import org.sejda.core.notification.strategy.NotificationStrategy;
import org.sejda.core.notification.strategy.SyncNotificationStrategy;
import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.AbstractNotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/context/AbstractNotificationContext.class */
abstract class AbstractNotificationContext implements NotificationContext {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractNotificationContext.class);
    private final EventListenerHoldingStrategy holder;
    private final NotificationStrategy strategy = getStrategy();

    protected AbstractNotificationContext(EventListenerHoldingStrategy holder) {
        this.holder = holder;
    }

    @Override // org.sejda.core.notification.context.NotificationContext
    public void notifyListeners(AbstractNotificationEvent event) {
        synchronized (this.holder) {
            if (this.holder.size() > 0) {
                for (EventListener<? extends AbstractNotificationEvent> listener : this.holder.get(event)) {
                    this.strategy.notifyListener(listener, event);
                }
            }
        }
    }

    @Override // org.sejda.core.notification.context.NotificationContext
    public <T extends AbstractNotificationEvent> void addListener(EventListener<T> listener) {
        synchronized (this.holder) {
            LOG.trace("Adding event listener: {}", listener);
            this.holder.add(listener);
        }
    }

    @Override // org.sejda.core.notification.context.NotificationContext
    public <T extends AbstractNotificationEvent> void addListener(Class<T> eventClass, EventListener<T> listener) {
        synchronized (this.holder) {
            LOG.trace("Adding event listener {} on event {}", listener, eventClass);
            this.holder.add(eventClass, listener);
        }
    }

    @Override // org.sejda.core.notification.context.NotificationContext
    public <T extends AbstractNotificationEvent> boolean removeListener(EventListener<T> listener) {
        boolean zRemove;
        synchronized (this.holder) {
            LOG.trace("Removing event listener: {}", listener);
            zRemove = this.holder.remove(listener);
        }
        return zRemove;
    }

    @Override // org.sejda.core.notification.context.NotificationContext
    public void clearListeners() {
        synchronized (this.holder) {
            this.holder.clear();
        }
    }

    @Override // org.sejda.core.notification.context.NotificationContext
    public int size() {
        return this.holder.size();
    }

    private NotificationStrategy getStrategy() {
        try {
            return DefaultSejdaConfiguration.getInstance().getNotificationStrategy().getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (IllegalAccessException e) {
            LOG.warn("Unable to access constructor for the configured NotificationStrategy. Default strategy will be used.", e);
            return new SyncNotificationStrategy();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            LOG.warn("An error occur while instantiating a new NotificationStrategy. Default strategy will be used.", e2);
            return new SyncNotificationStrategy();
        }
    }
}
