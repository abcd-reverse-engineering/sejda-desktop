package code.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import scala.reflect.ScalaSignature;

/* compiled from: ThreadUtils.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0019;QAB\u0004\t\u000211QAD\u0004\t\u0002=AQAF\u0001\u0005\u0002]AQ\u0001G\u0001\u0005\u0002eAQ!J\u0001\u0005\u0002\u0019BQaO\u0001\u0005\u0002q\n1\u0002\u00165sK\u0006$W\u000b^5mg*\u0011\u0001\"C\u0001\u0005kRLGNC\u0001\u000b\u0003\u0011\u0019w\u000eZ3\u0004\u0001A\u0011Q\"A\u0007\u0002\u000f\tYA\u000b\u001b:fC\u0012,F/\u001b7t'\t\t\u0001\u0003\u0005\u0002\u0012)5\t!CC\u0001\u0014\u0003\u0015\u00198-\u00197b\u0013\t)\"C\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00031\tA\u0001Z;naR\t!\u0004\u0005\u0002\u001cE9\u0011A\u0004\t\t\u0003;Ii\u0011A\b\u0006\u0003?-\ta\u0001\u0010:p_Rt\u0014BA\u0011\u0013\u0003\u0019\u0001&/\u001a3fM&\u00111\u0005\n\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u0005\u0012\u0012A\u00058b[\u0016$G\u000b\u001b:fC\u00124\u0015m\u0019;pef$\"aJ\u001d\u0013\u0007!R#G\u0002\u0003*\t\u00019#\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004CA\u00161\u001b\u0005a#BA\u0017/\u0003\u0011a\u0017M\\4\u000b\u0003=\nAA[1wC&\u0011\u0011\u0007\f\u0002\u0007\u001f\nTWm\u0019;\u0011\u0005M:T\"\u0001\u001b\u000b\u0005U2\u0014AC2p]\u000e,(O]3oi*\u0011\u0001BL\u0005\u0003qQ\u0012Q\u0002\u00165sK\u0006$g)Y2u_JL\b\"\u0002\u001e\u0005\u0001\u0004Q\u0012\u0001\u00028b[\u0016\f\u0001D\\3x\r&DX\r\u001a#bK6|g\u000e\u00165sK\u0006$\u0007k\\8m)\ri\u0004)\u0012\t\u0003gyJ!a\u0010\u001b\u0003\u001f\u0015CXmY;u_J\u001cVM\u001d<jG\u0016DQ!Q\u0003A\u0002\t\u000bQaY8v]R\u0004\"!E\"\n\u0005\u0011\u0013\"aA%oi\")!(\u0002a\u00015\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ThreadUtils.class */
public final class ThreadUtils {
    public static ExecutorService newFixedDaemonThreadPool(final int count, final String name) {
        return ThreadUtils$.MODULE$.newFixedDaemonThreadPool(count, name);
    }

    public static ThreadFactory namedThreadFactory(final String name) {
        return ThreadUtils$.MODULE$.namedThreadFactory(name);
    }

    public static String dump() {
        return ThreadUtils$.MODULE$.dump();
    }
}
