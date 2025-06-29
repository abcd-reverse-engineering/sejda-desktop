package code.sejda.tasks.edit;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import org.sejda.model.TopLeftRectangularBox;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple12;
import scala.runtime.AbstractFunction12;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/EditTextOperation$.class */
public final class EditTextOperation$ extends AbstractFunction12<String, Option<String>, TopLeftRectangularBox, Object, Object, Object, Option<Object>, Option<Point2D>, String, Option<Color>, Option<Object>, Option<String>, EditTextOperation> implements Serializable {
    public static final EditTextOperation$ MODULE$ = new EditTextOperation$();

    public Option<String> $lessinit$greater$default$2() {
        return None$.MODULE$;
    }

    public final String toString() {
        return "EditTextOperation";
    }

    public EditTextOperation apply(final String text, final Option<String> fontOpt, final TopLeftRectangularBox boundingBox, final int pageNumber, final boolean bold, final boolean italic, final Option<Object> fontSize, final Option<Point2D> position, final String originalText, final Option<Color> color, final Option<Object> lineHeight, final Option<String> fontId) {
        return new EditTextOperation(text, fontOpt, boundingBox, pageNumber, bold, italic, fontSize, position, originalText, color, lineHeight, fontId);
    }

    public Option<String> apply$default$2() {
        return None$.MODULE$;
    }

    public Option<Tuple12<String, Option<String>, TopLeftRectangularBox, Object, Object, Object, Option<Object>, Option<Point2D>, String, Option<Color>, Option<Object>, Option<String>>> unapply(final EditTextOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple12(x$0.text(), x$0.fontOpt(), x$0.boundingBox(), BoxesRunTime.boxToInteger(x$0.pageNumber()), BoxesRunTime.boxToBoolean(x$0.bold()), BoxesRunTime.boxToBoolean(x$0.italic()), x$0.fontSize(), x$0.position(), x$0.originalText(), x$0.color(), x$0.lineHeight(), x$0.fontId()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(EditTextOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5, final Object v6, final Object v7, final Object v8, final Object v9, final Object v10, final Object v11, final Object v12) {
        return apply((String) v1, (Option<String>) v2, (TopLeftRectangularBox) v3, BoxesRunTime.unboxToInt(v4), BoxesRunTime.unboxToBoolean(v5), BoxesRunTime.unboxToBoolean(v6), (Option<Object>) v7, (Option<Point2D>) v8, (String) v9, (Option<Color>) v10, (Option<Object>) v11, (Option<String>) v12);
    }

    private EditTextOperation$() {
    }

    public boolean $lessinit$greater$default$5() {
        return false;
    }

    public boolean $lessinit$greater$default$6() {
        return false;
    }

    public boolean apply$default$5() {
        return false;
    }

    public boolean apply$default$6() {
        return false;
    }

    public Option<Object> $lessinit$greater$default$7() {
        return None$.MODULE$;
    }

    public Option<Point2D> $lessinit$greater$default$8() {
        return None$.MODULE$;
    }

    public String $lessinit$greater$default$9() {
        return "";
    }

    public Option<Object> apply$default$7() {
        return None$.MODULE$;
    }

    public Option<Point2D> apply$default$8() {
        return None$.MODULE$;
    }

    public String apply$default$9() {
        return "";
    }

    public Option<Color> $lessinit$greater$default$10() {
        return None$.MODULE$;
    }

    public Option<Object> $lessinit$greater$default$11() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$12() {
        return None$.MODULE$;
    }

    public Option<Color> apply$default$10() {
        return None$.MODULE$;
    }

    public Option<Object> apply$default$11() {
        return None$.MODULE$;
    }

    public Option<String> apply$default$12() {
        return None$.MODULE$;
    }
}
