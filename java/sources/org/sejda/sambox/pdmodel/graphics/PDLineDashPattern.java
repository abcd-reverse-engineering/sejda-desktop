package org.sejda.sambox.pdmodel.graphics;

import java.util.Arrays;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/PDLineDashPattern.class */
public final class PDLineDashPattern implements COSObjectable {
    private final int phase;
    private final float[] array;

    public PDLineDashPattern() {
        this.array = new float[0];
        this.phase = 0;
    }

    public PDLineDashPattern(COSArray array, int phase) {
        this.array = array.toFloatArray();
        this.phase = phase;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        COSArray cos = new COSArray();
        COSArray patternArray = new COSArray();
        patternArray.setFloatArray(this.array);
        cos.add((COSBase) patternArray);
        cos.add(COSInteger.get(this.phase));
        return cos;
    }

    public int getPhase() {
        return this.phase;
    }

    public float[] getDashArray() {
        return (float[]) this.array.clone();
    }

    public String toString() {
        return "PDLineDashPattern{array=" + Arrays.toString(this.array) + ", phase=" + this.phase + "}";
    }
}
