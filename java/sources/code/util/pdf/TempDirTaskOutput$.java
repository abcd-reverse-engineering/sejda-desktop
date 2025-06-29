package code.util.pdf;

import java.io.File;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: TempDirTaskOutput.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/TempDirTaskOutput$.class */
public final class TempDirTaskOutput$ extends AbstractFunction1<File, TempDirTaskOutput> implements Serializable {
    public static final TempDirTaskOutput$ MODULE$ = new TempDirTaskOutput$();

    public final String toString() {
        return "TempDirTaskOutput";
    }

    public TempDirTaskOutput apply(final File destination) {
        return new TempDirTaskOutput(destination);
    }

    public Option<File> unapply(final TempDirTaskOutput x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.destination());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TempDirTaskOutput$.class);
    }

    private TempDirTaskOutput$() {
    }
}
