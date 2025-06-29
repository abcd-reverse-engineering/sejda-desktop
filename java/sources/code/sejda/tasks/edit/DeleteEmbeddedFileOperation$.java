package code.sejda.tasks.edit;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeleteEmbeddedFileOperation$.class */
public final class DeleteEmbeddedFileOperation$ extends AbstractFunction1<String, DeleteEmbeddedFileOperation> implements Serializable {
    public static final DeleteEmbeddedFileOperation$ MODULE$ = new DeleteEmbeddedFileOperation$();

    public final String toString() {
        return "DeleteEmbeddedFileOperation";
    }

    public DeleteEmbeddedFileOperation apply(final String filename) {
        return new DeleteEmbeddedFileOperation(filename);
    }

    public Option<String> unapply(final DeleteEmbeddedFileOperation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.filename());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DeleteEmbeddedFileOperation$.class);
    }

    private DeleteEmbeddedFileOperation$() {
    }
}
