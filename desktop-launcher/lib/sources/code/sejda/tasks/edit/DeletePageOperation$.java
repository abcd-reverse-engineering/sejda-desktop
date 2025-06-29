package code.sejda.tasks.edit;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeletePageOperation$.class */
public final class DeletePageOperation$ extends AbstractFunction1<Object, DeletePageOperation> implements Serializable {
    public static final DeletePageOperation$ MODULE$ = new DeletePageOperation$();

    public final String toString() {
        return "DeletePageOperation";
    }

    public DeletePageOperation apply(final int pageNumber) {
        return new DeletePageOperation(pageNumber);
    }

    public Option<Object> unapply(final DeletePageOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(BoxesRunTime.boxToInteger(x$0.pageNumber()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DeletePageOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1) {
        return apply(BoxesRunTime.unboxToInt(v1));
    }

    private DeletePageOperation$() {
    }
}
