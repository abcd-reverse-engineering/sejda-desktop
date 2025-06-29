package org.sejda.impl.sambox;

import java.io.File;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.SingleOutputWriter;
import org.sejda.impl.sambox.component.PdfAlternateMixer;
import org.sejda.model.exception.TaskException;
import org.sejda.model.parameter.AlternateMixMultipleInputParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/AlternateMixTask.class */
public class AlternateMixTask extends BaseTask<AlternateMixMultipleInputParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(AlternateMixTask.class);
    private PdfAlternateMixer mixer = null;
    private SingleOutputWriter outputWriter;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(AlternateMixMultipleInputParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((AlternateMixTask) parameters, executionContext);
        this.mixer = new PdfAlternateMixer();
        this.outputWriter = OutputWriters.newSingleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(AlternateMixMultipleInputParameters parameters) throws TaskException {
        LOG.debug("Starting alternate mix of {} input documents", Integer.valueOf(parameters.getInputList().size()));
        this.mixer.mix(parameters.getInputList(), executionContext());
        this.mixer.setVersionOnPDDocument(parameters.getVersion());
        this.mixer.setCompress(parameters.isCompress());
        File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
        this.outputWriter.taskOutput(tmpFile);
        LOG.debug("Temporary output set to {}", tmpFile);
        this.mixer.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
        org.sejda.commons.util.IOUtils.closeQuietly(this.mixer);
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Alternate mix of {} files completed", Integer.valueOf(parameters.getInputList().size()));
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.mixer);
    }
}
