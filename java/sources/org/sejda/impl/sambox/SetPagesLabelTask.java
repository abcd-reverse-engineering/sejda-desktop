package org.sejda.impl.sambox;

import java.io.File;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.SingleOutputWriter;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.SetPagesLabelParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/SetPagesLabelTask.class */
public class SetPagesLabelTask extends BaseTask<SetPagesLabelParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(SetPagesLabelTask.class);
    private PDDocumentHandler documentHandler = null;
    private SingleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(SetPagesLabelParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((SetPagesLabelTask) parameters, executionContext);
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newSingleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(SetPagesLabelParameters parameters) throws IllegalStateException, TaskException {
        ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).progressUndetermined();
        PdfSource<?> source = parameters.getSource();
        LOG.debug("Opening {}", source);
        executionContext().notifiableTaskMetadata().setCurrentSource(source);
        this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
        File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
        this.outputWriter.taskOutput(tmpFile);
        LOG.debug("Temporary output set to {}", tmpFile);
        LOG.debug("Applying {} labels ", Integer.valueOf(parameters.getLabels().size()));
        this.documentHandler.setPageLabelsOnDocument(parameters.getLabels());
        this.documentHandler.setCreatorOnPDDocument();
        this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
        this.documentHandler.setCompress(parameters.isCompress());
        this.documentHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Labels applied to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
