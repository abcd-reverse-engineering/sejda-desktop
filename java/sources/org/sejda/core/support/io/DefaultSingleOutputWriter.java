package org.sejda.core.support.io;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import org.sejda.model.output.DirectoryTaskOutput;
import org.sejda.model.output.ExistingOutputPolicy;
import org.sejda.model.output.FileOrDirectoryTaskOutput;
import org.sejda.model.output.FileTaskOutput;
import org.sejda.model.task.TaskExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/io/DefaultSingleOutputWriter.class */
class DefaultSingleOutputWriter implements SingleOutputWriter {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSingleOutputWriter.class);
    private File taskOutput;
    private final ExistingOutputPolicy existingOutputPolicy;
    private final TaskExecutionContext executionContext;

    DefaultSingleOutputWriter(ExistingOutputPolicy existingOutputPolicy, TaskExecutionContext executionContext) {
        this.existingOutputPolicy = (ExistingOutputPolicy) Optional.of(existingOutputPolicy).filter(p -> {
            return p != ExistingOutputPolicy.SKIP;
        }).orElseGet(() -> {
            LOG.debug("Cannot use {} output policy for single output, replaced with {}", ExistingOutputPolicy.SKIP, ExistingOutputPolicy.FAIL);
            return ExistingOutputPolicy.FAIL;
        });
        this.executionContext = executionContext;
    }

    @Override // org.sejda.core.support.io.SingleOutputWriter
    public void taskOutput(File taskOutput) {
        this.taskOutput = taskOutput;
    }

    @Override // org.sejda.model.output.TaskOutputDispatcher
    public void dispatch(FileTaskOutput output) throws IOException {
        if (Objects.isNull(this.taskOutput)) {
            throw new IOException("No task output set");
        }
        OutputWriterHelper.moveFile(this.taskOutput, output.getDestination(), this.existingOutputPolicy, this.executionContext);
    }

    @Override // org.sejda.model.output.TaskOutputDispatcher
    public void dispatch(DirectoryTaskOutput output) throws IOException {
        throw new IOException("Unsupported DirectoryTaskOutput, expected a FileTaskOutput");
    }

    @Override // org.sejda.model.output.TaskOutputDispatcher
    public void dispatch(FileOrDirectoryTaskOutput output) throws IOException {
        throw new IOException("Unsupported FileOrDirectoryTaskOutput, expected a FileTaskOutput");
    }
}
