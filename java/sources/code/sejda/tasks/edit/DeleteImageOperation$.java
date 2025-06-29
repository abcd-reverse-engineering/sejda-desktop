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
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeleteImageOperation$.class */
public final class DeleteImageOperation$ extends AbstractFunction2<Object, String, DeleteImageOperation> implements Serializable {
    public static final DeleteImageOperation$ MODULE$ = new DeleteImageOperation$();

    public final String toString() {
        return "DeleteImageOperation";
    }

    public DeleteImageOperation apply(final int pageNumber, final String objId) {
        return new DeleteImageOperation(pageNumber, objId);
    }

    public Option<Tuple2<Object, String>> unapply(final DeleteImageOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2(BoxesRunTime.boxToInteger(x$0.pageNumber()), x$0.objId()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DeleteImageOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2) {
        return apply(BoxesRunTime.unboxToInt(v1), (String) v2);
    }

    private DeleteImageOperation$() {
    }
}
