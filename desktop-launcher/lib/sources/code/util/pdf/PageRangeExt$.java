package code.util.pdf;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: PageRanges.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PageRangeExt$.class */
public final class PageRangeExt$ extends AbstractFunction1<org.sejda.model.pdf.page.PageRange, PageRangeExt> implements Serializable {
    public static final PageRangeExt$ MODULE$ = new PageRangeExt$();

    public final String toString() {
        return "PageRangeExt";
    }

    public PageRangeExt apply(final org.sejda.model.pdf.page.PageRange underlying) {
        return new PageRangeExt(underlying);
    }

    public Option<org.sejda.model.pdf.page.PageRange> unapply(final PageRangeExt x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.underlying());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PageRangeExt$.class);
    }

    private PageRangeExt$() {
    }
}
