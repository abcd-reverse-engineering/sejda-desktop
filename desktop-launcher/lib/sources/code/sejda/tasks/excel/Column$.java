package code.sejda.tasks.excel;

import java.io.Serializable;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.PDTableAttributeObject;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.Seq;
import scala.runtime.AbstractFunction2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: TableDetector.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/Column$.class */
public final class Column$ extends AbstractFunction2<Seq<Text>, Object, Column> implements Serializable {
    public static final Column$ MODULE$ = new Column$();

    public int $lessinit$greater$default$2() {
        return Columns$.MODULE$.nextIndex();
    }

    public final String toString() {
        return PDTableAttributeObject.SCOPE_COLUMN;
    }

    public Column apply(final Seq<Text> elems, final int index) {
        return new Column(elems, index);
    }

    public int apply$default$2() {
        return Columns$.MODULE$.nextIndex();
    }

    public Option<Tuple2<Seq<Text>, Object>> unapply(final Column x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2(x$0.elems(), BoxesRunTime.boxToInteger(x$0.index())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Column$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2) {
        return apply((Seq<Text>) v1, BoxesRunTime.unboxToInt(v2));
    }

    private Column$() {
    }
}
