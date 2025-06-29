package code.sejda.tasks.common;

import code.util.Loggable;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.task.BaseTask;
import org.slf4j.Logger;
import scala.Predef$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;

/* compiled from: BaseBaseTask.scala */
@ScalaSignature(bytes = "\u0006\u0005y3Qa\u0002\u0005\u0002\u0002EAQa\u000e\u0001\u0005\u0002aBqa\u000f\u0001A\u0002\u0013%A\bC\u0004Q\u0001\u0001\u0007I\u0011B)\t\r]\u0003\u0001\u0015)\u0003>\u0011\u0015A\u0006\u0001\"\u0001Z\u0011\u0015a\u0006\u0001\"\u0011^\u00051\u0011\u0015m]3CCN,G+Y:l\u0015\tI!\"\u0001\u0004d_6lwN\u001c\u0006\u0003\u00171\tQ\u0001^1tWNT!!\u0004\b\u0002\u000bM,'\u000eZ1\u000b\u0003=\tAaY8eK\u000e\u0001QC\u0001\n!'\r\u00011#\r\t\u0004)qqR\"A\u000b\u000b\u0005Y9\u0012\u0001\u0002;bg.T!\u0001G\r\u0002\u000b5|G-\u001a7\u000b\u00055Q\"\"A\u000e\u0002\u0007=\u0014x-\u0003\u0002\u001e+\tA!)Y:f)\u0006\u001c8\u000e\u0005\u0002 A1\u0001A!B\u0011\u0001\u0005\u0004\u0011#!\u0001+\u0012\u0005\rJ\u0003C\u0001\u0013(\u001b\u0005)#\"\u0001\u0014\u0002\u000bM\u001c\u0017\r\\1\n\u0005!*#a\u0002(pi\"Lgn\u001a\t\u0003U=j\u0011a\u000b\u0006\u0003Y5\nAAY1tK*\u0011afF\u0001\na\u0006\u0014\u0018-\\3uKJL!\u0001M\u0016\u0003\u001dQ\u000b7o\u001b)be\u0006lW\r^3sgB\u0011!'N\u0007\u0002g)\u0011AGD\u0001\u0005kRLG.\u0003\u00027g\tAAj\\4hC\ndW-\u0001\u0004=S:LGO\u0010\u000b\u0002sA\u0019!\b\u0001\u0010\u000e\u0003!\t1#Z7jiR,G\rV1tW^\u000b'O\\5oON,\u0012!\u0010\t\u0004}\r+U\"A \u000b\u0005\u0001\u000b\u0015!C5n[V$\u0018M\u00197f\u0015\t\u0011U%\u0001\u0006d_2dWm\u0019;j_:L!\u0001R \u0003\u0007M+G\u000f\u0005\u0002G\u001b:\u0011qi\u0013\t\u0003\u0011\u0016j\u0011!\u0013\u0006\u0003\u0015B\ta\u0001\u0010:p_Rt\u0014B\u0001'&\u0003\u0019\u0001&/\u001a3fM&\u0011aj\u0014\u0002\u0007'R\u0014\u0018N\\4\u000b\u00051+\u0013aF3nSR$X\r\u001a+bg.<\u0016M\u001d8j]\u001e\u001cx\fJ3r)\t\u0011V\u000b\u0005\u0002%'&\u0011A+\n\u0002\u0005+:LG\u000fC\u0004W\u0007\u0005\u0005\t\u0019A\u001f\u0002\u0007a$\u0013'\u0001\u000bf[&$H/\u001a3UCN\\w+\u0019:oS:<7\u000fI\u0001\u0010K6LG\u000fV1tW^\u000b'O\\5oOR\u0011!K\u0017\u0005\u00067\u0016\u0001\r!R\u0001\u0002g\u0006AAo\\*ue&tw\rF\u0001F\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/BaseBaseTask.class */
public abstract class BaseBaseTask<T extends TaskParameters> extends BaseTask<T> implements Loggable {
    private Set<String> emittedTaskWarnings;
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.common.BaseBaseTask] */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$trans$0) {
                this.logger = logger();
                r0 = this;
                r0.bitmap$trans$0 = true;
            }
        }
        return this.logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !this.bitmap$trans$0 ? logger$lzycompute() : this.logger;
    }

    public BaseBaseTask() {
        Loggable.$init$(this);
        this.emittedTaskWarnings = (Set) Predef$.MODULE$.Set().apply(Nil$.MODULE$);
    }

    private Set<String> emittedTaskWarnings() {
        return this.emittedTaskWarnings;
    }

    private void emittedTaskWarnings_$eq(final Set<String> x$1) {
        this.emittedTaskWarnings = x$1;
    }

    public void emitTaskWarning(final String s) {
        if (emittedTaskWarnings().contains(s)) {
            return;
        }
        emittedTaskWarnings_$eq((Set) emittedTaskWarnings().$plus(s));
        logger().warn(s);
        ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(s);
    }

    public String toString() {
        return new StringBuilder(1).append(super.toString()).append(" ").append(System.getProperty("sejda.task.id")).toString();
    }
}
