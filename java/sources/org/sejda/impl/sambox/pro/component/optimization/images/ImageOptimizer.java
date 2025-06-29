package org.sejda.impl.sambox.pro.component.optimization.images;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;
import org.sejda.commons.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/images/ImageOptimizer.class */
public class ImageOptimizer {
    private static final Logger LOG = LoggerFactory.getLogger(ImageOptimizer.class);

    public static File optimize(BufferedImage bufferedImage, float quality, int dpi, int width, int height, boolean convertToGray) throws IOException {
        long start = System.currentTimeMillis();
        File outputFile = File.createTempFile("pdfimage", ".jpeg");
        outputFile.deleteOnExit();
        try {
            BufferedImage newImage = maybeResize(bufferedImage, width, height, convertToGray, quality);
            ImageWriter imageWriter = (ImageWriter) ImageIO.getImageWritersBySuffix("jpeg").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile);
            imageWriter.setOutput(ios);
            JPEGImageWriteParam jpegParams = imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(2);
            jpegParams.setCompressionQuality(quality);
            IIOMetadata imageMetaData = null;
            try {
                imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(newImage), jpegParams);
                Element tree = (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
                Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
                jfif.setAttribute("Xdensity", Integer.toString(dpi));
                jfif.setAttribute("Ydensity", Integer.toString(dpi));
                jfif.setAttribute("resUnits", "1");
                imageMetaData.setFromTree("javax_imageio_jpeg_image_1.0", tree);
            } catch (Exception e) {
                LOG.warn("Failed to set DPI for image, metadata manipulation failed", e);
            }
            try {
                imageWriter.write((IIOMetadata) null, new IIOImage(newImage, (List) null, imageMetaData), jpegParams);
                IOUtils.closeQuietly(ios);
                imageWriter.dispose();
                return outputFile;
            } catch (Throwable th) {
                IOUtils.closeQuietly(ios);
                imageWriter.dispose();
                throw th;
            }
        } finally {
            bufferedImage.flush();
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed > 500) {
                LOG.trace("Optimizing image took {}ms", Long.valueOf(elapsed));
            }
        }
    }

    private static BufferedImage maybeResize(BufferedImage bufferedImage, int width, int height, boolean convertToGray, float quality) {
        BufferedImage currentImage = bufferedImage;
        boolean isResizeRelevant = Math.abs(currentImage.getWidth() - width) > 20 && Math.abs(currentImage.getHeight() - height) > 20;
        boolean isShirinking = currentImage.getHeight() > height || currentImage.getWidth() > width;
        if (isResizeRelevant && isShirinking) {
            LOG.debug("Resizing image from {}x{} to {}x{}", new Object[]{Integer.valueOf(currentImage.getWidth()), Integer.valueOf(currentImage.getHeight()), Integer.valueOf(width), Integer.valueOf(height)});
            try {
                currentImage = Thumbnails.of(new BufferedImage[]{currentImage}).size(width, height).scalingMode(scalingMode(quality)).asBufferedImage();
            } catch (IOException e) {
                LOG.warn("Unable to resize image", e);
            }
        }
        return maybeOpaque(currentImage, convertToGray);
    }

    private static ScalingMode scalingMode(float quality) {
        if (quality > 0.8d) {
            return ScalingMode.PROGRESSIVE_BILINEAR;
        }
        return ScalingMode.BICUBIC;
    }

    private static int imageType(boolean convertToGray) {
        if (convertToGray) {
            return 10;
        }
        return 1;
    }

    private static BufferedImage maybeOpaque(BufferedImage image, boolean convertToGray) {
        if (image.getColorModel().hasAlpha() || (convertToGray && image.getType() != 10)) {
            LOG.debug("Applying white background to image with transparency");
            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), imageType(convertToGray));
            Graphics2D g2d = newImage.createGraphics();
            g2d.drawImage(image, 0, 0, Color.WHITE, (ImageObserver) null);
            g2d.dispose();
            return newImage;
        }
        return image;
    }
}
