package org.sejda.sambox.contentstream.operator.text;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/text/SetFontAndSize.class */
public class SetFontAndSize extends OperatorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(SetFontAndSize.class);

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        if (arguments.size() < 2) {
            throw new MissingOperandException(operator, arguments);
        }
        COSBase base0 = arguments.get(0);
        COSBase base1 = arguments.get(1);
        if (!(base0 instanceof COSName)) {
            return;
        }
        COSName fontName = (COSName) base0;
        if (!(base1 instanceof COSNumber)) {
            return;
        }
        float fontSize = ((COSNumber) base1).floatValue();
        getContext().getGraphicsState().getTextState().setFontSize(fontSize);
        PDFont font = getContext().getResources().getFont(fontName);
        if (Objects.isNull(font)) {
            LOG.warn("font '{}' not found in resources", fontName.getName());
        }
        getContext().getGraphicsState().getTextState().setFont(font);
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.SET_FONT_AND_SIZE;
    }
}
