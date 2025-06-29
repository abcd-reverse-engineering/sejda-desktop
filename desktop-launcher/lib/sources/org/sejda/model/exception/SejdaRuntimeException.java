package org.sejda.model.exception;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/exception/SejdaRuntimeException.class */
public class SejdaRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1630506833274259591L;

    public SejdaRuntimeException() {
    }

    public SejdaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SejdaRuntimeException(String message) {
        super(message);
    }

    public SejdaRuntimeException(Throwable cause) {
        super(cause);
    }
}
