package code.util.pdf;

import scala.Option;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag$;
import scala.runtime.BoxesRunTime;
import scala.util.Try$;

/* compiled from: PageRanges.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PageRange$.class */
public final class PageRange$ {
    public static final PageRange$ MODULE$ = new PageRange$();

    private PageRange$() {
    }

    public Option<org.sejda.model.pdf.page.PageRange> unapply(final String s) {
        return Try$.MODULE$.apply(() -> {
            boolean z = false;
            $colon.colon colonVar = null;
            List list = Predef$.MODULE$.wrapIntArray((int[]) ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.refArrayOps(s.trim().split("-")), x$3 -> {
                return BoxesRunTime.boxToInteger($anonfun$unapply$3(x$3));
            }, ClassTag$.MODULE$.Int())).toList();
            if (list instanceof $colon.colon) {
                z = true;
                colonVar = ($colon.colon) list;
                int from = BoxesRunTime.unboxToInt(colonVar.head());
                if (Nil$.MODULE$.equals(colonVar.next$access$1()) && s.contains("-")) {
                    return new org.sejda.model.pdf.page.PageRange(from);
                }
            }
            if (z) {
                int from2 = BoxesRunTime.unboxToInt(colonVar.head());
                if (Nil$.MODULE$.equals(colonVar.next$access$1())) {
                    return new org.sejda.model.pdf.page.PageRange(from2, from2);
                }
            }
            if (z) {
                int from3 = BoxesRunTime.unboxToInt(colonVar.head());
                $colon.colon colonVarNext$access$1 = colonVar.next$access$1();
                if (colonVarNext$access$1 instanceof $colon.colon) {
                    $colon.colon colonVar2 = colonVarNext$access$1;
                    int to = BoxesRunTime.unboxToInt(colonVar2.head());
                    if (Nil$.MODULE$.equals(colonVar2.next$access$1())) {
                        return new org.sejda.model.pdf.page.PageRange(from3, to);
                    }
                }
            }
            throw new RuntimeException(new StringBuilder(12).append("Can't parse ").append(s).toString());
        }).toOption();
    }

    public static final /* synthetic */ int $anonfun$unapply$3(final String x$3) {
        return StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(x$3.trim()));
    }
}
