package code.util;

import scala.Function0;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

/* compiled from: MemoryHelpers.scala */
@ScalaSignature(bytes = "\u0006\u0005Q;Q!\u0003\u0006\t\u0002=1Q!\u0005\u0006\t\u0002IAQ\u0001H\u0001\u0005\u0002uAqAH\u0001A\u0002\u0013\u0005q\u0004C\u0004$\u0003\u0001\u0007I\u0011\u0001\u0013\t\r)\n\u0001\u0015)\u0003!\u0011\u0015Y\u0013\u0001\"\u0001-\u0011\u0015\u0011\u0014\u0001\"\u00014\u0011\u0015!\u0015\u0001\"\u0003F\u00035iU-\\8ss\"+G\u000e]3sg*\u00111\u0002D\u0001\u0005kRLGNC\u0001\u000e\u0003\u0011\u0019w\u000eZ3\u0004\u0001A\u0011\u0001#A\u0007\u0002\u0015\tiQ*Z7pefDU\r\u001c9feN\u001c2!A\n\u001a!\t!r#D\u0001\u0016\u0015\u00051\u0012!B:dC2\f\u0017B\u0001\r\u0016\u0005\u0019\te.\u001f*fMB\u0011\u0001CG\u0005\u00037)\u0011\u0001\u0002T8hO\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003=\tqc\u0018;fgRLgnZ0dY>\u001cXmX1oI~{\u0007/\u001a8\u0016\u0003\u0001\u0002\"\u0001F\u0011\n\u0005\t*\"a\u0002\"p_2,\u0017M\\\u0001\u001c?R,7\u000f^5oO~\u001bGn\\:f?\u0006tGmX8qK:|F%Z9\u0015\u0005\u0015B\u0003C\u0001\u000b'\u0013\t9SC\u0001\u0003V]&$\bbB\u0015\u0005\u0003\u0003\u0005\r\u0001I\u0001\u0004q\u0012\n\u0014\u0001G0uKN$\u0018N\\4`G2|7/Z0b]\u0012|v\u000e]3oA\u0005Yq/\u001b;i)\u0016\u001cH/\u001b8h)\t)S\u0006C\u0003/\r\u0001\u0007q&\u0001\u0002g]B\u0019A\u0003M\u0013\n\u0005E*\"!\u0003$v]\u000e$\u0018n\u001c81\u0003Q9\u0018\u000e\u001e5NK6|'/_'p]&$xN]5oOV\u0011Ag\u000e\u000b\u0004k\u0001\u0013\u0005C\u0001\u001c8\u0019\u0001!Q\u0001O\u0004C\u0002e\u0012\u0011\u0001V\t\u0003uu\u0002\"\u0001F\u001e\n\u0005q*\"a\u0002(pi\"Lgn\u001a\t\u0003)yJ!aP\u000b\u0003\u0007\u0005s\u0017\u0010C\u0003B\u000f\u0001\u0007q&A\b`G2|7/Z!oIJ+w\u000e]3o\u0011\u0015qs\u00011\u0001D!\r!\u0002'N\u0001\u0006SN|u*\u0014\u000b\u0003A\u0019CQa\u0012\u0005A\u0002!\u000b!!\u001a=\u0011\u0005%\u000bfB\u0001&P\u001d\tYe*D\u0001M\u0015\tie\"\u0001\u0004=e>|GOP\u0005\u0002-%\u0011\u0001+F\u0001\ba\u0006\u001c7.Y4f\u0013\t\u00116KA\u0005UQJ|w/\u00192mK*\u0011\u0001+\u0006")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/MemoryHelpers.class */
public final class MemoryHelpers {
    public static <T> T withMemoryMonitoring(Function0<BoxedUnit> function0, Function0<T> function02) {
        return (T) MemoryHelpers$.MODULE$.withMemoryMonitoring(function0, function02);
    }

    public static void withTesting(final Function0<BoxedUnit> fn) {
        MemoryHelpers$.MODULE$.withTesting(fn);
    }

    public static boolean _testing_close_and_open() {
        return MemoryHelpers$.MODULE$._testing_close_and_open();
    }
}
