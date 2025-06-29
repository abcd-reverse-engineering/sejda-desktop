package code.sejda.tasks.excel;

import org.sejda.commons.util.StringUtils;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.sambox.text.TextPosition;
import scala.MatchError;
import scala.Option;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.Seq;
import scala.math.Ordering$DeprecatedFloatOrdering$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

/* compiled from: TableDetector.scala */
@ScalaSignature(bytes = "\u0006\u0005u<Q!\u0006\f\t\u0002}1Q!\t\f\t\u0002\tBQ!K\u0001\u0005\u0002)BQaK\u0001\u0005\u00021BQA_\u0001\u0005\u0002m4A!\t\f\u0001c!AQ'\u0002B\u0001B\u0003%a\u0007C\u0003*\u000b\u0011\u0005Q\nC\u0004P\u000b\t\u0007I\u0011\u0001)\t\rQ+\u0001\u0015!\u0003R\u0011\u001d)VA1A\u0005\u0002ACaAV\u0003!\u0002\u0013\t\u0006bB,\u0006\u0005\u0004%\t\u0001\u0015\u0005\u00071\u0016\u0001\u000b\u0011B)\t\u000fe+!\u0019!C\u0001!\"1!,\u0002Q\u0001\nECq!R\u0003C\u0002\u0013\u00051\f\u0003\u0004e\u000b\u0001\u0006I\u0001\u0018\u0005\u0006K\u0016!\tE\u001a\u0005\u0006]\u0016!\ta\u0017\u0005\u0006_\u0016!\t\u0001]\u0001\u0005)\u0016DHO\u0003\u0002\u00181\u0005)Q\r_2fY*\u0011\u0011DG\u0001\u0006i\u0006\u001c8n\u001d\u0006\u00037q\tQa]3kI\u0006T\u0011!H\u0001\u0005G>$Wm\u0001\u0001\u0011\u0005\u0001\nQ\"\u0001\f\u0003\tQ+\u0007\u0010^\n\u0003\u0003\r\u0002\"\u0001J\u0014\u000e\u0003\u0015R\u0011AJ\u0001\u0006g\u000e\fG.Y\u0005\u0003Q\u0015\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001 \u0003\ry\u0007\u000f\u001e\u000b\u0003[e\u00042\u0001\n\u00181\u0013\tySE\u0001\u0004PaRLwN\u001c\t\u0003A\u0015\u00192!B\u00123!\t\u00013'\u0003\u00025-\tIBk\u001c9MK\u001a$(+Z2uC:<W\u000f\\1s\u0005>DH*[6f\u0003\r!\bo\u001d\t\u0004o}\u0012eB\u0001\u001d>\u001d\tID(D\u0001;\u0015\tYd$\u0001\u0004=e>|GOP\u0005\u0002M%\u0011a(J\u0001\ba\u0006\u001c7.Y4f\u0013\t\u0001\u0015IA\u0002TKFT!AP\u0013\u0011\u0005\r[U\"\u0001#\u000b\u0005\u00153\u0015\u0001\u0002;fqRT!a\u0012%\u0002\rM\fWNY8y\u0015\tY\u0012JC\u0001K\u0003\ry'oZ\u0005\u0003\u0019\u0012\u0013A\u0002V3yiB{7/\u001b;j_:$\"\u0001\r(\t\u000bU:\u0001\u0019\u0001\u001c\u0002\u0007Q|\u0007/F\u0001R!\t!#+\u0003\u0002TK\t)a\t\\8bi\u0006!Ao\u001c9!\u0003\u0011aWM\u001a;\u0002\u000b1,g\r\u001e\u0011\u0002\u000bILw\r\u001b;\u0002\rILw\r\u001b;!\u0003\u0019\u0011w\u000e\u001e;p[\u00069!m\u001c;u_6\u0004S#\u0001/\u0011\u0005u\u0013W\"\u00010\u000b\u0005}\u0003\u0017\u0001\u00027b]\u001eT\u0011!Y\u0001\u0005U\u00064\u0018-\u0003\u0002d=\n11\u000b\u001e:j]\u001e\fQ\u0001^3yi\u0002\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002OB\u0011\u0001\u000e\u001c\b\u0003S*\u0004\"!O\u0013\n\u0005-,\u0013A\u0002)sK\u0012,g-\u0003\u0002d[*\u00111.J\u0001\u000ei>\u001cFO]5oO\u0012+'-^4\u0002\r\u0011Lg/\u001b3f)\t\tH\u000f\u0005\u0003%e6j\u0013BA:&\u0005\u0019!V\u000f\u001d7fe!)Q\u000f\u0006a\u0001m\u00061Q.\u0019:hS:\u0004\"\u0001J<\n\u0005a,#aA%oi\")Qg\u0001a\u0001m\u0005)\u0011\r\u001d9msR\u0011\u0001\u0007 \u0005\u0006k\u0011\u0001\rA\u000e")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/Text.class */
public class Text implements TopLeftRectangularBoxLike {
    private final Seq<TextPosition> tps;
    private final float top;
    private final float left;
    private final float right;
    private final float bottom;
    private final String text;

