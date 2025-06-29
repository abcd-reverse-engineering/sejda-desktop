package org.sejda.core.notification.context;

import java.util.List;
import org.sejda.commons.collection.ListValueMap;
import org.sejda.core.support.util.ReflectionUtils;
import org.sejda.model.exception.NotificationContextException;
import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.AbstractNotificationEvent;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/context/SimpleEventListenerHoldingStrategy.class */
class SimpleEventListenerHoldingStrategy implements EventListenerHoldingStrategy {
    private final ListValueMap<Class<? extends AbstractNotificationEvent>, EventListener<? extends AbstractNotificationEvent>> listeners = new ListValueMap<>();

    SimpleEventListenerHoldingStrategy() {
    }

    @Override // org.sejda.core.notification.context.EventListenerHoldingStrategy
    public <T extends AbstractNotificationEvent> void add(EventListener<T> listener) throws SecurityException {
        Class<T> eventClass = getListenerEventClass(listener);
        this.listeners.put(eventClass, listener);
    }

    @Override // org.sejda.core.notification.context.EventListenerHoldingStrategy
    public <T extends AbstractNotificationEvent> void add(Class<T> eventClass, EventListener<T> listener) {
        this.listeners.put(eventClass, listener);
    }

    @Override // org.sejda.core.notification.context.EventListenerHoldingStrategy
    public <T extends AbstractNotificationEvent> boolean remove(EventListener<T> listener) throws SecurityException {
        Class<T> eventClass = getListenerEventClass(listener);
        return this.listeners.remove(eventClass, listener);
    }

    private <T extends AbstractNotificationEvent> Class<T> getListenerEventClass(EventListener<T> listener) throws SecurityException {
        Class<T> eventClass = ReflectionUtils.inferParameterClass(listener.getClass(), "onEvent");
        if (eventClass == null) {
            throw new NotificationContextException("Unable to infer the listened event class.");
        }
        return eventClass;
    }

    @Override // org.sejda.core.notification.context.EventListenerHoldingStrategy
    public void clear() {
        this.listeners.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.sejda.core.notification.context.EventListenerHoldingStrategy
    public List<EventListener<? extends AbstractNotificationEvent>> get(AbstractNotificationEvent event) {
        return this.listeners.get(event.getClass());
    }

    @Override // org.sejda.core.notification.context.EventListenerHoldingStrategy
    public int size() {
        return this.listeners.size();
    }
}
