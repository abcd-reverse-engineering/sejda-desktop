package code.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.reflect.ScalaSignature;

/* compiled from: Loggable.scala */
@ScalaSignature(bytes = "\u0006\u0005\r2qa\u0001\u0003\u0011\u0002\u0007\u0005\u0011\u0002C\u0003\u0011\u0001\u0011\u0005\u0011\u0003\u0003\u0005\u0016\u0001!\u0015\r\u0011\"\u0005\u0017\u0005!aunZ4bE2,'BA\u0003\u0007\u0003\u0011)H/\u001b7\u000b\u0003\u001d\tAaY8eK\u000e\u00011C\u0001\u0001\u000b!\tYa\"D\u0001\r\u0015\u0005i\u0011!B:dC2\f\u0017BA\b\r\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\"\u0012A\u0005\t\u0003\u0017MI!\u0001\u0006\u0007\u0003\tUs\u0017\u000e^\u0001\u0007Y><w-\u001a:\u0016\u0003]\u0001\"\u0001G\u000f\u000e\u0003eQ!AG\u000e\u0002\u000bMdg\r\u000e6\u000b\u0003q\t1a\u001c:h\u0013\tq\u0012D\u0001\u0004M_\u001e<WM\u001d\u0015\u0003\u0005\u0001\u0002\"aC\u0011\n\u0005\tb!!\u0003;sC:\u001c\u0018.\u001a8u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/Loggable.class */
public interface Loggable {
    static void $init$(final Loggable $this) {
    }

    default Logger logger() {
        return LoggerFactory.getLogger(getClass());
    }
}
