package code.sejda.tasks.edit;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005%c\u0001B\r\u001b\u0001\u000eB\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t}\u0001\u0011\t\u0012)A\u0005w!Aq\b\u0001BK\u0002\u0013\u0005!\b\u0003\u0005A\u0001\tE\t\u0015!\u0003<\u0011\u0015\t\u0005\u0001\"\u0001C\u0011\u001d9\u0005!!A\u0005\u0002!Cqa\u0013\u0001\u0012\u0002\u0013\u0005A\nC\u0004X\u0001E\u0005I\u0011\u0001'\t\u000fa\u0003\u0011\u0011!C!3\"9!\rAA\u0001\n\u0003Q\u0004bB2\u0001\u0003\u0003%\t\u0001\u001a\u0005\bU\u0002\t\t\u0011\"\u0011l\u0011\u001d\u0011\b!!A\u0005\u0002MDq\u0001\u001f\u0001\u0002\u0002\u0013\u0005\u0013\u0010C\u0004|\u0001\u0005\u0005I\u0011\t?\t\u000fu\u0004\u0011\u0011!C!}\"Aq\u0010AA\u0001\n\u0003\n\taB\u0005\u0002\u0006i\t\t\u0011#\u0001\u0002\b\u0019A\u0011DGA\u0001\u0012\u0003\tI\u0001\u0003\u0004B'\u0011\u0005\u0011\u0011\u0005\u0005\b{N\t\t\u0011\"\u0012\u007f\u0011%\t\u0019cEA\u0001\n\u0003\u000b)\u0003C\u0005\u0002,M\t\t\u0011\"!\u0002.!I\u0011qH\n\u0002\u0002\u0013%\u0011\u0011\t\u0002\u0019\t\u0016dW\r^3IS\u001eDG.[4ii>\u0003XM]1uS>t'BA\u000e\u001d\u0003\u0011)G-\u001b;\u000b\u0005uq\u0012!\u0002;bg.\u001c(BA\u0010!\u0003\u0015\u0019XM\u001b3b\u0015\u0005\t\u0013\u0001B2pI\u0016\u001c\u0001a\u0005\u0003\u0001I)j\u0003CA\u0013)\u001b\u00051#\"A\u0014\u0002\u000bM\u001c\u0017\r\\1\n\u0005%2#AB!osJ+g\r\u0005\u0002&W%\u0011AF\n\u0002\b!J|G-^2u!\tqcG\u0004\u00020i9\u0011\u0001gM\u0007\u0002c)\u0011!GI\u0001\u0007yI|w\u000e\u001e \n\u0003\u001dJ!!\u000e\u0014\u0002\u000fA\f7m[1hK&\u0011q\u0007\u000f\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003k\u0019\n!\u0002]1hK:+XNY3s+\u0005Y\u0004CA\u0013=\u0013\tidEA\u0002J]R\f1\u0002]1hK:+XNY3sA\u0005\u0011\u0011\u000eZ\u0001\u0004S\u0012\u0004\u0013A\u0002\u001fj]&$h\bF\u0002D\u000b\u001a\u0003\"\u0001\u0012\u0001\u000e\u0003iAQ!O\u0003A\u0002mBQaP\u0003A\u0002m\nAaY8qsR\u00191)\u0013&\t\u000fe2\u0001\u0013!a\u0001w!9qH\u0002I\u0001\u0002\u0004Y\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u0002\u001b*\u00121HT\u0016\u0002\u001fB\u0011\u0001+V\u0007\u0002#*\u0011!kU\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001\u0016\u0014\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002W#\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u0005i\u0001O]8ek\u000e$\bK]3gSb,\u0012A\u0017\t\u00037\u0002l\u0011\u0001\u0018\u0006\u0003;z\u000bA\u0001\\1oO*\tq,\u0001\u0003kCZ\f\u0017BA1]\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5us\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HCA3i!\t)c-\u0003\u0002hM\t\u0019\u0011I\\=\t\u000f%\\\u0011\u0011!a\u0001w\u0005\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012\u0001\u001c\t\u0004[B,W\"\u00018\u000b\u0005=4\u0013AC2pY2,7\r^5p]&\u0011\u0011O\u001c\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0002uoB\u0011Q%^\u0005\u0003m\u001a\u0012qAQ8pY\u0016\fg\u000eC\u0004j\u001b\u0005\u0005\t\u0019A3\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u00035jDq!\u001b\b\u0002\u0002\u0003\u00071(\u0001\u0005iCND7i\u001c3f)\u0005Y\u0014\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003i\u000ba!Z9vC2\u001cHc\u0001;\u0002\u0004!9\u0011.EA\u0001\u0002\u0004)\u0017\u0001\u0007#fY\u0016$X\rS5hQ2Lw\r\u001b;Pa\u0016\u0014\u0018\r^5p]B\u0011AiE\n\u0006'\u0005-\u0011q\u0003\t\b\u0003\u001b\t\u0019bO\u001eD\u001b\t\tyAC\u0002\u0002\u0012\u0019\nqA];oi&lW-\u0003\u0003\u0002\u0016\u0005=!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeA!\u0011\u0011DA\u0010\u001b\t\tYBC\u0002\u0002\u001ey\u000b!![8\n\u0007]\nY\u0002\u0006\u0002\u0002\b\u0005)\u0011\r\u001d9msR)1)a\n\u0002*!)\u0011H\u0006a\u0001w!)qH\u0006a\u0001w\u00059QO\\1qa2LH\u0003BA\u0018\u0003w\u0001R!JA\u0019\u0003kI1!a\r'\u0005\u0019y\u0005\u000f^5p]B)Q%a\u000e<w%\u0019\u0011\u0011\b\u0014\u0003\rQ+\b\u000f\\33\u0011!\tidFA\u0001\u0002\u0004\u0019\u0015a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u00111\t\t\u00047\u0006\u0015\u0013bAA$9\n1qJ\u00196fGR\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeleteHighlightOperation.class */
public class DeleteHighlightOperation implements Product, Serializable {
    private final int pageNumber;
    private final int id;

