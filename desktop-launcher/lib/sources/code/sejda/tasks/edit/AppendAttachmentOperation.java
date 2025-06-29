package code.sejda.tasks.edit;

import java.io.Serializable;
import org.sejda.model.RectangularBox;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u00055e\u0001\u0002\u000f\u001e\u0001\u001aB\u0001\u0002\u0010\u0001\u0003\u0016\u0004%\t!\u0010\u0005\t\u0003\u0002\u0011\t\u0012)A\u0005}!A!\t\u0001BK\u0002\u0013\u00051\t\u0003\u0005V\u0001\tE\t\u0015!\u0003E\u0011!1\u0006A!f\u0001\n\u00039\u0006\u0002C.\u0001\u0005#\u0005\u000b\u0011\u0002-\t\u000bq\u0003A\u0011A/\t\u000f\r\u0004\u0011\u0011!C\u0001I\"9\u0001\u000eAI\u0001\n\u0003I\u0007b\u0002;\u0001#\u0003%\t!\u001e\u0005\bo\u0002\t\n\u0011\"\u0001y\u0011\u001dQ\b!!A\u0005BmD\u0001\"a\u0002\u0001\u0003\u0003%\t!\u0010\u0005\n\u0003\u0013\u0001\u0011\u0011!C\u0001\u0003\u0017A\u0011\"a\u0006\u0001\u0003\u0003%\t%!\u0007\t\u0013\u0005\u001d\u0002!!A\u0005\u0002\u0005%\u0002\"CA\u001a\u0001\u0005\u0005I\u0011IA\u001b\u0011%\tI\u0004AA\u0001\n\u0003\nY\u0004C\u0005\u0002>\u0001\t\t\u0011\"\u0011\u0002@!I\u0011\u0011\t\u0001\u0002\u0002\u0013\u0005\u00131I\u0004\n\u0003\u000fj\u0012\u0011!E\u0001\u0003\u00132\u0001\u0002H\u000f\u0002\u0002#\u0005\u00111\n\u0005\u00079Z!\t!a\u0019\t\u0013\u0005ub#!A\u0005F\u0005}\u0002\"CA3-\u0005\u0005I\u0011QA4\u0011%\tyGFA\u0001\n\u0003\u000b\t\bC\u0005\u0002\u0004Z\t\t\u0011\"\u0003\u0002\u0006\nI\u0012\t\u001d9f]\u0012\fE\u000f^1dQ6,g\u000e^(qKJ\fG/[8o\u0015\tqr$\u0001\u0003fI&$(B\u0001\u0011\"\u0003\u0015!\u0018m]6t\u0015\t\u00113%A\u0003tK*$\u0017MC\u0001%\u0003\u0011\u0019w\u000eZ3\u0004\u0001M!\u0001aJ\u00171!\tA3&D\u0001*\u0015\u0005Q\u0013!B:dC2\f\u0017B\u0001\u0017*\u0005\u0019\te.\u001f*fMB\u0011\u0001FL\u0005\u0003_%\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u00022s9\u0011!g\u000e\b\u0003gYj\u0011\u0001\u000e\u0006\u0003k\u0015\na\u0001\u0010:p_Rt\u0014\"\u0001\u0016\n\u0005aJ\u0013a\u00029bG.\fw-Z\u0005\u0003um\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!\u0001O\u0015\u0002\u0015A\fw-\u001a(v[\n,'/F\u0001?!\tAs(\u0003\u0002AS\t\u0019\u0011J\u001c;\u0002\u0017A\fw-\u001a(v[\n,'\u000fI\u0001\u000eE>,h\u000eZ5oO\n{\u00070Z:\u0016\u0003\u0011\u00032!R%M\u001d\t1u\t\u0005\u00024S%\u0011\u0001*K\u0001\u0007!J,G-\u001a4\n\u0005)[%aA*fi*\u0011\u0001*\u000b\t\u0003\u001bNk\u0011A\u0014\u0006\u0003\u001fB\u000bQ!\\8eK2T!AI)\u000b\u0003I\u000b1a\u001c:h\u0013\t!fJ\u0001\bSK\u000e$\u0018M\\4vY\u0006\u0014(i\u001c=\u0002\u001d\t|WO\u001c3j]\u001e\u0014u\u000e_3tA\u0005Aa-\u001b7f]\u0006lW-F\u0001Y!\t)\u0015,\u0003\u0002[\u0017\n11\u000b\u001e:j]\u001e\f\u0011BZ5mK:\fW.\u001a\u0011\u0002\rqJg.\u001b;?)\u0011q\u0006-\u00192\u0011\u0005}\u0003Q\"A\u000f\t\u000bq:\u0001\u0019\u0001 \t\u000b\t;\u0001\u0019\u0001#\t\u000bY;\u0001\u0019\u0001-\u0002\t\r|\u0007/\u001f\u000b\u0005=\u00164w\rC\u0004=\u0011A\u0005\t\u0019\u0001 \t\u000f\tC\u0001\u0013!a\u0001\t\"9a\u000b\u0003I\u0001\u0002\u0004A\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0002U*\u0012ah[\u0016\u0002YB\u0011QN]\u0007\u0002]*\u0011q\u000e]\u0001\nk:\u001c\u0007.Z2lK\u0012T!!]\u0015\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002t]\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\taO\u000b\u0002EW\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aT#A=+\u0005a[\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001}!\ri\u0018QA\u0007\u0002}*\u0019q0!\u0001\u0002\t1\fgn\u001a\u0006\u0003\u0003\u0007\tAA[1wC&\u0011!L`\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\ti!a\u0005\u0011\u0007!\ny!C\u0002\u0002\u0012%\u00121!\u00118z\u0011!\t)BDA\u0001\u0002\u0004q\u0014a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\u001cA1\u0011QDA\u0012\u0003\u001bi!!a\b\u000b\u0007\u0005\u0005\u0012&\u0001\u0006d_2dWm\u0019;j_:LA!!\n\u0002 \tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\tY#!\r\u0011\u0007!\ni#C\u0002\u00020%\u0012qAQ8pY\u0016\fg\u000eC\u0005\u0002\u0016A\t\t\u00111\u0001\u0002\u000e\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\ra\u0018q\u0007\u0005\t\u0003+\t\u0012\u0011!a\u0001}\u0005A\u0001.Y:i\u0007>$W\rF\u0001?\u0003!!xn\u0015;sS:<G#\u0001?\u0002\r\u0015\fX/\u00197t)\u0011\tY#!\u0012\t\u0013\u0005UA#!AA\u0002\u00055\u0011!G!qa\u0016tG-\u0011;uC\u000eDW.\u001a8u\u001fB,'/\u0019;j_:\u0004\"a\u0018\f\u0014\u000bY\ti%!\u0017\u0011\u0011\u0005=\u0013Q\u000b E1zk!!!\u0015\u000b\u0007\u0005M\u0013&A\u0004sk:$\u0018.\\3\n\t\u0005]\u0013\u0011\u000b\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\u001c\u0004\u0003BA.\u0003Cj!!!\u0018\u000b\t\u0005}\u0013\u0011A\u0001\u0003S>L1AOA/)\t\tI%A\u0003baBd\u0017\u0010F\u0004_\u0003S\nY'!\u001c\t\u000bqJ\u0002\u0019\u0001 \t\u000b\tK\u0002\u0019\u0001#\t\u000bYK\u0002\u0019\u0001-\u0002\u000fUt\u0017\r\u001d9msR!\u00111OA@!\u0015A\u0013QOA=\u0013\r\t9(\u000b\u0002\u0007\u001fB$\u0018n\u001c8\u0011\r!\nYH\u0010#Y\u0013\r\ti(\u000b\u0002\u0007)V\u0004H.Z\u001a\t\u0011\u0005\u0005%$!AA\u0002y\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t9\tE\u0002~\u0003\u0013K1!a#\u007f\u0005\u0019y%M[3di\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendAttachmentOperation.class */
public class AppendAttachmentOperation implements Product, Serializable {
    private final int pageNumber;
    private final Set<RectangularBox> boundingBoxes;
    private final String filename;

