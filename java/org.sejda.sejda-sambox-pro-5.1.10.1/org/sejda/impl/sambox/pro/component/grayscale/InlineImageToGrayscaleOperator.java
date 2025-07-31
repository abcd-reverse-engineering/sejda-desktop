package org.sejda.impl.sambox.pro.component.grayscale;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.graphics.image.PDInlineImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/grayscale/InlineImageToGrayscaleOperator.class */
public class InlineImageToGrayscaleOperator extends OperatorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(InlineImageToGrayscaleOperator.class);

    public void process(Operator operator, List<COSBase> operands) {
        if (!GrayscaleUtils.isGray(operator.getImageParameters())) {
            try {
                PDInlineImage pDInlineImage = new PDInlineImage(operator.getImageParameters(), operator.getImageData(), getContext().getResources());
                if (!pDInlineImage.isStencil()) {
                    LOG.debug("Converting inline image");
                    InputStream fis = GrayscaleUtils.toGrayDeflated_PixelByPixel(pDInlineImage.getImage());
                    try {
                        byte[] bytes = IOUtils.toByteArray(fis);
                        operator.setImageData(bytes);
                        IOUtils.closeQuietly(fis);
                        COSDictionary dictionary = operator.getImageParameters().duplicate();
                        dictionary.removeItems(new COSName[]{COSName.BPC, COSName.BITS_PER_COMPONENT, COSName.COLORSPACE, COSName.CS, COSName.FILTER, COSName.F, COSName.LENGTH, COSName.L, COSName.DP});
                        dictionary.setItem(COSName.BPC, COSInteger.get(8L));
                        dictionary.setItem(COSName.CS, COSName.G);
                        dictionary.setItem(COSName.F, COSName.FLATE_DECODE_ABBREVIATION);
                        operator.setImageParameters(dictionary);
                        LOG.trace("Inline image converted to grayscale");
                    } catch (Throwable th) {
                        IOUtils.closeQuietly(fis);
                        throw th;
                    }
                }
            } catch (IOException | RuntimeException ex) {
                LOG.warn("Failed to convert image inline image", ex);
            }
        }
    }

    public String getName() {
        return "BI";
    }
}
