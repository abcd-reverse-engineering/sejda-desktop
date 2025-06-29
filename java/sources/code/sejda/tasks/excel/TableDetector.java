package code.sejda.tasks.excel;

import code.util.Loggable;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.text.TextPosition;
import org.slf4j.Logger;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ListBuffer;
import scala.collection.mutable.ListBuffer$;
import scala.math.Ordering$Int$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ObjectRef;

/* compiled from: TableDetector.scala */
@ScalaSignature(bytes = "\u0006\u000554A!\u0002\u0004\u0001\u001f!)\u0001\u0005\u0001C\u0001C!)1\u0005\u0001C\u0001I!)Q\n\u0001C\u0005\u001d\")\u0011\f\u0001C\u00055\niA+\u00192mK\u0012+G/Z2u_JT!a\u0002\u0005\u0002\u000b\u0015D8-\u001a7\u000b\u0005%Q\u0011!\u0002;bg.\u001c(BA\u0006\r\u0003\u0015\u0019XM\u001b3b\u0015\u0005i\u0011\u0001B2pI\u0016\u001c\u0001a\u0005\u0003\u0001!YQ\u0002CA\t\u0015\u001b\u0005\u0011\"\"A\n\u0002\u000bM\u001c\u0017\r\\1\n\u0005U\u0011\"AB!osJ+g\r\u0005\u0002\u001815\ta!\u0003\u0002\u001a\r\t)Q\u000b^5mgB\u00111DH\u0007\u00029)\u0011Q\u0004D\u0001\u0005kRLG.\u0003\u0002 9\tAAj\\4hC\ndW-\u0001\u0004=S:LGO\u0010\u000b\u0002EA\u0011q\u0003A\u0001\u0007I\u0016$Xm\u0019;\u0015\u0007\u0015zD\tE\u0002']Er!a\n\u0017\u000f\u0005!ZS\"A\u0015\u000b\u0005)r\u0011A\u0002\u001fs_>$h(C\u0001\u0014\u0013\ti##A\u0004qC\u000e\\\u0017mZ3\n\u0005=\u0002$\u0001\u0002'jgRT!!\f\n\u0011\u0005IjT\"A\u001a\u000b\u0005\u001d!$BA\u001b7\u0003%\u0019w.\u001c9p]\u0016tGO\u0003\u00028q\u000511/Y7c_bT!!\u000f\u001e\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u0017mR\u0011\u0001P\u0001\u0004_J<\u0017B\u0001 4\u0005%!\u0015\r^1UC\ndW\rC\u0003A\u0005\u0001\u0007\u0011)\u0001\u0006qC\u001e,g*^7cKJ\u0004\"!\u0005\"\n\u0005\r\u0013\"aA%oi\")QI\u0001a\u0001\r\u0006\u0019Am\\2\u0011\u0005\u001d[U\"\u0001%\u000b\u0005%S\u0015a\u00029e[>$W\r\u001c\u0006\u0003oiJ!\u0001\u0014%\u0003\u0015A#Ei\\2v[\u0016tG/A\rdC2\u001cW\u000f\\1uK\n+8m[3uK\u00124\u0016M]5b]\u000e,GcA(S/B\u0011\u0011\u0003U\u0005\u0003#J\u0011A\u0001T8oO\")1k\u0001a\u0001)\u00061a/\u00197vKN\u00042AJ+P\u0013\t1\u0006GA\u0002TKFDQ\u0001W\u0002A\u0002\u0005\u000baAY;dW\u0016$\u0018AC:qY&$xk\u001c:egR\u00111L\u001b\t\u00049\u0006\u001cW\"A/\u000b\u0005y{\u0016aB7vi\u0006\u0014G.\u001a\u0006\u0003AJ\t!bY8mY\u0016\u001cG/[8o\u0013\t\u0011WL\u0001\u0006MSN$()\u001e4gKJ\u00042\u0001X1e!\t)\u0007.D\u0001g\u0015\t9'*\u0001\u0003uKb$\u0018BA5g\u00051!V\r\u001f;Q_NLG/[8o\u0011\u0015YG\u00011\u0001m\u0003\r!\bo\u001d\t\u0004MU#\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/TableDetector.class */
public class TableDetector implements Utils, Loggable {
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    public static final /* synthetic */ void $anonfun$detect$1(final Text t) {
    }

    @Override // code.sejda.tasks.excel.Utils
    public boolean areDeltaEqual(final float v1, final float v2, final float delta) {
        return areDeltaEqual(v1, v2, delta);
    }

    @Override // code.sejda.tasks.excel.Utils
    public float areDeltaEqual$default$3() {
        return areDeltaEqual$default$3();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.excel.TableDetector] */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$trans$0) {
                this.logger = logger();
                r0 = this;
                r0.bitmap$trans$0 = true;
            }
        }
        return this.logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !this.bitmap$trans$0 ? logger$lzycompute() : this.logger;
    }

    public TableDetector() {
        Utils.$init$(this);
        Loggable.$init$(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x012f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public scala.collection.immutable.List<org.sejda.impl.sambox.component.excel.DataTable> detect(final int r9, final org.sejda.sambox.pdmodel.PDDocument r10) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.excel.TableDetector.detect(int, org.sejda.sambox.pdmodel.PDDocument):scala.collection.immutable.List");
    }

    private long calculateBucketedVariance(final Seq<Object> values, final int bucket) {
        Seq bucketedVals = (Seq) values.map(value -> {
            long modulo = value % bucket;
            return modulo == 0 ? value : value + (bucket - modulo);
        });
        long mostOccurring = ((Tuple2) bucketedVals.groupBy(x -> {
            return BoxesRunTime.unboxToLong(Predef$.MODULE$.identity(BoxesRunTime.boxToLong(x)));
        }).mapValues(x$3 -> {
            return BoxesRunTime.boxToInteger(x$3.size());
        }).maxBy(x$4 -> {
            return BoxesRunTime.boxToInteger(x$4._2$mcI$sp());
        }, Ordering$Int$.MODULE$))._1$mcJ$sp();
        return mostOccurring;
    }

    public ListBuffer<ListBuffer<TextPosition>> code$sejda$tasks$excel$TableDetector$$splitWords(final Seq<TextPosition> tps) {
        ListBuffer results = (ListBuffer) ListBuffer$.MODULE$.apply(Nil$.MODULE$);
        ObjectRef prev = ObjectRef.create((Object) null);
        ObjectRef prevPrev = ObjectRef.create((Object) null);
        ObjectRef current = ObjectRef.create((ListBuffer) ListBuffer$.MODULE$.apply(Nil$.MODULE$));
        tps.foreach(x0$1 -> {
            String unicode = x0$1.getUnicode();
            if (unicode != null ? unicode.equals("\t") : "\t" == 0) {
                return BoxedUnit.UNIT;
            }
            if (((TextPosition) prev.elem) != null) {
                boolean whitespaceAfterWhitespace = isWhitespace$1((TextPosition) prev.elem) && isWhitespace$1(x0$1);
                boolean differentColor = !areSame$1(x0$1.getColor(), ((TextPosition) prev.elem).getColor());
                boolean differentLine = !this.areDeltaEqual(((TextPosition) prev.elem).getY(), x0$1.getY(), 4.0f);
                float distance = (x0$1.getX() - ((TextPosition) prev.elem).getX()) - ((TextPosition) prev.elem).getWidth();
                if (distance > 2 * x0$1.getWidthOfSpace() || differentLine || whitespaceAfterWhitespace || differentColor) {
                    results.$plus$eq((ListBuffer) current.elem);
                    current.elem = (ListBuffer) ListBuffer$.MODULE$.apply(Nil$.MODULE$);
                } else if (((TextPosition) prevPrev.elem) != null) {
                    boolean prevWhitespace = isWhitespace$1((TextPosition) prev.elem);
                    float distance2 = (x0$1.getX() - ((TextPosition) prevPrev.elem).getX()) - ((TextPosition) prevPrev.elem).getWidth();
                    if (prevWhitespace && distance2 > 2 * ((TextPosition) prevPrev.elem).getWidthOfSpace()) {
                        ((ListBuffer) current.elem).$minus$eq((TextPosition) prev.elem);
                        results.$plus$eq((ListBuffer) current.elem);
                        current.elem = (ListBuffer) ListBuffer$.MODULE$.apply(Nil$.MODULE$);
                    }
                }
            }
            prevPrev.elem = (TextPosition) prev.elem;
            prev.elem = x0$1;
            return ((ListBuffer) current.elem).$plus$eq(x0$1);
        });
        if (!((ListBuffer) current.elem).nonEmpty()) {
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            results.$plus$eq((ListBuffer) current.elem);
        }
        return (ListBuffer) results.filter(x$7 -> {
            return BoxesRunTime.boxToBoolean(x$7.nonEmpty());
        });
    }

    private static final boolean isWhitespace$1(final TextPosition tp) {
        return tp.getUnicode().trim().isEmpty();
    }

    private static final boolean areSame$1(final PDColor c1, final PDColor c2) {
        Option map = Option$.MODULE$.apply(c1).map(x$5 -> {
            return x$5.toString();
        });
        Option map2 = Option$.MODULE$.apply(c2).map(x$6 -> {
            return x$6.toString();
        });
        return map != null ? map.equals(map2) : map2 == null;
    }
}
