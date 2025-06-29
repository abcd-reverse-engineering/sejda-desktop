package org.sejda.sambox.pdmodel.interactive.documentnavigation.destination;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/destination/PDPageFitDestination.class */
public class PDPageFitDestination extends PDPageDestination {
    protected static final String TYPE = "Fit";
    protected static final String TYPE_BOUNDED = "FitB";

    public PDPageFitDestination() {
        this.array.growToSize(2);
        this.array.set(1, (COSBase) COSName.getPDFName(TYPE));
    }

    public PDPageFitDestination(COSArray arr) {
        super(arr);
    }

    public boolean fitBoundingBox() {
        return TYPE_BOUNDED.equals(this.array.getName(1));
    }

    public void setFitBoundingBox(boolean fitBoundingBox) {
        this.array.growToSize(2);
        if (fitBoundingBox) {
            this.array.set(1, (COSBase) COSName.getPDFName(TYPE_BOUNDED));
        } else {
            this.array.set(1, (COSBase) COSName.getPDFName(TYPE));
        }
    }

    @Override // org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination
    public void transform(Matrix transformation) {
    }
}
