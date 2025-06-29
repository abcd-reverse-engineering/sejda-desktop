package org.sejda.commons;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.sejda.commons.util.RequireUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/Pool.class */
public class Pool<T> {
    private static final Logger LOG = LoggerFactory.getLogger(Pool.class);
    private final ArrayBlockingQueue<T> pool;
    private final Supplier<T> supplier;
    private Optional<Consumer<T>> applyOnGive = Optional.empty();

    public Pool(Supplier<T> creator, int poolsize) {
        RequireUtils.requireNotNullArg(creator, "Pool objects creator cannot be null");
        this.pool = new ArrayBlockingQueue<>(poolsize);
        this.supplier = creator;
    }

    public T borrow() {
        return (T) Optional.ofNullable(this.pool.poll()).orElseGet(this.supplier);
    }

    public void give(T object) {
        this.applyOnGive.ifPresent(c -> {
            c.accept(object);
        });
        if (!this.pool.offer(object)) {
            LOG.info("Poll is already full, cannot return borrowed instance");
        }
    }

    public Pool<T> onGive(Consumer<T> applyOnGive) {
        this.applyOnGive = Optional.ofNullable(applyOnGive);
        return this;
    }
}
