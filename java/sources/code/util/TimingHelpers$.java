package code.util;

import scala.Predef$;

/* compiled from: TimingHelpers.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/TimingHelpers$.class */
public final class TimingHelpers$ {
    public static final TimingHelpers$ MODULE$ = new TimingHelpers$();
    private static long ts = 0;

    private TimingHelpers$() {
    }

    public long ts() {
        return ts;
    }

    public void ts_$eq(final long x$1) {
        ts = x$1;
    }

    public void start() {
        ts_$eq(System.currentTimeMillis());
    }

    public void time(final String s) {
        Predef$.MODULE$.println(new StringBuilder(8).append(s).append(" took ").append(System.currentTimeMillis() - ts()).append("ms").toString());
        ts_$eq(System.currentTimeMillis());
    }
}
