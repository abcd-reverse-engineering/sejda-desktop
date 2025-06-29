package code.util;

import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;

/* compiled from: DurationHelpers.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/DurationHelpers$.class */
public final class DurationHelpers$ {
    public static final DurationHelpers$ MODULE$ = new DurationHelpers$();
    private static final FiniteDuration perHour = Duration$.MODULE$.apply(1, TimeUnit.HOURS);
    private static final FiniteDuration perDay = Duration$.MODULE$.apply(1, TimeUnit.DAYS);
    private static final FiniteDuration perMinute = Duration$.MODULE$.apply(1, TimeUnit.MINUTES);

    private DurationHelpers$() {
    }

    public FiniteDuration perHour() {
        return perHour;
    }

    public FiniteDuration perDay() {
        return perDay;
    }

    public FiniteDuration perMinute() {
        return perMinute;
    }

    public FiniteDuration perSeconds(final int s) {
        return Duration$.MODULE$.apply(s, TimeUnit.SECONDS);
    }

    public FiniteDuration seconds(final int s) {
        return Duration$.MODULE$.apply(s, TimeUnit.SECONDS);
    }

    public FiniteDuration minutes(final int m) {
        return Duration$.MODULE$.apply(m, TimeUnit.MINUTES);
    }
}
