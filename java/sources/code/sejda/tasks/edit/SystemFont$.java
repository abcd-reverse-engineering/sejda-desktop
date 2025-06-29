package code.sejda.tasks.edit;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.runtime.AbstractFunction4;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/SystemFont$.class */
public final class SystemFont$ extends AbstractFunction4<String, String, String, String, SystemFont> implements Serializable {
    public static final SystemFont$ MODULE$ = new SystemFont$();

    public final String toString() {
        return "SystemFont";
    }

    public SystemFont apply(final String path, final String postscriptName, final String family, final String style) {
        return new SystemFont(path, postscriptName, family, style);
    }

    public Option<Tuple4<String, String, String, String>> unapply(final SystemFont x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple4(x$0.path(), x$0.postscriptName(), x$0.family(), x$0.style()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SystemFont$.class);
    }

    private SystemFont$() {
    }
}
