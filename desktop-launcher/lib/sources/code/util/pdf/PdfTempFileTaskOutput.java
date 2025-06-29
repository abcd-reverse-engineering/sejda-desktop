package code.util.pdf;

import java.io.File;
import java.io.Serializable;
import org.sejda.model.output.FileTaskOutput;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: PdfTempFileTaskOutput.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005]b\u0001B\n\u0015\u0001nA\u0001B\u000f\u0001\u0003\u0016\u0004%\ta\u000f\u0005\t\t\u0002\u0011\t\u0012)A\u0005y!)Q\t\u0001C\u0001\r\"9!\nAA\u0001\n\u0003Y\u0005bB'\u0001#\u0003%\tA\u0014\u0005\b3\u0002\t\t\u0011\"\u0011[\u0011\u001d\t\u0007!!A\u0005\u0002\tDqA\u001a\u0001\u0002\u0002\u0013\u0005q\rC\u0004n\u0001\u0005\u0005I\u0011\t8\t\u000fU\u0004\u0011\u0011!C\u0001m\"91\u0010AA\u0001\n\u0003bxa\u0002@\u0015\u0003\u0003E\ta \u0004\t'Q\t\t\u0011#\u0001\u0002\u0002!1Q)\u0004C\u0001\u0003'A\u0011\"!\u0006\u000e\u0003\u0003%)%a\u0006\t\u0013\u0005eQ\"!A\u0005\u0002\u0006m\u0001\"CA\u0010\u001b\u0005\u0005I\u0011QA\u0011\u0011%\ti#DA\u0001\n\u0013\tyCA\u000bQI\u001a$V-\u001c9GS2,G+Y:l\u001fV$\b/\u001e;\u000b\u0005U1\u0012a\u00019eM*\u0011q\u0003G\u0001\u0005kRLGNC\u0001\u001a\u0003\u0011\u0019w\u000eZ3\u0004\u0001M!\u0001\u0001\b\u0015/!\tib%D\u0001\u001f\u0015\ty\u0002%\u0001\u0004pkR\u0004X\u000f\u001e\u0006\u0003C\t\nQ!\\8eK2T!a\t\u0013\u0002\u000bM,'\u000eZ1\u000b\u0003\u0015\n1a\u001c:h\u0013\t9cD\u0001\bGS2,G+Y:l\u001fV$\b/\u001e;\u0011\u0005%bS\"\u0001\u0016\u000b\u0003-\nQa]2bY\u0006L!!\f\u0016\u0003\u000fA\u0013x\u000eZ;diB\u0011qf\u000e\b\u0003aUr!!\r\u001b\u000e\u0003IR!a\r\u000e\u0002\rq\u0012xn\u001c;?\u0013\u0005Y\u0013B\u0001\u001c+\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001O\u001d\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005YR\u0013a\u00033fgRLg.\u0019;j_:,\u0012\u0001\u0010\t\u0003{\tk\u0011A\u0010\u0006\u0003\u007f\u0001\u000b!![8\u000b\u0003\u0005\u000bAA[1wC&\u00111I\u0010\u0002\u0005\r&dW-\u0001\u0007eKN$\u0018N\\1uS>t\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003\u000f&\u0003\"\u0001\u0013\u0001\u000e\u0003QAQAO\u0002A\u0002q\nAaY8qsR\u0011q\t\u0014\u0005\bu\u0011\u0001\n\u00111\u0001=\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012a\u0014\u0016\u0003yA[\u0013!\u0015\t\u0003%^k\u0011a\u0015\u0006\u0003)V\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005YS\u0013AC1o]>$\u0018\r^5p]&\u0011\u0001l\u0015\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001\\!\tav,D\u0001^\u0015\tq\u0006)\u0001\u0003mC:<\u0017B\u00011^\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\t1\r\u0005\u0002*I&\u0011QM\u000b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003Q.\u0004\"!K5\n\u0005)T#aA!os\"9A\u000eCA\u0001\u0002\u0004\u0019\u0017a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001p!\r\u00018\u000f[\u0007\u0002c*\u0011!OK\u0001\u000bG>dG.Z2uS>t\u0017B\u0001;r\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005]T\bCA\u0015y\u0013\tI(FA\u0004C_>dW-\u00198\t\u000f1T\u0011\u0011!a\u0001Q\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\tYV\u0010C\u0004m\u0017\u0005\u0005\t\u0019A2\u0002+A#g\rV3na\u001aKG.\u001a+bg.|U\u000f\u001e9viB\u0011\u0001*D\n\u0006\u001b\u0005\r\u0011q\u0002\t\u0007\u0003\u000b\tY\u0001P$\u000e\u0005\u0005\u001d!bAA\u0005U\u00059!/\u001e8uS6,\u0017\u0002BA\u0007\u0003\u000f\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\ri\u0014\u0011C\u0005\u0003qy\"\u0012a`\u0001\ti>\u001cFO]5oOR\t1,A\u0003baBd\u0017\u0010F\u0002H\u0003;AQA\u000f\tA\u0002q\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002$\u0005%\u0002\u0003B\u0015\u0002&qJ1!a\n+\u0005\u0019y\u0005\u000f^5p]\"A\u00111F\t\u0002\u0002\u0003\u0007q)A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\r\u0011\u0007q\u000b\u0019$C\u0002\u00026u\u0013aa\u00142kK\u000e$\b")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PdfTempFileTaskOutput.class */
public class PdfTempFileTaskOutput extends FileTaskOutput implements Product, Serializable {
    private final File destination;

    public static Option<File> unapply(final PdfTempFileTaskOutput x$0) {
        return PdfTempFileTaskOutput$.MODULE$.unapply(x$0);
    }

    public static PdfTempFileTaskOutput apply(final File destination) {
        return PdfTempFileTaskOutput$.MODULE$.apply(destination);
    }

    public static <A> Function1<File, A> andThen(final Function1<PdfTempFileTaskOutput, A> g) {
        return PdfTempFileTaskOutput$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, PdfTempFileTaskOutput> compose(final Function1<A, File> g) {
        return PdfTempFileTaskOutput$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public File destination() {
        return this.destination;
    }

    public PdfTempFileTaskOutput copy(final File destination) {
        return new PdfTempFileTaskOutput(destination);
    }

    public File copy$default$1() {
        return destination();
    }

    public String productPrefix() {
        return "PdfTempFileTaskOutput";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return destination();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof PdfTempFileTaskOutput;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "destination";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PdfTempFileTaskOutput(final File destination) {
        super(destination);
        this.destination = destination;
        Product.$init$(this);
    }
}
