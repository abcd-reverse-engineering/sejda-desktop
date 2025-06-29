package org.sejda.model.exception;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/exception/TaskNonLenientExecutionException.class */
public class TaskNonLenientExecutionException extends TaskExecutionException {
    private static final long serialVersionUID = -3283178318931486615L;

    public TaskNonLenientExecutionException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public TaskNonLenientExecutionException(String message) {
        super(message);
    }
}
