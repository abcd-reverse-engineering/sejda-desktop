package code.sejda.tasks.edit;

import java.awt.geom.Point2D;
import java.io.Serializable;
import org.sejda.model.input.Source;
import org.sejda.model.pdf.page.PageRange;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple8;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\tUd\u0001B\u00181\u0001fB\u0001b\u0014\u0001\u0003\u0016\u0004%\t\u0001\u0015\u0005\tC\u0002\u0011\t\u0012)A\u0005#\"A\u0011\u000e\u0001BK\u0002\u0013\u0005!\u000e\u0003\u0005o\u0001\tE\t\u0015!\u0003l\u0011!y\u0007A!f\u0001\n\u0003Q\u0007\u0002\u00039\u0001\u0005#\u0005\u000b\u0011B6\t\u0011E\u0004!Q3A\u0005\u0002ID\u0001\" \u0001\u0003\u0012\u0003\u0006Ia\u001d\u0005\t}\u0002\u0011)\u001a!C\u0001\u007f\"Q\u0011\u0011\u0003\u0001\u0003\u0012\u0003\u0006I!!\u0001\t\u0015\u0005M\u0001A!f\u0001\n\u0003\t)\u0002\u0003\u0006\u0002\u001e\u0001\u0011\t\u0012)A\u0005\u0003/A!\"a\b\u0001\u0005+\u0007I\u0011AA\u0011\u0011)\tI\u0003\u0001B\tB\u0003%\u00111\u0005\u0005\u000b\u0003W\u0001!Q3A\u0005\u0002\u00055\u0002BCA#\u0001\tE\t\u0015!\u0003\u00020!9\u0011q\t\u0001\u0005\u0002\u0005%\u0003\"CA4\u0001\u0005\u0005I\u0011AA5\u0011%\tY\bAI\u0001\n\u0003\ti\bC\u0005\u0002\u001c\u0002\t\n\u0011\"\u0001\u0002\u001e\"I\u0011\u0011\u0015\u0001\u0012\u0002\u0013\u0005\u0011Q\u0014\u0005\n\u0003G\u0003\u0011\u0013!C\u0001\u0003KC\u0011\"!+\u0001#\u0003%\t!a+\t\u0013\u0005=\u0006!%A\u0005\u0002\u0005E\u0006\"CA[\u0001E\u0005I\u0011AA\\\u0011%\tY\fAI\u0001\n\u0003\ti\fC\u0005\u0002B\u0002\t\t\u0011\"\u0011\u0002D\"I\u0011q\u001a\u0001\u0002\u0002\u0013\u0005\u0011Q\u0003\u0005\n\u0003#\u0004\u0011\u0011!C\u0001\u0003'D\u0011\"!7\u0001\u0003\u0003%\t%a7\t\u0013\u0005%\b!!A\u0005\u0002\u0005-\b\"CA{\u0001\u0005\u0005I\u0011IA|\u0011%\tY\u0010AA\u0001\n\u0003\ni\u0010C\u0005\u0002��\u0002\t\t\u0011\"\u0011\u0003\u0002!I!1\u0001\u0001\u0002\u0002\u0013\u0005#QA\u0004\n\u0005\u0013\u0001\u0014\u0011!E\u0001\u0005\u00171\u0001b\f\u0019\u0002\u0002#\u0005!Q\u0002\u0005\b\u0003\u000f*C\u0011\u0001B\u0017\u0011%\ty0JA\u0001\n\u000b\u0012\t\u0001C\u0005\u00030\u0015\n\t\u0011\"!\u00032!I!1J\u0013\u0012\u0002\u0013\u0005\u0011q\u0017\u0005\n\u0005\u001b*\u0013\u0013!C\u0001\u0003{C\u0011Ba\u0014&\u0003\u0003%\tI!\u0015\t\u0013\t\u001dT%%A\u0005\u0002\u0005]\u0006\"\u0003B5KE\u0005I\u0011AA_\u0011%\u0011Y'JA\u0001\n\u0013\u0011iGA\tBI\u0012LU.Y4f\u001fB,'/\u0019;j_:T!!\r\u001a\u0002\t\u0015$\u0017\u000e\u001e\u0006\u0003gQ\nQ\u0001^1tWNT!!\u000e\u001c\u0002\u000bM,'\u000eZ1\u000b\u0003]\nAaY8eK\u000e\u00011\u0003\u0002\u0001;\u0001\u000e\u0003\"a\u000f \u000e\u0003qR\u0011!P\u0001\u0006g\u000e\fG.Y\u0005\u0003\u007fq\u0012a!\u00118z%\u00164\u0007CA\u001eB\u0013\t\u0011EHA\u0004Qe>$Wo\u0019;\u0011\u0005\u0011ceBA#K\u001d\t1\u0015*D\u0001H\u0015\tA\u0005(\u0001\u0004=e>|GOP\u0005\u0002{%\u00111\nP\u0001\ba\u0006\u001c7.Y4f\u0013\tieJ\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002Ly\u0005Y\u0011.\\1hKN{WO]2f+\u0005\t\u0006G\u0001*`!\r\u00196,X\u0007\u0002)*\u0011QKV\u0001\u0006S:\u0004X\u000f\u001e\u0006\u0003/b\u000bQ!\\8eK2T!!N-\u000b\u0003i\u000b1a\u001c:h\u0013\taFK\u0001\u0004T_V\u00148-\u001a\t\u0003=~c\u0001\u0001B\u0005a\u0005\u0005\u0005\t\u0011!B\u0001E\n\u0019q\fJ\u0019\u0002\u0019%l\u0017mZ3T_V\u00148-\u001a\u0011\u0012\u0005\r4\u0007CA\u001ee\u0013\t)GHA\u0004O_RD\u0017N\\4\u0011\u0005m:\u0017B\u00015=\u0005\r\te._\u0001\u0006o&$G\u000f[\u000b\u0002WB\u00111\b\\\u0005\u0003[r\u0012QA\u00127pCR\faa^5ei\"\u0004\u0013A\u00025fS\u001eDG/A\u0004iK&<\u0007\u000e\u001e\u0011\u0002\u0011A|7/\u001b;j_:,\u0012a\u001d\t\u0003inl\u0011!\u001e\u0006\u0003m^\fAaZ3p[*\u0011\u00010_\u0001\u0004C^$(\"\u0001>\u0002\t)\fg/Y\u0005\u0003yV\u0014q\u0001U8j]R\u0014D)A\u0005q_NLG/[8oA\u0005I\u0001/Y4f%\u0006tw-Z\u000b\u0003\u0003\u0003\u0001B!a\u0001\u0002\u000e5\u0011\u0011Q\u0001\u0006\u0005\u0003\u000f\tI!\u0001\u0003qC\u001e,'bAA\u0006-\u0006\u0019\u0001\u000f\u001a4\n\t\u0005=\u0011Q\u0001\u0002\n!\u0006<WMU1oO\u0016\f!\u0002]1hKJ\u000bgnZ3!\u0003!\u0011x\u000e^1uS>tWCAA\f!\rY\u0014\u0011D\u0005\u0004\u00037a$aA%oi\u0006I!o\u001c;bi&|g\u000eI\u0001\fKb\u001cW\r\u001d;QC\u001e,7/\u0006\u0002\u0002$A)A)!\n\u0002\u0018%\u0019\u0011q\u0005(\u0003\u0007M+\u0017/\u0001\u0007fq\u000e,\u0007\u000f\u001e)bO\u0016\u001c\b%\u0001\ntS\u001et\u0017\r^;sK\u001aKW\r\u001c3OC6,WCAA\u0018!\u0015Y\u0014\u0011GA\u001b\u0013\r\t\u0019\u0004\u0010\u0002\u0007\u001fB$\u0018n\u001c8\u0011\t\u0005]\u0012q\b\b\u0005\u0003s\tY\u0004\u0005\u0002Gy%\u0019\u0011Q\b\u001f\u0002\rA\u0013X\rZ3g\u0013\u0011\t\t%a\u0011\u0003\rM#(/\u001b8h\u0015\r\ti\u0004P\u0001\u0014g&<g.\u0019;ve\u00164\u0015.\u001a7e\u001d\u0006lW\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015%\u0005-\u0013qJA-\u00037\ni&a\u0018\u0002b\u0005\r\u0014Q\r\t\u0004\u0003\u001b\u0002Q\"\u0001\u0019\t\r=\u000b\u0002\u0019AA)a\u0011\t\u0019&a\u0016\u0011\tM[\u0016Q\u000b\t\u0004=\u0006]CA\u00031\u0002P\u0005\u0005\t\u0011!B\u0001E\")\u0011.\u0005a\u0001W\")q.\u0005a\u0001W\")\u0011/\u0005a\u0001g\"1a0\u0005a\u0001\u0003\u0003Aq!a\u0005\u0012\u0001\u0004\t9\u0002C\u0005\u0002 E\u0001\n\u00111\u0001\u0002$!I\u00111F\t\u0011\u0002\u0003\u0007\u0011qF\u0001\u0005G>\u0004\u0018\u0010\u0006\n\u0002L\u0005-\u0014QNA8\u0003c\n\u0019(!\u001e\u0002x\u0005e\u0004\u0002C(\u0013!\u0003\u0005\r!!\u0015\t\u000f%\u0014\u0002\u0013!a\u0001W\"9qN\u0005I\u0001\u0002\u0004Y\u0007bB9\u0013!\u0003\u0005\ra\u001d\u0005\t}J\u0001\n\u00111\u0001\u0002\u0002!I\u00111\u0003\n\u0011\u0002\u0003\u0007\u0011q\u0003\u0005\n\u0003?\u0011\u0002\u0013!a\u0001\u0003GA\u0011\"a\u000b\u0013!\u0003\u0005\r!a\f\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011q\u0010\u0019\u0005\u0003\u0003\u000b9I\u000b\u0003\u0002\u0004\u0006%\u0005\u0003B*\\\u0003\u000b\u00032AXAD\t%\u00017#!A\u0001\u0002\u000b\u0005!m\u000b\u0002\u0002\fB!\u0011QRAL\u001b\t\tyI\u0003\u0003\u0002\u0012\u0006M\u0015!C;oG\",7m[3e\u0015\r\t)\nP\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BAM\u0003\u001f\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"!a(+\u0007-\fI)\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0011\u0011q\u0015\u0016\u0004g\u0006%\u0015AD2paf$C-\u001a4bk2$H%N\u000b\u0003\u0003[SC!!\u0001\u0002\n\u0006q1m\u001c9zI\u0011,g-Y;mi\u00122TCAAZU\u0011\t9\"!#\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%oU\u0011\u0011\u0011\u0018\u0016\u0005\u0003G\tI)\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001d\u0016\u0005\u0005}&\u0006BA\u0018\u0003\u0013\u000bQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAAc!\u0011\t9-!4\u000e\u0005\u0005%'bAAfs\u0006!A.\u00198h\u0013\u0011\t\t%!3\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019a-!6\t\u0013\u0005]W$!AA\u0002\u0005]\u0011a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002^B)\u0011q\\AsM6\u0011\u0011\u0011\u001d\u0006\u0004\u0003Gd\u0014AC2pY2,7\r^5p]&!\u0011q]Aq\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u00055\u00181\u001f\t\u0004w\u0005=\u0018bAAyy\t9!i\\8mK\u0006t\u0007\u0002CAl?\u0005\u0005\t\u0019\u00014\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003\u000b\fI\u0010C\u0005\u0002X\u0002\n\t\u00111\u0001\u0002\u0018\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\u0018\u0005AAo\\*ue&tw\r\u0006\u0002\u0002F\u00061Q-];bYN$B!!<\u0003\b!A\u0011q[\u0012\u0002\u0002\u0003\u0007a-A\tBI\u0012LU.Y4f\u001fB,'/\u0019;j_:\u00042!!\u0014&'\u0015)#q\u0002B\u0012!M\u0011\tBa\u0006\u0003\u001c-\\7/!\u0001\u0002\u0018\u0005\r\u0012qFA&\u001b\t\u0011\u0019BC\u0002\u0003\u0016q\nqA];oi&lW-\u0003\u0003\u0003\u001a\tM!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oqA\"!Q\u0004B\u0011!\u0011\u00196La\b\u0011\u0007y\u0013\t\u0003B\u0005aK\u0005\u0005\t\u0011!B\u0001EB!!Q\u0005B\u0016\u001b\t\u00119CC\u0002\u0003*e\f!![8\n\u00075\u00139\u0003\u0006\u0002\u0003\f\u0005)\u0011\r\u001d9msR\u0011\u00121\nB\u001a\u0005{\u0011yD!\u0011\u0003D\t\u0015#q\tB%\u0011\u0019y\u0005\u00061\u0001\u00036A\"!q\u0007B\u001e!\u0011\u00196L!\u000f\u0011\u0007y\u0013Y\u0004\u0002\u0006a\u0005g\t\t\u0011!A\u0003\u0002\tDQ!\u001b\u0015A\u0002-DQa\u001c\u0015A\u0002-DQ!\u001d\u0015A\u0002MDaA \u0015A\u0002\u0005\u0005\u0001bBA\nQ\u0001\u0007\u0011q\u0003\u0005\n\u0003?A\u0003\u0013!a\u0001\u0003GA\u0011\"a\u000b)!\u0003\u0005\r!a\f\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uI]\nq\"\u00199qYf$C-\u001a4bk2$H\u0005O\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\u0011\u0019Fa\u0019\u0011\u000bm\n\tD!\u0016\u0011!m\u00129Fa\u0017lWN\f\t!a\u0006\u0002$\u0005=\u0012b\u0001B-y\t1A+\u001e9mKb\u0002DA!\u0018\u0003bA!1k\u0017B0!\rq&\u0011\r\u0003\nA.\n\t\u0011!A\u0003\u0002\tD\u0011B!\u001a,\u0003\u0003\u0005\r!a\u0013\u0002\u0007a$\u0003'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HeN\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001d\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\t=\u0004\u0003BAd\u0005cJAAa\u001d\u0002J\n1qJ\u00196fGR\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AddImageOperation.class */
public class AddImageOperation implements Product, Serializable {
    private final Source<?> imageSource;
    private final float width;
    private final float height;
    private final Point2D position;
    private final PageRange pageRange;
    private final int rotation;
    private final Seq<Object> exceptPages;
    private final Option<String> signatureFieldName;

