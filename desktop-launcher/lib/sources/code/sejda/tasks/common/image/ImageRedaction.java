package code.sejda.tasks.common.image;

import java.io.Serializable;
import org.sejda.sambox.pdmodel.PDPage;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: PageImageRedactor.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005=e\u0001\u0002\u000f\u001e\u0001\"B\u0001B\u0010\u0001\u0003\u0016\u0004%\ta\u0010\u0005\t\u0017\u0002\u0011\t\u0012)A\u0005\u0001\"AA\n\u0001BK\u0002\u0013\u0005Q\n\u0003\u0005R\u0001\tE\t\u0015!\u0003O\u0011!\u0011\u0006A!f\u0001\n\u0003\u0019\u0006\u0002\u0003/\u0001\u0005#\u0005\u000b\u0011\u0002+\t\u000bu\u0003A\u0011\u00010\t\u000f\u0011\u0004\u0011\u0011!C\u0001K\"9\u0011\u000eAI\u0001\n\u0003Q\u0007bB;\u0001#\u0003%\tA\u001e\u0005\bq\u0002\t\n\u0011\"\u0001z\u0011\u001dY\b!!A\u0005BqD\u0001\"!\u0003\u0001\u0003\u0003%\t!\u0014\u0005\n\u0003\u0017\u0001\u0011\u0011!C\u0001\u0003\u001bA\u0011\"!\u0007\u0001\u0003\u0003%\t%a\u0007\t\u0013\u0005%\u0002!!A\u0005\u0002\u0005-\u0002\"CA\u001b\u0001\u0005\u0005I\u0011IA\u001c\u0011%\tY\u0004AA\u0001\n\u0003\ni\u0004C\u0005\u0002@\u0001\t\t\u0011\"\u0011\u0002B!I\u00111\t\u0001\u0002\u0002\u0013\u0005\u0013QI\u0004\n\u0003\u0013j\u0012\u0011!E\u0001\u0003\u00172\u0001\u0002H\u000f\u0002\u0002#\u0005\u0011Q\n\u0005\u0007;Z!\t!!\u001a\t\u0013\u0005}b#!A\u0005F\u0005\u0005\u0003\"CA4-\u0005\u0005I\u0011QA5\u0011%\t\tHFA\u0001\n\u0003\u000b\u0019\bC\u0005\u0002\u0006Z\t\t\u0011\"\u0003\u0002\b\nq\u0011*\\1hKJ+G-Y2uS>t'B\u0001\u0010 \u0003\u0015IW.Y4f\u0015\t\u0001\u0013%\u0001\u0004d_6lwN\u001c\u0006\u0003E\r\nQ\u0001^1tWNT!\u0001J\u0013\u0002\u000bM,'\u000eZ1\u000b\u0003\u0019\nAaY8eK\u000e\u00011\u0003\u0002\u0001*_I\u0002\"AK\u0017\u000e\u0003-R\u0011\u0001L\u0001\u0006g\u000e\fG.Y\u0005\u0003]-\u0012a!\u00118z%\u00164\u0007C\u0001\u00161\u0013\t\t4FA\u0004Qe>$Wo\u0019;\u0011\u0005MZdB\u0001\u001b:\u001d\t)\u0004(D\u00017\u0015\t9t%\u0001\u0004=e>|GOP\u0005\u0002Y%\u0011!hK\u0001\ba\u0006\u001c7.Y4f\u0013\taTH\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002;W\u0005!\u0001/Y4f+\u0005\u0001\u0005CA!J\u001b\u0005\u0011%BA\"E\u0003\u001d\u0001H-\\8eK2T!!\u0012$\u0002\rM\fWNY8y\u0015\t!sIC\u0001I\u0003\ry'oZ\u0005\u0003\u0015\n\u0013a\u0001\u0015#QC\u001e,\u0017!\u00029bO\u0016\u0004\u0013a\u00029bO\u0016tU/\\\u000b\u0002\u001dB\u0011!fT\u0005\u0003!.\u00121!\u00138u\u0003!\u0001\u0018mZ3Ok6\u0004\u0013!B8cU&#W#\u0001+\u0011\u0005UKfB\u0001,X!\t)4&\u0003\u0002YW\u00051\u0001K]3eK\u001aL!AW.\u0003\rM#(/\u001b8h\u0015\tA6&\u0001\u0004pE*LE\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\t}\u000b'm\u0019\t\u0003A\u0002i\u0011!\b\u0005\u0006}\u001d\u0001\r\u0001\u0011\u0005\u0006\u0019\u001e\u0001\rA\u0014\u0005\u0006%\u001e\u0001\r\u0001V\u0001\u0005G>\u0004\u0018\u0010\u0006\u0003`M\u001eD\u0007b\u0002 \t!\u0003\u0005\r\u0001\u0011\u0005\b\u0019\"\u0001\n\u00111\u0001O\u0011\u001d\u0011\u0006\u0002%AA\u0002Q\u000babY8qs\u0012\"WMZ1vYR$\u0013'F\u0001lU\t\u0001EnK\u0001n!\tq7/D\u0001p\u0015\t\u0001\u0018/A\u0005v]\u000eDWmY6fI*\u0011!oK\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001;p\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\u00059(F\u0001(m\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\u0012A\u001f\u0016\u0003)2\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A?\u0011\u0007y\f9!D\u0001��\u0015\u0011\t\t!a\u0001\u0002\t1\fgn\u001a\u0006\u0003\u0003\u000b\tAA[1wC&\u0011!l`\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\ty!!\u0006\u0011\u0007)\n\t\"C\u0002\u0002\u0014-\u00121!\u00118z\u0011!\t9BDA\u0001\u0002\u0004q\u0015a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\u001eA1\u0011qDA\u0013\u0003\u001fi!!!\t\u000b\u0007\u0005\r2&\u0001\u0006d_2dWm\u0019;j_:LA!a\n\u0002\"\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\ti#a\r\u0011\u0007)\ny#C\u0002\u00022-\u0012qAQ8pY\u0016\fg\u000eC\u0005\u0002\u0018A\t\t\u00111\u0001\u0002\u0010\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\ri\u0018\u0011\b\u0005\t\u0003/\t\u0012\u0011!a\u0001\u001d\u0006A\u0001.Y:i\u0007>$W\rF\u0001O\u0003!!xn\u0015;sS:<G#A?\u0002\r\u0015\fX/\u00197t)\u0011\ti#a\u0012\t\u0013\u0005]A#!AA\u0002\u0005=\u0011AD%nC\u001e,'+\u001a3bGRLwN\u001c\t\u0003AZ\u0019RAFA(\u00037\u0002\u0002\"!\u0015\u0002X\u0001sEkX\u0007\u0003\u0003'R1!!\u0016,\u0003\u001d\u0011XO\u001c;j[\u0016LA!!\u0017\u0002T\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u001a\u0011\t\u0005u\u00131M\u0007\u0003\u0003?RA!!\u0019\u0002\u0004\u0005\u0011\u0011n\\\u0005\u0004y\u0005}CCAA&\u0003\u0015\t\u0007\u000f\u001d7z)\u001dy\u00161NA7\u0003_BQAP\rA\u0002\u0001CQ\u0001T\rA\u00029CQAU\rA\u0002Q\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002v\u0005\u0005\u0005#\u0002\u0016\u0002x\u0005m\u0014bAA=W\t1q\n\u001d;j_:\u0004bAKA?\u0001:#\u0016bAA@W\t1A+\u001e9mKNB\u0001\"a!\u001b\u0003\u0003\u0005\raX\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAE!\rq\u00181R\u0005\u0004\u0003\u001b{(AB(cU\u0016\u001cG\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/image/ImageRedaction.class */
public class ImageRedaction implements Product, Serializable {
    private final PDPage page;
    private final int pageNum;
    private final String objId;

