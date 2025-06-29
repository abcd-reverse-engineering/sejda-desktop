package code.limits;

import java.io.Serializable;
import org.joda.time.DateTime;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: LicenseInformation.scala */
/* loaded from: com.sejda.desktop-launcher-1.0.0.jar:code/limits/LicenseInformation$.class */
public final class LicenseInformation$ implements Serializable {
    public static final LicenseInformation$ MODULE$ = new LicenseInformation$();

    public LicenseInformation apply(final Option<String> key) {
        return new LicenseInformation(key);
    }

    public Option<Option<String>> unapply(final LicenseInformation x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.key());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LicenseInformation$.class);
    }

    private LicenseInformation$() {
    }

    private String normalize(final String key) {
        return key.replace("-", "").replace(" ", "").toUpperCase();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x007e, code lost:
    
        if (r0.equals(r1) != false) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public code.limits.LicenseInformation fromJson(final net.liftweb.json.JsonAST.JValue r5) {
        /*
            Method dump skipped, instructions count: 275
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.limits.LicenseInformation$.fromJson(net.liftweb.json.JsonAST$JValue):code.limits.LicenseInformation");
    }

    public static final /* synthetic */ DateTime $anonfun$fromJson$1(final long l) {
        return new DateTime(l);
    }
}
