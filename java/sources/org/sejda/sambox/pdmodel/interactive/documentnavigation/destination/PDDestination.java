package org.sejda.sambox.pdmodel.interactive.documentnavigation.destination;

import java.io.IOException;
import java.util.Objects;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.pdmodel.common.PDDestinationOrAction;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/destination/PDDestination.class */
public abstract class PDDestination implements PDDestinationOrAction {
    public static PDDestination create(COSBase base) throws IOException {
        if (Objects.nonNull(base)) {
            if (base instanceof COSString) {
                return new PDNamedDestination((COSString) base);
            }
            if (base instanceof COSName) {
                return new PDNamedDestination((COSName) base);
            }
            if (base instanceof COSArray) {
                COSArray array = (COSArray) base;
                if (((COSArray) base).size() > 1 && (((COSArray) base).getObject(1) instanceof COSName)) {
                    String typeString = ((COSName) array.getObject(1)).getName();
                    if (typeString.equals("Fit") || typeString.equals("FitB")) {
                        return new PDPageFitDestination(array);
                    }
                    if (typeString.equals("FitV") || typeString.equals("FitBV")) {
                        return new PDPageFitHeightDestination(array);
                    }
                    if (typeString.equals("FitR")) {
                        return new PDPageFitRectangleDestination(array);
                    }
                    if (typeString.equals("FitH") || typeString.equals("FitBH")) {
                        return new PDPageFitWidthDestination(array);
                    }
                    if (typeString.equals("XYZ")) {
                        return new PDPageXYZDestination(array);
                    }
                    throw new IOException("Unknown destination type: " + typeString);
                }
            }
            throw new IOException("Cannot convert " + base + " to a destination");
        }
        return null;
    }
}
