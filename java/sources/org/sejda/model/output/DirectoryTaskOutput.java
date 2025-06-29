package org.sejda.model.output;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.model.exception.TaskOutputVisitException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/output/DirectoryTaskOutput.class */
public class DirectoryTaskOutput extends AbstractTaskOutput implements MultipleTaskOutput {
    private final File directory;

    public DirectoryTaskOutput(File directory) {
        if (Objects.isNull(directory) || (directory.exists() && !directory.isDirectory())) {
            throw new IllegalArgumentException("A not null directory instance is expected. Path: " + String.valueOf(directory));
        }
        this.directory = directory;
    }

    @Override // org.sejda.model.output.TaskOutput
    public File getDestination() {
        return this.directory;
    }

    @Override // org.sejda.model.output.TaskOutput
    public void accept(TaskOutputDispatcher writer) throws TaskOutputVisitException {
        try {
            writer.dispatch(this);
        } catch (IOException e) {
            throw new TaskOutputVisitException("Exception dispatching the file task output.", e);
        }
    }

    @Override // org.sejda.model.output.AbstractTaskOutput
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append(this.directory).toString();
    }

    @Override // org.sejda.model.output.AbstractTaskOutput
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.directory).toHashCode();
    }

    @Override // org.sejda.model.output.AbstractTaskOutput
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DirectoryTaskOutput)) {
            return false;
        }
        DirectoryTaskOutput output = (DirectoryTaskOutput) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.directory, output.getDestination()).isEquals();
    }
}
