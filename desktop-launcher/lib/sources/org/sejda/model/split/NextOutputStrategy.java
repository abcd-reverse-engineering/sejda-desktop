package org.sejda.model.split;

import org.sejda.model.exception.TaskException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/split/NextOutputStrategy.class */
public interface NextOutputStrategy {
    void ensureIsValid() throws TaskException;

    boolean isOpening(Integer page) throws TaskException;

    boolean isClosing(Integer page) throws TaskException;
}
