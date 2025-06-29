package code.sejda.tasks.edit;

import java.io.Serializable;
import org.sejda.model.rotation.Rotation;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/RotatePageOperation$.class */
public final class RotatePageOperation$ extends AbstractFunction2<Object, Rotation, RotatePageOperation> implements Serializable {
    public static final RotatePageOperation$ MODULE$ = new RotatePageOperation$();

    public final String toString() {
        return "RotatePageOperation";
    }

    public RotatePageOperation apply(final int pageNumber, final Rotation rotation) {
        return new RotatePageOperation(pageNumber, rotation);
    }

    public Option<Tuple2<Object, Rotation>> unapply(final RotatePageOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2(BoxesRunTime.boxToInteger(x$0.pageNumber()), x$0.rotation()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RotatePageOperation$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2) {
        return apply(BoxesRunTime.unboxToInt(v1), (Rotation) v2);
    }

    private RotatePageOperation$() {
    }
}
