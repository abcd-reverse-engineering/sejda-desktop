package org.sejda.sambox.contentstream.operator.graphics;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.MissingResourceException;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDTransparencyGroup;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/graphics/DrawObject.class */
public final class DrawObject extends GraphicsOperatorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(DrawObject.class);

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> operands) throws IOException {
        if (operands.isEmpty()) {
            throw new MissingOperandException(operator, operands);
        }
        COSBase base0 = operands.get(0);
        if (!(base0 instanceof COSName)) {
            return;
        }
        COSName objectName = (COSName) base0;
        PDXObject xobject = getContext().getResources().getXObject(objectName);
        if (xobject == null) {
            throw new MissingResourceException("Missing XObject: " + objectName.getName());
        }
        if (xobject instanceof PDImageXObject) {
            PDImageXObject image = (PDImageXObject) xobject;
            getContext().drawImage(image);
            return;
        }
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
