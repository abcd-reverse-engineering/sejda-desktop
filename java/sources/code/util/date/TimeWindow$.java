package code.util.date;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.concurrent.duration.Duration;
import scala.runtime.AbstractFunction4;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: DateUtils.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/date/TimeWindow$.class */
public final class TimeWindow$ extends AbstractFunction4<Object, Object, Object, Duration, TimeWindow> implements Serializable {
    public static final TimeWindow$ MODULE$ = new TimeWindow$();

    public final String toString() {
        return "TimeWindow";
    }

    public TimeWindow apply(final int dayOfWeek, final int fromHour, final int fromMinute, final Duration duration) {
        return new TimeWindow(dayOfWeek, fromHour, fromMinute, duration);
    }

    public Option<Tuple4<Object, Object, Object, Duration>> unapply(final TimeWindow x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple4(BoxesRunTime.boxToInteger(x$0.dayOfWeek()), BoxesRunTime.boxToInteger(x$0.fromHour()), BoxesRunTime.boxToInteger(x$0.fromMinute()), x$0.duration()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TimeWindow$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4) {
        return apply(BoxesRunTime.unboxToInt(v1), BoxesRunTime.unboxToInt(v2), BoxesRunTime.unboxToInt(v3), (Duration) v4);
    }

    private TimeWindow$() {
    }
}
