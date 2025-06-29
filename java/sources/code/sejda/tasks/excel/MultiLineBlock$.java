package code.sejda.tasks.excel;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: TableDetector.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/MultiLineBlock$.class */
public final class MultiLineBlock$ extends AbstractFunction1<Seq<Line>, MultiLineBlock> implements Serializable {
    public static final MultiLineBlock$ MODULE$ = new MultiLineBlock$();

    public final String toString() {
        return "MultiLineBlock";
    }

    public MultiLineBlock apply(final Seq<Line> elems) {
        return new MultiLineBlock(elems);
    }

    public Option<Seq<Line>> unapplySeq(final MultiLineBlock x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.elems());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MultiLineBlock$.class);
    }

    private MultiLineBlock$() {
    }
}
