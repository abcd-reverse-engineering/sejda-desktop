package code.service;

import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: exceptions.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005]b\u0001B\u000b\u0017\u0001nA\u0001\"\r\u0001\u0003\u0016\u0004%\tA\r\u0005\tw\u0001\u0011\t\u0012)A\u0005g!)A\b\u0001C\u0001{!9\u0011\tAA\u0001\n\u0003\u0011\u0005b\u0002#\u0001#\u0003%\t!\u0012\u0005\b!\u0002\t\t\u0011\"\u0011R\u0011\u001dI\u0006!!A\u0005\u0002iCqA\u0018\u0001\u0002\u0002\u0013\u0005q\fC\u0004f\u0001\u0005\u0005I\u0011\t4\t\u000f5\u0004\u0011\u0011!C\u0001]\"91\u000fAA\u0001\n\u0003\"\bb\u0002<\u0001\u0003\u0003%\te\u001e\u0005\bq\u0002\t\t\u0011\"\u0011z\u000f\u001dYh#!A\t\u0002q4q!\u0006\f\u0002\u0002#\u0005Q\u0010\u0003\u0004=\u001f\u0011\u0005\u00111\u0003\u0005\n\u0003+y\u0011\u0011!C#\u0003/A\u0011\"!\u0007\u0010\u0003\u0003%\t)a\u0007\t\u0013\u0005}q\"!A\u0005\u0002\u0006\u0005\u0002\"CA\u0017\u001f\u0005\u0005I\u0011BA\u0018\u0005U!\u0016m]6O_R4u.\u001e8e\u000bb\u001cW\r\u001d;j_:T!a\u0006\r\u0002\u000fM,'O^5dK*\t\u0011$\u0001\u0003d_\u0012,7\u0001A\n\u0005\u0001qQc\u0006\u0005\u0002\u001eO9\u0011a\u0004\n\b\u0003?\tj\u0011\u0001\t\u0006\u0003Ci\ta\u0001\u0010:p_Rt\u0014\"A\u0012\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u00152\u0013a\u00029bG.\fw-\u001a\u0006\u0002G%\u0011\u0001&\u000b\u0002\u0011%VtG/[7f\u000bb\u001cW\r\u001d;j_:T!!\n\u0014\u0011\u0005-bS\"\u0001\u0014\n\u000552#a\u0002)s_\u0012,8\r\u001e\t\u0003;=J!\u0001M\u0015\u0003\u0019M+'/[1mSj\f'\r\\3\u0002\u0005%$W#A\u001a\u0011\u0005QBdBA\u001b7!\tyb%\u0003\u00028M\u00051\u0001K]3eK\u001aL!!\u000f\u001e\u0003\rM#(/\u001b8h\u0015\t9d%A\u0002jI\u0002\na\u0001P5oSRtDC\u0001 A!\ty\u0004!D\u0001\u0017\u0011\u0015\t4\u00011\u00014\u0003\u0011\u0019w\u000e]=\u0015\u0005y\u001a\u0005bB\u0019\u0005!\u0003\u0005\raM\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u00051%FA\u001aHW\u0005A\u0005CA%O\u001b\u0005Q%BA&M\u0003%)hn\u00195fG.,GM\u0003\u0002NM\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005=S%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012A\u0015\t\u0003'bk\u0011\u0001\u0016\u0006\u0003+Z\u000bA\u0001\\1oO*\tq+\u0001\u0003kCZ\f\u0017BA\u001dU\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005Y\u0006CA\u0016]\u0013\tifEA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002aGB\u00111&Y\u0005\u0003E\u001a\u00121!\u00118z\u0011\u001d!\u0007\"!AA\u0002m\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A4\u0011\u0007!\\\u0007-D\u0001j\u0015\tQg%\u0001\u0006d_2dWm\u0019;j_:L!\u0001\\5\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003_J\u0004\"a\u000b9\n\u0005E4#a\u0002\"p_2,\u0017M\u001c\u0005\bI*\t\t\u00111\u0001a\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0005I+\bb\u00023\f\u0003\u0003\u0005\raW\u0001\tQ\u0006\u001c\bnQ8eKR\t1,\u0001\u0004fcV\fGn\u001d\u000b\u0003_jDq\u0001Z\u0007\u0002\u0002\u0003\u0007\u0001-A\u000bUCN\\gj\u001c;G_VtG-\u0012=dKB$\u0018n\u001c8\u0011\u0005}z1\u0003B\b\u007f\u0003\u0013\u0001Ra`A\u0003gyj!!!\u0001\u000b\u0007\u0005\ra%A\u0004sk:$\u0018.\\3\n\t\u0005\u001d\u0011\u0011\u0001\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BA\u0006\u0003#i!!!\u0004\u000b\u0007\u0005=a+\u0001\u0002j_&\u0019\u0001'!\u0004\u0015\u0003q\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002%\u0006)\u0011\r\u001d9msR\u0019a(!\b\t\u000bE\u0012\u0002\u0019A\u001a\u0002\u000fUt\u0017\r\u001d9msR!\u00111EA\u0015!\u0011Y\u0013QE\u001a\n\u0007\u0005\u001dbE\u0001\u0004PaRLwN\u001c\u0005\t\u0003W\u0019\u0012\u0011!a\u0001}\u0005\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005E\u0002cA*\u00024%\u0019\u0011Q\u0007+\u0003\r=\u0013'.Z2u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/TaskNotFoundException.class */
public class TaskNotFoundException extends RuntimeException implements Product {
    private final String id;

    public static Option<String> unapply(final TaskNotFoundException x$0) {
        return TaskNotFoundException$.MODULE$.unapply(x$0);
    }

    public static TaskNotFoundException apply(final String id) {
        return TaskNotFoundException$.MODULE$.apply(id);
    }

    public static <A> Function1<String, A> andThen(final Function1<TaskNotFoundException, A> g) {
        return TaskNotFoundException$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, TaskNotFoundException> compose(final Function1<A, String> g) {
        return TaskNotFoundException$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String id() {
        return this.id;
    }

    public TaskNotFoundException copy(final String id) {
        return new TaskNotFoundException(id);
    }

    public String copy$default$1() {
        return id();
    }

    public String productPrefix() {
        return "TaskNotFoundException";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return id();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof TaskNotFoundException;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "id";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof TaskNotFoundException) {
                TaskNotFoundException taskNotFoundException = (TaskNotFoundException) x$1;
                String strId = id();
                String strId2 = taskNotFoundException.id();
                if (strId != null ? strId.equals(strId2) : strId2 == null) {
                    if (taskNotFoundException.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TaskNotFoundException(final String id) {
        super(new StringBuilder(16).append("Task not found: ").append(id).toString());
        this.id = id;
        Product.$init$(this);
    }
}
