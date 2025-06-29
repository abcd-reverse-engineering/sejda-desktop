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
import org.sejda.impl.sambox.util.ViewerPreferencesUtils;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.ViewerPreferencesParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/ViewerPreferencesTask.class */
public class ViewerPreferencesTask extends BaseTask<ViewerPreferencesParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(ViewerPreferencesTask.class);
    private PDDocumentHandler documentHandler = null;
    private int totalSteps;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(ViewerPreferencesParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((ViewerPreferencesTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(ViewerPreferencesParameters parameters) throws IllegalStateException, TaskException {
        for (PdfSource<?> source : parameters.getSourceList()) {
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
            this.documentHandler.setCreatorOnPDDocument();
            File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
            LOG.debug("Created output on temporary buffer {}", tmpFile);
            this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
            this.documentHandler.setCompress(parameters.isCompress());
            this.documentHandler.setPageModeOnDocument(parameters.getPageMode());
            this.documentHandler.setPageLayoutOnDocument(parameters.getPageLayout());
            setViewerPreferences(parameters);
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
        LOG.debug("Viewer preferences set on input documents and written to {}", parameters.getOutput());
    }

    private void setViewerPreferences(ViewerPreferencesParameters parameters) throws TaskException {
        PDViewerPreferences preferences = this.documentHandler.getViewerPreferences();
        ViewerPreferencesUtils.setBooleanPreferences(preferences, parameters.getEnabledPreferences());
        if (parameters.getDirection() != null) {
            PDViewerPreferences.READING_DIRECTION direction = ViewerPreferencesUtils.getDirection(parameters.getDirection());
            preferences.setReadingDirection(direction);
            LOG.trace("Direction set to '{}'", direction);
        }
        if (parameters.getDuplex() != null) {
            PDViewerPreferences.DUPLEX duplex = ViewerPreferencesUtils.getDuplex(parameters.getDuplex());
            preferences.setDuplex(duplex);
            LOG.trace("Duplex set to '{}'", duplex);
        }
        if (parameters.getPrintScaling() != null) {
            PDViewerPreferences.PRINT_SCALING printScaling = ViewerPreferencesUtils.getPrintScaling(parameters.getPrintScaling());
            preferences.setPrintScaling(printScaling);
            LOG.trace("PrintScaling set to '{}'", printScaling);
        }
        PDViewerPreferences.NON_FULL_SCREEN_PAGE_MODE nfsMode = ViewerPreferencesUtils.getNFSMode(parameters.getNfsMode());
        preferences.setNonFullScreenPageMode(nfsMode);
        LOG.trace("Non full screen mode set to '{}'", nfsMode);
        this.documentHandler.setViewerPreferences(preferences);
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
