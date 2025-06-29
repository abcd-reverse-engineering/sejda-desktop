package code.util.ratelimit;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: RateLimiting.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005ec\u0001B\r\u001b\u0001\u0006B\u0001b\u000e\u0001\u0003\u0016\u0004%\t\u0001\u000f\u0005\ty\u0001\u0011\t\u0012)A\u0005s!AQ\b\u0001BK\u0002\u0013\u0005a\b\u0003\u0005G\u0001\tE\t\u0015!\u0003@\u0011\u00159\u0005\u0001\"\u0001I\u0011\u0015i\u0005\u0001\"\u0011O\u0011\u001d9\u0006!!A\u0005\u0002aCqa\u0017\u0001\u0012\u0002\u0013\u0005A\fC\u0004h\u0001E\u0005I\u0011\u00015\t\u000f)\u0004\u0011\u0011!C!W\"9A\u000eAA\u0001\n\u0003A\u0004bB7\u0001\u0003\u0003%\tA\u001c\u0005\bi\u0002\t\t\u0011\"\u0011v\u0011\u001da\b!!A\u0005\u0002uD\u0011\"!\u0002\u0001\u0003\u0003%\t%a\u0002\t\u0013\u0005-\u0001!!A\u0005B\u00055\u0001\"CA\b\u0001\u0005\u0005I\u0011IA\t\u000f%\t)BGA\u0001\u0012\u0003\t9B\u0002\u0005\u001a5\u0005\u0005\t\u0012AA\r\u0011\u001995\u0003\"\u0001\u00022!9QjEA\u0001\n\u000br\u0005\"CA\u001a'\u0005\u0005I\u0011QA\u001b\u0011%\tYdEA\u0001\n\u0003\u000bi\u0004C\u0005\u0002PM\t\t\u0011\"\u0003\u0002R\tI!+\u0019;f\u0019&l\u0017\u000e\u001e\u0006\u00037q\t\u0011B]1uK2LW.\u001b;\u000b\u0005uq\u0012\u0001B;uS2T\u0011aH\u0001\u0005G>$Wm\u0001\u0001\u0014\t\u0001\u0011\u0003f\u000b\t\u0003G\u0019j\u0011\u0001\n\u0006\u0002K\u0005)1oY1mC&\u0011q\u0005\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\rJ\u0013B\u0001\u0016%\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001\f\u001b\u000f\u00055\u0012dB\u0001\u00182\u001b\u0005y#B\u0001\u0019!\u0003\u0019a$o\\8u}%\tQ%\u0003\u00024I\u00059\u0001/Y2lC\u001e,\u0017BA\u001b7\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t\u0019D%A\u0006nCb\u0014V-];fgR\u001cX#A\u001d\u0011\u0005\rR\u0014BA\u001e%\u0005\rIe\u000e^\u0001\r[\u0006D(+Z9vKN$8\u000fI\u0001\tIV\u0014\u0018\r^5p]V\tq\b\u0005\u0002A\t6\t\u0011I\u0003\u0002>\u0005*\u00111\tJ\u0001\u000bG>t7-\u001e:sK:$\u0018BA#B\u0005!!UO]1uS>t\u0017!\u00033ve\u0006$\u0018n\u001c8!\u0003\u0019a\u0014N\\5u}Q\u0019\u0011j\u0013'\u0011\u0005)\u0003Q\"\u0001\u000e\t\u000b]*\u0001\u0019A\u001d\t\u000bu*\u0001\u0019A \u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012a\u0014\t\u0003!Vk\u0011!\u0015\u0006\u0003%N\u000bA\u0001\\1oO*\tA+\u0001\u0003kCZ\f\u0017B\u0001,R\u0005\u0019\u0019FO]5oO\u0006!1m\u001c9z)\rI\u0015L\u0017\u0005\bo\u001d\u0001\n\u00111\u0001:\u0011\u001dit\u0001%AA\u0002}\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001^U\tIdlK\u0001`!\t\u0001W-D\u0001b\u0015\t\u00117-A\u0005v]\u000eDWmY6fI*\u0011A\rJ\u0001\u000bC:tw\u000e^1uS>t\u0017B\u00014b\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\u0005I'FA _\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\tq*\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005=\u0014\bCA\u0012q\u0013\t\tHEA\u0002B]fDqa\u001d\u0007\u0002\u0002\u0003\u0007\u0011(A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002mB\u0019qO_8\u000e\u0003aT!!\u001f\u0013\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002|q\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\rq\u00181\u0001\t\u0003G}L1!!\u0001%\u0005\u001d\u0011un\u001c7fC:Dqa\u001d\b\u0002\u0002\u0003\u0007q.\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GcA(\u0002\n!91oDA\u0001\u0002\u0004I\u0014\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003e\na!Z9vC2\u001cHc\u0001@\u0002\u0014!91/EA\u0001\u0002\u0004y\u0017!\u0003*bi\u0016d\u0015.\\5u!\tQ5cE\u0003\u0014\u00037\t9\u0003E\u0004\u0002\u001e\u0005\r\u0012hP%\u000e\u0005\u0005}!bAA\u0011I\u00059!/\u001e8uS6,\u0017\u0002BA\u0013\u0003?\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c83!\u0011\tI#a\f\u000e\u0005\u0005-\"bAA\u0017'\u0006\u0011\u0011n\\\u0005\u0004k\u0005-BCAA\f\u0003\u0015\t\u0007\u000f\u001d7z)\u0015I\u0015qGA\u001d\u0011\u00159d\u00031\u0001:\u0011\u0015id\u00031\u0001@\u0003\u001d)h.\u00199qYf$B!a\u0010\u0002LA)1%!\u0011\u0002F%\u0019\u00111\t\u0013\u0003\r=\u0003H/[8o!\u0015\u0019\u0013qI\u001d@\u0013\r\tI\u0005\n\u0002\u0007)V\u0004H.\u001a\u001a\t\u0011\u00055s#!AA\u0002%\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t\u0019\u0006E\u0002Q\u0003+J1!a\u0016R\u0005\u0019y%M[3di\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ratelimit/RateLimit.class */
public class RateLimit implements Product, Serializable {
    private final int maxRequests;
    private final Duration duration;

