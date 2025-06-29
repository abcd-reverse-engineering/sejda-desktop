package code.service;

import code.util.Loggable;
import java.io.File;
import java.util.TimerTask;
import org.slf4j.Logger;

/* compiled from: InJvmTaskExecutor.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/ShutdownHelper$$anon$2.class */
public final class ShutdownHelper$$anon$2 extends TimerTask implements Loggable {
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;
    private final File kill$1;

    public ShutdownHelper$$anon$2(final File kill$1) {
        this.kill$1 = kill$1;
        Loggable.$init$(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.service.ShutdownHelper$$anon$2] */
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

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        if (this.kill$1.exists()) {
            logger().warn("Kill file exists, shutting down JVM");
            System.exit(100);
        }
    }
}
