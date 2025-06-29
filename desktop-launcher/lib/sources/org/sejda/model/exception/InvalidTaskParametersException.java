package org.sejda.model.exception;

import java.util.List;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/exception/InvalidTaskParametersException.class */
public class InvalidTaskParametersException extends TaskException {
    private static final long serialVersionUID = 1046358680829746043L;
    private List<String> reasons;

    public InvalidTaskParametersException(String message, List<String> reasons) {
        super(message);
        this.reasons = reasons;
    }

    public List<String> getReasons() {
        return this.reasons;
    }
}
