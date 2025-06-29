package code.sejda.tasks.bookmark;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple5;
import scala.runtime.AbstractFunction5;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditBookmarksParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/bookmark/AddedBookmark$.class */
public final class AddedBookmark$ extends AbstractFunction5<String, String, String, Object, Object, AddedBookmark> implements Serializable {
    public static final AddedBookmark$ MODULE$ = new AddedBookmark$();

    public final String toString() {
        return "AddedBookmark";
    }

    public AddedBookmark apply(final String tmpId, final String parentId, final String title, final int page, final int index) {
        return new AddedBookmark(tmpId, parentId, title, page, index);
    }

    public Option<Tuple5<String, String, String, Object, Object>> unapply(final AddedBookmark x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple5(x$0.tmpId(), x$0.parentId(), x$0.title(), BoxesRunTime.boxToInteger(x$0.page()), BoxesRunTime.boxToInteger(x$0.index())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AddedBookmark$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5) {
        return apply((String) v1, (String) v2, (String) v3, BoxesRunTime.unboxToInt(v4), BoxesRunTime.unboxToInt(v5));
    }

    private AddedBookmark$() {
    }
}
