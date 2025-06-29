package org.sejda.impl.sambox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Set;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.SingleOutputWriter;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.image.AbstractPdfToSingleImageParameters;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/PdfToSingleImageTask.class */
public class PdfToSingleImageTask<T extends AbstractPdfToSingleImageParameters> extends BasePdfToImageTask<T> {
    private static final Logger LOG = LoggerFactory.getLogger(PdfToSingleImageTask.class);
    private SingleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> sourceOpener = null;
    private PDDocumentHandler documentHandler = null;

    @Override // org.sejda.impl.sambox.BasePdfToImageTask, org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(T parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((PdfToSingleImageTask<T>) parameters, executionContext);
        if (!getWriter().supportMultiImage()) {
            throw new TaskExecutionException("Selected ImageWriter doesn't support multiple images in the same file");
        }
        this.outputWriter = OutputWriters.newSingleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
        this.sourceOpener = new DefaultPdfSourceOpener(executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(T parameters) throws TaskException {
        File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
        this.outputWriter.taskOutput(tmpFile);
        LOG.debug("Temporary output set to {}", tmpFile);
        LOG.debug("Opening {}", parameters.getSource());
        executionContext().notifiableTaskMetadata().setCurrentSource(parameters.getSource());
        this.documentHandler = (PDDocumentHandler) parameters.getSource().open(this.sourceOpener);
        Set<Integer> requestedPages = parameters.getPages(this.documentHandler.getNumberOfPages());
        int numberOfPages = requestedPages.size();
        LOG.trace("Found {} pages", Integer.valueOf(numberOfPages));
        int currentStep = 0;
        getWriter().openDestination(tmpFile, parameters);
        Iterator<Integer> it = requestedPages.iterator();
        while (it.hasNext()) {
            int page = it.next().intValue();
            currentStep++;
            LOG.trace("Converting page {}", Integer.valueOf(page));
            try {
                BufferedImage pageImage = this.documentHandler.renderImage(page, parameters.getResolutionInDpi(), parameters.getOutputImageColorType());
                getWriter().write(pageImage, parameters);
            } catch (TaskException e) {
                executionContext().assertTaskIsLenient(e);
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Page %d was skipped, could not be converted", Integer.valueOf(page)), e);
            }
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(numberOfPages);
        }
        getWriter().closeDestination();
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Document converted to {} and saved to {}", parameters.getOutputImageType(), parameters.getOutput());
    }

    @Override // org.sejda.impl.sambox.BasePdfToImageTask, org.sejda.model.task.Task
    public void after() {
        super.after();
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
