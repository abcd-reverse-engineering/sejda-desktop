package com.luciad.imageio.webp;

import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DirectColorModel;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;
import org.sejda.sambox.util.SpecVersionUtils;

/* loaded from: org.sejda.imageio.webp-imageio-0.1.6.jar:com/luciad/imageio/webp/WebPImageWriterSpi.class */
public class WebPImageWriterSpi extends ImageWriterSpi {
    public WebPImageWriterSpi() {
        super("Luciad", SpecVersionUtils.V1_0, new String[]{"WebP", "webp"}, new String[]{"webp"}, new String[]{"image/webp"}, WebPReader.class.getName(), new Class[]{ImageOutputStream.class}, new String[]{WebPImageReaderSpi.class.getName()}, false, (String) null, (String) null, (String[]) null, (String[]) null, false, (String) null, (String) null, (String[]) null, (String[]) null);
    }

    public boolean canEncodeImage(ImageTypeSpecifier type) {
        ColorModel colorModel = type.getColorModel();
        SampleModel sampleModel = type.getSampleModel();
        int transferType = sampleModel.getTransferType();
        if (colorModel instanceof ComponentColorModel) {
            if (!(sampleModel instanceof ComponentSampleModel)) {
                return false;
            }
            if (transferType != 0 && transferType != 3) {
                return false;
            }
        } else if ((colorModel instanceof DirectColorModel) && (!(sampleModel instanceof SinglePixelPackedSampleModel) || transferType != 3)) {
            return false;
        }
        ColorSpace colorSpace = colorModel.getColorSpace();
        if (!colorSpace.isCS_sRGB()) {
            return false;
        }
        int[] sampleSize = sampleModel.getSampleSize();
        for (int i : sampleSize) {
            if (i > 8) {
                return false;
            }
        }
        return true;
    }

    public ImageWriter createWriterInstance(Object extension) throws IOException {
        return new WebPWriter(this);
    }

    public String getDescription(Locale locale) {
        return "WebP Writer";
    }
}
