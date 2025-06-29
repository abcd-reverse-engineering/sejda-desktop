package org.sejda.sambox.pdmodel.common.function;

import java.util.Optional;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/PDFunctionType2.class */
public class PDFunctionType2 extends PDFunction {
    private final COSArray c0;
    private final COSArray c1;
    private final float exponent;

    public PDFunctionType2(COSBase function) {
        super(function);
        this.c0 = (COSArray) Optional.ofNullable((COSArray) getCOSObject().getDictionaryObject(COSName.C0, COSArray.class)).orElseGet(COSArray::new);
        if (this.c0.size() == 0) {
            this.c0.add((COSBase) new COSFloat(0.0f));
        }
        this.c1 = (COSArray) Optional.ofNullable((COSArray) getCOSObject().getDictionaryObject(COSName.C1, COSArray.class)).orElseGet(COSArray::new);
        if (this.c1.size() == 0) {
            this.c1.add((COSBase) new COSFloat(1.0f));
        }
        this.exponent = getCOSObject().getFloat(COSName.N);
    }

    @Override // org.sejda.sambox.pdmodel.common.function.PDFunction
    public int getFunctionType() {
        return 2;
    }

    @Override // org.sejda.sambox.pdmodel.common.function.PDFunction
    public float[] eval(float[] input) {
        float xToN = (float) Math.pow(input[0], this.exponent);
        float[] result = new float[Math.min(this.c0.size(), this.c1.size())];
        for (int j = 0; j < result.length; j++) {
            float c0j = ((COSNumber) this.c0.getObject(j)).floatValue();
            float c1j = ((COSNumber) this.c1.getObject(j)).floatValue();
            result[j] = c0j + (xToN * (c1j - c0j));
        }
        return clipToRange(result);
    }

    public COSArray getC0() {
        return this.c0;
    }

    public COSArray getC1() {
        return this.c1;
    }

    public float getN() {
        return this.exponent;
    }

    @Override // org.sejda.sambox.pdmodel.common.function.PDFunction
    public String toString() {
        return "FunctionType2{C0: " + getC0() + " C1: " + getC1() + " N: " + getN() + "}";
    }
}
