package code.util.sort;

import org.apache.commons.io.FilenameUtils;
import scala.Predef$;
import scala.collection.StringOps$;
import scala.collection.immutable.List;
import scala.runtime.BoxesRunTime;
import scala.util.control.NonFatal$;

/* compiled from: AlphanumComparator.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/sort/AlphanumComparator$.class */
public final class AlphanumComparator$ {
    public static final AlphanumComparator$ MODULE$ = new AlphanumComparator$();

    private AlphanumComparator$() {
    }

    public boolean filenameSorting(final String s1, final String s2) {
        return compare(FilenameUtils.getBaseName(s1), FilenameUtils.getBaseName(s2)) < 0;
    }

    public boolean sorting(final String s1, final String s2) {
        return compare(s1, s2) < 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x0177, code lost:
    
        if (r12 == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x017e, code lost:
    
        if (r8.isEmpty() == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0181, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0189, code lost:
    
        if (false != r0) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0190, code lost:
    
        if (r8.isEmpty() != false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0193, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0198, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:?, code lost:
    
        return 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final int _compare$1(final scala.collection.immutable.List r7, final scala.collection.immutable.List r8) {
        /*
            Method dump skipped, instructions count: 410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.util.sort.AlphanumComparator$._compare$1(scala.collection.immutable.List, scala.collection.immutable.List):int");
    }

    private static final boolean isNotDigit$1(final String s) {
        return toIntMaybe$1(s) instanceof String;
    }

    private static final Object toIntMaybe$1(final String s) {
        try {
            return BoxesRunTime.boxToInteger(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(s)));
        } catch (Throwable th) {
            if (NonFatal$.MODULE$.apply(th)) {
                return s;
            }
            throw th;
        }
    }

    public int compare(final String s1, final String s2) {
        List f1 = Predef$.MODULE$.wrapRefArray(s1.split("\\W+")).toList();
        List f2 = Predef$.MODULE$.wrapRefArray(s2.split("\\W+")).toList();
        return _compare$1(f1, f2);
    }
}
