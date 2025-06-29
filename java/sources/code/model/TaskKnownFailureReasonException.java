package code.model;

import org.sejda.model.exception.TaskException;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: exceptions.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\u001dc\u0001B\u000b\u0017\u0001nA\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t\u0007\u0002\u0011\t\u0012)A\u0005w!)A\t\u0001C\u0001\u000b\"9\u0011\nAA\u0001\n\u0003Q\u0005b\u0002'\u0001#\u0003%\t!\u0014\u0005\b1\u0002\t\t\u0011\"\u0011Z\u0011\u001d\t\u0007!!A\u0005\u0002\tDqA\u001a\u0001\u0002\u0002\u0013\u0005q\rC\u0004n\u0001\u0005\u0005I\u0011\t8\t\u000fU\u0004\u0011\u0011!C\u0001m\"91\u0010AA\u0001\n\u0003b\bb\u0002@\u0001\u0003\u0003%\te \u0005\n\u0003\u0003\u0001\u0011\u0011!C!\u0003\u00079\u0011\"a\u0002\u0017\u0003\u0003E\t!!\u0003\u0007\u0011U1\u0012\u0011!E\u0001\u0003\u0017Aa\u0001R\b\u0005\u0002\u0005\r\u0002\"CA\u0013\u001f\u0005\u0005IQIA\u0014\u0011%\tIcDA\u0001\n\u0003\u000bY\u0003C\u0005\u00020=\t\t\u0011\"!\u00022!I\u0011QH\b\u0002\u0002\u0013%\u0011q\b\u0002 )\u0006\u001c8n\u00138po:4\u0015-\u001b7ve\u0016\u0014V-Y:p]\u0016C8-\u001a9uS>t'BA\f\u0019\u0003\u0015iw\u000eZ3m\u0015\u0005I\u0012\u0001B2pI\u0016\u001c\u0001a\u0005\u0003\u00019\u001dj\u0003CA\u000f&\u001b\u0005q\"BA\u0010!\u0003%)\u0007pY3qi&|gN\u0003\u0002\u0018C)\u0011!eI\u0001\u0006g\u0016TG-\u0019\u0006\u0002I\u0005\u0019qN]4\n\u0005\u0019r\"!\u0004+bg.,\u0005pY3qi&|g\u000e\u0005\u0002)W5\t\u0011FC\u0001+\u0003\u0015\u00198-\u00197b\u0013\ta\u0013FA\u0004Qe>$Wo\u0019;\u0011\u000592dBA\u00185\u001d\t\u00014'D\u00012\u0015\t\u0011$$\u0001\u0004=e>|GOP\u0005\u0002U%\u0011Q'K\u0001\ba\u0006\u001c7.Y4f\u0013\t9\u0004H\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00026S\u0005\u0019Qn]4\u0016\u0003m\u0002\"\u0001\u0010!\u000f\u0005ur\u0004C\u0001\u0019*\u0013\ty\u0014&\u0001\u0004Qe\u0016$WMZ\u0005\u0003\u0003\n\u0013aa\u0015;sS:<'BA *\u0003\u0011i7o\u001a\u0011\u0002\rqJg.\u001b;?)\t1\u0005\n\u0005\u0002H\u00015\ta\u0003C\u0003:\u0007\u0001\u00071(\u0001\u0003d_BLHC\u0001$L\u0011\u001dID\u0001%AA\u0002m\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001OU\tYtjK\u0001Q!\t\tf+D\u0001S\u0015\t\u0019F+A\u0005v]\u000eDWmY6fI*\u0011Q+K\u0001\u000bC:tw\u000e^1uS>t\u0017BA,S\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003i\u0003\"a\u00171\u000e\u0003qS!!\u00180\u0002\t1\fgn\u001a\u0006\u0002?\u0006!!.\u0019<b\u0013\t\tE,\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001d!\tAC-\u0003\u0002fS\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011\u0001n\u001b\t\u0003Q%L!A[\u0015\u0003\u0007\u0005s\u0017\u0010C\u0004m\u0011\u0005\u0005\t\u0019A2\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005y\u0007c\u00019tQ6\t\u0011O\u0003\u0002sS\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005Q\f(\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"a\u001e>\u0011\u0005!B\u0018BA=*\u0005\u001d\u0011un\u001c7fC:Dq\u0001\u001c\u0006\u0002\u0002\u0003\u0007\u0001.\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GC\u0001.~\u0011\u001da7\"!AA\u0002\r\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002G\u00061Q-];bYN$2a^A\u0003\u0011\u001daW\"!AA\u0002!\fq\u0004V1tW.swn\u001e8GC&dWO]3SK\u0006\u001cxN\\#yG\u0016\u0004H/[8o!\t9ubE\u0003\u0010\u0003\u001b\tI\u0002\u0005\u0004\u0002\u0010\u0005U1HR\u0007\u0003\u0003#Q1!a\u0005*\u0003\u001d\u0011XO\u001c;j[\u0016LA!a\u0006\u0002\u0012\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u0005m\u0011\u0011E\u0007\u0003\u0003;Q1!a\b_\u0003\tIw.C\u00028\u0003;!\"!!\u0003\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012AW\u0001\u0006CB\u0004H.\u001f\u000b\u0004\r\u00065\u0002\"B\u001d\u0013\u0001\u0004Y\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0003g\tI\u0004\u0005\u0003)\u0003kY\u0014bAA\u001cS\t1q\n\u001d;j_:D\u0001\"a\u000f\u0014\u0003\u0003\u0005\rAR\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA!!\rY\u00161I\u0005\u0004\u0003\u000bb&AB(cU\u0016\u001cG\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TaskKnownFailureReasonException.class */
public class TaskKnownFailureReasonException extends TaskException implements Product {
    private final String msg;

    public static Option<String> unapply(final TaskKnownFailureReasonException x$0) {
        return TaskKnownFailureReasonException$.MODULE$.unapply(x$0);
    }

    public static TaskKnownFailureReasonException apply(final String msg) {
        return TaskKnownFailureReasonException$.MODULE$.apply(msg);
    }

    public static <A> Function1<String, A> andThen(final Function1<TaskKnownFailureReasonException, A> g) {
        return TaskKnownFailureReasonException$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, TaskKnownFailureReasonException> compose(final Function1<A, String> g) {
        return TaskKnownFailureReasonException$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String msg() {
        return this.msg;
    }

    public TaskKnownFailureReasonException copy(final String msg) {
        return new TaskKnownFailureReasonException(msg);
    }

    public String copy$default$1() {
        return msg();
    }

    public String productPrefix() {
        return "TaskKnownFailureReasonException";
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
        return x$1 instanceof TaskKnownFailureReasonException;
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
            if (x$1 instanceof TaskKnownFailureReasonException) {
                TaskKnownFailureReasonException taskKnownFailureReasonException = (TaskKnownFailureReasonException) x$1;
                String strMsg = msg();
                String strMsg2 = taskKnownFailureReasonException.msg();
                if (strMsg != null ? strMsg.equals(strMsg2) : strMsg2 == null) {
                    if (taskKnownFailureReasonException.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TaskKnownFailureReasonException(final String msg) {
        super(msg);
        this.msg = msg;
        Product.$init$(this);
    }
}
