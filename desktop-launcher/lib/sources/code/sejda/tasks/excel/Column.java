package code.sejda.tasks.excel;

import code.util.ImplicitJavaConversions$;
import java.io.Serializable;
import org.sejda.core.support.util.StringUtils;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.PDTableAttributeObject;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ListBuffer;
import scala.collection.mutable.ListBuffer$;
import scala.math.Ordering$DeprecatedFloatOrdering$;
import scala.math.Ordering$Int$;
import scala.math.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: TableDetector.scala */
@ScalaSignature(bytes = "\u0006\u0005\t%a\u0001B\u0015+\u0001NB\u0001\"\u0014\u0001\u0003\u0016\u0004%\tA\u0014\u0005\t+\u0002\u0011\t\u0012)A\u0005\u001f\"Aa\u000b\u0001BK\u0002\u0013\u0005q\u000b\u0003\u0005\\\u0001\tE\t\u0015!\u0003Y\u0011\u0015a\u0006\u0001\"\u0001^\u0011\u001d\t\u0007A1A\u0005\u0002\tDaa\u001b\u0001!\u0002\u0013\u0019\u0007\"\u00027\u0001\t\u0003i\u0007\"B9\u0001\t\u0003i\u0007\"\u0002:\u0001\t\u0003i\u0007\"B:\u0001\t\u0003i\u0007\"\u0002;\u0001\t\u0003)\b\"\u0002=\u0001\t\u0003I\bbBA\u000b\u0001\u0011\u0005\u0011q\u0003\u0005\b\u0003W\u0001A\u0011AA\u0017\u0011\u001d\t\u0019\u0004\u0001C\u0001\u0003kAq!a\u0011\u0001\t\u0003\n)\u0005C\u0004\u0002H\u0001!\t!!\u0013\t\u000f\u0005-\u0003\u0001\"\u0001\u0002N!9\u0011Q\u000b\u0001\u0005\u0002\u0005]\u0003\"CA-\u0001\u0005\u0005I\u0011AA.\u0011%\t\t\u0007AI\u0001\n\u0003\t\u0019\u0007C\u0005\u0002z\u0001\t\n\u0011\"\u0001\u0002|!I\u0011q\u0010\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0011\u0005\t\u0003#\u0003\u0011\u0011!C\u0001/\"I\u00111\u0013\u0001\u0002\u0002\u0013\u0005\u0011Q\u0013\u0005\n\u0003C\u0003\u0011\u0011!C!\u0003GC\u0011\"!,\u0001\u0003\u0003%\t!a,\t\u0013\u0005M\u0006!!A\u0005B\u0005U\u0006\"CA]\u0001\u0005\u0005I\u0011IA^\u0011%\ti\fAA\u0001\n\u0003\nylB\u0005\u0002D*\n\t\u0011#\u0001\u0002F\u001aA\u0011FKA\u0001\u0012\u0003\t9\r\u0003\u0004]C\u0011\u0005\u0011q\u001c\u0005\n\u0003\u0007\n\u0013\u0011!C#\u0003CD\u0011\"a9\"\u0003\u0003%\t)!:\t\u0013\u0005-\u0018%%A\u0005\u0002\u0005m\u0004\"CAwC\u0005\u0005I\u0011QAx\u0011%\ti0II\u0001\n\u0003\tY\bC\u0005\u0002��\u0006\n\t\u0011\"\u0003\u0003\u0002\t11i\u001c7v[:T!a\u000b\u0017\u0002\u000b\u0015D8-\u001a7\u000b\u00055r\u0013!\u0002;bg.\u001c(BA\u00181\u0003\u0015\u0019XM\u001b3b\u0015\u0005\t\u0014\u0001B2pI\u0016\u001c\u0001aE\u0003\u0001iir\u0014\t\u0005\u00026q5\taGC\u00018\u0003\u0015\u00198-\u00197b\u0013\tIdG\u0001\u0004B]f\u0014VM\u001a\t\u0003wqj\u0011AK\u0005\u0003{)\u0012\u0011\u0004V8q\u0019\u00164GOU3di\u0006tw-\u001e7be\n{\u0007\u0010T5lKB\u0011QgP\u0005\u0003\u0001Z\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002C\u0015:\u00111\t\u0013\b\u0003\t\u001ek\u0011!\u0012\u0006\u0003\rJ\na\u0001\u0010:p_Rt\u0014\"A\u001c\n\u0005%3\u0014a\u00029bG.\fw-Z\u0005\u0003\u00172\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!!\u0013\u001c\u0002\u000b\u0015dW-\\:\u0016\u0003=\u00032A\u0011)S\u0013\t\tFJA\u0002TKF\u0004\"aO*\n\u0005QS#\u0001\u0002+fqR\fa!\u001a7f[N\u0004\u0013!B5oI\u0016DX#\u0001-\u0011\u0005UJ\u0016B\u0001.7\u0005\rIe\u000e^\u0001\u0007S:$W\r\u001f\u0011\u0002\rqJg.\u001b;?)\rqv\f\u0019\t\u0003w\u0001AQ!T\u0003A\u0002=CqAV\u0003\u0011\u0002\u0003\u0007\u0001,A\u0003uKb$8/F\u0001d!\r!\u0017NU\u0007\u0002K*\u0011amZ\u0001\b[V$\u0018M\u00197f\u0015\tAg'\u0001\u0006d_2dWm\u0019;j_:L!A[3\u0003\u00151K7\u000f\u001e\"vM\u001a,'/\u0001\u0004uKb$8\u000fI\u0001\u0004i>\u0004X#\u00018\u0011\u0005Uz\u0017B\u000197\u0005\u00151En\\1u\u0003\u0019\u0011w\u000e\u001e;p[\u0006)!/[4ii\u0006!A.\u001a4u\u0003\r\tG\r\u001a\u000b\u0003GZDQa\u001e\u0007A\u0002I\u000b\u0011\u0001^\u0001\tG>tG/Y5ogR!!0`A\t!\t)40\u0003\u0002}m\t9!i\\8mK\u0006t\u0007\"\u0002@\u000e\u0001\u0004y\u0018\u0001B2fY2\u0004B!!\u0001\u0002\u000e5\u0011\u00111\u0001\u0006\u0005\u0003\u000b\t9!A\u0003n_\u0012,GNC\u00020\u0003\u0013Q!!a\u0003\u0002\u0007=\u0014x-\u0003\u0003\u0002\u0010\u0005\r!!\u0006+pa2+g\r\u001e*fGR\fgnZ;mCJ\u0014u\u000e\u001f\u0005\u0007\u0003'i\u0001\u0019\u0001\u001e\u0002\u0007=\u0014'.\u0001\tuKb$X*\u0019;dQ&tw-\u0011:fCR!\u0011\u0011DA\u0015!\u0011\tY\"a\t\u000f\t\u0005u\u0011q\u0004\t\u0003\tZJ1!!\t7\u0003\u0019\u0001&/\u001a3fM&!\u0011QEA\u0014\u0005\u0019\u0019FO]5oO*\u0019\u0011\u0011\u0005\u001c\t\u000byt\u0001\u0019A@\u0002\u00135,'oZ3XSRDGc\u00010\u00020!1\u0011\u0011G\bA\u0002y\u000bQa\u001c;iKJ\f!\u0002Z5wS\u0012,\u0017J\u001c;p)\u0019\t9$!\u0010\u0002@A\u0019Q'!\u000f\n\u0007\u0005mbG\u0001\u0003V]&$\bBBA\u0019!\u0001\u0007a\f\u0003\u0004\u0002BA\u0001\r\u0001W\u0001\u0007[\u0006\u0014x-\u001b8\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u0007\u0002\u001bQ|7\u000b\u001e:j]\u001e$UMY;h+\t\tI\"A\nhKR\u001cFO]8oO2+g\r^'be\u001eLg.\u0006\u0002\u0002PA!Q'!\u0015Y\u0013\r\t\u0019F\u000e\u0002\u0007\u001fB$\u0018n\u001c8\u0002'!\f7o\u0015;s_:<G*\u001a4u\u001b\u0006\u0014x-\u001b8\u0016\u0003i\fAaY8qsR)a,!\u0018\u0002`!9Q*\u0006I\u0001\u0002\u0004y\u0005b\u0002,\u0016!\u0003\u0005\r\u0001W\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\t)GK\u0002P\u0003OZ#!!\u001b\u0011\t\u0005-\u0014QO\u0007\u0003\u0003[RA!a\u001c\u0002r\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003g2\u0014AC1o]>$\u0018\r^5p]&!\u0011qOA7\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\tiHK\u0002Y\u0003O\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAAB!\u0011\t))a$\u000e\u0005\u0005\u001d%\u0002BAE\u0003\u0017\u000bA\u0001\\1oO*\u0011\u0011QR\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002&\u0005\u001d\u0015\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003/\u000bi\nE\u00026\u00033K1!a'7\u0005\r\te.\u001f\u0005\t\u0003?S\u0012\u0011!a\u00011\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!*\u0011\r\u0005\u001d\u0016\u0011VAL\u001b\u00059\u0017bAAVO\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\rQ\u0018\u0011\u0017\u0005\n\u0003?c\u0012\u0011!a\u0001\u0003/\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u00111QA\\\u0011!\ty*HA\u0001\u0002\u0004A\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003a\u000ba!Z9vC2\u001cHc\u0001>\u0002B\"I\u0011qT\u0010\u0002\u0002\u0003\u0007\u0011qS\u0001\u0007\u0007>dW/\u001c8\u0011\u0005m\n3#B\u0011\u0002J\u0006U\u0007cBAf\u0003#|\u0005LX\u0007\u0003\u0003\u001bT1!a47\u0003\u001d\u0011XO\u001c;j[\u0016LA!a5\u0002N\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\u001c\u001a\u0011\t\u0005]\u0017Q\\\u0007\u0003\u00033TA!a7\u0002\f\u0006\u0011\u0011n\\\u0005\u0004\u0017\u0006eGCAAc)\t\t\u0019)A\u0003baBd\u0017\u0010F\u0003_\u0003O\fI\u000fC\u0003NI\u0001\u0007q\nC\u0004WIA\u0005\t\u0019\u0001-\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uII\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002r\u0006e\b#B\u001b\u0002R\u0005M\b#B\u001b\u0002v>C\u0016bAA|m\t1A+\u001e9mKJB\u0001\"a?'\u0003\u0003\u0005\rAX\u0001\u0004q\u0012\u0002\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$#'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003\u0004A!\u0011Q\u0011B\u0003\u0013\u0011\u00119!a\"\u0003\r=\u0013'.Z2u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/Column.class */
public class Column implements TopLeftRectangularBoxLike, Product, Serializable {
    private final Seq<Text> elems;
    private final int index;
    private final ListBuffer<Text> texts;

    public static Option<Tuple2<Seq<Text>, Object>> unapply(final Column x$0) {
        return Column$.MODULE$.unapply(x$0);
    }

    public static Column apply(final Seq<Text> elems, final int index) {
        return Column$.MODULE$.apply(elems, index);
    }

    public static Function1<Tuple2<Seq<Text>, Object>, Column> tupled() {
        return Column$.MODULE$.tupled();
    }

    public static Function1<Seq<Text>, Function1<Object, Column>> curried() {
        return Column$.MODULE$.curried();
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

    public Seq<Text> elems() {
        return this.elems;
    }

    public int index() {
        return this.index;
    }

    public Column copy(final Seq<Text> elems, final int index) {
        return new Column(elems, index);
    }

    public Seq<Text> copy$default$1() {
        return elems();
    }

    public int copy$default$2() {
        return index();
    }

    public String productPrefix() {
        return PDTableAttributeObject.SCOPE_COLUMN;
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return elems();
            case 1:
                return BoxesRunTime.boxToInteger(index());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof Column;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "elems";
            case 1:
                return "index";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(elems())), index()), 2);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof Column) {
                Column column = (Column) x$1;
                if (index() == column.index()) {
                    Seq<Text> seqElems = elems();
                    Seq<Text> seqElems2 = column.elems();
                    if (seqElems != null ? seqElems.equals(seqElems2) : seqElems2 == null) {
                        if (column.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public Column(final Seq<Text> elems, final int index) {
        this.elems = elems;
        this.index = index;
        TopLeftRectangularBoxLike.$init$(this);
        Product.$init$(this);
        this.texts = (ListBuffer) ListBuffer$.MODULE$.apply(elems);
    }

    public ListBuffer<Text> texts() {
        return this.texts;
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float top() {
        return BoxesRunTime.unboxToFloat(((IterableOnceOps) texts().map(x$17 -> {
            return BoxesRunTime.boxToFloat(x$17.top());
        })).min(Ordering$DeprecatedFloatOrdering$.MODULE$));
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float bottom() {
        return BoxesRunTime.unboxToFloat(((IterableOnceOps) texts().map(x$18 -> {
            return BoxesRunTime.boxToFloat(x$18.bottom());
        })).max(Ordering$DeprecatedFloatOrdering$.MODULE$));
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float right() {
        return BoxesRunTime.unboxToFloat(((IterableOnceOps) texts().map(x$19 -> {
            return BoxesRunTime.boxToFloat(x$19.right());
        })).max(Ordering$DeprecatedFloatOrdering$.MODULE$));
    }

    @Override // code.sejda.tasks.excel.TopLeftRectangularBoxLike
    public float left() {
        return BoxesRunTime.unboxToFloat(((IterableOnceOps) texts().map(x$20 -> {
            return BoxesRunTime.boxToFloat(x$20.left());
        })).min(Ordering$DeprecatedFloatOrdering$.MODULE$));
    }

    public ListBuffer<Text> add(final Text t) {
        return texts().$plus$eq(t);
    }

    public boolean contains(final TopLeftRectangularBox cell, final TopLeftRectangularBoxLike obj) {
        return ((float) cell.getTop()) < obj.top() && ((float) (cell.getTop() + cell.getHeight())) > obj.top() && ((float) cell.getLeft()) < obj.left() && ((float) (cell.getLeft() + cell.getWidth())) > obj.left();
    }

    public String textMatchingArea(final TopLeftRectangularBox cell) {
        return ((IterableOnceOps) ((StrictOptimizedIterableOps) texts().filter(t -> {
            return BoxesRunTime.boxToBoolean(this.contains(cell, t));
        })).map(x$21 -> {
            return x$21.text();
        })).mkString("");
    }

    public Column mergeWith(final Column other) {
        ListBuffer newTexts = (ListBuffer) ((SeqOps) texts().$plus$plus(other.texts())).sortBy(x$22 -> {
            return BoxesRunTime.boxToFloat(x$22.top());
        }, Ordering$DeprecatedFloatOrdering$.MODULE$);
        return new Column(ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq(newTexts), index());
    }

    public void divideInto(final Column other, final int margin) {
        ListBuffer outliers = (ListBuffer) texts().filter(t -> {
            return BoxesRunTime.boxToBoolean($anonfun$divideInto$1(margin, t));
        });
        outliers.foreach(t2 -> {
            $anonfun$divideInto$2(this, margin, other, t2);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ boolean $anonfun$divideInto$1(final int margin$2, final Text t) {
        return t.left() < ((float) margin$2);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$divideInto$2(final Column $this, final int margin$2, final Column other$1, final Text t) throws MatchError {
        Tuple2<Option<Text>, Option<Text>> tuple2Divide = t.divide(margin$2);
        if (tuple2Divide == null) {
            throw new MatchError(tuple2Divide);
        }
        Option leftOpt = (Option) tuple2Divide._1();
        Option rightOpt = (Option) tuple2Divide._2();
        Tuple2 tuple2 = new Tuple2(leftOpt, rightOpt);
        Option leftOpt2 = (Option) tuple2._1();
        Option rightOpt2 = (Option) tuple2._2();
        rightOpt2.foreach(right -> {
            $anonfun$divideInto$3($this, t, right);
            return BoxedUnit.UNIT;
        });
        leftOpt2.foreach(left -> {
            return other$1.add(left);
        });
    }

    public static final /* synthetic */ void $anonfun$divideInto$3(final Column $this, final Text t$2, final Text right) {
        $this.texts().update($this.texts().indexOf(t$2), right);
    }

    public String toString() {
        return new StringBuilder(3).append("col").append(index()).toString();
    }

    public String toStringDebug() {
        return texts().mkString(new StringBuilder(15).append("\ncol").append(index()).append(" ").append(toStringBox()).append("\n========\n").toString(), "\n", "\n+++++++++\n");
    }

    public Option<Object> getStrongLeftMargin() {
        boolean isRtl = texts().exists(t -> {
            return BoxesRunTime.boxToBoolean($anonfun$getStrongLeftMargin$1(t));
        });
        if (!isRtl && texts().length() >= 10) {
            ListBuffer lefts = (ListBuffer) ((SeqOps) texts().map(x$24 -> {
                return BoxesRunTime.boxToInteger($anonfun$getStrongLeftMargin$2(x$24));
            })).sorted(Ordering$Int$.MODULE$);
            int mostOccurring = ((Tuple2) lefts.groupBy(x -> {
                return BoxesRunTime.unboxToInt(Predef$.MODULE$.identity(BoxesRunTime.boxToInteger(x)));
            }).mapValues(x$25 -> {
                return BoxesRunTime.boxToInteger(x$25.size());
            }).maxBy(x$26 -> {
                return BoxesRunTime.boxToInteger(x$26._2$mcI$sp());
            }, Ordering$Int$.MODULE$))._1$mcI$sp();
            ListBuffer nonOutliers = (ListBuffer) lefts.filter(l -> {
                return package$.MODULE$.abs(mostOccurring - l) < 3;
            });
            boolean hasStrongMargin = ((double) nonOutliers.size()) > ((double) texts().length()) * 0.9d;
            if (hasStrongMargin) {
                return new Some(nonOutliers.min(Ordering$Int$.MODULE$));
            }
            return None$.MODULE$;
        }
        return None$.MODULE$;
    }

    public static final /* synthetic */ boolean $anonfun$getStrongLeftMargin$1(final Text t) {
        return StringUtils.isRtl(t.text());
    }

    public static final /* synthetic */ int $anonfun$getStrongLeftMargin$2(final Text x$24) {
        return (int) x$24.left();
    }

    public boolean hasStrongLeftMargin() {
        return getStrongLeftMargin().isDefined();
    }
}
