package code.sejda.tasks.deskew;

import code.sejda.tasks.common.AnnotationFilters$;
import code.util.Loggable;
import code.util.MemoryHelpers$;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfFileSource;
import org.sejda.model.input.PdfSource;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.rendering.ImageType;
import org.sejda.sambox.rendering.PDFRenderer;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import scala.Predef$;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.ObjectRef;
import scala.util.control.NonFatal$;

/* compiled from: DeskewTask.scala */
@ScalaSignature(bytes = "\u0006\u000554Aa\u0002\u0005\u0001#!)q\u0005\u0001C\u0001Q!)!\u0006\u0001C\u0001W!)q\b\u0001C\u0005\u0001\")\u0011\u0002\u0001C\u0005\u001d\")1\r\u0001C!I\"QQ\r\u0001I\u0001\u0002\u0003\u0005I\u0011\u00014\u0003\u0015\u0011+7o[3x)\u0006\u001c8N\u0003\u0002\n\u0015\u00051A-Z:lK^T!a\u0003\u0007\u0002\u000bQ\f7o[:\u000b\u00055q\u0011!B:fU\u0012\f'\"A\b\u0002\t\r|G-Z\u0002\u0001'\r\u0001!#\t\t\u0004'miR\"\u0001\u000b\u000b\u0005U1\u0012\u0001\u0002;bg.T!a\u0006\r\u0002\u000b5|G-\u001a7\u000b\u00055I\"\"\u0001\u000e\u0002\u0007=\u0014x-\u0003\u0002\u001d)\tA!)Y:f)\u0006\u001c8\u000e\u0005\u0002\u001f?5\t\u0001\"\u0003\u0002!\u0011\t\u0001B)Z:lK^\u0004\u0016M]1nKR,'o\u001d\t\u0003E\u0015j\u0011a\t\u0006\u0003I9\tA!\u001e;jY&\u0011ae\t\u0002\t\u0019><w-\u00192mK\u00061A(\u001b8jiz\"\u0012!\u000b\t\u0003=\u0001\tq!\u001a=fGV$X\r\u0006\u0002-eA\u0011Q\u0006M\u0007\u0002])\tq&A\u0003tG\u0006d\u0017-\u0003\u00022]\t!QK\\5u\u0011\u0015\u0019$\u00011\u0001\u001e\u0003\u0019\u0001\u0018M]1ng\"\u001a!!\u000e \u0011\u000752\u0004(\u0003\u00028]\t1A\u000f\u001b:poN\u0004\"!\u000f\u001f\u000e\u0003iR!a\u000f\f\u0002\u0013\u0015D8-\u001a9uS>t\u0017BA\u001f;\u00055!\u0016m]6Fq\u000e,\u0007\u000f^5p]\u000e\n\u0001(A\u0006f[&$x+\u0019:oS:<GC\u0001\u0017B\u0011\u0015\u00115\u00011\u0001D\u0003\u001d9\u0018M\u001d8j]\u001e\u0004\"\u0001R&\u000f\u0005\u0015K\u0005C\u0001$/\u001b\u00059%B\u0001%\u0011\u0003\u0019a$o\\8u}%\u0011!JL\u0001\u0007!J,G-\u001a4\n\u00051k%AB*ue&twM\u0003\u0002K]Q!AfT-_\u0011\u0015\u0001F\u00011\u0001R\u0003\r!wn\u0019\t\u0003%^k\u0011a\u0015\u0006\u0003)V\u000bq\u0001\u001d3n_\u0012,GN\u0003\u0002W1\u000511/Y7c_bL!\u0001W*\u0003\u0015A#Ei\\2v[\u0016tG\u000fC\u0003[\t\u0001\u00071,\u0001\u0003qC\u001e,\u0007C\u0001*]\u0013\ti6K\u0001\u0004Q\tB\u000bw-\u001a\u0005\u0006?\u0012\u0001\r\u0001Y\u0001\u0006C:<G.\u001a\t\u0003[\u0005L!A\u0019\u0018\u0003\r\u0011{WO\u00197f\u0003\u0015\tg\r^3s)\u0005a\u0013A\u00079s_R,7\r^3eI\u0015DXmY;uS>t7i\u001c8uKb$HCA4l)\u0005A\u0007CA\nj\u0013\tQGC\u0001\u000bUCN\\W\t_3dkRLwN\\\"p]R,\u0007\u0010\u001e\u0005\bY\u001a\t\t\u00111\u0001*\u0003\rAH%\r")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/deskew/DeskewTask.class */
public class DeskewTask extends BaseTask<DeskewParameters> implements Loggable {
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    @Override // org.sejda.model.task.Task
    public void after() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.deskew.DeskewTask] */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$trans$0) {
                this.logger = logger();
                r0 = this;
                r0.bitmap$trans$0 = true;
            }
        }
        return this.logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !this.bitmap$trans$0 ? logger$lzycompute() : this.logger;
    }

    public DeskewTask() {
        Loggable.$init$(this);
    }

    @Override // org.sejda.model.task.Task
    public void execute(final DeskewParameters params) throws TaskException {
        int totalSteps = params.getSourceList().size();
        IntRef currentStep = IntRef.create(0);
        MultipleOutputWriter outputWriter = OutputWriters.newMultipleOutputWriter(params.getExistingOutputPolicy(), executionContext());
        DefaultPdfSourceOpener opener = new DefaultPdfSourceOpener(executionContext());
        IntRef deskewedPages = IntRef.create(0);
        ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(params.getSourceList()).asScala()).foreach(source -> {
            $anonfun$execute$1(this, params, currentStep, opener, deskewedPages, outputWriter, totalSteps, source);
            return BoxedUnit.UNIT;
        });
        if (deskewedPages.elem == 0) {
            emitWarning("No pages were deskewed");
        }
        params.getOutput().accept(outputWriter);
    }

    public static final /* synthetic */ void $anonfun$execute$1(final DeskewTask $this, final DeskewParameters params$1, final IntRef currentStep$1, final DefaultPdfSourceOpener opener$1, final IntRef deskewedPages$1, final MultipleOutputWriter outputWriter$1, final int totalSteps$1, final PdfSource source) throws IllegalStateException, IOException, TaskException {
        $this.logger().debug(new StringBuilder(49).append("Deskewing ").append(source).append(" with minThreshold: ").append(params$1.minThreshold()).append(" and maxThreshold: ").append(params$1.maxThreshold()).toString());
        currentStep$1.elem++;
        ObjectRef failedPages = ObjectRef.create((Set) Predef$.MODULE$.Set().apply(Nil$.MODULE$));
        ObjectRef docHandler = ObjectRef.create((PDDocumentHandler) source.open(opener$1));
        ObjectRef doc = ObjectRef.create(((PDDocumentHandler) docHandler.elem).getUnderlyingPDDocument());
        int numPages = ((PDDocument) doc.elem).getNumberOfPages();
        File outputFile = IOUtils.createTemporaryBuffer();
        int dpi = 144;
        ObjectRef tmpFile1 = ObjectRef.create(IOUtils.createTemporaryBuffer());
        ObjectRef tmpFile2 = ObjectRef.create(IOUtils.createTemporaryBuffer());
        params$1.getPages(numPages).foreach(pageNum -> {
            int pageIndex = pageNum - 1;
            MemoryHelpers$.MODULE$.withMemoryMonitoring(() -> {
                closeAndReopen$1(docHandler, tmpFile2, params$1, tmpFile1, opener$1, doc);
            }, () -> {
                double dUnboxToDouble;
                try {
                    long startMs = System.currentTimeMillis();
                    PDPage page = ((PDDocument) doc.elem).getPage(pageIndex);
                    if (params$1.angles().nonEmpty()) {
                        dUnboxToDouble = BoxesRunTime.unboxToDouble(params$1.angles().getOrElse(BoxesRunTime.boxToInteger(pageNum), () -> {
                            return 0.0d;
                        }));
                    } else {
                        PDFRenderer pdfRenderer = new PDFRenderer((PDDocument) doc.elem);
                        pdfRenderer.setAnnotationsFilter(AnnotationFilters$.MODULE$.NoStickyNotesPopups());
                        BufferedImage bim = pdfRenderer.renderImageWithDPI(pageIndex, dpi, ImageType.BINARY);
                        double _angle = new ImageDeskew(bim).getSkewAngle();
                        if (Math.abs(_angle) < params$1.minThreshold()) {
                            $this.logger().info(new StringBuilder(50).append("Not deskewing page ").append(pageNum).append(", angle: ").append(_angle).append(" under min threshold: ").append(params$1.minThreshold()).toString());
                            _angle = 0.0d;
                        } else if (Math.abs(_angle) > params$1.maxThreshold()) {
                            $this.logger().info(new StringBuilder(49).append("Not deskewing page ").append(pageNum).append(", angle: ").append(_angle).append(" over max threshold: ").append(params$1.maxThreshold()).toString());
                            _angle = 0.0d;
                        }
                        dUnboxToDouble = _angle;
                    }
                    double angle = dUnboxToDouble;
                    if (angle != 0) {
                        $this.deskew((PDDocument) doc.elem, page, angle);
                        deskewedPages$1.elem++;
                        $this.logger().info(new StringBuilder(33).append("Deskewing page ").append(pageNum).append(" by angle: ").append(angle).append(" took ").append((System.currentTimeMillis() - startMs) / 1000).append(OperatorName.CLOSE_AND_STROKE).toString());
                    }
                } catch (Throwable th) {
                    if (!NonFatal$.MODULE$.apply(th)) {
                        throw th;
                    }
                    $this.logger().warn(new StringBuilder(22).append("Failed deskewing page ").append(pageNum).toString(), th);
                    failedPages.elem = ((Set) failedPages.elem).$plus(BoxesRunTime.boxToInteger(pageNum));
                    BoxedUnit boxedUnit = BoxedUnit.UNIT;
                }
            });
        });
        ((PDDocumentHandler) docHandler.elem).savePDDocument(outputFile, params$1.getOutput().getEncryptionAtRestPolicy());
        String outName = NameGenerator.nameGenerator(params$1.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(currentStep$1.elem));
        outputWriter$1.addOutput(FileOutput.file(outputFile).name(outName));
        if (((Set) failedPages.elem).nonEmpty()) {
            $this.emitWarning(new StringBuilder(28).append(source.getName()).append(": Could not process ").append(((Set) failedPages.elem).size()).append(" pages: ").append(((Set) failedPages.elem).mkString(",")).toString());
        }
        ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).stepsCompleted(currentStep$1.elem).outOf(totalSteps$1);
        ((PDDocumentHandler) docHandler.elem).close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void closeAndReopen$1(final ObjectRef docHandler$1, final ObjectRef tmpFile2$1, final DeskewParameters params$1, final ObjectRef tmpFile1$1, final DefaultPdfSourceOpener opener$1, final ObjectRef doc$1) throws IllegalStateException, IOException, TaskException {
        ((PDDocumentHandler) docHandler$1.elem).savePDDocument((File) tmpFile2$1.elem, params$1.getOutput().getEncryptionAtRestPolicy());
        ((PDDocumentHandler) docHandler$1.elem).close();
        File tmpFile = (File) tmpFile2$1.elem;
        tmpFile2$1.elem = (File) tmpFile1$1.elem;
        tmpFile1$1.elem = tmpFile;
        PdfFileSource tmpSource = PdfFileSource.newInstanceNoPassword((File) tmpFile1$1.elem);
        tmpSource.setEncryptionAtRestPolicy(params$1.getOutput().getEncryptionAtRestPolicy());
        docHandler$1.elem = (PDDocumentHandler) tmpSource.open(opener$1);
        doc$1.elem = ((PDDocumentHandler) docHandler$1.elem).getUnderlyingPDDocument();
    }

    public /* synthetic */ TaskExecutionContext protected$executionContext(final DeskewTask x$1) {
        return x$1.executionContext();
    }

    private void emitWarning(final String warning) {
        logger().info(warning);
        ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(warning);
    }

    private void deskew(final PDDocument doc, final PDPage page, final double angle) throws IOException {
        PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.PREPEND, false, false);
        PDRectangle cropBox = page.getCropBox();
        float tx = (cropBox.getLowerLeftX() + cropBox.getUpperRightX()) / 2;
        float ty = (cropBox.getLowerLeftY() + cropBox.getUpperRightY()) / 2;
        cs.transform(Matrix.getTranslateInstance(tx, ty));
        cs.transform(Matrix.getRotateInstance(Math.toRadians(angle), 0.0f, 0.0f));
        cs.transform(Matrix.getTranslateInstance(-tx, -ty));
        cs.close();
    }
}
