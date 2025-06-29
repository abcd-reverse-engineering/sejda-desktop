package org.sejda.impl.sambox.component;

import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_ProfileGray;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.imageio.IIOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import net.coobird.thumbnailator.Thumbnails;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.Source;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.sejda.sambox.pdmodel.graphics.image.UnsupportedImageFormatException;
import org.sejda.sambox.pdmodel.graphics.image.UnsupportedTiffImageException;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.sejda.sambox.util.Matrix;
import org.sejda.sambox.util.filetypedetector.FileType;
import org.sejda.sambox.util.filetypedetector.FileTypeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PageImageWriter.class */
public class PageImageWriter {
    private static final Logger LOG = LoggerFactory.getLogger(PageImageWriter.class);
    private PDDocument document;

    public PageImageWriter(PDDocument document) {
        this.document = document;
    }

    public void append(PDPage page, PDImageXObject image, Point2D position, float width, float height, PDExtendedGraphicsState gs, int rotation) throws TaskIOException {
        write(page, image, position, width, height, PDPageContentStream.AppendMode.APPEND, gs, true, rotation);
    }

    public void append(PDPage page, PDFormXObject image, Point2D position, float width, float height, PDExtendedGraphicsState gs, int rotation) throws TaskIOException {
        write(page, image, position, width, height, PDPageContentStream.AppendMode.APPEND, gs, true, rotation);
    }

    public void prepend(PDPage page, PDImageXObject image, Point2D position, float width, float height, PDExtendedGraphicsState gs, int rotation) throws TaskIOException {
        write(page, image, position, width, height, PDPageContentStream.AppendMode.PREPEND, gs, false, rotation);
    }

    public void prepend(PDPage page, PDFormXObject image, Point2D position, float width, float height, PDExtendedGraphicsState gs, int rotation) throws TaskIOException {
        write(page, image, position, width, height, PDPageContentStream.AppendMode.PREPEND, gs, false, rotation);
    }

