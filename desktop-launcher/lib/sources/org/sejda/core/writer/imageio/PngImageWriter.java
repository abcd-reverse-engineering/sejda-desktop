package org.sejda.core.writer.imageio;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadata;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.parameter.image.PdfToImageParameters;
import org.sejda.model.parameter.image.PdfToPngParameters;

/* loaded from: org.sejda.sejda-image-writers-5.1.10.jar:org/sejda/core/writer/imageio/PngImageWriter.class */
public class PngImageWriter extends SingleImageWriter<PdfToPngParameters> {
    @Override // org.sejda.core.writer.imageio.SingleImageWriter, org.sejda.core.writer.model.ImageWriter
    public /* bridge */ /* synthetic */ boolean supportMultiImage() {
        return super.supportMultiImage();
    }

    @Override // org.sejda.core.writer.imageio.SingleImageWriter, org.sejda.core.writer.model.ImageWriter
    public /* bridge */ /* synthetic */ void write(RenderedImage renderedImage, PdfToImageParameters pdfToImageParameters) throws TaskIOException {
        super.write(renderedImage, pdfToImageParameters);
    }

    @Override // org.sejda.core.writer.imageio.AbstractImageWriter, org.sejda.core.writer.model.ImageWriter
    public /* bridge */ /* synthetic */ void closeDestination() throws TaskIOException {
        super.closeDestination();
    }

    @Override // org.sejda.core.writer.imageio.AbstractImageWriter, java.io.Closeable, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() throws IOException {
        super.close();
    }

    @Override // org.sejda.core.writer.imageio.AbstractImageWriter, org.sejda.core.writer.model.ImageWriter
    public /* bridge */ /* synthetic */ void openDestination(File file, PdfToImageParameters pdfToImageParameters) throws TaskIOException {
        super.openDestination(file, pdfToImageParameters);
    }

    @Override // org.sejda.core.writer.imageio.AbstractImageWriter
    public /* bridge */ /* synthetic */ IIOMetadata newImageMetadata(RenderedImage renderedImage, PdfToImageParameters pdfToImageParameters, ImageWriteParam imageWriteParam) {
        return super.newImageMetadata(renderedImage, pdfToImageParameters, imageWriteParam);
    }

    public PngImageWriter() {
        super("png");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.sejda.core.writer.imageio.AbstractImageWriter
    public ImageWriteParam newImageWriterParams(PdfToPngParameters params) {
        return null;
    }
}
