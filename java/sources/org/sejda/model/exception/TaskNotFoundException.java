package org.sejda.model.exception;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/exception/TaskNotFoundException.class */
public class TaskNotFoundException extends TaskException {
    private static final long serialVersionUID = 1245281490666874279L;

    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(Throwable cause) {
        super(cause);
    }
}
