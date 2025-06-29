package org.sejda.impl.sambox.pro.component.contentstream.operator.text;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.text.SetFontAndSize;
import org.sejda.sambox.cos.COSBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/contentstream/operator/text/SetFontAndSizeLenient.class */
public class SetFontAndSizeLenient extends SetFontAndSize {
    private static final Logger LOG = LoggerFactory.getLogger(SetFontAndSizeLenient.class);

    @Override // org.sejda.sambox.contentstream.operator.text.SetFontAndSize, org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) {
        try {
            super.process(operator, arguments);
        } catch (IOException | ClassCastException | IllegalArgumentException | IndexOutOfBoundsException | NullPointerException e) {
            LOG.warn("An error occurred processing SetFontAndSize operator: {}", e.getMessage());
        }
    }
}
