package com.luciad.imageio.webp;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Locale;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import org.sejda.sambox.util.SpecVersionUtils;

/* loaded from: org.sejda.imageio.webp-imageio-0.1.6.jar:com/luciad/imageio/webp/WebPImageReaderSpi.class */
public class WebPImageReaderSpi extends ImageReaderSpi {
    private static final byte[] RIFF = {82, 73, 70, 70};
    private static final byte[] WEBP = {87, 69, 66, 80};
    private static final byte[] VP8_ = {86, 80, 56, 32};
    private static final byte[] VP8L = {86, 80, 56, 76};
    private static final byte[] VP8X = {86, 80, 56, 88};

    public WebPImageReaderSpi() {
        super("Luciad", SpecVersionUtils.V1_0, new String[]{"WebP", "webp"}, new String[]{"webp"}, new String[]{"image/webp"}, WebPReader.class.getName(), new Class[]{ImageInputStream.class}, new String[]{WebPImageWriterSpi.class.getName()}, false, (String) null, (String) null, (String[]) null, (String[]) null, false, (String) null, (String) null, (String[]) null, (String[]) null);
    }

    public ImageReader createReaderInstance(Object extension) throws IOException {
        return new WebPReader(this);
    }

    public boolean canDecodeInput(Object source) throws IOException {
        if (!(source instanceof ImageInputStream)) {
            return false;
        }
        ImageInputStream stream = (ImageInputStream) source;
        byte[] b = new byte[4];
        ByteOrder oldByteOrder = stream.getByteOrder();
        stream.mark();
        stream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        try {
            stream.readFully(b);
            if (!Arrays.equals(b, RIFF)) {
                return false;
            }
            long chunkLength = stream.readUnsignedInt();
            long streamLength = stream.length();
            if (streamLength != -1 && streamLength != chunkLength + 8) {
                stream.setByteOrder(oldByteOrder);
                stream.reset();
                return false;
            }
            stream.readFully(b);
            if (!Arrays.equals(b, WEBP)) {
                stream.setByteOrder(oldByteOrder);
                stream.reset();
                return false;
            }
            stream.readFully(b);
            if (!Arrays.equals(b, VP8_) && !Arrays.equals(b, VP8L)) {
                if (!Arrays.equals(b, VP8X)) {
                    stream.setByteOrder(oldByteOrder);
                    stream.reset();
                    return false;
                }
            }
            stream.setByteOrder(oldByteOrder);
            stream.reset();
            return true;
        } finally {
            stream.setByteOrder(oldByteOrder);
            stream.reset();
        }
    }

    public String getDescription(Locale locale) {
        return "WebP Reader";
    }
}
