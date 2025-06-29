package code.sejda.tasks.edit;

import java.awt.Color;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/EditHighlightOperation$.class */
public final class EditHighlightOperation$ extends AbstractFunction3<Object, Object, Color, EditHighlightOperation> implements Serializable {
    public static final EditHighlightOperation$ MODULE$ = new EditHighlightOperation$();

    public final String toString() {
        return "EditHighlightOperation";
    }

    public EditHighlightOperation apply(final int pageNumber, final int id, final Color color) {
        return new EditHighlightOperation(pageNumber, id, color);
    }

    public Option<Tuple3<Object, Object, Color>> unapply(final EditHighlightOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple3(BoxesRunTime.boxToInteger(x$0.pageNumber()), BoxesRunTime.boxToInteger(x$0.id()), x$0.color()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(EditHighlightOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3) {
        return apply(BoxesRunTime.unboxToInt(v1), BoxesRunTime.unboxToInt(v2), (Color) v3);
    }

    private EditHighlightOperation$() {
    }
}
