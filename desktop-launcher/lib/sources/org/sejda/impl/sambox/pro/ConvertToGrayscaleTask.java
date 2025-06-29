package org.sejda.impl.sambox.pro;

import java.io.File;
import java.util.Iterator;
import java.util.Optional;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.pro.component.ContentStreamWriterStack;
import org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pro.parameter.ConvertToGrayscaleParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/ConvertToGrayscaleTask.class */
public class ConvertToGrayscaleTask extends BaseTask<ConvertToGrayscaleParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(ConvertToGrayscaleTask.class);
    private int totalSteps;
    private PDDocumentHandler documentHandler = null;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;
    private MultipleOutputWriter outputWriter;
    private GrayscaleConverter converter;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(ConvertToGrayscaleParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((ConvertToGrayscaleTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(ConvertToGrayscaleParameters parameters) throws IllegalStateException, TaskException {
        for (PdfSource<?> source : parameters.getSourceList()) {
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
            this.documentHandler.setCreatorOnPDDocument();
            File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
            LOG.debug("Created output on temporary buffer {}", tmpFile);
            this.converter = new GrayscaleConverter(new ContentStreamWriterStack(), parameters.isConvertImages(), parameters.isConvertTextToBlack(), parameters.isCompressImages());
            int pageNum = 0;
            LOG.debug("Starting conversion to grayscale");
            Iterator<PDPage> it = this.documentHandler.getPages().iterator();
            while (it.hasNext()) {
                PDPage page = it.next();
                pageNum++;
                LOG.trace("Converting page {}", Integer.valueOf(pageNum));
                try {
                    this.converter.convert(page);
                } catch (Exception e) {
                    executionContext().assertTaskIsLenient(e);
                    ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Page %d was skipped, could not be converted", Integer.valueOf(pageNum)), e);
                }
            }
            this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
            this.documentHandler.setCompress(parameters.isCompress());
            this.documentHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
            String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(fileNumber));
            });
            this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
            org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(fileNumber).outOf(this.totalSteps);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input documents converted to grayscale and written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
