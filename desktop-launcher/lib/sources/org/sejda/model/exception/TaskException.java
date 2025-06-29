package org.sejda.model.exception;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/exception/TaskException.class */
public class TaskException extends Exception {
    private static final long serialVersionUID = -1829569805895216058L;

    public TaskException() {
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(String message) {
        super(message);
    }

    public TaskException(Throwable cause) {
        super(cause);
    }
}
