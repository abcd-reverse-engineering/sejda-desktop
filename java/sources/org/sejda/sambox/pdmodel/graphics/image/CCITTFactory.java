package org.sejda.sambox.pdmodel.graphics.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.filter.Filter;
import org.sejda.sambox.filter.FilterFactory;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/image/CCITTFactory.class */
public final class CCITTFactory {
    private CCITTFactory() {
    }

    public static PDImageXObject createFromImage(BufferedImage image) throws IOException {
        if (image.getType() != 12 && image.getColorModel().getPixelSize() != 1) {
            throw new IllegalArgumentException("Only 1-bit b/w images supported");
        }
        int height = image.getHeight();
        int width = image.getWidth();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MemoryCacheImageOutputStream mcios = new MemoryCacheImageOutputStream(bos);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                try {
                    mcios.writeBits((image.getRGB(x, y) & 1) ^ (-1), 1);
                } catch (Throwable th) {
                    try {
                        mcios.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            int bitOffset = mcios.getBitOffset();
            if (bitOffset != 0) {
                mcios.writeBits(0L, 8 - bitOffset);
            }
        }
        mcios.flush();
        mcios.close();
        return prepareImageXObject(bos.toByteArray(), width, height, PDDeviceGray.INSTANCE);
    }

    private static PDImageXObject prepareImageXObject(byte[] byteArray, int width, int height, PDColorSpace initColorSpace) throws IOException {
        FastByteArrayOutputStream baos = new FastByteArrayOutputStream();
        Filter filter = FilterFactory.INSTANCE.getFilter(COSName.CCITTFAX_DECODE);
        COSDictionary dict = new COSDictionary();
        dict.setInt(COSName.COLUMNS, width);
        dict.setInt(COSName.ROWS, height);
        filter.encode(new ByteArrayInputStream(byteArray), baos, dict);
        ByteArrayInputStream encodedByteStream = new ByteArrayInputStream(baos.toByteArray());
        PDImageXObject image = new PDImageXObject(encodedByteStream, COSName.CCITTFAX_DECODE, width, height, 1, initColorSpace);
        dict.setInt(COSName.K, -1);
        image.getCOSObject().setItem(COSName.DECODE_PARMS, (COSBase) dict);
        return image;
    }

    public static PDImageXObject createFromFile(File file) throws IOException {
        return createFromFile(file, 0);
    }

    public static PDImageXObject createFromFile(File file, int number) throws IOException {
        SeekableSource source = SeekableSources.seekableSourceFrom(file);
        try {
            PDImageXObject pDImageXObjectCreateFromRandomAccessImpl = createFromRandomAccessImpl(source, number);
            if (source != null) {
                source.close();
            }
            return pDImageXObjectCreateFromRandomAccessImpl;
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
        return createFromRandomAccessImpl(source, 0);
    }

    public static PDImageXObject createFromSeekableSource(SeekableSource source, int number) throws IOException {
        return createFromRandomAccessImpl(source, number);
    }

    private static PDImageXObject createFromRandomAccessImpl(SeekableSource source, int number) throws IOException {
        COSDictionary decodeParms = new COSDictionary();
        SeekableSource tiffView = extractFromTiff(source, decodeParms, number);
        if (tiffView != null) {
            PDImageXObject pdImage = new PDImageXObject(tiffView.asInputStream(), COSName.CCITTFAX_DECODE, decodeParms.getInt(COSName.COLUMNS), decodeParms.getInt(COSName.ROWS), 1, PDDeviceGray.INSTANCE);
            COSDictionary dict = pdImage.getCOSObject();
            dict.setItem(COSName.DECODE_PARMS, (COSBase) decodeParms);
            return pdImage;
        }
        return null;
    }

    private static SeekableSource extractFromTiff(SeekableSource source, COSDictionary params, int number) throws IOException {
        int val;
        source.position(0L);
        char endianess = (char) source.read();
        if (((char) source.read()) != endianess) {
            throw new IOException("Not a valid tiff file");
        }
        if (endianess != 'M' && endianess != 'I') {
            throw new IOException("Not a valid tiff file");
        }
        int magicNumber = readshort(endianess, source);
        if (magicNumber != 42) {
            throw new IOException("Not a valid tiff file");
        }
        long address = readlong(endianess, source);
        source.position(address);
        for (int i = 0; i < number; i++) {
            int numtags = readshort(endianess, source);
            if (numtags > 50) {
                throw new IOException("Not a valid tiff file");
            }
            source.position(address + 2 + (numtags * 12));
            address = readlong(endianess, source);
            if (address == 0) {
                return null;
            }
            source.position(address);
        }
        int numtags2 = readshort(endianess, source);
        if (numtags2 > 50) {
            throw new IOException("Not a valid tiff file");
        }
        int k = -1000;
        int dataoffset = 0;
        int datalength = 0;
        for (int i2 = 0; i2 < numtags2; i2++) {
            int tag = readshort(endianess, source);
            int type = readshort(endianess, source);
            int count = readlong(endianess, source);
            switch (type) {
                case 1:
                    val = source.read();
                    source.read();
                    source.read();
                    source.read();
                    break;
                case 3:
                    val = readshort(endianess, source);
                    source.read();
                    source.read();
                    break;
                default:
                    val = readlong(endianess, source);
                    break;
            }
            switch (tag) {
                case PDAnnotation.FLAG_TOGGLE_NO_VIEW /* 256 */:
                    params.setInt(COSName.COLUMNS, val);
                    break;
                case 257:
                    params.setInt(COSName.ROWS, val);
                    break;
                case 259:
                    if (val == 4) {
                        k = -1;
                    }
                    if (val == 3) {
                        k = 0;
                        break;
                    } else {
                        break;
                    }
                case 262:
                    if (val == 1) {
                        params.setBoolean(COSName.BLACK_IS_1, true);
                        break;
                    } else {
                        break;
                    }
                case 266:
                    if (val != 1) {
                        throw new UnsupportedTiffImageException("FillOrder " + val + " is not supported");
                    }
                    break;
                case 273:
                    if (count == 1) {
                        dataoffset = val;
                        break;
                    } else {
                        break;
                    }
                case 274:
                    if (val != 1) {
                        throw new UnsupportedTiffImageException("Orientation " + val + " is not supported");
                    }
                    break;
                case 279:
                    if (count == 1) {
                        datalength = val;
                        break;
                    } else {
                        break;
                    }
                case 292:
                    if ((val & 1) != 0) {
                        k = 50;
                    }
                    if ((val & 4) != 0) {
                        throw new UnsupportedTiffImageException("CCITT Group 3 'uncompressed mode' is not supported");
                    }
                    if ((val & 2) != 0) {
                        throw new UnsupportedTiffImageException("CCITT Group 3 'fill bits before EOL' is not supported");
                    }
                    break;
                case 324:
                    if (count == 1) {
                        dataoffset = val;
                        break;
                    } else {
                        break;
                    }
                case 325:
                    if (count == 1) {
                        datalength = val;
                        break;
                    } else {
                        break;
                    }
            }
        }
        if (k == -1000) {
            throw new UnsupportedTiffImageException("First image in tiff is not CCITT T4 or T6 compressed");
        }
        if (dataoffset == 0) {
            throw new UnsupportedTiffImageException("First image in tiff is not a single tile/strip");
        }
        params.setInt(COSName.K, k);
        source.position(dataoffset);
        return source.view(dataoffset, datalength);
    }

    private static int readshort(char endianess, SeekableSource source) throws IOException {
        if (endianess == 'I') {
            return source.read() | (source.read() << 8);
        }
        return (source.read() << 8) | source.read();
    }

    private static int readlong(char endianess, SeekableSource source) throws IOException {
        if (endianess == 'I') {
            return source.read() | (source.read() << 8) | (source.read() << 16) | (source.read() << 24);
        }
        return (source.read() << 24) | (source.read() << 16) | (source.read() << 8) | source.read();
    }
}
