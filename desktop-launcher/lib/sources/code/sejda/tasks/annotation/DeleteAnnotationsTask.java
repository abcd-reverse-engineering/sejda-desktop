package code.sejda.tasks.annotation;

import code.sejda.tasks.common.BaseBaseTask;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.SignatureClipper;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;

/* compiled from: DeleteAnnotationsTask.scala */
@ScalaSignature(bytes = "\u0006\u0005}2A!\u0002\u0004\u0001\u001f!)\u0001\u0005\u0001C\u0001C!)1\u0005\u0001C!I!)Q\u0006\u0001C!]!Qq\u0006\u0001I\u0001\u0002\u0003\u0005I\u0011\u0001\u0019\u0003+\u0011+G.\u001a;f\u0003:tw\u000e^1uS>t7\u000fV1tW*\u0011q\u0001C\u0001\u000bC:tw\u000e^1uS>t'BA\u0005\u000b\u0003\u0015!\u0018m]6t\u0015\tYA\"A\u0003tK*$\u0017MC\u0001\u000e\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0019\u0001\u0001\u0005\u000e\u0011\u0007E!b#D\u0001\u0013\u0015\t\u0019\u0002\"\u0001\u0004d_6lwN\\\u0005\u0003+I\u0011ABQ1tK\n\u000b7/\u001a+bg.\u0004\"a\u0006\r\u000e\u0003\u0019I!!\u0007\u0004\u00037\u0011+G.\u001a;f\u0003:tw\u000e^1uS>t7\u000fU1sC6,G/\u001a:t!\tYb$D\u0001\u001d\u0015\tiB\"\u0001\u0003vi&d\u0017BA\u0010\u001d\u0005!aunZ4bE2,\u0017A\u0002\u001fj]&$h\bF\u0001#!\t9\u0002!A\u0004fq\u0016\u001cW\u000f^3\u0015\u0005\u0015Z\u0003C\u0001\u0014*\u001b\u00059#\"\u0001\u0015\u0002\u000bM\u001c\u0017\r\\1\n\u0005):#\u0001B+oSRDQ\u0001\f\u0002A\u0002Y\t!\u0002]1sC6,G/\u001a:t\u0003\u0015\tg\r^3s)\u0005)\u0013A\u00079s_R,7\r^3eI\u0015DXmY;uS>t7i\u001c8uKb$HCA\u0019>)\u0005\u0011\u0004CA\u001a<\u001b\u0005!$BA\u001b7\u0003\u0011!\u0018m]6\u000b\u0005]B\u0014!B7pI\u0016d'BA\u0006:\u0015\u0005Q\u0014aA8sO&\u0011A\b\u000e\u0002\u0015)\u0006\u001c8.\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;\t\u000fy\"\u0011\u0011!a\u0001E\u0005\u0019\u0001\u0010J\u0019")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/annotation/DeleteAnnotationsTask.class */
public class DeleteAnnotationsTask extends BaseBaseTask<DeleteAnnotationsParameters> {
    @Override // org.sejda.model.task.Task
    public void after() {
    }

    @Override // org.sejda.model.task.Task
    public void execute(final DeleteAnnotationsParameters parameters) {
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

    public static final /* synthetic */ void $anonfun$execute$1(final DeleteAnnotationsTask $this, final IntRef currentStep$1, final DefaultPdfSourceOpener opener$1, final DeleteAnnotationsParameters parameters$1, final MultipleOutputWriter outputWriter$1, final int totalSteps$1, final PdfSource source) throws IllegalStateException, IOException, TaskException {
        $this.logger().debug(new StringBuilder(27).append("Removing annotations from: ").append(source).toString());
        currentStep$1.elem++;
        PDDocumentHandler docHandler = (PDDocumentHandler) source.open(opener$1);
        PDDocument doc = docHandler.getUnderlyingPDDocument();
        File tmpFile = IOUtils.createTemporaryBuffer(parameters$1.getOutput());
        docHandler.getPermissions().ensurePermission(PdfAccessPermission.MODIFY);
        docHandler.setCreatorOnPDDocument();
        docHandler.setVersionOnPDDocument(parameters$1.getVersion());
        docHandler.setCompress(parameters$1.isCompress());
        int numPages = doc.getNumberOfPages();
        parameters$1.getPages(numPages).foreach(pageNum -> {
            int pageIndex = pageNum - 1;
            PDPage page = doc.getPage(pageIndex);
            List pageAnnotations = page.getAnnotations();
            ArrayList annots = new ArrayList(pageAnnotations);
            ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(pageAnnotations).asScala()).foreach(a -> {
                if (!parameters$1.types().exists(t -> {
                    return BoxesRunTime.boxToBoolean($anonfun$execute$4(a, t));
                })) {
                    return BoxedUnit.UNIT;
                }
                return BoxesRunTime.boxToBoolean(annots.remove(a));
            });
            page.setAnnotations(annots);
        });
        SignatureClipper.clipSignatures(docHandler.getUnderlyingPDDocument());
        docHandler.savePDDocument(tmpFile, parameters$1.getOutput().getEncryptionAtRestPolicy());
        String outName = NameGenerator.nameGenerator(parameters$1.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(currentStep$1.elem));
        outputWriter$1.addOutput(FileOutput.file(tmpFile).name(outName));
        ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).stepsCompleted(currentStep$1.elem).outOf(totalSteps$1);
        docHandler.close();
    }

    public static final /* synthetic */ boolean $anonfun$execute$4(final PDAnnotation a$1, final AnnotationType t) {
        return t.matches(a$1);
    }

    public /* synthetic */ TaskExecutionContext protected$executionContext(final DeleteAnnotationsTask x$1) {
        return x$1.executionContext();
    }
}
