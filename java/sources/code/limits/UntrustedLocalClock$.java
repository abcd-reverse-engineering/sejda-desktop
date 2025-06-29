package code.limits;

import code.util.Loggable;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import scala.MatchError;
import scala.collection.immutable.$colon;
import scala.collection.immutable.Nil$;
import scala.concurrent.Await$;
import scala.concurrent.ExecutionContext$Implicits$;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.Duration$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.Failure;
import scala.util.Success;
import scala.util.control.NonFatal$;

/* compiled from: UntrustedLocalClock.scala */
/* loaded from: com.sejda.desktop-launcher-1.0.0.jar:code/limits/UntrustedLocalClock$.class */
public final class UntrustedLocalClock$ implements Loggable {
    public static final UntrustedLocalClock$ MODULE$ = new UntrustedLocalClock$();
    private static final long defaultAllowedDifferenceInSeconds;
    private static final int defaultTimeout;
    private static transient Logger logger;
    private static volatile transient boolean bitmap$trans$0;

    static {
        Loggable.$init$(MODULE$);
        defaultAllowedDifferenceInSeconds = 43200L;
        defaultTimeout = 60000;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$trans$0) {
                logger = logger();
                r0 = 1;
                bitmap$trans$0 = true;
            }
        }
        return logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !bitmap$trans$0 ? logger$lzycompute() : logger;
    }

    private UntrustedLocalClock$() {
    }

    public long defaultAllowedDifferenceInSeconds() {
        return defaultAllowedDifferenceInSeconds;
    }

    public int defaultTimeout() {
        return defaultTimeout;
    }

    public long assertAccurate$default$1() {
        return defaultAllowedDifferenceInSeconds();
    }

    public void assertAccurate(final long allowedSecondDifference) {
        try {
            long internetTime = getInternetTime();
            long localTime = getLocalTime();
            long clocksDiff = Math.abs(internetTime - localTime);
            if (clocksDiff - (allowedSecondDifference * 1000) > 0) {
                String msg = new StringBuilder(29).append("Internet time: ").append(new Date(internetTime)).append(", local time: ").append(new Date(localTime)).toString();
                throw new LocalClockInaccurateException(msg);
            }
        } catch (LocalClockInaccurateException e) {
            throw e;
        } catch (Throwable th) {
            if (!NonFatal$.MODULE$.apply(th)) {
                throw th;
            }
            logger().warn(new StringBuilder(31).append("Error checking internet time (").append(DateTime.now()).append(")").toString(), th);
        }
    }

    public long assertAccurateAsync$default$1() {
        return defaultAllowedDifferenceInSeconds();
    }

    public void assertAccurateAsync(final long allowedSecondDifference) {
        Thread thread = new Thread(new Runnable(allowedSecondDifference) { // from class: code.limits.UntrustedLocalClock$$anon$1
            private final long allowedSecondDifference$1;

            {
                this.allowedSecondDifference$1 = allowedSecondDifference;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    UntrustedLocalClock$.MODULE$.assertAccurate(this.allowedSecondDifference$1);
                } catch (LocalClockInaccurateException e) {
                    System.exit(99);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private long getInternetTime() {
        $colon.colon colonVar = new $colon.colon("time.google.com", new $colon.colon("time.cloudflare.com", new $colon.colon("time.facebook.com", new $colon.colon("time.windows.com", new $colon.colon("time.apple.com", new $colon.colon("ntp.sejda.com", Nil$.MODULE$))))));
        Promise promise = Promise$.MODULE$.apply();
        colonVar.foreach(server -> {
            $anonfun$getInternetTime$1(promise, server);
            return BoxedUnit.UNIT;
        });
        return BoxesRunTime.unboxToLong(Await$.MODULE$.result(promise.future(), Duration$.MODULE$.apply(defaultTimeout() * colonVar.size(), TimeUnit.MILLISECONDS)));
    }

    public static final /* synthetic */ void $anonfun$getInternetTime$1(final Promise promise$1, final String server) {
        Future f = Future$.MODULE$.apply(() -> {
            return MODULE$._getInternetTime(server);
        }, ExecutionContext$Implicits$.MODULE$.global());
        f.onComplete(x0$1 -> {
            if (x0$1 instanceof Success) {
                long date = BoxesRunTime.unboxToLong(((Success) x0$1).value());
                return BoxesRunTime.boxToBoolean(promise$1.trySuccess(BoxesRunTime.boxToLong(date)));
            }
            if (x0$1 instanceof Failure) {
                return BoxedUnit.UNIT;
            }
            throw new MatchError(x0$1);
        }, ExecutionContext$Implicits$.MODULE$.global());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long _getInternetTime(final String server) {
        NTPUDPClient client = new NTPUDPClient();
        try {
            client.setDefaultTimeout(defaultTimeout());
            TimeInfo ioe = client.getTime(InetAddress.getByName(server));
            return ioe.getMessage().getTransmitTimeStamp().getTime();
        } finally {
            client.close();
        }
    }

    private long getLocalTime() {
        return new Date().getTime();
    }
}
