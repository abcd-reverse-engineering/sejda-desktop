package code.sejda.tasks.edit;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import org.sejda.model.pdf.page.PageRange;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple9;
import scala.runtime.AbstractFunction9;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendTextOperation$.class */
public final class AppendTextOperation$ extends AbstractFunction9<String, String, Object, Color, Point2D, PageRange, Object, Object, Option<Object>, AppendTextOperation> implements Serializable {
    public static final AppendTextOperation$ MODULE$ = new AppendTextOperation$();

    public final String toString() {
        return "AppendTextOperation";
    }

    public AppendTextOperation apply(final String text, final String font, final double fontSize, final Color color, final Point2D position, final PageRange pageRange, final boolean bold, final boolean italic, final Option<Object> lineHeight) {
        return new AppendTextOperation(text, font, fontSize, color, position, pageRange, bold, italic, lineHeight);
    }

    public Option<Tuple9<String, String, Object, Color, Point2D, PageRange, Object, Object, Option<Object>>> unapply(final AppendTextOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple9(x$0.text(), x$0.font(), BoxesRunTime.boxToDouble(x$0.fontSize()), x$0.color(), x$0.position(), x$0.pageRange(), BoxesRunTime.boxToBoolean(x$0.bold()), BoxesRunTime.boxToBoolean(x$0.italic()), x$0.lineHeight()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AppendTextOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5, final Object v6, final Object v7, final Object v8, final Object v9) {
        return apply((String) v1, (String) v2, BoxesRunTime.unboxToDouble(v3), (Color) v4, (Point2D) v5, (PageRange) v6, BoxesRunTime.unboxToBoolean(v7), BoxesRunTime.unboxToBoolean(v8), (Option<Object>) v9);
    }

    private AppendTextOperation$() {
    }

    public Color $lessinit$greater$default$4() {
        return Color.BLACK;
    }

    public Color apply$default$4() {
        return Color.BLACK;
    }

    public boolean $lessinit$greater$default$7() {
        return false;
    }

    public boolean $lessinit$greater$default$8() {
        return false;
    }

    public Option<Object> $lessinit$greater$default$9() {
        return None$.MODULE$;
    }

    public boolean apply$default$7() {
        return false;
    }

    public boolean apply$default$8() {
        return false;
    }

    public Option<Object> apply$default$9() {
        return None$.MODULE$;
    }
}
