package code.service;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: exceptions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/TaskOutputTooLargeException$.class */
public final class TaskOutputTooLargeException$ extends AbstractFunction1<Object, TaskOutputTooLargeException> implements Serializable {
    public static final TaskOutputTooLargeException$ MODULE$ = new TaskOutputTooLargeException$();

    public final String toString() {
        return "TaskOutputTooLargeException";
    }

    public TaskOutputTooLargeException apply(final long bytes) {
        return new TaskOutputTooLargeException(bytes);
    }

    public Option<Object> unapply(final TaskOutputTooLargeException x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(BoxesRunTime.boxToLong(x$0.bytes()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TaskOutputTooLargeException$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1) {
        return apply(BoxesRunTime.unboxToLong(v1));
    }

    private TaskOutputTooLargeException$() {
    }
}
