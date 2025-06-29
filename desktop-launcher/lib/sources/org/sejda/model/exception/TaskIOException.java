package org.sejda.model.exception;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/exception/TaskIOException.class */
public class TaskIOException extends TaskException {
    private static final long serialVersionUID = 5205172452237197631L;

    public TaskIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskIOException(String message) {
        super(message);
    }

    public TaskIOException(Throwable cause) {
        super(cause);
    }

    public static void require(boolean condition, String exceptionMessage) throws TaskIOException {
        if (!condition) {
            throw new TaskIOException(exceptionMessage);
        }
    }
}
