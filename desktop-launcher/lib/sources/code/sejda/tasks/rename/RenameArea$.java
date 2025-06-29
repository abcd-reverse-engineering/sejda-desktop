package code.sejda.tasks.rename;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple6;
import scala.runtime.AbstractFunction6;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: RenameByTextParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/rename/RenameArea$.class */
public final class RenameArea$ extends AbstractFunction6<Object, Object, Object, Object, Object, String, RenameArea> implements Serializable {
    public static final RenameArea$ MODULE$ = new RenameArea$();

    public final String toString() {
        return "RenameArea";
    }

    public RenameArea apply(final int x, final int y, final int width, final int height, final int pageNum, final String name) {
        return new RenameArea(x, y, width, height, pageNum, name);
    }

    public Option<Tuple6<Object, Object, Object, Object, Object, String>> unapply(final RenameArea x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple6(BoxesRunTime.boxToInteger(x$0.x()), BoxesRunTime.boxToInteger(x$0.y()), BoxesRunTime.boxToInteger(x$0.width()), BoxesRunTime.boxToInteger(x$0.height()), BoxesRunTime.boxToInteger(x$0.pageNum()), x$0.name()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RenameArea$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5, final Object v6) {
        return apply(BoxesRunTime.unboxToInt(v1), BoxesRunTime.unboxToInt(v2), BoxesRunTime.unboxToInt(v3), BoxesRunTime.unboxToInt(v4), BoxesRunTime.unboxToInt(v5), (String) v6);
    }

    private RenameArea$() {
    }
}
