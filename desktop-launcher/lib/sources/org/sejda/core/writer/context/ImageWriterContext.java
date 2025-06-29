package org.sejda.core.writer.context;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.sejda.core.writer.imageio.JpegImageWriter;
import org.sejda.core.writer.imageio.PngImageWriter;
import org.sejda.core.writer.imageio.TiffMultiImageWriter;
import org.sejda.core.writer.imageio.TiffSingleImageWriter;
import org.sejda.core.writer.model.ImageWriter;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.parameter.image.PdfToImageParameters;
import org.sejda.model.parameter.image.PdfToJpegParameters;
import org.sejda.model.parameter.image.PdfToMultipleTiffParameters;
import org.sejda.model.parameter.image.PdfToPngParameters;
import org.sejda.model.parameter.image.PdfToSingleTiffParameters;

/* loaded from: org.sejda.sejda-image-writers-5.1.10.jar:org/sejda/core/writer/context/ImageWriterContext.class */
public final class ImageWriterContext {
    private static final Map<Class<? extends PdfToImageParameters>, Class<? extends ImageWriter<?>>> BUILDERS_REGISTRY = new HashMap();

    static {
        BUILDERS_REGISTRY.put(PdfToMultipleTiffParameters.class, TiffSingleImageWriter.class);
        BUILDERS_REGISTRY.put(PdfToSingleTiffParameters.class, TiffMultiImageWriter.class);
        BUILDERS_REGISTRY.put(PdfToJpegParameters.class, JpegImageWriter.class);
        BUILDERS_REGISTRY.put(PdfToPngParameters.class, PngImageWriter.class);
    }

    public static ImageWriterContext getContext() {
        return ImageContextHolder.IMAGE_WRITER_CONTEXT;
    }

    public <T extends PdfToImageParameters> ImageWriter<T> createImageWriter(T params) throws TaskException {
        Class<? extends ImageWriter<?>> writer = BUILDERS_REGISTRY.get(params.getClass());
        if (Objects.isNull(writer)) {
            throw new TaskExecutionException(String.format("No suitable ImageWriter found for %s", params));
        }
        try {
            return (ImageWriter) ImageWriter.class.cast(writer.getConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            throw new TaskException("Unable to create ImageWriter", e);
        }
    }

    /* loaded from: org.sejda.sejda-image-writers-5.1.10.jar:org/sejda/core/writer/context/ImageWriterContext$ImageContextHolder.class */
    private static final class ImageContextHolder {
        static final ImageWriterContext IMAGE_WRITER_CONTEXT = new ImageWriterContext();

        private ImageContextHolder() {
        }
    }
}
