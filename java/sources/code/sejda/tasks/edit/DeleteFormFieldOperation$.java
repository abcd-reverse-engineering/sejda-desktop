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
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeleteFormFieldOperation$.class */
public final class DeleteFormFieldOperation$ extends AbstractFunction2<String, Object, DeleteFormFieldOperation> implements Serializable {
    public static final DeleteFormFieldOperation$ MODULE$ = new DeleteFormFieldOperation$();

    public final String toString() {
        return "DeleteFormFieldOperation";
    }

    public DeleteFormFieldOperation apply(final String objId, final int pageNumber) {
        return new DeleteFormFieldOperation(objId, pageNumber);
    }

    public Option<Tuple2<String, Object>> unapply(final DeleteFormFieldOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2(x$0.objId(), BoxesRunTime.boxToInteger(x$0.pageNumber())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DeleteFormFieldOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2) {
        return apply((String) v1, BoxesRunTime.unboxToInt(v2));
    }

    private DeleteFormFieldOperation$() {
    }
}
