package code.sejda.tasks.edit;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005Eb\u0001\u0002\f\u0018\u0001\u0002B\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\tw\u0001\u0011\t\u0012)A\u0005q!)A\b\u0001C\u0001{!9\u0011\tAA\u0001\n\u0003\u0011\u0005b\u0002#\u0001#\u0003%\t!\u0012\u0005\b!\u0002\t\t\u0011\"\u0011R\u0011\u001dQ\u0006!!A\u0005\u0002]Bqa\u0017\u0001\u0002\u0002\u0013\u0005A\fC\u0004c\u0001\u0005\u0005I\u0011I2\t\u000f)\u0004\u0011\u0011!C\u0001W\"9\u0001\u000fAA\u0001\n\u0003\n\bbB:\u0001\u0003\u0003%\t\u0005\u001e\u0005\bk\u0002\t\t\u0011\"\u0011w\u0011\u001d9\b!!A\u0005Ba<qA_\f\u0002\u0002#\u00051PB\u0004\u0017/\u0005\u0005\t\u0012\u0001?\t\rq\u0002B\u0011AA\t\u0011\u001d)\b#!A\u0005FYD\u0011\"a\u0005\u0011\u0003\u0003%\t)!\u0006\t\u0013\u0005e\u0001#!A\u0005\u0002\u0006m\u0001\"CA\u0014!\u0005\u0005I\u0011BA\u0015\u0005M!U\r\\3uKB\u000bw-Z(qKJ\fG/[8o\u0015\tA\u0012$\u0001\u0003fI&$(B\u0001\u000e\u001c\u0003\u0015!\u0018m]6t\u0015\taR$A\u0003tK*$\u0017MC\u0001\u001f\u0003\u0011\u0019w\u000eZ3\u0004\u0001M!\u0001!I\u0014+!\t\u0011S%D\u0001$\u0015\u0005!\u0013!B:dC2\f\u0017B\u0001\u0014$\u0005\u0019\te.\u001f*fMB\u0011!\u0005K\u0005\u0003S\r\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002,g9\u0011A&\r\b\u0003[Aj\u0011A\f\u0006\u0003_}\ta\u0001\u0010:p_Rt\u0014\"\u0001\u0013\n\u0005I\u001a\u0013a\u00029bG.\fw-Z\u0005\u0003iU\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!AM\u0012\u0002\u0015A\fw-\u001a(v[\n,'/F\u00019!\t\u0011\u0013(\u0003\u0002;G\t\u0019\u0011J\u001c;\u0002\u0017A\fw-\u001a(v[\n,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005y\u0002\u0005CA \u0001\u001b\u00059\u0002\"\u0002\u001c\u0004\u0001\u0004A\u0014\u0001B2paf$\"AP\"\t\u000fY\"\u0001\u0013!a\u0001q\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001$+\u0005a:5&\u0001%\u0011\u0005%sU\"\u0001&\u000b\u0005-c\u0015!C;oG\",7m[3e\u0015\ti5%\u0001\u0006b]:|G/\u0019;j_:L!a\u0014&\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002%B\u00111\u000bW\u0007\u0002)*\u0011QKV\u0001\u0005Y\u0006twMC\u0001X\u0003\u0011Q\u0017M^1\n\u0005e#&AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005u\u0003\u0007C\u0001\u0012_\u0013\ty6EA\u0002B]fDq!\u0019\u0005\u0002\u0002\u0003\u0007\u0001(A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002IB\u0019Q\r[/\u000e\u0003\u0019T!aZ\u0012\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002jM\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\taw\u000e\u0005\u0002#[&\u0011an\t\u0002\b\u0005>|G.Z1o\u0011\u001d\t'\"!AA\u0002u\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0011!K\u001d\u0005\bC.\t\t\u00111\u00019\u0003!A\u0017m\u001d5D_\u0012,G#\u0001\u001d\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012AU\u0001\u0007KF,\u0018\r\\:\u0015\u00051L\bbB1\u000f\u0003\u0003\u0005\r!X\u0001\u0014\t\u0016dW\r^3QC\u001e,w\n]3sCRLwN\u001c\t\u0003\u007fA\u0019B\u0001E?\u0002\bA)a0a\u00019}5\tqPC\u0002\u0002\u0002\r\nqA];oi&lW-C\u0002\u0002\u0006}\u0014\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\tI!a\u0004\u000e\u0005\u0005-!bAA\u0007-\u0006\u0011\u0011n\\\u0005\u0004i\u0005-A#A>\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0007y\n9\u0002C\u00037'\u0001\u0007\u0001(A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005u\u00111\u0005\t\u0005E\u0005}\u0001(C\u0002\u0002\"\r\u0012aa\u00149uS>t\u0007\u0002CA\u0013)\u0005\u0005\t\u0019\u0001 \u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002,A\u00191+!\f\n\u0007\u0005=BK\u0001\u0004PE*,7\r\u001e")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeletePageOperation.class */
public class DeletePageOperation implements Product, Serializable {
    private final int pageNumber;

    public static Option<Object> unapply(final DeletePageOperation x$0) {
        return DeletePageOperation$.MODULE$.unapply(x$0);
    }

    public static DeletePageOperation apply(final int pageNumber) {
        return DeletePageOperation$.MODULE$.apply(pageNumber);
    }

    public static <A> Function1<Object, A> andThen(final Function1<DeletePageOperation, A> g) {
        return DeletePageOperation$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, DeletePageOperation> compose(final Function1<A, Object> g) {
        return DeletePageOperation$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public DeletePageOperation copy(final int pageNumber) {
        return new DeletePageOperation(pageNumber);
    }

    public int copy$default$1() {
        return pageNumber();
    }

    public String productPrefix() {
        return "DeletePageOperation";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(pageNumber());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof DeletePageOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "pageNumber";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), pageNumber()), 1);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof DeletePageOperation) {
                DeletePageOperation deletePageOperation = (DeletePageOperation) x$1;
                if (pageNumber() != deletePageOperation.pageNumber() || !deletePageOperation.canEqual(this)) {
                }
            }
            return false;
        }
        return true;
    }

    public DeletePageOperation(final int pageNumber) {
        this.pageNumber = pageNumber;
        Product.$init$(this);
    }
}
