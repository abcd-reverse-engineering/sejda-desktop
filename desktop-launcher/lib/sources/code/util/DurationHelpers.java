package code.util;

import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

/* compiled from: DurationHelpers.scala */
@ScalaSignature(bytes = "\u0006\u0005m:Q\u0001D\u0007\t\u0002I1Q\u0001F\u0007\t\u0002UAQ\u0001H\u0001\u0005\u0002uAqAH\u0001C\u0002\u0013\u0005q\u0004\u0003\u0004)\u0003\u0001\u0006I\u0001\t\u0005\bS\u0005\u0011\r\u0011\"\u0001 \u0011\u0019Q\u0013\u0001)A\u0005A!91&\u0001b\u0001\n\u0003y\u0002B\u0002\u0017\u0002A\u0003%\u0001\u0005C\u0003.\u0003\u0011\u0005a\u0006C\u00035\u0003\u0011\u0005Q\u0007C\u00038\u0003\u0011\u0005\u0001(A\bEkJ\fG/[8o\u0011\u0016d\u0007/\u001a:t\u0015\tqq\"\u0001\u0003vi&d'\"\u0001\t\u0002\t\r|G-Z\u0002\u0001!\t\u0019\u0012!D\u0001\u000e\u0005=!UO]1uS>t\u0007*\u001a7qKJ\u001c8CA\u0001\u0017!\t9\"$D\u0001\u0019\u0015\u0005I\u0012!B:dC2\f\u0017BA\u000e\u0019\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012AE\u0001\ba\u0016\u0014\bj\\;s+\u0005\u0001\u0003CA\u0011'\u001b\u0005\u0011#BA\u0012%\u0003!!WO]1uS>t'BA\u0013\u0019\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003O\t\u0012aBR5oSR,G)\u001e:bi&|g.\u0001\u0005qKJDu.\u001e:!\u0003\u0019\u0001XM\u001d#bs\u00069\u0001/\u001a:ECf\u0004\u0013!\u00039fe6Kg.\u001e;f\u0003)\u0001XM]'j]V$X\rI\u0001\u000ba\u0016\u00148+Z2p]\u0012\u001cHC\u0001\u00110\u0011\u0015\u0001\u0014\u00021\u00012\u0003\u0005\u0019\bCA\f3\u0013\t\u0019\u0004DA\u0002J]R\fqa]3d_:$7\u000f\u0006\u0002!m!)\u0001G\u0003a\u0001c\u00059Q.\u001b8vi\u0016\u001cHC\u0001\u0011:\u0011\u0015Q4\u00021\u00012\u0003\u0005i\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/DurationHelpers.class */
public final class DurationHelpers {
    public static FiniteDuration minutes(final int m) {
        return DurationHelpers$.MODULE$.minutes(m);
    }

    public static FiniteDuration seconds(final int s) {
        return DurationHelpers$.MODULE$.seconds(s);
    }

    public static FiniteDuration perSeconds(final int s) {
        return DurationHelpers$.MODULE$.perSeconds(s);
    }

    public static FiniteDuration perMinute() {
        return DurationHelpers$.MODULE$.perMinute();
    }

    public static FiniteDuration perDay() {
        return DurationHelpers$.MODULE$.perDay();
    }

    public static FiniteDuration perHour() {
        return DurationHelpers$.MODULE$.perHour();
    }
}
