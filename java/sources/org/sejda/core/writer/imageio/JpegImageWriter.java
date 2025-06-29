package org.sejda.core.writer.imageio;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.parameter.image.PdfToImageParameters;
import org.sejda.model.parameter.image.PdfToJpegParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/* loaded from: org.sejda.sejda-image-writers-5.1.10.jar:org/sejda/core/writer/imageio/JpegImageWriter.class */
public class JpegImageWriter extends SingleImageWriter<PdfToJpegParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(JpegImageWriter.class);

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

    public JpegImageWriter() {
        super("jpeg");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.sejda.core.writer.imageio.AbstractImageWriter
    public ImageWriteParam newImageWriterParams(PdfToJpegParameters params) {
        JPEGImageWriteParam param = new JPEGImageWriteParam(this.writer.getLocale());
        param.setCompressionMode(2);
        param.setCompressionQuality(params.getQuality() / 100.0f);
        return param;
    }

    @Override // org.sejda.core.writer.imageio.AbstractImageWriter
    public IIOMetadata newImageMetadata(RenderedImage image, PdfToJpegParameters params, ImageWriteParam writerParams) throws DOMException {
        IIOMetadata imageMetaData = null;
        try {
            imageMetaData = this.writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), writerParams);
            Element tree = (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
            Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
            jfif.setAttribute("Xdensity", Integer.toString(params.getResolutionInDpi()));
            jfif.setAttribute("Ydensity", Integer.toString(params.getResolutionInDpi()));
            jfif.setAttribute("resUnits", "1");
            imageMetaData.setFromTree("javax_imageio_jpeg_image_1.0", tree);
        } catch (Exception e1) {
            LOG.warn("Failed to set DPI for image, metadata manipulation failed", e1);
        }
        return imageMetaData;
    }
}
