package org.sejda.sambox.pdmodel.graphics.image;

import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceCMYK;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.w3c.dom.Element;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/image/JPEGFactory.class */
public final class JPEGFactory {
    private JPEGFactory() {
    }

    public static PDImageXObject createFromFile(File file) throws IOException {
        SeekableSource source = SeekableSources.seekableSourceFrom(file);
        try {
            PDImageXObject pDImageXObjectCreateFromSeekableSource = createFromSeekableSource(source);
            if (source != null) {
                source.close();
            }
            return pDImageXObjectCreateFromSeekableSource;
        } catch (Throwable th) {
            if (source != null) {
                try {
                    source.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static PDImageXObject createFromSeekableSource(SeekableSource source) throws IOException {
        BufferedImage awtImage = readJpeg(source.asNewInputStream());
        PDImageXObject pdImage = new PDImageXObject(new BufferedInputStream(source.asNewInputStream()), COSName.DCT_DECODE, awtImage.getWidth(), awtImage.getHeight(), awtImage.getColorModel().getComponentSize(0), getColorSpaceFromAWT(awtImage));
        if (awtImage.getColorModel().hasAlpha()) {
            throw new UnsupportedOperationException("alpha channel not implemented");
        }
        return pdImage;
    }

    public static BufferedImage readJpegFile(File file) throws IOException {
        return readJpeg(file);
    }

    private static BufferedImage readJpeg(Object fileOrStream) throws IOException {
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("JPEG");
        ImageReader reader = null;
        while (readers.hasNext()) {
            reader = readers.next();
            if (reader.canReadRaster()) {
                break;
            }
        }
        RequireUtils.requireIOCondition(Objects.nonNull(reader), "Cannot find an ImageIO reader for JPEG image");
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(fileOrStream);
            try {
                reader.setInput(iis);
                ImageIO.setUseCache(false);
                BufferedImage bufferedImage = reader.read(0);
                if (iis != null) {
                    iis.close();
                }
                return bufferedImage;
            } finally {
            }
        } finally {
            reader.dispose();
        }
    }

    public static PDImageXObject createFromImage(BufferedImage image) throws IOException {
        return createFromImage(image, 0.75f);
    }

    public static PDImageXObject createFromImage(BufferedImage image, float quality) throws IOException {
        return createFromImage(image, quality, 72);
    }

    public static PDImageXObject createFromImage(BufferedImage image, float quality, int dpi) throws IOException {
        return createJPEG(image, quality, dpi);
    }

    private static BufferedImage getAlphaImage(BufferedImage image) {
        if (!image.getColorModel().hasAlpha()) {
            return null;
        }
        if (image.getTransparency() == 2) {
            throw new UnsupportedOperationException("BITMASK Transparency JPEG compression is not useful, use LosslessImageFactory instead");
        }
        WritableRaster alphaRaster = image.getAlphaRaster();
        if (alphaRaster == null) {
            return null;
        }
        BufferedImage alphaImage = new BufferedImage(image.getWidth(), image.getHeight(), 10);
        alphaImage.setData(alphaRaster);
        return alphaImage;
    }

    private static PDImageXObject createJPEG(BufferedImage image, float quality, int dpi) throws IOException {
        BufferedImage awtColorImage = getColorImage(image);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(encodeImageToJPEGStream(awtColorImage, quality, dpi));
        PDImageXObject pdImage = new PDImageXObject(byteStream, COSName.DCT_DECODE, awtColorImage.getWidth(), awtColorImage.getHeight(), 8, getColorSpaceFromAWT(awtColorImage));
        BufferedImage awtAlphaImage = getAlphaImage(image);
        if (awtAlphaImage != null) {
            PDImage xAlpha = createFromImage(awtAlphaImage, quality);
            pdImage.getCOSObject().setItem(COSName.SMASK, xAlpha);
        }
        return pdImage;
    }

    private static byte[] encodeImageToJPEGStream(BufferedImage image, float quality, int dpi) throws IOException {
        ImageWriter imageWriter = (ImageWriter) ImageIO.getImageWritersBySuffix("jpeg").next();
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        try {
            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            try {
                imageWriter.setOutput(ios);
                ImageWriteParam jpegParam = imageWriter.getDefaultWriteParam();
                jpegParam.setCompressionMode(2);
                jpegParam.setCompressionQuality(quality);
                ImageTypeSpecifier imageTypeSpecifier = new ImageTypeSpecifier(image);
                IIOMetadata metadata = imageWriter.getDefaultImageMetadata(imageTypeSpecifier, jpegParam);
                Element tree = (Element) metadata.getAsTree("javax_imageio_jpeg_image_1.0");
                Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
                String dpiString = Integer.toString(dpi);
                jfif.setAttribute("Xdensity", dpiString);
                jfif.setAttribute("Ydensity", dpiString);
                jfif.setAttribute("resUnits", "1");
                imageWriter.write(metadata, new IIOImage(image, (List) null, (IIOMetadata) null), jpegParam);
                if (ios != null) {
                    ios.close();
                }
                return out.toByteArray();
            } finally {
            }
        } finally {
            if (imageWriter != null) {
                imageWriter.dispose();
            }
        }
    }

    public static PDColorSpace getColorSpaceFromAWT(BufferedImage awtImage) {
        if (awtImage.getColorModel().getNumComponents() == 1) {
            return PDDeviceGray.INSTANCE;
        }
        ColorSpace awtColorSpace = awtImage.getColorModel().getColorSpace();
        if ((awtColorSpace instanceof ICC_ColorSpace) && !awtColorSpace.isCS_sRGB()) {
            throw new UnsupportedOperationException("ICC color spaces not implemented");
        }
        switch (awtColorSpace.getType()) {
            case 5:
                return PDDeviceRGB.INSTANCE;
            case 6:
                return PDDeviceGray.INSTANCE;
            case 7:
            case 8:
            default:
                throw new UnsupportedOperationException("color space not implemented: " + awtColorSpace.getType());
            case 9:
                return PDDeviceCMYK.INSTANCE;
        }
    }

    private static BufferedImage getColorImage(BufferedImage image) {
        if (!image.getColorModel().hasAlpha()) {
            return image;
        }
        if (image.getColorModel().getColorSpace().getType() != 5) {
            throw new UnsupportedOperationException("only RGB color spaces are implemented");
        }
        BufferedImage rgbImage = new BufferedImage(image.getWidth(), image.getHeight(), 5);
        return new ColorConvertOp((RenderingHints) null).filter(image, rgbImage);
    }
}
