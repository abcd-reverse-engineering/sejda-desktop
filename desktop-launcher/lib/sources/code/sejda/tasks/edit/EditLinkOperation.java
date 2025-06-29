package code.sejda.tasks.edit;

import java.io.Serializable;
import org.sejda.model.RectangularBox;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005uf\u0001\u0002\u0012$\u00012B\u0001B\u0011\u0001\u0003\u0016\u0004%\ta\u0011\u0005\t\u000f\u0002\u0011\t\u0012)A\u0005\t\"A\u0001\n\u0001BK\u0002\u0013\u00051\t\u0003\u0005J\u0001\tE\t\u0015!\u0003E\u0011!Q\u0005A!f\u0001\n\u0003Y\u0005\u0002C/\u0001\u0005#\u0005\u000b\u0011\u0002'\t\u0011y\u0003!Q3A\u0005\u0002}C\u0001b\u0019\u0001\u0003\u0012\u0003\u0006I\u0001\u0019\u0005\tI\u0002\u0011)\u001a!C\u0001K\"A!\u000e\u0001B\tB\u0003%a\rC\u0003l\u0001\u0011\u0005A\u000eC\u0004t\u0001\u0005\u0005I\u0011\u0001;\t\u000fi\u0004\u0011\u0013!C\u0001w\"A\u0011Q\u0002\u0001\u0012\u0002\u0013\u00051\u0010C\u0005\u0002\u0010\u0001\t\n\u0011\"\u0001\u0002\u0012!I\u0011Q\u0003\u0001\u0012\u0002\u0013\u0005\u0011q\u0003\u0005\n\u00037\u0001\u0011\u0013!C\u0001\u0003;A\u0011\"!\t\u0001\u0003\u0003%\t%a\t\t\u0011\u0005M\u0002!!A\u0005\u0002\rC\u0011\"!\u000e\u0001\u0003\u0003%\t!a\u000e\t\u0013\u0005\r\u0003!!A\u0005B\u0005\u0015\u0003\"CA*\u0001\u0005\u0005I\u0011AA+\u0011%\ty\u0006AA\u0001\n\u0003\n\t\u0007C\u0005\u0002f\u0001\t\t\u0011\"\u0011\u0002h!I\u0011\u0011\u000e\u0001\u0002\u0002\u0013\u0005\u00131\u000e\u0005\n\u0003[\u0002\u0011\u0011!C!\u0003_:\u0011\"a\u001d$\u0003\u0003E\t!!\u001e\u0007\u0011\t\u001a\u0013\u0011!E\u0001\u0003oBaa\u001b\u000f\u0005\u0002\u0005=\u0005\"CA59\u0005\u0005IQIA6\u0011%\t\t\nHA\u0001\n\u0003\u000b\u0019\nC\u0005\u0002 r\t\t\u0011\"!\u0002\"\"I\u00111\u0017\u000f\u0002\u0002\u0013%\u0011Q\u0017\u0002\u0012\u000b\u0012LG\u000fT5oW>\u0003XM]1uS>t'B\u0001\u0013&\u0003\u0011)G-\u001b;\u000b\u0005\u0019:\u0013!\u0002;bg.\u001c(B\u0001\u0015*\u0003\u0015\u0019XM\u001b3b\u0015\u0005Q\u0013\u0001B2pI\u0016\u001c\u0001a\u0005\u0003\u0001[M2\u0004C\u0001\u00182\u001b\u0005y#\"\u0001\u0019\u0002\u000bM\u001c\u0017\r\\1\n\u0005Iz#AB!osJ+g\r\u0005\u0002/i%\u0011Qg\f\u0002\b!J|G-^2u!\t9tH\u0004\u00029{9\u0011\u0011\bP\u0007\u0002u)\u00111hK\u0001\u0007yI|w\u000e\u001e \n\u0003AJ!AP\u0018\u0002\u000fA\f7m[1hK&\u0011\u0001)\u0011\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003}=\n!!\u001b3\u0016\u0003\u0011\u0003\"AL#\n\u0005\u0019{#aA%oi\u0006\u0019\u0011\u000e\u001a\u0011\u0002\u0015A\fw-\u001a(v[\n,'/A\u0006qC\u001e,g*^7cKJ\u0004\u0013!\u00042pk:$\u0017N\\4C_b,7/F\u0001M!\ri\u0015\u000b\u0016\b\u0003\u001d>\u0003\"!O\u0018\n\u0005A{\u0013A\u0002)sK\u0012,g-\u0003\u0002S'\n\u00191+\u001a;\u000b\u0005A{\u0003CA+\\\u001b\u00051&BA,Y\u0003\u0015iw\u000eZ3m\u0015\tA\u0013LC\u0001[\u0003\ry'oZ\u0005\u00039Z\u0013aBU3di\u0006tw-\u001e7be\n{\u00070\u0001\bc_VtG-\u001b8h\u0005>DXm\u001d\u0011\u0002\t!\u0014XMZ\u000b\u0002AB\u0011Q*Y\u0005\u0003EN\u0013aa\u0015;sS:<\u0017!\u00025sK\u001a\u0004\u0013\u0001B6j]\u0012,\u0012A\u001a\t\u0003O\"l\u0011aI\u0005\u0003S\u000e\u0012\u0001\u0002T5oWRK\b/Z\u0001\u0006W&tG\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\r5tw\u000e]9s!\t9\u0007\u0001C\u0003C\u0017\u0001\u0007A\tC\u0003I\u0017\u0001\u0007A\tC\u0003K\u0017\u0001\u0007A\nC\u0003_\u0017\u0001\u0007\u0001\rC\u0003e\u0017\u0001\u0007a-\u0001\u0003d_BLHCB7vm^D\u0018\u0010C\u0004C\u0019A\u0005\t\u0019\u0001#\t\u000f!c\u0001\u0013!a\u0001\t\"9!\n\u0004I\u0001\u0002\u0004a\u0005b\u00020\r!\u0003\u0005\r\u0001\u0019\u0005\bI2\u0001\n\u00111\u0001g\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012\u0001 \u0016\u0003\tv\\\u0013A \t\u0004\u007f\u0006%QBAA\u0001\u0015\u0011\t\u0019!!\u0002\u0002\u0013Ut7\r[3dW\u0016$'bAA\u0004_\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005-\u0011\u0011\u0001\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\t\t\u0019B\u000b\u0002M{\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\"TCAA\rU\t\u0001W0\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0016\u0005\u0005}!F\u00014~\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u0011Q\u0005\t\u0005\u0003O\t\t$\u0004\u0002\u0002*)!\u00111FA\u0017\u0003\u0011a\u0017M\\4\u000b\u0005\u0005=\u0012\u0001\u00026bm\u0006L1AYA\u0015\u00031\u0001(o\u001c3vGR\f%/\u001b;z\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!!\u000f\u0002@A\u0019a&a\u000f\n\u0007\u0005urFA\u0002B]fD\u0001\"!\u0011\u0015\u0003\u0003\u0005\r\u0001R\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005\u001d\u0003CBA%\u0003\u001f\nI$\u0004\u0002\u0002L)\u0019\u0011QJ\u0018\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002R\u0005-#\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a\u0016\u0002^A\u0019a&!\u0017\n\u0007\u0005msFA\u0004C_>dW-\u00198\t\u0013\u0005\u0005c#!AA\u0002\u0005e\u0012A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!!\n\u0002d!A\u0011\u0011I\f\u0002\u0002\u0003\u0007A)\u0001\u0005iCND7i\u001c3f)\u0005!\u0015\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005\u0015\u0012AB3rk\u0006d7\u000f\u0006\u0003\u0002X\u0005E\u0004\"CA!5\u0005\u0005\t\u0019AA\u001d\u0003E)E-\u001b;MS:\\w\n]3sCRLwN\u001c\t\u0003Or\u0019R\u0001HA=\u0003\u000b\u0003\"\"a\u001f\u0002\u0002\u0012#E\n\u00194n\u001b\t\tiHC\u0002\u0002��=\nqA];oi&lW-\u0003\u0003\u0002\u0004\u0006u$!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8okA!\u0011qQAG\u001b\t\tII\u0003\u0003\u0002\f\u00065\u0012AA5p\u0013\r\u0001\u0015\u0011\u0012\u000b\u0003\u0003k\nQ!\u00199qYf$2\"\\AK\u0003/\u000bI*a'\u0002\u001e\")!i\ba\u0001\t\")\u0001j\ba\u0001\t\")!j\ba\u0001\u0019\")al\ba\u0001A\")Am\ba\u0001M\u00069QO\\1qa2LH\u0003BAR\u0003_\u0003RALAS\u0003SK1!a*0\u0005\u0019y\u0005\u000f^5p]BAa&a+E\t2\u0003g-C\u0002\u0002.>\u0012a\u0001V;qY\u0016,\u0004\u0002CAYA\u0005\u0005\t\u0019A7\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u00028B!\u0011qEA]\u0013\u0011\tY,!\u000b\u0003\r=\u0013'.Z2u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/EditLinkOperation.class */
public class EditLinkOperation implements Product, Serializable {
    private final int id;
    private final int pageNumber;
    private final Set<RectangularBox> boundingBoxes;
    private final String href;
    private final LinkType kind;

