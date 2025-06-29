package org.sejda.sambox.contentstream.operator;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDTransparencyGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/DrawObject.class */
public class DrawObject extends OperatorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(DrawObject.class);

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        if (arguments.isEmpty()) {
            throw new MissingOperandException(operator, arguments);
        }
        COSBase base0 = arguments.get(0);
        if (!(base0 instanceof COSName)) {
            return;
        }
        COSName name = (COSName) base0;
        if (getContext().getResources().isImageXObject(name)) {
            return;
        }
        PDXObject xobject = getContext().getResources().getXObject(name);
        if (xobject instanceof PDFormXObject) {
            try {
                getContext().increaseLevel();
                if (getContext().getLevel() > 50) {
                    LOG.error("recursion is too deep, skipping form XObject");
                    getContext().decreaseLevel();
                } else {
                    if (xobject instanceof PDTransparencyGroup) {
                        getContext().showTransparencyGroup((PDTransparencyGroup) xobject);
                    } else {
                        getContext().showForm((PDFormXObject) xobject);
                    }
                }
            } finally {
                getContext().decreaseLevel();
            }
        }
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.DRAW_OBJECT;
    }
}
