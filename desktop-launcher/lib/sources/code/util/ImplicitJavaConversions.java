package code.util;

import java.util.List;
import scala.collection.Iterable;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Buffer;
import scala.reflect.ScalaSignature;

/* compiled from: ImplicitJavaConversions.scala */
@ScalaSignature(bytes = "\u0006\u0005\u00055q!B\u0005\u000b\u0011\u0003ya!B\t\u000b\u0011\u0003\u0011\u0002\"B\r\u0002\t\u0003Q\u0002\"B\u000e\u0002\t\u0007a\u0002\"B \u0002\t\u0007\u0001\u0005\"\u0002*\u0002\t\u0007\u0019\u0006\"B/\u0002\t\u0007q\u0006\"B4\u0002\t\u0007A\u0007\"\u0002<\u0002\t\u00079\u0018aF%na2L7-\u001b;KCZ\f7i\u001c8wKJ\u001c\u0018n\u001c8t\u0015\tYA\"\u0001\u0003vi&d'\"A\u0007\u0002\t\r|G-Z\u0002\u0001!\t\u0001\u0012!D\u0001\u000b\u0005]IU\u000e\u001d7jG&$(*\u0019<b\u0007>tg/\u001a:tS>t7o\u0005\u0002\u0002'A\u0011AcF\u0007\u0002+)\ta#A\u0003tG\u0006d\u0017-\u0003\u0002\u0019+\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#A\b\u0002#M\u001c\u0017\r\\1NCB$vNS1wC6\u000b\u0007/F\u0002\u001eOE\"\"AH\u001a\u0011\t}\u0019S\u0005M\u0007\u0002A)\u00111\"\t\u0006\u0002E\u0005!!.\u0019<b\u0013\t!\u0003EA\u0002NCB\u0004\"AJ\u0014\r\u0001\u0011)\u0001f\u0001b\u0001S\t\t1*\u0005\u0002+[A\u0011AcK\u0005\u0003YU\u0011qAT8uQ&tw\r\u0005\u0002\u0015]%\u0011q&\u0006\u0002\u0004\u0003:L\bC\u0001\u00142\t\u0015\u00114A1\u0001*\u0005\u00051\u0006\"\u0002\u001b\u0004\u0001\u0004)\u0014\u0001C:dC2\fW*\u00199\u0011\tYjT\u0005\r\b\u0003om\u0002\"\u0001O\u000b\u000e\u0003eR!A\u000f\b\u0002\rq\u0012xn\u001c;?\u0013\taT#\u0001\u0004Qe\u0016$WMZ\u0005\u0003IyR!\u0001P\u000b\u0002%M\u001c\u0017\r\\1TKF$vNS1wC2K7\u000f^\u000b\u0003\u0003\u001a#\"AQ$\u0011\u0007}\u0019U)\u0003\u0002EA\t!A*[:u!\t1c\tB\u0003)\t\t\u0007\u0011\u0006C\u0003I\t\u0001\u0007\u0011*\u0001\u0005tG\u0006d\u0017mU3r!\rQu*\u0012\b\u0003\u00176s!\u0001\u000f'\n\u0003YI!AT\u000b\u0002\u000fA\f7m[1hK&\u0011\u0001+\u0015\u0002\u0004'\u0016\f(B\u0001(\u0016\u0003EQ\u0017M^1NCB$vnU2bY\u0006l\u0015\r]\u000b\u0004)^KFCA+[!\u00111TH\u0016-\u0011\u0005\u0019:F!\u0002\u0015\u0006\u0005\u0004I\u0003C\u0001\u0014Z\t\u0015\u0011TA1\u0001*\u0011\u0015YV\u00011\u0001]\u0003\u001dQ\u0017M^1NCB\u0004BaH\u0012W1\u0006\u0011\".\u0019<b\u0019&\u001cH\u000fV8TG\u0006d\u0017mU3r+\ty&\r\u0006\u0002aIB\u0019!jT1\u0011\u0005\u0019\u0012G!B2\u0007\u0005\u0004I#!\u0001+\t\u000b\u00154\u0001\u0019\u00014\u0002\u0011)\fg/\u0019'jgR\u00042aH\"b\u0003mQ\u0017M^1Ji\u0016\u0014\u0018M\u00197f)>\u001c6-\u00197b\u0013R,'/\u00192mKV\u0011\u0011N\u001c\u000b\u0003U>\u00042AS6n\u0013\ta\u0017K\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\t1c\u000eB\u0003d\u000f\t\u0007\u0011\u0006C\u0003q\u000f\u0001\u0007\u0011/\u0001\u0007kCZ\f\u0017\n^3sC\ndW\rE\u0002sk6l\u0011a\u001d\u0006\u0003i\u0006\nA\u0001\\1oO&\u0011An]\u0001\u0019Y&\u001cHOQ;gM\u0016\u0014Hk\\%n[V$\u0018M\u00197f'\u0016\fXC\u0001=|)\tIH\u0010E\u0002K\u001fj\u0004\"AJ>\u0005\u000b\rD!\u0019A\u0015\t\u000buD\u0001\u0019\u0001@\u0002\r\t,hMZ3s!\u0011y\u0018\u0011\u0002>\u000e\u0005\u0005\u0005!\u0002BA\u0002\u0003\u000b\tq!\\;uC\ndWMC\u0002\u0002\bU\t!bY8mY\u0016\u001cG/[8o\u0013\u0011\tY!!\u0001\u0003\r\t+hMZ3s\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ImplicitJavaConversions.class */
public final class ImplicitJavaConversions {
    public static <T> Seq<T> listBufferToImmutableSeq(final Buffer<T> buffer) {
        return ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq(buffer);
    }

    public static <T> Iterable<T> javaIterableToScalaIterable(final Iterable<T> javaIterable) {
        return ImplicitJavaConversions$.MODULE$.javaIterableToScalaIterable(javaIterable);
    }

    public static <T> Seq<T> javaListToScalaSeq(final List<T> javaList) {
        return ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(javaList);
    }

    public static <K, V> Map<K, V> javaMapToScalaMap(final java.util.Map<K, V> javaMap) {
        return ImplicitJavaConversions$.MODULE$.javaMapToScalaMap(javaMap);
    }

    public static <K> List<K> scalaSeqToJavaList(final Seq<K> scalaSeq) {
        return ImplicitJavaConversions$.MODULE$.scalaSeqToJavaList(scalaSeq);
    }

    public static <K, V> java.util.Map<K, V> scalaMapToJavaMap(final Map<K, V> scalaMap) {
        return ImplicitJavaConversions$.MODULE$.scalaMapToJavaMap(scalaMap);
    }
}
