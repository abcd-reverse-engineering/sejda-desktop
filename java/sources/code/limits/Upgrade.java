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
@ScalaSignature(bytes = "\u0006\u0005\u0005me\u0001\u0002\u0010 \u0001\u0012B\u0001B\u000f\u0001\u0003\u0016\u0004%\ta\u000f\u0005\t\t\u0002\u0011\t\u0012)A\u0005y!AQ\t\u0001BK\u0002\u0013\u0005a\t\u0003\u0005N\u0001\tE\t\u0015!\u0003H\u0011\u0015q\u0005\u0001\"\u0001P\u0011\u0015!\u0006\u0001\"\u0001V\u0011\u0015I\u0007\u0001\"\u0001V\u0011\u001dQ\u0007!!A\u0005\u0002-DqA\u001c\u0001\u0012\u0002\u0013\u0005q\u000eC\u0004{\u0001E\u0005I\u0011A>\t\u000fu\u0004\u0011\u0011!C!}\"I\u0011Q\u0002\u0001\u0002\u0002\u0013\u0005\u0011q\u0002\u0005\n\u0003/\u0001\u0011\u0011!C\u0001\u00033A\u0011\"!\n\u0001\u0003\u0003%\t%a\n\t\u0013\u0005U\u0002!!A\u0005\u0002\u0005]\u0002\"CA!\u0001\u0005\u0005I\u0011IA\"\u0011%\t9\u0005AA\u0001\n\u0003\nI\u0005C\u0005\u0002L\u0001\t\t\u0011\"\u0011\u0002N!I\u0011q\n\u0001\u0002\u0002\u0013\u0005\u0013\u0011K\u0004\b\u0003+z\u0002\u0012AA,\r\u0019qr\u0004#\u0001\u0002Z!1a*\u0006C\u0001\u0003KBq!a\u001a\u0016\t\u0003\tI\u0007C\u0004\u0002nU!\t!a\u001c\t\u0013\u0005UT#!A\u0005\u0002\u0006]\u0004\u0002CA?+E\u0005I\u0011A>\t\u0013\u0005}T#!A\u0005\u0002\u0006\u0005\u0005\u0002CAH+E\u0005I\u0011A>\t\u0013\u0005EU#!A\u0005\n\u0005M%aB+qOJ\fG-\u001a\u0006\u0003A\u0005\na\u0001\\5nSR\u001c(\"\u0001\u0012\u0002\t\r|G-Z\u0002\u0001'\u0011\u0001Qe\u000b\u0018\u0011\u0005\u0019JS\"A\u0014\u000b\u0003!\nQa]2bY\u0006L!AK\u0014\u0003\r\u0005s\u0017PU3g!\t1C&\u0003\u0002.O\t9\u0001K]8ek\u000e$\bCA\u00188\u001d\t\u0001TG\u0004\u00022i5\t!G\u0003\u00024G\u00051AH]8pizJ\u0011\u0001K\u0005\u0003m\u001d\nq\u0001]1dW\u0006<W-\u0003\u00029s\ta1+\u001a:jC2L'0\u00192mK*\u0011agJ\u0001\u0007e\u0016\f7o\u001c8\u0016\u0003q\u0002\"!P!\u000f\u0005yz\u0004CA\u0019(\u0013\t\u0001u%\u0001\u0004Qe\u0016$WMZ\u0005\u0003\u0005\u000e\u0013aa\u0015;sS:<'B\u0001!(\u0003\u001d\u0011X-Y:p]\u0002\n\u0011#\u001e8uS2\u0014Vm]3u'\u0016\u001cwN\u001c3t+\u00059\u0005c\u0001\u0014I\u0015&\u0011\u0011j\n\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005\u0019Z\u0015B\u0001'(\u0005\u0011auN\\4\u0002%UtG/\u001b7SKN,GoU3d_:$7\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007A\u00136\u000b\u0005\u0002R\u00015\tq\u0004C\u0003;\u000b\u0001\u0007A\bC\u0004F\u000bA\u0005\t\u0019A$\u0002\u0013Q|'j]8o\u000bb$X#\u0001,\u0011\u0005]3gB\u0001-d\u001d\tI\u0006M\u0004\u0002[;:\u0011\u0011gW\u0005\u00029\u0006\u0019a.\u001a;\n\u0005y{\u0016a\u00027jMR<XM\u0019\u0006\u00029&\u0011\u0011MY\u0001\u0005UN|gN\u0003\u0002_?&\u0011A-Z\u0001\b\u0015N|g.Q*U\u0015\t\t'-\u0003\u0002hQ\n1!JV1mk\u0016T!\u0001Z3\u0002\rQ|'j]8o\u0003\u0011\u0019w\u000e]=\u0015\u0007AcW\u000eC\u0004;\u0011A\u0005\t\u0019\u0001\u001f\t\u000f\u0015C\u0001\u0013!a\u0001\u000f\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u00019+\u0005q\n8&\u0001:\u0011\u0005MDX\"\u0001;\u000b\u0005U4\u0018!C;oG\",7m[3e\u0015\t9x%\u0001\u0006b]:|G/\u0019;j_:L!!\u001f;\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003qT#aR9\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005y\b\u0003BA\u0001\u0003\u0017i!!a\u0001\u000b\t\u0005\u0015\u0011qA\u0001\u0005Y\u0006twM\u0003\u0002\u0002\n\u0005!!.\u0019<b\u0013\r\u0011\u00151A\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003#\u00012AJA\n\u0013\r\t)b\n\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u00037\t\t\u0003E\u0002'\u0003;I1!a\b(\u0005\r\te.\u001f\u0005\n\u0003Gi\u0011\u0011!a\u0001\u0003#\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u0015!\u0019\tY#!\r\u0002\u001c5\u0011\u0011Q\u0006\u0006\u0004\u0003_9\u0013AC2pY2,7\r^5p]&!\u00111GA\u0017\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005e\u0012q\b\t\u0004M\u0005m\u0012bAA\u001fO\t9!i\\8mK\u0006t\u0007\"CA\u0012\u001f\u0005\u0005\t\u0019AA\u000e\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0007}\f)\u0005C\u0005\u0002$A\t\t\u00111\u0001\u0002\u0012\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\u0012\u0005AAo\\*ue&tw\rF\u0001��\u0003\u0019)\u0017/^1mgR!\u0011\u0011HA*\u0011%\t\u0019cEA\u0001\u0002\u0004\tY\"A\u0004Va\u001e\u0014\u0018\rZ3\u0011\u0005E+2\u0003B\u000b&\u00037\u0002B!!\u0018\u0002d5\u0011\u0011q\f\u0006\u0005\u0003C\n9!\u0001\u0002j_&\u0019\u0001(a\u0018\u0015\u0005\u0005]\u0013\u0001\u00034s_6T5o\u001c8\u0015\u0007A\u000bY\u0007C\u0003b/\u0001\u0007a+A\u0006ge>l'j]8o\u001fB$H\u0003BA9\u0003g\u00022A\n%Q\u0011\u0015\t\u0007\u00041\u0001W\u0003\u0015\t\u0007\u000f\u001d7z)\u0015\u0001\u0016\u0011PA>\u0011\u0015Q\u0014\u00041\u0001=\u0011\u001d)\u0015\u0004%AA\u0002\u001d\u000bq\"\u00199qYf$C-\u001a4bk2$HEM\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t\u0019)a#\u0011\t\u0019B\u0015Q\u0011\t\u0006M\u0005\u001dEhR\u0005\u0004\u0003\u0013;#A\u0002+va2,'\u0007\u0003\u0005\u0002\u000en\t\t\u00111\u0001Q\u0003\rAH\u0005M\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001a\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005U\u0005\u0003BA\u0001\u0003/KA!!'\u0002\u0004\t1qJ\u00196fGR\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/limits/Upgrade.class */
public class Upgrade implements Product, Serializable {
    private final String reason;
    private final Option<Object> untilResetSeconds;

    public static Option<Tuple2<String, Option<Object>>> unapply(final Upgrade x$0) {
        return Upgrade$.MODULE$.unapply(x$0);
    }

    public static Upgrade apply(final String reason, final Option<Object> untilResetSeconds) {
        return Upgrade$.MODULE$.apply(reason, untilResetSeconds);
    }

    public static Option<Upgrade> fromJsonOpt(final JsonAST.JValue json) {
        return Upgrade$.MODULE$.fromJsonOpt(json);
    }

    public static Upgrade fromJson(final JsonAST.JValue json) {
        return Upgrade$.MODULE$.fromJson(json);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String reason() {
        return this.reason;
    }

    public Option<Object> untilResetSeconds() {
        return this.untilResetSeconds;
    }

    public Upgrade copy(final String reason, final Option<Object> untilResetSeconds) {
        return new Upgrade(reason, untilResetSeconds);
    }

    public String copy$default$1() {
        return reason();
    }

    public Option<Object> copy$default$2() {
        return untilResetSeconds();
    }

    public String productPrefix() {
        return "Upgrade";
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return reason();
            case 1:
                return untilResetSeconds();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof Upgrade;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "reason";
            case 1:
                return "untilResetSeconds";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof Upgrade) {
                Upgrade upgrade = (Upgrade) x$1;
                String strReason = reason();
                String strReason2 = upgrade.reason();
                if (strReason != null ? strReason.equals(strReason2) : strReason2 == null) {
                    Option<Object> optionUntilResetSeconds = untilResetSeconds();
                    Option<Object> optionUntilResetSeconds2 = upgrade.untilResetSeconds();
                    if (optionUntilResetSeconds != null ? optionUntilResetSeconds.equals(optionUntilResetSeconds2) : optionUntilResetSeconds2 == null) {
                        if (upgrade.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public Upgrade(final String reason, final Option<Object> untilResetSeconds) {
        this.reason = reason;
        this.untilResetSeconds = untilResetSeconds;
        Product.$init$(this);
    }

    public JsonAST.JValue toJsonExt() {
        return JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("upgrade"), JsonDSL$.MODULE$.pair2Assoc(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("reason"), reason()), x -> {
            return JsonDSL$.MODULE$.string2jvalue(x);
        }).$tilde(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("untilResetSeconds"), untilResetSeconds()), opt -> {
            return JsonDSL$.MODULE$.option2jvalue(opt, x2 -> {
                return $anonfun$toJsonExt$3(BoxesRunTime.unboxToLong(x2));
            });
        })), Predef$.MODULE$.$conforms());
    }

    public static final /* synthetic */ JsonAST.JInt $anonfun$toJsonExt$3(final long x) {
        return JsonDSL$.MODULE$.long2jvalue(x);
    }

    public JsonAST.JValue toJson() {
        return JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("reason"), reason()), x -> {
            return JsonDSL$.MODULE$.string2jvalue(x);
        });
    }
}
