package code.sejda.tasks.edit;

import java.awt.geom.Point2D;
import java.io.Serializable;
import org.sejda.model.input.Source;
import org.sejda.model.pdf.page.PageRange;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple8;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.runtime.AbstractFunction8;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AddImageOperation$.class */
public final class AddImageOperation$ extends AbstractFunction8<Source<?>, Object, Object, Point2D, PageRange, Object, Seq<Object>, Option<String>, AddImageOperation> implements Serializable {
    public static final AddImageOperation$ MODULE$ = new AddImageOperation$();

    public final String toString() {
        return "AddImageOperation";
    }

    public AddImageOperation apply(final Source<?> imageSource, final float width, final float height, final Point2D position, final PageRange pageRange, final int rotation, final Seq<Object> exceptPages, final Option<String> signatureFieldName) {
        return new AddImageOperation(imageSource, width, height, position, pageRange, rotation, exceptPages, signatureFieldName);
    }

    public Option<Tuple8<Source<?>, Object, Object, Point2D, PageRange, Object, Seq<Object>, Option<String>>> unapply(final AddImageOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple8(x$0.imageSource(), BoxesRunTime.boxToFloat(x$0.width()), BoxesRunTime.boxToFloat(x$0.height()), x$0.position(), x$0.pageRange(), BoxesRunTime.boxToInteger(x$0.rotation()), x$0.exceptPages(), x$0.signatureFieldName()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AddImageOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5, final Object v6, final Object v7, final Object v8) {
        return apply((Source<?>) v1, BoxesRunTime.unboxToFloat(v2), BoxesRunTime.unboxToFloat(v3), (Point2D) v4, (PageRange) v5, BoxesRunTime.unboxToInt(v6), (Seq<Object>) v7, (Option<String>) v8);
    }

    private AddImageOperation$() {
    }

    public Seq<Object> $lessinit$greater$default$7() {
        return package$.MODULE$.Seq().empty();
    }

    public Seq<Object> apply$default$7() {
        return package$.MODULE$.Seq().empty();
    }

    public Option<String> $lessinit$greater$default$8() {
        return None$.MODULE$;
    }

    public Option<String> apply$default$8() {
        return None$.MODULE$;
    }
}
