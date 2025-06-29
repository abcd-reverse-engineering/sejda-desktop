package code.sejda.tasks.excel;

import java.io.Serializable;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLine;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: TableDetector.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/Line$.class */
public final class Line$ extends AbstractFunction1<Text, Line> implements Serializable {
    public static final Line$ MODULE$ = new Line$();

    public final String toString() {
        return PDAnnotationLine.SUB_TYPE;
    }

    public Line apply(final Text first) {
        return new Line(first);
    }

    public Option<Text> unapply(final Line x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.first());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Line$.class);
    }

    private Line$() {
    }
}