    public static Option<Tuple3<PDPage, Object, String>> unapply(final ImageRedaction x$0) {
        return ImageRedaction$.MODULE$.unapply(x$0);
    }

    public static ImageRedaction apply(final PDPage page, final int pageNum, final String objId) {
        return ImageRedaction$.MODULE$.apply(page, pageNum, objId);
    }

    public static Function1<Tuple3<PDPage, Object, String>, ImageRedaction> tupled() {
        return ImageRedaction$.MODULE$.tupled();
    }

    public static Function1<PDPage, Function1<Object, Function1<String, ImageRedaction>>> curried() {
        return ImageRedaction$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public PDPage page() {
        return this.page;
    }

    public int pageNum() {
        return this.pageNum;
    }

    public String objId() {
        return this.objId;
    }

    public ImageRedaction copy(final PDPage page, final int pageNum, final String objId) {
        return new ImageRedaction(page, pageNum, objId);
    }

    public PDPage copy$default$1() {
        return page();
    }

    public int copy$default$2() {
        return pageNum();
    }

    public String copy$default$3() {
        return objId();
    }

    public String productPrefix() {
        return "ImageRedaction";
    }

    public int productArity() {
        return 3;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return page();
            case 1:
                return BoxesRunTime.boxToInteger(pageNum());
            case 2:
                return objId();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof ImageRedaction;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "page";
            case 1:
                return "pageNum";
            case 2:
                return "objId";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(page())), pageNum()), Statics.anyHash(objId())), 3);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof ImageRedaction) {
                ImageRedaction imageRedaction = (ImageRedaction) x$1;
                if (pageNum() == imageRedaction.pageNum()) {
                    PDPage pDPagePage = page();
                    PDPage pDPagePage2 = imageRedaction.page();
                    if (pDPagePage != null ? pDPagePage.equals(pDPagePage2) : pDPagePage2 == null) {
                        String strObjId = objId();
                        String strObjId2 = imageRedaction.objId();
                        if (strObjId != null ? strObjId.equals(strObjId2) : strObjId2 == null) {
                            if (imageRedaction.canEqual(this)) {
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public ImageRedaction(final PDPage page, final int pageNum, final String objId) {
        this.page = page;
        this.pageNum = pageNum;
        this.objId = objId;
        Product.$init$(this);
    }
}
