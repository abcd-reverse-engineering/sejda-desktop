package code.sejda.tasks.edit;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/InsertPageOperation$.class */
public final class InsertPageOperation$ extends AbstractFunction1<Object, InsertPageOperation> implements Serializable {
    public static final InsertPageOperation$ MODULE$ = new InsertPageOperation$();

    public final String toString() {
        return "InsertPageOperation";
    }

    public InsertPageOperation apply(final int pageNumber) {
        return new InsertPageOperation(pageNumber);
    }

    public Option<Object> unapply(final InsertPageOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(BoxesRunTime.boxToInteger(x$0.pageNumber()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(InsertPageOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1) {
        return apply(BoxesRunTime.unboxToInt(v1));
    }

    private InsertPageOperation$() {
    }
}
