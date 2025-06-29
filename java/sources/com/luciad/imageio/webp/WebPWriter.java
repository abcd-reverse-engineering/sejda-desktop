package com.luciad.imageio.webp;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.SinglePixelPackedSampleModel;
import java.io.IOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

/* loaded from: org.sejda.imageio.webp-imageio-0.1.6.jar:com/luciad/imageio/webp/WebPWriter.class */
class WebPWriter extends ImageWriter {
    WebPWriter(ImageWriterSpi originatingProvider) {
        super(originatingProvider);
    }

    public ImageWriteParam getDefaultWriteParam() {
        return new WebPWriteParam(getLocale());
    }

    public IIOMetadata convertImageMetadata(IIOMetadata inData, ImageTypeSpecifier imageType, ImageWriteParam param) {
        return null;
    }

    public IIOMetadata convertStreamMetadata(IIOMetadata inData, ImageWriteParam param) {
        return null;
    }

    public IIOMetadata getDefaultImageMetadata(ImageTypeSpecifier imageType, ImageWriteParam param) {
        return null;
    }

    public IIOMetadata getDefaultStreamMetadata(ImageWriteParam param) {
        return null;
    }

    public void write(IIOMetadata streamMetadata, IIOImage image, ImageWriteParam param) throws IOException {
        if (param == null) {
            param = getDefaultWriteParam();
        }
        WebPWriteParam writeParam = (WebPWriteParam) param;
        ImageOutputStream output = (ImageOutputStream) getOutput();
        RenderedImage ri = image.getRenderedImage();
        byte[] encodedData = encode(writeParam.getEncoderOptions(), ri);
        output.write(encodedData);
    }

    private static byte[] encode(WebPEncoderOptions aOptions, RenderedImage aImage) throws IOException {
        byte[] pixels;
        if (aOptions == null) {
            throw new NullPointerException("Encoder options may not be null");
        }
        if (aImage == null) {
            throw new NullPointerException("Image may not be null");
        }
        ThreadLocal<WebPEncoderOptions> encoderThreadLocal = new ThreadLocal<>();
        try {
            encoderThreadLocal.set(aOptions);
            boolean encodeAlpha = hasTranslucency(aImage);
            if (encodeAlpha) {
                byte[] rgbaData = getRGBA(aImage);
                pixels = WebP.encodeRGBA(aOptions, rgbaData, aImage.getWidth(), aImage.getHeight(), aImage.getWidth() * 4);
            } else {
                byte[] rgbData = getRGB(aImage);
                pixels = WebP.encodeRGB(aOptions, rgbData, aImage.getWidth(), aImage.getHeight(), aImage.getWidth() * 3);
            }
            return pixels;
        } finally {
            encoderThreadLocal.remove();
        }
    }

    private static boolean hasTranslucency(RenderedImage aRi) {
        return aRi.getColorModel().hasAlpha();
    }

    private static int getShift(int aMask) {
        int shift = 0;
        while (((aMask >> shift) & 1) == 0) {
            shift++;
        }
        return shift;
    }

    private static byte[] getRGB(RenderedImage aRi) throws IOException {
        int width = aRi.getWidth();
        int height = aRi.getHeight();
        DirectColorModel colorModel = aRi.getColorModel();
        if (colorModel instanceof ComponentColorModel) {
            ComponentSampleModel sampleModel = aRi.getSampleModel();
            int type = sampleModel.getTransferType();
            if (type == 0) {
                return extractComponentRGBByte(width, height, sampleModel, aRi.getData().getDataBuffer());
            }
            if (type == 3) {
                return extractComponentRGBInt(width, height, sampleModel, aRi.getData().getDataBuffer());
            }
            throw new IOException("Incompatible image: " + aRi);
        }
        if (colorModel instanceof DirectColorModel) {
            SinglePixelPackedSampleModel sampleModel2 = aRi.getSampleModel();
            if (sampleModel2.getTransferType() == 3) {
                return extractDirectRGBInt(width, height, colorModel, sampleModel2, aRi.getData().getDataBuffer());
            }
            throw new IOException("Incompatible image: " + aRi);
        }
        BufferedImage i = new BufferedImage(aRi.getWidth(), aRi.getHeight(), 1);
        Graphics2D g = i.createGraphics();
        g.drawRenderedImage(aRi, new AffineTransform());
        g.dispose();
        return getRGB(i);
    }

    private static byte[] extractDirectRGBInt(int aWidth, int aHeight, DirectColorModel aColorModel, SinglePixelPackedSampleModel aSampleModel, DataBufferInt aDataBuffer) {
        byte[] out = new byte[aWidth * aHeight * 3];
        int rMask = aColorModel.getRedMask();
        int gMask = aColorModel.getGreenMask();
        int bMask = aColorModel.getBlueMask();
        int rShift = getShift(rMask);
        int gShift = getShift(gMask);
        int bShift = getShift(bMask);
        int[] bank = aDataBuffer.getBankData()[0];
        int scanlineStride = aSampleModel.getScanlineStride();
        int scanIx = 0;
        int b = 0;
        for (int y = 0; y < aHeight; y++) {
            int pixIx = scanIx;
            int x = 0;
            while (x < aWidth) {
                int i = pixIx;
                pixIx++;
                int pixel = bank[i];
                out[b] = (byte) ((pixel & rMask) >>> rShift);
                out[b + 1] = (byte) ((pixel & gMask) >>> gShift);
                out[b + 2] = (byte) ((pixel & bMask) >>> bShift);
                x++;
                b += 3;
            }
            scanIx += scanlineStride;
        }
        return out;
    }

