package org.sejda.sambox.pdmodel.interactive.documentnavigation.destination;

import java.awt.geom.Point2D;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/destination/PDPageFitRectangleDestination.class */
public class PDPageFitRectangleDestination extends PDPageDestination {
    protected static final String TYPE = "FitR";

    public PDPageFitRectangleDestination() {
        this.array.growToSize(6);
        this.array.set(1, (COSBase) COSName.getPDFName(TYPE));
    }

    public PDPageFitRectangleDestination(COSArray arr) {
        super(arr);
    }

    public int getLeft() {
        return this.array.getInt(2);
    }

    public void setLeft(int x) {
        this.array.growToSize(3);
        if (x == -1) {
            this.array.set(2, (COSBase) null);
        } else {
            this.array.set(2, (COSBase) COSInteger.get(x));
        }
    }

    public int getBottom() {
        return this.array.getInt(3);
    }

    public void setBottom(int y) {
        this.array.growToSize(6);
        if (y == -1) {
            this.array.set(3, (COSBase) null);
        } else {
            this.array.set(3, (COSBase) COSInteger.get(y));
        }
    }

    public int getRight() {
        return this.array.getInt(4);
    }

    public void setRight(int x) {
        this.array.growToSize(6);
        if (x == -1) {
            this.array.set(4, (COSBase) null);
        } else {
            this.array.set(4, (COSBase) COSInteger.get(x));
        }
    }

    public int getTop() {
        return this.array.getInt(5);
    }

    public void setTop(int y) {
        this.array.growToSize(6);
        if (y == -1) {
            this.array.set(5, (COSBase) null);
        } else {
            this.array.set(5, (COSBase) COSInteger.get(y));
        }
    }

    @Override // org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination
    public void transform(Matrix transformation) {
        Point2D.Float newCoord = transformation.transformPoint(getLeft(), getTop());
        setLeft((int) newCoord.x);
        setTop((int) newCoord.y);
        Point2D.Float newCoord1 = transformation.transformPoint(getRight(), getBottom());
        setRight((int) newCoord1.x);
        setBottom((int) newCoord1.y);
    }
}
