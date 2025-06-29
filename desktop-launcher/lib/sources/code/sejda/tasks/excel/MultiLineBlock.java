package code.sejda.tasks.excel;

import code.sejda.tasks.common.FontsHelper$;
import code.util.ImplicitJavaConversions$;
import java.io.Serializable;
import org.sejda.impl.sambox.component.excel.DataTable;
import org.sejda.model.TopLeftRectangularBox;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.immutable.$colon;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ListBuffer;
import scala.collection.mutable.ListBuffer$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BooleanRef;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ObjectRef;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: TableDetector.scala */
@ScalaSignature(bytes = "\u0006\u0005\u00055h\u0001B\u0011#\u0001.B\u0001\"\u0011\u0001\u0003\u0016\u0004%\tA\u0011\u0005\t\u0015\u0002\u0011\t\u0012)A\u0005\u0007\")1\n\u0001C\u0001\u0019\"9q\n\u0001b\u0001\n\u0003\u0001\u0006BB-\u0001A\u0003%\u0011\u000bC\u0004[\u0001\u0001\u0007I\u0011A.\t\u000f\u0001\u0004\u0001\u0019!C\u0001C\"1q\r\u0001Q!\nqCQ\u0001\u001b\u0001\u0005\u0002%DQ\u0001\u001c\u0001\u0005\u00025DQ\u0001\u001d\u0001\u0005\u0002EDQA\u001f\u0001\u0005\u0002mDQ! \u0001\u0005\u0002yDq!!\u0001\u0001\t\u0003\n\u0019\u0001C\u0004\u0002\u0016\u0001!\t!a\u0006\t\u000f\u0005e\u0001\u0001\"\u0003\u0002\u001c!9\u0011q\b\u0001\u0005\u0002\u0005\u0005\u0003\"CA2\u0001\u0005\u0005I\u0011IA3\u0011%\t)\bAA\u0001\n\u0003\t9\bC\u0005\u0002z\u0001\t\t\u0011\"\u0001\u0002|!I\u0011Q\u0011\u0001\u0002\u0002\u0013\u0005\u0013q\u0011\u0005\n\u0003#\u0003\u0011\u0011!C\u0001\u0003'C\u0011\"!(\u0001\u0003\u0003%\t%a(\t\u0013\u0005\r\u0006!!A\u0005B\u0005\u0015\u0006\"CAT\u0001\u0005\u0005I\u0011IAU\u000f%\tiKIA\u0001\u0012\u0003\tyK\u0002\u0005\"E\u0005\u0005\t\u0012AAY\u0011\u0019Y5\u0004\"\u0001\u0002J\"I\u0011\u0011A\u000e\u0002\u0002\u0013\u0015\u00131\u001a\u0005\n\u0003\u001b\\\u0012\u0011!CA\u0003\u001fD\u0011\"a5\u001c\u0003\u0003%\t)!6\t\u0013\u0005\r8$!A\u0005\n\u0005\u0015(AD'vYRLG*\u001b8f\u00052|7m\u001b\u0006\u0003G\u0011\nQ!\u001a=dK2T!!\n\u0014\u0002\u000bQ\f7o[:\u000b\u0005\u001dB\u0013!B:fU\u0012\f'\"A\u0015\u0002\t\r|G-Z\u0002\u0001'\u0011\u0001AFM\u001b\u0011\u00055\u0002T\"\u0001\u0018\u000b\u0003=\nQa]2bY\u0006L!!\r\u0018\u0003\r\u0005s\u0017PU3g!\ti3'\u0003\u00025]\t9\u0001K]8ek\u000e$\bC\u0001\u001c?\u001d\t9DH\u0004\u00029w5\t\u0011H\u0003\u0002;U\u00051AH]8pizJ\u0011aL\u0005\u0003{9\nq\u0001]1dW\u0006<W-\u0003\u0002@\u0001\na1+\u001a:jC2L'0\u00192mK*\u0011QHL\u0001\u0006K2,Wn]\u000b\u0002\u0007B\u0019Q\u0006\u0012$\n\u0005\u0015s#A\u0003\u001fsKB,\u0017\r^3e}A\u0011q\tS\u0007\u0002E%\u0011\u0011J\t\u0002\u0005\u0019&tW-\u0001\u0004fY\u0016l7\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00055s\u0005CA$\u0001\u0011\u0015\t5\u00011\u0001D\u0003\u0015a\u0017N\\3t+\u0005\t\u0006c\u0001*X\r6\t1K\u0003\u0002U+\u00069Q.\u001e;bE2,'B\u0001,/\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u00031N\u0013!\u0002T5ti\n+hMZ3s\u0003\u0019a\u0017N\\3tA\u000591m\u001c7v[:\u001cX#\u0001/\u0011\u0007I;V\f\u0005\u0002H=&\u0011qL\t\u0002\u0007\u0007>dW/\u001c8\u0002\u0017\r|G.^7og~#S-\u001d\u000b\u0003E\u0016\u0004\"!L2\n\u0005\u0011t#\u0001B+oSRDqAZ\u0004\u0002\u0002\u0003\u0007A,A\u0002yIE\n\u0001bY8mk6t7\u000fI\u0001\bC\u0012$G*\u001b8f)\t\t&\u000eC\u0003l\u0013\u0001\u0007a)A\u0001m\u0003%\tG\rZ\"pYVlg\u000e\u0006\u0002]]\")qN\u0003a\u0001;\u0006\t1-\u0001\u0006gS:$7i\u001c7v[:$\"A];\u0011\u00075\u001aX,\u0003\u0002u]\t1q\n\u001d;j_:DQA^\u0006A\u0002]\f\u0011\u0001\u001e\t\u0003\u000fbL!!\u001f\u0012\u0003\tQ+\u0007\u0010^\u0001\u0018[\u0016\u0014x-Z(wKJd\u0017\r\u001d9j]\u001e\u001cu\u000e\\;n]N$\"\u0001\u0018?\t\u000bic\u0001\u0019\u0001/\u0002O5,'oZ3D_2,XN\\:UQ\u0006$8i\\7qY\u0016$X\r\\=J]\u000edW\u000fZ3Pi\",'o\u001d\u000b\u00039~DQAW\u0007A\u0002q\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003\u000b\u0001B!a\u0002\u0002\u00109!\u0011\u0011BA\u0006!\tAd&C\u0002\u0002\u000e9\na\u0001\u0015:fI\u00164\u0017\u0002BA\t\u0003'\u0011aa\u0015;sS:<'bAA\u0007]\u0005\u0001B-Z2p[B|7/Z\"pYVlgn\u001d\u000b\u0002E\u0006a\u0011N\u001c;feN,7\r^5p]R1\u0011QDA\u0019\u0003w\u0001B!L:\u0002 A!\u0011\u0011EA\u0017\u001b\t\t\u0019C\u0003\u0003\u0002&\u0005\u001d\u0012!B7pI\u0016d'bA\u0014\u0002*)\u0011\u00111F\u0001\u0004_J<\u0017\u0002BA\u0018\u0003G\u0011Q\u0003V8q\u0019\u00164GOU3di\u0006tw-\u001e7be\n{\u0007\u0010C\u0004\u00024A\u0001\r!!\u000e\u0002\u0005I\f\u0004cA$\u00028%\u0019\u0011\u0011\b\u0012\u00033Q{\u0007\u000fT3giJ+7\r^1oOVd\u0017M\u001d\"pq2K7.\u001a\u0005\b\u0003{\u0001\u0002\u0019AA\u001b\u0003\t\u0011('A\u0006u_\u0012\u000bG/\u0019+bE2,G\u0003BA\"\u00033\u0002B!!\u0012\u0002V5\u0011\u0011q\t\u0006\u0004G\u0005%#\u0002BA&\u0003\u001b\n\u0011bY8na>tWM\u001c;\u000b\t\u0005=\u0013\u0011K\u0001\u0007g\u0006l'm\u001c=\u000b\t\u0005M\u0013qE\u0001\u0005S6\u0004H.\u0003\u0003\u0002X\u0005\u001d#!\u0003#bi\u0006$\u0016M\u00197f\u0011\u001d\tY&\u0005a\u0001\u0003;\nq\u0001]1hK:+X\u000eE\u0002.\u0003?J1!!\u0019/\u0005\rIe\u000e^\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005\u001d\u0004\u0003BA5\u0003gj!!a\u001b\u000b\t\u00055\u0014qN\u0001\u0005Y\u0006twM\u0003\u0002\u0002r\u0005!!.\u0019<b\u0013\u0011\t\t\"a\u001b\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005u\u0013A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003{\n\u0019\tE\u0002.\u0003\u007fJ1!!!/\u0005\r\te.\u001f\u0005\tMR\t\t\u00111\u0001\u0002^\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\nB1\u00111RAG\u0003{j\u0011!V\u0005\u0004\u0003\u001f+&\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!&\u0002\u001cB\u0019Q&a&\n\u0007\u0005eeFA\u0004C_>dW-\u00198\t\u0011\u00194\u0012\u0011!a\u0001\u0003{\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011qMAQ\u0011!1w#!AA\u0002\u0005u\u0013\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005u\u0013AB3rk\u0006d7\u000f\u0006\u0003\u0002\u0016\u0006-\u0006\u0002\u00034\u001a\u0003\u0003\u0005\r!! \u0002\u001d5+H\u000e^5MS:,'\t\\8dWB\u0011qiG\n\u00067\u0005M\u0016q\u0018\t\u0007\u0003k\u000bYlQ'\u000e\u0005\u0005]&bAA]]\u00059!/\u001e8uS6,\u0017\u0002BA_\u0003o\u0013\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\t\t-a2\u000e\u0005\u0005\r'\u0002BAc\u0003_\n!![8\n\u0007}\n\u0019\r\u0006\u0002\u00020R\u0011\u0011qM\u0001\u0006CB\u0004H.\u001f\u000b\u0004\u001b\u0006E\u0007\"B!\u001f\u0001\u0004\u0019\u0015AC;oCB\u0004H._*fcR!\u0011q[Ap!\u0011i3/!7\u0011\tY\nYNR\u0005\u0004\u0003;\u0004%aA*fc\"A\u0011\u0011]\u0010\u0002\u0002\u0003\u0007Q*A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a:\u0011\t\u0005%\u0014\u0011^\u0005\u0005\u0003W\fYG\u0001\u0004PE*,7\r\u001e")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/MultiLineBlock.class */
public class MultiLineBlock implements Product, Serializable {
    private final Seq<Line> elems;
    private final ListBuffer<Line> lines;
    private ListBuffer<Column> columns;

