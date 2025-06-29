package org.sejda.sambox.pdmodel.graphics.color;

import java.io.IOException;
import java.util.Arrays;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDColor.class */
public final class PDColor {
    private static final Logger LOG = LoggerFactory.getLogger(PDColor.class);
    private float[] components;
    private final COSName patternName;
    private final PDColorSpace colorSpace;

    public PDColor(COSArray array, PDColorSpace colorSpace) {
        if (array.size() > 0 && (array.get(array.size() - 1) instanceof COSName)) {
            this.components = new float[array.size() - 1];
            initComponents(array);
            COSBase base = array.get(array.size() - 1);
            if (base instanceof COSName) {
                this.patternName = (COSName) base;
            } else {
                LOG.warn("pattern name in " + array + " isn't a name, ignored");
                this.patternName = COSName.getPDFName("Unknown");
            }
        } else {
            this.components = new float[array.size()];
            initComponents(array);
            this.patternName = null;
        }
        this.colorSpace = colorSpace;
    }

    private void initComponents(COSArray array) {
        for (int i = 0; i < this.components.length; i++) {
            COSBase base = array.get(i);
            if (base instanceof COSNumber) {
                this.components[i] = ((COSNumber) base).floatValue();
            } else {
                LOG.warn("color component " + i + " in " + array + " isn't a number, ignored");
                this.components[i] = 0.0f;
            }
        }
    }

    public PDColor(float[] components, PDColorSpace colorSpace) {
        this.components = (float[]) components.clone();
        this.patternName = null;
        this.colorSpace = colorSpace;
    }

    public PDColor(COSName patternName, PDColorSpace colorSpace) {
        this.components = new float[0];
        this.patternName = patternName;
        this.colorSpace = colorSpace;
    }

    public PDColor(float[] components, COSName patternName, PDColorSpace colorSpace) {
        this.components = (float[]) components.clone();
        this.patternName = patternName;
        this.colorSpace = colorSpace;
    }

    public float[] getComponents() {
        if ((this.colorSpace instanceof PDPattern) || this.colorSpace == null) {
            return (float[]) this.components.clone();
        }
        return Arrays.copyOf(this.components, this.colorSpace.getNumberOfComponents());
    }

    public COSName getPatternName() {
        return this.patternName;
    }

    public boolean isPattern() {
        return this.patternName != null;
    }

    public int toRGB() throws IOException {
        float[] floats = this.colorSpace.toRGB(this.components);
        int r = Math.round(floats[0] * 255.0f);
        int g = Math.round(floats[1] * 255.0f);
        int b = Math.round(floats[2] * 255.0f);
        int rgb = (r << 8) + g;
        return (rgb << 8) + b;
    }

    public COSArray toCOSArray() {
        COSArray array = new COSArray();
        array.setFloatArray(this.components);
        if (this.patternName != null) {
            array.add((COSBase) this.patternName);
        }
        return array;
    }

    public COSArray toComponentsCOSArray() {
        COSArray array = new COSArray();
        array.setFloatArray(this.components);
        return array;
    }

    public PDColorSpace getColorSpace() {
        return this.colorSpace;
    }

    public String toString() {
        return "PDColor{components=" + Arrays.toString(this.components) + ", patternName=" + this.patternName + "}";
    }
}
