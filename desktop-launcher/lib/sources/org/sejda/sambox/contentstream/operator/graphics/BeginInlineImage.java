package org.sejda.sambox.contentstream.operator.graphics;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.pdmodel.graphics.image.PDImage;
import org.sejda.sambox.pdmodel.graphics.image.PDInlineImage;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/graphics/BeginInlineImage.class */
public final class BeginInlineImage extends GraphicsOperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> operands) throws IOException {
        if (Objects.nonNull(operator.getImageData()) && operator.getImageData().length > 0) {
            PDImage image = new PDInlineImage(operator.getImageParameters(), operator.getImageData(), getContext().getResources());
            getContext().drawImage(image);
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.BEGIN_INLINE_IMAGE;
    }
}
