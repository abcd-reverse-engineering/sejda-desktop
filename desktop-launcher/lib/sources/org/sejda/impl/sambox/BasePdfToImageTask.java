package org.sejda.impl.sambox;

import org.sejda.commons.util.IOUtils;
import org.sejda.core.writer.context.ImageWriterContext;
import org.sejda.core.writer.model.ImageWriter;
import org.sejda.model.exception.TaskException;
import org.sejda.model.parameter.image.PdfToImageParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/BasePdfToImageTask.class */
abstract class BasePdfToImageTask<T extends PdfToImageParameters> extends BaseTask<T> {
    private static final Logger LOG = LoggerFactory.getLogger(BasePdfToImageTask.class);
    private ImageWriter<T> writer;

    BasePdfToImageTask() {
    }

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(T parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((BasePdfToImageTask<T>) parameters, executionContext);
        this.writer = ImageWriterContext.getContext().createImageWriter(parameters);
        LOG.trace("Found image writer {}", this.writer);
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        IOUtils.closeQuietly(this.writer);
    }

    ImageWriter<T> getWriter() {
        return this.writer;
    }
}
