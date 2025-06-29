package code.service;

import java.io.Serializable;
import scala.runtime.AbstractFunction0;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: exceptions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/TaskTimedOutBefore$.class */
public final class TaskTimedOutBefore$ extends AbstractFunction0<TaskTimedOutBefore> implements Serializable {
    public static final TaskTimedOutBefore$ MODULE$ = new TaskTimedOutBefore$();

    public final String toString() {
        return "TaskTimedOutBefore";
    }

    /* renamed from: apply, reason: merged with bridge method [inline-methods] */
    public TaskTimedOutBefore m124apply() {
        return new TaskTimedOutBefore();
    }

    public boolean unapply(final TaskTimedOutBefore x$0) {
        return x$0 != null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TaskTimedOutBefore$.class);
    }

    private TaskTimedOutBefore$() {
    }
}
