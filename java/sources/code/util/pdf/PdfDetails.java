package code.util.pdf;

import com.google.common.cache.LoadingCache;
import java.io.Serializable;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.TaskSource;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: PdfDetails.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005uv!\u0002\r\u001a\u0011\u0003\u0001c!\u0002\u0012\u001a\u0011\u0003\u0019\u0003\"\u0002\u001c\u0002\t\u00039\u0004\u0002\u0003\u001d\u0002\u0011\u000b\u0007I\u0011A\u001d\t\u000f\u0005}\u0014\u0001\"\u0001\u0002\u0002\"9\u00111S\u0001\u0005\n\u0005U\u0005\"CAR\u0003\u0005\u0005I\u0011QAS\u0011%\tI+AA\u0001\n\u0003\u000bY\u000bC\u0005\u00024\u0006\t\t\u0011\"\u0003\u00026\u001a!!%\u0007!a\u0011!y\u0017B!f\u0001\n\u0003\u0001\b\u0002\u0003;\n\u0005#\u0005\u000b\u0011B9\t\u000bYJA\u0011A;\t\u000f]L\u0011\u0011!C\u0001q\"9!0CI\u0001\n\u0003Y\b\"CA\u0007\u0013\u0005\u0005I\u0011IA\b\u0011!\ti\"CA\u0001\n\u0003\u0001\b\"CA\u0010\u0013\u0005\u0005I\u0011AA\u0011\u0011%\ti#CA\u0001\n\u0003\ny\u0003C\u0005\u0002>%\t\t\u0011\"\u0001\u0002@!I\u0011\u0011J\u0005\u0002\u0002\u0013\u0005\u00131\n\u0005\n\u0003\u001fJ\u0011\u0011!C!\u0003#B\u0011\"a\u0015\n\u0003\u0003%\t%!\u0016\t\u0013\u0005]\u0013\"!A\u0005B\u0005e\u0013A\u0003)eM\u0012+G/Y5mg*\u0011!dG\u0001\u0004a\u00124'B\u0001\u000f\u001e\u0003\u0011)H/\u001b7\u000b\u0003y\tAaY8eK\u000e\u0001\u0001CA\u0011\u0002\u001b\u0005I\"A\u0003)eM\u0012+G/Y5mgN!\u0011\u0001\n\u0016/!\t)\u0003&D\u0001'\u0015\u00059\u0013!B:dC2\f\u0017BA\u0015'\u0005\u0019\te.\u001f*fMB\u00111\u0006L\u0007\u00027%\u0011Qf\u0007\u0002\t\u0019><w-\u00192mKB\u0011q\u0006N\u0007\u0002a)\u0011\u0011GM\u0001\u0003S>T\u0011aM\u0001\u0005U\u00064\u0018-\u0003\u00026a\ta1+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\"\u0012\u0001I\u0001\u0006G\u0006\u001c\u0007.Z\u000b\u0002uA!1hQ#]\u001b\u0005a$B\u0001\u001d>\u0015\tqt(\u0001\u0004d_6lwN\u001c\u0006\u0003\u0001\u0006\u000baaZ8pO2,'\"\u0001\"\u0002\u0007\r|W.\u0003\u0002Ey\taAj\\1eS:<7)Y2iKB\u0012a\t\u0016\t\u0004\u000fB\u0013V\"\u0001%\u000b\u0005%S\u0015!B5oaV$(BA&M\u0003\u0015iw\u000eZ3m\u0015\tie*A\u0003tK*$\u0017MC\u0001P\u0003\ry'oZ\u0005\u0003#\"\u0013\u0011\u0002\u00153g'>,(oY3\u0011\u0005M#F\u0002\u0001\u0003\u000b+Z\u000b\t\u0011!A\u0003\u0002\u0005]$aA0%c\u0019!qk\u0001\u0002Y\u0005\u0015!\u0013M\\8o'\t1\u0016\f\u0005\u0003<5\u0016c\u0016BA.=\u0005-\u0019\u0015m\u00195f\u0019>\fG-\u001a:\u0011\u0007\u0015jv,\u0003\u0002_M\t1q\n\u001d;j_:\u0004\"!I\u0005\u0014\t%!\u0013\r\u001a\t\u0003K\tL!a\u0019\u0014\u0003\u000fA\u0013x\u000eZ;diB\u0011Q-\u001c\b\u0003M.t!a\u001a6\u000e\u0003!T!![\u0010\u0002\rq\u0012xn\u001c;?\u0013\u00059\u0013B\u00017'\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u000e8\u000b\u000514\u0013!\u00029bO\u0016\u001cX#A9\u0011\u0005\u0015\u0012\u0018BA:'\u0005\rIe\u000e^\u0001\u0007a\u0006<Wm\u001d\u0011\u0015\u0005}3\b\"B8\r\u0001\u0004\t\u0018\u0001B2paf$\"aX=\t\u000f=l\u0001\u0013!a\u0001c\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001?+\u0005El8&\u0001@\u0011\u0007}\fI!\u0004\u0002\u0002\u0002)!\u00111AA\u0003\u0003%)hn\u00195fG.,GMC\u0002\u0002\b\u0019\n!\"\u00198o_R\fG/[8o\u0013\u0011\tY!!\u0001\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003#\u0001B!a\u0005\u0002\u001a5\u0011\u0011Q\u0003\u0006\u0004\u0003/\u0011\u0014\u0001\u00027b]\u001eLA!a\u0007\u0002\u0016\t11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002$\u0005%\u0002cA\u0013\u0002&%\u0019\u0011q\u0005\u0014\u0003\u0007\u0005s\u0017\u0010\u0003\u0005\u0002,E\t\t\u00111\u0001r\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011\u0011\u0007\t\u0007\u0003g\tI$a\t\u000e\u0005\u0005U\"bAA\u001cM\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005m\u0012Q\u0007\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002B\u0005\u001d\u0003cA\u0013\u0002D%\u0019\u0011Q\t\u0014\u0003\u000f\t{w\u000e\\3b]\"I\u00111F\n\u0002\u0002\u0003\u0007\u00111E\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002\u0012\u00055\u0003\u0002CA\u0016)\u0005\u0005\t\u0019A9\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012!]\u0001\ti>\u001cFO]5oOR\u0011\u0011\u0011C\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005\u0005\u00131\f\u0005\n\u0003W9\u0012\u0011!a\u0001\u0003GAaA\u000e,\u0005\u0002\u0005}CCAA1!\t\u0019f\u000bC\u0004\u0002fY#\t!a\u001a\u0002\t1|\u0017\r\u001a\u000b\u00049\u0006%\u0004\u0002CA6\u0003G\u0002\r!!\u001c\u0002\rM|WO]2fa\u0011\ty'a\u001d\u0011\t\u001d\u0003\u0016\u0011\u000f\t\u0004'\u0006MD\u0001DA;\u0003S\n\t\u0011!A\u0003\u0002\u0005]$aA0%eE!\u0011\u0011PA\u0012!\r)\u00131P\u0005\u0004\u0003{2#a\u0002(pi\"LgnZ\u0001\fa\u0006\u00148/Z\"bG\",G\rF\u0002]\u0003\u0007Cq!a\u001b\u0005\u0001\u0004\t)\t\r\u0003\u0002\b\u0006=\u0005#B$\u0002\n\u00065\u0015bAAF\u0011\nQA+Y:l'>,(oY3\u0011\u0007M\u000by\t\u0002\u0007\u0002\u0012\u0006\r\u0015\u0011!A\u0001\u0006\u0003\t9HA\u0002`IM\nQ\u0001]1sg\u0016$2\u0001XAL\u0011\u001d\tY'\u0002a\u0001\u00033\u0003D!a'\u0002 B!q\tUAO!\r\u0019\u0016q\u0014\u0003\r\u0003C\u000b9*!A\u0001\u0002\u000b\u0005\u0011q\u000f\u0002\u0004?\u0012\"\u0014!B1qa2LHcA0\u0002(\")qN\u0002a\u0001c\u00069QO\\1qa2LH\u0003BAW\u0003_\u00032!J/r\u0011!\t\tlBA\u0001\u0002\u0004y\u0016a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011q\u0017\t\u0005\u0003'\tI,\u0003\u0003\u0002<\u0006U!AB(cU\u0016\u001cG\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PdfDetails.class */
public class PdfDetails implements Product, Serializable {
    private final int pages;

    public static Option<Object> unapply(final PdfDetails x$0) {
        return PdfDetails$.MODULE$.unapply(x$0);
    }

    public static PdfDetails apply(final int pages) {
        return PdfDetails$.MODULE$.apply(pages);
    }

    public static Option<PdfDetails> parseCached(final TaskSource<?> source) {
        return PdfDetails$.MODULE$.parseCached(source);
    }

    public static LoadingCache<PdfSource<?>, Option<PdfDetails>> cache() {
        return PdfDetails$.MODULE$.cache();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int pages() {
        return this.pages;
    }

    public PdfDetails copy(final int pages) {
        return new PdfDetails(pages);
    }

    public int copy$default$1() {
        return pages();
    }

    public String productPrefix() {
        return "PdfDetails";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(pages());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof PdfDetails;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "pages";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), pages()), 1);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof PdfDetails) {
                PdfDetails pdfDetails = (PdfDetails) x$1;
                if (pages() != pdfDetails.pages() || !pdfDetails.canEqual(this)) {
                }
            }
            return false;
        }
        return true;
    }

    public PdfDetails(final int pages) {
        this.pages = pages;
        Product.$init$(this);
    }
}
