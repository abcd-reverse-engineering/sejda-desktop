package code.sejda.tasks.excel;

import java.io.Serializable;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLine;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.mutable.ListBuffer;
import scala.collection.mutable.ListBuffer$;
import scala.math.Ordering$DeprecatedFloatOrdering$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: TableDetector.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005=e\u0001B\u0010!\u0001&B\u0001b\u0011\u0001\u0003\u0016\u0004%\t\u0001\u0012\u0005\t\u0011\u0002\u0011\t\u0012)A\u0005\u000b\")\u0011\n\u0001C\u0001\u0015\"9Q\n\u0001b\u0001\n\u0003q\u0005BB,\u0001A\u0003%q\nC\u0003Y\u0001\u0011\u0005\u0011\fC\u0003^\u0001\u0011\u0005a\fC\u0003c\u0001\u0011\u0005a\fC\u0003d\u0001\u0011\u0005a\fC\u0003e\u0001\u0011\u0005a\fC\u0003f\u0001\u0011\u0005a\rC\u0003j\u0001\u0011\u0005#\u000eC\u0003t\u0001\u0011\u0005A\u000fC\u0004}\u0001\u0005\u0005I\u0011A?\t\u0011}\u0004\u0011\u0013!C\u0001\u0003\u0003A\u0001\"a\u0006\u0001\u0003\u0003%\t\u0005\u001e\u0005\t\u00033\u0001\u0011\u0011!C\u00013\"I\u00111\u0004\u0001\u0002\u0002\u0013\u0005\u0011Q\u0004\u0005\n\u0003S\u0001\u0011\u0011!C!\u0003WA\u0011\"!\u000e\u0001\u0003\u0003%\t!a\u000e\t\u0013\u0005\u0005\u0003!!A\u0005B\u0005\r\u0003\"CA$\u0001\u0005\u0005I\u0011IA%\u0011%\tY\u0005AA\u0001\n\u0003\nieB\u0005\u0002R\u0001\n\t\u0011#\u0001\u0002T\u0019Aq\u0004IA\u0001\u0012\u0003\t)\u0006\u0003\u0004J3\u0011\u0005\u0011Q\u000e\u0005\tSf\t\t\u0011\"\u0012\u0002p!I\u0011\u0011O\r\u0002\u0002\u0013\u0005\u00151\u000f\u0005\n\u0003oJ\u0012\u0011!CA\u0003sB\u0011\"!\"\u001a\u0003\u0003%I!a\"\u0003\t1Kg.\u001a\u0006\u0003C\t\nQ!\u001a=dK2T!a\t\u0013\u0002\u000bQ\f7o[:\u000b\u0005\u00152\u0013!B:fU\u0012\f'\"A\u0014\u0002\t\r|G-Z\u0002\u0001'\u0015\u0001!\u0006\r\u001b8!\tYc&D\u0001-\u0015\u0005i\u0013!B:dC2\f\u0017BA\u0018-\u0005\u0019\te.\u001f*fMB\u0011\u0011GM\u0007\u0002A%\u00111\u0007\t\u0002\u001a)>\u0004H*\u001a4u%\u0016\u001cG/\u00198hk2\f'OQ8y\u0019&\\W\r\u0005\u0002,k%\u0011a\u0007\f\u0002\b!J|G-^2u!\tA\u0004I\u0004\u0002:}9\u0011!(P\u0007\u0002w)\u0011A\bK\u0001\u0007yI|w\u000e\u001e \n\u00035J!a\u0010\u0017\u0002\u000fA\f7m[1hK&\u0011\u0011I\u0011\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003\u007f1\nQAZ5sgR,\u0012!\u0012\t\u0003c\u0019K!a\u0012\u0011\u0003\tQ+\u0007\u0010^\u0001\u0007M&\u00148\u000f\u001e\u0011\u0002\rqJg.\u001b;?)\tYE\n\u0005\u00022\u0001!)1i\u0001a\u0001\u000b\u0006)A/\u001a=ugV\tq\nE\u0002Q+\u0016k\u0011!\u0015\u0006\u0003%N\u000bq!\\;uC\ndWM\u0003\u0002UY\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005Y\u000b&A\u0003'jgR\u0014UO\u001a4fe\u00061A/\u001a=ug\u0002\nAa]5{KV\t!\f\u0005\u0002,7&\u0011A\f\f\u0002\u0004\u0013:$\u0018a\u0001;paV\tq\f\u0005\u0002,A&\u0011\u0011\r\f\u0002\u0006\r2|\u0017\r^\u0001\u0007E>$Ho\\7\u0002\u000bILw\r\u001b;\u0002\t1,g\r^\u0001\u0004C\u0012$GCA(h\u0011\u0015A7\u00021\u0001F\u0003\u0005!\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003-\u0004\"\u0001\u001c9\u000f\u00055t\u0007C\u0001\u001e-\u0013\tyG&\u0001\u0004Qe\u0016$WMZ\u0005\u0003cJ\u0014aa\u0015;sS:<'BA8-\u00035!xn\u0015;sS:<G)\u001a2vOV\tQ\u000f\u0005\u0002ww6\tqO\u0003\u0002ys\u0006!A.\u00198h\u0015\u0005Q\u0018\u0001\u00026bm\u0006L!!]<\u0002\t\r|\u0007/\u001f\u000b\u0003\u0017zDqa\u0011\b\u0011\u0002\u0003\u0007Q)\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005\r!fA#\u0002\u0006-\u0012\u0011q\u0001\t\u0005\u0003\u0013\t\u0019\"\u0004\u0002\u0002\f)!\u0011QBA\b\u0003%)hn\u00195fG.,GMC\u0002\u0002\u00121\n!\"\u00198o_R\fG/[8o\u0013\u0011\t)\"a\u0003\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\ty\"!\n\u0011\u0007-\n\t#C\u0002\u0002$1\u00121!\u00118z\u0011!\t9CEA\u0001\u0002\u0004Q\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002.A1\u0011qFA\u0019\u0003?i\u0011aU\u0005\u0004\u0003g\u0019&\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!\u000f\u0002@A\u00191&a\u000f\n\u0007\u0005uBFA\u0004C_>dW-\u00198\t\u0013\u0005\u001dB#!AA\u0002\u0005}\u0011A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2!^A#\u0011!\t9#FA\u0001\u0002\u0004Q\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003i\u000ba!Z9vC2\u001cH\u0003BA\u001d\u0003\u001fB\u0011\"a\n\u0018\u0003\u0003\u0005\r!a\b\u0002\t1Kg.\u001a\t\u0003ce\u0019R!GA,\u0003G\u0002b!!\u0017\u0002`\u0015[UBAA.\u0015\r\ti\u0006L\u0001\beVtG/[7f\u0013\u0011\t\t'a\u0017\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0005\u0003\u0002f\u0005-TBAA4\u0015\r\tI'_\u0001\u0003S>L1!QA4)\t\t\u0019\u0006F\u0001v\u0003\u0015\t\u0007\u000f\u001d7z)\rY\u0015Q\u000f\u0005\u0006\u0007r\u0001\r!R\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\tY(!!\u0011\t-\ni(R\u0005\u0004\u0003\u007fb#AB(qi&|g\u000e\u0003\u0005\u0002\u0004v\t\t\u00111\u0001L\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u0013\u00032A^AF\u0013\r\tii\u001e\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/Line.class */
public class Line implements TopLeftRectangularBoxLike, Product, Serializable {
    private final Text first;
    private final ListBuffer<Text> texts;