    private static byte[] extractComponentRGBInt(int aWidth, int aHeight, ComponentSampleModel aSampleModel, DataBufferInt aDataBuffer) {
        byte[] out = new byte[aWidth * aHeight * 3];
        int[] bankIndices = aSampleModel.getBankIndices();
        int[] rBank = aDataBuffer.getBankData()[bankIndices[0]];
        int[] gBank = aDataBuffer.getBankData()[bankIndices[1]];
        int[] bBank = aDataBuffer.getBankData()[bankIndices[2]];
        int[] bankOffsets = aSampleModel.getBandOffsets();
        int rScanIx = bankOffsets[0];
        int gScanIx = bankOffsets[1];
        int bScanIx = bankOffsets[2];
        int pixelStride = aSampleModel.getPixelStride();
        int scanlineStride = aSampleModel.getScanlineStride();
        int b = 0;
        for (int y = 0; y < aHeight; y++) {
            int rPixIx = rScanIx;
            int gPixIx = gScanIx;
            int bPixIx = bScanIx;
            int x = 0;
            while (x < aWidth) {
                out[b] = (byte) rBank[rPixIx];
                rPixIx += pixelStride;
                out[b + 1] = (byte) gBank[gPixIx];
                gPixIx += pixelStride;
                out[b + 2] = (byte) bBank[bPixIx];
                bPixIx += pixelStride;
                x++;
                b += 3;
            }
            rScanIx += scanlineStride;
            gScanIx += scanlineStride;
            bScanIx += scanlineStride;
        }
        return out;
    }

    private static byte[] extractComponentRGBByte(int aWidth, int aHeight, ComponentSampleModel aSampleModel, DataBufferByte aDataBuffer) {
        byte[] out = new byte[aWidth * aHeight * 3];
        int[] bankIndices = aSampleModel.getBankIndices();
        byte[] rBank = aDataBuffer.getBankData()[bankIndices[0]];
        byte[] gBank = aDataBuffer.getBankData()[bankIndices[1]];
        byte[] bBank = aDataBuffer.getBankData()[bankIndices[2]];
        int[] bankOffsets = aSampleModel.getBandOffsets();
        int rScanIx = bankOffsets[0];
        int gScanIx = bankOffsets[1];
        int bScanIx = bankOffsets[2];
        int pixelStride = aSampleModel.getPixelStride();
        int scanlineStride = aSampleModel.getScanlineStride();
        int b = 0;
        for (int y = 0; y < aHeight; y++) {
            int rPixIx = rScanIx;
            int gPixIx = gScanIx;
            int bPixIx = bScanIx;
            int x = 0;
            while (x < aWidth) {
                out[b] = rBank[rPixIx];
                rPixIx += pixelStride;
                out[b + 1] = gBank[gPixIx];
                gPixIx += pixelStride;
                out[b + 2] = bBank[bPixIx];
                bPixIx += pixelStride;
                x++;
                b += 3;
            }
            rScanIx += scanlineStride;
            gScanIx += scanlineStride;
            bScanIx += scanlineStride;
        }
        return out;
    }

    private static byte[] getRGBA(RenderedImage aRi) throws IOException {
        int width = aRi.getWidth();
        int height = aRi.getHeight();
        DirectColorModel colorModel = aRi.getColorModel();
        if (colorModel instanceof ComponentColorModel) {
            ComponentSampleModel sampleModel = aRi.getSampleModel();
            int type = sampleModel.getTransferType();
            if (type == 0) {
                return extractComponentRGBAByte(width, height, sampleModel, aRi.getData().getDataBuffer());
            }
            if (type == 3) {
                return extractComponentRGBAInt(width, height, sampleModel, aRi.getData().getDataBuffer());
            }
            throw new IOException("Incompatible image: " + aRi);
        }
        if (colorModel instanceof DirectColorModel) {
            SinglePixelPackedSampleModel sampleModel2 = aRi.getSampleModel();
            if (sampleModel2.getTransferType() == 3) {
                return extractDirectRGBAInt(width, height, colorModel, sampleModel2, aRi.getData().getDataBuffer());
            }
            throw new IOException("Incompatible image: " + aRi);
        }
        BufferedImage i = new BufferedImage(aRi.getWidth(), aRi.getHeight(), 2);
        Graphics2D g = i.createGraphics();
        g.drawRenderedImage(aRi, new AffineTransform());
        g.dispose();
        return getRGBA(i);
    }

