package code.util.pdf;

import code.sejda.tasks.common.AnnotationFilters$;
import code.sejda.tasks.ocr.ReopenableDocumentHandler;
import code.util.Loggable;
import code.util.MemoryHelpers$;
import code.util.imageio.ImageIOUtils$;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.IIOImage;
import org.sejda.sambox.rendering.ImageType;
import org.sejda.sambox.rendering.PDFRenderer;
import org.sejda.sambox.rendering.PageDrawer;
import org.sejda.sambox.rendering.PageDrawerParameters;
import org.slf4j.Logger;
import scala.Predef$;
import scala.runtime.RichInt$;
import scala.util.Try$;

/* compiled from: PdfTiffConverter.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PdfTiffConverter$.class */
public final class PdfTiffConverter$ implements Loggable {
    public static final PdfTiffConverter$ MODULE$ = new PdfTiffConverter$();
    private static transient Logger logger;
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

    private PdfTiffConverter$() {
    }

    public File convert(final ReopenableDocumentHandler docHandler, final int dpi, final boolean renderText, final int startPage, final int endPage) throws IOException {
        long start = System.currentTimeMillis();
        File tiffFile = File.createTempFile("multipage", ".tif");
        TiffBuilder builder = new TiffBuilder(tiffFile);
        int maxPages = Math.min(docHandler.doc().getNumberOfPages(), endPage);
        try {
            RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(startPage), maxPages).foreach$mVc$sp(page -> {
                MemoryHelpers$.MODULE$.withMemoryMonitoring(() -> {
                    docHandler.reopen();
                }, () -> {
                    long start1 = System.currentTimeMillis();
                    PDFRenderer pdfRenderer = new PDFRenderer(renderText, docHandler) { // from class: code.util.pdf.PdfTiffConverter$$anon$1
                        private final boolean renderText$1;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(docHandler.doc());
                            this.renderText$1 = renderText;
                        }

                        @Override // org.sejda.sambox.rendering.PDFRenderer
                        public PageDrawer createPageDrawer(final PageDrawerParameters parameters) throws IOException {
                            PageDrawer drawer = super.createPageDrawer(parameters);
                            drawer.setTextContentRendered(this.renderText$1);
                            return drawer;
                        }
                    };
                    pdfRenderer.setAnnotationsFilter(AnnotationFilters$.MODULE$.None());
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, dpi, ImageType.RGB);
                    long jCurrentTimeMillis = System.currentTimeMillis() - start1;
                    IIOImage png = ImageIOUtils$.MODULE$.toIIOImage(bim, "png", dpi, 1.0f);
                    long start3 = System.currentTimeMillis();
                    builder.write(png);
                    long jCurrentTimeMillis2 = System.currentTimeMillis() - start3;
                });
            });
            Try$.MODULE$.apply(() -> {
                builder.close();
            });
            logger().debug(new StringBuilder(41).append("Done converting to TIFF pages ").append(startPage).append("-").append(endPage).append(", took ").append(System.currentTimeMillis() - start).append(" ms").toString());
            return tiffFile;
        } catch (Throwable th) {
            Try$.MODULE$.apply(() -> {
                builder.close();
            });
            throw th;
        }
    }
}
