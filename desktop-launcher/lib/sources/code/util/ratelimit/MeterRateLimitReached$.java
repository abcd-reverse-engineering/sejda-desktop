package code.util.ratelimit;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: RateLimiting.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ratelimit/MeterRateLimitReached$.class */
public final class MeterRateLimitReached$ extends AbstractFunction1<Meter, MeterRateLimitReached> implements Serializable {
    public static final MeterRateLimitReached$ MODULE$ = new MeterRateLimitReached$();

    public final String toString() {
        return "MeterRateLimitReached";
    }

    public MeterRateLimitReached apply(final Meter meter) {
        return new MeterRateLimitReached(meter);
    }

    public Option<Meter> unapply(final MeterRateLimitReached x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.meter());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MeterRateLimitReached$.class);
    }

    private MeterRateLimitReached$() {
    }
}
