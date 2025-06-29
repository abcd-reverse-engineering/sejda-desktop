package code.sejda.tasks.common.param;

import org.sejda.model.pdf.page.PageRange;
import scala.Predef$;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.JavaConverters$;
import scala.collection.mutable.Set;
import scala.math.Ordering$Int$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

/* compiled from: WithPageSelectionParams.scala */
@ScalaSignature(bytes = "\u0006\u000513q!\u0002\u0004\u0011\u0002\u0007\u0005\u0011\u0003C\u0003\u0019\u0001\u0011\u0005\u0011\u0004C\u0003\u001e\u0001\u0019\u0005a\u0004C\u00035\u0001\u0011\u0005Q\u0007C\u0003F\u0001\u0011\u0005aIA\fXSRD\u0007+Y4f'\u0016dWm\u0019;j_:\u0004\u0016M]1ng*\u0011q\u0001C\u0001\u0006a\u0006\u0014\u0018-\u001c\u0006\u0003\u0013)\taaY8n[>t'BA\u0006\r\u0003\u0015!\u0018m]6t\u0015\tia\"A\u0003tK*$\u0017MC\u0001\u0010\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0011\u0001A\u0005\t\u0003'Yi\u0011\u0001\u0006\u0006\u0002+\u0005)1oY1mC&\u0011q\u0003\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005Q\u0002CA\n\u001c\u0013\taBC\u0001\u0003V]&$\u0018!\u00049bO\u0016\u001cV\r\\3di&|g.F\u0001 !\r\u0001SeJ\u0007\u0002C)\u0011!eI\u0001\b[V$\u0018M\u00197f\u0015\t!C#\u0001\u0006d_2dWm\u0019;j_:L!AJ\u0011\u0003\u0007M+G\u000f\u0005\u0002)e5\t\u0011F\u0003\u0002+W\u0005!\u0001/Y4f\u0015\taS&A\u0002qI\u001aT!AL\u0018\u0002\u000b5|G-\u001a7\u000b\u00055\u0001$\"A\u0019\u0002\u0007=\u0014x-\u0003\u00024S\tI\u0001+Y4f%\u0006tw-Z\u0001\tO\u0016$\b+Y4fgR\u0011ag\u0011\t\u0004oy\u0002eB\u0001\u001d=!\tID#D\u0001;\u0015\tY\u0004#\u0001\u0004=e>|GOP\u0005\u0003{Q\ta\u0001\u0015:fI\u00164\u0017B\u0001\u0014@\u0015\tiD\u0003\u0005\u0002\u0014\u0003&\u0011!\t\u0006\u0002\u0004\u0013:$\b\"\u0002#\u0004\u0001\u0004\u0001\u0015!\u0005;pi\u0006dg*^7cKJ|e\rU1hK\u0006a\u0011\r\u001a3QC\u001e,'+\u00198hKR\u0011qI\u0013\t\u0003'!K!!\u0013\u000b\u0003\u000f\t{w\u000e\\3b]\")1\n\u0002a\u0001O\u0005)!/\u00198hK\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/param/WithPageSelectionParams.class */
public interface WithPageSelectionParams {
    Set<PageRange> pageSelection();

    static void $init$(final WithPageSelectionParams $this) {
    }

    default scala.collection.immutable.Set<Object> getPages(final int totalNumberOfPage) {
        if (pageSelection().isEmpty()) {
            return ((IterableOnceOps) ((IterableOps) JavaConverters$.MODULE$.asScalaSetConverter(new PageRange(1).getPages(totalNumberOfPage)).asScala()).map(x$1 -> {
                return BoxesRunTime.boxToInteger($anonfun$getPages$1(x$1));
            })).toSet();
        }
        return ((IterableOnceOps) ((IterableOnceOps) pageSelection().flatMap(x$2 -> {
            return (Set) ((IterableOps) JavaConverters$.MODULE$.asScalaSetConverter(x$2.getPages(totalNumberOfPage)).asScala()).map(x$3 -> {
                return BoxesRunTime.boxToInteger($anonfun$getPages$3(x$3));
            });
        })).toSeq().sorted(Ordering$Int$.MODULE$)).toSet();
    }

    static /* synthetic */ int $anonfun$getPages$1(final Integer x$1) {
        return Predef$.MODULE$.Integer2int(x$1);
    }

    static /* synthetic */ int $anonfun$getPages$3(final Integer x$3) {
        return Predef$.MODULE$.Integer2int(x$3);
    }

    default boolean addPageRange(final PageRange range) {
        return pageSelection().add(range);
    }
}
