package org.sejda.sambox.pdmodel.graphics.color;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDGamma.class */
public final class PDGamma implements COSObjectable {
    private final COSArray values;

    public PDGamma() {
        this.values = new COSArray(new COSFloat(0.0f), new COSFloat(0.0f), new COSFloat(0.0f));
    }

    public PDGamma(COSArray array) {
        this.values = array;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.values;
    }

    public COSArray getCOSArray() {
        return this.values;
    }

    public float getR() {
        return ((COSNumber) this.values.get(0)).floatValue();
    }

    public void setR(float r) {
        this.values.set(0, (COSBase) new COSFloat(r));
    }

    public float getG() {
        return ((COSNumber) this.values.get(1)).floatValue();
    }

    public void setG(float g) {
        this.values.set(1, (COSBase) new COSFloat(g));
    }

    public float getB() {
        return ((COSNumber) this.values.get(2)).floatValue();
    }

    public void setB(float b) {
        this.values.set(2, (COSBase) new COSFloat(b));
    }
}
