package code.limits;

import java.io.Serializable;
import net.liftweb.json.JsonAST;
import net.liftweb.json.JsonDSL$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: PlanLimits.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005ud\u0001B\u000e\u001d\u0001\u0006B\u0001b\u000e\u0001\u0003\u0016\u0004%\t\u0001\u000f\u0005\ty\u0001\u0011\t\u0012)A\u0005s!AQ\b\u0001BK\u0002\u0013\u0005a\b\u0003\u0005C\u0001\tE\t\u0015!\u0003@\u0011\u0015\u0019\u0005\u0001\"\u0001E\u0011\u0015I\u0005\u0001\"\u0001K\u0011\u001dq\u0006!!A\u0005\u0002}CqA\u0019\u0001\u0012\u0002\u0013\u00051\rC\u0004o\u0001E\u0005I\u0011A8\t\u000fE\u0004\u0011\u0011!C!e\"91\u0010AA\u0001\n\u0003A\u0004b\u0002?\u0001\u0003\u0003%\t! \u0005\n\u0003\u000f\u0001\u0011\u0011!C!\u0003\u0013A\u0011\"a\u0006\u0001\u0003\u0003%\t!!\u0007\t\u0013\u0005\r\u0002!!A\u0005B\u0005\u0015\u0002\"CA\u0015\u0001\u0005\u0005I\u0011IA\u0016\u0011%\ti\u0003AA\u0001\n\u0003\ny\u0003C\u0005\u00022\u0001\t\t\u0011\"\u0011\u00024\u001d9\u0011q\u0007\u000f\t\u0002\u0005ebAB\u000e\u001d\u0011\u0003\tY\u0004\u0003\u0004D)\u0011\u0005\u0011q\t\u0005\b\u0003\u0013\"B\u0011AA&\u0011\u001d\ty\u0005\u0006C\u0001\u0003#B\u0011\"a\u0017\u0015\u0003\u0003%\t)!\u0018\t\u0013\u0005\rD#!A\u0005\u0002\u0006\u0015\u0004\"CA:)\u0005\u0005I\u0011BA;\u0005-\tVo\u001c;b+B$\u0017\r^3\u000b\u0005uq\u0012A\u00027j[&$8OC\u0001 \u0003\u0011\u0019w\u000eZ3\u0004\u0001M!\u0001A\t\u0015,!\t\u0019c%D\u0001%\u0015\u0005)\u0013!B:dC2\f\u0017BA\u0014%\u0005\u0019\te.\u001f*fMB\u00111%K\u0005\u0003U\u0011\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002-i9\u0011QF\r\b\u0003]Ej\u0011a\f\u0006\u0003a\u0001\na\u0001\u0010:p_Rt\u0014\"A\u0013\n\u0005M\"\u0013a\u00029bG.\fw-Z\u0005\u0003kY\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!a\r\u0013\u0002#I,W.Y5oS:<'+Z9vKN$8/F\u0001:!\t\u0019#(\u0003\u0002<I\t\u0019\u0011J\u001c;\u0002%I,W.Y5oS:<'+Z9vKN$8\u000fI\u0001\u0012k:$\u0018\u000e\u001c*fg\u0016$8+Z2p]\u0012\u001cX#A \u0011\u0005\r\u0002\u0015BA!%\u0005\u0011auN\\4\u0002%UtG/\u001b7SKN,GoU3d_:$7\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007\u0015;\u0005\n\u0005\u0002G\u00015\tA\u0004C\u00038\u000b\u0001\u0007\u0011\bC\u0003>\u000b\u0001\u0007q(\u0001\u0004u_*\u001bxN\\\u000b\u0002\u0017B\u0011Aj\u0017\b\u0003\u001bbs!AT+\u000f\u0005=\u0013fB\u0001\u0018Q\u0013\u0005\t\u0016a\u00018fi&\u00111\u000bV\u0001\bY&4Go^3c\u0015\u0005\t\u0016B\u0001,X\u0003\u0011Q7o\u001c8\u000b\u0005M#\u0016BA-[\u0003\u001dQ5o\u001c8B'RS!AV,\n\u0005qk&A\u0002&WC2,XM\u0003\u0002Z5\u0006!1m\u001c9z)\r)\u0005-\u0019\u0005\bo\u001d\u0001\n\u00111\u0001:\u0011\u001dit\u0001%AA\u0002}\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001eU\tITmK\u0001g!\t9G.D\u0001i\u0015\tI'.A\u0005v]\u000eDWmY6fI*\u00111\u000eJ\u0001\u000bC:tw\u000e^1uS>t\u0017BA7i\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\u0005\u0001(FA f\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t1\u000f\u0005\u0002us6\tQO\u0003\u0002wo\u0006!A.\u00198h\u0015\u0005A\u0018\u0001\u00026bm\u0006L!A_;\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z\u00039\u0001(o\u001c3vGR,E.Z7f]R$2A`A\u0002!\t\u0019s0C\u0002\u0002\u0002\u0011\u00121!\u00118z\u0011!\t)\u0001DA\u0001\u0002\u0004I\u0014a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\fA)\u0011QBA\n}6\u0011\u0011q\u0002\u0006\u0004\u0003#!\u0013AC2pY2,7\r^5p]&!\u0011QCA\b\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005m\u0011\u0011\u0005\t\u0004G\u0005u\u0011bAA\u0010I\t9!i\\8mK\u0006t\u0007\u0002CA\u0003\u001d\u0005\u0005\t\u0019\u0001@\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004g\u0006\u001d\u0002\u0002CA\u0003\u001f\u0005\u0005\t\u0019A\u001d\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012!O\u0001\ti>\u001cFO]5oOR\t1/\u0001\u0004fcV\fGn\u001d\u000b\u0005\u00037\t)\u0004\u0003\u0005\u0002\u0006I\t\t\u00111\u0001\u007f\u0003-\tVo\u001c;b+B$\u0017\r^3\u0011\u0005\u0019#2\u0003\u0002\u000b#\u0003{\u0001B!a\u0010\u0002F5\u0011\u0011\u0011\t\u0006\u0004\u0003\u0007:\u0018AA5p\u0013\r)\u0014\u0011\t\u000b\u0003\u0003s\t\u0001B\u001a:p[*\u001bxN\u001c\u000b\u0004\u000b\u00065\u0003\"\u0002,\u0017\u0001\u0004Y\u0015a\u00034s_6T5o\u001c8PaR$B!a\u0015\u0002ZA!1%!\u0016F\u0013\r\t9\u0006\n\u0002\u0007\u001fB$\u0018n\u001c8\t\u000bY;\u0002\u0019A&\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000b\u0015\u000by&!\u0019\t\u000b]B\u0002\u0019A\u001d\t\u000buB\u0002\u0019A \u0002\u000fUt\u0017\r\u001d9msR!\u0011qMA8!\u0015\u0019\u0013QKA5!\u0015\u0019\u00131N\u001d@\u0013\r\ti\u0007\n\u0002\u0007)V\u0004H.\u001a\u001a\t\u0011\u0005E\u0014$!AA\u0002\u0015\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t9\bE\u0002u\u0003sJ1!a\u001fv\u0005\u0019y%M[3di\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/limits/QuotaUpdate.class */
public class QuotaUpdate implements Product, Serializable {
    private final int remainingRequests;
    private final long untilResetSeconds;

