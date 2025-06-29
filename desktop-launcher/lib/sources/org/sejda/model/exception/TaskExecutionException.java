package org.sejda.model.exception;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/exception/TaskExecutionException.class */
public class TaskExecutionException extends TaskException {
    private static final long serialVersionUID = 2048737995810559256L;

    public TaskExecutionException() {
    }

    public TaskExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskExecutionException(String message) {
        super(message);
    }

    public TaskExecutionException(Throwable cause) {
        super(cause);
    }
}
