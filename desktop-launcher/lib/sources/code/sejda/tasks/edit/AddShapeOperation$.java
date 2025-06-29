package code.sejda.tasks.edit;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import org.sejda.model.parameter.edit.Shape;
import org.sejda.model.pdf.page.PageRange;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple8;
import scala.runtime.AbstractFunction8;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AddShapeOperation$.class */
public final class AddShapeOperation$ extends AbstractFunction8<Shape, Object, Object, Point2D, PageRange, Object, Color, Color, AddShapeOperation> implements Serializable {
    public static final AddShapeOperation$ MODULE$ = new AddShapeOperation$();

    public final String toString() {
        return "AddShapeOperation";
    }

    public AddShapeOperation apply(final Shape shape, final float width, final float height, final Point2D position, final PageRange pageRange, final float borderWidth, final Color borderColor, final Color backgroundColor) {
        return new AddShapeOperation(shape, width, height, position, pageRange, borderWidth, borderColor, backgroundColor);
    }

    public Option<Tuple8<Shape, Object, Object, Point2D, PageRange, Object, Color, Color>> unapply(final AddShapeOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple8(x$0.shape(), BoxesRunTime.boxToFloat(x$0.width()), BoxesRunTime.boxToFloat(x$0.height()), x$0.position(), x$0.pageRange(), BoxesRunTime.boxToFloat(x$0.borderWidth()), x$0.borderColor(), x$0.backgroundColor()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AddShapeOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5, final Object v6, final Object v7, final Object v8) {
        return apply((Shape) v1, BoxesRunTime.unboxToFloat(v2), BoxesRunTime.unboxToFloat(v3), (Point2D) v4, (PageRange) v5, BoxesRunTime.unboxToFloat(v6), (Color) v7, (Color) v8);
    }

    private AddShapeOperation$() {
    }

    public float $lessinit$greater$default$6() {
        return 1.0f;
    }

    public float apply$default$6() {
        return 1.0f;
    }

    public Color $lessinit$greater$default$7() {
        return Color.BLACK;
    }

    public Color apply$default$7() {
        return Color.BLACK;
    }
}
