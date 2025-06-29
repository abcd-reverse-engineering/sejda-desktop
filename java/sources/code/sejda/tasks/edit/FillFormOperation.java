package code.sejda.tasks.edit;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005ec\u0001B\r\u001b\u0001\u000eB\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t\u0007\u0002\u0011\t\u0012)A\u0005w!AA\t\u0001BK\u0002\u0013\u0005!\b\u0003\u0005F\u0001\tE\t\u0015!\u0003<\u0011\u00151\u0005\u0001\"\u0001H\u0011\u001da\u0005!!A\u0005\u00025Cq\u0001\u0015\u0001\u0012\u0002\u0013\u0005\u0011\u000bC\u0004]\u0001E\u0005I\u0011A)\t\u000fu\u0003\u0011\u0011!C!=\"9a\rAA\u0001\n\u00039\u0007bB6\u0001\u0003\u0003%\t\u0001\u001c\u0005\be\u0002\t\t\u0011\"\u0011t\u0011\u001dQ\b!!A\u0005\u0002mD\u0011\"!\u0001\u0001\u0003\u0003%\t%a\u0001\t\u0013\u0005\u001d\u0001!!A\u0005B\u0005%\u0001\"CA\u0006\u0001\u0005\u0005I\u0011IA\u0007\u0011%\ty\u0001AA\u0001\n\u0003\n\tbB\u0005\u0002\u0016i\t\t\u0011#\u0001\u0002\u0018\u0019A\u0011DGA\u0001\u0012\u0003\tI\u0002\u0003\u0004G'\u0011\u0005\u0011\u0011\u0007\u0005\n\u0003\u0017\u0019\u0012\u0011!C#\u0003\u001bA\u0011\"a\r\u0014\u0003\u0003%\t)!\u000e\t\u0013\u0005m2#!A\u0005\u0002\u0006u\u0002\"CA('\u0005\u0005I\u0011BA)\u0005E1\u0015\u000e\u001c7G_Jlw\n]3sCRLwN\u001c\u0006\u00037q\tA!\u001a3ji*\u0011QDH\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003?\u0001\nQa]3kI\u0006T\u0011!I\u0001\u0005G>$Wm\u0001\u0001\u0014\t\u0001!#&\f\t\u0003K!j\u0011A\n\u0006\u0002O\u0005)1oY1mC&\u0011\u0011F\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\u0015Z\u0013B\u0001\u0017'\u0005\u001d\u0001&o\u001c3vGR\u0004\"A\f\u001c\u000f\u0005=\"dB\u0001\u00194\u001b\u0005\t$B\u0001\u001a#\u0003\u0019a$o\\8u}%\tq%\u0003\u00026M\u00059\u0001/Y2lC\u001e,\u0017BA\u001c9\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t)d%\u0001\u0003oC6,W#A\u001e\u0011\u0005q\u0002eBA\u001f?!\t\u0001d%\u0003\u0002@M\u00051\u0001K]3eK\u001aL!!\u0011\"\u0003\rM#(/\u001b8h\u0015\tyd%A\u0003oC6,\u0007%A\u0003wC2,X-\u0001\u0004wC2,X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007!S5\n\u0005\u0002J\u00015\t!\u0004C\u0003:\u000b\u0001\u00071\bC\u0003E\u000b\u0001\u00071(\u0001\u0003d_BLHc\u0001%O\u001f\"9\u0011H\u0002I\u0001\u0002\u0004Y\u0004b\u0002#\u0007!\u0003\u0005\raO\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005\u0011&FA\u001eTW\u0005!\u0006CA+[\u001b\u00051&BA,Y\u0003%)hn\u00195fG.,GM\u0003\u0002ZM\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005m3&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012\u0014!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001`!\t\u0001W-D\u0001b\u0015\t\u00117-\u0001\u0003mC:<'\"\u00013\u0002\t)\fg/Y\u0005\u0003\u0003\u0006\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012\u0001\u001b\t\u0003K%L!A\u001b\u0014\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u00055\u0004\bCA\u0013o\u0013\tygEA\u0002B]fDq!]\u0006\u0002\u0002\u0003\u0007\u0001.A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002iB\u0019Q\u000f_7\u000e\u0003YT!a\u001e\u0014\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002zm\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\tax\u0010\u0005\u0002&{&\u0011aP\n\u0002\b\u0005>|G.Z1o\u0011\u001d\tX\"!AA\u00025\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019q,!\u0002\t\u000fEt\u0011\u0011!a\u0001Q\u0006A\u0001.Y:i\u0007>$W\rF\u0001i\u0003!!xn\u0015;sS:<G#A0\u0002\r\u0015\fX/\u00197t)\ra\u00181\u0003\u0005\bcF\t\t\u00111\u0001n\u0003E1\u0015\u000e\u001c7G_Jlw\n]3sCRLwN\u001c\t\u0003\u0013N\u0019RaEA\u000e\u0003O\u0001r!!\b\u0002$mZ\u0004*\u0004\u0002\u0002 )\u0019\u0011\u0011\u0005\u0014\u0002\u000fI,h\u000e^5nK&!\u0011QEA\u0010\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gN\r\t\u0005\u0003S\ty#\u0004\u0002\u0002,)\u0019\u0011QF2\u0002\u0005%|\u0017bA\u001c\u0002,Q\u0011\u0011qC\u0001\u0006CB\u0004H.\u001f\u000b\u0006\u0011\u0006]\u0012\u0011\b\u0005\u0006sY\u0001\ra\u000f\u0005\u0006\tZ\u0001\raO\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\ty$a\u0013\u0011\u000b\u0015\n\t%!\u0012\n\u0007\u0005\rcE\u0001\u0004PaRLwN\u001c\t\u0006K\u0005\u001d3hO\u0005\u0004\u0003\u00132#A\u0002+va2,'\u0007\u0003\u0005\u0002N]\t\t\u00111\u0001I\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003'\u00022\u0001YA+\u0013\r\t9&\u0019\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/FillFormOperation.class */
public class FillFormOperation implements Product, Serializable {
    private final String name;
    private final String value;

    public static Option<Tuple2<String, String>> unapply(final FillFormOperation x$0) {
        return FillFormOperation$.MODULE$.unapply(x$0);
    }

    public static FillFormOperation apply(final String name, final String value) {
        return FillFormOperation$.MODULE$.apply(name, value);
    }

    public static Function1<Tuple2<String, String>, FillFormOperation> tupled() {
        return FillFormOperation$.MODULE$.tupled();
    }

    public static Function1<String, Function1<String, FillFormOperation>> curried() {
        return FillFormOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String name() {
        return this.name;
    }

    public String value() {
        return this.value;
    }

    public FillFormOperation copy(final String name, final String value) {
        return new FillFormOperation(name, value);
    }

    public String copy$default$1() {
        return name();
    }

    public String copy$default$2() {
        return value();
    }

    public String productPrefix() {
        return "FillFormOperation";
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return name();
            case 1:
                return value();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof FillFormOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "name";
            case 1:
                return "value";
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
            if (x$1 instanceof FillFormOperation) {
                FillFormOperation fillFormOperation = (FillFormOperation) x$1;
                String strName = name();
                String strName2 = fillFormOperation.name();
                if (strName != null ? strName.equals(strName2) : strName2 == null) {
                    String strValue = value();
                    String strValue2 = fillFormOperation.value();
                    if (strValue != null ? strValue.equals(strValue2) : strValue2 == null) {
                        if (fillFormOperation.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public FillFormOperation(final String name, final String value) {
        this.name = name;
        this.value = value;
        Product.$init$(this);
    }
}