    public static Option<Text> unapply(final Line x$0) {
        return Line$.MODULE$.unapply(x$0);
    }

    public static Line apply(final Text first) {
        return Line$.MODULE$.apply(first);
    }

    public static <A> Function1<Text, A> andThen(final Function1<Line, A> g) {
        return Line$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, Line> compose(final Function1<A, Text> g) {
        return Line$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
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

    public Text first() {
        return this.first;
    }

    public Line copy(final Text first) {
        return new Line(first);
    }

    public Text copy$default$1() {
        return first();
    }

    public String productPrefix() {
        return PDAnnotationLine.SUB_TYPE;
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return first();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof Line;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "first";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof Line) {
                Line line = (Line) x$1;
                Text textFirst = first();
                Text textFirst2 = line.first();
                if (textFirst != null ? textFirst.equals(textFirst2) : textFirst2 == null) {
                    if (line.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public Line(final Text first) {
        this.first = first;
        TopLeftRectangularBoxLike.$init$(this);
        Product.$init$(this);
        this.texts = (ListBuffer) ListBuffer$.MODULE$.apply(ScalaRunTime$.MODULE$.wrapRefArray(new Text[]{first}));
    }

    public ListBuffer<Text> texts() {
        return this.texts;
    }

    public int size() {
        return texts().size();
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float top() {
        return BoxesRunTime.unboxToFloat(((IterableOnceOps) texts().map(x$12 -> {
            return BoxesRunTime.boxToFloat(x$12.top());
        })).min(Ordering$DeprecatedFloatOrdering$.MODULE$));
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float bottom() {
        return BoxesRunTime.unboxToFloat(((IterableOnceOps) texts().map(x$13 -> {
            return BoxesRunTime.boxToFloat(x$13.bottom());
        })).max(Ordering$DeprecatedFloatOrdering$.MODULE$));
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float right() {
        return BoxesRunTime.unboxToFloat(((IterableOnceOps) texts().map(x$14 -> {
            return BoxesRunTime.boxToFloat(x$14.right());
        })).max(Ordering$DeprecatedFloatOrdering$.MODULE$));
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float left() {
        return BoxesRunTime.unboxToFloat(((IterableOnceOps) texts().map(x$15 -> {
            return BoxesRunTime.boxToFloat(x$15.left());
        })).min(Ordering$DeprecatedFloatOrdering$.MODULE$));
    }

    public ListBuffer<Text> add(final Text t) {
        return texts().$plus$eq(t);
    }

    public String toString() {
        return texts().mkString(", ");
    }

    public String toStringDebug() {
        return new StringBuilder(6).append("- [").append(top()).append(",").append(bottom()).append("] ").append(texts().mkString(",")).toString();
    }
}
