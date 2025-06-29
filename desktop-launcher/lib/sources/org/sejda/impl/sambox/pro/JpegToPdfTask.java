package org.sejda.impl.sambox.pro;

import java.io.File;
import java.util.List;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.PdfRotator;
import org.sejda.impl.sambox.component.image.ImagesToPdfDocumentConverter;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.Source;
import org.sejda.model.pro.parameter.JpegToPdfParameters;
import org.sejda.model.rotation.Rotation;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/JpegToPdfTask.class */
public class JpegToPdfTask extends BaseTask<JpegToPdfParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(JpegToPdfTask.class);
    private int totalSteps;
    private PDDocumentHandler documentHandler = null;
    private MultipleOutputWriter outputWriter;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(JpegToPdfParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((JpegToPdfTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(JpegToPdfParameters parameters) throws IllegalStateException, TaskException {
        if (parameters.isMergeOutputs()) {
            executeOneOutput(parameters);
        } else {
            executeMultipleOutputs(parameters);
        }
    }

    private void executeOneOutput(JpegToPdfParameters parameters) throws IllegalStateException, TaskException {
        Rotation rotation;
        File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
        ImagesToPdfDocumentConverter converter = new ImagesToPdfDocumentConverter() { // from class: org.sejda.impl.sambox.pro.JpegToPdfTask.1
            @Override // org.sejda.impl.sambox.component.image.ImagesToPdfDocumentConverter
            public void beforeImage(Source<?> source) {
                JpegToPdfTask.this.executionContext().incrementAndGetOutputDocumentsCounter();
            }

            @Override // org.sejda.impl.sambox.component.image.ImagesToPdfDocumentConverter
            public void afterImage(PDImageXObject image) {
                ApplicationEventsNotifier.notifyEvent(JpegToPdfTask.this.executionContext().notifiableTaskMetadata()).stepsCompleted(JpegToPdfTask.this.executionContext().outputDocumentsCounter()).outOf(JpegToPdfTask.this.totalSteps);
            }

            @Override // org.sejda.impl.sambox.component.image.ImagesToPdfDocumentConverter
            public void failedImage(Source<?> source, TaskIOException e) throws TaskException {
                JpegToPdfTask.this.executionContext().assertTaskIsLenient(e);
                ApplicationEventsNotifier.notifyEvent(JpegToPdfTask.this.executionContext().notifiableTaskMetadata()).taskWarning(String.format("Image %s was skipped, could not be processed", source.getName()), e);
            }
        };
        this.documentHandler = converter.getDocumentHandler();
        for (int i = 0; i < parameters.getSourceList().size(); i++) {
            Source<?> source = parameters.getSourceList().get(i);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            PDRectangle pageSize = null;
            if (parameters.getPageSize() != null) {
                pageSize = ImagesToPdfDocumentConverter.toPDRectangle(parameters.getPageSize());
            }
            if (parameters.isPageSizeMatchImageSize()) {
                pageSize = null;
            }
            List<PDPage> pages = converter.addPages(source, pageSize, parameters.getPageOrientation(), parameters.getMarginInches());
            if (parameters.getRotations().size() > i && (rotation = parameters.getRotations().get(i)) != null && rotation != Rotation.DEGREES_0) {
                for (PDPage p : pages) {
                    PdfRotator.rotate(p, rotation);
                }
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
        this.documentHandler.setCompress(parameters.isCompress());
        this.documentHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
        if (this.documentHandler.getNumberOfPages() == 0) {
            throw new TaskException("All images could not be processed");
        }
        String outName = NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(parameters.getSourceList().get(0).getName()));
        this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input images written to {}", parameters.getOutput());
    }

    private void executeMultipleOutputs(JpegToPdfParameters parameters) throws TaskException {
        Rotation rotation;
        int currentStep = 0;
        for (int i = 0; i < parameters.getSourceList().size(); i++) {
            currentStep++;
            Source<?> source = parameters.getSourceList().get(i);
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
            LOG.debug("Created output on temporary buffer {}", tmpFile);
            try {
                try {
                    PDRectangle pageSize = null;
                    if (parameters.getPageSize() != null) {
                        pageSize = ImagesToPdfDocumentConverter.toPDRectangle(parameters.getPageSize());
                    }
                    if (parameters.isPageSizeMatchImageSize()) {
                        pageSize = null;
                    }
                    ImagesToPdfDocumentConverter converter = new ImagesToPdfDocumentConverter();
                    this.documentHandler = converter.getDocumentHandler();
                    List<PDPage> pages = converter.addPages(source, pageSize, parameters.getPageOrientation(), parameters.getMarginInches());
                    if (parameters.getRotations().size() > i && (rotation = parameters.getRotations().get(i)) != null && rotation != Rotation.DEGREES_0) {
                        for (PDPage p : pages) {
                            PdfRotator.rotate(p, rotation);
                        }
                    }
                    this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
                    this.documentHandler.setCompress(parameters.isCompress());
                    this.documentHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
                    int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
                    String outName = NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(fileNumber));
                    this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                    org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                } catch (TaskException e) {
                    executionContext().assertTaskIsLenient(e);
                    ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Image %s was skipped, could not be processed", source.getName()), e);
                    org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                }
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(this.totalSteps);
            } catch (Throwable th) {
                org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                throw th;
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        if (executionContext().outputDocumentsCounter() == 0) {
            throw new TaskException("All images could not be processed");
        }
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input images written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
        this.outputWriter = null;
    }
}
