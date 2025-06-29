package code.model;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: exceptions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TouchException$.class */
public final class TouchException$ extends AbstractFunction2<String, Object, TouchException> implements Serializable {
    public static final TouchException$ MODULE$ = new TouchException$();

    public final String toString() {
        return "TouchException";
    }

    public TouchException apply(final String url, final int statusCode) {
        return new TouchException(url, statusCode);
    }

    public Option<Tuple2<String, Object>> unapply(final TouchException x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2(x$0.url(), BoxesRunTime.boxToInteger(x$0.statusCode())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TouchException$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2) {
        return apply((String) v1, BoxesRunTime.unboxToInt(v2));
    }

    private TouchException$() {
    }
}
