package code.sejda.tasks.common;

import code.sejda.tasks.common.GoogleFonts;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: GoogleFonts.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/GoogleFonts$GoogleFont$.class */
public class GoogleFonts$GoogleFont$ extends AbstractFunction1<String, GoogleFonts.GoogleFont> implements Serializable {
    public static final GoogleFonts$GoogleFont$ MODULE$ = new GoogleFonts$GoogleFont$();

    public final String toString() {
        return "GoogleFont";
    }

    public GoogleFonts.GoogleFont apply(final String name) {
        return new GoogleFonts.GoogleFont(name);
    }

    public Option<String> unapply(final GoogleFonts.GoogleFont x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.name());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(GoogleFonts$GoogleFont$.class);
    }
}
