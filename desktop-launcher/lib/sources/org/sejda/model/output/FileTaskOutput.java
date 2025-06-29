package org.sejda.model.output;

import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.model.exception.TaskOutputVisitException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/output/FileTaskOutput.class */
public class FileTaskOutput extends AbstractTaskOutput implements SingleTaskOutput {
    private final File file;

    public FileTaskOutput(File file) {
        if (file == null || (file.exists() && !file.isFile())) {
            throw new IllegalArgumentException("A valid instance is expected (not null or existing file).");
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
            throw new TaskOutputVisitException("Exception dispatching the file task output.", e);
        }
    }

    @Override // org.sejda.model.output.AbstractTaskOutput
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append(getDestination()).toString();
    }

    @Override // org.sejda.model.output.AbstractTaskOutput
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(getDestination()).toHashCode();
    }

    @Override // org.sejda.model.output.AbstractTaskOutput
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FileTaskOutput)) {
            return false;
        }
        FileTaskOutput output = (FileTaskOutput) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.file, output.getDestination()).isEquals();
    }
}
