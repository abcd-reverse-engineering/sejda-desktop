package org.sejda.impl.sambox;

import org.sejda.commons.util.IOUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.optimization.OptimizationRuler;
import org.sejda.impl.sambox.component.split.PagesPdfSplitter;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.AbstractSplitByPageParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/SplitByPageNumbersTask.class */
public class SplitByPageNumbersTask<T extends AbstractSplitByPageParameters> extends BaseTask<T> {
    private static final Logger LOG = LoggerFactory.getLogger(SplitByPageNumbersTask.class);
    private int totalSteps;
    private PDDocument document = null;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;
    private PagesPdfSplitter<T> splitter;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(T parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((SplitByPageNumbersTask<T>) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(T parameters) throws TaskException {
        int currentStep = 0;
        for (PdfSource<?> source : parameters.getSourceList()) {
            currentStep++;
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.document = ((PDDocumentHandler) source.open(this.documentLoader)).getUnderlyingPDDocument();
            this.splitter = new PagesPdfSplitter<>(this.document, parameters, new OptimizationRuler(parameters.getOptimizationPolicy()).apply(this.document).booleanValue());
            LOG.debug("Starting split by page numbers for {} ", parameters);
            this.splitter.split(executionContext(), parameters.getOutputPrefix(), source);
            IOUtils.closeQuietly(this.document);
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(this.totalSteps);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        LOG.debug("Input documents split and written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        IOUtils.closeQuietly(this.document);
        this.splitter = null;
    }
}
