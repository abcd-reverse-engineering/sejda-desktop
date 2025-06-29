package code.sejda.tasks.edit;

import java.awt.Color;
import java.io.Serializable;
import org.sejda.model.RectangularBox;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple17;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.runtime.AbstractFunction17;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendFormFieldOperation$.class */
public final class AppendFormFieldOperation$ extends AbstractFunction17<FormFieldType, String, Object, RectangularBox, Object, Object, Option<String>, Seq<String>, Object, Option<Color>, Option<Color>, Object, Option<Object>, FormFieldAlign, Object, String, Option<Object>, AppendFormFieldOperation> implements Serializable {
    public static final AppendFormFieldOperation$ MODULE$ = new AppendFormFieldOperation$();

    public final String toString() {
        return "AppendFormFieldOperation";
    }

    public AppendFormFieldOperation apply(final FormFieldType kind, final String name, final int pageNumber, final RectangularBox boundingBox, final boolean multiline, final boolean selected, final Option<String> valueOpt, final Seq<String> options, final boolean multiselect, final Option<Color> borderColor, final Option<Color> color, final boolean hasBorder, final Option<Object> fontSizeOpt, final FormFieldAlign align, final boolean required, final String id, final Option<Object> combChars) {
        return new AppendFormFieldOperation(kind, name, pageNumber, boundingBox, multiline, selected, valueOpt, options, multiselect, borderColor, color, hasBorder, fontSizeOpt, align, required, id, combChars);
    }

    public Option<Tuple17<FormFieldType, String, Object, RectangularBox, Object, Object, Option<String>, Seq<String>, Object, Option<Color>, Option<Color>, Object, Option<Object>, FormFieldAlign, Object, String, Option<Object>>> unapply(final AppendFormFieldOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple17(x$0.kind(), x$0.name(), BoxesRunTime.boxToInteger(x$0.pageNumber()), x$0.boundingBox(), BoxesRunTime.boxToBoolean(x$0.multiline()), BoxesRunTime.boxToBoolean(x$0.selected()), x$0.valueOpt(), x$0.options(), BoxesRunTime.boxToBoolean(x$0.multiselect()), x$0.borderColor(), x$0.color(), BoxesRunTime.boxToBoolean(x$0.hasBorder()), x$0.fontSizeOpt(), x$0.align(), BoxesRunTime.boxToBoolean(x$0.required()), x$0.id(), x$0.combChars()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AppendFormFieldOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5, final Object v6, final Object v7, final Object v8, final Object v9, final Object v10, final Object v11, final Object v12, final Object v13, final Object v14, final Object v15, final Object v16, final Object v17) {
        return apply((FormFieldType) v1, (String) v2, BoxesRunTime.unboxToInt(v3), (RectangularBox) v4, BoxesRunTime.unboxToBoolean(v5), BoxesRunTime.unboxToBoolean(v6), (Option<String>) v7, (Seq<String>) v8, BoxesRunTime.unboxToBoolean(v9), (Option<Color>) v10, (Option<Color>) v11, BoxesRunTime.unboxToBoolean(v12), (Option<Object>) v13, (FormFieldAlign) v14, BoxesRunTime.unboxToBoolean(v15), (String) v16, (Option<Object>) v17);
    }

    private AppendFormFieldOperation$() {
    }

    public boolean $lessinit$greater$default$5() {
        return false;
    }

    public boolean apply$default$5() {
        return false;
    }

    public boolean $lessinit$greater$default$6() {
        return false;
    }

    public boolean apply$default$6() {
        return false;
    }

    public Option<String> $lessinit$greater$default$7() {
        return None$.MODULE$;
    }

    public Option<String> apply$default$7() {
        return None$.MODULE$;
    }

    public Seq<String> $lessinit$greater$default$8() {
        return package$.MODULE$.Seq().empty();
    }

    public Seq<String> apply$default$8() {
        return package$.MODULE$.Seq().empty();
    }

    public boolean $lessinit$greater$default$9() {
        return false;
    }

    public boolean apply$default$9() {
        return false;
    }

    public Option<Color> $lessinit$greater$default$10() {
        return None$.MODULE$;
    }

    public Option<Color> apply$default$10() {
        return None$.MODULE$;
    }

    public Option<Color> $lessinit$greater$default$11() {
        return None$.MODULE$;
    }

    public Option<Color> apply$default$11() {
        return None$.MODULE$;
    }

    public boolean $lessinit$greater$default$12() {
        return true;
    }

    public boolean apply$default$12() {
        return true;
    }

    public Option<Object> $lessinit$greater$default$13() {
        return None$.MODULE$;
    }

    public Option<Object> apply$default$13() {
        return None$.MODULE$;
    }

    public FormFieldAlign $lessinit$greater$default$14() {
        return FormFieldAlign$Left$.MODULE$;
    }

    public FormFieldAlign apply$default$14() {
        return FormFieldAlign$Left$.MODULE$;
    }

    public boolean $lessinit$greater$default$15() {
        return false;
    }

    public boolean apply$default$15() {
        return false;
    }

    public String $lessinit$greater$default$16() {
        return "";
    }

    public String apply$default$16() {
        return "";
    }

    public Option<Object> $lessinit$greater$default$17() {
        return None$.MODULE$;
    }

    public Option<Object> apply$default$17() {
        return None$.MODULE$;
    }
}