    private static byte[] extractDirectRGBAInt(int aWidth, int aHeight, DirectColorModel aColorModel, SinglePixelPackedSampleModel aSampleModel, DataBufferInt aDataBuffer) {
        byte[] out = new byte[aWidth * aHeight * 4];
        int rMask = aColorModel.getRedMask();
        int gMask = aColorModel.getGreenMask();
        int bMask = aColorModel.getBlueMask();
        int aMask = aColorModel.getAlphaMask();
        int rShift = getShift(rMask);
        int gShift = getShift(gMask);
        int bShift = getShift(bMask);
        int aShift = getShift(aMask);
        int[] bank = aDataBuffer.getBankData()[0];
        int scanlineStride = aSampleModel.getScanlineStride();
        int scanIx = 0;
        int b = 0;
        for (int y = 0; y < aHeight; y++) {
            int pixIx = scanIx;
            int x = 0;
            while (x < aWidth) {
                int i = pixIx;
                pixIx++;
                int pixel = bank[i];
                out[b] = (byte) ((pixel & rMask) >>> rShift);
                out[b + 1] = (byte) ((pixel & gMask) >>> gShift);
                out[b + 2] = (byte) ((pixel & bMask) >>> bShift);
                out[b + 3] = (byte) ((pixel & aMask) >>> aShift);
                x++;
                b += 4;
            }
            scanIx += scanlineStride;
        }
        return out;
    }

    private static byte[] extractComponentRGBAInt(int aWidth, int aHeight, ComponentSampleModel aSampleModel, DataBufferInt aDataBuffer) {
        byte[] out = new byte[aWidth * aHeight * 4];
        int[] bankIndices = aSampleModel.getBankIndices();
        int[] rBank = aDataBuffer.getBankData()[bankIndices[0]];
        int[] gBank = aDataBuffer.getBankData()[bankIndices[1]];
        int[] bBank = aDataBuffer.getBankData()[bankIndices[2]];
        int[] aBank = aDataBuffer.getBankData()[bankIndices[3]];
        int[] bankOffsets = aSampleModel.getBandOffsets();
        int rScanIx = bankOffsets[0];
        int gScanIx = bankOffsets[1];
        int bScanIx = bankOffsets[2];
        int aScanIx = bankOffsets[3];
        int pixelStride = aSampleModel.getPixelStride();
        int scanlineStride = aSampleModel.getScanlineStride();
        int b = 0;
        for (int y = 0; y < aHeight; y++) {
            int rPixIx = rScanIx;
            int gPixIx = gScanIx;
            int bPixIx = bScanIx;
            int aPixIx = aScanIx;
            int x = 0;
            while (x < aWidth) {
                out[b] = (byte) rBank[rPixIx];
                rPixIx += pixelStride;
                out[b + 1] = (byte) gBank[gPixIx];
                gPixIx += pixelStride;
                out[b + 2] = (byte) bBank[bPixIx];
                bPixIx += pixelStride;
                out[b + 3] = (byte) aBank[aPixIx];
                aPixIx += pixelStride;
                x++;
                b += 4;
            }
            rScanIx += scanlineStride;
            gScanIx += scanlineStride;
            bScanIx += scanlineStride;
            aScanIx += scanlineStride;
        }
        return out;
    }

    private static byte[] extractComponentRGBAByte(int aWidth, int aHeight, ComponentSampleModel aSampleModel, DataBufferByte aDataBuffer) {
        byte[] out = new byte[aWidth * aHeight * 4];
        int[] bankIndices = aSampleModel.getBankIndices();
        byte[] rBank = aDataBuffer.getBankData()[bankIndices[0]];
        byte[] gBank = aDataBuffer.getBankData()[bankIndices[1]];
        byte[] bBank = aDataBuffer.getBankData()[bankIndices[2]];
        byte[] aBank = aDataBuffer.getBankData()[bankIndices[3]];
        int[] bankOffsets = aSampleModel.getBandOffsets();
        int rScanIx = bankOffsets[0];
        int gScanIx = bankOffsets[1];
        int bScanIx = bankOffsets[2];
        int aScanIx = bankOffsets[3];
        int pixelStride = aSampleModel.getPixelStride();
        int scanlineStride = aSampleModel.getScanlineStride();
        int b = 0;
        for (int y = 0; y < aHeight; y++) {
            int rPixIx = rScanIx;
            int gPixIx = gScanIx;
            int bPixIx = bScanIx;
            int aPixIx = aScanIx;
            int x = 0;
            while (x < aWidth) {
                out[b] = rBank[rPixIx];
                rPixIx += pixelStride;
                out[b + 1] = gBank[gPixIx];
                gPixIx += pixelStride;
                out[b + 2] = bBank[bPixIx];
                bPixIx += pixelStride;
                out[b + 3] = aBank[aPixIx];
                aPixIx += pixelStride;
                x++;
                b += 4;
            }
            rScanIx += scanlineStride;
            gScanIx += scanlineStride;
            bScanIx += scanlineStride;
            aScanIx += scanlineStride;
        }
        return out;
    }
}
