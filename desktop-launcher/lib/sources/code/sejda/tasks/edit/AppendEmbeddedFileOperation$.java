package code.sejda.tasks.edit;

import java.io.Serializable;
import org.sejda.model.input.Source;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendEmbeddedFileOperation$.class */
public final class AppendEmbeddedFileOperation$ extends AbstractFunction1<Source<?>, AppendEmbeddedFileOperation> implements Serializable {
    public static final AppendEmbeddedFileOperation$ MODULE$ = new AppendEmbeddedFileOperation$();

    public final String toString() {
        return "AppendEmbeddedFileOperation";
    }

    public AppendEmbeddedFileOperation apply(final Source<?> file) {
        return new AppendEmbeddedFileOperation(file);
    }

    public Option<Source<?>> unapply(final AppendEmbeddedFileOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.file());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AppendEmbeddedFileOperation$.class);
    }

    private AppendEmbeddedFileOperation$() {
    }
}
