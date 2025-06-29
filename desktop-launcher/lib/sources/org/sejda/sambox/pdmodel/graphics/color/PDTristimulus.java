package org.sejda.sambox.pdmodel.graphics.color;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDTristimulus.class */
public final class PDTristimulus implements COSObjectable {
    private final COSArray values;

    public PDTristimulus() {
        this.values = new COSArray(new COSFloat(0.0f), new COSFloat(0.0f), new COSFloat(0.0f));
    }

    public PDTristimulus(COSArray array) {
        this.values = array;
    }

    public PDTristimulus(float[] array) {
        this.values = new COSArray();
        for (int i = 0; i < array.length && i < 3; i++) {
            this.values.add((COSBase) new COSFloat(array[i]));
        }
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSArray getCOSObject() {
        return this.values;
    }

    public float getX() {
        return ((COSNumber) this.values.getObject(0, COSNumber.class)).floatValue();
    }

    public void setX(float x) {
        this.values.set(0, (COSBase) new COSFloat(x));
    }

    public float getY() {
        return ((COSNumber) this.values.getObject(1, COSNumber.class)).floatValue();
    }

    public void setY(float y) {
        this.values.set(1, (COSBase) new COSFloat(y));
    }

    public float getZ() {
        return ((COSNumber) this.values.getObject(2, COSNumber.class)).floatValue();
    }

    public void setZ(float z) {
        this.values.set(2, (COSBase) new COSFloat(z));
    }
}
