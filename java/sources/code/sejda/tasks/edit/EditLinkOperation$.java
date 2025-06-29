package code.sejda.tasks.edit;

import java.io.Serializable;
import org.sejda.model.RectangularBox;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple5;
import scala.collection.immutable.Set;
import scala.runtime.AbstractFunction5;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/EditLinkOperation$.class */
public final class EditLinkOperation$ extends AbstractFunction5<Object, Object, Set<RectangularBox>, String, LinkType, EditLinkOperation> implements Serializable {
    public static final EditLinkOperation$ MODULE$ = new EditLinkOperation$();

    public final String toString() {
        return "EditLinkOperation";
    }

    public EditLinkOperation apply(final int id, final int pageNumber, final Set<RectangularBox> boundingBoxes, final String href, final LinkType kind) {
        return new EditLinkOperation(id, pageNumber, boundingBoxes, href, kind);
    }

    public Option<Tuple5<Object, Object, Set<RectangularBox>, String, LinkType>> unapply(final EditLinkOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple5(BoxesRunTime.boxToInteger(x$0.id()), BoxesRunTime.boxToInteger(x$0.pageNumber()), x$0.boundingBoxes(), x$0.href(), x$0.kind()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(EditLinkOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5) {
        return apply(BoxesRunTime.unboxToInt(v1), BoxesRunTime.unboxToInt(v2), (Set<RectangularBox>) v3, (String) v4, (LinkType) v5);
    }

    private EditLinkOperation$() {
    }
}
