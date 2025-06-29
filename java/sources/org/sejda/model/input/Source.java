package org.sejda.model.input;

import org.sejda.model.exception.TaskIOException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/Source.class */
public interface Source<T> extends TaskSource<T> {
    <R> R dispatch(SourceDispatcher<R> dispatcher) throws TaskIOException;
}
