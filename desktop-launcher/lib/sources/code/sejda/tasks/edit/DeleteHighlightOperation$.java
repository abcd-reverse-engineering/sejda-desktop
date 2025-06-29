package code.sejda.tasks.edit;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeleteHighlightOperation$.class */
public final class DeleteHighlightOperation$ extends AbstractFunction2<Object, Object, DeleteHighlightOperation> implements Serializable {
    public static final DeleteHighlightOperation$ MODULE$ = new DeleteHighlightOperation$();

    public final String toString() {
        return "DeleteHighlightOperation";
    }

    public DeleteHighlightOperation apply(final int pageNumber, final int id) {
        return new DeleteHighlightOperation(pageNumber, id);
    }

    public Option<Tuple2<Object, Object>> unapply(final DeleteHighlightOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2.mcII.sp(x$0.pageNumber(), x$0.id()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DeleteHighlightOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2) {
        return apply(BoxesRunTime.unboxToInt(v1), BoxesRunTime.unboxToInt(v2));
    }

    private DeleteHighlightOperation$() {
    }
}
