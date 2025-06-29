package org.sejda.impl.sambox.pro.component.grayscale;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.graphics.image.PDImage;
import org.sejda.sambox.pdmodel.graphics.image.PDInlineImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/grayscale/InlineImageToGrayscaleOperator.class */
public class InlineImageToGrayscaleOperator extends OperatorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(InlineImageToGrayscaleOperator.class);

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> operands) {
        if (!GrayscaleUtils.isGray(operator.getImageParameters())) {
            try {
                PDImage image = new PDInlineImage(operator.getImageParameters(), operator.getImageData(), getContext().getResources());
                if (!image.isStencil()) {
                    LOG.debug("Converting inline image");
                    InputStream fis = GrayscaleUtils.toGrayDeflated_PixelByPixel(image.getImage());
                    try {
                        byte[] bytes = IOUtils.toByteArray(fis);
                        operator.setImageData(bytes);
                        IOUtils.closeQuietly(fis);
                        COSDictionary dictionary = operator.getImageParameters().duplicate();
                        dictionary.removeItems(COSName.BPC, COSName.BITS_PER_COMPONENT, COSName.COLORSPACE, COSName.CS, COSName.FILTER, COSName.F, COSName.LENGTH, COSName.L, COSName.DP);
                        dictionary.setItem(COSName.BPC, (COSBase) COSInteger.get(8L));
                        dictionary.setItem(COSName.CS, (COSBase) COSName.G);
                        dictionary.setItem(COSName.F, (COSBase) COSName.FLATE_DECODE_ABBREVIATION);
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

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.BEGIN_INLINE_IMAGE;
    }
}
