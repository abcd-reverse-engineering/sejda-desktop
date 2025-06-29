package org.sejda.model.input;

import org.sejda.model.exception.TaskIOException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/PdfSource.class */
public interface PdfSource<T> extends TaskSource<T> {
    String getPassword();

    void setPassword(String password);

    <R> R open(PdfSourceOpener<R> opener) throws TaskIOException;
}
