package code.util.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.commons.lang3.StringUtils;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.Iterable;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ClassTag;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.util.matching.Regex;

/* compiled from: ExceptionUtils.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/exceptions/ExceptionUtils$.class */
public final class ExceptionUtils$ {
    public static final ExceptionUtils$ MODULE$ = new ExceptionUtils$();
    private static final Seq<String> diskFullStrings = package$.MODULE$.Seq().apply(ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"There is not enough space on the disk", "No space left on device", "Espace insuffisant sur le disque", "Espacio en disco insuficiente", "Za mało miejsca na dysku", "Le quota disponible est insuffisant pour traiter cette commande", "Spazio su disco insufficiente", "Na disku není dost místa", "Недостаточно места на диске", "Não existe espaço suficiente no disco", "Espaço insuficiente no disco", "磁盘空间不足", "ディスクに十分な空き領域がありません。", "Out of space on device", "Nincs elég hely a lemezen"}));

    private ExceptionUtils$() {
    }

    public boolean containsStrings(final Throwable throwable, final Seq<String> searchStrings) {
        return searchStrings.exists(s -> {
            return BoxesRunTime.boxToBoolean($anonfun$containsStrings$1(throwable, s));
        });
    }

    public static final /* synthetic */ boolean $anonfun$containsStrings$1(final Throwable throwable$1, final String s) {
        return MODULE$.containsString(throwable$1, s);
    }

    public boolean containsAllStrings(final Throwable throwable, final Seq<String> searchStrings) {
        return searchStrings.count(s -> {
            return BoxesRunTime.boxToBoolean($anonfun$containsAllStrings$1(throwable, s));
        }) == searchStrings.size();
    }

    public static final /* synthetic */ boolean $anonfun$containsAllStrings$1(final Throwable throwable$2, final String s) {
        return MODULE$.containsString(throwable$2, s);
    }

    public boolean containsStrings(final Throwable throwable, final Iterable<String> searchStrings) {
        return searchStrings.exists(s -> {
            return BoxesRunTime.boxToBoolean($anonfun$containsStrings$2(throwable, s));
        });
    }

    public static final /* synthetic */ boolean $anonfun$containsStrings$2(final Throwable throwable$3, final String s) {
        return MODULE$.containsString(throwable$3, s);
    }

    public boolean containsString(final Throwable throwable, final String searchString) {
        if (StringUtils.contains(StringUtils.trimToEmpty(throwable.getMessage()), searchString)) {
            return true;
        }
        if (throwable.getCause() != null) {
            Throwable cause = throwable.getCause();
            if (cause == null) {
                if (throwable == null) {
                    return false;
                }
            } else if (cause.equals(throwable)) {
                return false;
            }
            return containsString(throwable.getCause(), searchString);
        }
        return false;
    }

    public Seq<String> diskFullStrings() {
        return diskFullStrings;
    }

    public <T extends Exception> Option<Regex.Match> regexMatchTyped(final Throwable throwable, final Regex regex, final ClassTag<T> evidence$1) {
        while (true) {
            Some someFindFirstMatchIn = regex.findFirstMatchIn(throwable.getMessage());
            if (someFindFirstMatchIn instanceof Some) {
                Regex.Match matchFound = (Regex.Match) someFindFirstMatchIn.value();
                if (scala.reflect.package$.MODULE$.classTag(evidence$1).runtimeClass().isInstance(throwable)) {
                    return new Some(matchFound);
                }
            }
            if (throwable.getCause() == null) {
                break;
            }
            Throwable cause = throwable.getCause();
            Throwable th = throwable;
            if (cause != null) {
                if (cause.equals(th)) {
                    break;
                }
                evidence$1 = evidence$1;
                regex = regex;
                throwable = throwable.getCause();
            } else {
                if (th == null) {
                    break;
                }
                evidence$1 = evidence$1;
                regex = regex;
                throwable = throwable.getCause();
            }
        }
        return None$.MODULE$;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public Option<Regex.Match> regexMatch(final Throwable throwable, final Regex regex) throws MatchError {
        while (true) {
            Some someFindFirstMatchIn = regex.findFirstMatchIn(throwable.getMessage());
            if (someFindFirstMatchIn instanceof Some) {
                Regex.Match matchFound = (Regex.Match) someFindFirstMatchIn.value();
                return new Some(matchFound);
            }
            if (!None$.MODULE$.equals(someFindFirstMatchIn)) {
                throw new MatchError(someFindFirstMatchIn);
            }
            if (throwable.getCause() == null) {
                break;
            }
            Throwable cause = throwable.getCause();
            Throwable th = throwable;
            if (cause != null) {
                if (cause.equals(th)) {
                    break;
                }
                regex = regex;
                throwable = throwable.getCause();
            } else {
                if (th == null) {
                    break;
                }
                regex = regex;
                throwable = throwable.getCause();
            }
        }
        return None$.MODULE$;
    }

    public String asString(final Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    private final Option _find$1(final Throwable ex, final int level, final ClassTag evidence$2$1) {
        while (true) {
            Throwable th = ex;
            if (level <= 10 && ex != null) {
                if (th != null) {
                    Option optionUnapply = evidence$2$1.unapply(th);
                    if (!optionUnapply.isEmpty() && optionUnapply.get() != null && scala.reflect.package$.MODULE$.classTag(evidence$2$1).runtimeClass().isInstance(th)) {
                        return new Some(th);
                    }
                }
                level++;
                ex = ex.getCause();
            }
            return None$.MODULE$;
        }
    }

    public <T extends Exception> Option<T> find(final Throwable throwable, final ClassTag<T> evidence$2) {
        return _find$1(throwable, 1, evidence$2);
    }
}
