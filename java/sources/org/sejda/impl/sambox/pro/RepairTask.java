package org.sejda.impl.sambox.pro;

import java.io.File;
import java.util.Optional;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.pro.component.PageTreeRebuilder;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pro.parameter.RepairParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/RepairTask.class */
public class RepairTask extends BaseTask<RepairParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(RepairTask.class);
    private int totalSteps;
    private PDDocumentHandler documentHandler = null;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;
    private MultipleOutputWriter outputWriter;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(RepairParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((RepairTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(RepairParameters parameters) throws TaskException {
        File tmpFile;
        int currentStep = 0;
        for (PdfSource<?> source : parameters.getSourceList()) {
            currentStep++;
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            try {
                try {
                    this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
                    this.documentHandler.setCreatorOnPDDocument();
                    tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
                    LOG.debug("Created output on temporary buffer {}", tmpFile);
                    new PageTreeRebuilder(this.documentHandler.getUnderlyingPDDocument()).rebuild();
                } catch (TaskException ex) {
                    TaskExecutionException failure = new TaskExecutionException(String.format("Unable to repair and recover any page from %s", source.getName()), ex);
                    executionContext().assertTaskIsLenient(failure);
                    ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(failure.getMessage());
                    org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                }
                if (this.documentHandler.getPages().stream().findAny().isEmpty()) {
                    throw new TaskExecutionException(String.format("Unable to repair and recover any page from %s", source.getName()));
                }
                this.documentHandler.setCompress(parameters.isCompress());
                this.documentHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
                int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
                String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                    return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(fileNumber));
                });
                this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(this.totalSteps);
            } catch (Throwable th) {
                org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                throw th;
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        executionContext().assertHasOutputDocuments("Unable to repair and recover any documents");
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input documents recovered and written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
