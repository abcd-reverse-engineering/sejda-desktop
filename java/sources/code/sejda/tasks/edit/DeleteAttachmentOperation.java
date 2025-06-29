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
@ScalaSignature(bytes = "\u0006\u0005\u0005%c\u0001B\r\u001b\u0001\u000eB\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t}\u0001\u0011\t\u0012)A\u0005w!Aq\b\u0001BK\u0002\u0013\u0005!\b\u0003\u0005A\u0001\tE\t\u0015!\u0003<\u0011\u0015\t\u0005\u0001\"\u0001C\u0011\u001d9\u0005!!A\u0005\u0002!Cqa\u0013\u0001\u0012\u0002\u0013\u0005A\nC\u0004X\u0001E\u0005I\u0011\u0001'\t\u000fa\u0003\u0011\u0011!C!3\"9!\rAA\u0001\n\u0003Q\u0004bB2\u0001\u0003\u0003%\t\u0001\u001a\u0005\bU\u0002\t\t\u0011\"\u0011l\u0011\u001d\u0011\b!!A\u0005\u0002MDq\u0001\u001f\u0001\u0002\u0002\u0013\u0005\u0013\u0010C\u0004|\u0001\u0005\u0005I\u0011\t?\t\u000fu\u0004\u0011\u0011!C!}\"Aq\u0010AA\u0001\n\u0003\n\taB\u0005\u0002\u0006i\t\t\u0011#\u0001\u0002\b\u0019A\u0011DGA\u0001\u0012\u0003\tI\u0001\u0003\u0004B'\u0011\u0005\u0011\u0011\u0005\u0005\b{N\t\t\u0011\"\u0012\u007f\u0011%\t\u0019cEA\u0001\n\u0003\u000b)\u0003C\u0005\u0002,M\t\t\u0011\"!\u0002.!I\u0011qH\n\u0002\u0002\u0013%\u0011\u0011\t\u0002\u001a\t\u0016dW\r^3BiR\f7\r[7f]R|\u0005/\u001a:bi&|gN\u0003\u0002\u001c9\u0005!Q\rZ5u\u0015\tib$A\u0003uCN\\7O\u0003\u0002 A\u0005)1/\u001a6eC*\t\u0011%\u0001\u0003d_\u0012,7\u0001A\n\u0005\u0001\u0011RS\u0006\u0005\u0002&Q5\taEC\u0001(\u0003\u0015\u00198-\u00197b\u0013\tIcE\u0001\u0004B]f\u0014VM\u001a\t\u0003K-J!\u0001\f\u0014\u0003\u000fA\u0013x\u000eZ;diB\u0011aF\u000e\b\u0003_Qr!\u0001M\u001a\u000e\u0003ER!A\r\u0012\u0002\rq\u0012xn\u001c;?\u0013\u00059\u0013BA\u001b'\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u000e\u001d\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005U2\u0013AA5e+\u0005Y\u0004CA\u0013=\u0013\tidEA\u0002J]R\f1!\u001b3!\u0003)\u0001\u0018mZ3Ok6\u0014WM]\u0001\fa\u0006<WMT;nE\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0004\u0007\u00163\u0005C\u0001#\u0001\u001b\u0005Q\u0002\"B\u001d\u0006\u0001\u0004Y\u0004\"B \u0006\u0001\u0004Y\u0014\u0001B2paf$2aQ%K\u0011\u001dId\u0001%AA\u0002mBqa\u0010\u0004\u0011\u0002\u0003\u00071(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u00035S#a\u000f(,\u0003=\u0003\"\u0001U+\u000e\u0003ES!AU*\u0002\u0013Ut7\r[3dW\u0016$'B\u0001+'\u0003)\tgN\\8uCRLwN\\\u0005\u0003-F\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#\u0001.\u0011\u0005m\u0003W\"\u0001/\u000b\u0005us\u0016\u0001\u00027b]\u001eT\u0011aX\u0001\u0005U\u00064\u0018-\u0003\u0002b9\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002fQB\u0011QEZ\u0005\u0003O\u001a\u00121!\u00118z\u0011\u001dI7\"!AA\u0002m\n1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#\u00017\u0011\u00075\u0004X-D\u0001o\u0015\tyg%\u0001\u0006d_2dWm\u0019;j_:L!!\u001d8\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003i^\u0004\"!J;\n\u0005Y4#a\u0002\"p_2,\u0017M\u001c\u0005\bS6\t\t\u00111\u0001f\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0005iS\bbB5\u000f\u0003\u0003\u0005\raO\u0001\tQ\u0006\u001c\bnQ8eKR\t1(\u0001\u0005u_N#(/\u001b8h)\u0005Q\u0016AB3rk\u0006d7\u000fF\u0002u\u0003\u0007Aq![\t\u0002\u0002\u0003\u0007Q-A\rEK2,G/Z!ui\u0006\u001c\u0007.\\3oi>\u0003XM]1uS>t\u0007C\u0001#\u0014'\u0015\u0019\u00121BA\f!\u001d\ti!a\u0005<w\rk!!a\u0004\u000b\u0007\u0005Ea%A\u0004sk:$\u0018.\\3\n\t\u0005U\u0011q\u0002\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\u0014\u0004\u0003BA\r\u0003?i!!a\u0007\u000b\u0007\u0005ua,\u0001\u0002j_&\u0019q'a\u0007\u0015\u0005\u0005\u001d\u0011!B1qa2LH#B\"\u0002(\u0005%\u0002\"B\u001d\u0017\u0001\u0004Y\u0004\"B \u0017\u0001\u0004Y\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0003_\tY\u0004E\u0003&\u0003c\t)$C\u0002\u00024\u0019\u0012aa\u00149uS>t\u0007#B\u0013\u00028mZ\u0014bAA\u001dM\t1A+\u001e9mKJB\u0001\"!\u0010\u0018\u0003\u0003\u0005\raQ\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA\"!\rY\u0016QI\u0005\u0004\u0003\u000fb&AB(cU\u0016\u001cG\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeleteAttachmentOperation.class */
public class DeleteAttachmentOperation implements Product, Serializable {
    private final int id;
    private final int pageNumber;

    public static Option<Tuple2<Object, Object>> unapply(final DeleteAttachmentOperation x$0) {
        return DeleteAttachmentOperation$.MODULE$.unapply(x$0);
    }

    public static DeleteAttachmentOperation apply(final int id, final int pageNumber) {
        return DeleteAttachmentOperation$.MODULE$.apply(id, pageNumber);
    }

    public static Function1<Tuple2<Object, Object>, DeleteAttachmentOperation> tupled() {
        return DeleteAttachmentOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Object, DeleteAttachmentOperation>> curried() {
        return DeleteAttachmentOperation$.MODULE$.curried();
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

    public DeleteAttachmentOperation copy(final int id, final int pageNumber) {
        return new DeleteAttachmentOperation(id, pageNumber);
    }

    public int copy$default$1() {
        return id();
    }

    public int copy$default$2() {
        return pageNumber();
    }

    public String productPrefix() {
        return "DeleteAttachmentOperation";
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(id());
            case 1:
                return BoxesRunTime.boxToInteger(pageNumber());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof DeleteAttachmentOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "id";
            case 1:
                return "pageNumber";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), id()), pageNumber()), 2);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof DeleteAttachmentOperation) {
                DeleteAttachmentOperation deleteAttachmentOperation = (DeleteAttachmentOperation) x$1;
                if (id() != deleteAttachmentOperation.id() || pageNumber() != deleteAttachmentOperation.pageNumber() || !deleteAttachmentOperation.canEqual(this)) {
                }
            }
            return false;
        }
        return true;
    }

    public DeleteAttachmentOperation(final int id, final int pageNumber) {
        this.id = id;
        this.pageNumber = pageNumber;
        Product.$init$(this);
    }
}
