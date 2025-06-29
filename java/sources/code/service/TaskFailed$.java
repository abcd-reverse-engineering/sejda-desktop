package code.service;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: exceptions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/TaskFailed$.class */
public final class TaskFailed$ extends AbstractFunction1<String, TaskFailed> implements Serializable {
    public static final TaskFailed$ MODULE$ = new TaskFailed$();

    public final String toString() {
        return "TaskFailed";
    }

    public TaskFailed apply(final String msg) {
        return new TaskFailed(msg);
    }

    public Option<String> unapply(final TaskFailed x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.msg());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TaskFailed$.class);
    }

    private TaskFailed$() {
    }
}
