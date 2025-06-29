package code.util.ratelimit;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: RateLimiting.scala */
@ScalaSignature(bytes = "\u0006\u0005\u00055f\u0001B\u0011#\u0001&B\u0001\u0002\u0011\u0001\u0003\u0016\u0004%\t%\u0011\u0005\n\u0015\u0002\u0011\t\u0012)A\u0005\u0005.C\u0001\u0002\u0014\u0001\u0003\u0016\u0004%\t%\u0011\u0005\n\u001b\u0002\u0011\t\u0012)A\u0005\u0005:C\u0001b\u0014\u0001\u0003\u0016\u0004%\t\u0005\u0015\u0005\n)\u0002\u0011\t\u0012)A\u0005#VCQA\u0016\u0001\u0005\u0002]CQ\u0001\u0018\u0001\u0005\nuCQ!\u0019\u0001\u0005\n\tDQA\u001a\u0001\u0005\nuCQa\u001a\u0001\u0005\n!DQ\u0001\u001c\u0001\u0005B5DQA\u001e\u0001\u0005B]Dq\u0001\u001f\u0001\u0002\u0002\u0013\u0005\u0011\u0010C\u0004~\u0001E\u0005I\u0011\u0001@\t\u0011\u0005M\u0001!%A\u0005\u0002yD\u0011\"!\u0006\u0001#\u0003%\t!a\u0006\t\u0013\u0005m\u0001!!A\u0005B\u0005u\u0001\"CA\u0017\u0001\u0005\u0005I\u0011AA\u0018\u0011%\t\t\u0004AA\u0001\n\u0003\t\u0019\u0004C\u0005\u0002@\u0001\t\t\u0011\"\u0011\u0002B!I\u0011q\n\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u000b\u0005\n\u0003+\u0002\u0011\u0011!C!\u0003/B\u0001\"a\u0017\u0001\u0003\u0003%\te\u001e\u0005\n\u0003;\u0002\u0011\u0011!C!\u0003?:\u0011\"a\u0019#\u0003\u0003E\t!!\u001a\u0007\u0011\u0005\u0012\u0013\u0011!E\u0001\u0003OBaAV\u000e\u0005\u0002\u0005}\u0004\"CAA7\u0005\u0005IQIAB\u0011%\t)iGA\u0001\n\u0003\u000b9\tC\u0005\u0002\u0010n\t\t\u0011\"!\u0002\u0012\"I\u00111U\u000e\u0002\u0002\u0013%\u0011Q\u0015\u0002\u0014\t&\u001c8\u000eU3sg&\u001cH/\u001a8u\u001b\u0016$XM\u001d\u0006\u0003G\u0011\n\u0011B]1uK2LW.\u001b;\u000b\u0005\u00152\u0013\u0001B;uS2T\u0011aJ\u0001\u0005G>$Wm\u0001\u0001\u0014\t\u0001Qc\u0006\u000e\t\u0003W1j\u0011AI\u0005\u0003[\t\u0012Q!T3uKJ\u0004\"a\f\u001a\u000e\u0003AR\u0011!M\u0001\u0006g\u000e\fG.Y\u0005\u0003gA\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u00026{9\u0011ag\u000f\b\u0003oij\u0011\u0001\u000f\u0006\u0003s!\na\u0001\u0010:p_Rt\u0014\"A\u0019\n\u0005q\u0002\u0014a\u00029bG.\fw-Z\u0005\u0003}}\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!\u0001\u0010\u0019\u0002\t9\fW.Z\u000b\u0002\u0005B\u00111i\u0012\b\u0003\t\u0016\u0003\"a\u000e\u0019\n\u0005\u0019\u0003\u0014A\u0002)sK\u0012,g-\u0003\u0002I\u0013\n11\u000b\u001e:j]\u001eT!A\u0012\u0019\u0002\u000b9\fW.\u001a\u0011\n\u0005\u0001c\u0013AB2mS\u0016tG/A\u0004dY&,g\u000e\u001e\u0011\n\u00051c\u0013aA2gOV\t\u0011\u000b\u0005\u0002,%&\u00111K\t\u0002\n%\u0006$X\rT5nSR\fAa\u00194hA%\u0011q\nL\u0001\u0007y%t\u0017\u000e\u001e \u0015\taK&l\u0017\t\u0003W\u0001AQ\u0001Q\u0004A\u0002\tCQ\u0001T\u0004A\u0002\tCQaT\u0004A\u0002E\u000bq\u0001\u001e:z\u0019>\fG\rF\u0001_!\tys,\u0003\u0002aa\t!QK\\5u\u0003\u001d!(/_*bm\u0016$\u0012a\u0019\t\u0003_\u0011L!!\u001a\u0019\u0003\r\u0005s\u0017PV1m\u0003\u0011aw.\u00193\u0002\tM\fg/\u001a\u000b\u0002SB\u0011qF[\u0005\u0003WB\u0012qAQ8pY\u0016\fg.\u0001\u0003uS\u000e\\GC\u00018r!\tys.\u0003\u0002qa\t\u0019\u0011J\u001c;\t\u000fId\u0001\u0013!a\u0001g\u0006\u0019an\\<\u0011\u0005=\"\u0018BA;1\u0005\u0011auN\\4\u0002\rUtG/[2l)\u0005q\u0017\u0001B2paf$B\u0001\u0017>|y\"9\u0001I\u0004I\u0001\u0002\u0004\u0011\u0005b\u0002'\u000f!\u0003\u0005\rA\u0011\u0005\b\u001f:\u0001\n\u00111\u0001R\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012a \u0016\u0004\u0005\u0006\u00051FAA\u0002!\u0011\t)!a\u0004\u000e\u0005\u0005\u001d!\u0002BA\u0005\u0003\u0017\t\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u00055\u0001'\u0001\u0006b]:|G/\u0019;j_:LA!!\u0005\u0002\b\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTCAA\rU\r\t\u0016\u0011A\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005}\u0001\u0003BA\u0011\u0003Wi!!a\t\u000b\t\u0005\u0015\u0012qE\u0001\u0005Y\u0006twM\u0003\u0002\u0002*\u0005!!.\u0019<b\u0013\rA\u00151E\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002]\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BA\u001b\u0003w\u00012aLA\u001c\u0013\r\tI\u0004\r\u0002\u0004\u0003:L\b\u0002CA\u001f)\u0005\u0005\t\u0019\u00018\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t\u0019\u0005\u0005\u0004\u0002F\u0005-\u0013QG\u0007\u0003\u0003\u000fR1!!\u00131\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003\u001b\n9E\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGcA5\u0002T!I\u0011Q\b\f\u0002\u0002\u0003\u0007\u0011QG\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002 \u0005e\u0003\u0002CA\u001f/\u0005\u0005\t\u0019\u00018\u0002\u0011!\f7\u000f[\"pI\u0016\fa!Z9vC2\u001cHcA5\u0002b!I\u0011QH\r\u0002\u0002\u0003\u0007\u0011QG\u0001\u0014\t&\u001c8\u000eU3sg&\u001cH/\u001a8u\u001b\u0016$XM\u001d\t\u0003Wm\u0019RaGA5\u0003k\u0002\u0002\"a\u001b\u0002r\t\u0013\u0015\u000bW\u0007\u0003\u0003[R1!a\u001c1\u0003\u001d\u0011XO\u001c;j[\u0016LA!a\u001d\u0002n\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u001a\u0011\t\u0005]\u0014QP\u0007\u0003\u0003sRA!a\u001f\u0002(\u0005\u0011\u0011n\\\u0005\u0004}\u0005eDCAA3\u0003!!xn\u0015;sS:<GCAA\u0010\u0003\u0015\t\u0007\u000f\u001d7z)\u001dA\u0016\u0011RAF\u0003\u001bCQ\u0001\u0011\u0010A\u0002\tCQ\u0001\u0014\u0010A\u0002\tCQa\u0014\u0010A\u0002E\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002\u0014\u0006}\u0005#B\u0018\u0002\u0016\u0006e\u0015bAALa\t1q\n\u001d;j_:\u0004baLAN\u0005\n\u000b\u0016bAAOa\t1A+\u001e9mKNB\u0001\"!) \u0003\u0003\u0005\r\u0001W\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAT!\u0011\t\t#!+\n\t\u0005-\u00161\u0005\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ratelimit/DiskPersistentMeter.class */
public class DiskPersistentMeter extends Meter implements Product, Serializable {
    public static Option<Tuple3<String, String, RateLimit>> unapply(final DiskPersistentMeter x$0) {
        return DiskPersistentMeter$.MODULE$.unapply(x$0);
    }

    public static DiskPersistentMeter apply(final String name, final String client, final RateLimit cfg) {
        return DiskPersistentMeter$.MODULE$.apply(name, client, cfg);
    }

    public static Function1<Tuple3<String, String, RateLimit>, DiskPersistentMeter> tupled() {
        return DiskPersistentMeter$.MODULE$.tupled();
    }

    public static Function1<String, Function1<String, Function1<RateLimit, DiskPersistentMeter>>> curried() {
        return DiskPersistentMeter$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override // code.util.ratelimit.Meter
    public String name() {
        return super.name();
    }

    @Override // code.util.ratelimit.Meter
    public String client() {
        return super.client();
    }

    @Override // code.util.ratelimit.Meter
    public RateLimit cfg() {
        return super.cfg();
    }

    public DiskPersistentMeter copy(final String name, final String client, final RateLimit cfg) {
        return new DiskPersistentMeter(name, client, cfg);
    }

    public String copy$default$1() {
        return name();
    }

    public String copy$default$2() {
        return client();
    }

    public RateLimit copy$default$3() {
        return cfg();
    }

    public String productPrefix() {
        return "DiskPersistentMeter";
    }

    public int productArity() {
        return 3;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return name();
            case 1:
                return client();
            case 2:
                return cfg();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof DiskPersistentMeter;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "name";
            case 1:
                return "client";
            case 2:
                return "cfg";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof DiskPersistentMeter) {
                DiskPersistentMeter diskPersistentMeter = (DiskPersistentMeter) x$1;
                String strName = name();
                String strName2 = diskPersistentMeter.name();
                if (strName != null ? strName.equals(strName2) : strName2 == null) {
                    String strClient = client();
                    String strClient2 = diskPersistentMeter.client();
                    if (strClient != null ? strClient.equals(strClient2) : strClient2 == null) {
                        RateLimit rateLimitCfg = cfg();
                        RateLimit rateLimitCfg2 = diskPersistentMeter.cfg();
                        if (rateLimitCfg != null ? rateLimitCfg.equals(rateLimitCfg2) : rateLimitCfg2 == null) {
                            if (diskPersistentMeter.canEqual(this)) {
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public DiskPersistentMeter(final String name, final String client, final RateLimit cfg) {
        super(name, client, cfg);
        Product.$init$(this);
    }

    private void tryLoad() {
        try {
            load();
        } catch (Throwable th) {
            logger().warn(new StringBuilder(23).append("Loading meter failed: ").append(name()).append(" ").append(client()).toString());
        }
    }

    private Object trySave() {
        try {
            return BoxesRunTime.boxToBoolean(save());
        } catch (Throwable th) {
            logger().warn(new StringBuilder(22).append("Saving meter failed: ").append(name()).append(" ").append(client()).toString());
            return BoxedUnit.UNIT;
        }
    }

    private void load() {
        Tuple2 tuple2;
        Some someLoad = DiskPersistentMeters$.MODULE$.load(this);
        if ((someLoad instanceof Some) && (tuple2 = (Tuple2) someLoad.value()) != null) {
            int remainingReq = tuple2._1$mcI$sp();
            long nextReset = tuple2._2$mcJ$sp();
            load(remainingReq, nextReset);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
    }

    private boolean save() {
        return DiskPersistentMeters$.MODULE$.save(this);
    }

    @Override // code.util.ratelimit.Meter
    public int tick(final long now) {
        try {
            tryLoad();
            return super.tick(now);
        } finally {
            trySave();
        }
    }

    @Override // code.util.ratelimit.Meter
    public int untick() {
        try {
            return super.untick();
        } finally {
            trySave();
        }
    }
}
