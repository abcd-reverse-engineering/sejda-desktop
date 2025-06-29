package org.sejda.impl.sambox;

import org.sejda.commons.util.IOUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.SamboxOutlineLevelsHandler;
import org.sejda.impl.sambox.component.optimization.OptimizationRuler;
import org.sejda.impl.sambox.component.split.PageDestinationsLevelPdfSplitter;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.outline.OutlinePageDestinations;
import org.sejda.model.parameter.SplitByOutlineLevelParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/SplitByOutlineLevelTask.class */
public class SplitByOutlineLevelTask extends BaseTask<SplitByOutlineLevelParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(SplitByOutlineLevelTask.class);
    private int totalSteps;
    private PDDocument document = null;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;
    private PageDestinationsLevelPdfSplitter splitter;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(SplitByOutlineLevelParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((SplitByOutlineLevelTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(SplitByOutlineLevelParameters parameters) throws TaskException {
        int currentStep = 0;
        for (PdfSource<?> source : parameters.getSourceList()) {
            currentStep++;
            LOG.debug("Opening {} ", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.document = ((PDDocumentHandler) source.open(this.documentLoader)).getUnderlyingPDDocument();
            LOG.debug("Retrieving outline information for level {}", Integer.valueOf(parameters.getLevelToSplitAt()));
            OutlinePageDestinations pagesDestination = new SamboxOutlineLevelsHandler(this.document, parameters.getMatchingTitleRegEx()).getPageDestinationsForLevel(parameters.getLevelToSplitAt());
            this.splitter = new PageDestinationsLevelPdfSplitter(this.document, parameters, pagesDestination, new OptimizationRuler(parameters.getOptimizationPolicy()).apply(this.document).booleanValue());
            LOG.debug("Starting split by outline level for {} ", parameters);
            this.splitter.split(executionContext(), parameters.getOutputPrefix(), source);
            IOUtils.closeQuietly(this.document);
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(this.totalSteps);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        LOG.debug("Input documents splitted and written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        IOUtils.closeQuietly(this.document);
        this.splitter = null;
    }
}
