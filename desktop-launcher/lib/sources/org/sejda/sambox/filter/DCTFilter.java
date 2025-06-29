package org.sejda.sambox.filter;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.sejda.commons.util.IOUtils;
import org.sejda.sambox.cos.COSDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/filter/DCTFilter.class */
final class DCTFilter extends Filter {
    private static final Logger LOG = LoggerFactory.getLogger(DCTFilter.class);
    private static final int POS_TRANSFORM = 11;
    private static final String ADOBE = "Adobe";
    private static XPathExpression xPathExpression;

    DCTFilter() {
    }

    static {
        try {
            xPathExpression = XPathFactory.newInstance().newXPath().compile("Chroma/ColorSpaceType/@name");
        } catch (XPathExpressionException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    /* JADX WARN: Finally extract failed */
    @Override // org.sejda.sambox.filter.Filter
    public DecodeResult decode(InputStream encoded, OutputStream decoded, COSDictionary parameters, int index) throws IOException {
        Raster raster;
        Integer transform;
        ImageReader reader = findImageReader("JPEG", "a suitable JAI I/O image filter is not installed");
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(encoded);
            try {
                if (iis.read() != 10) {
                    iis.seek(0L);
                }
                reader.setInput(iis);
                String numChannels = getNumChannels(reader);
                boolean previousUseCacheValue = ImageIO.getUseCache();
                ImageIO.setUseCache(false);
                try {
                    if ("3".equals(numChannels) || numChannels.isEmpty()) {
                        try {
                            BufferedImage image = reader.read(0);
                            raster = image.getRaster();
                        } catch (IIOException e) {
                            raster = reader.readRaster(0, (ImageReadParam) null);
                        }
                    } else {
                        raster = reader.readRaster(0, (ImageReadParam) null);
                    }
                    LOG.debug("Raster size is {}x{}", Integer.valueOf(raster.getWidth()), Integer.valueOf(raster.getHeight()));
                    if (raster.getNumBands() == 4) {
                        try {
                            transform = getAdobeTransform(reader.getImageMetadata(0));
                        } catch (IIOException | NegativeArraySizeException e2) {
                            transform = Integer.valueOf(getAdobeTransformByBruteForce(iis));
                        }
                        int colorTransform = transform != null ? transform.intValue() : 0;
                        switch (colorTransform) {
                            case 0:
                                break;
                            case 1:
                                raster = fromYCbCrtoCMYK(raster);
                                break;
                            case 2:
                                raster = fromYCCKtoCMYK(raster);
                                break;
                            default:
                                throw new IllegalArgumentException("Unknown colorTransform");
                        }
                    } else if (raster.getNumBands() == 3) {
                        raster = fromBGRtoRGB(raster);
                    }
                    DataBufferByte dataBuffer = raster.getDataBuffer();
                    byte[] bytes = dataBuffer.getData();
                    if (bytes.length > 100000000) {
                        File tmpFile = Files.createTempFile("rasterdatabuffer", ".tmp", new FileAttribute[0]).toFile();
                        OutputStream fout = new BufferedOutputStream(new FileOutputStream(tmpFile));
                        try {
                            fout.write(bytes);
                            IOUtils.closeQuietly(fout);
                            FileInputStream fin = new FileInputStream(tmpFile);
                            try {
                                IOUtils.copy(fin, decoded);
                                IOUtils.closeQuietly(fin);
                                tmpFile.delete();
                            } catch (Throwable th) {
                                IOUtils.closeQuietly(fin);
                                tmpFile.delete();
                                throw th;
                            }
                        } catch (Throwable th2) {
                            IOUtils.closeQuietly(fout);
                            throw th2;
                        }
                    } else {
                        decoded.write(bytes);
                    }
                    ImageIO.setUseCache(previousUseCacheValue);
                    if (iis != null) {
                        iis.close();
                    }
                    return new DecodeResult(parameters);
                } catch (Throwable th3) {
                    ImageIO.setUseCache(previousUseCacheValue);
                    throw th3;
                }
            } finally {
            }
        } finally {
            reader.dispose();
        }
    }

    private Integer getAdobeTransform(IIOMetadata metadata) throws XPathExpressionException {
        Element tree = (Element) metadata.getAsTree("javax_imageio_jpeg_image_1.0");
        Element markerSequence = (Element) tree.getElementsByTagName("markerSequence").item(0);
        NodeList app14AdobeNodeList = markerSequence.getElementsByTagName("app14Adobe");
        if (app14AdobeNodeList != null && app14AdobeNodeList.getLength() > 0) {
            Element adobe = (Element) app14AdobeNodeList.item(0);
            return Integer.valueOf(Integer.parseInt(adobe.getAttribute("transform")));
        }
        try {
            String value = xPathExpression.evaluate(metadata.getAsTree("javax_imageio_1.0"));
            if ("YCbCr".equals(value)) {
                return 1;
            }
            if ("YCCK".equals(value)) {
                return 2;
            }
            return 0;
        } catch (XPathExpressionException e) {
            return 0;
        }
    }

    private int getAdobeTransformByBruteForce(ImageInputStream iis) throws IOException {
        int a = 0;
        iis.seek(0L);
        while (true) {
            int by = iis.read();
            if (by != -1) {
                if (ADOBE.charAt(a) == by) {
                    a++;
                    if (a != ADOBE.length()) {
                        continue;
                    } else {
                        a = 0;
                        long afterAdobePos = iis.getStreamPosition();
                        iis.seek(afterAdobePos - 9);
                        int tag = iis.readUnsignedShort();
                        if (tag != 65518) {
                            iis.seek(afterAdobePos);
                        } else {
                            int len = iis.readUnsignedShort();
                            if (len >= 12) {
                                byte[] app14 = new byte[Math.max(len, 12)];
                                if (iis.read(app14) >= 12) {
                                    return app14[POS_TRANSFORM];
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                } else {
                    a = 0;
                }
            } else {
                return 0;
            }
        }
    }

    private WritableRaster fromYCCKtoCMYK(Raster raster) {
        WritableRaster writableRaster = raster.createCompatibleWritableRaster();
        int[] value = new int[4];
        int height = raster.getHeight();
        for (int y = 0; y < height; y++) {
            int width = raster.getWidth();
            for (int x = 0; x < width; x++) {
                raster.getPixel(x, y, value);
                float Y = value[0];
                float Cb = value[1];
                float Cr = value[2];
                float K = value[3];
                int r = clamp((Y + (1.402f * Cr)) - 179.456f);
                int g = clamp(((Y - (0.34414f * Cb)) - (0.71414f * Cr)) + 135.45984f);
                int b = clamp((Y + (1.772f * Cb)) - 226.816f);
                int cyan = 255 - r;
                int magenta = 255 - g;
                int yellow = 255 - b;
                value[0] = cyan;
                value[1] = magenta;
                value[2] = yellow;
                value[3] = (int) K;
                writableRaster.setPixel(x, y, value);
            }
        }
        return writableRaster;
    }

    private WritableRaster fromYCbCrtoCMYK(Raster raster) {
        WritableRaster writableRaster = raster.createCompatibleWritableRaster();
        int[] value = new int[4];
        int height = raster.getHeight();
        for (int y = 0; y < height; y++) {
            int width = raster.getWidth();
            for (int x = 0; x < width; x++) {
                raster.getPixel(x, y, value);
                float Y = value[0];
                float Cb = value[1];
                float Cr = value[2];
                float K = value[3];
                int r = clamp((1.164f * (Y - 16.0f)) + (1.596f * (Cr - 128.0f)));
                int g = clamp((1.164f * (Y - 16.0f)) + ((-0.392f) * (Cb - 128.0f)) + ((-0.813f) * (Cr - 128.0f)));
                int b = clamp((1.164f * (Y - 16.0f)) + (2.017f * (Cb - 128.0f)));
                int cyan = 255 - r;
                int magenta = 255 - g;
                int yellow = 255 - b;
                value[0] = cyan;
                value[1] = magenta;
                value[2] = yellow;
                value[3] = (int) K;
                writableRaster.setPixel(x, y, value);
            }
        }
        return writableRaster;
    }

    private WritableRaster fromBGRtoRGB(Raster raster) {
        WritableRaster writableRaster = raster.createCompatibleWritableRaster();
        int width = raster.getWidth();
        int height = raster.getHeight();
        int w3 = width * 3;
        int[] tab = new int[w3];
        for (int y = 0; y < height; y++) {
            raster.getPixels(0, y, width, 1, tab);
            for (int off = 0; off < w3; off += 3) {
                int tmp = tab[off];
                tab[off] = tab[off + 2];
                tab[off + 2] = tmp;
            }
            writableRaster.setPixels(0, y, width, 1, tab);
        }
        return writableRaster;
    }

    private String getNumChannels(ImageReader reader) {
        try {
            IIOMetadata imageMetadata = reader.getImageMetadata(0);
            if (imageMetadata == null) {
                return "";
            }
            IIOMetadataNode metaTree = imageMetadata.getAsTree("javax_imageio_1.0");
            Element numChannelsItem = (Element) metaTree.getElementsByTagName("NumChannels").item(0);
            if (numChannelsItem == null) {
                return "";
            }
            return numChannelsItem.getAttribute("value");
        } catch (IOException | ClassCastException | NegativeArraySizeException e) {
            return "";
        }
    }

    private int clamp(float value) {
        return (int) (value < 0.0f ? 0.0f : value > 255.0f ? 255.0f : value);
    }

    @Override // org.sejda.sambox.filter.Filter
    public void encode(InputStream input, OutputStream encoded, COSDictionary parameters) {
        throw new UnsupportedOperationException("DCTFilter encoding not implemented, use the JPEGFactory methods instead");
    }
}
