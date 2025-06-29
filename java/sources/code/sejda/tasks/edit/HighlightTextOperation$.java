package code.sejda.tasks.edit;

import java.awt.Color;
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
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/HighlightTextOperation$.class */
public final class HighlightTextOperation$ extends AbstractFunction4<Object, Set<RectangularBox>, Color, HighlightType, HighlightTextOperation> implements Serializable {
    public static final HighlightTextOperation$ MODULE$ = new HighlightTextOperation$();

    public final String toString() {
        return "HighlightTextOperation";
    }

    public HighlightTextOperation apply(final int pageNumber, final Set<RectangularBox> boundingBoxes, final Color color, final HighlightType kind) {
        return new HighlightTextOperation(pageNumber, boundingBoxes, color, kind);
    }

    public Option<Tuple4<Object, Set<RectangularBox>, Color, HighlightType>> unapply(final HighlightTextOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple4(BoxesRunTime.boxToInteger(x$0.pageNumber()), x$0.boundingBoxes(), x$0.color(), x$0.kind()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(HighlightTextOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4) {
        return apply(BoxesRunTime.unboxToInt(v1), (Set<RectangularBox>) v2, (Color) v3, (HighlightType) v4);
    }

    private HighlightTextOperation$() {
    }
}
