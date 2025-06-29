package code.service;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: exceptions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/TaskNotFoundException$.class */
public final class TaskNotFoundException$ extends AbstractFunction1<String, TaskNotFoundException> implements Serializable {
    public static final TaskNotFoundException$ MODULE$ = new TaskNotFoundException$();

    public final String toString() {
        return "TaskNotFoundException";
    }

    public TaskNotFoundException apply(final String id) {
        return new TaskNotFoundException(id);
    }

    public Option<String> unapply(final TaskNotFoundException x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.id());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TaskNotFoundException$.class);
    }

    private TaskNotFoundException$() {
    }
}
