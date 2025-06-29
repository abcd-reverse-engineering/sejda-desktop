package org.sejda.sambox.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/filter/Filter.class */
public abstract class Filter {
    private static final Logger LOG = LoggerFactory.getLogger(Filter.class);
    public static final String SYSPROP_DEFLATELEVEL = "org.sejda.sambox.filter.deflatelevel";

    public abstract DecodeResult decode(InputStream inputStream, OutputStream outputStream, COSDictionary cOSDictionary, int i) throws IOException;

    public abstract void encode(InputStream inputStream, OutputStream outputStream, COSDictionary cOSDictionary) throws IOException;

    protected Filter() {
    }

    protected COSDictionary getDecodeParams(COSDictionary dictionary, int index) {
        COSBase filter = dictionary.getDictionaryObject(COSName.F, COSName.FILTER);
        COSBase dp = (COSBase) Optional.ofNullable(dictionary.getDictionaryObject(COSName.DP, COSName.DECODE_PARMS)).orElseGet(COSDictionary::new);
        if ((filter instanceof COSName) && (dp instanceof COSDictionary)) {
            return (COSDictionary) dp;
        }
        if ((filter instanceof COSArray) && (dp instanceof COSArray)) {
            COSArray array = (COSArray) dp;
            if (index < array.size()) {
                COSBase params = (COSBase) Optional.ofNullable(array.getObject(index)).orElseGet(COSDictionary::new);
                if (params instanceof COSDictionary) {
                    return (COSDictionary) params;
                }
                LOG.error("Ignoring invalid DecodeParams. Expected dictionary but found {}", params.getClass().getName());
                return new COSDictionary();
            }
        }
        if (!(filter instanceof COSArray) && !(dp instanceof COSArray)) {
            LOG.error("Ignoring invalid DecodeParams. Expected array or dictionary but found {}", dp.getClass().getName());
        }
        return new COSDictionary();
    }

    protected static ImageReader findImageReader(String formatName, String errorCause) throws MissingImageReaderException {
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(formatName);
        while (readers.hasNext()) {
            ImageReader reader = readers.next();
            if (reader != null && reader.canReadRaster()) {
                return reader;
            }
        }
        throw new MissingImageReaderException("Cannot read " + formatName + " image: " + errorCause);
    }

    public static int getCompressionLevel() throws NumberFormatException {
        int compressionLevel = -1;
        try {
            compressionLevel = Integer.parseInt(System.getProperty(SYSPROP_DEFLATELEVEL, "-1"));
        } catch (NumberFormatException ex) {
            LOG.warn(ex.getMessage(), ex);
        }
        return Math.max(-1, Math.min(9, compressionLevel));
    }
}
