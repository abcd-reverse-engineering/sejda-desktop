package code.sejda.tasks.bookmark;

import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import scala.collection.immutable.List;
import scala.reflect.ScalaSignature;

/* compiled from: EditBookmarksParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005%4AAD\b\u00011!Aa\u0005\u0001BC\u0002\u0013\u0005q\u0005\u0003\u0005;\u0001\t\u0005\t\u0015!\u0003)\u0011!Y\u0004A!b\u0001\n\u0003a\u0004\u0002C!\u0001\u0005\u0003\u0005\u000b\u0011B\u001f\t\u0011\t\u0003!Q1A\u0005\u0002\rC\u0001\u0002\u0013\u0001\u0003\u0002\u0003\u0006I\u0001\u0012\u0005\u0006\u0013\u0002!\tAS\u0004\b\u001f>\t\t\u0011#\u0001Q\r\u001dqq\"!A\t\u0002ECQ!S\u0005\u0005\u0002YCqaV\u0005\u0012\u0002\u0013\u0005\u0001\fC\u0004d\u0013E\u0005I\u0011\u00013\t\u000f\u0019L\u0011\u0013!C\u0001O\n9R\tZ5u\u0005>|7.\\1sWN\u0004\u0016M]1nKR,'o\u001d\u0006\u0003!E\t\u0001BY8pW6\f'o\u001b\u0006\u0003%M\tQ\u0001^1tWNT!\u0001F\u000b\u0002\u000bM,'\u000eZ1\u000b\u0003Y\tAaY8eK\u000e\u00011C\u0001\u0001\u001a!\tQB%D\u0001\u001c\u0015\taR$\u0001\u0003cCN,'B\u0001\u0010 \u0003%\u0001\u0018M]1nKR,'O\u0003\u0002!C\u0005)Qn\u001c3fY*\u0011AC\t\u0006\u0002G\u0005\u0019qN]4\n\u0005\u0015Z\"!K'vYRL\u0007\u000f\\3QI\u001a\u001cv.\u001e:dK6+H\u000e^5qY\u0016|U\u000f\u001e9viB\u000b'/Y7fi\u0016\u00148/A\u0004eK2,G/\u001a3\u0016\u0003!\u00022!K\u001a7\u001d\tQ\u0003G\u0004\u0002,]5\tAF\u0003\u0002./\u00051AH]8pizJ\u0011aL\u0001\u0006g\u000e\fG.Y\u0005\u0003cI\nq\u0001]1dW\u0006<WMC\u00010\u0013\t!TG\u0001\u0003MSN$(BA\u00193!\t9\u0004(D\u0001\u0010\u0013\tItBA\bEK2,G/\u001a3C_>\\W.\u0019:l\u0003!!W\r\\3uK\u0012\u0004\u0013!B1eI\u0016$W#A\u001f\u0011\u0007%\u001ad\b\u0005\u00028\u007f%\u0011\u0001i\u0004\u0002\u000e\u0003\u0012$W\r\u001a\"p_.l\u0017M]6\u0002\r\u0005$G-\u001a3!\u0003\u001d)\b\u000fZ1uK\u0012,\u0012\u0001\u0012\t\u0004SM*\u0005CA\u001cG\u0013\t9uBA\bVa\u0012\fG/\u001a3C_>\\W.\u0019:l\u0003!)\b\u000fZ1uK\u0012\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0003L\u00196s\u0005CA\u001c\u0001\u0011\u001d1s\u0001%AA\u0002!BqaO\u0004\u0011\u0002\u0003\u0007Q\bC\u0004C\u000fA\u0005\t\u0019\u0001#\u0002/\u0015#\u0017\u000e\u001e\"p_.l\u0017M]6t!\u0006\u0014\u0018-\\3uKJ\u001c\bCA\u001c\n'\tI!\u000b\u0005\u0002T)6\t!'\u0003\u0002Ve\t1\u0011I\\=SK\u001a$\u0012\u0001U\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003eS#\u0001\u000b.,\u0003m\u0003\"\u0001X1\u000e\u0003uS!AX0\u0002\u0013Ut7\r[3dW\u0016$'B\u000113\u0003)\tgN\\8uCRLwN\\\u0005\u0003Ev\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%eU\tQM\u000b\u0002>5\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIM*\u0012\u0001\u001b\u0016\u0003\tj\u0003")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/bookmark/EditBookmarksParameters.class */
public class EditBookmarksParameters extends MultiplePdfSourceMultipleOutputParameters {
    private final List<DeletedBookmark> deleted;
    private final List<AddedBookmark> added;
    private final List<UpdatedBookmark> updated;

    public List<DeletedBookmark> deleted() {
        return this.deleted;
    }

    public EditBookmarksParameters(final List<DeletedBookmark> deleted, final List<AddedBookmark> added, final List<UpdatedBookmark> updated) {
        this.deleted = deleted;
        this.added = added;
        this.updated = updated;
    }

    public List<AddedBookmark> added() {
        return this.added;
    }

    public List<UpdatedBookmark> updated() {
        return this.updated;
    }
}
