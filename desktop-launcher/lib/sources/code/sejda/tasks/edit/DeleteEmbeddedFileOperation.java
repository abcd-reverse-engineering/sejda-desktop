package code.sejda.tasks.edit;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\u0005c\u0001\u0002\f\u0018\u0001\u0002B\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\t\u0001\u0002\u0011\t\u0012)A\u0005q!)\u0011\t\u0001C\u0001\u0005\"9a\tAA\u0001\n\u00039\u0005bB%\u0001#\u0003%\tA\u0013\u0005\b+\u0002\t\t\u0011\"\u0011W\u0011\u001dq\u0006!!A\u0005\u0002}Cqa\u0019\u0001\u0002\u0002\u0013\u0005A\rC\u0004k\u0001\u0005\u0005I\u0011I6\t\u000fI\u0004\u0011\u0011!C\u0001g\"9\u0001\u0010AA\u0001\n\u0003J\bbB>\u0001\u0003\u0003%\t\u0005 \u0005\b{\u0002\t\t\u0011\"\u0011\u007f\u0011!y\b!!A\u0005B\u0005\u0005q!CA\u0003/\u0005\u0005\t\u0012AA\u0004\r!1r#!A\t\u0002\u0005%\u0001BB!\u0011\t\u0003\t\t\u0003C\u0004~!\u0005\u0005IQ\t@\t\u0013\u0005\r\u0002#!A\u0005\u0002\u0006\u0015\u0002\"CA\u0015!\u0005\u0005I\u0011QA\u0016\u0011%\t9\u0004EA\u0001\n\u0013\tIDA\u000eEK2,G/Z#nE\u0016$G-\u001a3GS2,w\n]3sCRLwN\u001c\u0006\u00031e\tA!\u001a3ji*\u0011!dG\u0001\u0006i\u0006\u001c8n\u001d\u0006\u00039u\tQa]3kI\u0006T\u0011AH\u0001\u0005G>$Wm\u0001\u0001\u0014\t\u0001\tsE\u000b\t\u0003E\u0015j\u0011a\t\u0006\u0002I\u0005)1oY1mC&\u0011ae\t\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\tB\u0013BA\u0015$\u0005\u001d\u0001&o\u001c3vGR\u0004\"aK\u001a\u000f\u00051\ndBA\u00171\u001b\u0005q#BA\u0018 \u0003\u0019a$o\\8u}%\tA%\u0003\u00023G\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u001b6\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t\u00114%\u0001\u0005gS2,g.Y7f+\u0005A\u0004CA\u001d>\u001d\tQ4\b\u0005\u0002.G%\u0011AhI\u0001\u0007!J,G-\u001a4\n\u0005yz$AB*ue&twM\u0003\u0002=G\u0005Ia-\u001b7f]\u0006lW\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\r+\u0005C\u0001#\u0001\u001b\u00059\u0002\"\u0002\u001c\u0004\u0001\u0004A\u0014\u0001B2paf$\"a\u0011%\t\u000fY\"\u0001\u0013!a\u0001q\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A&+\u0005ab5&A'\u0011\u00059\u001bV\"A(\u000b\u0005A\u000b\u0016!C;oG\",7m[3e\u0015\t\u00116%\u0001\u0006b]:|G/\u0019;j_:L!\u0001V(\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002/B\u0011\u0001,X\u0007\u00023*\u0011!lW\u0001\u0005Y\u0006twMC\u0001]\u0003\u0011Q\u0017M^1\n\u0005yJ\u0016\u0001\u00049s_\u0012,8\r^!sSRLX#\u00011\u0011\u0005\t\n\u0017B\u00012$\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\t)\u0007\u000e\u0005\u0002#M&\u0011qm\t\u0002\u0004\u0003:L\bbB5\t\u0003\u0003\u0005\r\u0001Y\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u00031\u00042!\u001c9f\u001b\u0005q'BA8$\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003c:\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0011Ao\u001e\t\u0003EUL!A^\u0012\u0003\u000f\t{w\u000e\\3b]\"9\u0011NCA\u0001\u0002\u0004)\u0017A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$\"a\u0016>\t\u000f%\\\u0011\u0011!a\u0001A\u0006A\u0001.Y:i\u0007>$W\rF\u0001a\u0003!!xn\u0015;sS:<G#A,\u0002\r\u0015\fX/\u00197t)\r!\u00181\u0001\u0005\bS:\t\t\u00111\u0001f\u0003m!U\r\\3uK\u0016k'-\u001a3eK\u00124\u0015\u000e\\3Pa\u0016\u0014\u0018\r^5p]B\u0011A\tE\n\u0006!\u0005-\u0011q\u0003\t\u0007\u0003\u001b\t\u0019\u0002O\"\u000e\u0005\u0005=!bAA\tG\u00059!/\u001e8uS6,\u0017\u0002BA\u000b\u0003\u001f\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\tI\"a\b\u000e\u0005\u0005m!bAA\u000f7\u0006\u0011\u0011n\\\u0005\u0004i\u0005mACAA\u0004\u0003\u0015\t\u0007\u000f\u001d7z)\r\u0019\u0015q\u0005\u0005\u0006mM\u0001\r\u0001O\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\ti#a\r\u0011\t\t\ny\u0003O\u0005\u0004\u0003c\u0019#AB(qi&|g\u000e\u0003\u0005\u00026Q\t\t\u00111\u0001D\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003w\u00012\u0001WA\u001f\u0013\r\ty$\u0017\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeleteEmbeddedFileOperation.class */
public class DeleteEmbeddedFileOperation implements Product, Serializable {
    private final String filename;

    public static Option<String> unapply(final DeleteEmbeddedFileOperation x$0) {
        return DeleteEmbeddedFileOperation$.MODULE$.unapply(x$0);
    }

    public static DeleteEmbeddedFileOperation apply(final String filename) {
        return DeleteEmbeddedFileOperation$.MODULE$.apply(filename);
    }

    public static <A> Function1<String, A> andThen(final Function1<DeleteEmbeddedFileOperation, A> g) {
        return DeleteEmbeddedFileOperation$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, DeleteEmbeddedFileOperation> compose(final Function1<A, String> g) {
        return DeleteEmbeddedFileOperation$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String filename() {
        return this.filename;
    }

    public DeleteEmbeddedFileOperation copy(final String filename) {
        return new DeleteEmbeddedFileOperation(filename);
    }

    public String copy$default$1() {
        return filename();
    }

    public String productPrefix() {
        return "DeleteEmbeddedFileOperation";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return filename();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof DeleteEmbeddedFileOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "filename";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof DeleteEmbeddedFileOperation) {
                DeleteEmbeddedFileOperation deleteEmbeddedFileOperation = (DeleteEmbeddedFileOperation) x$1;
                String strFilename = filename();
                String strFilename2 = deleteEmbeddedFileOperation.filename();
                if (strFilename != null ? strFilename.equals(strFilename2) : strFilename2 == null) {
                    if (deleteEmbeddedFileOperation.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public DeleteEmbeddedFileOperation(final String filename) {
        this.filename = filename;
        Product.$init$(this);
    }
}
