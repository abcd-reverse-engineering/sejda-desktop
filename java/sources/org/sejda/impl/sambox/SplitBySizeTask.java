package org.sejda.impl.sambox;

import org.sejda.commons.util.IOUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.util.HumanReadableSize;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.optimization.OptimizationRuler;
import org.sejda.impl.sambox.component.split.AbstractPdfSplitter;
import org.sejda.impl.sambox.component.split.SizePdfSplitter;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.SplitBySizeParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/SplitBySizeTask.class */
public class SplitBySizeTask extends BaseTask<SplitBySizeParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(SplitBySizeTask.class);
    private int totalSteps;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;
    private PDDocument document = null;
    private AbstractPdfSplitter<SplitBySizeParameters> splitter;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(SplitBySizeParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((SplitBySizeTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(SplitBySizeParameters parameters) throws TaskException {
        int currentStep = 0;
        for (PdfSource<?> source : parameters.getSourceList()) {
            currentStep++;
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.document = ((PDDocumentHandler) source.open(this.documentLoader)).getUnderlyingPDDocument();
            this.splitter = new SizePdfSplitter(this.document, parameters, new OptimizationRuler(parameters.getOptimizationPolicy()).apply(this.document).booleanValue());
            LOG.debug("Starting split by size {}", HumanReadableSize.toString(parameters.getSizeToSplitAt()));
            this.splitter.split(executionContext(), parameters.getOutputPrefix(), source);
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(this.totalSteps);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        LOG.debug("Input documents rotated and written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        IOUtils.closeQuietly(this.document);
        this.splitter = null;
    }
}
