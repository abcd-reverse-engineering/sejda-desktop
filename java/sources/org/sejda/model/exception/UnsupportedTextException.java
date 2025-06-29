package org.sejda.model.exception;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/exception/UnsupportedTextException.class */
public class UnsupportedTextException extends TaskIOException {
    private String unsupportedChars;

    public UnsupportedTextException(String message, String unsupportedChars) {
        super(message);
        this.unsupportedChars = unsupportedChars;
    }

    public String getUnsupportedChars() {
        return this.unsupportedChars;
    }
}
