package code.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import scala.Int$;
import scala.runtime.BoxedUnit;

/* compiled from: CacheUtils.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/CacheUtils$.class */
public final class CacheUtils$ {
    private static BoxedUnit ensureStarted;
    private static volatile boolean bitmap$0;
    public static final CacheUtils$ MODULE$ = new CacheUtils$();
    private static final ConcurrentHashMap.KeySetView<Cache<?, ?>, Boolean> caches = ConcurrentHashMap.newKeySet();
    private static final TimerTask task = new CacheUtils$$anon$2();

    private CacheUtils$() {
    }

    public ConcurrentHashMap.KeySetView<Cache<?, ?>, Boolean> caches() {
        return caches;
    }

    public <K, V> LoadingCache<K, V> newLoadingCache(final int expire, final TimeUnit timeUnit, final CacheLoader<K, V> loader) {
        LoadingCache cache = CacheBuilder.newBuilder().expireAfterWrite(expire, timeUnit).build(loader);
        scheduleCleanup(cache);
        return cache;
    }

    public <K, V> Cache<K, V> newCache(final int expire, final TimeUnit timeUnit) {
        Cache cache = CacheBuilder.newBuilder().expireAfterWrite(expire, timeUnit).build();
        scheduleCleanup(cache);
        return cache;
    }

    public void code$util$CacheUtils$$cleanUp() {
        caches().forEach(new Consumer<Cache<?, ?>>() { // from class: code.util.CacheUtils$$anon$1
            @Override // java.util.function.Consumer
            public Consumer<Cache<?, ?>> andThen(final Consumer<? super Cache<?, ?>> x$1) {
                return super.andThen(x$1);
            }

            @Override // java.util.function.Consumer
            public void accept(final Cache<?, ?> cache) {
                cache.cleanUp();
            }
        });
    }

    public void scheduleCleanup(final Cache<?, ?> cache) {
        ensureStarted();
        caches().add(cache);
    }

    public TimerTask task() {
        return task;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6 */
    private void ensureStarted$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$0) {
                new Timer(true).schedule(task(), 600000, Int$.MODULE$.int2long(600000));
                r0 = 1;
                bitmap$0 = true;
            }
        }
    }

    public void ensureStarted() {
        if (bitmap$0) {
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            ensureStarted$lzycompute();
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
    }
}
