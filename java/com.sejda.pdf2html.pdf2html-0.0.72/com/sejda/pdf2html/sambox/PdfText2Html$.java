package com.sejda.pdf2html.sambox;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.sejda.pdf2html.ImageExtractor;
import com.sejda.pdf2html.Images2HTML;
import com.sejda.pdf2html.PDFText2HTML;
import com.sejda.pdf2html.StatisticAnalyzer;
import com.sejda.pdf2html.util.Loggable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import javax.imageio.ImageIO;
import org.sejda.io.SeekableSource;
import org.sejda.sambox.input.PDFParser;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.$less$colon$less$;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.collection.StringOps$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.Try$;
import scala.util.control.NonFatal$;

/* compiled from: PdfText2Html.scala */
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/PdfText2Html$.class */
public final class PdfText2Html$ implements Loggable {
    public static final PdfText2Html$ MODULE$ = new PdfText2Html$();
    private static transient Logger logger;

    static {
        PdfText2Html$ pdfText2Html$ = MODULE$;
        pdfText2Html$.com$sejda$pdf2html$util$Loggable$_setter_$logger_$eq(LoggerFactory.getLogger(pdfText2Html$.getClass()));
        ImageIO.scanForPlugins();
    }

    @Override // com.sejda.pdf2html.util.Loggable
    public Logger logger() {
        return logger;
    }

    @Override // com.sejda.pdf2html.util.Loggable
    public void com$sejda$pdf2html$util$Loggable$_setter_$logger_$eq(final Logger x$1) {
        logger = x$1;
    }

    private PdfText2Html$() {
    }

    public boolean convert$default$3() {
        return true;
    }

    public int convert$default$4() {
        return 30;
    }

    public boolean convert$default$5() {
        return true;
    }

    public int convert$default$6() {
        return Integer.MAX_VALUE;
    }

    public boolean convert$default$7() {
        return false;
    }

    public Option<String> convert$default$8() {
        return None$.MODULE$;
    }

    public boolean convert$default$9() {
        return true;
    }

    public int convert$default$10() {
        return 960;
    }

    public int convert$default$11() {
        return 800;
    }

    public Option<String> convert$default$12() {
        return None$.MODULE$;
    }

    private static final StatisticAnalyzer createStatisticParser$1(final PDDocument doc$1) {
        StatisticAnalyzer statisticParser = new StatisticAnalyzer();
        OutputStreamWriter out = new OutputStreamWriter(ByteStreams.nullOutputStream());
        try {
            statisticParser.writeText(doc$1, out);
            return statisticParser;
        } finally {
            Closeables.close(out, true);
        }
    }

    private static final ImageExtractor createImageExtractor$1(final File outputFile$1, final int maxImagesPerDocument$1, final int maxPagesPerDocument$1, final boolean resizeImages$1, final int maxImageWidth$1, final int maxImageHeight$1, final PDDocument doc$1) {
        File imagesStorageFolder = new File(outputFile$1.getParentFile(), "images");
        imagesStorageFolder.mkdirs();
        Images2HTML imageExtractor = new Images2HTML(imagesStorageFolder, maxImagesPerDocument$1, maxPagesPerDocument$1, resizeImages$1, maxImageWidth$1, maxImageHeight$1);
        return imageExtractor.processDocument(doc$1);
    }

    private static final HeaderFooterRemover createHeaderFooterRemover$1(final PDDocument doc$1) {
        HeaderFooterRemover remover = new HeaderFooterRemover();
        remover.train(doc$1);
        return remover;
    }

    private final void process$1(final PDDocument doc, final File outputFile$1, final int maxImagesPerDocument$1, final int maxPagesPerDocument$1, final boolean resizeImages$1, final int maxImageWidth$1, final int maxImageHeight$1, final boolean withImages$1, final boolean withToc$1, final Option fallbackTitle$1, final boolean removeOverflowHyphenatedWords$1) {
        BufferedWriter out = new BufferedWriter(new FileWriter(outputFile$1));
        try {
            PDFText2HTML converter = new PDFText2HTML(createStatisticParser$1(doc), createHeaderFooterRemover$1(doc));
            if (withImages$1) {
                converter.setImageExtractor(createImageExtractor$1(outputFile$1, maxImagesPerDocument$1, maxPagesPerDocument$1, resizeImages$1, maxImageWidth$1, maxImageHeight$1, doc));
            }
            boolean isOCRProduced = BoxesRunTime.unboxToBoolean(Try$.MODULE$.apply(() -> {
                return doc.getDocumentInformation().getProducer().toLowerCase().contains("paper capture");
            }).getOrElse(() -> {
                return false;
            }));
            if (isOCRProduced) {
                logger().debug("Detected OCR produced document");
            }
            converter.setOCRProduced(isOCRProduced);
            converter.setMaxPages(maxPagesPerDocument$1);
            converter.writeText(doc, out);
            if (withToc$1) {
                File tocFile = new File(outputFile$1.getParent(), "toc.html");
                Function1 anchorFactory = pageNumber -> {
                    return $anonfun$convert$3(outputFile$1, BoxesRunTime.unboxToInt(pageNumber));
                };
                new PdfText2ToC(doc, anchorFactory).writeHtml(tocFile);
                File opfFile = new File(outputFile$1.getParent(), StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(outputFile$1.getName()), ".html", ".opf"));
                new OPFFileWriter(doc, fallbackTitle$1).write(opfFile, outputFile$1, tocFile);
            }
            if (removeOverflowHyphenatedWords$1) {
                try {
                    HyphenatedOverflowWordsProcessor$.MODULE$.process(outputFile$1);
                    VariousFormattingImprovementsProcessor$.MODULE$.process(outputFile$1);
                } catch (Throwable th) {
                    if (!NonFatal$.MODULE$.apply(th)) {
                        throw th;
                    }
                    logger().error("Failed to remove hyphenated overflow words", th);
                    BoxedUnit boxedUnit = BoxedUnit.UNIT;
                }
            }
        } finally {
            closeQuietly(doc);
            Closeables.close(out, true);
        }
    }

    public static final /* synthetic */ String $anonfun$convert$3(final File outputFile$1, final int pageNumber) {
        return new StringBuilder(5).append(outputFile$1.getName()).append("#page").append(pageNumber).toString();
    }

    public void convert(final SeekableSource in, final File outputFile, final boolean withImages, final int maxImagesPerDocument, final boolean resizeImages, final int maxPagesPerDocument, final boolean withToc, final Option<String> fallbackTitle, final boolean removeOverflowHyphenatedWords, final int maxImageWidth, final int maxImageHeight, final Option<String> password) {
        PDDocument doc = PDFParser.parse(in, (String) password.orNull($less$colon$less$.MODULE$.refl()));
        process$1(doc, outputFile, maxImagesPerDocument, maxPagesPerDocument, resizeImages, maxImageWidth, maxImageHeight, withImages, withToc, fallbackTitle, removeOverflowHyphenatedWords);
    }

    private void closeQuietly(final PDDocument closeable) {
        Try$.MODULE$.apply(() -> {
            closeable.close();
        });
    }
}
