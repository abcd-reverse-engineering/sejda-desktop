package code.sejda.tasks.common;

import java.io.Serializable;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.sambox.pdmodel.PDPage;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple5;
import scala.runtime.AbstractFunction5;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: PageTextRedactor.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/Redaction$.class */
public final class Redaction$ extends AbstractFunction5<PDPage, Object, String, TopLeftRectangularBox, Replacement, Redaction> implements Serializable {
    public static final Redaction$ MODULE$ = new Redaction$();

    public final String toString() {
        return "Redaction";
    }

    public Redaction apply(final PDPage page, final int pageNumber, final String originalText, final TopLeftRectangularBox boundingBox, final Replacement replacement) {
        return new Redaction(page, pageNumber, originalText, boundingBox, replacement);
    }

    public Option<Tuple5<PDPage, Object, String, TopLeftRectangularBox, Replacement>> unapply(final Redaction x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple5(x$0.page(), BoxesRunTime.boxToInteger(x$0.pageNumber()), x$0.originalText(), x$0.boundingBox(), x$0.replacement()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Redaction$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3, final Object v4, final Object v5) {
        return apply((PDPage) v1, BoxesRunTime.unboxToInt(v2), (String) v3, (TopLeftRectangularBox) v4, (Replacement) v5);
    }

    private Redaction$() {
    }
}
