package code.sejda.tasks.edit;

import java.io.Serializable;
import org.sejda.model.RectangularBox;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.collection.immutable.Set;
import scala.runtime.AbstractFunction3;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendAttachmentOperation$.class */
public final class AppendAttachmentOperation$ extends AbstractFunction3<Object, Set<RectangularBox>, String, AppendAttachmentOperation> implements Serializable {
    public static final AppendAttachmentOperation$ MODULE$ = new AppendAttachmentOperation$();

    public final String toString() {
        return "AppendAttachmentOperation";
    }

    public AppendAttachmentOperation apply(final int pageNumber, final Set<RectangularBox> boundingBoxes, final String filename) {
        return new AppendAttachmentOperation(pageNumber, boundingBoxes, filename);
    }

    public Option<Tuple3<Object, Set<RectangularBox>, String>> unapply(final AppendAttachmentOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple3(BoxesRunTime.boxToInteger(x$0.pageNumber()), x$0.boundingBoxes(), x$0.filename()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AppendAttachmentOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3) {
        return apply(BoxesRunTime.unboxToInt(v1), (Set<RectangularBox>) v2, (String) v3);
    }

    private AppendAttachmentOperation$() {
    }
}
