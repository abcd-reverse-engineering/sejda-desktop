package org.sejda.model.input;

import org.sejda.model.exception.TaskIOException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/PdfSourceOpener.class */
public interface PdfSourceOpener<T> {
    T open(PdfURLSource source) throws TaskIOException;

    T open(PdfFileSource source) throws TaskIOException;

    T open(PdfStreamSource source) throws TaskIOException;
}
