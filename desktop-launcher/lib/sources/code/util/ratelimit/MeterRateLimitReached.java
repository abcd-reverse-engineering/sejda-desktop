package code.util.ratelimit;

import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: RateLimiting.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005Mb\u0001B\u000b\u0017\u0001vA\u0001b\r\u0001\u0003\u0016\u0004%\t\u0001\u000e\u0005\ts\u0001\u0011\t\u0012)A\u0005k!)!\b\u0001C\u0001w!9a\bAA\u0001\n\u0003y\u0004bB!\u0001#\u0003%\tA\u0011\u0005\b\u001b\u0002\t\t\u0011\"\u0011O\u0011\u001d9\u0006!!A\u0005\u0002aCq\u0001\u0018\u0001\u0002\u0002\u0013\u0005Q\fC\u0004d\u0001\u0005\u0005I\u0011\t3\t\u000f-\u0004\u0011\u0011!C\u0001Y\"9\u0011\u000fAA\u0001\n\u0003\u0012\bb\u0002;\u0001\u0003\u0003%\t%\u001e\u0005\bm\u0002\t\t\u0011\"\u0011x\u000f\u001dIh#!A\t\u0002i4q!\u0006\f\u0002\u0002#\u00051\u0010\u0003\u0004;\u001f\u0011\u0005\u0011q\u0002\u0005\n\u0003#y\u0011\u0011!C#\u0003'A\u0011\"!\u0006\u0010\u0003\u0003%\t)a\u0006\t\u0013\u0005mq\"!A\u0005\u0002\u0006u\u0001\"CA\u0015\u001f\u0005\u0005I\u0011BA\u0016\u0005UiU\r^3s%\u0006$X\rT5nSR\u0014V-Y2iK\u0012T!a\u0006\r\u0002\u0013I\fG/\u001a7j[&$(BA\r\u001b\u0003\u0011)H/\u001b7\u000b\u0003m\tAaY8eK\u000e\u00011\u0003\u0002\u0001\u001fYA\u0002\"aH\u0015\u000f\u0005\u00012cBA\u0011%\u001b\u0005\u0011#BA\u0012\u001d\u0003\u0019a$o\\8u}%\tQ%A\u0003tG\u0006d\u0017-\u0003\u0002(Q\u00059\u0001/Y2lC\u001e,'\"A\u0013\n\u0005)Z#\u0001\u0005*v]RLW.Z#yG\u0016\u0004H/[8o\u0015\t9\u0003\u0006\u0005\u0002.]5\t\u0001&\u0003\u00020Q\t9\u0001K]8ek\u000e$\bCA\u00102\u0013\t\u00114F\u0001\u0007TKJL\u0017\r\\5{C\ndW-A\u0003nKR,'/F\u00016!\t1t'D\u0001\u0017\u0013\tAdCA\u0003NKR,'/\u0001\u0004nKR,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005qj\u0004C\u0001\u001c\u0001\u0011\u0015\u00194\u00011\u00016\u0003\u0011\u0019w\u000e]=\u0015\u0005q\u0002\u0005bB\u001a\u0005!\u0003\u0005\r!N\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005\u0019%FA\u001bEW\u0005)\u0005C\u0001$L\u001b\u00059%B\u0001%J\u0003%)hn\u00195fG.,GM\u0003\u0002KQ\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u00051;%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012a\u0014\t\u0003!Vk\u0011!\u0015\u0006\u0003%N\u000bA\u0001\\1oO*\tA+\u0001\u0003kCZ\f\u0017B\u0001,R\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\t\u0011\f\u0005\u0002.5&\u00111\f\u000b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003=\u0006\u0004\"!L0\n\u0005\u0001D#aA!os\"9!\rCA\u0001\u0002\u0004I\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001f!\r1\u0017NX\u0007\u0002O*\u0011\u0001\u000eK\u0001\u000bG>dG.Z2uS>t\u0017B\u00016h\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u00055\u0004\bCA\u0017o\u0013\ty\u0007FA\u0004C_>dW-\u00198\t\u000f\tT\u0011\u0011!a\u0001=\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\ty5\u000fC\u0004c\u0017\u0005\u0005\t\u0019A-\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012!W\u0001\u0007KF,\u0018\r\\:\u0015\u00055D\bb\u00022\u000e\u0003\u0003\u0005\rAX\u0001\u0016\u001b\u0016$XM\u001d*bi\u0016d\u0015.\\5u%\u0016\f7\r[3e!\t1tb\u0005\u0003\u0010y\u0006\u0015\u0001#B?\u0002\u0002UbT\"\u0001@\u000b\u0005}D\u0013a\u0002:v]RLW.Z\u0005\u0004\u0003\u0007q(!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!\u0011qAA\u0007\u001b\t\tIAC\u0002\u0002\fM\u000b!![8\n\u0007I\nI\u0001F\u0001{\u0003!!xn\u0015;sS:<G#A(\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0007q\nI\u0002C\u00034%\u0001\u0007Q'A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005}\u0011Q\u0005\t\u0005[\u0005\u0005R'C\u0002\u0002$!\u0012aa\u00149uS>t\u0007\u0002CA\u0014'\u0005\u0005\t\u0019\u0001\u001f\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002.A\u0019\u0001+a\f\n\u0007\u0005E\u0012K\u0001\u0004PE*,7\r\u001e")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ratelimit/MeterRateLimitReached.class */
public class MeterRateLimitReached extends RuntimeException implements Product {
    private final Meter meter;

    public static Option<Meter> unapply(final MeterRateLimitReached x$0) {
        return MeterRateLimitReached$.MODULE$.unapply(x$0);
    }

    public static MeterRateLimitReached apply(final Meter meter) {
        return MeterRateLimitReached$.MODULE$.apply(meter);
    }

    public static <A> Function1<Meter, A> andThen(final Function1<MeterRateLimitReached, A> g) {
        return MeterRateLimitReached$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, MeterRateLimitReached> compose(final Function1<A, Meter> g) {
        return MeterRateLimitReached$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Meter meter() {
        return this.meter;
    }

    public MeterRateLimitReached copy(final Meter meter) {
        return new MeterRateLimitReached(meter);
    }

    public Meter copy$default$1() {
        return meter();
    }

    public String productPrefix() {
        return "MeterRateLimitReached";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return meter();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof MeterRateLimitReached;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "meter";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof MeterRateLimitReached) {
                MeterRateLimitReached meterRateLimitReached = (MeterRateLimitReached) x$1;
                Meter meter = meter();
                Meter meter2 = meterRateLimitReached.meter();
                if (meter != null ? meter.equals(meter2) : meter2 == null) {
                    if (meterRateLimitReached.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MeterRateLimitReached(final Meter meter) {
        super(new StringBuilder(56).append("Manual meter rate limit exceeded for ").append(meter.name()).append(" by ").append(meter.client()).append(". Limits were: ").append(meter.cfg()).toString());
        this.meter = meter;
        Product.$init$(this);
    }
}
