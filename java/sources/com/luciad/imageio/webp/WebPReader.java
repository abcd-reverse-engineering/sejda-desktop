package com.luciad.imageio.webp;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

/* loaded from: org.sejda.imageio.webp-imageio-0.1.6.jar:com/luciad/imageio/webp/WebPReader.class */
class WebPReader extends ImageReader {
    private byte[] fData;
    private int fWidth;
    private int fHeight;

    WebPReader(ImageReaderSpi originatingProvider) {
        super(originatingProvider);
    }

    public void setInput(Object input, boolean seekForwardOnly, boolean ignoreMetadata) {
        super.setInput(input, seekForwardOnly, ignoreMetadata);
        this.fData = null;
        this.fWidth = -1;
        this.fHeight = -1;
    }

    public int getNumImages(boolean allowSearch) throws IOException {
        return 1;
    }

    private void readHeader() throws IOException {
        if (this.fWidth != -1 && this.fHeight != -1) {
            return;
        }
        readData();
        int[] info = WebP.getInfo(this.fData, 0, this.fData.length);
        this.fWidth = info[0];
        this.fHeight = info[1];
    }

    private void readData() throws IOException {
        byte[] data;
        if (this.fData != null) {
            return;
        }
        ImageInputStream input = (ImageInputStream) getInput();
        long length = input.length();
        if (length > 2147483647L) {
            throw new IOException("Cannot read image of size " + length);
        }
        if (input.getStreamPosition() != 0) {
            if (isSeekForwardOnly()) {
                throw new IOException();
            }
            input.seek(0L);
        }
        if (length > 0) {
            data = new byte[(int) length];
            input.readFully(data);
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            while (true) {
                int bytesRead = input.read(buffer);
                if (bytesRead == -1) {
                    break;
                } else {
                    out.write(buffer, 0, bytesRead);
                }
            }
            out.close();
            data = out.toByteArray();
        }
        this.fData = data;
    }

    private void checkIndex(int imageIndex) {
        if (imageIndex != 0) {
            throw new IndexOutOfBoundsException("Invalid image index: " + imageIndex);
        }
    }

    public int getWidth(int imageIndex) throws IOException {
        checkIndex(imageIndex);
        readHeader();
        return this.fWidth;
    }

    public int getHeight(int imageIndex) throws IOException {
        checkIndex(imageIndex);
        readHeader();
        return this.fHeight;
    }

    public IIOMetadata getStreamMetadata() throws IOException {
        return null;
    }

    public IIOMetadata getImageMetadata(int imageIndex) throws IOException {
        return null;
    }

    public Iterator<ImageTypeSpecifier> getImageTypes(int imageIndex) throws IOException {
        return Collections.singletonList(ImageTypeSpecifier.createFromBufferedImageType(2)).iterator();
    }

    public ImageReadParam getDefaultReadParam() {
        return new WebPReadParam();
    }

    public BufferedImage read(int imageIndex, ImageReadParam param) throws IOException {
        DirectColorModel directColorModel;
        checkIndex(imageIndex);
        readData();
        readHeader();
        WebPReadParam readParam = param != null ? (WebPReadParam) param : new WebPReadParam();
        int[] outParams = new int[4];
        int[] pixels = WebP.decode(readParam.getDecoderOptions(), this.fData, 0, this.fData.length, outParams);
        int width = outParams[1];
        int height = outParams[2];
        boolean alpha = outParams[3] != 0;
        if (alpha) {
            directColorModel = new DirectColorModel(32, 16711680, 65280, 255, -16777216);
        } else {
            directColorModel = new DirectColorModel(24, 16711680, 65280, 255, 0);
        }
        SampleModel sampleModel = directColorModel.createCompatibleSampleModel(width, height);
        DataBufferInt db = new DataBufferInt(pixels, width * height);
        WritableRaster raster = WritableRaster.createWritableRaster(sampleModel, db, (Point) null);
        return new BufferedImage(directColorModel, raster, false, new Hashtable());
    }
}
