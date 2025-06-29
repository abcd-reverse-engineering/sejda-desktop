package org.sejda.core.writer.model;

import java.awt.image.RenderedImage;
import java.io.Closeable;
import java.io.File;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.parameter.image.PdfToImageParameters;

/* loaded from: org.sejda.sejda-image-writers-5.1.10.jar:org/sejda/core/writer/model/ImageWriter.class */
public interface ImageWriter<T extends PdfToImageParameters> extends Closeable {
    void openDestination(File file, T t) throws TaskIOException;

    void closeDestination() throws TaskIOException;

    void write(RenderedImage renderedImage, T t) throws TaskIOException;

    boolean supportMultiImage();
}
