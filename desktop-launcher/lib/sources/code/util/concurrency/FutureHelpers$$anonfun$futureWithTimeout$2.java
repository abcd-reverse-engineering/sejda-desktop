package code.util.concurrency;

import java.io.Serializable;
import java.util.TimerTask;
import scala.Function1;
import scala.concurrent.Promise;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

/* compiled from: FutureHelpers.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/concurrency/FutureHelpers$$anonfun$futureWithTimeout$2.class */
public final class FutureHelpers$$anonfun$futureWithTimeout$2 extends AbstractPartialFunction<Throwable, Object> implements Serializable {
    private static final long serialVersionUID = 0;
    private final Promise p$1;
    private final TimerTask timerTask$1;

    public FutureHelpers$$anonfun$futureWithTimeout$2(final Promise p$1, final TimerTask timerTask$1) {
        this.p$1 = p$1;
        this.timerTask$1 = timerTask$1;
    }

    public final <A1 extends Throwable, B1> B1 applyOrElse(A1 a1, Function1<A1, B1> function1) {
        if (!(a1 instanceof Exception)) {
            return (B1) function1.apply(a1);
        }
        if (!this.p$1.tryFailure((Exception) a1)) {
            return (B1) BoxedUnit.UNIT;
        }
        return (B1) BoxesRunTime.boxToBoolean(this.timerTask$1.cancel());
    }

    public final boolean isDefinedAt(final Throwable x1) {
        if (!(x1 instanceof Exception)) {
            return false;
        }
        return true;
    }

    public /* bridge */ /* synthetic */ Object applyOrElse(final Object x, Function1 function1) {
        return applyOrElse((FutureHelpers$$anonfun$futureWithTimeout$2) x, (Function1<FutureHelpers$$anonfun$futureWithTimeout$2, B1>) function1);
    }
}