    private void write(PDPage page, PDXObject image, Point2D position, float width, float height, PDPageContentStream.AppendMode mode, PDExtendedGraphicsState gs, boolean resetContext, int rotation) throws TaskIOException {
        try {
            PDPageContentStream contentStream = new PDPageContentStream(this.document, page, mode, true, resetContext);
            try {
                AffineTransform at = new AffineTransform(width, 0.0f, 0.0f, height, (float) position.getX(), (float) position.getY());
                if (rotation != 0) {
                    at.rotate(Math.toRadians(rotation));
                }
                if (image instanceof PDFormXObject) {
                    contentStream.drawImage((PDFormXObject) image, new Matrix(at), gs);
                } else {
                    contentStream.drawImage((PDImageXObject) image, new Matrix(at), gs);
                }
                contentStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new TaskIOException("An error occurred writing image to the page.", e);
        }
    }

    public static PDImageXObject toPDXImageObject(Source<?> source) throws TaskIOException {
        return toPDXImageObject(source, 0);
    }

    public static PDImageXObject toPDXImageObject(Source<?> source, int number) throws TaskIOException {
        try {
            return createFromSeekableSource(source.getSeekableSource(), source.getName(), number);
        } catch (Exception e) {
            String message = "An error occurred creating PDImageXObject from file source: " + source.getName();
            if (number > 0) {
                message = message + " and number: " + number;
            }
            throw new TaskIOException(message, e);
        }
    }

    public static PDImageXObject createFromSeekableSource(SeekableSource original, String name) throws TaskIOException, IOException {
        return createFromSeekableSource(original, name, 0);
    }

    public static PDImageXObject createFromSeekableSource(SeekableSource original, String name, int number) throws TaskIOException, IOException, IIOException {
        SeekableSource source = original;
        if (number == 0) {
            Optional<SeekableSource> maybeConvertedFile = convertExifRotatedIf(source, name);
            if (maybeConvertedFile.isPresent()) {
                source = maybeConvertedFile.get();
            }
            Optional<SeekableSource> maybeConvertedFile2 = convertCMYKJpegIf(source, name);
            if (maybeConvertedFile2.isPresent()) {
                source = maybeConvertedFile2.get();
            }
            Optional<SeekableSource> maybeConvertedFile3 = convertICCGrayPngIf(source, name);
            if (maybeConvertedFile3.isPresent()) {
                source = maybeConvertedFile3.get();
            }
        }
        try {
            return PDImageXObject.createFromSeekableSource(source, name, number);
        } catch (UnsupportedTiffImageException e) {
            LOG.warn("Found unsupported TIFF compression, converting TIFF to JPEG: " + e.getMessage());
            try {
                return PDImageXObject.createFromSeekableSource(convertTiffToJpg(source, number), name);
            } catch (UnsupportedOperationException ex) {
                if (ex.getMessage().contains("alpha channel")) {
                    LOG.warn("Found alpha channel image, JPEG compression failed, converting TIFF to PNG");
                    return PDImageXObject.createFromSeekableSource(convertTiffToPng(source, number), name);
                }
                throw ex;
            }
        }
    }

    public static SeekableSource convertTiffToJpg(SeekableSource source, int number) throws TaskIOException, IOException {
        return convertImageTo(source, number, "jpeg");
    }

    public static SeekableSource convertTiffToPng(SeekableSource source, int number) throws TaskIOException, IOException {
        return convertImageTo(source, number, "png");
    }

    private static FileType getFileType(SeekableSource source) {
        try {
            return FileTypeDetector.detectFileType(source);
        } catch (IOException e) {
            return null;
        }
    }

    public static BufferedImage read(SeekableSource source, int number) throws IOException {
        BufferedImage image = null;
        ImageInputStream is = ImageIO.createImageInputStream(source.asNewInputStream());
        Iterator<ImageReader> readers = ImageIO.getImageReaders(is);
        if (readers.hasNext()) {
            ImageReader reader = readers.next();
            reader.setInput(is);
            try {
                image = reader.read(number);
                reader.dispose();
                is.close();
            } catch (Throwable th) {
                reader.dispose();
                is.close();
                throw th;
            }
        }
        return image;
    }

    public static SeekableSource convertImageTo(SeekableSource source, int number, String format) throws TaskIOException, IOException {
        BufferedImage image = read(source, number);
        File tmpFile = IOUtils.createTemporaryBuffer("." + format);
        FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(tmpFile);
        try {
            ImageWriter writer = (ImageWriter) ImageIO.getImageWritersByFormatName(format).next();
            writer.setOutput(fileImageOutputStream);
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (format.equals("jpeg")) {
                param.setCompressionMode(2);
                param.setCompressionQuality(1.0f);
            }
            writer.write((IIOMetadata) null, new IIOImage(image, (List) null, (IIOMetadata) null), param);
            org.sejda.commons.util.IOUtils.closeQuietly(fileImageOutputStream);
            return SeekableSources.seekableSourceFrom(tmpFile);
        } catch (Throwable th) {
            org.sejda.commons.util.IOUtils.closeQuietly(fileImageOutputStream);
            throw th;
        }
    }

    private static Optional<SeekableSource> convertExifRotatedIf(SeekableSource source, String name) throws TaskIOException, IOException {
        int degrees = ExifHelper.getRotationBasedOnExifOrientation(source.asNewInputStream());
        if (degrees > 0) {
            BufferedImage orig = ImageIO.read(source.asNewInputStream());
            if (orig == null) {
                FileType type = FileTypeDetector.detectFileType(source);
                throw new UnsupportedImageFormatException(type, name, null);
            }
            BufferedImage result = Thumbnails.of(new BufferedImage[]{orig}).scale(1.0d).rotate(degrees).asBufferedImage();
            File tmpFile = IOUtils.createTemporaryBufferWithName(name);
            ImageIO.write(result, getImageIOSaveFormat(source), tmpFile);
            return Optional.of(SeekableSources.seekableSourceFrom(tmpFile));
        }
        return Optional.empty();
    }

    private static String getImageIOSaveFormat(SeekableSource source) {
        FileType fileType = getFileType(source);
        if (fileType == FileType.JPEG) {
            return "jpg";
        }
        return "png";
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.imageio.IIOException */
    private static Optional<SeekableSource> convertCMYKJpegIf(SeekableSource source, String name) throws TaskIOException, IIOException, IOException {
        try {
            if (FileType.JPEG.equals(getFileType(source))) {
                ImageInputStream iis = ImageIO.createImageInputStream(source.asNewInputStream());
                try {
                    ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("jpg").next();
                    boolean isCmyk = false;
                    try {
                        ImageIO.setUseCache(false);
                        reader.setInput(iis);
                        Iterator<ImageTypeSpecifier> it = reader.getImageTypes(0);
                        while (it.hasNext()) {
                            ImageTypeSpecifier typeSpecifier = it.next();
                            if (typeSpecifier.getColorModel().getColorSpace().getType() == 9) {
                                isCmyk = true;
                            }
                        }
                        if (isCmyk) {
                            LOG.debug("Detected a CMYK JPEG image, will convert to RGB and save to a new file");
                            BufferedImage image = reader.read(0);
                            File tmpFile = IOUtils.createTemporaryBufferWithName(name);
                            ImageIO.write(image, "jpg", tmpFile);
                            Optional<SeekableSource> optionalOf = Optional.of(SeekableSources.seekableSourceFrom(tmpFile));
                            reader.dispose();
                            if (iis != null) {
                                iis.close();
                            }
                            return optionalOf;
                        }
                        reader.dispose();
                        if (iis != null) {
                            iis.close();
                        }
                    } catch (Throwable th) {
                        reader.dispose();
                        throw th;
                    }
                } finally {
                }
            }
        } catch (IIOException e) {
            if (!e.getMessage().startsWith("Not a JPEG stream")) {
                throw e;
            }
        }
        return Optional.empty();
    }

    private static Optional<SeekableSource> convertICCGrayPngIf(SeekableSource source, String name) throws TaskIOException, IOException {
        try {
            if (FileType.PNG.equals(getFileType(source))) {
                ImageInputStream iis = ImageIO.createImageInputStream(source.asNewInputStream());
                try {
                    ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("png").next();
                    boolean isICCGray = false;
                    try {
                        ImageIO.setUseCache(false);
                        reader.setInput(iis);
                        Iterator<ImageTypeSpecifier> it = reader.getImageTypes(0);
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            ImageTypeSpecifier typeSpecifier = it.next();
                            ICC_ColorSpace colorSpace = typeSpecifier.getColorModel().getColorSpace();
                            if ((colorSpace instanceof ICC_ColorSpace) && (colorSpace.getProfile() instanceof ICC_ProfileGray)) {
                                isICCGray = true;
                                break;
                            }
                        }
                        if (isICCGray) {
                            LOG.debug("Detected a Gray PNG image, will convert to RGB and save to a new file");
                            BufferedImage original = reader.read(0);
                            BufferedImage rgb = toARGB(original);
                            File tmpFile = IOUtils.createTemporaryBufferWithName(name);
                            ImageIO.write(rgb, "png", tmpFile);
                            Optional<SeekableSource> optionalOf = Optional.of(SeekableSources.seekableSourceFrom(tmpFile));
                            reader.dispose();
                            if (iis != null) {
                                iis.close();
                            }
                            return optionalOf;
                        }
                        reader.dispose();
                        if (iis != null) {
                            iis.close();
                        }
                    } catch (Throwable th) {
                        reader.dispose();
                        throw th;
                    }
                } finally {
                }
            }
        } catch (IIOException e) {
            LOG.debug("Failed convertICCGrayPngIf()", e);
        }
        return Optional.empty();
    }

    private static BufferedImage toARGB(BufferedImage i) {
        BufferedImage rgb = new BufferedImage(i.getWidth((ImageObserver) null), i.getHeight((ImageObserver) null), 2);
        rgb.createGraphics().drawImage(i, 0, 0, (ImageObserver) null);
        return rgb;
    }
}
