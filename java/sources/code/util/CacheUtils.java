package code.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import scala.reflect.ScalaSignature;

/* compiled from: CacheUtils.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005=r!\u0002\u0007\u000e\u0011\u0003\u0011b!\u0002\u000b\u000e\u0011\u0003)\u0002\"\u0002\u000f\u0002\t\u0003i\u0002b\u0002\u0010\u0002\u0005\u0004%\ta\b\u0005\u0007}\u0005\u0001\u000b\u0011\u0002\u0011\t\u000b=\u000bA\u0011\u0001)\t\u000b9\fA\u0011A8\t\u000ba\fA\u0011B=\t\u000bu\fA\u0011\u0001@\t\u0013\u0005E\u0011A1A\u0005\u0002\u0005M\u0001\u0002CA\u000e\u0003\u0001\u0006I!!\u0006\t\u0015\u0005-\u0012\u0001#b\u0001\n\u0003\ti#\u0001\u0006DC\u000eDW-\u0016;jYNT!AD\b\u0002\tU$\u0018\u000e\u001c\u0006\u0002!\u0005!1m\u001c3f\u0007\u0001\u0001\"aE\u0001\u000e\u00035\u0011!bQ1dQ\u0016,F/\u001b7t'\t\ta\u0003\u0005\u0002\u001855\t\u0001DC\u0001\u001a\u0003\u0015\u00198-\u00197b\u0013\tY\u0002D\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003I\taaY1dQ\u0016\u001cX#\u0001\u0011\u0011\t\u0005RS&\u0013\b\u0003E!j\u0011a\t\u0006\u0003I\u0015\n!bY8oGV\u0014(/\u001a8u\u0015\tqaEC\u0001(\u0003\u0011Q\u0017M^1\n\u0005%\u001a\u0013!E\"p]\u000e,(O]3oi\"\u000b7\u000f['ba&\u00111\u0006\f\u0002\u000b\u0017\u0016L8+\u001a;WS\u0016<(BA\u0015$a\rqCh\u0012\t\u0005_aRd)D\u00011\u0015\t\t$'A\u0003dC\u000eDWM\u0003\u00024i\u000511m\\7n_:T!!\u000e\u001c\u0002\r\u001d|wn\u001a7f\u0015\u00059\u0014aA2p[&\u0011\u0011\b\r\u0002\u0006\u0007\u0006\u001c\u0007.\u001a\t\u0003wqb\u0001\u0001B\u0005>\t\u0005\u0005\t\u0011!B\u0001\u007f\t\u0019q\fJ\u0019\u0002\u000f\r\f7\r[3tAE\u0011\u0001i\u0011\t\u0003/\u0005K!A\u0011\r\u0003\u000f9{G\u000f[5oOB\u0011q\u0003R\u0005\u0003\u000bb\u00111!\u00118z!\tYt\tB\u0005I\t\u0005\u0005\t\u0011!B\u0001\u007f\t\u0019q\f\n\u001a\u0011\u0005)kU\"A&\u000b\u000513\u0013\u0001\u00027b]\u001eL!AT&\u0003\u000f\t{w\u000e\\3b]\u0006ya.Z<M_\u0006$\u0017N\\4DC\u000eDW-F\u0002R-v#BAU0eSB!qfU+]\u0013\t!\u0006G\u0001\u0007M_\u0006$\u0017N\\4DC\u000eDW\r\u0005\u0002<-\u0012)q+\u0002b\u00011\n\t1*\u0005\u0002A3B\u0011!JW\u0005\u00037.\u0013aa\u00142kK\u000e$\bCA\u001e^\t\u0015qVA1\u0001Y\u0005\u00051\u0006\"\u00021\u0006\u0001\u0004\t\u0017AB3ya&\u0014X\r\u0005\u0002\u0018E&\u00111\r\u0007\u0002\u0004\u0013:$\b\"B3\u0006\u0001\u00041\u0017\u0001\u0003;j[\u0016,f.\u001b;\u0011\u0005\t:\u0017B\u00015$\u0005!!\u0016.\\3V]&$\b\"\u00026\u0006\u0001\u0004Y\u0017A\u00027pC\u0012,'\u000f\u0005\u00030YVc\u0016BA71\u0005-\u0019\u0015m\u00195f\u0019>\fG-\u001a:\u0002\u00119,woQ1dQ\u0016,2\u0001]:v)\r\tho\u001e\t\u0005_a\u0012H\u000f\u0005\u0002<g\u0012)qK\u0002b\u00011B\u00111(\u001e\u0003\u0006=\u001a\u0011\r\u0001\u0017\u0005\u0006A\u001a\u0001\r!\u0019\u0005\u0006K\u001a\u0001\rAZ\u0001\bG2,\u0017M\\+q)\u0005Q\bCA\f|\u0013\ta\bD\u0001\u0003V]&$\u0018aD:dQ\u0016$W\u000f\\3DY\u0016\fg.\u001e9\u0015\u0005i|\bBB\u0019\t\u0001\u0004\t\t\u0001\r\u0004\u0002\u0004\u0005\u001d\u0011Q\u0002\t\u0007_a\n)!a\u0003\u0011\u0007m\n9\u0001\u0002\u0006\u0002\n}\f\t\u0011!A\u0003\u0002}\u00121a\u0018\u00138!\rY\u0014Q\u0002\u0003\u000b\u0003\u001fy\u0018\u0011!A\u0001\u0006\u0003y$aA0%q\u0005!A/Y:l+\t\t)B\u0005\u0004\u0002\u0018\u0005u\u0011Q\u0005\u0004\u0007\u00033Q\u0001!!\u0006\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0002\u000bQ\f7o\u001b\u0011\u0011\t\u0005}\u0011\u0011E\u0007\u0002K%\u0019\u00111E\u0013\u0003\u0013QKW.\u001a:UCN\\\u0007cA\n\u0002(%\u0019\u0011\u0011F\u0007\u0003\u00111{wmZ1cY\u0016\fQ\"\u001a8tkJ,7\u000b^1si\u0016$W#\u0001>")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/CacheUtils.class */
public final class CacheUtils {
    public static void ensureStarted() {
        CacheUtils$.MODULE$.ensureStarted();
    }

    public static TimerTask task() {
        return CacheUtils$.MODULE$.task();
    }

    public static void scheduleCleanup(final Cache<?, ?> cache) {
        CacheUtils$.MODULE$.scheduleCleanup(cache);
    }

    public static <K, V> Cache<K, V> newCache(final int expire, final TimeUnit timeUnit) {
        return CacheUtils$.MODULE$.newCache(expire, timeUnit);
    }

    public static <K, V> LoadingCache<K, V> newLoadingCache(final int expire, final TimeUnit timeUnit, final CacheLoader<K, V> loader) {
        return CacheUtils$.MODULE$.newLoadingCache(expire, timeUnit, loader);
    }

    public static ConcurrentHashMap.KeySetView<Cache<?, ?>, Boolean> caches() {
        return CacheUtils$.MODULE$.caches();
    }
}
