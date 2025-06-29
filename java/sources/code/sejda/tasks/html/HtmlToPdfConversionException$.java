package code.sejda.tasks.html;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: HtmlToPdfConversionException.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/html/HtmlToPdfConversionException$.class */
public final class HtmlToPdfConversionException$ implements Serializable {
    public static final HtmlToPdfConversionException$ MODULE$ = new HtmlToPdfConversionException$();

    public Throwable $lessinit$greater$default$2() {
        return null;
    }

    public Option<String> $lessinit$greater$default$3() {
        return None$.MODULE$;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(HtmlToPdfConversionException$.class);
    }

    private HtmlToPdfConversionException$() {
    }
}