    public static Option<Tuple2<Object, Duration>> unapply(final RateLimit x$0) {
        return RateLimit$.MODULE$.unapply(x$0);
    }

    public static RateLimit apply(final int maxRequests, final Duration duration) {
        return RateLimit$.MODULE$.apply(maxRequests, duration);
    }

    public static Function1<Tuple2<Object, Duration>, RateLimit> tupled() {
        return RateLimit$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Duration, RateLimit>> curried() {
        return RateLimit$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int maxRequests() {
        return this.maxRequests;
    }

    public RateLimit copy(final int maxRequests, final Duration duration) {
        return new RateLimit(maxRequests, duration);
    }

    public int copy$default$1() {
        return maxRequests();
    }

    public String productPrefix() {
        return "RateLimit";
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(maxRequests());
            case 1:
                return duration();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof RateLimit;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "maxRequests";
            case 1:
                return "duration";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), maxRequests()), Statics.anyHash(duration())), 2);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof RateLimit) {
                RateLimit rateLimit = (RateLimit) x$1;
                if (maxRequests() == rateLimit.maxRequests()) {
                    Duration duration = duration();
                    Duration duration2 = rateLimit.duration();
                    if (duration != null ? duration.equals(duration2) : duration2 == null) {
                        if (rateLimit.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public RateLimit(final int maxRequests, final Duration duration) {
        this.maxRequests = maxRequests;
        this.duration = duration;
        Product.$init$(this);
    }

    public Duration duration() {
        return this.duration;
    }

    public Duration copy$default$2() {
        return duration();
    }

    public String toString() {
        return new StringBuilder(5).append(maxRequests()).append(" per ").append(duration()).toString();
    }
}
