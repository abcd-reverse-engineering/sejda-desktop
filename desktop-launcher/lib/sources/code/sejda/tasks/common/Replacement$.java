package code.sejda.tasks.common;

import java.awt.geom.Point2D;
import java.io.Serializable;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple7;
import scala.runtime.AbstractFunction7;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: PageTextRedactor.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/Replacement$.class */
public final class Replacement$ extends AbstractFunction7<String, Option<PDFont>, Option<Object>, Option<Point2D>, Option<PDColor>, Object, Option<Object>, Replacement> implements Serializable {
    public static final Replacement$ MODULE$ = new Replacement$();

    public final String toString() {
        return "Replacement";
    }

    public Replacement apply(final String text, final Option<PDFont> font, final Option<Object> fontSize, final Option<Point2D> position, final Option<PDColor> color, final boolean fauxItalic, final Option<Object> lineHeight) {
        return new Replacement(text, font, fontSize, position, color, fauxItalic, lineHeight);
    }

    public Option<Tuple7<String, Option<PDFont>, Option<Object>, Option<Point2D>, Option<PDColor>, Object, Option<Object>>> unapply(final Replacement x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple7(x$0.text(), x$0.font(), x$0.fontSize(), x$0.position(), x$0.color(), BoxesRunTime.boxToBoolean(x$0.fauxItalic()), x$0.lineHeight()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Replacement$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5, final Object v6, final Object v7) {
        return apply((String) v1, (Option<PDFont>) v2, (Option<Object>) v3, (Option<Point2D>) v4, (Option<PDColor>) v5, BoxesRunTime.unboxToBoolean(v6), (Option<Object>) v7);
    }

    private Replacement$() {
    }
}
