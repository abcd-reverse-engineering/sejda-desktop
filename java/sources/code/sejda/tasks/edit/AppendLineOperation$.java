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
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendLineOperation$.class */
public final class AppendLineOperation$ extends AbstractFunction6<Object, RectangularBox, Color, Seq<Object>, Object, String, AppendLineOperation> implements Serializable {
    public static final AppendLineOperation$ MODULE$ = new AppendLineOperation$();

    public String $lessinit$greater$default$6() {
        return "line";
    }

    public final String toString() {
        return "AppendLineOperation";
    }

    public AppendLineOperation apply(final int pageNumber, final RectangularBox boundingBox, final Color color, final Seq<Object> points, final double lineSize, final String kind) {
        return new AppendLineOperation(pageNumber, boundingBox, color, points, lineSize, kind);
    }

    public String apply$default$6() {
        return "line";
    }

    public Option<Tuple6<Object, RectangularBox, Color, Seq<Object>, Object, String>> unapply(final AppendLineOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple6(BoxesRunTime.boxToInteger(x$0.pageNumber()), x$0.boundingBox(), x$0.color(), x$0.points(), BoxesRunTime.boxToDouble(x$0.lineSize()), x$0.kind()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AppendLineOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5, final Object v6) {
        return apply(BoxesRunTime.unboxToInt(v1), (RectangularBox) v2, (Color) v3, (Seq<Object>) v4, BoxesRunTime.unboxToDouble(v5), (String) v6);
    }

    private AppendLineOperation$() {
    }
}
