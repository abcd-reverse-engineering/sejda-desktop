package org.sejda.core.writer.imageio;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadata;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.parameter.image.PdfToImageParameters;

/* loaded from: org.sejda.sejda-image-writers-5.1.10.jar:org/sejda/core/writer/imageio/MultiImageWriter.class */
abstract class MultiImageWriter<T extends PdfToImageParameters> extends AbstractImageWriter<T> {
    private boolean prepared;
    private ImageWriteParam imageWriterParams;

    MultiImageWriter(String format) {
        super(format);
        this.prepared = false;
        if (!this.writer.canWriteSequence()) {
            throw new UnsupportedOperationException("The ImageWriter does not support writing multiple images to a single image file.");
        }
    }

    @Override // org.sejda.core.writer.imageio.AbstractImageWriter, org.sejda.core.writer.model.ImageWriter
    public void openDestination(File file, T params) throws TaskIOException {
        super.openDestination(file, params);
        this.imageWriterParams = newImageWriterParams(params);
    }

    @Override // org.sejda.core.writer.model.ImageWriter
    public void write(RenderedImage image, T params) throws TaskIOException {
        TaskIOException.require(Objects.nonNull(getOutput()), "Cannot call write before opening the write destination");
        try {
            if (!this.prepared) {
                this.writer.prepareWriteSequence((IIOMetadata) null);
                this.prepared = true;
            }
            this.writer.writeToSequence(new IIOImage(image, (List) null, newImageMetadata(image, params, this.imageWriterParams)), this.imageWriterParams);
        } catch (IOException e) {
            throw new TaskIOException(e);
        }
    }

    @Override // org.sejda.core.writer.imageio.AbstractImageWriter, org.sejda.core.writer.model.ImageWriter
    public void closeDestination() throws TaskIOException {
        if (Objects.nonNull(this.writer)) {
            try {
                this.writer.endWriteSequence();
            } catch (IOException e) {
                throw new TaskIOException("An error occurred while ending write sequence", e);
            }
        }
        super.closeDestination();
    }

    @Override // org.sejda.core.writer.model.ImageWriter
    public boolean supportMultiImage() {
        return true;
    }
}
