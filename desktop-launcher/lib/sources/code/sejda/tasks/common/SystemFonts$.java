package code.sejda.tasks.common;

import scala.Option;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.HashMap$;

/* compiled from: SystemFonts.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/SystemFonts$.class */
public final class SystemFonts$ {
    public static final SystemFonts$ MODULE$ = new SystemFonts$();
    private static final HashMap<String, String> cachedFailureReasons = (HashMap) HashMap$.MODULE$.apply(Nil$.MODULE$);

    private SystemFonts$() {
    }

    public HashMap<String, String> cachedFailureReasons() {
        return cachedFailureReasons;
    }

    public Option<String> addFailureReason(final String key, final String reason) {
        return cachedFailureReasons().put(key, reason);
    }

    public Option<String> getFailureReason(final String key) {
        return cachedFailureReasons().get(key);
    }
}
