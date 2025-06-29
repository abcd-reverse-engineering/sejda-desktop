package org.sejda.sambox.pdmodel.graphics.image;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.imageio.stream.MemoryCacheImageInputStream;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.graphics.color.PDIndexed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/image/SampledImageReader.class */
final class SampledImageReader {
    private static final Logger LOG;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !SampledImageReader.class.desiredAssertionStatus();
        LOG = LoggerFactory.getLogger(SampledImageReader.class);
    }

    private SampledImageReader() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0105, code lost:
    
        org.sejda.sambox.pdmodel.graphics.image.SampledImageReader.LOG.warn("premature EOF, image will be incomplete");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.awt.image.BufferedImage getStencilImage(org.sejda.sambox.pdmodel.graphics.image.PDImage r6, java.awt.Paint r7) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 317
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.sejda.sambox.pdmodel.graphics.image.SampledImageReader.getStencilImage(org.sejda.sambox.pdmodel.graphics.image.PDImage, java.awt.Paint):java.awt.image.BufferedImage");
    }

    public static BufferedImage getRGBImage(PDImage pdImage, COSArray colorKey) throws IOException {
        if (pdImage.isEmpty()) {
            throw new IOException("Image stream is empty");
        }
        PDColorSpace colorSpace = pdImage.getColorSpace();
        int numComponents = colorSpace.getNumberOfComponents();
        int width = pdImage.getWidth();
        int height = pdImage.getHeight();
        int bitsPerComponent = pdImage.getBitsPerComponent();
        if (width <= 0 || height <= 0) {
            throw new IOException("image width and height must be positive");
        }
        try {
            if (bitsPerComponent == 1 && colorKey == null && numComponents == 1) {
                return from1Bit(pdImage);
            }
            WritableRaster raster = Raster.createBandedRaster(0, width, height, numComponents, new Point(0, 0));
            float[] defaultDecode = pdImage.getColorSpace().getDefaultDecode(8);
            float[] decode = getDecodeArray(pdImage);
            if (bitsPerComponent == 8 && colorKey == null && Arrays.equals(decode, defaultDecode)) {
                return from8bit(pdImage, raster);
            }
            return fromAny(pdImage, raster, colorKey);
        } catch (NegativeArraySizeException ex) {
            throw new IOException(ex);
        }
    }

    public static WritableRaster getRawRaster(PDImage pdImage) throws IOException {
        if (pdImage.isEmpty()) {
            throw new IOException("Image stream is empty");
        }
        PDColorSpace colorSpace = pdImage.getColorSpace();
        int numComponents = colorSpace.getNumberOfComponents();
        int width = pdImage.getWidth();
        int height = pdImage.getHeight();
        int bitsPerComponent = pdImage.getBitsPerComponent();
        if (width <= 0 || height <= 0) {
            throw new IOException("image width and height must be positive");
        }
        int dataBufferType = 0;
        if (bitsPerComponent > 8) {
            dataBufferType = 1;
        }
        try {
            WritableRaster raster = Raster.createInterleavedRaster(dataBufferType, width, height, numComponents, new Point(0, 0));
            readRasterFromAny(pdImage, raster);
            return raster;
        } catch (NegativeArraySizeException ex) {
            throw new IOException(ex);
        }
    }

    private static void readRasterFromAny(PDImage pdImage, WritableRaster raster) throws IOException {
        PDColorSpace colorSpace = pdImage.getColorSpace();
        int numComponents = colorSpace.getNumberOfComponents();
        int bitsPerComponent = pdImage.getBitsPerComponent();
        float[] decode = getDecodeArray(pdImage);
        MemoryCacheImageInputStream memoryCacheImageInputStream = new MemoryCacheImageInputStream(pdImage.createInputStream());
        try {
            int inputWidth = pdImage.getWidth();
            int scanWidth = pdImage.getWidth();
            int scanHeight = pdImage.getHeight();
            float sampleMax = ((float) Math.pow(2.0d, bitsPerComponent)) - 1.0f;
            boolean isIndexed = colorSpace instanceof PDIndexed;
            int padding = 0;
            if (((inputWidth * numComponents) * bitsPerComponent) % 8 > 0) {
                padding = 8 - (((inputWidth * numComponents) * bitsPerComponent) % 8);
            }
            boolean isShort = raster.getDataBuffer().getDataType() == 1;
            if (!$assertionsDisabled && isIndexed && isShort) {
                throw new AssertionError();
            }
            byte[] srcColorValuesBytes = isShort ? null : new byte[numComponents];
            short[] srcColorValuesShort = isShort ? new short[numComponents] : null;
            for (int y = 0; y < scanHeight; y++) {
                for (int x = 0; x < scanWidth; x++) {
                    for (int c = 0; c < numComponents; c++) {
                        int value = (int) memoryCacheImageInputStream.readBits(bitsPerComponent);
                        float dMin = decode[c * 2];
                        float dMax = decode[(c * 2) + 1];
                        float output = dMin + (value * ((dMax - dMin) / sampleMax));
                        if (isIndexed) {
                            srcColorValuesBytes[c] = (byte) Math.round(output);
                        } else if (isShort) {
                            int outputShort = Math.round(((output - Math.min(dMin, dMax)) / Math.abs(dMax - dMin)) * 65535.0f);
                            srcColorValuesShort[c] = (short) outputShort;
                        } else {
                            int outputByte = Math.round(((output - Math.min(dMin, dMax)) / Math.abs(dMax - dMin)) * 255.0f);
                            srcColorValuesBytes[c] = (byte) outputByte;
                        }
                    }
                    if (isShort) {
                        raster.setDataElements(x, y, srcColorValuesShort);
                    } else {
                        raster.setDataElements(x, y, srcColorValuesBytes);
                    }
                }
                memoryCacheImageInputStream.readBits(padding);
            }
            memoryCacheImageInputStream.close();
        } catch (Throwable th) {
            try {
                memoryCacheImageInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static BufferedImage from1Bit(PDImage pdImage) throws IOException {
        WritableRaster raster;
        byte value0;
        byte value1;
        PDColorSpace colorSpace = pdImage.getColorSpace();
        int width = pdImage.getWidth();
        int height = pdImage.getHeight();
        float[] decode = getDecodeArray(pdImage);
        BufferedImage bim = null;
        if (colorSpace instanceof PDDeviceGray) {
            bim = new BufferedImage(width, height, 10);
            raster = bim.getRaster();
        } else {
            raster = Raster.createBandedRaster(0, width, height, 1, new Point(0, 0));
        }
        byte[] output = raster.getDataBuffer().getData();
        InputStream iis = pdImage.createInputStream();
        try {
            boolean isIndexed = colorSpace instanceof PDIndexed;
            int rowLen = width / 8;
            if (width % 8 > 0) {
                rowLen++;
            }
            if (isIndexed || decode[0] < decode[1]) {
                value0 = 0;
                value1 = -1;
            } else {
                value0 = -1;
                value1 = 0;
            }
            byte[] buff = new byte[rowLen];
            int idx = 0;
            int y = 0;
            while (true) {
                if (y >= height) {
                    break;
                }
                int x = 0;
                int readLen = iis.read(buff);
                for (int r = 0; r < rowLen && r < readLen; r++) {
                    byte b = buff[r];
                    int mask = 128;
                    for (int i = 0; i < 8; i++) {
                        int bit = b & mask;
                        mask >>= 1;
                        int i2 = idx;
                        idx++;
                        output[i2] = bit == 0 ? value0 : value1;
                        x++;
                        if (x == width) {
                            break;
                        }
                    }
                }
                if (readLen == rowLen) {
                    y++;
                } else {
                    LOG.warn("premature EOF, image will be incomplete");
                    break;
                }
            }
            if (bim != null) {
                BufferedImage bufferedImage = bim;
                if (iis != null) {
                    iis.close();
                }
                return bufferedImage;
            }
            BufferedImage rGBImage = colorSpace.toRGBImage(raster);
            if (iis != null) {
                iis.close();
            }
            return rGBImage;
        } catch (Throwable th) {
            if (iis != null) {
                try {
                    iis.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static BufferedImage from8bit(PDImage pdImage, WritableRaster raster) throws IOException {
        byte[][] banks = raster.getDataBuffer().getBankData();
        ByteBuffer source = pdImage.asByteBuffer();
        int width = pdImage.getWidth();
        int height = pdImage.getHeight();
        int numComponents = pdImage.getColorSpace().getNumberOfComponents();
        int max = width * height;
        boolean warnedAboutIndexOutOfBounds = false;
        for (int c = 0; c < numComponents; c++) {
            int sourceOffset = c;
            for (int i = 0; i < max; i++) {
                if (sourceOffset < source.limit()) {
                    banks[c][i] = source.get(sourceOffset);
                    sourceOffset += numComponents;
                } else {
                    if (!warnedAboutIndexOutOfBounds) {
                        LOG.warn("Tried reading: " + sourceOffset + " but only: " + source.limit() + " available (component: " + c + ")");
                        warnedAboutIndexOutOfBounds = true;
                    }
                    banks[c][i] = -1;
                }
            }
        }
        return pdImage.getColorSpace().toRGBImage(raster);
    }

    private static BufferedImage fromAny(PDImage pdImage, WritableRaster raster, COSArray colorKey) throws IOException {
        PDColorSpace colorSpace = pdImage.getColorSpace();
        int numComponents = colorSpace.getNumberOfComponents();
        int width = pdImage.getWidth();
        int height = pdImage.getHeight();
        int bitsPerComponent = pdImage.getBitsPerComponent();
        float[] decode = getDecodeArray(pdImage);
        MemoryCacheImageInputStream memoryCacheImageInputStream = new MemoryCacheImageInputStream(pdImage.createInputStream());
        try {
            float sampleMax = ((float) Math.pow(2.0d, bitsPerComponent)) - 1.0f;
            boolean isIndexed = colorSpace instanceof PDIndexed;
            float[] colorKeyRanges = null;
            BufferedImage colorKeyMask = null;
            if (colorKey != null) {
                if (colorKey.size() >= numComponents * 2) {
                    colorKeyRanges = colorKey.toFloatArray();
                    colorKeyMask = new BufferedImage(width, height, 10);
                } else {
                    LOG.warn("colorKey mask size is " + colorKey.size() + ", should be " + (numComponents * 2) + ", ignored");
                }
            }
            int padding = 0;
            if (((width * numComponents) * bitsPerComponent) % 8 > 0) {
                padding = 8 - (((width * numComponents) * bitsPerComponent) % 8);
            }
            byte[] srcColorValues = new byte[numComponents];
            byte[] alpha = new byte[1];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    boolean isMasked = true;
                    for (int c = 0; c < numComponents; c++) {
                        int value = (int) memoryCacheImageInputStream.readBits(bitsPerComponent);
                        if (colorKeyRanges != null) {
                            isMasked &= ((float) value) >= colorKeyRanges[c * 2] && ((float) value) <= colorKeyRanges[(c * 2) + 1];
                        }
                        float dMin = decode[c * 2];
                        float dMax = decode[(c * 2) + 1];
                        float output = dMin + (value * ((dMax - dMin) / sampleMax));
                        if (isIndexed) {
                            srcColorValues[c] = (byte) Math.round(output);
                        } else {
                            int outputByte = Math.round(((output - Math.min(dMin, dMax)) / Math.abs(dMax - dMin)) * 255.0f);
                            srcColorValues[c] = (byte) outputByte;
                        }
                    }
                    raster.setDataElements(x, y, srcColorValues);
                    if (colorKeyMask != null) {
                        alpha[0] = (byte) (isMasked ? 255 : 0);
                        colorKeyMask.getRaster().setDataElements(x, y, alpha);
                    }
                }
                memoryCacheImageInputStream.readBits(padding);
            }
            BufferedImage rgbImage = colorSpace.toRGBImage(raster);
            if (colorKeyMask != null) {
                BufferedImage bufferedImageApplyColorKeyMask = applyColorKeyMask(rgbImage, colorKeyMask);
                memoryCacheImageInputStream.close();
                return bufferedImageApplyColorKeyMask;
            }
            memoryCacheImageInputStream.close();
            return rgbImage;
        } catch (Throwable th) {
            try {
                memoryCacheImageInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static BufferedImage applyColorKeyMask(BufferedImage image, BufferedImage mask) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage masked = new BufferedImage(width, height, 2);
        WritableRaster src = image.getRaster();
        WritableRaster dest = masked.getRaster();
        WritableRaster alpha = mask.getRaster();
        float[] rgb = new float[3];
        float[] rgba = new float[4];
        float[] alphaPixel = null;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                src.getPixel(x, y, rgb);
                rgba[0] = rgb[0];
                rgba[1] = rgb[1];
                rgba[2] = rgb[2];
                alphaPixel = alpha.getPixel(x, y, alphaPixel);
                rgba[3] = 255.0f - alphaPixel[0];
                dest.setPixel(x, y, rgba);
            }
        }
        return masked;
    }

    private static float[] getDecodeArray(PDImage pdImage) throws IOException {
        COSArray cosDecode = pdImage.getDecode();
        float[] decode = null;
        if (cosDecode != null) {
            int numberOfComponents = pdImage.getColorSpace().getNumberOfComponents();
            if (cosDecode.size() != numberOfComponents * 2) {
                if (pdImage.isStencil() && cosDecode.size() >= 2 && (cosDecode.get(0) instanceof COSNumber) && (cosDecode.get(1) instanceof COSNumber)) {
                    float decode0 = ((COSNumber) cosDecode.get(0)).floatValue();
                    float decode1 = ((COSNumber) cosDecode.get(1)).floatValue();
                    if (decode0 >= 0.0f && decode0 <= 1.0f && decode1 >= 0.0f && decode1 <= 1.0f) {
                        LOG.warn("decode array " + cosDecode + " not compatible with color space, using the first two entries");
                        return new float[]{decode0, decode1};
                    }
                }
                LOG.error("decode array " + cosDecode + " not compatible with color space, using default");
            } else {
                decode = cosDecode.toFloatArray();
            }
        }
        if (decode == null) {
            return pdImage.getColorSpace().getDefaultDecode(pdImage.getBitsPerComponent());
        }
        return decode;
    }
}
