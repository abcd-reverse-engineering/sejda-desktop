package code.util.pdf;

import java.io.Serializable;
import java.util.Set;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.JavaConverters$;
import scala.collection.SortedSet;
import scala.collection.SortedSet$;
import scala.collection.immutable.Seq;
import scala.math.Ordering$;
import scala.math.Ordering$Int$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: PageRanges.scala */
@ScalaSignature(bytes = "\u0006\u0005\u00055e\u0001B\r\u001b\u0001\u0006B\u0001b\u000e\u0001\u0003\u0016\u0004%\t\u0001\u000f\u0005\t\u0013\u0002\u0011\t\u0012)A\u0005s!)!\n\u0001C\u0001\u0017\")q\n\u0001C\u0001!\")!\f\u0001C\u00017\"9\u0011\u000eAA\u0001\n\u0003Q\u0007b\u00027\u0001#\u0003%\t!\u001c\u0005\bq\u0002\t\t\u0011\"\u0011z\u0011\u001di\b!!A\u0005\u0002yD\u0001b \u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0001\u0005\n\u0003\u001b\u0001\u0011\u0011!C!\u0003\u001fA\u0011\"a\u0006\u0001\u0003\u0003%\t!!\u0007\t\u0013\u0005\r\u0002!!A\u0005B\u0005\u0015\u0002\"CA\u0015\u0001\u0005\u0005I\u0011IA\u0016\u0011%\ti\u0003AA\u0001\n\u0003\ny\u0003C\u0005\u00022\u0001\t\t\u0011\"\u0011\u00024\u001d9\u0011q\u0007\u000e\t\u0002\u0005ebAB\r\u001b\u0011\u0003\tY\u0004\u0003\u0004K%\u0011\u0005\u0011q\t\u0005\b\u0003\u0013\u0012B\u0011AA&\u0011\u001d\t)G\u0005C\u0001\u0003OB\u0011\"!\u001e\u0013\u0003\u0003%\t)a\u001e\t\u0013\u0005%##!A\u0005\u0002\u0006m\u0004\"CAB%\u0005\u0005I\u0011BAC\u0005)\u0001\u0016mZ3SC:<Wm\u001d\u0006\u00037q\t1\u0001\u001d3g\u0015\tib$\u0001\u0003vi&d'\"A\u0010\u0002\t\r|G-Z\u0002\u0001'\u0011\u0001!\u0005K\u0016\u0011\u0005\r2S\"\u0001\u0013\u000b\u0003\u0015\nQa]2bY\u0006L!a\n\u0013\u0003\r\u0005s\u0017PU3g!\t\u0019\u0013&\u0003\u0002+I\t9\u0001K]8ek\u000e$\bC\u0001\u00175\u001d\ti#G\u0004\u0002/c5\tqF\u0003\u00021A\u00051AH]8pizJ\u0011!J\u0005\u0003g\u0011\nq\u0001]1dW\u0006<W-\u0003\u00026m\ta1+\u001a:jC2L'0\u00192mK*\u00111\u0007J\u0001\u0007e\u0006tw-Z:\u0016\u0003e\u00022\u0001\f\u001e=\u0013\tYdGA\u0002TKF\u0004\"!P$\u000e\u0003yR!a\u0010!\u0002\tA\fw-\u001a\u0006\u00037\u0005S!AQ\"\u0002\u000b5|G-\u001a7\u000b\u0005\u0011+\u0015!B:fU\u0012\f'\"\u0001$\u0002\u0007=\u0014x-\u0003\u0002I}\tI\u0001+Y4f%\u0006tw-Z\u0001\be\u0006tw-Z:!\u0003\u0019a\u0014N\\5u}Q\u0011AJ\u0014\t\u0003\u001b\u0002i\u0011A\u0007\u0005\u0006o\r\u0001\r!O\u0001\u0010gBd\u0017\u000e\u001e\"pk:$\u0017M]5fgV\t\u0011\u000bE\u0002S+^k\u0011a\u0015\u0006\u0003)\u0012\n!bY8mY\u0016\u001cG/[8o\u0013\t16KA\u0005T_J$X\rZ*fiB\u00111\u0005W\u0005\u00033\u0012\u00121!\u00138u\u0003A\u0019\b\u000f\\5u\u0005>,h\u000eZ1sS\u0016\u001c(*F\u0001]!\ri\u0016mY\u0007\u0002=*\u0011Qd\u0018\u0006\u0002A\u0006!!.\u0019<b\u0013\t\u0011gLA\u0002TKR\u0004\"\u0001Z4\u000e\u0003\u0015T!AZ0\u0002\t1\fgnZ\u0005\u0003Q\u0016\u0014q!\u00138uK\u001e,'/\u0001\u0003d_BLHC\u0001'l\u0011\u001d9d\u0001%AA\u0002e\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001oU\tItnK\u0001q!\t\th/D\u0001s\u0015\t\u0019H/A\u0005v]\u000eDWmY6fI*\u0011Q\u000fJ\u0001\u000bC:tw\u000e^1uS>t\u0017BA<s\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003i\u0004\"\u0001Z>\n\u0005q,'AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001X\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!a\u0001\u0002\nA\u00191%!\u0002\n\u0007\u0005\u001dAEA\u0002B]fD\u0001\"a\u0003\u000b\u0003\u0003\u0005\raV\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005E\u0001#\u0002*\u0002\u0014\u0005\r\u0011bAA\u000b'\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\tY\"!\t\u0011\u0007\r\ni\"C\u0002\u0002 \u0011\u0012qAQ8pY\u0016\fg\u000eC\u0005\u0002\f1\t\t\u00111\u0001\u0002\u0004\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\rQ\u0018q\u0005\u0005\t\u0003\u0017i\u0011\u0011!a\u0001/\u0006A\u0001.Y:i\u0007>$W\rF\u0001X\u0003!!xn\u0015;sS:<G#\u0001>\u0002\r\u0015\fX/\u00197t)\u0011\tY\"!\u000e\t\u0013\u0005-\u0001#!AA\u0002\u0005\r\u0011A\u0003)bO\u0016\u0014\u0016M\\4fgB\u0011QJE\n\u0005%\t\ni\u0004\u0005\u0003\u0002@\u0005\u0015SBAA!\u0015\r\t\u0019eX\u0001\u0003S>L1!NA!)\t\tI$A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u00055\u00131\u000b\t\u0005G\u0005=C*C\u0002\u0002R\u0011\u0012aa\u00149uS>t\u0007bBA+)\u0001\u0007\u0011qK\u0001\u0002gB!\u0011\u0011LA1\u001d\u0011\tY&!\u0018\u0011\u00059\"\u0013bAA0I\u00051\u0001K]3eK\u001aL1\u0001`A2\u0015\r\ty\u0006J\u0001\u000bMJ|Wn\u0015;sS:<G\u0003BA5\u0003g\u0002R!a\u001b\u0002rqj!!!\u001c\u000b\u0007\u0005=4+A\u0005j[6,H/\u00192mK&\u00191(!\u001c\t\u000f\u0005US\u00031\u0001\u0002X\u0005)\u0011\r\u001d9msR\u0019A*!\u001f\t\u000b]2\u0002\u0019A\u001d\u0015\t\u0005u\u0014q\u0010\t\u0005G\u0005=\u0013\b\u0003\u0005\u0002\u0002^\t\t\u00111\u0001M\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u000f\u00032\u0001ZAE\u0013\r\tY)\u001a\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PageRanges.class */
public class PageRanges implements Product, Serializable {
    private final Seq<org.sejda.model.pdf.page.PageRange> ranges;

    public static Option<Seq<org.sejda.model.pdf.page.PageRange>> unapply(final PageRanges x$0) {
        return PageRanges$.MODULE$.unapply(x$0);
    }

    public static PageRanges apply(final Seq<org.sejda.model.pdf.page.PageRange> ranges) {
        return PageRanges$.MODULE$.apply(ranges);
    }

    public static Seq<org.sejda.model.pdf.page.PageRange> fromString(final String s) {
        return PageRanges$.MODULE$.fromString(s);
    }

    public static Option<PageRanges> unapply(final String s) {
        return PageRanges$.MODULE$.unapply(s);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Seq<org.sejda.model.pdf.page.PageRange> ranges() {
        return this.ranges;
    }

    public PageRanges copy(final Seq<org.sejda.model.pdf.page.PageRange> ranges) {
        return new PageRanges(ranges);
    }

    public Seq<org.sejda.model.pdf.page.PageRange> copy$default$1() {
        return ranges();
    }

    public String productPrefix() {
        return "PageRanges";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return ranges();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof PageRanges;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "ranges";
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
            if (x$1 instanceof PageRanges) {
                PageRanges pageRanges = (PageRanges) x$1;
                Seq<org.sejda.model.pdf.page.PageRange> seqRanges = ranges();
                Seq<org.sejda.model.pdf.page.PageRange> seqRanges2 = pageRanges.ranges();
                if (seqRanges != null ? seqRanges.equals(seqRanges2) : seqRanges2 == null) {
                    if (pageRanges.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public PageRanges(final Seq<org.sejda.model.pdf.page.PageRange> ranges) {
        this.ranges = ranges;
        Product.$init$(this);
    }

    public SortedSet<Object> splitBoundaries() {
        return (SortedSet) SortedSet$.MODULE$.apply((Seq) ranges().flatMap(x$1 -> {
            return new PageRangeExt(x$1).splitBoundaries();
        }), Ordering$Int$.MODULE$);
    }

    public Set<Integer> splitBoundariesJ() {
        return (Set) JavaConverters$.MODULE$.setAsJavaSetConverter(splitBoundaries().map(x -> {
            return $anonfun$splitBoundariesJ$1(BoxesRunTime.unboxToInt(x));
        }, Ordering$.MODULE$.ordered(Predef$.MODULE$.$conforms()))).asJava();
    }

    public static final /* synthetic */ Integer $anonfun$splitBoundariesJ$1(final int x) {
        return Predef$.MODULE$.int2Integer(x);
    }
}