    public static Text apply(final Seq<TextPosition> tps) {
        return Text$.MODULE$.apply(tps);
    }

    public static Option<Text> opt(final Seq<TextPosition> tps) {
        return Text$.MODULE$.opt(tps);
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float width() {
        return width();
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float height() {
        return height();
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public TopLeftRectangularBox asTopLeftRectangularBox() {
        return asTopLeftRectangularBox();
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public String toStringBox() {
        return toStringBox();
    }

    public Text(final Seq<TextPosition> tps) {
        this.tps = tps;
        TopLeftRectangularBoxLike.$init$(this);
        this.top = BoxesRunTime.unboxToFloat(((IterableOnceOps) tps.map(x$8 -> {
            return BoxesRunTime.boxToFloat(x$8.getY());
        })).min(Ordering$DeprecatedFloatOrdering$.MODULE$));
        this.left = BoxesRunTime.unboxToFloat(((IterableOnceOps) tps.map(x$9 -> {
            return BoxesRunTime.boxToFloat(x$9.getX());
        })).min(Ordering$DeprecatedFloatOrdering$.MODULE$));
        this.right = BoxesRunTime.unboxToFloat(((IterableOnceOps) tps.map(tp -> {
            return BoxesRunTime.boxToFloat($anonfun$right$1(tp));
        })).max(Ordering$DeprecatedFloatOrdering$.MODULE$));
        this.bottom = BoxesRunTime.unboxToFloat(((IterableOnceOps) tps.map(tp2 -> {
            return BoxesRunTime.boxToFloat($anonfun$bottom$1(tp2));
        })).max(Ordering$DeprecatedFloatOrdering$.MODULE$));
        this.text = StringUtils.normalizeWhitespace(((IterableOnceOps) tps.map(x$10 -> {
            return x$10.getUnicode();
        })).mkString(""));
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float top() {
        return this.top;
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float left() {
        return this.left;
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float right() {
        return this.right;
    }

    public static final /* synthetic */ float $anonfun$right$1(final TextPosition tp) {
        return tp.getWidth() + tp.getX();
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float bottom() {
        return this.bottom;
    }

    public static final /* synthetic */ float $anonfun$bottom$1(final TextPosition tp) {
        return tp.getHeight() + tp.getY();
    }

    public String text() {
        return this.text;
    }

    public String toString() {
        return text();
    }

    public String toStringDebug() {
        return new StringBuilder(1).append(toStringBox()).append(" ").append(text()).toString();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public Tuple2<Option<Text>, Option<Text>> divide(final int margin) throws MatchError {
        Tuple2 tuple2Partition = this.tps.partition(tp -> {
            return BoxesRunTime.boxToBoolean($anonfun$divide$1(margin, tp));
        });
        if (tuple2Partition == null) {
            throw new MatchError(tuple2Partition);
        }
        Seq left = (Seq) tuple2Partition._1();
        Seq right = (Seq) tuple2Partition._2();
        Tuple2 tuple2 = new Tuple2(left, right);
        Seq left2 = (Seq) tuple2._1();
        Seq right2 = (Seq) tuple2._2();
        return new Tuple2<>(Text$.MODULE$.opt(left2), Text$.MODULE$.opt(right2));
    }

    public static final /* synthetic */ boolean $anonfun$divide$1(final int margin$1, final TextPosition tp) {
        return tp.getX() < ((float) margin$1);
    }
}
