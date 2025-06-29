package code.sejda.tasks.edit;

import java.awt.Color;
import java.io.Serializable;
import org.sejda.model.RectangularBox;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple6;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005=h\u0001B\u0014)\u0001FB\u0001b\u0012\u0001\u0003\u0016\u0004%\t\u0001\u0013\u0005\t\u0019\u0002\u0011\t\u0012)A\u0005\u0013\"AQ\n\u0001BK\u0002\u0013\u0005a\n\u0003\u0005Y\u0001\tE\t\u0015!\u0003P\u0011!I\u0006A!f\u0001\n\u0003Q\u0006\u0002C2\u0001\u0005#\u0005\u000b\u0011B.\t\u0011\u0011\u0004!Q3A\u0005\u0002\u0015D\u0001\u0002\u001c\u0001\u0003\u0012\u0003\u0006IA\u001a\u0005\t[\u0002\u0011)\u001a!C\u0001]\"Aq\u000e\u0001B\tB\u0003%\u0011\u000e\u0003\u0005q\u0001\tU\r\u0011\"\u0001r\u0011!Q\bA!E!\u0002\u0013\u0011\b\"B>\u0001\t\u0003a\b\"CA\u0006\u0001\u0005\u0005I\u0011AA\u0007\u0011%\tY\u0002AI\u0001\n\u0003\ti\u0002C\u0005\u00024\u0001\t\n\u0011\"\u0001\u00026!I\u0011\u0011\b\u0001\u0012\u0002\u0013\u0005\u00111\b\u0005\n\u0003\u007f\u0001\u0011\u0013!C\u0001\u0003\u0003B\u0011\"!\u0012\u0001#\u0003%\t!a\u0012\t\u0013\u0005-\u0003!%A\u0005\u0002\u00055\u0003\"CA)\u0001\u0005\u0005I\u0011IA*\u0011!\ty\u0006AA\u0001\n\u0003A\u0005\"CA1\u0001\u0005\u0005I\u0011AA2\u0011%\ty\u0007AA\u0001\n\u0003\n\t\bC\u0005\u0002��\u0001\t\t\u0011\"\u0001\u0002\u0002\"I\u00111\u0012\u0001\u0002\u0002\u0013\u0005\u0013Q\u0012\u0005\n\u0003#\u0003\u0011\u0011!C!\u0003'C\u0011\"!&\u0001\u0003\u0003%\t%a&\t\u0013\u0005e\u0005!!A\u0005B\u0005mu!CAPQ\u0005\u0005\t\u0012AAQ\r!9\u0003&!A\t\u0002\u0005\r\u0006BB> \t\u0003\tY\fC\u0005\u0002\u0016~\t\t\u0011\"\u0012\u0002\u0018\"I\u0011QX\u0010\u0002\u0002\u0013\u0005\u0015q\u0018\u0005\n\u0003\u001b|\u0012\u0013!C\u0001\u0003\u001bB\u0011\"a4 \u0003\u0003%\t)!5\t\u0013\u0005\rx$%A\u0005\u0002\u00055\u0003\"CAs?\u0005\u0005I\u0011BAt\u0005M\t\u0005\u000f]3oI2Kg.Z(qKJ\fG/[8o\u0015\tI#&\u0001\u0003fI&$(BA\u0016-\u0003\u0015!\u0018m]6t\u0015\tic&A\u0003tK*$\u0017MC\u00010\u0003\u0011\u0019w\u000eZ3\u0004\u0001M!\u0001A\r\u001d<!\t\u0019d'D\u00015\u0015\u0005)\u0014!B:dC2\f\u0017BA\u001c5\u0005\u0019\te.\u001f*fMB\u00111'O\u0005\u0003uQ\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002=\t:\u0011QH\u0011\b\u0003}\u0005k\u0011a\u0010\u0006\u0003\u0001B\na\u0001\u0010:p_Rt\u0014\"A\u001b\n\u0005\r#\u0014a\u00029bG.\fw-Z\u0005\u0003\u000b\u001a\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!a\u0011\u001b\u0002\u0015A\fw-\u001a(v[\n,'/F\u0001J!\t\u0019$*\u0003\u0002Li\t\u0019\u0011J\u001c;\u0002\u0017A\fw-\u001a(v[\n,'\u000fI\u0001\fE>,h\u000eZ5oO\n{\u00070F\u0001P!\t\u0001f+D\u0001R\u0015\t\u00116+A\u0003n_\u0012,GN\u0003\u0002.)*\tQ+A\u0002pe\u001eL!aV)\u0003\u001dI+7\r^1oOVd\u0017M\u001d\"pq\u0006a!m\\;oI&twMQ8yA\u0005)1m\u001c7peV\t1\f\u0005\u0002]C6\tQL\u0003\u0002_?\u0006\u0019\u0011m\u001e;\u000b\u0003\u0001\fAA[1wC&\u0011!-\u0018\u0002\u0006\u0007>dwN]\u0001\u0007G>dwN\u001d\u0011\u0002\rA|\u0017N\u001c;t+\u00051\u0007c\u0001\u001fhS&\u0011\u0001N\u0012\u0002\u0004'\u0016\f\bCA\u001ak\u0013\tYGG\u0001\u0004E_V\u0014G.Z\u0001\ba>Lg\u000e^:!\u0003!a\u0017N\\3TSj,W#A5\u0002\u00131Lg.Z*ju\u0016\u0004\u0013\u0001B6j]\u0012,\u0012A\u001d\t\u0003g^t!\u0001^;\u0011\u0005y\"\u0014B\u0001<5\u0003\u0019\u0001&/\u001a3fM&\u0011\u00010\u001f\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005Y$\u0014!B6j]\u0012\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0007~\u007f\u0006\u0005\u00111AA\u0003\u0003\u000f\tI\u0001\u0005\u0002\u007f\u00015\t\u0001\u0006C\u0003H\u001b\u0001\u0007\u0011\nC\u0003N\u001b\u0001\u0007q\nC\u0003Z\u001b\u0001\u00071\fC\u0003e\u001b\u0001\u0007a\rC\u0003n\u001b\u0001\u0007\u0011\u000eC\u0004q\u001bA\u0005\t\u0019\u0001:\u0002\t\r|\u0007/\u001f\u000b\u000e{\u0006=\u0011\u0011CA\n\u0003+\t9\"!\u0007\t\u000f\u001ds\u0001\u0013!a\u0001\u0013\"9QJ\u0004I\u0001\u0002\u0004y\u0005bB-\u000f!\u0003\u0005\ra\u0017\u0005\bI:\u0001\n\u00111\u0001g\u0011\u001dig\u0002%AA\u0002%Dq\u0001\u001d\b\u0011\u0002\u0003\u0007!/\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005}!fA%\u0002\"-\u0012\u00111\u0005\t\u0005\u0003K\ty#\u0004\u0002\u0002()!\u0011\u0011FA\u0016\u0003%)hn\u00195fG.,GMC\u0002\u0002.Q\n!\"\u00198o_R\fG/[8o\u0013\u0011\t\t$a\n\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\u0005]\"fA(\u0002\"\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTCAA\u001fU\rY\u0016\u0011E\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00135+\t\t\u0019EK\u0002g\u0003C\tabY8qs\u0012\"WMZ1vYR$S'\u0006\u0002\u0002J)\u001a\u0011.!\t\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%mU\u0011\u0011q\n\u0016\u0004e\u0006\u0005\u0012!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002VA!\u0011qKA/\u001b\t\tIFC\u0002\u0002\\}\u000bA\u0001\\1oO&\u0019\u00010!\u0017\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u0011QMA6!\r\u0019\u0014qM\u0005\u0004\u0003S\"$aA!os\"A\u0011QN\f\u0002\u0002\u0003\u0007\u0011*A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003g\u0002b!!\u001e\u0002|\u0005\u0015TBAA<\u0015\r\tI\bN\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA?\u0003o\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u00111QAE!\r\u0019\u0014QQ\u0005\u0004\u0003\u000f#$a\u0002\"p_2,\u0017M\u001c\u0005\n\u0003[J\u0012\u0011!a\u0001\u0003K\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011QKAH\u0011!\tiGGA\u0001\u0002\u0004I\u0015\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003%\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003+\na!Z9vC2\u001cH\u0003BAB\u0003;C\u0011\"!\u001c\u001e\u0003\u0003\u0005\r!!\u001a\u0002'\u0005\u0003\b/\u001a8e\u0019&tWm\u00149fe\u0006$\u0018n\u001c8\u0011\u0005y|2#B\u0010\u0002&\u0006E\u0006cCAT\u0003[Kuj\u00174jevl!!!+\u000b\u0007\u0005-F'A\u0004sk:$\u0018.\\3\n\t\u0005=\u0016\u0011\u0016\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:4\u0004\u0003BAZ\u0003sk!!!.\u000b\u0007\u0005]v,\u0001\u0002j_&\u0019Q)!.\u0015\u0005\u0005\u0005\u0016!B1qa2LH#D?\u0002B\u0006\r\u0017QYAd\u0003\u0013\fY\rC\u0003HE\u0001\u0007\u0011\nC\u0003NE\u0001\u0007q\nC\u0003ZE\u0001\u00071\fC\u0003eE\u0001\u0007a\rC\u0003nE\u0001\u0007\u0011\u000eC\u0004qEA\u0005\t\u0019\u0001:\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIY\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002T\u0006}\u0007#B\u001a\u0002V\u0006e\u0017bAAli\t1q\n\u001d;j_:\u0004\u0012bMAn\u0013>[f-\u001b:\n\u0007\u0005uGG\u0001\u0004UkBdWM\u000e\u0005\t\u0003C$\u0013\u0011!a\u0001{\u0006\u0019\u0001\u0010\n\u0019\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00137\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\tI\u000f\u0005\u0003\u0002X\u0005-\u0018\u0002BAw\u00033\u0012aa\u00142kK\u000e$\b")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendLineOperation.class */
public class AppendLineOperation implements Product, Serializable {
    private final int pageNumber;
    private final RectangularBox boundingBox;
    private final Color color;
    private final Seq<Object> points;
    private final double lineSize;
    private final String kind;

