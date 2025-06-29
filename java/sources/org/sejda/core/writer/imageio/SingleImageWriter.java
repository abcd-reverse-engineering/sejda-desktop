package org.sejda.core.writer.imageio;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadata;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.parameter.image.PdfToImageParameters;

/* loaded from: org.sejda.sejda-image-writers-5.1.10.jar:org/sejda/core/writer/imageio/SingleImageWriter.class */
abstract class SingleImageWriter<T extends PdfToImageParameters> extends AbstractImageWriter<T> {
    SingleImageWriter(String imageFormat) {
        super(imageFormat);
    }

    @Override // org.sejda.core.writer.model.ImageWriter
    public void write(RenderedImage image, T params) throws TaskIOException {
        TaskIOException.require(Objects.nonNull(getOutput()), "Cannot call write before opening the write destination");
        ImageWriteParam imageWriterParams = newImageWriterParams(params);
        try {
            this.writer.write((IIOMetadata) null, new IIOImage(image, (List) null, newImageMetadata(image, params, imageWriterParams)), imageWriterParams);
        } catch (IOException e) {
            throw new TaskIOException(e);
        }
    }

    @Override // org.sejda.core.writer.model.ImageWriter
    public boolean supportMultiImage() {
        return false;
    }
}
