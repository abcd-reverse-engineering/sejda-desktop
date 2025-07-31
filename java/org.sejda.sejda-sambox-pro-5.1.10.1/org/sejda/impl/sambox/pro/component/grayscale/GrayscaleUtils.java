package org.sejda.impl.sambox.pro.component.grayscale;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.zip.DeflaterOutputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.graphics.color.PDCalGray;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.graphics.image.LosslessFactory;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/grayscale/GrayscaleUtils.class */
public final class GrayscaleUtils {
    private static final Logger LOG = LoggerFactory.getLogger(GrayscaleUtils.class);

    public static int toGray(int rgb) {
        Color color = new Color(rgb);
        return (int) ((0.21d * color.getRed()) + (0.71d * color.getGreen()) + (0.08d * color.getBlue()));
    }

    public static float toGray(float[] rgb) {
        return (float) ((0.21d * rgb[0]) + (0.71d * rgb[1]) + (0.08d * rgb[2]));
    }

    public static PDImageXObject toGray(BufferedImage image) throws IOException {
        long start = System.currentTimeMillis();
        PDImageXObject x = new PDImageXObject(toGrayDeflated_PixelByPixel(image), COSName.FLATE_DECODE, image.getWidth(), image.getHeight(), 8, PDDeviceGray.INSTANCE);
        LOG.debug("toGray {} ms", Long.valueOf(System.currentTimeMillis() - start));
        return x;
    }

    public static PDImageXObject toGrayJavaBuiltIn(BufferedImage image) throws IOException {
        BufferedImage gray = new BufferedImage(image.getWidth(), image.getHeight(), 10);
        Graphics2D g = gray.createGraphics();
        g.drawImage(image, 0, 0, (ImageObserver) null);
        g.dispose();
        return LosslessFactory.createFromImage(gray);
    }

    public static InputStream toGrayDeflated_PixelByPixel(BufferedImage image) throws IOException {
        long start = System.currentTimeMillis();
        File tmpFile = Files.createTempFile("grayDeflated", ".tmp", new FileAttribute[0]).toFile();
        DeflaterOutputStream cmp = new DeflaterOutputStream(new FileOutputStream(tmpFile));
        try {
            MemoryCacheImageOutputStream mcios = new MemoryCacheImageOutputStream(cmp);
            try {
                int height = image.getHeight();
                int width = image.getWidth();
                for (int j = 0; j < height; j++) {
                    for (int i = 0; i < width; i++) {
                        mcios.writeBits(toGray(image.getRGB(i, j)) & 255, 8);
                    }
                    int bitOffset = mcios.getBitOffset();
                    if (bitOffset != 0) {
                        mcios.writeBits(0L, 8 - bitOffset);
                    }
                }
                mcios.close();
                cmp.close();
                LOG.debug("toGrayDeflated_PixelByPixel {} ms", Long.valueOf(System.currentTimeMillis() - start));
                return new FileInputStream(tmpFile);
            } finally {
            }
        } catch (Throwable th) {
            try {
                cmp.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static InputStream toGrayDeflated_ImageIO(BufferedImage image) throws IOException {
        try {
            long start = System.currentTimeMillis();
            File tmpFile = IOUtils.createTemporaryBuffer(".jpg");
            DeflaterOutputStream cmp = new DeflaterOutputStream(new FileOutputStream(tmpFile));
            try {
                BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), 10);
                Graphics2D g2d = grayscaleImage.createGraphics();
                g2d.drawImage(image, 0, 0, Color.WHITE, (ImageObserver) null);
                g2d.dispose();
                ImageIO.write(grayscaleImage, "jpg", cmp);
                cmp.close();
                LOG.debug("toGrayDeflated_ImageIO {} ms", Long.valueOf(System.currentTimeMillis() - start));
                return new FileInputStream(tmpFile);
            } finally {
            }
        } catch (TaskIOException ex) {
            throw new IOException(ex.getMessage(), ex);
        }
    }

    public static boolean isGray(COSDictionary stream) {
        COSBase colorspace = stream.getDictionaryObject(COSName.COLORSPACE, COSName.CS);
        if (colorspace instanceof COSArray) {
            COSArray array = (COSArray) colorspace;
            if (array.size() > 0) {
                colorspace = array.get(0);
            }
        }
        return COSName.DEVICEGRAY.equals(colorspace) || COSName.CALGRAY.equals(colorspace) || COSName.G.equals(colorspace);
    }

    public static boolean isGray(PDImageXObject image) throws IOException {
        return (image.getColorSpace() instanceof PDDeviceGray) || (image.getColorSpace() instanceof PDCalGray);
    }

    public static boolean isGrayScale(BufferedImage image) {
        if (image.getType() == 10 || image.getType() == 11 || image.getRaster().getNumBands() == 1) {
            return true;
        }
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int c = 1; c < image.getRaster().getNumBands(); c++) {
                    if (image.getRaster().getSample(x, y, c - 1) != image.getRaster().getSample(x, y, c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
