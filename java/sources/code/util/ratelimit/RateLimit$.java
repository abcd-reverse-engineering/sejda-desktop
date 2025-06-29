package code.util.ratelimit;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.concurrent.duration.Duration;
import scala.runtime.AbstractFunction2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: RateLimiting.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ratelimit/RateLimit$.class */
public final class RateLimit$ extends AbstractFunction2<Object, Duration, RateLimit> implements Serializable {
    public static final RateLimit$ MODULE$ = new RateLimit$();

    public final String toString() {
        return "RateLimit";
    }

    public RateLimit apply(final int maxRequests, final Duration duration) {
        return new RateLimit(maxRequests, duration);
    }

    public Option<Tuple2<Object, Duration>> unapply(final RateLimit x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2(BoxesRunTime.boxToInteger(x$0.maxRequests()), x$0.duration()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RateLimit$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2) {
        return apply(BoxesRunTime.unboxToInt(v1), (Duration) v2);
    }

    private RateLimit$() {
    }
}