    public static Option<Tuple2<Object, Object>> unapply(final DeleteHighlightOperation x$0) {
        return DeleteHighlightOperation$.MODULE$.unapply(x$0);
    }

    public static DeleteHighlightOperation apply(final int pageNumber, final int id) {
        return DeleteHighlightOperation$.MODULE$.apply(pageNumber, id);
    }

    public static Function1<Tuple2<Object, Object>, DeleteHighlightOperation> tupled() {
        return DeleteHighlightOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Object, DeleteHighlightOperation>> curried() {
        return DeleteHighlightOperation$.MODULE$.curried();
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

    public DeleteHighlightOperation copy(final int pageNumber, final int id) {
        return new DeleteHighlightOperation(pageNumber, id);
    }

    public int copy$default$1() {
        return pageNumber();
    }

    public int copy$default$2() {
        return id();
    }

    public String productPrefix() {
        return "DeleteHighlightOperation";
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 1:
                return BoxesRunTime.boxToInteger(id());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof DeleteHighlightOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "pageNumber";
            case 1:
                return "id";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), pageNumber()), id()), 2);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof DeleteHighlightOperation) {
                DeleteHighlightOperation deleteHighlightOperation = (DeleteHighlightOperation) x$1;
                if (pageNumber() != deleteHighlightOperation.pageNumber() || id() != deleteHighlightOperation.id() || !deleteHighlightOperation.canEqual(this)) {
                }
            }
            return false;
        }
        return true;
    }

    public DeleteHighlightOperation(final int pageNumber, final int id) {
        this.pageNumber = pageNumber;
        this.id = id;
        Product.$init$(this);
    }
}
