package code.util.ratelimit;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: RateLimiting.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ratelimit/DiskPersistentMeter$.class */
public final class DiskPersistentMeter$ extends AbstractFunction3<String, String, RateLimit, DiskPersistentMeter> implements Serializable {
    public static final DiskPersistentMeter$ MODULE$ = new DiskPersistentMeter$();

    public final String toString() {
        return "DiskPersistentMeter";
    }

    public DiskPersistentMeter apply(final String name, final String client, final RateLimit cfg) {
        return new DiskPersistentMeter(name, client, cfg);
    }

    public Option<Tuple3<String, String, RateLimit>> unapply(final DiskPersistentMeter x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple3(x$0.name(), x$0.client(), x$0.cfg()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DiskPersistentMeter$.class);
    }

    private DiskPersistentMeter$() {
    }
}
