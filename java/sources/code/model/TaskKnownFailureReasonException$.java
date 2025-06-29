package code.model;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: exceptions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TaskKnownFailureReasonException$.class */
public final class TaskKnownFailureReasonException$ extends AbstractFunction1<String, TaskKnownFailureReasonException> implements Serializable {
    public static final TaskKnownFailureReasonException$ MODULE$ = new TaskKnownFailureReasonException$();

    public final String toString() {
        return "TaskKnownFailureReasonException";
    }

    public TaskKnownFailureReasonException apply(final String msg) {
        return new TaskKnownFailureReasonException(msg);
    }

    public Option<String> unapply(final TaskKnownFailureReasonException x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.msg());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TaskKnownFailureReasonException$.class);
    }

    private TaskKnownFailureReasonException$() {
    }
}