    public static Option<Seq<Line>> unapplySeq(final MultiLineBlock x$0) {
        return MultiLineBlock$.MODULE$.unapplySeq(x$0);
    }

    public static MultiLineBlock apply(final Seq<Line> elems) {
        return MultiLineBlock$.MODULE$.apply(elems);
    }

    public static <A> Function1<Seq<Line>, A> andThen(final Function1<MultiLineBlock, A> g) {
        return MultiLineBlock$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, MultiLineBlock> compose(final Function1<A, Seq<Line>> g) {
        return MultiLineBlock$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Seq<Line> elems() {
        return this.elems;
    }

    public String productPrefix() {
        return "MultiLineBlock";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return elems();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof MultiLineBlock;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "elems";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof MultiLineBlock) {
                MultiLineBlock multiLineBlock = (MultiLineBlock) x$1;
                Seq<Line> seqElems = elems();
                Seq<Line> seqElems2 = multiLineBlock.elems();
                if (seqElems != null ? seqElems.equals(seqElems2) : seqElems2 == null) {
                    if (multiLineBlock.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public MultiLineBlock(final Seq<Line> elems) {
        this.elems = elems;
        Product.$init$(this);
        this.lines = (ListBuffer) ListBuffer$.MODULE$.apply(elems);
        this.columns = (ListBuffer) ListBuffer$.MODULE$.apply(Nil$.MODULE$);
    }

    public ListBuffer<Line> lines() {
        return this.lines;
    }

    public ListBuffer<Column> columns() {
        return this.columns;
    }

    public void columns_$eq(final ListBuffer<Column> x$1) {
        this.columns = x$1;
    }

    public ListBuffer<Line> addLine(final Line l) {
        return lines().$plus$eq(l);
    }

    public ListBuffer<Column> addColumn(final Column c) {
        return columns().$plus$eq(c);
    }

    public Option<Column> findColumn(final Text t) {
        ListBuffer overlaps = (ListBuffer) columns().map(c -> {
            float overlap = Math.max(0.0f, Math.min(t.right(), c.right()) - Math.max(t.left(), c.left()));
            return new Tuple2(c, BoxesRunTime.boxToFloat(overlap));
        });
        Some someHeadOption = ((ListBuffer) overlaps.sortWith((o1, o2) -> {
            return BoxesRunTime.boxToBoolean($anonfun$findColumn$2(o1, o2));
        })).headOption();
        if (someHeadOption instanceof Some) {
            Tuple2 largest = (Tuple2) someHeadOption.value();
            if (BoxesRunTime.unboxToFloat(largest._2()) > 0) {
                return new Some(largest._1());
            }
        }
        return None$.MODULE$;
    }

    public static final /* synthetic */ boolean $anonfun$findColumn$2(final Tuple2 o1, final Tuple2 o2) {
        return BoxesRunTime.unboxToFloat(o2._2()) < BoxesRunTime.unboxToFloat(o1._2());
    }

    public ListBuffer<Column> mergeOverlappingColumns(final ListBuffer<Column> columns) {
        ListBuffer results = (ListBuffer) ListBuffer$.MODULE$.apply(Nil$.MODULE$);
        ObjectRef prev = ObjectRef.create((Object) null);
        columns.foreach(c -> {
            $anonfun$mergeOverlappingColumns$1(prev, results, c);
            return BoxedUnit.UNIT;
        });
        return results;
    }

    public static final /* synthetic */ void $anonfun$mergeOverlappingColumns$1(final ObjectRef prev$3, final ListBuffer results$2, final Column c) {
        if (((Column) prev$3.elem) != null) {
            float distance = c.left() - ((Column) prev$3.elem).right();
            boolean merged = false;
            if (distance < 0) {
                float overlapOnPrev = Math.abs(c.left() - ((Column) prev$3.elem).right());
                double overlapPercentagePrev = (overlapOnPrev * 100.0d) / ((Column) prev$3.elem).width();
                double overlapPercentageC = (overlapOnPrev * 100.0d) / c.width();
                if (overlapPercentagePrev > 80 || overlapPercentageC > 80) {
                    Some strongLeftMargin = c.getStrongLeftMargin();
                    if (strongLeftMargin instanceof Some) {
                        int margin = BoxesRunTime.unboxToInt(strongLeftMargin.value());
                        c.divideInto((Column) prev$3.elem, margin);
                        BoxedUnit boxedUnit = BoxedUnit.UNIT;
                    } else {
                        results$2.update(results$2.size() - 1, ((Column) prev$3.elem).mergeWith(c));
                        merged = true;
                        BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
                    }
                }
            }
            if (merged) {
                BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
            } else {
                results$2.$plus$eq(c);
            }
        } else {
            results$2.$plus$eq(c);
        }
        prev$3.elem = c;
    }

    private static final Option findOtherColumnThatIncludes$1(final Column c, final ListBuffer cols$1) {
        return cols$1.find(other -> {
            return BoxesRunTime.boxToBoolean($anonfun$mergeColumnsThatCompletelyIncludeOthers$1(c, other));
        });
    }

    public static final /* synthetic */ boolean $anonfun$mergeColumnsThatCompletelyIncludeOthers$1(final Column c$1, final Column other) {
        if (other == null) {
            if (c$1 == null) {
                return false;
            }
        } else if (other.equals(c$1)) {
            return false;
        }
        return other.left() <= c$1.left() && other.right() >= c$1.right();
    }

    private static final Tuple2 _merge$1(final ListBuffer cols) {
        ListBuffer results = (ListBuffer) ListBuffer$.MODULE$.apply(Nil$.MODULE$);
        BooleanRef columnsChanged = BooleanRef.create(false);
        ListBuffer processed = (ListBuffer) ListBuffer$.MODULE$.apply(Nil$.MODULE$);
        cols.foreach(x0$1 -> {
            if (processed.contains(x0$1)) {
                return BoxedUnit.UNIT;
            }
            if (columnsChanged.elem) {
                results.$plus$eq(x0$1);
                return processed.$plus$eq(x0$1);
            }
            Some someFindOtherColumnThatIncludes$1 = findOtherColumnThatIncludes$1(x0$1, cols);
            if (someFindOtherColumnThatIncludes$1 instanceof Some) {
                Column anotherColumn = (Column) someFindOtherColumnThatIncludes$1.value();
                results.$plus$eq(anotherColumn.mergeWith(x0$1));
                results.$minus$eq(anotherColumn);
                processed.$plus$eq(anotherColumn);
                processed.$plus$eq(x0$1);
                columnsChanged.elem = true;
                return BoxedUnit.UNIT;
            }
            results.$plus$eq(x0$1);
            return processed.$plus$eq(x0$1);
        });
        return new Tuple2(results, BoxesRunTime.boxToBoolean(columnsChanged.elem));
    }

    public ListBuffer<Column> mergeColumnsThatCompletelyIncludeOthers(final ListBuffer<Column> columns) {
        ListBuffer results = columns;
        boolean columnsChanged = true;
        int iterations = 0;
        while (columnsChanged) {
            Tuple2 x = _merge$1(results);
            results = (ListBuffer) x._1();
            columnsChanged = x._2$mcZ$sp();
            iterations++;
            if (iterations > columns().length() * 2) {
                throw new RuntimeException("Too many iterations");
            }
        }
        return results;
    }

    public String toString() {
        return ((IterableOnceOps) columns().map(x$16 -> {
            return x$16.toString();
        })).mkString("\n", ", ", "\n");
    }

    public void decomposeColumns() {
        lines().foreach(line -> {
            $anonfun$decomposeColumns$1(this, line);
            return BoxedUnit.UNIT;
        });
        columns_$eq(mergeColumnsThatCompletelyIncludeOthers(columns()));
        columns_$eq((ListBuffer) columns().sortWith((a, b) -> {
            return BoxesRunTime.boxToBoolean($anonfun$decomposeColumns$3(a, b));
        }));
        columns_$eq(mergeOverlappingColumns(columns()));
    }

    public static final /* synthetic */ void $anonfun$decomposeColumns$1(final MultiLineBlock $this, final Line line) {
        line.texts().foreach(t -> {
            Some someFindColumn = $this.findColumn(t);
            if (someFindColumn instanceof Some) {
                Column col = (Column) someFindColumn.value();
                return col.add(t);
            }
            Column c = new Column(new $colon.colon(t, Nil$.MODULE$), Column$.MODULE$.$lessinit$greater$default$2());
            return $this.addColumn(c);
        });
    }

    public static final /* synthetic */ boolean $anonfun$decomposeColumns$3(final Column a, final Column b) {
        return a.left() < b.left();
    }

    private Option<TopLeftRectangularBox> intersection(final TopLeftRectangularBoxLike r1, final TopLeftRectangularBoxLike r2) {
        if (r1.left() > r2.right() || r2.left() > r1.right() || r1.top() > r2.bottom() || r2.top() > r1.bottom()) {
            return None$.MODULE$;
        }
        float left = Math.max(r1.left(), r2.left());
        float top = Math.max(r1.top(), r2.top());
        float width = Math.min(r1.right(), r2.right()) - Math.max(r1.left(), r2.left());
        float height = Math.min(r1.top() + r1.height(), r2.top() + r2.height()) - Math.max(r1.top(), r2.top());
        return new Some(new TopLeftRectangularBox((int) left, (int) top, (int) width, (int) height));
    }

    public DataTable toDataTable(final int pageNum) {
        DataTable dataTable = new DataTable(pageNum);
        lines().foreach(line -> {
            ListBuffer dataRow = (ListBuffer) this.columns().map(column -> {
                Some someIntersection = this.intersection(line, column);
                if (someIntersection instanceof Some) {
                    TopLeftRectangularBox cell = (TopLeftRectangularBox) someIntersection.value();
                    String value = column.textMatchingArea(cell.withPadding(1));
                    return FontsHelper$.MODULE$.arabicShaping(value);
                }
                return "";
            });
            return dataTable.addRow((String[]) ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq(dataRow).toArray(ClassTag$.MODULE$.apply(String.class)));
        });
        return dataTable;
    }
}
