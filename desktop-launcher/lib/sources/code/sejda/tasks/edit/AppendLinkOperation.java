package code.sejda.tasks.edit;

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
@ScalaSignature(bytes = "\u0006\u0005\u0005-f\u0001B\u0010!\u0001&B\u0001b\u0010\u0001\u0003\u0016\u0004%\t\u0001\u0011\u0005\t\t\u0002\u0011\t\u0012)A\u0005\u0003\"AQ\t\u0001BK\u0002\u0013\u0005a\t\u0003\u0005Y\u0001\tE\t\u0015!\u0003H\u0011!I\u0006A!f\u0001\n\u0003Q\u0006\u0002\u00030\u0001\u0005#\u0005\u000b\u0011B.\t\u0011}\u0003!Q3A\u0005\u0002\u0001D\u0001\"\u001a\u0001\u0003\u0012\u0003\u0006I!\u0019\u0005\u0006M\u0002!\ta\u001a\u0005\b[\u0002\t\t\u0011\"\u0001o\u0011\u001d\u0019\b!%A\u0005\u0002QD\u0001b \u0001\u0012\u0002\u0013\u0005\u0011\u0011\u0001\u0005\n\u0003\u000b\u0001\u0011\u0013!C\u0001\u0003\u000fA\u0011\"a\u0003\u0001#\u0003%\t!!\u0004\t\u0013\u0005E\u0001!!A\u0005B\u0005M\u0001\u0002CA\u0012\u0001\u0005\u0005I\u0011\u0001!\t\u0013\u0005\u0015\u0002!!A\u0005\u0002\u0005\u001d\u0002\"CA\u001a\u0001\u0005\u0005I\u0011IA\u001b\u0011%\t\u0019\u0005AA\u0001\n\u0003\t)\u0005C\u0005\u0002P\u0001\t\t\u0011\"\u0011\u0002R!I\u0011Q\u000b\u0001\u0002\u0002\u0013\u0005\u0013q\u000b\u0005\n\u00033\u0002\u0011\u0011!C!\u00037B\u0011\"!\u0018\u0001\u0003\u0003%\t%a\u0018\b\u0013\u0005\r\u0004%!A\t\u0002\u0005\u0015d\u0001C\u0010!\u0003\u0003E\t!a\u001a\t\r\u0019LB\u0011AA@\u0011%\tI&GA\u0001\n\u000b\nY\u0006C\u0005\u0002\u0002f\t\t\u0011\"!\u0002\u0004\"I\u0011QR\r\u0002\u0002\u0013\u0005\u0015q\u0012\u0005\n\u0003CK\u0012\u0011!C\u0005\u0003G\u00131#\u00119qK:$G*\u001b8l\u001fB,'/\u0019;j_:T!!\t\u0012\u0002\t\u0015$\u0017\u000e\u001e\u0006\u0003G\u0011\nQ\u0001^1tWNT!!\n\u0014\u0002\u000bM,'\u000eZ1\u000b\u0003\u001d\nAaY8eK\u000e\u00011\u0003\u0002\u0001+aM\u0002\"a\u000b\u0018\u000e\u00031R\u0011!L\u0001\u0006g\u000e\fG.Y\u0005\u0003_1\u0012a!\u00118z%\u00164\u0007CA\u00162\u0013\t\u0011DFA\u0004Qe>$Wo\u0019;\u0011\u0005QbdBA\u001b;\u001d\t1\u0014(D\u00018\u0015\tA\u0004&\u0001\u0004=e>|GOP\u0005\u0002[%\u00111\bL\u0001\ba\u0006\u001c7.Y4f\u0013\tidH\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002<Y\u0005Q\u0001/Y4f\u001dVl'-\u001a:\u0016\u0003\u0005\u0003\"a\u000b\"\n\u0005\rc#aA%oi\u0006Y\u0001/Y4f\u001dVl'-\u001a:!\u00035\u0011w.\u001e8eS:<'i\u001c=fgV\tq\tE\u0002I\u0019>s!!\u0013&\u0011\u0005Yb\u0013BA&-\u0003\u0019\u0001&/\u001a3fM&\u0011QJ\u0014\u0002\u0004'\u0016$(BA&-!\t\u0001f+D\u0001R\u0015\t\u00116+A\u0003n_\u0012,GN\u0003\u0002&)*\tQ+A\u0002pe\u001eL!aV)\u0003\u001dI+7\r^1oOVd\u0017M\u001d\"pq\u0006q!m\\;oI&twMQ8yKN\u0004\u0013\u0001\u00025sK\u001a,\u0012a\u0017\t\u0003\u0011rK!!\u0018(\u0003\rM#(/\u001b8h\u0003\u0015A'/\u001a4!\u0003\u0011Y\u0017N\u001c3\u0016\u0003\u0005\u0004\"AY2\u000e\u0003\u0001J!\u0001\u001a\u0011\u0003\u00111Kgn\u001b+za\u0016\fQa[5oI\u0002\na\u0001P5oSRtD#\u00025jU.d\u0007C\u00012\u0001\u0011\u0015y\u0014\u00021\u0001B\u0011\u0015)\u0015\u00021\u0001H\u0011\u0015I\u0016\u00021\u0001\\\u0011\u0015y\u0016\u00021\u0001b\u0003\u0011\u0019w\u000e]=\u0015\u000b!|\u0007/\u001d:\t\u000f}R\u0001\u0013!a\u0001\u0003\"9QI\u0003I\u0001\u0002\u00049\u0005bB-\u000b!\u0003\u0005\ra\u0017\u0005\b?*\u0001\n\u00111\u0001b\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012!\u001e\u0016\u0003\u0003Z\\\u0013a\u001e\t\u0003qvl\u0011!\u001f\u0006\u0003un\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005qd\u0013AC1o]>$\u0018\r^5p]&\u0011a0\u001f\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0003\u0007Q#a\u0012<\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011\u0011\u0011\u0002\u0016\u00037Z\fabY8qs\u0012\"WMZ1vYR$C'\u0006\u0002\u0002\u0010)\u0012\u0011M^\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005U\u0001\u0003BA\f\u0003Ci!!!\u0007\u000b\t\u0005m\u0011QD\u0001\u0005Y\u0006twM\u0003\u0002\u0002 \u0005!!.\u0019<b\u0013\ri\u0016\u0011D\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\tI#a\f\u0011\u0007-\nY#C\u0002\u0002.1\u00121!\u00118z\u0011!\t\t$EA\u0001\u0002\u0004\t\u0015a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u00028A1\u0011\u0011HA \u0003Si!!a\u000f\u000b\u0007\u0005uB&\u0001\u0006d_2dWm\u0019;j_:LA!!\u0011\u0002<\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\t9%!\u0014\u0011\u0007-\nI%C\u0002\u0002L1\u0012qAQ8pY\u0016\fg\u000eC\u0005\u00022M\t\t\u00111\u0001\u0002*\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\t)\"a\u0015\t\u0011\u0005EB#!AA\u0002\u0005\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u0003\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u0016\u00051Q-];bYN$B!a\u0012\u0002b!I\u0011\u0011G\f\u0002\u0002\u0003\u0007\u0011\u0011F\u0001\u0014\u0003B\u0004XM\u001c3MS:\\w\n]3sCRLwN\u001c\t\u0003Ef\u0019R!GA5\u0003k\u0002\u0012\"a\u001b\u0002r\u0005;5,\u00195\u000e\u0005\u00055$bAA8Y\u00059!/\u001e8uS6,\u0017\u0002BA:\u0003[\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c85!\u0011\t9(! \u000e\u0005\u0005e$\u0002BA>\u0003;\t!![8\n\u0007u\nI\b\u0006\u0002\u0002f\u0005)\u0011\r\u001d9msRI\u0001.!\"\u0002\b\u0006%\u00151\u0012\u0005\u0006\u007fq\u0001\r!\u0011\u0005\u0006\u000br\u0001\ra\u0012\u0005\u00063r\u0001\ra\u0017\u0005\u0006?r\u0001\r!Y\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t\t*!(\u0011\u000b-\n\u0019*a&\n\u0007\u0005UEF\u0001\u0004PaRLwN\u001c\t\bW\u0005e\u0015iR.b\u0013\r\tY\n\f\u0002\u0007)V\u0004H.\u001a\u001b\t\u0011\u0005}U$!AA\u0002!\f1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t)\u000b\u0005\u0003\u0002\u0018\u0005\u001d\u0016\u0002BAU\u00033\u0011aa\u00142kK\u000e$\b")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendLinkOperation.class */
public class AppendLinkOperation implements Product, Serializable {
    private final int pageNumber;
    private final Set<RectangularBox> boundingBoxes;
    private final String href;
    private final LinkType kind;

