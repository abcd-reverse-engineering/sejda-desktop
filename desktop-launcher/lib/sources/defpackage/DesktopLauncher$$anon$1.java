package defpackage;

import code.util.Loggable;
import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.PercentageOfWorkDoneChangedEvent;
import org.slf4j.Logger;

/* compiled from: DesktopLauncher.scala */
/* loaded from: com.sejda.desktop-launcher-1.0.0.jar:DesktopLauncher$$anon$1.class */
public final class DesktopLauncher$$anon$1 implements EventListener<PercentageOfWorkDoneChangedEvent>, Loggable {
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [DesktopLauncher$$anon$1] */
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

    public DesktopLauncher$$anon$1() {
        Loggable.$init$(this);
    }

    @Override // org.sejda.model.notification.EventListener
    public void onEvent(final PercentageOfWorkDoneChangedEvent e) {
        if (e.isUndetermined()) {
            return;
        }
        logger().info(new StringBuilder(21).append("Task progress: ").append(e.getPercentage().toPlainString()).append("% done").toString());
    }
}
