package org.sejda.sambox.contentstream.operator.color;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.color.PDPattern;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/color/SetColor.class */
public abstract class SetColor extends OperatorProcessor {
    public abstract PDColor getColor();

    protected abstract void setColor(PDColor pDColor);

    public abstract PDColorSpace getColorSpace();

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) throws IOException {
        PDColorSpace colorSpace = getColorSpace();
        if (!(colorSpace instanceof PDPattern)) {
            if (arguments.size() < colorSpace.getNumberOfComponents()) {
                throw new MissingOperandException(operator, arguments);
            }
            if (!checkArrayTypesClass(arguments, COSNumber.class)) {
                return;
            }
        }
        COSArray array = new COSArray();
        array.addAll(arguments);
        setColor(new PDColor(array, colorSpace));
    }
}