    public static Option<Tuple4<Object, Set<RectangularBox>, String, LinkType>> unapply(final AppendLinkOperation x$0) {
        return AppendLinkOperation$.MODULE$.unapply(x$0);
    }

    public static AppendLinkOperation apply(final int pageNumber, final Set<RectangularBox> boundingBoxes, final String href, final LinkType kind) {
        return AppendLinkOperation$.MODULE$.apply(pageNumber, boundingBoxes, href, kind);
    }

    public static Function1<Tuple4<Object, Set<RectangularBox>, String, LinkType>, AppendLinkOperation> tupled() {
        return AppendLinkOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Set<RectangularBox>, Function1<String, Function1<LinkType, AppendLinkOperation>>>> curried() {
        return AppendLinkOperation$.MODULE$.curried();
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

    public String href() {
        return this.href;
    }

    public LinkType kind() {
        return this.kind;
    }

    public AppendLinkOperation copy(final int pageNumber, final Set<RectangularBox> boundingBoxes, final String href, final LinkType kind) {
        return new AppendLinkOperation(pageNumber, boundingBoxes, href, kind);
    }

    public int copy$default$1() {
        return pageNumber();
    }

    public Set<RectangularBox> copy$default$2() {
        return boundingBoxes();
    }

    public String copy$default$3() {
        return href();
    }

    public LinkType copy$default$4() {
        return kind();
    }

    public String productPrefix() {
        return "AppendLinkOperation";
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
                return href();
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
        return x$1 instanceof AppendLinkOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "pageNumber";
            case 1:
                return "boundingBoxes";
            case 2:
                return "href";
            case 3:
                return "kind";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), pageNumber()), Statics.anyHash(boundingBoxes())), Statics.anyHash(href())), Statics.anyHash(kind())), 4);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof AppendLinkOperation) {
                AppendLinkOperation appendLinkOperation = (AppendLinkOperation) x$1;
                if (pageNumber() == appendLinkOperation.pageNumber()) {
                    Set<RectangularBox> setBoundingBoxes = boundingBoxes();
                    Set<RectangularBox> setBoundingBoxes2 = appendLinkOperation.boundingBoxes();
                    if (setBoundingBoxes != null ? setBoundingBoxes.equals(setBoundingBoxes2) : setBoundingBoxes2 == null) {
                        String strHref = href();
                        String strHref2 = appendLinkOperation.href();
                        if (strHref != null ? strHref.equals(strHref2) : strHref2 == null) {
                            LinkType linkTypeKind = kind();
                            LinkType linkTypeKind2 = appendLinkOperation.kind();
                            if (linkTypeKind != null ? linkTypeKind.equals(linkTypeKind2) : linkTypeKind2 == null) {
                                if (appendLinkOperation.canEqual(this)) {
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

    public AppendLinkOperation(final int pageNumber, final Set<RectangularBox> boundingBoxes, final String href, final LinkType kind) {
        this.pageNumber = pageNumber;
        this.boundingBoxes = boundingBoxes;
        this.href = href;
        this.kind = kind;
        Product.$init$(this);
    }
}