    public static Option<Tuple6<Object, RectangularBox, Color, Seq<Object>, Object, String>> unapply(final AppendLineOperation x$0) {
        return AppendLineOperation$.MODULE$.unapply(x$0);
    }

    public static AppendLineOperation apply(final int pageNumber, final RectangularBox boundingBox, final Color color, final Seq<Object> points, final double lineSize, final String kind) {
        return AppendLineOperation$.MODULE$.apply(pageNumber, boundingBox, color, points, lineSize, kind);
    }

    public static Function1<Tuple6<Object, RectangularBox, Color, Seq<Object>, Object, String>, AppendLineOperation> tupled() {
        return AppendLineOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<RectangularBox, Function1<Color, Function1<Seq<Object>, Function1<Object, Function1<String, AppendLineOperation>>>>>> curried() {
        return AppendLineOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public RectangularBox boundingBox() {
        return this.boundingBox;
    }

    public Color color() {
        return this.color;
    }

    public Seq<Object> points() {
        return this.points;
    }

    public double lineSize() {
        return this.lineSize;
    }

    public String kind() {
        return this.kind;
    }

    public AppendLineOperation copy(final int pageNumber, final RectangularBox boundingBox, final Color color, final Seq<Object> points, final double lineSize, final String kind) {
        return new AppendLineOperation(pageNumber, boundingBox, color, points, lineSize, kind);
    }

    public int copy$default$1() {
        return pageNumber();
    }

    public RectangularBox copy$default$2() {
        return boundingBox();
    }

    public Color copy$default$3() {
        return color();
    }

    public Seq<Object> copy$default$4() {
        return points();
    }

    public double copy$default$5() {
        return lineSize();
    }

    public String copy$default$6() {
        return kind();
    }

    public String productPrefix() {
        return "AppendLineOperation";
    }

    public int productArity() {
        return 6;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 1:
                return boundingBox();
            case 2:
                return color();
            case 3:
                return points();
            case 4:
                return BoxesRunTime.boxToDouble(lineSize());
            case 5:
                return kind();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof AppendLineOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "pageNumber";
            case 1:
                return "boundingBox";
            case 2:
                return "color";
            case 3:
                return "points";
            case 4:
                return "lineSize";
            case 5:
                return "kind";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), pageNumber()), Statics.anyHash(boundingBox())), Statics.anyHash(color())), Statics.anyHash(points())), Statics.doubleHash(lineSize())), Statics.anyHash(kind())), 6);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof AppendLineOperation) {
                AppendLineOperation appendLineOperation = (AppendLineOperation) x$1;
                if (pageNumber() == appendLineOperation.pageNumber() && lineSize() == appendLineOperation.lineSize()) {
                    RectangularBox rectangularBoxBoundingBox = boundingBox();
                    RectangularBox rectangularBoxBoundingBox2 = appendLineOperation.boundingBox();
                    if (rectangularBoxBoundingBox != null ? rectangularBoxBoundingBox.equals(rectangularBoxBoundingBox2) : rectangularBoxBoundingBox2 == null) {
                        Color color = color();
                        Color color2 = appendLineOperation.color();
                        if (color != null ? color.equals(color2) : color2 == null) {
                            Seq<Object> seqPoints = points();
                            Seq<Object> seqPoints2 = appendLineOperation.points();
                            if (seqPoints != null ? seqPoints.equals(seqPoints2) : seqPoints2 == null) {
                                String strKind = kind();
                                String strKind2 = appendLineOperation.kind();
                                if (strKind != null ? strKind.equals(strKind2) : strKind2 == null) {
                                    if (appendLineOperation.canEqual(this)) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public AppendLineOperation(final int pageNumber, final RectangularBox boundingBox, final Color color, final Seq<Object> points, final double lineSize, final String kind) {
        this.pageNumber = pageNumber;
        this.boundingBox = boundingBox;
        this.color = color;
        this.points = points;
        this.lineSize = lineSize;
        this.kind = kind;
        Product.$init$(this);
    }
}
