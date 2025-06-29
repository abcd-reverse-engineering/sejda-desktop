package code.util;

import org.sejda.sambox.contentstream.operator.OperatorName;
import org.slf4j.Logger;
import scala.Function0;
import scala.runtime.BoxedUnit;

/* compiled from: MemoryHelpers.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/MemoryHelpers$.class */
public final class MemoryHelpers$ implements Loggable {
    public static final MemoryHelpers$ MODULE$ = new MemoryHelpers$();
    private static boolean _testing_close_and_open;
    private static transient Logger logger;
    private static volatile transient boolean bitmap$trans$0;

    static {
        Loggable.$init$(MODULE$);
        _testing_close_and_open = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$trans$0) {
                logger = logger();
                r0 = 1;
                bitmap$trans$0 = true;
            }
        }
        return logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !bitmap$trans$0 ? logger$lzycompute() : logger;
    }

    private MemoryHelpers$() {
    }

    public boolean _testing_close_and_open() {
        return _testing_close_and_open;
    }

    public void _testing_close_and_open_$eq(final boolean x$1) {
        _testing_close_and_open = x$1;
    }

    public void withTesting(final Function0<BoxedUnit> fn) {
        _testing_close_and_open_$eq(true);
        try {
            fn.apply$mcV$sp();
        } finally {
            _testing_close_and_open_$eq(false);
        }
    }

    public <T> T withMemoryMonitoring(Function0<BoxedUnit> function0, Function0<T> function02) {
        long jFreeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long jMaxMemory = Runtime.getRuntime().maxMemory();
        int i = (int) ((jFreeMemory * 100.0d) / jMaxMemory);
        logger().info(new StringBuilder(30).append("Used memory: ").append((jFreeMemory / 1000) / 1000).append("MB ").append(i).append("% from max: ").append((jMaxMemory / 1000) / 1000).append("MB").toString());
        if (i > 90 || _testing_close_and_open()) {
            logger().info(new StringBuilder(60).append("Closing and reopening source doc, memory usage reached: ").append((jFreeMemory / 1000) / 1000).append("MB ").append(i).append("%").toString());
            closeAndReopen$1(function0);
        }
        try {
            return (T) function02.apply();
        } catch (Throwable th) {
            if (th == null || !isOOM(th)) {
                throw th;
            }
            logger().info("OutOfMemoryError occurred, retrying after closing and reopening source doc");
            closeAndReopen$1(function0);
            return (T) function02.apply();
        }
    }

    private final void closeAndReopen$1(final Function0 _closeAndReopen$1) {
        long startms = System.currentTimeMillis();
        _closeAndReopen$1.apply$mcV$sp();
        long tookMs = System.currentTimeMillis() - startms;
        if (tookMs > 4000) {
            logger().info(new StringBuilder(29).append("Closing and reopening took: ").append(tookMs / 1000).append(OperatorName.CLOSE_AND_STROKE).toString());
        }
    }

    private boolean isOOM(final Throwable ex) {
        while (true) {
            Throwable th = ex;
            if (th == null) {
                return false;
            }
            if (th instanceof OutOfMemoryError) {
                return true;
            }
            ex = ex.getCause();
        }
    }
}
