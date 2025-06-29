package code.util.concurrency;

import java.util.Timer;
import java.util.TimerTask;
import scala.Function0;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

/* compiled from: FutureHelpers.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/concurrency/FutureHelpers$.class */
public final class FutureHelpers$ {
    public static final FutureHelpers$ MODULE$ = new FutureHelpers$();
    private static final Timer timer = new Timer(true);

    private FutureHelpers$() {
    }

    private Timer timer() {
        return timer;
    }

    public <T> Future<T> futureWithTimeout(final Future<T> future, final FiniteDuration timeout, final Function0<T> onTimeoutSuccessResult, final ExecutionContext ec) {
        final Promise p = Promise$.MODULE$.apply();
        TimerTask timerTask = new TimerTask(p, onTimeoutSuccessResult) { // from class: code.util.concurrency.FutureHelpers$$anon$1
            private final Promise p$1;
            private final Function0 onTimeoutSuccessResult$1;

            {
                this.p$1 = p;
                this.onTimeoutSuccessResult$1 = onTimeoutSuccessResult;
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                this.p$1.trySuccess(this.onTimeoutSuccessResult$1.apply());
            }
        };
        timer().schedule(timerTask, timeout.toMillis());
        future.map(a -> {
            if (!p.trySuccess(a)) {
                return BoxedUnit.UNIT;
            }
            return BoxesRunTime.boxToBoolean(timerTask.cancel());
        }, ec).recover(new FutureHelpers$$anonfun$futureWithTimeout$2(p, timerTask), ec);
        return p.future();
    }
}
