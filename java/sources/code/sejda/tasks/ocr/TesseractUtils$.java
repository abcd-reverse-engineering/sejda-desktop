package code.sejda.tasks.ocr;

import code.util.Loggable;
import code.util.ThreadUtils$;
import code.util.pdf.PdfTiffConverter$;
import java.io.File;
import java.io.IOException;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.pro.component.Overlayer;
import org.sejda.model.SejdaFileExtensions;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfFileSource;
import org.sejda.model.input.PdfSource;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.BuildFrom$;
import scala.collection.JavaConverters$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.concurrent.Await$;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.concurrent.duration.Duration$;
import scala.package$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichInt$;
import scala.runtime.ScalaRunTime$;
import scala.util.control.NonFatal$;

/* compiled from: TesseractUtils.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/ocr/TesseractUtils$.class */
public final class TesseractUtils$ implements Loggable {
    public static final TesseractUtils$ MODULE$ = new TesseractUtils$();
    private static final ExecutionContextExecutor futuresExecutionContext;
    private static final int batchSize;
    private static transient Logger logger;
    private static volatile transient boolean bitmap$trans$0;

    static {
        Loggable.$init$(MODULE$);
        futuresExecutionContext = ExecutionContext$.MODULE$.fromExecutor(ThreadUtils$.MODULE$.newFixedDaemonThreadPool(1, "tesseract-futures"));
        batchSize = 5;
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

    private TesseractUtils$() {
    }

    public ExecutionContextExecutor futuresExecutionContext() {
        return futuresExecutionContext;
    }

    public int batchSize() {
        return batchSize;
    }

    public Option<String> performOcr$default$2() {
        return None$.MODULE$;
    }

    public File performOcr(final PdfSource<?> source, final Option<String> language, final EncryptionAtRestPolicy encryptionAtRest, final TaskExecutionContext executionContext) throws IllegalStateException, IOException, TaskException {
        ReopenableDocumentHandler docHandler = new ReopenableDocumentHandler(source);
        int numberOfPages = docHandler.doc().getNumberOfPages();
        IndexedSeq textLayerPdfFutures = RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), numberOfPages, batchSize()).map(i -> {
            return $anonfun$performOcr$1(docHandler, language, BoxesRunTime.unboxToInt(i));
        });
        docHandler.close();
        Overlayer overlay = new Overlayer();
        IndexedSeq overlayFiles = (IndexedSeq) Await$.MODULE$.result(Future$.MODULE$.sequence(textLayerPdfFutures, BuildFrom$.MODULE$.buildFromIterableOps(), futuresExecutionContext()), Duration$.MODULE$.Inf());
        PDDocumentHandler baseDocHandler = (PDDocumentHandler) source.open(new DefaultPdfSourceOpener(executionContext));
        PDDocument baseDoc = baseDocHandler.getUnderlyingPDDocument();
        IndexedSeq overlayDocs = (IndexedSeq) overlayFiles.map(x0$1 -> {
            if (x0$1 != null) {
                int startPage = x0$1._1$mcI$sp();
                File file = (File) x0$1._2();
                PdfFileSource source2 = PdfFileSource.newInstanceNoPassword(file);
                return new Tuple2(BoxesRunTime.boxToInteger(startPage), source2.open(new DefaultPdfSourceOpener(executionContext)));
            }
            throw new MatchError(x0$1);
        });
        overlayDocs.foreach(x0$2 -> {
            if (x0$2 != null) {
                int startPage = x0$2._1$mcI$sp();
                PDDocumentHandler overlayDocHandler = (PDDocumentHandler) x0$2._2();
                return overlay.overlayDocuments(baseDoc, overlayDocHandler.getUnderlyingPDDocument(), startPage);
            }
            throw new MatchError(x0$2);
        });
        File tmpFile = IOUtils.createTemporaryBuffer();
        baseDocHandler.savePDDocument(tmpFile, encryptionAtRest);
        baseDocHandler.close();
        overlayDocs.foreach(x0$3 -> {
            $anonfun$performOcr$4(x0$3);
            return BoxedUnit.UNIT;
        });
        overlayFiles.foreach(x0$4 -> {
            return BoxesRunTime.boxToBoolean($anonfun$performOcr$5(x0$4));
        });
        return tmpFile;
    }

    public static final /* synthetic */ Future $anonfun$performOcr$1(final ReopenableDocumentHandler docHandler$1, final Option language$1, final int i) throws IOException {
        docHandler$1.reopen();
        int endPage = i + MODULE$.batchSize();
        docHandler$1.doc();
        File tiffFile = MODULE$.convertPages(docHandler$1, i, endPage);
        return MODULE$.ocrPages(tiffFile, i, endPage, language$1);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$performOcr$4(final Tuple2 x0$3) throws MatchError, IOException {
        if (x0$3 != null) {
            PDDocumentHandler overlayDocHandler = (PDDocumentHandler) x0$3._2();
            overlayDocHandler.close();
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        throw new MatchError(x0$3);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ boolean $anonfun$performOcr$5(final Tuple2 x0$4) throws MatchError {
        if (x0$4 != null) {
            File file = (File) x0$4._2();
            return file.delete();
        }
        throw new MatchError(x0$4);
    }

    public File convertPages(final ReopenableDocumentHandler docHandler, final int startPage, final int endPage) throws IOException {
        File inputTiffFile = PdfTiffConverter$.MODULE$.convert(docHandler, 300, false, startPage, endPage);
        inputTiffFile.deleteOnExit();
        return inputTiffFile;
    }

    public Future<Tuple2<Object, File>> ocrPages(final File inputTiffFile, final int startPage, final int endPage, final Option<String> language) {
        return Future$.MODULE$.apply(() -> {
            File tmpOcrFolder = IOUtils.createTemporaryFolder();
            File tmpOcrBase = new File(tmpOcrFolder, "tmp-ocr");
            String tesseractExecutable = (String) Option$.MODULE$.apply(System.getProperty("SEJDA_TESSERACT_PATH")).orElse(() -> {
                return Option$.MODULE$.apply(System.getenv("SEJDA_TESSERACT_PATH"));
            }).getOrElse(() -> {
                return "tesseract";
            });
            List params = (List) package$.MODULE$.List().apply(ScalaRunTime$.MODULE$.wrapRefArray(new String[]{tesseractExecutable, inputTiffFile.getAbsolutePath(), tmpOcrBase.getAbsolutePath(), "-l", (String) language.getOrElse(() -> {
                return "eng";
            }), "-c", "textonly_pdf=1", SejdaFileExtensions.PDF_EXTENSION}));
            MODULE$.logger().debug(params.mkString(" "));
            ProcessBuilder processBuilder = new ProcessBuilder((java.util.List<String>) JavaConverters$.MODULE$.seqAsJavaListConverter(params).asJava()).inheritIO();
            processBuilder.environment().put("OMP_THREAD_LIMIT", "1");
            long startMs = System.currentTimeMillis();
            final Process process = processBuilder.start();
            Runtime.getRuntime().addShutdownHook(new Thread(process) { // from class: code.sejda.tasks.ocr.TesseractUtils$$anon$1
                private final Process process$1;

                {
                    this.process$1 = process;
                }

                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    TesseractUtils$.code$sejda$tasks$ocr$TesseractUtils$$forciblyDestroy$1(this.process$1);
                }
            });
            try {
                int exitCode = process.waitFor();
                MODULE$.logger().debug(new StringBuilder(41).append("Tesseract done ").append(startPage).append("-").append(endPage).append(" with exit code ").append(exitCode).append(", took ").append(System.currentTimeMillis() - startMs).append("ms").toString());
                if (exitCode != 0) {
                    throw new TaskException("OCR process failed");
                }
                code$sejda$tasks$ocr$TesseractUtils$$forciblyDestroy$1(process);
                inputTiffFile.delete();
                File textLayerPdfFile = new File(new StringBuilder(4).append(tmpOcrBase.getAbsolutePath()).append(".pdf").toString());
                if (!textLayerPdfFile.exists()) {
                    throw new TaskException("No OCR output found");
                }
                textLayerPdfFile.deleteOnExit();
                return new Tuple2(BoxesRunTime.boxToInteger(startPage), textLayerPdfFile);
            } catch (Throwable th) {
                code$sejda$tasks$ocr$TesseractUtils$$forciblyDestroy$1(process);
                inputTiffFile.delete();
                throw th;
            }
        }, futuresExecutionContext());
    }

    public static final void code$sejda$tasks$ocr$TesseractUtils$$forciblyDestroy$1(final Process process$1) {
        try {
            if (process$1.isAlive()) {
                MODULE$.logger().debug("Destroying forcibly tesseract process");
                process$1.destroyForcibly();
            }
        } catch (Throwable th) {
            if (!NonFatal$.MODULE$.apply(th)) {
                throw th;
            }
            MODULE$.logger().error("Failed to destroy forcibly tesseract process");
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        }
    }
}
