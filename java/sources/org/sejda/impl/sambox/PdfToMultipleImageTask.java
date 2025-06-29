package org.sejda.impl.sambox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import org.sejda.commons.util.IOUtils;
import org.sejda.core.Sejda;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.core.support.util.RuntimeUtils;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.image.AbstractPdfToMultipleImageParameters;
import org.sejda.model.task.TaskExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/PdfToMultipleImageTask.class */
public class PdfToMultipleImageTask<T extends AbstractPdfToMultipleImageParameters> extends BasePdfToImageTask<T> {
    private static final Logger LOG = LoggerFactory.getLogger(PdfToMultipleImageTask.class);
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> sourceOpener;
    private PDDocumentHandler documentHandler = null;

    @Override // org.sejda.impl.sambox.BasePdfToImageTask, org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(T parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((PdfToMultipleImageTask<T>) parameters, executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
        this.sourceOpener = new DefaultPdfSourceOpener(executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(T parameters) throws TaskException {
        int percentageMemoryUsed;
        int currentStep = 0;
        int totalSteps = parameters.getSourceList().size();
        for (PdfSource<?> source : parameters.getSourceList()) {
            currentStep++;
            try {
                LOG.debug("Opening {}", source);
                executionContext().notifiableTaskMetadata().setCurrentSource(source);
                this.documentHandler = (PDDocumentHandler) source.open(this.sourceOpener);
                Set<Integer> requestedPages = parameters.getPages(this.documentHandler.getNumberOfPages());
                if (!requestedPages.isEmpty()) {
                    LOG.trace("Found {} pages to convert", Integer.valueOf(totalSteps));
                    Iterator<Integer> it = requestedPages.iterator();
                    while (it.hasNext()) {
                        int currentPage = it.next().intValue();
                        if (Boolean.getBoolean(Sejda.PERFORM_MEMORY_OPTIMIZATIONS_PROPERTY_NAME) && (percentageMemoryUsed = RuntimeUtils.getPercentageMemoryUsed()) > 60) {
                            LOG.debug("Closing and reopening source doc, memory usage reached: {}%", Integer.valueOf(percentageMemoryUsed));
                            IOUtils.closeQuietly(this.documentHandler);
                            this.documentHandler = (PDDocumentHandler) source.open(this.sourceOpener);
                        }
                        File tmpFile = org.sejda.model.util.IOUtils.createTemporaryBuffer();
                        int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
                        LOG.debug("Created output temporary buffer {} ", tmpFile);
                        try {
                            LOG.trace("Converting page {}", Integer.valueOf(currentPage));
                            BufferedImage pageImage = this.documentHandler.renderImage(currentPage, parameters.getResolutionInDpi(), parameters.getOutputImageColorType());
                            getWriter().openDestination(tmpFile, parameters);
                            getWriter().write(pageImage, parameters);
                            getWriter().closeDestination();
                            String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber, "." + parameters.getOutputImageType().getExtension())).orElseGet(() -> {
                                return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest(parameters.getOutputImageType().getExtension()).page(currentPage).originalName(source.getName()).fileNumber(fileNumber));
                            });
                            this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                        } catch (TaskException e) {
                            executionContext().assertTaskIsLenient(e);
                            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Page %d was skipped, could not be converted", Integer.valueOf(currentPage)), e);
                        }
                    }
                    ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(totalSteps);
                } else {
                    throw new TaskException("No pages converted");
                }
            } finally {
                IOUtils.closeQuietly(this.documentHandler);
            }
            IOUtils.closeQuietly(this.documentHandler);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Documents converted to {} and saved to {}", parameters.getOutputImageType(), parameters.getOutput());
    }

    @Override // org.sejda.impl.sambox.BasePdfToImageTask, org.sejda.model.task.Task
    public void after() {
        super.after();
        IOUtils.closeQuietly(this.documentHandler);
    }
}
