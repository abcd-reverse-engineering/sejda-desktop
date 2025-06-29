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
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeleteLinkOperation$.class */
public final class DeleteLinkOperation$ extends AbstractFunction2<Object, Object, DeleteLinkOperation> implements Serializable {
    public static final DeleteLinkOperation$ MODULE$ = new DeleteLinkOperation$();

    public final String toString() {
        return "DeleteLinkOperation";
    }

    public DeleteLinkOperation apply(final int id, final int pageNumber) {
        return new DeleteLinkOperation(id, pageNumber);
    }

    public Option<Tuple2<Object, Object>> unapply(final DeleteLinkOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2.mcII.sp(x$0.id(), x$0.pageNumber()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DeleteLinkOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2) {
        return apply(BoxesRunTime.unboxToInt(v1), BoxesRunTime.unboxToInt(v2));
    }

    private DeleteLinkOperation$() {
    }
}
