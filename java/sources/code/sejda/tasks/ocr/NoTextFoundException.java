package code.sejda.tasks.ocr;

import org.sejda.model.exception.TaskException;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: OcrTask.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005=c\u0001B\u000b\u0017\u0001~A\u0001\"\u0010\u0001\u0003\u0016\u0004%\tA\u0010\u0005\t\u000f\u0002\u0011\t\u0012)A\u0005\u007f!)\u0001\n\u0001C\u0001\u0013\"9Q\nAA\u0001\n\u0003q\u0005b\u0002)\u0001#\u0003%\t!\u0015\u0005\b9\u0002\t\t\u0011\"\u0011^\u0011\u001d)\u0007!!A\u0005\u0002\u0019DqA\u001b\u0001\u0002\u0002\u0013\u00051\u000eC\u0004r\u0001\u0005\u0005I\u0011\t:\t\u000fe\u0004\u0011\u0011!C\u0001u\"Aq\u0010AA\u0001\n\u0003\n\t\u0001C\u0005\u0002\u0006\u0001\t\t\u0011\"\u0011\u0002\b!I\u0011\u0011\u0002\u0001\u0002\u0002\u0013\u0005\u00131B\u0004\n\u0003\u001f1\u0012\u0011!E\u0001\u0003#1\u0001\"\u0006\f\u0002\u0002#\u0005\u00111\u0003\u0005\u0007\u0011>!\t!a\u000b\t\u0013\u00055r\"!A\u0005F\u0005=\u0002\"CA\u0019\u001f\u0005\u0005I\u0011QA\u001a\u0011%\t9dDA\u0001\n\u0003\u000bI\u0004C\u0005\u0002F=\t\t\u0011\"\u0003\u0002H\t!bj\u001c+fqR4u.\u001e8e\u000bb\u001cW\r\u001d;j_:T!a\u0006\r\u0002\u0007=\u001c'O\u0003\u0002\u001a5\u0005)A/Y:lg*\u00111\u0004H\u0001\u0006g\u0016TG-\u0019\u0006\u0002;\u0005!1m\u001c3f\u0007\u0001\u0019B\u0001\u0001\u0011,cA\u0011\u0011%K\u0007\u0002E)\u00111\u0005J\u0001\nKb\u001cW\r\u001d;j_:T!!\n\u0014\u0002\u000b5|G-\u001a7\u000b\u0005m9#\"\u0001\u0015\u0002\u0007=\u0014x-\u0003\u0002+E\tiA+Y:l\u000bb\u001cW\r\u001d;j_:\u0004\"\u0001L\u0018\u000e\u00035R\u0011AL\u0001\u0006g\u000e\fG.Y\u0005\u0003a5\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u00023u9\u00111\u0007\u000f\b\u0003i]j\u0011!\u000e\u0006\u0003my\ta\u0001\u0010:p_Rt\u0014\"\u0001\u0018\n\u0005ej\u0013a\u00029bG.\fw-Z\u0005\u0003wq\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!!O\u0017\u0002\u00075\u001cx-F\u0001@!\t\u0001EI\u0004\u0002B\u0005B\u0011A'L\u0005\u0003\u00076\na\u0001\u0015:fI\u00164\u0017BA#G\u0005\u0019\u0019FO]5oO*\u00111)L\u0001\u0005[N<\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003\u00152\u0003\"a\u0013\u0001\u000e\u0003YAQ!P\u0002A\u0002}\nAaY8qsR\u0011!j\u0014\u0005\b{\u0011\u0001\n\u00111\u0001@\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012A\u0015\u0016\u0003\u007fM[\u0013\u0001\u0016\t\u0003+jk\u0011A\u0016\u0006\u0003/b\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005ek\u0013AC1o]>$\u0018\r^5p]&\u00111L\u0016\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001_!\tyF-D\u0001a\u0015\t\t'-\u0001\u0003mC:<'\"A2\u0002\t)\fg/Y\u0005\u0003\u000b\u0002\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012a\u001a\t\u0003Y!L!![\u0017\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u00051|\u0007C\u0001\u0017n\u0013\tqWFA\u0002B]fDq\u0001\u001d\u0005\u0002\u0002\u0003\u0007q-A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002gB\u0019Ao\u001e7\u000e\u0003UT!A^\u0017\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002yk\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\tYh\u0010\u0005\u0002-y&\u0011Q0\f\u0002\b\u0005>|G.Z1o\u0011\u001d\u0001(\"!AA\u00021\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019a,a\u0001\t\u000fA\\\u0011\u0011!a\u0001O\u0006A\u0001.Y:i\u0007>$W\rF\u0001h\u0003\u0019)\u0017/^1mgR\u001910!\u0004\t\u000fAl\u0011\u0011!a\u0001Y\u0006!bj\u001c+fqR4u.\u001e8e\u000bb\u001cW\r\u001d;j_:\u0004\"aS\b\u0014\u000b=\t)\"!\t\u0011\r\u0005]\u0011QD K\u001b\t\tIBC\u0002\u0002\u001c5\nqA];oi&lW-\u0003\u0003\u0002 \u0005e!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!\u00111EA\u0015\u001b\t\t)CC\u0002\u0002(\t\f!![8\n\u0007m\n)\u0003\u0006\u0002\u0002\u0012\u0005AAo\\*ue&tw\rF\u0001_\u0003\u0015\t\u0007\u000f\u001d7z)\rQ\u0015Q\u0007\u0005\u0006{I\u0001\raP\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\tY$!\u0011\u0011\t1\nidP\u0005\u0004\u0003\u007fi#AB(qi&|g\u000e\u0003\u0005\u0002DM\t\t\u00111\u0001K\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u0013\u00022aXA&\u0013\r\ti\u0005\u0019\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/ocr/NoTextFoundException.class */
public class NoTextFoundException extends TaskException implements Product {
    private final String msg;

    public static Option<String> unapply(final NoTextFoundException x$0) {
        return NoTextFoundException$.MODULE$.unapply(x$0);
    }

    public static NoTextFoundException apply(final String msg) {
        return NoTextFoundException$.MODULE$.apply(msg);
    }

    public static <A> Function1<String, A> andThen(final Function1<NoTextFoundException, A> g) {
        return NoTextFoundException$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, NoTextFoundException> compose(final Function1<A, String> g) {
        return NoTextFoundException$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String msg() {
        return this.msg;
    }

    public NoTextFoundException copy(final String msg) {
        return new NoTextFoundException(msg);
    }

    public String copy$default$1() {
        return msg();
    }

    public String productPrefix() {
        return "NoTextFoundException";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return msg();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof NoTextFoundException;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "msg";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof NoTextFoundException) {
                NoTextFoundException noTextFoundException = (NoTextFoundException) x$1;
                String strMsg = msg();
                String strMsg2 = noTextFoundException.msg();
                if (strMsg != null ? strMsg.equals(strMsg2) : strMsg2 == null) {
                    if (noTextFoundException.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoTextFoundException(final String msg) {
        super(msg);
        this.msg = msg;
        Product.$init$(this);
    }
}
