package code.model;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.runtime.AbstractFunction4;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: exceptions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TooManyConcurrentTasksException$.class */
public final class TooManyConcurrentTasksException$ extends AbstractFunction4<String, Object, Object, String, TooManyConcurrentTasksException> implements Serializable {
    public static final TooManyConcurrentTasksException$ MODULE$ = new TooManyConcurrentTasksException$();

    public String $lessinit$greater$default$4() {
        return "";
    }

    public final String toString() {
        return "TooManyConcurrentTasksException";
    }

    public TooManyConcurrentTasksException apply(final String client, final long usage, final long limit, final String debug) {
        return new TooManyConcurrentTasksException(client, usage, limit, debug);
    }

    public String apply$default$4() {
        return "";
    }

    public Option<Tuple4<String, Object, Object, String>> unapply(final TooManyConcurrentTasksException x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple4(x$0.client(), BoxesRunTime.boxToLong(x$0.usage()), BoxesRunTime.boxToLong(x$0.limit()), x$0.debug()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TooManyConcurrentTasksException$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4) {
        return apply((String) v1, BoxesRunTime.unboxToLong(v2), BoxesRunTime.unboxToLong(v3), (String) v4);
    }

    private TooManyConcurrentTasksException$() {
    }
}
