package code.sejda.tasks.common;

import code.util.Loggable;
import org.sejda.core.support.util.StringUtils;
import org.sejda.sambox.util.BidiUtils;
import org.slf4j.Logger;
import scala.Predef$;
import scala.collection.StringOps$;
import scala.collection.immutable.Set;
import scala.runtime.BooleanRef;
import scala.runtime.BoxedUnit;
import scala.runtime.ObjectRef;
import scala.runtime.ScalaRunTime$;

/* compiled from: FontsHelper.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/FontsHelper$.class */
public final class FontsHelper$ implements Loggable {
    public static final FontsHelper$ MODULE$ = new FontsHelper$();
    private static transient Logger logger;
    private static volatile transient boolean bitmap$trans$0;

    static {
        Loggable.$init$(MODULE$);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$trans$0) {
                logger = logger();
                r0 = 1;
                bitmap$trans$0 = true;
            }
        }
        return logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !bitmap$trans$0 ? logger$lzycompute() : logger;
    }

    private FontsHelper$() {
    }

    public String arabicShaping(final String text) {
        return StringUtils.shapeArabicIf(text);
    }

    public String visualToLogical(final String text) {
        return BidiUtils.visualToLogical(text);
    }

    public String familyNameWithoutSubsetPrefix(final String s) {
        if (s != null && s.contains("+") && s.indexOf("+") == 6) {
            return s.substring(7);
        }
        return s;
    }

    public String normalizeName(final String name) {
        ObjectRef result = ObjectRef.create(familyNameWithoutSubsetPrefix(familyNameWithoutSubsetPrefix(name.toLowerCase())));
        result.elem = StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString((String) result.elem), ",", "")), "-", "")), " ", "");
        BooleanRef booleanRefCreate = BooleanRef.create(true);
        Set suffixes = (Set) Predef$.MODULE$.Set().apply(ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"bold", "oblique", "italic", "regular", "black", "mt"}));
        while (booleanRefCreate.elem) {
            booleanRefCreate.elem = false;
            suffixes.foreach(s -> {
                $anonfun$normalizeName$1(result, booleanRefCreate, s);
                return BoxedUnit.UNIT;
            });
        }
        return (String) result.elem;
    }

    public static final /* synthetic */ void $anonfun$normalizeName$1(final ObjectRef result$1, final BooleanRef continue$1, final String s) {
        if (((String) result$1.elem).endsWith(s)) {
            continue$1.elem = true;
            result$1.elem = StringOps$.MODULE$.dropRight$extension(Predef$.MODULE$.augmentString((String) result$1.elem), s.length());
        }
    }
}
