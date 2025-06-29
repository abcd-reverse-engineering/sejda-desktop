package code.sejda.tasks.common.image;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: PageImageRedactor.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\rc\u0001\u0002\f\u0018\u0001\nB\u0001\u0002\u000f\u0001\u0003\u0016\u0004%\t!\u000f\u0005\t\u0003\u0002\u0011\t\u0012)A\u0005u!)!\t\u0001C\u0001\u0007\"9a\tAA\u0001\n\u00039\u0005bB%\u0001#\u0003%\tA\u0013\u0005\b+\u0002\t\t\u0011\"\u0011W\u0011\u001dy\u0006!!A\u0005\u0002\u0001Dq\u0001\u001a\u0001\u0002\u0002\u0013\u0005Q\rC\u0004l\u0001\u0005\u0005I\u0011\t7\t\u000fM\u0004\u0011\u0011!C\u0001i\"9\u0011\u0010AA\u0001\n\u0003R\bb\u0002?\u0001\u0003\u0003%\t% \u0005\b}\u0002\t\t\u0011\"\u0011��\u0011%\t\t\u0001AA\u0001\n\u0003\n\u0019aB\u0005\u0002\b]\t\t\u0011#\u0001\u0002\n\u0019AacFA\u0001\u0012\u0003\tY\u0001\u0003\u0004C!\u0011\u0005\u00111\u0005\u0005\b}B\t\t\u0011\"\u0012��\u0011%\t)\u0003EA\u0001\n\u0003\u000b9\u0003C\u0005\u0002,A\t\t\u0011\"!\u0002.!I\u0011\u0011\b\t\u0002\u0002\u0013%\u00111\b\u0002\u0016\u00136\fw-\u001a*fI\u0006\u001cG/[8o%\u0016\u001cX\u000f\u001c;t\u0015\tA\u0012$A\u0003j[\u0006<WM\u0003\u0002\u001b7\u000511m\\7n_:T!\u0001H\u000f\u0002\u000bQ\f7o[:\u000b\u0005yy\u0012!B:fU\u0012\f'\"\u0001\u0011\u0002\t\r|G-Z\u0002\u0001'\u0011\u00011%\u000b\u0017\u0011\u0005\u0011:S\"A\u0013\u000b\u0003\u0019\nQa]2bY\u0006L!\u0001K\u0013\u0003\r\u0005s\u0017PU3g!\t!#&\u0003\u0002,K\t9\u0001K]8ek\u000e$\bCA\u00176\u001d\tq3G\u0004\u00020e5\t\u0001G\u0003\u00022C\u00051AH]8pizJ\u0011AJ\u0005\u0003i\u0015\nq\u0001]1dW\u0006<W-\u0003\u00027o\ta1+\u001a:jC2L'0\u00192mK*\u0011A'J\u0001\t]>$hi\\;oIV\t!\bE\u0002.wuJ!\u0001P\u001c\u0003\u0007M+\u0017\u000f\u0005\u0002?\u007f5\tq#\u0003\u0002A/\tq\u0011*\\1hKJ+G-Y2uS>t\u0017!\u00038pi\u001a{WO\u001c3!\u0003\u0019a\u0014N\\5u}Q\u0011A)\u0012\t\u0003}\u0001AQ\u0001O\u0002A\u0002i\nAaY8qsR\u0011A\t\u0013\u0005\bq\u0011\u0001\n\u00111\u0001;\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012a\u0013\u0016\u0003u1[\u0013!\u0014\t\u0003\u001dNk\u0011a\u0014\u0006\u0003!F\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005I+\u0013AC1o]>$\u0018\r^5p]&\u0011Ak\u0014\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001X!\tAV,D\u0001Z\u0015\tQ6,\u0001\u0003mC:<'\"\u0001/\u0002\t)\fg/Y\u0005\u0003=f\u0013aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLX#A1\u0011\u0005\u0011\u0012\u0017BA2&\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\t1\u0017\u000e\u0005\u0002%O&\u0011\u0001.\n\u0002\u0004\u0003:L\bb\u00026\t\u0003\u0003\u0005\r!Y\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u00035\u00042A\\9g\u001b\u0005y'B\u00019&\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003e>\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0011Q\u000f\u001f\t\u0003IYL!a^\u0013\u0003\u000f\t{w\u000e\\3b]\"9!NCA\u0001\u0002\u00041\u0017A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$\"aV>\t\u000f)\\\u0011\u0011!a\u0001C\u0006A\u0001.Y:i\u0007>$W\rF\u0001b\u0003!!xn\u0015;sS:<G#A,\u0002\r\u0015\fX/\u00197t)\r)\u0018Q\u0001\u0005\bU:\t\t\u00111\u0001g\u0003UIU.Y4f%\u0016$\u0017m\u0019;j_:\u0014Vm];miN\u0004\"A\u0010\t\u0014\u000bA\ti!!\u0007\u0011\r\u0005=\u0011Q\u0003\u001eE\u001b\t\t\tBC\u0002\u0002\u0014\u0015\nqA];oi&lW-\u0003\u0003\u0002\u0018\u0005E!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!\u00111DA\u0011\u001b\t\tiBC\u0002\u0002 m\u000b!![8\n\u0007Y\ni\u0002\u0006\u0002\u0002\n\u0005)\u0011\r\u001d9msR\u0019A)!\u000b\t\u000ba\u001a\u0002\u0019\u0001\u001e\u0002\u000fUt\u0017\r\u001d9msR!\u0011qFA\u001b!\u0011!\u0013\u0011\u0007\u001e\n\u0007\u0005MRE\u0001\u0004PaRLwN\u001c\u0005\t\u0003o!\u0012\u0011!a\u0001\t\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005u\u0002c\u0001-\u0002@%\u0019\u0011\u0011I-\u0003\r=\u0013'.Z2u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/image/ImageRedactionResults.class */
public class ImageRedactionResults implements Product, Serializable {
    private final Seq<ImageRedaction> notFound;

    public static Option<Seq<ImageRedaction>> unapply(final ImageRedactionResults x$0) {
        return ImageRedactionResults$.MODULE$.unapply(x$0);
    }

    public static ImageRedactionResults apply(final Seq<ImageRedaction> notFound) {
        return ImageRedactionResults$.MODULE$.apply(notFound);
    }

    public static <A> Function1<Seq<ImageRedaction>, A> andThen(final Function1<ImageRedactionResults, A> g) {
        return ImageRedactionResults$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, ImageRedactionResults> compose(final Function1<A, Seq<ImageRedaction>> g) {
        return ImageRedactionResults$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Seq<ImageRedaction> notFound() {
        return this.notFound;
    }

    public ImageRedactionResults copy(final Seq<ImageRedaction> notFound) {
        return new ImageRedactionResults(notFound);
    }

    public Seq<ImageRedaction> copy$default$1() {
        return notFound();
    }

    public String productPrefix() {
        return "ImageRedactionResults";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return notFound();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof ImageRedactionResults;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "notFound";
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
            if (x$1 instanceof ImageRedactionResults) {
                ImageRedactionResults imageRedactionResults = (ImageRedactionResults) x$1;
                Seq<ImageRedaction> seqNotFound = notFound();
                Seq<ImageRedaction> seqNotFound2 = imageRedactionResults.notFound();
                if (seqNotFound != null ? seqNotFound.equals(seqNotFound2) : seqNotFound2 == null) {
                    if (imageRedactionResults.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public ImageRedactionResults(final Seq<ImageRedaction> notFound) {
        this.notFound = notFound;
        Product.$init$(this);
    }
}
