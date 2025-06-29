package code.util;

import com.nqzero.permit.Permit;
import scala.runtime.BoxedUnit;

/* compiled from: ReflectionHelpers.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ReflectionHelpers$.class */
public final class ReflectionHelpers$ {
    public static final ReflectionHelpers$ MODULE$ = new ReflectionHelpers$();
    private static BoxedUnit initGodModeOnce;
    private static volatile boolean bitmap$0;

    private ReflectionHelpers$() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    private void initGodModeOnce$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$0) {
                Permit.godMode();
                r0 = 1;
                bitmap$0 = true;
            }
        }
    }

    public void initGodModeOnce() {
        if (bitmap$0) {
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            initGodModeOnce$lzycompute();
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
    }

    public void godMode() {
        initGodModeOnce();
    }
}
