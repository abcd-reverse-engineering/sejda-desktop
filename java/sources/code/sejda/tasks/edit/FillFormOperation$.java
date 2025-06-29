package code.sejda.tasks.edit;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/FillFormOperation$.class */
public final class FillFormOperation$ extends AbstractFunction2<String, String, FillFormOperation> implements Serializable {
    public static final FillFormOperation$ MODULE$ = new FillFormOperation$();

    public final String toString() {
        return "FillFormOperation";
    }

    public FillFormOperation apply(final String name, final String value) {
        return new FillFormOperation(name, value);
    }

    public Option<Tuple2<String, String>> unapply(final FillFormOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2(x$0.name(), x$0.value()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(FillFormOperation$.class);
    }

    private FillFormOperation$() {
    }
}
