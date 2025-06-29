package code.sejda.tasks.common;

import code.sejda.tasks.common.GoogleFonts;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: GoogleFonts.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/GoogleFonts$GoogleFontVariant$.class */
public class GoogleFonts$GoogleFontVariant$ extends AbstractFunction2<String, String, GoogleFonts.GoogleFontVariant> implements Serializable {
    public static final GoogleFonts$GoogleFontVariant$ MODULE$ = new GoogleFonts$GoogleFontVariant$();

    public final String toString() {
        return "GoogleFontVariant";
    }

    public GoogleFonts.GoogleFontVariant apply(final String name, final String variant) {
        return new GoogleFonts.GoogleFontVariant(name, variant);
    }

    public Option<Tuple2<String, String>> unapply(final GoogleFonts.GoogleFontVariant x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2(x$0.name(), x$0.variant()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(GoogleFonts$GoogleFontVariant$.class);
    }
}
