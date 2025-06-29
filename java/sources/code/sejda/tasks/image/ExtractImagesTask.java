package code.sejda.tasks.image;

import code.sejda.tasks.common.ClientFacingTaskException;
import code.sejda.tasks.common.image.PdfImageExtractingStreamEngine;
import code.util.Loggable;
import code.util.MemoryHelpers$;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import scala.Predef$;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.ObjectRef;
import scala.runtime.RichInt$;

/* compiled from: ExtractImagesTask.scala */
@ScalaSignature(bytes = "\u0006\u0005\u001d3A!\u0002\u0004\u0001\u001f!)Q\u0005\u0001C\u0001M!)\u0001\u0006\u0001C\u0001S!)Q\b\u0001C!}!Qq\b\u0001I\u0001\u0002\u0003\u0005I\u0011\u0001!\u0003#\u0015CHO]1di&k\u0017mZ3t)\u0006\u001c8N\u0003\u0002\b\u0011\u0005)\u0011.\\1hK*\u0011\u0011BC\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003\u00171\tQa]3kI\u0006T\u0011!D\u0001\u0005G>$Wm\u0001\u0001\u0014\u0007\u0001\u0001r\u0004E\u0002\u00123mi\u0011A\u0005\u0006\u0003'Q\tA\u0001^1tW*\u0011QCF\u0001\u0006[>$W\r\u001c\u0006\u0003\u0017]Q\u0011\u0001G\u0001\u0004_J<\u0017B\u0001\u000e\u0013\u0005!\u0011\u0015m]3UCN\\\u0007C\u0001\u000f\u001e\u001b\u00051\u0011B\u0001\u0010\u0007\u0005])\u0005\u0010\u001e:bGRLU.Y4fgB\u000b'/Y7fi\u0016\u00148\u000f\u0005\u0002!G5\t\u0011E\u0003\u0002#\u0019\u0005!Q\u000f^5m\u0013\t!\u0013E\u0001\u0005M_\u001e<\u0017M\u00197f\u0003\u0019a\u0014N\\5u}Q\tq\u0005\u0005\u0002\u001d\u0001\u00059Q\r_3dkR,GC\u0001\u00161!\tYc&D\u0001-\u0015\u0005i\u0013!B:dC2\f\u0017BA\u0018-\u0005\u0011)f.\u001b;\t\u000bE\u0012\u0001\u0019A\u000e\u0002\u0015A\f'/Y7fi\u0016\u00148\u000fK\u0002\u0003gq\u00022a\u000b\u001b7\u0013\t)DF\u0001\u0004uQJ|wo\u001d\t\u0003oij\u0011\u0001\u000f\u0006\u0003sQ\t\u0011\"\u001a=dKB$\u0018n\u001c8\n\u0005mB$!\u0004+bg.,\u0005pY3qi&|gnI\u00017\u0003\u0015\tg\r^3s)\u0005Q\u0013A\u00079s_R,7\r^3eI\u0015DXmY;uS>t7i\u001c8uKb$HCA!F)\u0005\u0011\u0005CA\tD\u0013\t!%C\u0001\u000bUCN\\W\t_3dkRLwN\\\"p]R,\u0007\u0010\u001e\u0005\b\r\u0012\t\t\u00111\u0001(\u0003\rAH%\r")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/image/ExtractImagesTask.class */
public class ExtractImagesTask extends BaseTask<ExtractImagesParameters> implements Loggable {
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    @Override // org.sejda.model.task.Task
    public void after() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.image.ExtractImagesTask] */
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

    public ExtractImagesTask() {
        Loggable.$init$(this);
    }

    @Override // org.sejda.model.task.Task
    public void execute(final ExtractImagesParameters parameters) throws TaskException {
        int totalSteps = parameters.getSourceList().size();
        IntRef currentStep = IntRef.create(0);
        MultipleOutputWriter outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext());
        DefaultPdfSourceOpener opener = new DefaultPdfSourceOpener(executionContext());
        ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(parameters.getSourceList()).asScala()).foreach(source -> {
            $anonfun$execute$1(this, currentStep, opener, parameters, outputWriter, totalSteps, source);
            return BoxedUnit.UNIT;
        });
        if (executionContext().outputDocumentsCounter() == 0) {
            throw new ClientFacingTaskException("No images found");
        }
        parameters.getOutput().accept(outputWriter);
    }

    public static final /* synthetic */ void $anonfun$execute$1(final ExtractImagesTask $this, final IntRef currentStep$1, final DefaultPdfSourceOpener opener$1, final ExtractImagesParameters parameters$1, final MultipleOutputWriter outputWriter$1, final int totalSteps$1, final PdfSource source) throws IOException {
        currentStep$1.elem++;
        $this.logger().debug(new StringBuilder(11).append("Processing ").append(source).toString());
        ObjectRef docHandler = ObjectRef.create((PDDocumentHandler) source.open(opener$1));
        ObjectRef doc = ObjectRef.create(((PDDocumentHandler) docHandler.elem).getUnderlyingPDDocument());
        IntRef currentImageCounter = IntRef.create(0);
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), ((PDDocument) doc.elem).getNumberOfPages()).foreach$mVc$sp(pageIndex -> {
            MemoryHelpers$.MODULE$.withMemoryMonitoring(() -> {
                closeAndReopen$1(docHandler, source, opener$1, doc);
            }, () -> {
                PdfImageExtractingStreamEngine engine = new PdfImageExtractingStreamEngine((PDDocument) doc.elem, (image, pageNum) -> {
                    $anonfun$execute$5($this, parameters$1, currentImageCounter, source, outputWriter$1, currentStep$1, totalSteps$1, image, BoxesRunTime.unboxToInt(pageNum));
                    return BoxedUnit.UNIT;
                });
                engine.processPage(pageIndex + 1);
            });
        });
        ((PDDocumentHandler) docHandler.elem).close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void closeAndReopen$1(final ObjectRef docHandler$1, final PdfSource source$1, final DefaultPdfSourceOpener opener$1, final ObjectRef doc$1) throws IOException {
        ((PDDocumentHandler) docHandler$1.elem).close();
        docHandler$1.elem = (PDDocumentHandler) source$1.open(opener$1);
        doc$1.elem = ((PDDocumentHandler) docHandler$1.elem).getUnderlyingPDDocument();
    }

    public static final /* synthetic */ void $anonfun$execute$5(final ExtractImagesTask $this, final ExtractImagesParameters parameters$1, final IntRef currentImageCounter$1, final PdfSource source$1, final MultipleOutputWriter outputWriter$1, final IntRef currentStep$1, final int totalSteps$1, final BufferedImage image, final int pageNum) throws TaskIOException {
        File outputFile = IOUtils.createTemporaryBuffer();
        OutputStream outputStream = parameters$1.getOutput().getEncryptionAtRestPolicy().encrypt(new FileOutputStream(outputFile));
        try {
            ImageIO.write(image, "png", outputStream);
            org.sejda.commons.util.IOUtils.closeQuietly(outputStream);
            currentImageCounter$1.elem++;
            String outName = NameGenerator.nameGenerator(parameters$1.getOutputPrefix()).generate(NameGenerationRequest.nameRequest("png").originalName(source$1.getName()).page(pageNum).fileNumber(currentImageCounter$1.elem));
            outputWriter$1.addOutput(FileOutput.file(outputFile).name(outName));
            $this.protected$executionContext($this).incrementAndGetOutputDocumentsCounter();
            ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).stepsCompleted(currentStep$1.elem).outOf(totalSteps$1);
        } catch (Throwable th) {
            org.sejda.commons.util.IOUtils.closeQuietly(outputStream);
            throw th;
        }
    }

    public /* synthetic */ TaskExecutionContext protected$executionContext(final ExtractImagesTask x$1) {
        return x$1.executionContext();
    }
}
