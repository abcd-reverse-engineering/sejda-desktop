package code.sejda.tasks.common;

import java.io.Serializable;
import org.sejda.sambox.pdmodel.PDDocument;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: DocumentFontSearcher.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/DocumentFontSearcher$.class */
public final class DocumentFontSearcher$ extends AbstractFunction1<PDDocument, DocumentFontSearcher> implements Serializable {
    public static final DocumentFontSearcher$ MODULE$ = new DocumentFontSearcher$();

    public final String toString() {
        return "DocumentFontSearcher";
    }

    public DocumentFontSearcher apply(final PDDocument doc) {
        return new DocumentFontSearcher(doc);
    }

    public Option<PDDocument> unapply(final DocumentFontSearcher x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.doc());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DocumentFontSearcher$.class);
    }

    private DocumentFontSearcher$() {
    }
}
