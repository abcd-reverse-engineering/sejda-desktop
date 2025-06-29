package org.sejda.model.output;

import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.model.exception.TaskOutputVisitException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/output/FileOrDirectoryTaskOutput.class */
public class FileOrDirectoryTaskOutput extends AbstractTaskOutput implements SingleOrMultipleTaskOutput {
    private File file;

    public FileOrDirectoryTaskOutput(File file) {
        if (file == null) {
            throw new IllegalArgumentException("A not null file or directory instance is expected");
        }
        this.file = file;
    }

    @Override // org.sejda.model.output.TaskOutput
    public File getDestination() {
        return this.file;
    }

    @Override // org.sejda.model.output.TaskOutput
    public void accept(TaskOutputDispatcher writer) throws TaskOutputVisitException {
        try {
            writer.dispatch(this);
        } catch (IOException e) {
            throw new TaskOutputVisitException("Exception dispatching the file or directory task output.", e);
        }
    }

    @Override // org.sejda.model.output.AbstractTaskOutput
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append(this.file).toString();
    }

    @Override // org.sejda.model.output.AbstractTaskOutput
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.file).toHashCode();
    }

    @Override // org.sejda.model.output.AbstractTaskOutput
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FileOrDirectoryTaskOutput)) {
            return false;
        }
        FileOrDirectoryTaskOutput output = (FileOrDirectoryTaskOutput) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.file, output.file).isEquals();
    }

    public static FileOrDirectoryTaskOutput file(File file) {
        if (file == null || (file.exists() && !file.isFile())) {
            throw new IllegalArgumentException("A valid instance is expected (not null or existing file).");
        }
        return new FileOrDirectoryTaskOutput(file);
    }

    public static FileOrDirectoryTaskOutput directory(File directory) {
        if (directory == null || !directory.isDirectory()) {
            throw new IllegalArgumentException("A not null directory instance is expected. Path: " + String.valueOf(directory));
        }
        return new FileOrDirectoryTaskOutput(directory);
    }
}
