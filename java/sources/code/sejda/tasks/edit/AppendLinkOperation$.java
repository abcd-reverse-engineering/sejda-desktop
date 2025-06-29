package code.sejda.tasks.edit;

import java.io.Serializable;
import org.sejda.model.RectangularBox;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.collection.immutable.Set;
import scala.runtime.AbstractFunction4;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendLinkOperation$.class */
public final class AppendLinkOperation$ extends AbstractFunction4<Object, Set<RectangularBox>, String, LinkType, AppendLinkOperation> implements Serializable {
    public static final AppendLinkOperation$ MODULE$ = new AppendLinkOperation$();

    public final String toString() {
        return "AppendLinkOperation";
    }

    public AppendLinkOperation apply(final int pageNumber, final Set<RectangularBox> boundingBoxes, final String href, final LinkType kind) {
        return new AppendLinkOperation(pageNumber, boundingBoxes, href, kind);
    }

    public Option<Tuple4<Object, Set<RectangularBox>, String, LinkType>> unapply(final AppendLinkOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple4(BoxesRunTime.boxToInteger(x$0.pageNumber()), x$0.boundingBoxes(), x$0.href(), x$0.kind()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AppendLinkOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4) {
        return apply(BoxesRunTime.unboxToInt(v1), (Set<RectangularBox>) v2, (String) v3, (LinkType) v4);
    }

    private AppendLinkOperation$() {
    }
}