    public static Option<Tuple2<Object, Object>> unapply(final QuotaUpdate x$0) {
        return QuotaUpdate$.MODULE$.unapply(x$0);
    }

    public static QuotaUpdate apply(final int remainingRequests, final long untilResetSeconds) {
        return QuotaUpdate$.MODULE$.apply(remainingRequests, untilResetSeconds);
    }

    public static Option<QuotaUpdate> fromJsonOpt(final JsonAST.JValue json) {
        return QuotaUpdate$.MODULE$.fromJsonOpt(json);
    }

    public static QuotaUpdate fromJson(final JsonAST.JValue json) {
        return QuotaUpdate$.MODULE$.fromJson(json);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int remainingRequests() {
        return this.remainingRequests;
    }

    public long untilResetSeconds() {
        return this.untilResetSeconds;
    }

    public QuotaUpdate copy(final int remainingRequests, final long untilResetSeconds) {
        return new QuotaUpdate(remainingRequests, untilResetSeconds);
    }

    public int copy$default$1() {
        return remainingRequests();
    }

    public long copy$default$2() {
        return untilResetSeconds();
    }

    public String productPrefix() {
        return "QuotaUpdate";
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(remainingRequests());
            case 1:
                return BoxesRunTime.boxToLong(untilResetSeconds());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof QuotaUpdate;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "remainingRequests";
            case 1:
                return "untilResetSeconds";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), remainingRequests()), Statics.longHash(untilResetSeconds())), 2);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof QuotaUpdate) {
                QuotaUpdate quotaUpdate = (QuotaUpdate) x$1;
                if (remainingRequests() != quotaUpdate.remainingRequests() || untilResetSeconds() != quotaUpdate.untilResetSeconds() || !quotaUpdate.canEqual(this)) {
                }
            }
            return false;
        }
        return true;
    }

    public QuotaUpdate(final int remainingRequests, final long untilResetSeconds) {
        this.remainingRequests = remainingRequests;
        this.untilResetSeconds = untilResetSeconds;
        Product.$init$(this);
    }

    public JsonAST.JValue toJson() {
        return JsonDSL$.MODULE$.pair2Assoc(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("remainingRequests"), BoxesRunTime.boxToInteger(remainingRequests())), x -> {
            return $anonfun$toJson$1(BoxesRunTime.unboxToInt(x));
        }).$tilde(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("untilResetSeconds"), BoxesRunTime.boxToLong(untilResetSeconds())), x2 -> {
            return $anonfun$toJson$2(BoxesRunTime.unboxToLong(x2));
        });
    }

    public static final /* synthetic */ JsonAST.JInt $anonfun$toJson$1(final int x) {
        return JsonDSL$.MODULE$.int2jvalue(x);
    }

    public static final /* synthetic */ JsonAST.JInt $anonfun$toJson$2(final long x) {
        return JsonDSL$.MODULE$.long2jvalue(x);
    }
}
