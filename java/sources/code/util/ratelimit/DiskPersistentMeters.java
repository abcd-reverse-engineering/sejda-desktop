package code.util.ratelimit;

import java.io.File;
import scala.Option;
import scala.Tuple2;
import scala.collection.immutable.List;
import scala.reflect.ScalaSignature;

/* compiled from: RateLimiting.scala */
@ScalaSignature(bytes = "\u0006\u0005a;Q!\u0003\u0006\t\u0002E1Qa\u0005\u0006\t\u0002QAQaG\u0001\u0005\u0002qAq!H\u0001C\u0002\u0013\u0005a\u0004\u0003\u00040\u0003\u0001\u0006Ia\b\u0005\u0006a\u0005!\t!\r\u0005\u0006s\u0005!\tA\u000f\u0005\u0006\u0019\u0006!\t!\u0014\u0005\u0006%\u0006!\taU\u0001\u0015\t&\u001c8\u000eU3sg&\u001cH/\u001a8u\u001b\u0016$XM]:\u000b\u0005-a\u0011!\u0003:bi\u0016d\u0017.\\5u\u0015\tia\"\u0001\u0003vi&d'\"A\b\u0002\t\r|G-Z\u0002\u0001!\t\u0011\u0012!D\u0001\u000b\u0005Q!\u0015n]6QKJ\u001c\u0018n\u001d;f]RlU\r^3sgN\u0011\u0011!\u0006\t\u0003-ei\u0011a\u0006\u0006\u00021\u0005)1oY1mC&\u0011!d\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005\t\u0012!\u00037pG\u0006$\u0018n\u001c8t+\u0005y\u0002c\u0001\u0011&O5\t\u0011E\u0003\u0002#G\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003I]\t!bY8mY\u0016\u001cG/[8o\u0013\t1\u0013E\u0001\u0003MSN$\bC\u0001\u0015.\u001b\u0005I#B\u0001\u0016,\u0003\u0011a\u0017M\\4\u000b\u00031\nAA[1wC&\u0011a&\u000b\u0002\u0007'R\u0014\u0018N\\4\u0002\u00151|7-\u0019;j_:\u001c\b%A\tfq&\u001cH/\u001b8h\u0019>\u001c\u0017\r^5p]N,\u0012A\r\t\u0004A\u0015\u001a\u0004C\u0001\u001b8\u001b\u0005)$B\u0001\u001c,\u0003\tIw.\u0003\u00029k\t!a)\u001b7f\u0003\u0011aw.\u00193\u0015\u0005m:\u0005c\u0001\f=}%\u0011Qh\u0006\u0002\u0007\u001fB$\u0018n\u001c8\u0011\tYy\u0014\tR\u0005\u0003\u0001^\u0011a\u0001V;qY\u0016\u0014\u0004C\u0001\fC\u0013\t\u0019uCA\u0002J]R\u0004\"AF#\n\u0005\u0019;\"\u0001\u0002'p]\u001eDQ\u0001\u0013\u0004A\u0002%\u000bQ!\\3uKJ\u0004\"A\u0005&\n\u0005-S!!B'fi\u0016\u0014\u0018\u0001B:bm\u0016$\"AT)\u0011\u0005Yy\u0015B\u0001)\u0018\u0005\u001d\u0011un\u001c7fC:DQ\u0001S\u0004A\u0002%\u000bQA]3tKR$\"\u0001V,\u0011\u0005Y)\u0016B\u0001,\u0018\u0005\u0011)f.\u001b;\t\u000b!C\u0001\u0019A%")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ratelimit/DiskPersistentMeters.class */
public final class DiskPersistentMeters {
    public static void reset(final Meter meter) {
        DiskPersistentMeters$.MODULE$.reset(meter);
    }

    public static boolean save(final Meter meter) {
        return DiskPersistentMeters$.MODULE$.save(meter);
    }

    public static Option<Tuple2<Object, Object>> load(final Meter meter) {
        return DiskPersistentMeters$.MODULE$.load(meter);
    }

    public static List<File> existingLocations() {
        return DiskPersistentMeters$.MODULE$.existingLocations();
    }

    public static List<String> locations() {
        return DiskPersistentMeters$.MODULE$.locations();
    }
}
