package code.util.pdf;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.collection.ArrayOps$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: PageRanges.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PageRanges$.class */
public final class PageRanges$ implements Serializable {
    public static final PageRanges$ MODULE$ = new PageRanges$();

    public PageRanges apply(final Seq<org.sejda.model.pdf.page.PageRange> ranges) {
        return new PageRanges(ranges);
    }

    public Option<Seq<org.sejda.model.pdf.page.PageRange>> unapply(final PageRanges x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.ranges());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PageRanges$.class);
    }

    private PageRanges$() {
    }

    public Option<PageRanges> unapply(final String s) {
        List list = Predef$.MODULE$.wrapRefArray((Object[]) ArrayOps$.MODULE$.flatMap$extension(Predef$.MODULE$.refArrayOps(s.trim().split(",")), s2 -> {
            return PageRange$.MODULE$.unapply(s2);
        }, ClassTag$.MODULE$.apply(org.sejda.model.pdf.page.PageRange.class))).toList();
        return Nil$.MODULE$.equals(list) ? None$.MODULE$ : new Some(new PageRanges(list));
    }

    public Seq<org.sejda.model.pdf.page.PageRange> fromString(final String s) {
        return (Seq) unapply(s).map(x$2 -> {
            return x$2.ranges().toSeq();
        }).getOrElse(() -> {
            return package$.MODULE$.Seq().empty();
        });
    }
}
