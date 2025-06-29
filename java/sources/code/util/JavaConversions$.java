package code.util;

import java.util.LinkedHashSet;
import scala.collection.immutable.Seq;
import scala.runtime.BoxesRunTime;

/* compiled from: JavaConversions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/JavaConversions$.class */
public final class JavaConversions$ {
    public static final JavaConversions$ MODULE$ = new JavaConversions$();

    private JavaConversions$() {
    }

    public <T> LinkedHashSet<T> asJavaLinkedHashSet(final Seq<T> s) {
        LinkedHashSet res = new LinkedHashSet();
        s.foreach(elem -> {
            return BoxesRunTime.boxToBoolean(res.add(elem));
        });
        return res;
    }
}
