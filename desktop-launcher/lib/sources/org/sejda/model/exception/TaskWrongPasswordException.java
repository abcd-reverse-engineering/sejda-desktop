package org.sejda.model.exception;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/exception/TaskWrongPasswordException.class */
public class TaskWrongPasswordException extends TaskIOException {
    private static final long serialVersionUID = -5517166148313118559L;

    public TaskWrongPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskWrongPasswordException(String message) {
        super(message);
    }

    public TaskWrongPasswordException(Throwable cause) {
        super(cause);
    }
}
