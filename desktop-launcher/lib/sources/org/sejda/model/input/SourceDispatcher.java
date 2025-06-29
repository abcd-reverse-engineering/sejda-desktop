package org.sejda.model.input;

import org.sejda.model.exception.TaskIOException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/SourceDispatcher.class */
public interface SourceDispatcher<T> {
    T dispatch(FileSource source) throws TaskIOException;

    T dispatch(StreamSource source) throws TaskIOException;
}
