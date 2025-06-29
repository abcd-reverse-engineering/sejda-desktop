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

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/color/SetStrokingColorSpace.class */
public class SetStrokingColorSpace extends OperatorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(SetStrokingColorSpace.class);

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        if (arguments == null || arguments.size() == 0) {
            LOG.warn("Ignoring SetStrokingColorSpace operator without operands");
            return;
        }
        COSBase base = arguments.get(0);
        if (base instanceof COSName) {
            COSName name = (COSName) base;
            try {
                PDColorSpace cs = getContext().getResources().getColorSpace(name);
                getContext().getGraphicsState().setStrokingColorSpace(cs);
                getContext().getGraphicsState().setStrokingColor(cs.getInitialColor());
            } catch (IOException ex) {
                LOG.warn("Ignoring SetStrokingColorSpace operator, parsing colorspace caused an error", ex);
            }
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.STROKING_COLORSPACE;
    }
}
