package org.sejda.model.task;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.sejda.commons.util.RequireUtils;
import org.sejda.model.input.TaskSource;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/task/NotifiableTaskMetadata.class */
public class NotifiableTaskMetadata implements Serializable {
    private static final long serialVersionUID = -6423865557633949211L;
    public static final NotifiableTaskMetadata NULL = new NullNotifiableTaskMetadata();
    private UUID taskIdentifier;
    private String qualifiedName;
    private List<File> taskOutput = new ArrayList();
    private List<File> skippedOutput = new ArrayList();
    private String currentSource;

    private NotifiableTaskMetadata() {
    }

    public NotifiableTaskMetadata(Task<?> task) {
        RequireUtils.requireNotNullArg(task, "No task given, unable to create notifiable metadata.");
        this.taskIdentifier = UUID.randomUUID();
        this.qualifiedName = task.getClass().getName();
    }

    public UUID getTaskIdentifier() {
        return this.taskIdentifier;
    }

    public String getQualifiedName() {
        return this.qualifiedName;
    }

    public void addTaskOutput(File output) {
        if (Objects.nonNull(output)) {
            this.taskOutput.add(output);
        }
    }

    public void addSkippedOutput(File skipped) {
        if (Objects.nonNull(skipped)) {
            this.skippedOutput.add(skipped);
        }
    }

    public void setCurrentSource(TaskSource<?> source) {
        this.currentSource = source.getName();
    }

    public void clearCurrentSource() {
        this.currentSource = "";
    }

    public String getCurrentSource() {
        return this.currentSource;
    }

    public List<File> taskOutput() {
        return Collections.unmodifiableList(this.taskOutput);
    }

    public List<File> skippedOutput() {
        return Collections.unmodifiableList(this.skippedOutput);
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.taskIdentifier).append(this.qualifiedName).toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NotifiableTaskMetadata)) {
            return false;
        }
        NotifiableTaskMetadata meta = (NotifiableTaskMetadata) other;
        return new EqualsBuilder().append(this.taskIdentifier, meta.taskIdentifier).append(this.qualifiedName, meta.qualifiedName).isEquals();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("taskIdentifier", this.taskIdentifier).append("qualifiedName", this.qualifiedName).toString();
    }

    /* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/task/NotifiableTaskMetadata$NullNotifiableTaskMetadata.class */
    private static class NullNotifiableTaskMetadata extends NotifiableTaskMetadata {
        private static final long serialVersionUID = 6788562820506828221L;

        private NullNotifiableTaskMetadata() {
        }

        @Override // org.sejda.model.task.NotifiableTaskMetadata
        public UUID getTaskIdentifier() {
            return null;
        }

        @Override // org.sejda.model.task.NotifiableTaskMetadata
        public String getQualifiedName() {
            return "";
        }
    }
}
