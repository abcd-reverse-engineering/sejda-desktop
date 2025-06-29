package code.sejda.tasks.deskew;

import org.sejda.model.pdf.page.PageRange;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.HashMap$;
import scala.collection.mutable.Set;
import scala.collection.mutable.Set$;

/* compiled from: DeskewParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/deskew/DeskewParameters$.class */
public final class DeskewParameters$ {
    public static final DeskewParameters$ MODULE$ = new DeskewParameters$();

    public double $lessinit$greater$default$1() {
        return 0.2d;
    }

    private DeskewParameters$() {
    }

    public double $lessinit$greater$default$2() {
        return 19.0d;
    }

    public Set<PageRange> $lessinit$greater$default$3() {
        return (Set) Set$.MODULE$.empty();
    }

    public HashMap<Object, Object> $lessinit$greater$default$4() {
        return HashMap$.MODULE$.empty();
    }
}
