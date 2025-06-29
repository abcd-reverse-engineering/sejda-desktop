package org.sejda.core.writer.imageio;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import org.sejda.commons.util.IOUtils;
import org.sejda.core.writer.model.ImageWriter;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.image.TiffCompressionType;
import org.sejda.model.parameter.image.PdfToImageParameters;

/* loaded from: org.sejda.sejda-image-writers-5.1.10.jar:org/sejda/core/writer/imageio/AbstractImageWriter.class */
abstract class AbstractImageWriter<T extends PdfToImageParameters> implements ImageWriter<T> {
    static final Map<TiffCompressionType, String> TIFF_COMPRESSION_TYPE_CACHE = Map.of(TiffCompressionType.PACKBITS, "PackBits", TiffCompressionType.NONE, "None", TiffCompressionType.JPEG_TTN2, "JPEG", TiffCompressionType.DEFLATE, "Deflate", TiffCompressionType.LZW, "LZW", TiffCompressionType.ZLIB, "ZLib", TiffCompressionType.CCITT_GROUP_3_1D, "CCITT RLE", TiffCompressionType.CCITT_GROUP_3_2D, "CCITT T.4", TiffCompressionType.CCITT_GROUP_4, "CCITT T.6");
    private ImageOutputStream out;
    private OutputStream wrappedOut;
    protected final javax.imageio.ImageWriter writer;

    abstract ImageWriteParam newImageWriterParams(T t);

    AbstractImageWriter(String format) {
        Iterator<javax.imageio.ImageWriter> writers = ImageIO.getImageWritersByFormatName(format);
        if (Objects.isNull(writers) || !writers.hasNext()) {
            throw new IllegalArgumentException(String.format("Unable to find an ImageWriter for the format %s", format));
        }
        this.writer = writers.next();
    }

    public IIOMetadata newImageMetadata(RenderedImage image, T params, ImageWriteParam writerParams) {
        return null;
    }

    @Override // org.sejda.core.writer.model.ImageWriter
    public void openDestination(File file, T params) throws TaskIOException {
        try {
            this.wrappedOut = params.getOutput().getEncryptionAtRestPolicy().encrypt(new FileOutputStream(file));
            this.out = ImageIO.createImageOutputStream(this.wrappedOut);
            TaskIOException.require(Objects.nonNull(this.out), "Unable to create image output stream");
            this.writer.setOutput(getOutput());
        } catch (IOException e) {
            throw new TaskIOException("Unable to create output stream.", e);
        }
    }

    ImageOutputStream getOutput() {
        return this.out;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (Objects.nonNull(this.writer)) {
            this.writer.dispose();
        }
    }

    @Override // org.sejda.core.writer.model.ImageWriter
    public void closeDestination() throws TaskIOException {
        try {
            IOUtils.close(getOutput());
            IOUtils.close(this.wrappedOut);
        } catch (IOException e) {
            throw new TaskIOException("Unable to close destination", e);
        }
    }
}