    public static Option<Tuple8<Source<?>, Object, Object, Point2D, PageRange, Object, Seq<Object>, Option<String>>> unapply(final AddImageOperation x$0) {
        return AddImageOperation$.MODULE$.unapply(x$0);
    }

    public static AddImageOperation apply(final Source<?> imageSource, final float width, final float height, final Point2D position, final PageRange pageRange, final int rotation, final Seq<Object> exceptPages, final Option<String> signatureFieldName) {
        return AddImageOperation$.MODULE$.apply(imageSource, width, height, position, pageRange, rotation, exceptPages, signatureFieldName);
    }

    public static Function1<Tuple8<Source<?>, Object, Object, Point2D, PageRange, Object, Seq<Object>, Option<String>>, AddImageOperation> tupled() {
        return AddImageOperation$.MODULE$.tupled();
    }

    public static Function1<Source<?>, Function1<Object, Function1<Object, Function1<Point2D, Function1<PageRange, Function1<Object, Function1<Seq<Object>, Function1<Option<String>, AddImageOperation>>>>>>>> curried() {
        return AddImageOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Source<?> imageSource() {
        return this.imageSource;
    }

    public float width() {
        return this.width;
    }

    public float height() {
        return this.height;
    }

    public Point2D position() {
        return this.position;
    }

    public AddImageOperation copy(final Source<?> imageSource, final float width, final float height, final Point2D position, final PageRange pageRange, final int rotation, final Seq<Object> exceptPages, final Option<String> signatureFieldName) {
        return new AddImageOperation(imageSource, width, height, position, pageRange, rotation, exceptPages, signatureFieldName);
    }

    public Source<?> copy$default$1() {
        return imageSource();
    }

    public float copy$default$2() {
        return width();
    }

    public float copy$default$3() {
        return height();
    }

    public Point2D copy$default$4() {
        return position();
    }

    public String productPrefix() {
        return "AddImageOperation";
    }

    public int productArity() {
        return 8;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return imageSource();
            case 1:
                return BoxesRunTime.boxToFloat(width());
            case 2:
                return BoxesRunTime.boxToFloat(height());
            case 3:
                return position();
            case 4:
                return pageRange();
            case 5:
                return BoxesRunTime.boxToInteger(rotation());
            case 6:
                return exceptPages();
            case 7:
                return signatureFieldName();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof AddImageOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "imageSource";
            case 1:
                return "width";
            case 2:
                return "height";
            case 3:
                return "position";
            case 4:
                return "pageRange";
            case 5:
                return "rotation";
            case 6:
                return "exceptPages";
            case 7:
                return "signatureFieldName";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(imageSource())), Statics.floatHash(width())), Statics.floatHash(height())), Statics.anyHash(position())), Statics.anyHash(pageRange())), rotation()), Statics.anyHash(exceptPages())), Statics.anyHash(signatureFieldName())), 8);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof AddImageOperation) {
                AddImageOperation addImageOperation = (AddImageOperation) x$1;
                if (width() == addImageOperation.width() && height() == addImageOperation.height() && rotation() == addImageOperation.rotation()) {
                    Source<?> sourceImageSource = imageSource();
                    Source<?> sourceImageSource2 = addImageOperation.imageSource();
                    if (sourceImageSource != null ? sourceImageSource.equals(sourceImageSource2) : sourceImageSource2 == null) {
                        Point2D point2DPosition = position();
                        Point2D point2DPosition2 = addImageOperation.position();
                        if (point2DPosition != null ? point2DPosition.equals(point2DPosition2) : point2DPosition2 == null) {
                            PageRange pageRange = pageRange();
                            PageRange pageRange2 = addImageOperation.pageRange();
                            if (pageRange != null ? pageRange.equals(pageRange2) : pageRange2 == null) {
                                Seq<Object> seqExceptPages = exceptPages();
                                Seq<Object> seqExceptPages2 = addImageOperation.exceptPages();
                                if (seqExceptPages != null ? seqExceptPages.equals(seqExceptPages2) : seqExceptPages2 == null) {
                                    Option<String> optionSignatureFieldName = signatureFieldName();
                                    Option<String> optionSignatureFieldName2 = addImageOperation.signatureFieldName();
                                    if (optionSignatureFieldName != null ? optionSignatureFieldName.equals(optionSignatureFieldName2) : optionSignatureFieldName2 == null) {
                                        if (addImageOperation.canEqual(this)) {
                                        }
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

    public AddImageOperation(final Source<?> imageSource, final float width, final float height, final Point2D position, final PageRange pageRange, final int rotation, final Seq<Object> exceptPages, final Option<String> signatureFieldName) {
        this.imageSource = imageSource;
        this.width = width;
        this.height = height;
        this.position = position;
        this.pageRange = pageRange;
        this.rotation = rotation;
        this.exceptPages = exceptPages;
        this.signatureFieldName = signatureFieldName;
        Product.$init$(this);
    }

    public PageRange pageRange() {
        return this.pageRange;
    }

    public int rotation() {
        return this.rotation;
    }

    public Seq<Object> exceptPages() {
        return this.exceptPages;
    }

    public PageRange copy$default$5() {
        return pageRange();
    }

    public int copy$default$6() {
        return rotation();
    }

    public Seq<Object> copy$default$7() {
        return exceptPages();
    }

    public Option<String> signatureFieldName() {
        return this.signatureFieldName;
    }

    public Option<String> copy$default$8() {
        return signatureFieldName();
    }
}
