package org.sejda.sambox.pdmodel.interactive.documentnavigation.destination;

import java.awt.geom.Point2D;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/destination/PDPageFitWidthDestination.class */
public class PDPageFitWidthDestination extends PDPageDestination {
    protected static final String TYPE = "FitH";
    protected static final String TYPE_BOUNDED = "FitBH";

    public PDPageFitWidthDestination() {
        this.array.growToSize(3);
        this.array.set(1, (COSBase) COSName.getPDFName(TYPE));
    }

    public PDPageFitWidthDestination(COSArray arr) {
        super(arr);
    }

    public int getTop() {
        return this.array.getInt(2);
    }

    public void setTop(int y) {
        this.array.growToSize(3);
        if (y == -1) {
            this.array.set(2, (COSBase) null);
        } else {
            this.array.set(2, (COSBase) COSInteger.get(y));
        }
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
        Point2D.Float newCoord = transformation.transformPoint(0.0f, getTop());
        setTop((int) newCoord.y);
    }
}
