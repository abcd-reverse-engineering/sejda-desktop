package code.util.pdf;

import java.io.File;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: TempDirTaskOutput.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/TempFileOrDirTaskOutput$.class */
public final class TempFileOrDirTaskOutput$ extends AbstractFunction1<File, TempFileOrDirTaskOutput> implements Serializable {
    public static final TempFileOrDirTaskOutput$ MODULE$ = new TempFileOrDirTaskOutput$();

    public final String toString() {
        return "TempFileOrDirTaskOutput";
    }

    public TempFileOrDirTaskOutput apply(final File destination) {
        return new TempFileOrDirTaskOutput(destination);
    }

    public Option<File> unapply(final TempFileOrDirTaskOutput x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.destination());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TempFileOrDirTaskOutput$.class);
    }

    private TempFileOrDirTaskOutput$() {
    }
}
