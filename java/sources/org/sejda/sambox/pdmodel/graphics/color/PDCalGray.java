package org.sejda.sambox.pdmodel.graphics.color;

import java.util.HashMap;
import java.util.Map;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDCalGray.class */
public final class PDCalGray extends PDCIEDictionaryBasedColorSpace {
    private final PDColor initialColor;
    private final Map<Float, float[]> map1;

    public PDCalGray() {
        super(COSName.CALGRAY);
        this.initialColor = new PDColor(new float[]{0.0f}, this);
        this.map1 = new HashMap();
    }

    public PDCalGray(COSArray array) {
        super(array);
        this.initialColor = new PDColor(new float[]{0.0f}, this);
        this.map1 = new HashMap();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public String getName() {
        return COSName.CALGRAY.getName();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public int getNumberOfComponents() {
        return 1;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] getDefaultDecode(int bitsPerComponent) {
        return new float[]{0.0f, 1.0f};
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public PDColor getInitialColor() {
        return this.initialColor;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] toRGB(float[] value) {
        if (this.wpX == 1.0f && this.wpY == 1.0f && this.wpZ == 1.0f) {
            float a = value[0];
            float[] result = this.map1.get(Float.valueOf(a));
            if (result != null) {
                return (float[]) result.clone();
            }
            float gamma = getGamma();
            float powAG = (float) Math.pow(a, gamma);
            float[] result2 = convXYZtoRGB(powAG, powAG, powAG);
            this.map1.put(Float.valueOf(a), (float[]) result2.clone());
            return result2;
        }
        return new float[]{value[0], value[0], value[0]};
    }

    public float getGamma() {
        float retval = 1.0f;
        COSNumber gamma = (COSNumber) this.dictionary.getDictionaryObject(COSName.GAMMA);
        if (gamma != null) {
            retval = gamma.floatValue();
        }
        return retval;
    }

    public void setGamma(float value) {
        this.dictionary.setItem(COSName.GAMMA, (COSBase) new COSFloat(value));
    }
}
