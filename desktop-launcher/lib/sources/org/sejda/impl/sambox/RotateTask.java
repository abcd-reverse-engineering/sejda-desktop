package org.sejda.impl.sambox;

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
import org.sejda.impl.sambox.component.PdfRotator;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.RotateParameters;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.rotation.Rotation;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/RotateTask.class */
public class RotateTask extends BaseTask<RotateParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(RotateTask.class);
    private int totalSteps;
    private PDDocumentHandler documentHandler = null;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(RotateParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((RotateTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(RotateParameters parameters) throws TaskException {
        for (int sourceIndex = 0; sourceIndex < parameters.getSourceList().size(); sourceIndex++) {
            PdfSource<?> source = parameters.getSourceList().get(sourceIndex);
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            try {
                this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
                this.documentHandler.getPermissions().ensurePermission(PdfAccessPermission.ASSEMBLE);
                this.documentHandler.setCreatorOnPDDocument();
                File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
                LOG.debug("Created output on temporary buffer {}", tmpFile);
                PdfRotator rotator = new PdfRotator(this.documentHandler.getUnderlyingPDDocument());
                for (int page = 1; page <= this.documentHandler.getNumberOfPages(); page++) {
                    Rotation rotation = parameters.getRotation(sourceIndex, page);
                    if (rotation != Rotation.DEGREES_0) {
                        try {
                            rotator.rotate(page, rotation);
                        } catch (PageNotFoundException e) {
                            String warningMessage = String.format("Page %d of %s was skipped, could not be rotated", Integer.valueOf(page), source.getName());
                            executionContext().assertTaskIsLenient(e);
                            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(warningMessage, e);
                        }
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
            } catch (Throwable th) {
                org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                throw th;
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input documents rotated and written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
