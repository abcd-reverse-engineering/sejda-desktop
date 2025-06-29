package org.sejda.io;

import java.io.Closeable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import org.sejda.commons.util.IOUtils;
import org.sejda.io.SeekableSource;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/ThreadBoundCopiesSupplier.class */
public class ThreadBoundCopiesSupplier<T extends SeekableSource> implements Closeable, Supplier<T> {
    private final ConcurrentMap<Long, T> copies = new ConcurrentHashMap();
    private final Supplier<T> supplier;

    public ThreadBoundCopiesSupplier(Supplier<T> supplier) {
        this.supplier = (Supplier) Objects.requireNonNull(supplier);
    }

    @Override // java.util.function.Supplier
    public T get() {
        return this.copies.computeIfAbsent(Long.valueOf(Thread.currentThread().threadId()), k -> {
            return this.supplier.get();
        });
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.copies.values().forEach((v0) -> {
            IOUtils.closeQuietly(v0);
        });
        this.copies.clear();
    }
}
