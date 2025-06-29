package code.util;

import java.util.TimerTask;
import org.slf4j.Logger;

/* compiled from: CacheUtils.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/CacheUtils$$anon$2.class */
public final class CacheUtils$$anon$2 extends TimerTask implements Loggable {
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.util.CacheUtils$$anon$2] */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$trans$0) {
                this.logger = logger();
                r0 = this;
                r0.bitmap$trans$0 = true;
            }
        }
        return this.logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !this.bitmap$trans$0 ? logger$lzycompute() : this.logger;
    }

    public CacheUtils$$anon$2() {
        Loggable.$init$(this);
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        logger().info(new StringBuilder(19).append("Cleaning up ").append(CacheUtils$.MODULE$.caches().size()).append(" caches").toString());
        CacheUtils$.MODULE$.code$util$CacheUtils$$cleanUp();
    }
}
