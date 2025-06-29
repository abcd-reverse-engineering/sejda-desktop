package code.util.sort;

import scala.reflect.ScalaSignature;

/* compiled from: AlphanumComparator.scala */
@ScalaSignature(bytes = "\u0006\u0005e:QAB\u0004\t\u000291Q\u0001E\u0004\t\u0002EAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0002mAQAL\u0001\u0005\u0002=BQAM\u0001\u0005\u0002M\n!#\u00117qQ\u0006tW/\\\"p[B\f'/\u0019;pe*\u0011\u0001\"C\u0001\u0005g>\u0014HO\u0003\u0002\u000b\u0017\u0005!Q\u000f^5m\u0015\u0005a\u0011\u0001B2pI\u0016\u001c\u0001\u0001\u0005\u0002\u0010\u00035\tqA\u0001\nBYBD\u0017M\\;n\u0007>l\u0007/\u0019:bi>\u00148CA\u0001\u0013!\t\u0019b#D\u0001\u0015\u0015\u0005)\u0012!B:dC2\f\u0017BA\f\u0015\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012AD\u0001\u0010M&dWM\\1nKN{'\u000f^5oOR\u0019Ad\b\u0017\u0011\u0005Mi\u0012B\u0001\u0010\u0015\u0005\u001d\u0011un\u001c7fC:DQ\u0001I\u0002A\u0002\u0005\n!a]\u0019\u0011\u0005\tJcBA\u0012(!\t!C#D\u0001&\u0015\t1S\"\u0001\u0004=e>|GOP\u0005\u0003QQ\ta\u0001\u0015:fI\u00164\u0017B\u0001\u0016,\u0005\u0019\u0019FO]5oO*\u0011\u0001\u0006\u0006\u0005\u0006[\r\u0001\r!I\u0001\u0003gJ\nqa]8si&tw\rF\u0002\u001daEBQ\u0001\t\u0003A\u0002\u0005BQ!\f\u0003A\u0002\u0005\nqaY8na\u0006\u0014X\rF\u00025oa\u0002\"aE\u001b\n\u0005Y\"\"aA%oi\")\u0001%\u0002a\u0001C!)Q&\u0002a\u0001C\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/sort/AlphanumComparator.class */
public final class AlphanumComparator {
    public static int compare(final String s1, final String s2) {
        return AlphanumComparator$.MODULE$.compare(s1, s2);
    }

    public static boolean sorting(final String s1, final String s2) {
        return AlphanumComparator$.MODULE$.sorting(s1, s2);
    }

    public static boolean filenameSorting(final String s1, final String s2) {
        return AlphanumComparator$.MODULE$.filenameSorting(s1, s2);
    }
}
