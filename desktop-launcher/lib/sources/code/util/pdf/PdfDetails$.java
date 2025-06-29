package code.util.pdf;

import code.util.CacheUtils$;
import code.util.Loggable;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.TaskSource;
import org.slf4j.Logger;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.util.control.NonFatal$;

/* compiled from: PdfDetails.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PdfDetails$.class */
public final class PdfDetails$ implements Loggable, Serializable {
    public static final PdfDetails$ MODULE$ = new PdfDetails$();
    private static LoadingCache<PdfSource<?>, Option<PdfDetails>> cache;
    private static transient Logger logger;
    private static volatile boolean bitmap$0;
    private static volatile transient boolean bitmap$trans$0;

    static {
        Loggable.$init$(MODULE$);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$trans$0) {
                logger = logger();
                r0 = 1;
                bitmap$trans$0 = true;
            }
        }
        return logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !bitmap$trans$0 ? logger$lzycompute() : logger;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PdfDetails$.class);
    }

    private PdfDetails$() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private LoadingCache<PdfSource<?>, Option<PdfDetails>> cache$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$0) {
                cache = CacheUtils$.MODULE$.newLoadingCache(2, TimeUnit.HOURS, new CacheLoader<PdfSource<?>, Option<PdfDetails>>() { // from class: code.util.pdf.PdfDetails$$anon$1
                    public Option<PdfDetails> load(final PdfSource<?> source) {
                        return PdfDetails$.MODULE$.code$util$pdf$PdfDetails$$parse(source);
                    }
                });
                r0 = 1;
                bitmap$0 = true;
            }
        }
        return cache;
    }

    public LoadingCache<PdfSource<?>, Option<PdfDetails>> cache() {
        return !bitmap$0 ? cache$lzycompute() : cache;
    }

    public Option<PdfDetails> parseCached(final TaskSource<?> source) {
        Option<PdfDetails> option;
        try {
            if (source instanceof PdfSource) {
                option = (Option) cache().get((PdfSource) source);
            } else {
                option = None$.MODULE$;
            }
            return option;
        } catch (Throwable th) {
            if (NonFatal$.MODULE$.apply(th)) {
                return None$.MODULE$;
            }
            throw th;
        }
    }

    public Option<PdfDetails> code$util$pdf$PdfDetails$$parse(final PdfSource<?> source) throws IOException {
        Some some;
        PDDocumentHandler reader = null;
        try {
            try {
                reader = (PDDocumentHandler) source.open(new DefaultPdfSourceOpener());
                some = new Some(new PdfDetails(reader.getNumberOfPages()));
            } catch (Throwable th) {
                if (!NonFatal$.MODULE$.apply(th)) {
                    throw th;
                }
                some = None$.MODULE$;
            }
            return some;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public PdfDetails apply(final int pages) {
        return new PdfDetails(pages);
    }

    public Option<Object> unapply(final PdfDetails x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(BoxesRunTime.boxToInteger(x$0.pages()));
    }
}