    public static Option<Tuple5<Object, Object, Set<RectangularBox>, String, LinkType>> unapply(final EditLinkOperation x$0) {
        return EditLinkOperation$.MODULE$.unapply(x$0);
    }

    public static EditLinkOperation apply(final int id, final int pageNumber, final Set<RectangularBox> boundingBoxes, final String href, final LinkType kind) {
        return EditLinkOperation$.MODULE$.apply(id, pageNumber, boundingBoxes, href, kind);
    }

    public static Function1<Tuple5<Object, Object, Set<RectangularBox>, String, LinkType>, EditLinkOperation> tupled() {
        return EditLinkOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Object, Function1<Set<RectangularBox>, Function1<String, Function1<LinkType, EditLinkOperation>>>>> curried() {
        return EditLinkOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int id() {
        return this.id;
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

    public EditLinkOperation copy(final int id, final int pageNumber, final Set<RectangularBox> boundingBoxes, final String href, final LinkType kind) {
        return new EditLinkOperation(id, pageNumber, boundingBoxes, href, kind);
    }

    public int copy$default$1() {
        return id();
    }

    public int copy$default$2() {
        return pageNumber();
    }

    public Set<RectangularBox> copy$default$3() {
        return boundingBoxes();
    }

    public String copy$default$4() {
        return href();
    }

    public LinkType copy$default$5() {
        return kind();
    }

    public String productPrefix() {
        return "EditLinkOperation";
    }

    public int productArity() {
        return 5;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(id());
            case 1:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 2:
                return boundingBoxes();
            case 3:
                return href();
            case 4:
                return kind();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof EditLinkOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "id";
            case 1:
                return "pageNumber";
            case 2:
                return "boundingBoxes";
            case 3:
                return "href";
            case 4:
                return "kind";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), id()), pageNumber()), Statics.anyHash(boundingBoxes())), Statics.anyHash(href())), Statics.anyHash(kind())), 5);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof EditLinkOperation) {
                EditLinkOperation editLinkOperation = (EditLinkOperation) x$1;
                if (id() == editLinkOperation.id() && pageNumber() == editLinkOperation.pageNumber()) {
                    Set<RectangularBox> setBoundingBoxes = boundingBoxes();
                    Set<RectangularBox> setBoundingBoxes2 = editLinkOperation.boundingBoxes();
                    if (setBoundingBoxes != null ? setBoundingBoxes.equals(setBoundingBoxes2) : setBoundingBoxes2 == null) {
                        String strHref = href();
                        String strHref2 = editLinkOperation.href();
                        if (strHref != null ? strHref.equals(strHref2) : strHref2 == null) {
                            LinkType linkTypeKind = kind();
                            LinkType linkTypeKind2 = editLinkOperation.kind();
                            if (linkTypeKind != null ? linkTypeKind.equals(linkTypeKind2) : linkTypeKind2 == null) {
                                if (editLinkOperation.canEqual(this)) {
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

    public EditLinkOperation(final int id, final int pageNumber, final Set<RectangularBox> boundingBoxes, final String href, final LinkType kind) {
        this.id = id;
        this.pageNumber = pageNumber;
        this.boundingBoxes = boundingBoxes;
        this.href = href;
        this.kind = kind;
        Product.$init$(this);
    }
}
