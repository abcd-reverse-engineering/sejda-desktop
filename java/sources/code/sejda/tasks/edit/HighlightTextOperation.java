package code.sejda.tasks.edit;

import java.awt.Color;
import java.io.Serializable;
import org.sejda.model.RectangularBox;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005Mf\u0001B\u0010!\u0001&B\u0001b\u0010\u0001\u0003\u0016\u0004%\t\u0001\u0011\u0005\t\t\u0002\u0011\t\u0012)A\u0005\u0003\"AQ\t\u0001BK\u0002\u0013\u0005a\t\u0003\u0005Y\u0001\tE\t\u0015!\u0003H\u0011!I\u0006A!f\u0001\n\u0003Q\u0006\u0002C2\u0001\u0005#\u0005\u000b\u0011B.\t\u0011\u0011\u0004!Q3A\u0005\u0002\u0015D\u0001B\u001b\u0001\u0003\u0012\u0003\u0006IA\u001a\u0005\u0006W\u0002!\t\u0001\u001c\u0005\be\u0002\t\t\u0011\"\u0001t\u0011\u001dA\b!%A\u0005\u0002eD\u0011\"!\u0003\u0001#\u0003%\t!a\u0003\t\u0013\u0005=\u0001!%A\u0005\u0002\u0005E\u0001\"CA\u000b\u0001E\u0005I\u0011AA\f\u0011%\tY\u0002AA\u0001\n\u0003\ni\u0002\u0003\u0005\u0002,\u0001\t\t\u0011\"\u0001A\u0011%\ti\u0003AA\u0001\n\u0003\ty\u0003C\u0005\u0002<\u0001\t\t\u0011\"\u0011\u0002>!I\u00111\n\u0001\u0002\u0002\u0013\u0005\u0011Q\n\u0005\n\u0003/\u0002\u0011\u0011!C!\u00033B\u0011\"!\u0018\u0001\u0003\u0003%\t%a\u0018\t\u0013\u0005\u0005\u0004!!A\u0005B\u0005\r\u0004\"CA3\u0001\u0005\u0005I\u0011IA4\u000f%\tY\u0007IA\u0001\u0012\u0003\tiG\u0002\u0005 A\u0005\u0005\t\u0012AA8\u0011\u0019Y\u0017\u0004\"\u0001\u0002\b\"I\u0011\u0011M\r\u0002\u0002\u0013\u0015\u00131\r\u0005\n\u0003\u0013K\u0012\u0011!CA\u0003\u0017C\u0011\"!&\u001a\u0003\u0003%\t)a&\t\u0013\u0005%\u0016$!A\u0005\n\u0005-&A\u0006%jO\"d\u0017n\u001a5u)\u0016DHo\u00149fe\u0006$\u0018n\u001c8\u000b\u0005\u0005\u0012\u0013\u0001B3eSRT!a\t\u0013\u0002\u000bQ\f7o[:\u000b\u0005\u00152\u0013!B:fU\u0012\f'\"A\u0014\u0002\t\r|G-Z\u0002\u0001'\u0011\u0001!\u0006M\u001a\u0011\u0005-rS\"\u0001\u0017\u000b\u00035\nQa]2bY\u0006L!a\f\u0017\u0003\r\u0005s\u0017PU3g!\tY\u0013'\u0003\u00023Y\t9\u0001K]8ek\u000e$\bC\u0001\u001b=\u001d\t)$H\u0004\u00027s5\tqG\u0003\u00029Q\u00051AH]8pizJ\u0011!L\u0005\u0003w1\nq\u0001]1dW\u0006<W-\u0003\u0002>}\ta1+\u001a:jC2L'0\u00192mK*\u00111\bL\u0001\u000ba\u0006<WMT;nE\u0016\u0014X#A!\u0011\u0005-\u0012\u0015BA\"-\u0005\rIe\u000e^\u0001\fa\u0006<WMT;nE\u0016\u0014\b%A\u0007c_VtG-\u001b8h\u0005>DXm]\u000b\u0002\u000fB\u0019\u0001\nT(\u000f\u0005%S\u0005C\u0001\u001c-\u0013\tYE&\u0001\u0004Qe\u0016$WMZ\u0005\u0003\u001b:\u00131aU3u\u0015\tYE\u0006\u0005\u0002Q-6\t\u0011K\u0003\u0002S'\u0006)Qn\u001c3fY*\u0011Q\u0005\u0016\u0006\u0002+\u0006\u0019qN]4\n\u0005]\u000b&A\u0004*fGR\fgnZ;mCJ\u0014u\u000e_\u0001\u000fE>,h\u000eZ5oO\n{\u00070Z:!\u0003\u0015\u0019w\u000e\\8s+\u0005Y\u0006C\u0001/b\u001b\u0005i&B\u00010`\u0003\r\tw\u000f\u001e\u0006\u0002A\u0006!!.\u0019<b\u0013\t\u0011WLA\u0003D_2|'/\u0001\u0004d_2|'\u000fI\u0001\u0005W&tG-F\u0001g!\t9\u0007.D\u0001!\u0013\tI\u0007EA\u0007IS\u001eDG.[4iiRK\b/Z\u0001\u0006W&tG\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u000b5tw\u000e]9\u0011\u0005\u001d\u0004\u0001\"B \n\u0001\u0004\t\u0005\"B#\n\u0001\u00049\u0005\"B-\n\u0001\u0004Y\u0006\"\u00023\n\u0001\u00041\u0017\u0001B2paf$R!\u001c;vm^Dqa\u0010\u0006\u0011\u0002\u0003\u0007\u0011\tC\u0004F\u0015A\u0005\t\u0019A$\t\u000feS\u0001\u0013!a\u00017\"9AM\u0003I\u0001\u0002\u00041\u0017AD2paf$C-\u001a4bk2$H%M\u000b\u0002u*\u0012\u0011i_\u0016\u0002yB\u0019Q0!\u0002\u000e\u0003yT1a`A\u0001\u0003%)hn\u00195fG.,GMC\u0002\u0002\u00041\n!\"\u00198o_R\fG/[8o\u0013\r\t9A \u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0003\u001bQ#aR>\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011\u00111\u0003\u0016\u00037n\fabY8qs\u0012\"WMZ1vYR$C'\u0006\u0002\u0002\u001a)\u0012am_\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005}\u0001\u0003BA\u0011\u0003Oi!!a\t\u000b\u0007\u0005\u0015r,\u0001\u0003mC:<\u0017\u0002BA\u0015\u0003G\u0011aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003c\t9\u0004E\u0002,\u0003gI1!!\u000e-\u0005\r\te.\u001f\u0005\t\u0003s\t\u0012\u0011!a\u0001\u0003\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a\u0010\u0011\r\u0005\u0005\u0013qIA\u0019\u001b\t\t\u0019EC\u0002\u0002F1\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\tI%a\u0011\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003\u001f\n)\u0006E\u0002,\u0003#J1!a\u0015-\u0005\u001d\u0011un\u001c7fC:D\u0011\"!\u000f\u0014\u0003\u0003\u0005\r!!\r\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003?\tY\u0006\u0003\u0005\u0002:Q\t\t\u00111\u0001B\u0003!A\u0017m\u001d5D_\u0012,G#A!\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\b\u0002\r\u0015\fX/\u00197t)\u0011\ty%!\u001b\t\u0013\u0005er#!AA\u0002\u0005E\u0012A\u0006%jO\"d\u0017n\u001a5u)\u0016DHo\u00149fe\u0006$\u0018n\u001c8\u0011\u0005\u001dL2#B\r\u0002r\u0005u\u0004#CA:\u0003s\nui\u00174n\u001b\t\t)HC\u0002\u0002x1\nqA];oi&lW-\u0003\u0003\u0002|\u0005U$!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oiA!\u0011qPAC\u001b\t\t\tIC\u0002\u0002\u0004~\u000b!![8\n\u0007u\n\t\t\u0006\u0002\u0002n\u0005)\u0011\r\u001d9msRIQ.!$\u0002\u0010\u0006E\u00151\u0013\u0005\u0006\u007fq\u0001\r!\u0011\u0005\u0006\u000br\u0001\ra\u0012\u0005\u00063r\u0001\ra\u0017\u0005\u0006Ir\u0001\rAZ\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\tI*!*\u0011\u000b-\nY*a(\n\u0007\u0005uEF\u0001\u0004PaRLwN\u001c\t\bW\u0005\u0005\u0016iR.g\u0013\r\t\u0019\u000b\f\u0002\u0007)V\u0004H.\u001a\u001b\t\u0011\u0005\u001dV$!AA\u00025\f1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\ti\u000b\u0005\u0003\u0002\"\u0005=\u0016\u0002BAY\u0003G\u0011aa\u00142kK\u000e$\b")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/HighlightTextOperation.class */
public class HighlightTextOperation implements Product, Serializable {
    private final int pageNumber;
    private final Set<RectangularBox> boundingBoxes;
    private final Color color;
    private final HighlightType kind;

