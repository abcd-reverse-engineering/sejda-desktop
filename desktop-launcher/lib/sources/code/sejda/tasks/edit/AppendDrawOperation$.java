package code.sejda.tasks.edit;

import java.awt.Color;
import java.io.Serializable;
import org.sejda.model.RectangularBox;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple6;
import scala.collection.immutable.Seq;
import scala.runtime.AbstractFunction6;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendDrawOperation$.class */
public final class AppendDrawOperation$ extends AbstractFunction6<Object, RectangularBox, Color, Seq<Object>, Seq<Seq<Object>>, Object, AppendDrawOperation> implements Serializable {
    public static final AppendDrawOperation$ MODULE$ = new AppendDrawOperation$();

    public final String toString() {
        return "AppendDrawOperation";
    }

    public AppendDrawOperation apply(final int pageNumber, final RectangularBox boundingBox, final Color color, final Seq<Object> inklist, final Seq<Seq<Object>> appearance, final double lineSize) {
        return new AppendDrawOperation(pageNumber, boundingBox, color, inklist, appearance, lineSize);
    }

    public Option<Tuple6<Object, RectangularBox, Color, Seq<Object>, Seq<Seq<Object>>, Object>> unapply(final AppendDrawOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple6(BoxesRunTime.boxToInteger(x$0.pageNumber()), x$0.boundingBox(), x$0.color(), x$0.inklist(), x$0.appearance(), BoxesRunTime.boxToDouble(x$0.lineSize())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AppendDrawOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5, final Object v6) {
        return apply(BoxesRunTime.unboxToInt(v1), (RectangularBox) v2, (Color) v3, (Seq<Object>) v4, (Seq<Seq<Object>>) v5, BoxesRunTime.unboxToDouble(v6));
    }

    private AppendDrawOperation$() {
    }
}