    public static Option<Tuple3<Object, Set<RectangularBox>, String>> unapply(final AppendAttachmentOperation x$0) {
        return AppendAttachmentOperation$.MODULE$.unapply(x$0);
    }

    public static AppendAttachmentOperation apply(final int pageNumber, final Set<RectangularBox> boundingBoxes, final String filename) {
        return AppendAttachmentOperation$.MODULE$.apply(pageNumber, boundingBoxes, filename);
    }

    public static Function1<Tuple3<Object, Set<RectangularBox>, String>, AppendAttachmentOperation> tupled() {
        return AppendAttachmentOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Set<RectangularBox>, Function1<String, AppendAttachmentOperation>>> curried() {
        return AppendAttachmentOperation$.MODULE$.curried();
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

    public String filename() {
        return this.filename;
    }

    public AppendAttachmentOperation copy(final int pageNumber, final Set<RectangularBox> boundingBoxes, final String filename) {
        return new AppendAttachmentOperation(pageNumber, boundingBoxes, filename);
    }

    public int copy$default$1() {
        return pageNumber();
    }

    public Set<RectangularBox> copy$default$2() {
        return boundingBoxes();
    }

    public String copy$default$3() {
        return filename();
    }

    public String productPrefix() {
        return "AppendAttachmentOperation";
    }

    public int productArity() {
        return 3;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 1:
                return boundingBoxes();
            case 2:
                return filename();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof AppendAttachmentOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "pageNumber";
            case 1:
                return "boundingBoxes";
            case 2:
                return "filename";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), pageNumber()), Statics.anyHash(boundingBoxes())), Statics.anyHash(filename())), 3);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof AppendAttachmentOperation) {
                AppendAttachmentOperation appendAttachmentOperation = (AppendAttachmentOperation) x$1;
                if (pageNumber() == appendAttachmentOperation.pageNumber()) {
                    Set<RectangularBox> setBoundingBoxes = boundingBoxes();
                    Set<RectangularBox> setBoundingBoxes2 = appendAttachmentOperation.boundingBoxes();
                    if (setBoundingBoxes != null ? setBoundingBoxes.equals(setBoundingBoxes2) : setBoundingBoxes2 == null) {
                        String strFilename = filename();
                        String strFilename2 = appendAttachmentOperation.filename();
                        if (strFilename != null ? strFilename.equals(strFilename2) : strFilename2 == null) {
                            if (appendAttachmentOperation.canEqual(this)) {
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public AppendAttachmentOperation(final int pageNumber, final Set<RectangularBox> boundingBoxes, final String filename) {
        this.pageNumber = pageNumber;
        this.boundingBoxes = boundingBoxes;
        this.filename = filename;
        Product.$init$(this);
    }
}