    public static Option<Tuple4<Object, Set<RectangularBox>, Color, HighlightType>> unapply(final HighlightTextOperation x$0) {
        return HighlightTextOperation$.MODULE$.unapply(x$0);
    }

    public static HighlightTextOperation apply(final int pageNumber, final Set<RectangularBox> boundingBoxes, final Color color, final HighlightType kind) {
        return HighlightTextOperation$.MODULE$.apply(pageNumber, boundingBoxes, color, kind);
    }

    public static Function1<Tuple4<Object, Set<RectangularBox>, Color, HighlightType>, HighlightTextOperation> tupled() {
        return HighlightTextOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Set<RectangularBox>, Function1<Color, Function1<HighlightType, HighlightTextOperation>>>> curried() {
        return HighlightTextOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public Set<RectangularBox> boundingBoxes() {
        return this.boundingBoxes;
    }

    public Color color() {
        return this.color;
    }

    public HighlightType kind() {
        return this.kind;
    }

    public HighlightTextOperation copy(final int pageNumber, final Set<RectangularBox> boundingBoxes, final Color color, final HighlightType kind) {
        return new HighlightTextOperation(pageNumber, boundingBoxes, color, kind);
    }

    public int copy$default$1() {
        return pageNumber();
    }

    public Set<RectangularBox> copy$default$2() {
        return boundingBoxes();
    }

    public Color copy$default$3() {
        return color();
    }

    public HighlightType copy$default$4() {
        return kind();
    }

    public String productPrefix() {
        return "HighlightTextOperation";
    }

    public int productArity() {
        return 4;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 1:
                return boundingBoxes();
            case 2:
                return color();
            case 3:
                return kind();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof HighlightTextOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "pageNumber";
            case 1:
                return "boundingBoxes";
            case 2:
                return "color";
            case 3:
                return "kind";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), pageNumber()), Statics.anyHash(boundingBoxes())), Statics.anyHash(color())), Statics.anyHash(kind())), 4);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof HighlightTextOperation) {
                HighlightTextOperation highlightTextOperation = (HighlightTextOperation) x$1;
                if (pageNumber() == highlightTextOperation.pageNumber()) {
                    Set<RectangularBox> setBoundingBoxes = boundingBoxes();
                    Set<RectangularBox> setBoundingBoxes2 = highlightTextOperation.boundingBoxes();
                    if (setBoundingBoxes != null ? setBoundingBoxes.equals(setBoundingBoxes2) : setBoundingBoxes2 == null) {
                        Color color = color();
                        Color color2 = highlightTextOperation.color();
                        if (color != null ? color.equals(color2) : color2 == null) {
                            HighlightType highlightTypeKind = kind();
                            HighlightType highlightTypeKind2 = highlightTextOperation.kind();
                            if (highlightTypeKind != null ? highlightTypeKind.equals(highlightTypeKind2) : highlightTypeKind2 == null) {
                                if (highlightTextOperation.canEqual(this)) {
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

    public HighlightTextOperation(final int pageNumber, final Set<RectangularBox> boundingBoxes, final Color color, final HighlightType kind) {
        this.pageNumber = pageNumber;
        this.boundingBoxes = boundingBoxes;
        this.color = color;
        this.kind = kind;
        Product.$init$(this);
    }
}
