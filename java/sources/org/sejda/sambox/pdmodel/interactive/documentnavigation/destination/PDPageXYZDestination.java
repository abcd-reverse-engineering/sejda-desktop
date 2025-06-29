package org.sejda.sambox.pdmodel.interactive.documentnavigation.destination;

import java.awt.geom.Point2D;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/destination/PDPageXYZDestination.class */
public class PDPageXYZDestination extends PDPageDestination {
    protected static final String TYPE = "XYZ";

    public PDPageXYZDestination() {
        this.array.growToSize(5);
        this.array.set(1, (COSBase) COSName.getPDFName(TYPE));
    }

    public PDPageXYZDestination(COSArray arr) {
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

    public int getTop() {
        return this.array.getInt(3);
    }

    public void setTop(int y) {
        this.array.growToSize(4);
        if (y == -1) {
            this.array.set(3, (COSBase) null);
        } else {
            this.array.set(3, (COSBase) COSInteger.get(y));
        }
    }

    public float getZoom() {
        COSBase obj = this.array.getObject(4);
        if (obj instanceof COSNumber) {
            return ((COSNumber) obj).floatValue();
        }
        return -1.0f;
    }

    public void setZoom(float zoom) {
        this.array.growToSize(5);
        if (zoom == -1.0f) {
            this.array.set(4, (COSBase) null);
        } else {
            this.array.set(4, (COSBase) new COSFloat(zoom));
        }
    }

    @Override // org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination
    public void transform(Matrix transformation) {
        Point2D.Float newCoord = transformation.transformPoint(getLeft(), getTop());
        setLeft((int) newCoord.x);
        setTop((int) newCoord.y);
    }
}
