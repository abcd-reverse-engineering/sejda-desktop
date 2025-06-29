package org.sejda.core.support.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.sejda.core.support.io.model.PopulatedFileOutput;
import org.sejda.model.output.DirectoryTaskOutput;
import org.sejda.model.output.ExistingOutputPolicy;
import org.sejda.model.output.FileOrDirectoryTaskOutput;
import org.sejda.model.output.FileTaskOutput;
import org.sejda.model.task.TaskExecutionContext;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/io/DefaultMultipleOutputWriter.class */
class DefaultMultipleOutputWriter implements MultipleOutputWriter {
    private Map<String, File> multipleFiles = new HashMap();
    private final ExistingOutputPolicy existingOutputPolicy;
    private final TaskExecutionContext executionContext;

    DefaultMultipleOutputWriter(ExistingOutputPolicy existingOutputPolicy, TaskExecutionContext executionContext) {
        this.existingOutputPolicy = (ExistingOutputPolicy) ObjectUtils.defaultIfNull(existingOutputPolicy, ExistingOutputPolicy.FAIL);
        this.executionContext = executionContext;
    }

    @Override // org.sejda.model.output.TaskOutputDispatcher
    public void dispatch(FileTaskOutput output) throws IOException {
        throw new IOException("Unsupported FileTaskOutput for a multiple output task.");
    }

    @Override // org.sejda.model.output.TaskOutputDispatcher
    public void dispatch(DirectoryTaskOutput output) throws IOException {
        OutputWriterHelper.moveToDirectory(this.multipleFiles, output.getDestination(), this.existingOutputPolicy, this.executionContext);
    }

    @Override // org.sejda.model.output.TaskOutputDispatcher
    public void dispatch(FileOrDirectoryTaskOutput output) throws IOException {
        if (this.multipleFiles.size() > 1 || output.getDestination().isDirectory()) {
            OutputWriterHelper.moveToDirectory(this.multipleFiles, output.getDestination(), this.existingOutputPolicy, this.executionContext);
        } else {
            OutputWriterHelper.moveToFile(this.multipleFiles, output.getDestination(), this.existingOutputPolicy, this.executionContext);
        }
    }

    @Override // org.sejda.core.support.io.MultipleOutputWriter
    public void addOutput(PopulatedFileOutput fileOutput) {
        if (Objects.nonNull(this.multipleFiles.putIfAbsent(fileOutput.getName(), fileOutput.getFile()))) {
            String basename = FilenameUtils.getBaseName(fileOutput.getName());
            String extension = FilenameUtils.getExtension(fileOutput.getName());
            for (int count = 1; Objects.nonNull(this.multipleFiles.putIfAbsent(String.format("%s(%d).%s", basename, Integer.valueOf(count), extension), fileOutput.getFile())) && count < 100; count++) {
            }
        }
    }
}
