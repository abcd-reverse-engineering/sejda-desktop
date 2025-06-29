package code.sejda.tasks.ocr;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: OcrTask.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/ocr/NoTextFoundException$.class */
public final class NoTextFoundException$ extends AbstractFunction1<String, NoTextFoundException> implements Serializable {
    public static final NoTextFoundException$ MODULE$ = new NoTextFoundException$();

    public final String toString() {
        return "NoTextFoundException";
    }

    public NoTextFoundException apply(final String msg) {
        return new NoTextFoundException(msg);
    }

    public Option<String> unapply(final NoTextFoundException x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.msg());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(NoTextFoundException$.class);
    }

    private NoTextFoundException$() {
    }
}
