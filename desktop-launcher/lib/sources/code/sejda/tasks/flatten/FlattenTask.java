package code.sejda.tasks.flatten;

import code.sejda.tasks.common.AcroFormsHelper;
import code.sejda.tasks.common.AnnotationFilters$;
import code.sejda.tasks.common.BaseBaseTask;
import code.util.MemoryHelpers$;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.image.ImagesToPdfDocumentConverter;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.FileSource;
import org.sejda.model.input.PdfFileSource;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.Source;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.rendering.ImageType;
import org.sejda.sambox.rendering.PDFRenderer;
import scala.MatchError;
import scala.Predef$;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.IntRef;
import scala.runtime.ObjectRef;
import scala.runtime.RichInt$;

/* compiled from: FlattenTask.scala */
@ScalaSignature(bytes = "\u0006\u0005)3A!\u0002\u0004\u0001\u001f!)\u0001\u0005\u0001C\u0001C!)1\u0005\u0001C\u0001I!)Q\b\u0001C!}!Qq\b\u0001I\u0001\u0002\u0003\u0005I\u0011\u0001!\u0003\u0017\u0019c\u0017\r\u001e;f]R\u000b7o\u001b\u0006\u0003\u000f!\tqA\u001a7biR,gN\u0003\u0002\n\u0015\u0005)A/Y:lg*\u00111\u0002D\u0001\u0006g\u0016TG-\u0019\u0006\u0002\u001b\u0005!1m\u001c3f\u0007\u0001\u00192\u0001\u0001\t\u001b!\r\tBCF\u0007\u0002%)\u00111\u0003C\u0001\u0007G>lWn\u001c8\n\u0005U\u0011\"\u0001\u0004\"bg\u0016\u0014\u0015m]3UCN\\\u0007CA\f\u0019\u001b\u00051\u0011BA\r\u0007\u0005E1E.\u0019;uK:\u0004\u0016M]1nKR,'o\u001d\t\u00037yi\u0011\u0001\b\u0006\u0003;1\tA!\u001e;jY&\u0011q\u0004\b\u0002\t\u0019><w-\u00192mK\u00061A(\u001b8jiz\"\u0012A\t\t\u0003/\u0001\tq!\u001a=fGV$X\r\u0006\u0002&WA\u0011a%K\u0007\u0002O)\t\u0001&A\u0003tG\u0006d\u0017-\u0003\u0002+O\t!QK\\5u\u0011\u0015a#\u00011\u0001\u0017\u0003)\u0001\u0018M]1nKR,'o\u001d\u0015\u0004\u00059b\u0004c\u0001\u00140c%\u0011\u0001g\n\u0002\u0007i\"\u0014xn^:\u0011\u0005IRT\"A\u001a\u000b\u0005Q*\u0014!C3yG\u0016\u0004H/[8o\u0015\t1t'A\u0003n_\u0012,GN\u0003\u0002\fq)\t\u0011(A\u0002pe\u001eL!aO\u001a\u0003\u001bQ\u000b7o[#yG\u0016\u0004H/[8oG\u0005\t\u0014!B1gi\u0016\u0014H#A\u0013\u00025A\u0014x\u000e^3di\u0016$G%\u001a=fGV$\u0018n\u001c8D_:$X\r\u001f;\u0015\u0005\u0005CE#\u0001\"\u0011\u0005\r3U\"\u0001#\u000b\u0005\u0015+\u0014\u0001\u0002;bg.L!a\u0012#\u0003)Q\u000b7o[#yK\u000e,H/[8o\u0007>tG/\u001a=u\u0011\u001dIE!!AA\u0002\t\n1\u0001\u001f\u00132\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/flatten/FlattenTask.class */
public class FlattenTask extends BaseBaseTask<FlattenParameters> {
    @Override // org.sejda.model.task.Task
    public void after() {
    }

    @Override // org.sejda.model.task.Task
    public void execute(final FlattenParameters parameters) throws TaskException {
        int totalSteps = parameters.getSourceList().size();
        IntRef currentStep = IntRef.create(0);
        MultipleOutputWriter outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext());
        DefaultPdfSourceOpener opener = new DefaultPdfSourceOpener(executionContext());
        ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(parameters.getSourceList()).asScala()).foreach(source -> {
            $anonfun$execute$1(this, currentStep, opener, parameters, outputWriter, totalSteps, source);
            return BoxedUnit.UNIT;
        });
        parameters.getOutput().accept(outputWriter);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$execute$1(final FlattenTask $this, final IntRef currentStep$1, final DefaultPdfSourceOpener opener$1, final FlattenParameters parameters$1, final MultipleOutputWriter outputWriter$1, final int totalSteps$1, final PdfSource source) throws IllegalStateException, MatchError, IOException, TaskException {
        $this.logger().debug(new StringBuilder(11).append("Processing ").append(source).toString());
        currentStep$1.elem++;
        PDDocumentHandler originalDocHandler = (PDDocumentHandler) source.open(opener$1);
        File tempIntermediateFile = IOUtils.createTemporaryBufferWithName(source.getName());
        AcroFormsHelper acroFormsHelper = new AcroFormsHelper(originalDocHandler.getUnderlyingPDDocument());
        if (acroFormsHelper.xfaIsDynamic()) {
            String message = "Document contains dynamic XFA elements, which are not supported";
            if (parameters$1.getSourceList().size() > 1) {
                message = new StringBuilder(2).append(source.getName()).append(": ").append(message).toString();
            }
            $this.emitTaskWarning(message);
        }
        Seq warnings = acroFormsHelper.flatten();
        warnings.foreach(s -> {
            $this.emitTaskWarning(s);
            return BoxedUnit.UNIT;
        });
        originalDocHandler.savePDDocument(tempIntermediateFile, parameters$1.getOutput().getEncryptionAtRestPolicy());
        PdfFileSource tempIntermediateSource = PdfFileSource.newInstanceNoPassword(tempIntermediateFile);
        tempIntermediateSource.setEncryptionAtRestPolicy(parameters$1.getOutput().getEncryptionAtRestPolicy());
        ObjectRef docHandler = ObjectRef.create((PDDocumentHandler) tempIntermediateSource.open(opener$1));
        ObjectRef doc = ObjectRef.create(((PDDocumentHandler) docHandler.elem).getUnderlyingPDDocument());
        int numPages = ((PDDocument) doc.elem).getNumberOfPages();
        File outputFile = IOUtils.createTemporaryBuffer();
        $this.logger().debug(new StringBuilder(17).append("Flattening mode: ").append(parameters$1.mode()).toString());
        FlattenMode flattenModeMode = parameters$1.mode();
        if (FlattenMode$OnlyForms$.MODULE$.equals(flattenModeMode)) {
            ((PDDocumentHandler) docHandler.elem).savePDDocument(outputFile, parameters$1.getOutput().getEncryptionAtRestPolicy());
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            if (!FlattenMode$All$.MODULE$.equals(flattenModeMode)) {
                throw new MatchError(flattenModeMode);
            }
            int dpi = parameters$1.dpi();
            ImagesToPdfDocumentConverter imageToPdfConverter = new ImagesToPdfDocumentConverter();
            RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), numPages).foreach$mVc$sp(pageIndex -> {
                MemoryHelpers$.MODULE$.withMemoryMonitoring(() -> {
                    closeAndReopen$1(docHandler, tempIntermediateSource, opener$1, doc);
                }, () -> {
                    long startMs = System.currentTimeMillis();
                    PDPage page = ((PDDocument) doc.elem).getPage(pageIndex);
                    PDFRenderer pdfRenderer = new PDFRenderer((PDDocument) doc.elem);
                    pdfRenderer.setAnnotationsFilter(AnnotationFilters$.MODULE$.NoStickyNotesPopups());
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(pageIndex, dpi, ImageType.RGB);
                    File pngFile = IOUtils.createTemporaryBufferWithName(new StringBuilder(9).append("page-").append(pageIndex).append(".png").toString());
                    ImageIO.write(bim, "png", pngFile);
                    try {
                        Source source2 = new FileSource(pngFile);
                        PDRectangle originalCropBox = page.getCropBox().rotate(page.getRotation());
                        PDRectangle pageSize = new PDRectangle(originalCropBox.getWidth(), originalCropBox.getHeight());
                        imageToPdfConverter.addPages(source2, pageSize, null, 0.0f);
                        FileUtils.deleteQuietly(pngFile);
                        $this.logger().info(new StringBuilder(23).append("Flattening page ").append(pageIndex + 1).append(" took ").append((System.currentTimeMillis() - startMs) / 1000).append(OperatorName.CLOSE_AND_STROKE).toString());
                    } catch (Throwable th) {
                        FileUtils.deleteQuietly(pngFile);
                        throw th;
                    }
                });
            });
            PDDocumentHandler converted = imageToPdfConverter.getDocumentHandler();
            converted.setDocumentTitle(((PDDocument) doc.elem).getDocumentInformation().getTitle());
            converted.savePDDocument(outputFile, parameters$1.getOutput().getEncryptionAtRestPolicy());
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
        String outName = NameGenerator.nameGenerator(parameters$1.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(currentStep$1.elem));
        outputWriter$1.addOutput(FileOutput.file(outputFile).name(outName));
        ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).stepsCompleted(currentStep$1.elem).outOf(totalSteps$1);
        ((PDDocumentHandler) docHandler.elem).close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void closeAndReopen$1(final ObjectRef docHandler$1, final PdfFileSource tempIntermediateSource$1, final DefaultPdfSourceOpener opener$1, final ObjectRef doc$1) throws IOException {
        ((PDDocumentHandler) docHandler$1.elem).close();
        docHandler$1.elem = (PDDocumentHandler) tempIntermediateSource$1.open(opener$1);
        doc$1.elem = ((PDDocumentHandler) docHandler$1.elem).getUnderlyingPDDocument();
    }

    public /* synthetic */ TaskExecutionContext protected$executionContext(final FlattenTask x$1) {
        return x$1.executionContext();
    }
}
