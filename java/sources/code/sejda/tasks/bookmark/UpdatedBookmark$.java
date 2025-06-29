package code.sejda.tasks.bookmark;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditBookmarksParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/bookmark/UpdatedBookmark$.class */
public final class UpdatedBookmark$ extends AbstractFunction3<String, String, Option<Object>, UpdatedBookmark> implements Serializable {
    public static final UpdatedBookmark$ MODULE$ = new UpdatedBookmark$();

    public final String toString() {
        return "UpdatedBookmark";
    }

    public UpdatedBookmark apply(final String id, final String title, final Option<Object> page) {
        return new UpdatedBookmark(id, title, page);
    }

    public Option<Tuple3<String, String, Option<Object>>> unapply(final UpdatedBookmark x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple3(x$0.id(), x$0.title(), x$0.page()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(UpdatedBookmark$.class);
    }

    private UpdatedBookmark$() {
    }
}
