package code.model;

import scala.Enumeration;
import scala.collection.immutable.$colon;
import scala.collection.immutable.Nil$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: Task.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TaskStatus$.class */
public final class TaskStatus$ extends Enumeration {
    public static final TaskStatus$ MODULE$ = new TaskStatus$();
    private static final Enumeration.Value Queued = MODULE$.Value();
    private static final Enumeration.Value Processing = MODULE$.Value();
    private static final Enumeration.Value Completed = MODULE$.Value();
    private static final Enumeration.Value Failed = MODULE$.Value();

    private Object writeReplace() {
        return new ModuleSerializationProxy(TaskStatus$.class);
    }

    private TaskStatus$() {
    }

    public Enumeration.Value Queued() {
        return Queued;
    }

    public Enumeration.Value Processing() {
        return Processing;
    }

    public Enumeration.Value Completed() {
        return Completed;
    }

    public Enumeration.Value Failed() {
        return Failed;
    }

    public Enumeration.Value fromString(final String s) {
        return (Enumeration.Value) new $colon.colon(Queued(), new $colon.colon(Processing(), new $colon.colon(Completed(), new $colon.colon(Failed(), Nil$.MODULE$)))).find(x$9 -> {
            return BoxesRunTime.boxToBoolean($anonfun$fromString$1(s, x$9));
        }).getOrElse(() -> {
            throw new RuntimeException(new StringBuilder(20).append("Unknown task status ").append(s).toString());
        });
    }

    public static final /* synthetic */ boolean $anonfun$fromString$1(final String s$1, final Enumeration.Value x$9) {
        String string = x$9.toString();
        return string != null ? string.equals(s$1) : s$1 == null;
    }
}
