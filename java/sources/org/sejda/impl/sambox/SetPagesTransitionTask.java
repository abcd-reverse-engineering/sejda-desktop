package org.sejda.impl.sambox;

import java.io.File;
import java.util.Iterator;
import java.util.Optional;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.SingleOutputWriter;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.util.TransitionUtils;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.SetPagesTransitionParameters;
import org.sejda.model.pdf.viewerpreference.PdfPageMode;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.interactive.pagenavigation.PDTransition;
import org.sejda.sambox.pdmodel.interactive.pagenavigation.PDTransitionStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/SetPagesTransitionTask.class */
public class SetPagesTransitionTask extends BaseTask<SetPagesTransitionParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(SetPagesTransitionTask.class);
    private PDDocumentHandler documentHandler = null;
    private SingleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(SetPagesTransitionParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((SetPagesTransitionTask) parameters, executionContext);
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newSingleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(SetPagesTransitionParameters parameters) throws IllegalStateException, TaskException {
        ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).progressUndetermined();
        PdfSource<?> source = parameters.getSource();
        LOG.debug("Opening {}", source);
        executionContext().notifiableTaskMetadata().setCurrentSource(source);
        this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
        File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
        this.outputWriter.taskOutput(tmpFile);
        LOG.debug("Temporary output set to {}", tmpFile);
        LOG.debug("Applying transitions");
        int current = 0;
        Iterator<PDPage> it = this.documentHandler.getPages().iterator();
        while (it.hasNext()) {
            PDPage page = it.next();
            current++;
            Optional.ofNullable(parameters.getOrDefault(current)).ifPresent(t -> {
                LOG.trace("Applying transition {}", t);
                PDTransition transition = new PDTransition((PDTransitionStyle) Optional.ofNullable(TransitionUtils.getTransition(t.getStyle())).orElse(PDTransitionStyle.R));
                TransitionUtils.initTransitionDimension(t, transition);
                TransitionUtils.initTransitionMotion(t, transition);
                TransitionUtils.initTransitionDirection(t, transition);
                transition.setDuration(t.getTransitionDuration());
                page.setTransition(transition, t.getDisplayDuration());
            });
        }
        if (parameters.isFullScreen()) {
            this.documentHandler.setPageModeOnDocument(PdfPageMode.FULLSCREEN);
        }
        this.documentHandler.setCreatorOnPDDocument();
        this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
        this.documentHandler.setCompress(parameters.isCompress());
        this.documentHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Transitions set on {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
