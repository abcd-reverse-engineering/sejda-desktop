package code.util;

import scala.reflect.ScalaSignature;

/* compiled from: TimingHelpers.scala */
@ScalaSignature(bytes = "\u0006\u0005a:Q\u0001C\u0005\t\u000291Q\u0001E\u0005\t\u0002EAQ\u0001G\u0001\u0005\u0002eAqAG\u0001A\u0002\u0013\u00051\u0004C\u0004 \u0003\u0001\u0007I\u0011\u0001\u0011\t\r\u0019\n\u0001\u0015)\u0003\u001d\u0011\u00159\u0013\u0001\"\u0001)\u0011\u0015I\u0013\u0001\"\u0001+\u00035!\u0016.\\5oO\"+G\u000e]3sg*\u0011!bC\u0001\u0005kRLGNC\u0001\r\u0003\u0011\u0019w\u000eZ3\u0004\u0001A\u0011q\"A\u0007\u0002\u0013\tiA+[7j]\u001eDU\r\u001c9feN\u001c\"!\u0001\n\u0011\u0005M1R\"\u0001\u000b\u000b\u0003U\tQa]2bY\u0006L!a\u0006\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\ta\"\u0001\u0002ugV\tA\u0004\u0005\u0002\u0014;%\u0011a\u0004\u0006\u0002\u0005\u0019>tw-\u0001\u0004ug~#S-\u001d\u000b\u0003C\u0011\u0002\"a\u0005\u0012\n\u0005\r\"\"\u0001B+oSRDq!\n\u0003\u0002\u0002\u0003\u0007A$A\u0002yIE\n1\u0001^:!\u0003\u0015\u0019H/\u0019:u)\u0005\t\u0013\u0001\u0002;j[\u0016$\"!I\u0016\t\u000b1:\u0001\u0019A\u0017\u0002\u0003M\u0004\"AL\u001b\u000f\u0005=\u001a\u0004C\u0001\u0019\u0015\u001b\u0005\t$B\u0001\u001a\u000e\u0003\u0019a$o\\8u}%\u0011A\u0007F\u0001\u0007!J,G-\u001a4\n\u0005Y:$AB*ue&twM\u0003\u00025)\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/TimingHelpers.class */
public final class TimingHelpers {
    public static void time(final String s) {
        TimingHelpers$.MODULE$.time(s);
    }

    public static void start() {
        TimingHelpers$.MODULE$.start();
    }

    public static long ts() {
        return TimingHelpers$.MODULE$.ts();
    }
}
