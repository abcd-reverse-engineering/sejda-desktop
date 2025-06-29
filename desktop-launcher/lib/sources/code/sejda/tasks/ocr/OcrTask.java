package code.sejda.tasks.ocr;

import code.util.ImplicitJavaConversions$;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.pro.component.PdfTextExtractor;
import org.sejda.model.SejdaFileExtensions;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfFileSource;
import org.sejda.model.input.PdfSource;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.IntRef;

/* compiled from: OcrTask.scala */
@ScalaSignature(bytes = "\u0006\u000513Aa\u0002\u0005\u0001#!)\u0011\u0005\u0001C\u0001E!9A\u0005\u0001b\u0001\n\u0003)\u0003B\u0002\u0017\u0001A\u0003%a\u0005C\u0003.\u0001\u0011\u0005a\u0006C\u0003C\u0001\u0011\u00053\t\u0003\u0006E\u0001A\u0005\t\u0011!A\u0005\u0002\u0015\u0013qaT2s)\u0006\u001c8N\u0003\u0002\n\u0015\u0005\u0019qn\u0019:\u000b\u0005-a\u0011!\u0002;bg.\u001c(BA\u0007\u000f\u0003\u0015\u0019XM\u001b3b\u0015\u0005y\u0011\u0001B2pI\u0016\u001c\u0001a\u0005\u0002\u0001%A\u00191cG\u000f\u000e\u0003QQ!!\u0006\f\u0002\tQ\f7o\u001b\u0006\u0003/a\tQ!\\8eK2T!!D\r\u000b\u0003i\t1a\u001c:h\u0013\taBC\u0001\u0005CCN,G+Y:l!\tqr$D\u0001\t\u0013\t\u0001\u0003BA\u0007PGJ\u0004\u0016M]1nKR,'o]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003\r\u0002\"A\b\u0001\u0002\r1|wmZ3s+\u00051\u0003CA\u0014+\u001b\u0005A#BA\u0015\u001a\u0003\u0015\u0019HN\u001a\u001bk\u0013\tY\u0003F\u0001\u0004M_\u001e<WM]\u0001\bY><w-\u001a:!\u0003\u001d)\u00070Z2vi\u0016$\"aL\u001b\u0011\u0005A\u001aT\"A\u0019\u000b\u0003I\nQa]2bY\u0006L!\u0001N\u0019\u0003\tUs\u0017\u000e\u001e\u0005\u0006m\u0011\u0001\r!H\u0001\u000ba\u0006\u0014\u0018-\\3uKJ\u001c\bf\u0001\u00039\u0003B\u0019\u0001'O\u001e\n\u0005i\n$A\u0002;ie><8\u000f\u0005\u0002=\u007f5\tQH\u0003\u0002?-\u0005IQ\r_2faRLwN\\\u0005\u0003\u0001v\u0012Q\u0002V1tW\u0016C8-\u001a9uS>t7%A\u001e\u0002\u000b\u00054G/\u001a:\u0015\u0003=\n!\u0004\u001d:pi\u0016\u001cG/\u001a3%Kb,7-\u001e;j_:\u001cuN\u001c;fqR$\"A\u0012&\u0015\u0003\u001d\u0003\"a\u0005%\n\u0005%#\"\u0001\u0006+bg.,\u00050Z2vi&|gnQ8oi\u0016DH\u000fC\u0004L\r\u0005\u0005\t\u0019A\u0012\u0002\u0007a$\u0013\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/ocr/OcrTask.class */
public class OcrTask extends BaseTask<OcrParameters> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override // org.sejda.model.task.Task
    public void after() {
    }

    public Logger logger() {
        return this.logger;
    }

    @Override // org.sejda.model.task.Task
    public void execute(final OcrParameters parameters) throws TaskException {
        int totalSteps = parameters.getSourceList().size();
        IntRef currentStep = IntRef.create(0);
        MultipleOutputWriter outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext());
        ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(parameters.getSourceList()).foreach(source -> {
            $anonfun$execute$1(this, currentStep, parameters, outputWriter, totalSteps, source);
            return BoxedUnit.UNIT;
        });
        parameters.getOutput().accept(outputWriter);
    }

    public static final /* synthetic */ void $anonfun$execute$1(final OcrTask $this, final IntRef currentStep$1, final OcrParameters parameters$1, final MultipleOutputWriter outputWriter$1, final int totalSteps$1, final PdfSource source) throws IllegalStateException, IOException, TaskException {
        $this.logger().debug("Processing {}", source);
        currentStep$1.elem++;
        File outputPdfFile = TesseractUtils$.MODULE$.performOcr(source, parameters$1.language(), parameters$1.getOutput().getEncryptionAtRestPolicy(), $this.protected$executionContext($this));
        if (parameters$1.outputFormats().contains(OutputFormat$Pdf$.MODULE$) || parameters$1.outputFormats().isEmpty()) {
            String outName = NameGenerator.nameGenerator(parameters$1.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(currentStep$1.elem));
            outputWriter$1.addOutput(FileOutput.file(outputPdfFile).name(outName));
        }
        if (parameters$1.outputFormats().contains(OutputFormat$Text$.MODULE$)) {
            PdfFileSource pdfResultSource = PdfFileSource.newInstanceNoPassword(outputPdfFile);
            pdfResultSource.setEncryptionAtRestPolicy(parameters$1.getOutput().getEncryptionAtRestPolicy());
            PDDocumentHandler pdfResultDoc = (PDDocumentHandler) pdfResultSource.open(new DefaultPdfSourceOpener($this.protected$executionContext($this)));
            File txtFile = IOUtils.createTemporaryBuffer(".txt");
            OutputStream txtOut = parameters$1.getOutput().getEncryptionAtRestPolicy().encrypt(new FileOutputStream(txtFile));
            PdfTextExtractor textExtractor = new PdfTextExtractor(Charset.forName("UTF-8"), txtOut);
            try {
                textExtractor.extract(pdfResultDoc.getUnderlyingPDDocument());
                if (textExtractor.wasEmptyOutput()) {
                    String message = new StringBuilder(15).append("No text found: ").append(source.getName()).toString();
                    if (parameters$1.isLenient()) {
                        ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).taskWarning(message);
                    } else {
                        throw new NoTextFoundException(message);
                    }
                } else {
                    String outName2 = NameGenerator.nameGenerator(parameters$1.getOutputPrefix()).generate(NameGenerationRequest.nameRequest(SejdaFileExtensions.TXT_EXTENSION).originalName(source.getName()).fileNumber(currentStep$1.elem));
                    outputWriter$1.addOutput(FileOutput.file(txtFile).name(outName2));
                }
            } finally {
                org.sejda.commons.util.IOUtils.closeQuietly(textExtractor);
                org.sejda.commons.util.IOUtils.closeQuietly(txtOut);
                org.sejda.commons.util.IOUtils.closeQuietly(pdfResultDoc);
            }
        }
        ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).stepsCompleted(currentStep$1.elem).outOf(totalSteps$1);
    }

    public /* synthetic */ TaskExecutionContext protected$executionContext(final OcrTask x$1) {
        return x$1.executionContext();
    }
}
