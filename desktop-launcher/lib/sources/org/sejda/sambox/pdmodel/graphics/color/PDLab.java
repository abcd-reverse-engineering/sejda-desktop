package org.sejda.sambox.pdmodel.graphics.color;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDRange;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDLab.class */
public final class PDLab extends PDCIEDictionaryBasedColorSpace {
    private PDColor initialColor;

    public PDLab() {
        super(COSName.LAB);
    }

    public PDLab(COSArray lab) {
        super(lab);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public String getName() {
        return COSName.LAB.getName();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDCIEBasedColorSpace, org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public BufferedImage toRGBImage(WritableRaster raster) throws IOException {
        int width = raster.getWidth();
        int height = raster.getHeight();
        BufferedImage rgbImage = new BufferedImage(width, height, 1);
        WritableRaster rgbRaster = rgbImage.getRaster();
        PDRange aRange = getARange();
        PDRange bRange = getBRange();
        float minA = aRange.getMin();
        float maxA = aRange.getMax();
        float minB = bRange.getMin();
        float maxB = bRange.getMax();
        float deltaA = maxA - minA;
        float deltaB = maxB - minB;
        float[] abc = new float[3];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                raster.getPixel(x, y, abc);
                abc[0] = abc[0] / 255.0f;
                abc[1] = abc[1] / 255.0f;
                abc[2] = abc[2] / 255.0f;
                abc[0] = abc[0] * 100.0f;
                abc[1] = minA + (abc[1] * deltaA);
                abc[2] = minB + (abc[2] * deltaB);
                float[] rgb = toRGB(abc);
                rgb[0] = rgb[0] * 255.0f;
                rgb[1] = rgb[1] * 255.0f;
                rgb[2] = rgb[2] * 255.0f;
                rgbRaster.setPixel(x, y, rgb);
            }
        }
        return rgbImage;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDCIEBasedColorSpace, org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public BufferedImage toRawImage(WritableRaster raster) {
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] toRGB(float[] value) {
        float lstar = (value[0] + 16.0f) * 0.00862069f;
        float x = this.wpX * inverse(lstar + (value[1] * 0.002f));
        float y = this.wpY * inverse(lstar);
        float z = this.wpZ * inverse(lstar - (value[2] * 0.005f));
        return convXYZtoRGB(x, y, z);
    }

    private float inverse(float x) {
        if (x > 0.20689655172413793d) {
            return x * x * x;
        }
        return 0.12841855f * (x - 0.13793103f);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public int getNumberOfComponents() {
        return 3;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] getDefaultDecode(int bitsPerComponent) {
        PDRange a = getARange();
        PDRange b = getBRange();
        return new float[]{0.0f, 100.0f, a.getMin(), a.getMax(), b.getMin(), b.getMax()};
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public PDColor getInitialColor() {
        if (this.initialColor == null) {
            this.initialColor = new PDColor(new float[]{0.0f, Math.max(0.0f, getARange().getMin()), Math.max(0.0f, getBRange().getMin())}, this);
        }
        return this.initialColor;
    }

    private COSArray getDefaultRangeArray() {
        COSArray range = new COSArray();
        range.add((COSBase) new COSFloat(-100.0f));
        range.add((COSBase) new COSFloat(100.0f));
        range.add((COSBase) new COSFloat(-100.0f));
        range.add((COSBase) new COSFloat(100.0f));
        return range;
    }

    public PDRange getARange() {
        COSArray rangeArray = (COSArray) this.dictionary.getDictionaryObject(COSName.RANGE);
        if (rangeArray == null) {
            rangeArray = getDefaultRangeArray();
        }
        return new PDRange(rangeArray, 0);
    }

    public PDRange getBRange() {
        COSArray rangeArray = (COSArray) this.dictionary.getDictionaryObject(COSName.RANGE);
        if (rangeArray == null) {
            rangeArray = getDefaultRangeArray();
        }
        return new PDRange(rangeArray, 1);
    }

    public void setARange(PDRange range) {
        setComponentRangeArray(range, 0);
    }

    public void setBRange(PDRange range) {
        setComponentRangeArray(range, 2);
    }

    private void setComponentRangeArray(PDRange range, int index) {
        COSArray rangeArray = (COSArray) this.dictionary.getDictionaryObject(COSName.RANGE);
        if (rangeArray == null) {
            rangeArray = getDefaultRangeArray();
        }
        if (range == null) {
            rangeArray.set(index, (COSBase) new COSFloat(-100.0f));
            rangeArray.set(index + 1, (COSBase) new COSFloat(100.0f));
        } else {
            rangeArray.set(index, (COSBase) new COSFloat(range.getMin()));
            rangeArray.set(index + 1, (COSBase) new COSFloat(range.getMax()));
        }
        this.dictionary.setItem(COSName.RANGE, (COSBase) rangeArray);
        this.initialColor = null;
    }
}
