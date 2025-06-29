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
@ScalaSignature(bytes = "\u0006\u0005\u0005%c\u0001B\r\u001b\u0001\u000eB\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t}\u0001\u0011\t\u0012)A\u0005w!Aq\b\u0001BK\u0002\u0013\u0005!\b\u0003\u0005A\u0001\tE\t\u0015!\u0003<\u0011\u0015\t\u0005\u0001\"\u0001C\u0011\u001d9\u0005!!A\u0005\u0002!Cqa\u0013\u0001\u0012\u0002\u0013\u0005A\nC\u0004X\u0001E\u0005I\u0011\u0001'\t\u000fa\u0003\u0011\u0011!C!3\"9!\rAA\u0001\n\u0003Q\u0004bB2\u0001\u0003\u0003%\t\u0001\u001a\u0005\bU\u0002\t\t\u0011\"\u0011l\u0011\u001d\u0011\b!!A\u0005\u0002MDq\u0001\u001f\u0001\u0002\u0002\u0013\u0005\u0013\u0010C\u0004|\u0001\u0005\u0005I\u0011\t?\t\u000fu\u0004\u0011\u0011!C!}\"Aq\u0010AA\u0001\n\u0003\n\taB\u0005\u0002\u0006i\t\t\u0011#\u0001\u0002\b\u0019A\u0011DGA\u0001\u0012\u0003\tI\u0001\u0003\u0004B'\u0011\u0005\u0011\u0011\u0005\u0005\b{N\t\t\u0011\"\u0012\u007f\u0011%\t\u0019cEA\u0001\n\u0003\u000b)\u0003C\u0005\u0002,M\t\t\u0011\"!\u0002.!I\u0011qH\n\u0002\u0002\u0013%\u0011\u0011\t\u0002\u0014\t\u0016dW\r^3MS:\\w\n]3sCRLwN\u001c\u0006\u00037q\tA!\u001a3ji*\u0011QDH\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003?\u0001\nQa]3kI\u0006T\u0011!I\u0001\u0005G>$Wm\u0001\u0001\u0014\t\u0001!#&\f\t\u0003K!j\u0011A\n\u0006\u0002O\u0005)1oY1mC&\u0011\u0011F\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\u0015Z\u0013B\u0001\u0017'\u0005\u001d\u0001&o\u001c3vGR\u0004\"A\f\u001c\u000f\u0005=\"dB\u0001\u00194\u001b\u0005\t$B\u0001\u001a#\u0003\u0019a$o\\8u}%\tq%\u0003\u00026M\u00059\u0001/Y2lC\u001e,\u0017BA\u001c9\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t)d%\u0001\u0002jIV\t1\b\u0005\u0002&y%\u0011QH\n\u0002\u0004\u0013:$\u0018aA5eA\u0005Q\u0001/Y4f\u001dVl'-\u001a:\u0002\u0017A\fw-\u001a(v[\n,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007\r+e\t\u0005\u0002E\u00015\t!\u0004C\u0003:\u000b\u0001\u00071\bC\u0003@\u000b\u0001\u00071(\u0001\u0003d_BLHcA\"J\u0015\"9\u0011H\u0002I\u0001\u0002\u0004Y\u0004bB \u0007!\u0003\u0005\raO\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005i%FA\u001eOW\u0005y\u0005C\u0001)V\u001b\u0005\t&B\u0001*T\u0003%)hn\u00195fG.,GM\u0003\u0002UM\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005Y\u000b&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012\u0014!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001[!\tY\u0006-D\u0001]\u0015\tif,\u0001\u0003mC:<'\"A0\u0002\t)\fg/Y\u0005\u0003Cr\u0013aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003K\"\u0004\"!\n4\n\u0005\u001d4#aA!os\"9\u0011nCA\u0001\u0002\u0004Y\u0014a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001m!\ri\u0007/Z\u0007\u0002]*\u0011qNJ\u0001\u000bG>dG.Z2uS>t\u0017BA9o\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005Q<\bCA\u0013v\u0013\t1hEA\u0004C_>dW-\u00198\t\u000f%l\u0011\u0011!a\u0001K\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\tQ&\u0010C\u0004j\u001d\u0005\u0005\t\u0019A\u001e\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012aO\u0001\ti>\u001cFO]5oOR\t!,\u0001\u0004fcV\fGn\u001d\u000b\u0004i\u0006\r\u0001bB5\u0012\u0003\u0003\u0005\r!Z\u0001\u0014\t\u0016dW\r^3MS:\\w\n]3sCRLwN\u001c\t\u0003\tN\u0019RaEA\u0006\u0003/\u0001r!!\u0004\u0002\u0014mZ4)\u0004\u0002\u0002\u0010)\u0019\u0011\u0011\u0003\u0014\u0002\u000fI,h\u000e^5nK&!\u0011QCA\b\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gN\r\t\u0005\u00033\ty\"\u0004\u0002\u0002\u001c)\u0019\u0011Q\u00040\u0002\u0005%|\u0017bA\u001c\u0002\u001cQ\u0011\u0011qA\u0001\u0006CB\u0004H.\u001f\u000b\u0006\u0007\u0006\u001d\u0012\u0011\u0006\u0005\u0006sY\u0001\ra\u000f\u0005\u0006\u007fY\u0001\raO\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\ty#a\u000f\u0011\u000b\u0015\n\t$!\u000e\n\u0007\u0005MbE\u0001\u0004PaRLwN\u001c\t\u0006K\u0005]2hO\u0005\u0004\u0003s1#A\u0002+va2,'\u0007\u0003\u0005\u0002>]\t\t\u00111\u0001D\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u0007\u00022aWA#\u0013\r\t9\u0005\u0018\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeleteLinkOperation.class */
public class DeleteLinkOperation implements Product, Serializable {
    private final int id;
    private final int pageNumber;

    public static Option<Tuple2<Object, Object>> unapply(final DeleteLinkOperation x$0) {
        return DeleteLinkOperation$.MODULE$.unapply(x$0);
    }

    public static DeleteLinkOperation apply(final int id, final int pageNumber) {
        return DeleteLinkOperation$.MODULE$.apply(id, pageNumber);
    }

    public static Function1<Tuple2<Object, Object>, DeleteLinkOperation> tupled() {
        return DeleteLinkOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Object, DeleteLinkOperation>> curried() {
        return DeleteLinkOperation$.MODULE$.curried();
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

    public DeleteLinkOperation copy(final int id, final int pageNumber) {
        return new DeleteLinkOperation(id, pageNumber);
    }

    public int copy$default$1() {
        return id();
    }

    public int copy$default$2() {
        return pageNumber();
    }

    public String productPrefix() {
        return "DeleteLinkOperation";
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
        return x$1 instanceof DeleteLinkOperation;
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
            if (x$1 instanceof DeleteLinkOperation) {
                DeleteLinkOperation deleteLinkOperation = (DeleteLinkOperation) x$1;
                if (id() != deleteLinkOperation.id() || pageNumber() != deleteLinkOperation.pageNumber() || !deleteLinkOperation.canEqual(this)) {
                }
            }
            return false;
        }
        return true;
    }

    public DeleteLinkOperation(final int id, final int pageNumber) {
        this.id = id;
        this.pageNumber = pageNumber;
        Product.$init$(this);
    }
}
