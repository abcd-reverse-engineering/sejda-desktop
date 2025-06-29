package code.sejda.tasks.common;

import code.sejda.tasks.common.AcroFormsHelper;
import java.io.Serializable;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;

/* compiled from: AcroFormsHelper.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/AcroFormsHelper$PDPageContentStreamPimped$.class */
public class AcroFormsHelper$PDPageContentStreamPimped$ extends AbstractFunction1<PDPageContentStream, AcroFormsHelper.PDPageContentStreamPimped> implements Serializable {
    private final /* synthetic */ AcroFormsHelper $outer;

    public final String toString() {
        return "PDPageContentStreamPimped";
    }

    public AcroFormsHelper.PDPageContentStreamPimped apply(final PDPageContentStream cs) {
        return new AcroFormsHelper.PDPageContentStreamPimped(this.$outer, cs);
    }

    public Option<PDPageContentStream> unapply(final AcroFormsHelper.PDPageContentStreamPimped x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.cs());
    }

    public AcroFormsHelper$PDPageContentStreamPimped$(final AcroFormsHelper $outer) {
        if ($outer == null) {
            throw null;
        }
        this.$outer = $outer;
    }
}
