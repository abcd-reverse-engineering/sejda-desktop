package code.sejda.tasks.bookmark;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditBookmarksParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/bookmark/DeletedBookmark$.class */
public final class DeletedBookmark$ extends AbstractFunction1<String, DeletedBookmark> implements Serializable {
    public static final DeletedBookmark$ MODULE$ = new DeletedBookmark$();

    public final String toString() {
        return "DeletedBookmark";
    }

    public DeletedBookmark apply(final String id) {
        return new DeletedBookmark(id);
    }

    public Option<String> unapply(final DeletedBookmark x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.id());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DeletedBookmark$.class);
    }

    private DeletedBookmark$() {
    }
}
