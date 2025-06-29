package code.sejda.tasks.rename;

import java.io.Serializable;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.sambox.contentstream.operator.OperatorName;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple6;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: RenameByTextParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005uf\u0001\u0002\u0014(\u0001BB\u0001B\u0012\u0001\u0003\u0016\u0004%\ta\u0012\u0005\t\u0017\u0002\u0011\t\u0012)A\u0005\u0011\"AA\n\u0001BK\u0002\u0013\u0005q\t\u0003\u0005N\u0001\tE\t\u0015!\u0003I\u0011!q\u0005A!f\u0001\n\u00039\u0005\u0002C(\u0001\u0005#\u0005\u000b\u0011\u0002%\t\u0011A\u0003!Q3A\u0005\u0002\u001dC\u0001\"\u0015\u0001\u0003\u0012\u0003\u0006I\u0001\u0013\u0005\t%\u0002\u0011)\u001a!C\u0001\u000f\"A1\u000b\u0001B\tB\u0003%\u0001\n\u0003\u0005U\u0001\tU\r\u0011\"\u0001V\u0011!q\u0006A!E!\u0002\u00131\u0006\"B0\u0001\t\u0003\u0001\u0007\"B5\u0001\t\u0003Q\u0007b\u0002;\u0001\u0003\u0003%\t!\u001e\u0005\by\u0002\t\n\u0011\"\u0001~\u0011!\t\t\u0002AI\u0001\n\u0003i\b\u0002CA\n\u0001E\u0005I\u0011A?\t\u0011\u0005U\u0001!%A\u0005\u0002uD\u0001\"a\u0006\u0001#\u0003%\t! \u0005\n\u00033\u0001\u0011\u0013!C\u0001\u00037A\u0011\"a\b\u0001\u0003\u0003%\t%!\t\t\u0011\u0005E\u0002!!A\u0005\u0002\u001dC\u0011\"a\r\u0001\u0003\u0003%\t!!\u000e\t\u0013\u0005\u0005\u0003!!A\u0005B\u0005\r\u0003\"CA)\u0001\u0005\u0005I\u0011AA*\u0011%\ti\u0006AA\u0001\n\u0003\ny\u0006C\u0005\u0002d\u0001\t\t\u0011\"\u0011\u0002f!I\u0011q\r\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u000e\u0005\n\u0003W\u0002\u0011\u0011!C!\u0003[:\u0011\"!\u001d(\u0003\u0003E\t!a\u001d\u0007\u0011\u0019:\u0013\u0011!E\u0001\u0003kBaa\u0018\u0011\u0005\u0002\u00055\u0005\"CA4A\u0005\u0005IQIA5\u0011%\ty\tIA\u0001\n\u0003\u000b\t\nC\u0005\u0002 \u0002\n\t\u0011\"!\u0002\"\"I\u00111\u0017\u0011\u0002\u0002\u0013%\u0011Q\u0017\u0002\u000b%\u0016t\u0017-\\3Be\u0016\f'B\u0001\u0015*\u0003\u0019\u0011XM\\1nK*\u0011!fK\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003Y5\nQa]3kI\u0006T\u0011AL\u0001\u0005G>$Wm\u0001\u0001\u0014\t\u0001\ttG\u000f\t\u0003eUj\u0011a\r\u0006\u0002i\u0005)1oY1mC&\u0011ag\r\u0002\u0007\u0003:L(+\u001a4\u0011\u0005IB\u0014BA\u001d4\u0005\u001d\u0001&o\u001c3vGR\u0004\"aO\"\u000f\u0005q\neBA\u001fA\u001b\u0005q$BA 0\u0003\u0019a$o\\8u}%\tA'\u0003\u0002Cg\u00059\u0001/Y2lC\u001e,\u0017B\u0001#F\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t\u00115'A\u0001y+\u0005A\u0005C\u0001\u001aJ\u0013\tQ5GA\u0002J]R\f!\u0001\u001f\u0011\u0002\u0003e\f!!\u001f\u0011\u0002\u000b]LG\r\u001e5\u0002\r]LG\r\u001e5!\u0003\u0019AW-[4ii\u00069\u0001.Z5hQR\u0004\u0013a\u00029bO\u0016tU/\\\u0001\ta\u0006<WMT;nA\u0005!a.Y7f+\u00051\u0006CA,\\\u001d\tA\u0016\f\u0005\u0002>g%\u0011!lM\u0001\u0007!J,G-\u001a4\n\u0005qk&AB*ue&twM\u0003\u0002[g\u0005)a.Y7fA\u00051A(\u001b8jiz\"r!Y2eK\u001a<\u0007\u000e\u0005\u0002c\u00015\tq\u0005C\u0003G\u001b\u0001\u0007\u0001\nC\u0003M\u001b\u0001\u0007\u0001\nC\u0003O\u001b\u0001\u0007\u0001\nC\u0003Q\u001b\u0001\u0007\u0001\nC\u0003S\u001b\u0001\u0007\u0001\nC\u0003U\u001b\u0001\u0007a+A\u0003bg\n{\u0007\u0010F\u0001l!\ta'/D\u0001n\u0015\tqw.A\u0003n_\u0012,GN\u0003\u0002-a*\t\u0011/A\u0002pe\u001eL!a]7\u0003+Q{\u0007\u000fT3giJ+7\r^1oOVd\u0017M\u001d\"pq\u0006!1m\u001c9z)\u001d\tgo\u001e=zunDqAR\b\u0011\u0002\u0003\u0007\u0001\nC\u0004M\u001fA\u0005\t\u0019\u0001%\t\u000f9{\u0001\u0013!a\u0001\u0011\"9\u0001k\u0004I\u0001\u0002\u0004A\u0005b\u0002*\u0010!\u0003\u0005\r\u0001\u0013\u0005\b)>\u0001\n\u00111\u0001W\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012A \u0016\u0003\u0011~\\#!!\u0001\u0011\t\u0005\r\u0011QB\u0007\u0003\u0003\u000bQA!a\u0002\u0002\n\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003\u0017\u0019\u0014AC1o]>$\u0018\r^5p]&!\u0011qBA\u0003\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM\nabY8qs\u0012\"WMZ1vYR$C'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%mU\u0011\u0011Q\u0004\u0016\u0003-~\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA\u0012!\u0011\t)#a\f\u000e\u0005\u0005\u001d\"\u0002BA\u0015\u0003W\tA\u0001\\1oO*\u0011\u0011QF\u0001\u0005U\u00064\u0018-C\u0002]\u0003O\tA\u0002\u001d:pIV\u001cG/\u0011:jif\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u00028\u0005u\u0002c\u0001\u001a\u0002:%\u0019\u00111H\u001a\u0003\u0007\u0005s\u0017\u0010\u0003\u0005\u0002@a\t\t\u00111\u0001I\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011Q\t\t\u0007\u0003\u000f\ni%a\u000e\u000e\u0005\u0005%#bAA&g\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005=\u0013\u0011\n\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002V\u0005m\u0003c\u0001\u001a\u0002X%\u0019\u0011\u0011L\u001a\u0003\u000f\t{w\u000e\\3b]\"I\u0011q\b\u000e\u0002\u0002\u0003\u0007\u0011qG\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002$\u0005\u0005\u0004\u0002CA 7\u0005\u0005\t\u0019\u0001%\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001S\u0001\ti>\u001cFO]5oOR\u0011\u00111E\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005U\u0013q\u000e\u0005\n\u0003\u007fq\u0012\u0011!a\u0001\u0003o\t!BU3oC6,\u0017I]3b!\t\u0011\u0007eE\u0003!\u0003o\n\u0019\tE\u0006\u0002z\u0005}\u0004\n\u0013%I\u0011Z\u000bWBAA>\u0015\r\tihM\u0001\beVtG/[7f\u0013\u0011\t\t)a\u001f\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>tg\u0007\u0005\u0003\u0002\u0006\u0006-UBAAD\u0015\u0011\tI)a\u000b\u0002\u0005%|\u0017b\u0001#\u0002\bR\u0011\u00111O\u0001\u0006CB\u0004H.\u001f\u000b\u000eC\u0006M\u0015QSAL\u00033\u000bY*!(\t\u000b\u0019\u001b\u0003\u0019\u0001%\t\u000b1\u001b\u0003\u0019\u0001%\t\u000b9\u001b\u0003\u0019\u0001%\t\u000bA\u001b\u0003\u0019\u0001%\t\u000bI\u001b\u0003\u0019\u0001%\t\u000bQ\u001b\u0003\u0019\u0001,\u0002\u000fUt\u0017\r\u001d9msR!\u00111UAX!\u0015\u0011\u0014QUAU\u0013\r\t9k\r\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0013I\nY\u000b\u0013%I\u0011\"3\u0016bAAWg\t1A+\u001e9mKZB\u0001\"!-%\u0003\u0003\u0005\r!Y\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA\\!\u0011\t)#!/\n\t\u0005m\u0016q\u0005\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/rename/RenameArea.class */
public class RenameArea implements Product, Serializable {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int pageNum;
    private final String name;

