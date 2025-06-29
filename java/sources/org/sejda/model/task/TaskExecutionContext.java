package org.sejda.model.task;

import java.util.Objects;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.exception.TaskNonLenientExecutionException;
import org.sejda.model.parameter.base.TaskParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/task/TaskExecutionContext.class */
public class TaskExecutionContext {
    private static final Logger LOG = LoggerFactory.getLogger(TaskExecutionContext.class);
    private NotifiableTaskMetadata taskMetadata;
    private Task<? extends TaskParameters> task;
    private boolean lenient;
    private StopWatch stopWatch = new StopWatch();
    private int outputDocumentsCounter = 0;

    public TaskExecutionContext(Task<? extends TaskParameters> task, boolean lenient) {
        if (Objects.isNull(task)) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        this.taskMetadata = new NotifiableTaskMetadata(task);
        this.task = task;
        this.lenient = lenient;
    }

    public NotifiableTaskMetadata notifiableTaskMetadata() {
        return this.taskMetadata;
    }

    public Task task() {
        return this.task;
    }

    public void taskStart() {
        this.stopWatch.start();
    }

    public void taskEnded() {
        this.stopWatch.stop();
        LOG.info("Task ({}) executed in {}", this.task, DurationFormatUtils.formatDurationWords(this.stopWatch.getTime(), true, true));
    }

    public long executionTime() {
        return this.stopWatch.getTime();
    }

    public int incrementAndGetOutputDocumentsCounter() {
        int i = this.outputDocumentsCounter + 1;
        this.outputDocumentsCounter = i;
        return i;
    }

    public int outputDocumentsCounter() {
        return this.outputDocumentsCounter;
    }

    public void assertTaskIsLenient(Exception e) throws TaskNonLenientExecutionException {
        if (!this.lenient) {
            throw new TaskNonLenientExecutionException(e);
        }
    }

    public void assertTaskIsLenient(String message) throws TaskNonLenientExecutionException {
        if (!this.lenient) {
            throw new TaskNonLenientExecutionException(message);
        }
    }

    public void assertHasOutputDocuments(String message) throws TaskExecutionException {
        if (this.outputDocumentsCounter <= 0) {
            throw new TaskExecutionException(message);
        }
    }
}
