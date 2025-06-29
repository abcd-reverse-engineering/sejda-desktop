package org.sejda.sambox.contentstream.operator.color;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/color/SetNonStrokingColorSpace.class */
public class SetNonStrokingColorSpace extends OperatorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(SetNonStrokingColorSpace.class);

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        if (arguments == null || arguments.size() == 0) {
            LOG.warn("Ignoring SetNonStrokingColorSpace operator without operands");
            return;
        }
        COSBase base = arguments.get(0);
        if (base instanceof COSName) {
            COSName name = (COSName) base;
            try {
                PDColorSpace cs = getContext().getResources().getColorSpace(name);
                getContext().getGraphicsState().setNonStrokingColorSpace(cs);
                getContext().getGraphicsState().setNonStrokingColor(cs.getInitialColor());
            } catch (IOException ex) {
                LOG.warn("Ignoring SetNonStrokingColorSpace operator, parsing colorspace caused an error", ex);
            }
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.NON_STROKING_COLORSPACE;
    }
}