    public static Option<Tuple6<Object, Object, Object, Object, Object, String>> unapply(final RenameArea x$0) {
        return RenameArea$.MODULE$.unapply(x$0);
    }

    public static RenameArea apply(final int x, final int y, final int width, final int height, final int pageNum, final String name) {
        return RenameArea$.MODULE$.apply(x, y, width, height, pageNum, name);
    }

    public static Function1<Tuple6<Object, Object, Object, Object, Object, String>, RenameArea> tupled() {
        return RenameArea$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Object, Function1<Object, Function1<Object, Function1<Object, Function1<String, RenameArea>>>>>> curried() {
        return RenameArea$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public int pageNum() {
        return this.pageNum;
    }

    public String name() {
        return this.name;
    }

    public RenameArea copy(final int x, final int y, final int width, final int height, final int pageNum, final String name) {
        return new RenameArea(x, y, width, height, pageNum, name);
    }

    public int copy$default$1() {
        return x();
    }

    public int copy$default$2() {
        return y();
    }

    public int copy$default$3() {
        return width();
    }

    public int copy$default$4() {
        return height();
    }

    public int copy$default$5() {
        return pageNum();
    }

    public String copy$default$6() {
        return name();
    }

    public String productPrefix() {
        return "RenameArea";
    }

    public int productArity() {
        return 6;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(x());
            case 1:
                return BoxesRunTime.boxToInteger(y());
            case 2:
                return BoxesRunTime.boxToInteger(width());
            case 3:
                return BoxesRunTime.boxToInteger(height());
            case 4:
                return BoxesRunTime.boxToInteger(pageNum());
            case 5:
                return name();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof RenameArea;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "x";
            case 1:
                return OperatorName.CURVE_TO_REPLICATE_FINAL_POINT;
            case 2:
                return "width";
            case 3:
                return "height";
            case 4:
                return "pageNum";
            case 5:
                return "name";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), x()), y()), width()), height()), pageNum()), Statics.anyHash(name())), 6);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof RenameArea) {
                RenameArea renameArea = (RenameArea) x$1;
                if (x() == renameArea.x() && y() == renameArea.y() && width() == renameArea.width() && height() == renameArea.height() && pageNum() == renameArea.pageNum()) {
                    String strName = name();
                    String strName2 = renameArea.name();
                    if (strName != null ? strName.equals(strName2) : strName2 == null) {
                        if (renameArea.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public RenameArea(final int x, final int y, final int width, final int height, final int pageNum, final String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.pageNum = pageNum;
        this.name = name;
        Product.$init$(this);
    }

    public TopLeftRectangularBox asBox() {
        return new TopLeftRectangularBox(x(), y(), width(), height());
    }
}
