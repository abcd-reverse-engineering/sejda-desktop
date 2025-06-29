package code.util.pdf;

import java.io.File;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: PdfTempFileTaskOutput.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PdfTempFileTaskOutput$.class */
public final class PdfTempFileTaskOutput$ extends AbstractFunction1<File, PdfTempFileTaskOutput> implements Serializable {
    public static final PdfTempFileTaskOutput$ MODULE$ = new PdfTempFileTaskOutput$();

    public final String toString() {
        return "PdfTempFileTaskOutput";
    }

    public PdfTempFileTaskOutput apply(final File destination) {
        return new PdfTempFileTaskOutput(destination);
    }

    public Option<File> unapply(final PdfTempFileTaskOutput x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.destination());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PdfTempFileTaskOutput$.class);
    }

    private PdfTempFileTaskOutput$() {
    }
}
