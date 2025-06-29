package code.sejda.tasks.edit;

import java.awt.Color;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u00055d\u0001\u0002\u000f\u001e\u0001\u001aB\u0001\u0002\u0010\u0001\u0003\u0016\u0004%\t!\u0010\u0005\t\u0003\u0002\u0011\t\u0012)A\u0005}!A!\t\u0001BK\u0002\u0013\u0005Q\b\u0003\u0005D\u0001\tE\t\u0015!\u0003?\u0011!!\u0005A!f\u0001\n\u0003)\u0005\u0002\u0003(\u0001\u0005#\u0005\u000b\u0011\u0002$\t\u000b=\u0003A\u0011\u0001)\t\u000fY\u0003\u0011\u0011!C\u0001/\"91\fAI\u0001\n\u0003a\u0006bB4\u0001#\u0003%\t\u0001\u0018\u0005\bQ\u0002\t\n\u0011\"\u0001j\u0011\u001dY\u0007!!A\u0005B1Dqa\u001d\u0001\u0002\u0002\u0013\u0005Q\bC\u0004u\u0001\u0005\u0005I\u0011A;\t\u000fm\u0004\u0011\u0011!C!y\"I\u0011q\u0001\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0002\u0005\n\u0003'\u0001\u0011\u0011!C!\u0003+A\u0011\"!\u0007\u0001\u0003\u0003%\t%a\u0007\t\u0013\u0005u\u0001!!A\u0005B\u0005}\u0001\"CA\u0011\u0001\u0005\u0005I\u0011IA\u0012\u000f%\t9#HA\u0001\u0012\u0003\tIC\u0002\u0005\u001d;\u0005\u0005\t\u0012AA\u0016\u0011\u0019ye\u0003\"\u0001\u0002D!I\u0011Q\u0004\f\u0002\u0002\u0013\u0015\u0013q\u0004\u0005\n\u0003\u000b2\u0012\u0011!CA\u0003\u000fB\u0011\"a\u0014\u0017\u0003\u0003%\t)!\u0015\t\u0013\u0005\rd#!A\u0005\n\u0005\u0015$AF#eSRD\u0015n\u001a5mS\u001eDGo\u00149fe\u0006$\u0018n\u001c8\u000b\u0005yy\u0012\u0001B3eSRT!\u0001I\u0011\u0002\u000bQ\f7o[:\u000b\u0005\t\u001a\u0013!B:fU\u0012\f'\"\u0001\u0013\u0002\t\r|G-Z\u0002\u0001'\u0011\u0001q%\f\u0019\u0011\u0005!ZS\"A\u0015\u000b\u0003)\nQa]2bY\u0006L!\u0001L\u0015\u0003\r\u0005s\u0017PU3g!\tAc&\u0003\u00020S\t9\u0001K]8ek\u000e$\bCA\u0019:\u001d\t\u0011tG\u0004\u00024m5\tAG\u0003\u00026K\u00051AH]8pizJ\u0011AK\u0005\u0003q%\nq\u0001]1dW\u0006<W-\u0003\u0002;w\ta1+\u001a:jC2L'0\u00192mK*\u0011\u0001(K\u0001\u000ba\u0006<WMT;nE\u0016\u0014X#\u0001 \u0011\u0005!z\u0014B\u0001!*\u0005\rIe\u000e^\u0001\fa\u0006<WMT;nE\u0016\u0014\b%\u0001\u0002jI\u0006\u0019\u0011\u000e\u001a\u0011\u0002\u000b\r|Gn\u001c:\u0016\u0003\u0019\u0003\"a\u0012'\u000e\u0003!S!!\u0013&\u0002\u0007\u0005<HOC\u0001L\u0003\u0011Q\u0017M^1\n\u00055C%!B\"pY>\u0014\u0018AB2pY>\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0005#N#V\u000b\u0005\u0002S\u00015\tQ\u0004C\u0003=\u000f\u0001\u0007a\bC\u0003C\u000f\u0001\u0007a\bC\u0003E\u000f\u0001\u0007a)\u0001\u0003d_BLH\u0003B)Y3jCq\u0001\u0010\u0005\u0011\u0002\u0003\u0007a\bC\u0004C\u0011A\u0005\t\u0019\u0001 \t\u000f\u0011C\u0001\u0013!a\u0001\r\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A/+\u0005yr6&A0\u0011\u0005\u0001,W\"A1\u000b\u0005\t\u001c\u0017!C;oG\",7m[3e\u0015\t!\u0017&\u0001\u0006b]:|G/\u0019;j_:L!AZ1\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\t!N\u000b\u0002G=\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012!\u001c\t\u0003]Fl\u0011a\u001c\u0006\u0003a*\u000bA\u0001\\1oO&\u0011!o\u001c\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011a/\u001f\t\u0003Q]L!\u0001_\u0015\u0003\u0007\u0005s\u0017\u0010C\u0004{\u001d\u0005\u0005\t\u0019\u0001 \u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005i\b\u0003\u0002@\u0002\u0004Yl\u0011a \u0006\u0004\u0003\u0003I\u0013AC2pY2,7\r^5p]&\u0019\u0011QA@\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003\u0017\t\t\u0002E\u0002)\u0003\u001bI1!a\u0004*\u0005\u001d\u0011un\u001c7fC:DqA\u001f\t\u0002\u0002\u0003\u0007a/\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GcA7\u0002\u0018!9!0EA\u0001\u0002\u0004q\u0014\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003y\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002[\u00061Q-];bYN$B!a\u0003\u0002&!9!\u0010FA\u0001\u0002\u00041\u0018AF#eSRD\u0015n\u001a5mS\u001eDGo\u00149fe\u0006$\u0018n\u001c8\u0011\u0005I32#\u0002\f\u0002.\u0005e\u0002\u0003CA\u0018\u0003kqdHR)\u000e\u0005\u0005E\"bAA\u001aS\u00059!/\u001e8uS6,\u0017\u0002BA\u001c\u0003c\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c84!\u0011\tY$!\u0011\u000e\u0005\u0005u\"bAA \u0015\u0006\u0011\u0011n\\\u0005\u0004u\u0005uBCAA\u0015\u0003\u0015\t\u0007\u000f\u001d7z)\u001d\t\u0016\u0011JA&\u0003\u001bBQ\u0001P\rA\u0002yBQAQ\rA\u0002yBQ\u0001R\rA\u0002\u0019\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002T\u0005}\u0003#\u0002\u0015\u0002V\u0005e\u0013bAA,S\t1q\n\u001d;j_:\u0004b\u0001KA.}y2\u0015bAA/S\t1A+\u001e9mKNB\u0001\"!\u0019\u001b\u0003\u0003\u0005\r!U\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA4!\rq\u0017\u0011N\u0005\u0004\u0003Wz'AB(cU\u0016\u001cG\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/EditHighlightOperation.class */
public class EditHighlightOperation implements Product, Serializable {
    private final int pageNumber;
    private final int id;
    private final Color color;

    public static Option<Tuple3<Object, Object, Color>> unapply(final EditHighlightOperation x$0) {
        return EditHighlightOperation$.MODULE$.unapply(x$0);
    }

    public static EditHighlightOperation apply(final int pageNumber, final int id, final Color color) {
        return EditHighlightOperation$.MODULE$.apply(pageNumber, id, color);
    }

    public static Function1<Tuple3<Object, Object, Color>, EditHighlightOperation> tupled() {
        return EditHighlightOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Object, Function1<Color, EditHighlightOperation>>> curried() {
        return EditHighlightOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public int id() {
        return this.id;
    }

    public Color color() {
        return this.color;
    }

    public EditHighlightOperation copy(final int pageNumber, final int id, final Color color) {
        return new EditHighlightOperation(pageNumber, id, color);
    }

    public int copy$default$1() {
        return pageNumber();
    }

    public int copy$default$2() {
        return id();
    }

    public Color copy$default$3() {
        return color();
    }

    public String productPrefix() {
        return "EditHighlightOperation";
    }

    public int productArity() {
        return 3;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 1:
                return BoxesRunTime.boxToInteger(id());
            case 2:
                return color();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof EditHighlightOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "pageNumber";
            case 1:
                return "id";
            case 2:
                return "color";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), pageNumber()), id()), Statics.anyHash(color())), 3);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof EditHighlightOperation) {
                EditHighlightOperation editHighlightOperation = (EditHighlightOperation) x$1;
                if (pageNumber() == editHighlightOperation.pageNumber() && id() == editHighlightOperation.id()) {
                    Color color = color();
                    Color color2 = editHighlightOperation.color();
                    if (color != null ? color.equals(color2) : color2 == null) {
                        if (editHighlightOperation.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public EditHighlightOperation(final int pageNumber, final int id, final Color color) {
        this.pageNumber = pageNumber;
        this.id = id;
        this.color = color;
        Product.$init$(this);
    }
}
