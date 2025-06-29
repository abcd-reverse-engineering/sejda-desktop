package org.sejda.sambox.pdmodel.graphics.color;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDCalRGB.class */
public class PDCalRGB extends PDCIEDictionaryBasedColorSpace {
    private final PDColor initialColor;

    public PDCalRGB() {
        super(COSName.CALRGB);
        this.initialColor = new PDColor(new float[]{0.0f, 0.0f, 0.0f}, this);
    }

    public PDCalRGB(COSArray rgb) {
        super(rgb);
        this.initialColor = new PDColor(new float[]{0.0f, 0.0f, 0.0f}, this);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public String getName() {
        return COSName.CALRGB.getName();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public int getNumberOfComponents() {
        return 3;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] getDefaultDecode(int bitsPerComponent) {
        return new float[]{0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f};
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public PDColor getInitialColor() {
        return this.initialColor;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] toRGB(float[] value) {
        if (this.wpX == 1.0f && this.wpY == 1.0f && this.wpZ == 1.0f) {
            float a = value[0];
            float b = value[1];
            float c = value[2];
            PDGamma gamma = getGamma();
            float powAR = (float) Math.pow(a, gamma.getR());
            float powBG = (float) Math.pow(b, gamma.getG());
            float powCB = (float) Math.pow(c, gamma.getB());
            float[] matrix = getMatrix();
            float mXA = matrix[0];
            float mYA = matrix[1];
            float mZA = matrix[2];
            float mXB = matrix[3];
            float mYB = matrix[4];
            float mZB = matrix[5];
            float mXC = matrix[6];
            float mYC = matrix[7];
            float mZC = matrix[8];
            float x = (mXA * powAR) + (mXB * powBG) + (mXC * powCB);
            float y = (mYA * powAR) + (mYB * powBG) + (mYC * powCB);
            float z = (mZA * powAR) + (mZB * powBG) + (mZC * powCB);
            return convXYZtoRGB(x, y, z);
        }
        return new float[]{value[0], value[1], value[2]};
    }

    public final PDGamma getGamma() {
        COSArray gammaArray = (COSArray) this.dictionary.getDictionaryObject(COSName.GAMMA);
        if (gammaArray == null) {
            gammaArray = new COSArray();
            gammaArray.add((COSBase) new COSFloat(1.0f));
            gammaArray.add((COSBase) new COSFloat(1.0f));
            gammaArray.add((COSBase) new COSFloat(1.0f));
            this.dictionary.setItem(COSName.GAMMA, (COSBase) gammaArray);
        }
        return new PDGamma(gammaArray);
    }

    public final float[] getMatrix() {
        COSArray matrix = (COSArray) this.dictionary.getDictionaryObject(COSName.MATRIX);
        if (matrix == null) {
            return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        }
        return matrix.toFloatArray();
    }

    public final void setGamma(PDGamma gamma) {
        COSArray gammaArray = null;
        if (gamma != null) {
            gammaArray = gamma.getCOSArray();
        }
        this.dictionary.setItem(COSName.GAMMA, (COSBase) gammaArray);
    }

    public final void setMatrix(Matrix matrix) {
        COSArray matrixArray = null;
        if (matrix != null) {
            float[][] values = matrix.getValues();
            matrixArray = new COSArray();
            matrixArray.add((COSBase) new COSFloat(values[0][0]));
            matrixArray.add((COSBase) new COSFloat(values[0][1]));
            matrixArray.add((COSBase) new COSFloat(values[0][2]));
            matrixArray.add((COSBase) new COSFloat(values[1][0]));
            matrixArray.add((COSBase) new COSFloat(values[1][1]));
            matrixArray.add((COSBase) new COSFloat(values[1][2]));
            matrixArray.add((COSBase) new COSFloat(values[2][0]));
            matrixArray.add((COSBase) new COSFloat(values[2][1]));
            matrixArray.add((COSBase) new COSFloat(values[2][2]));
        }
        this.dictionary.setItem(COSName.MATRIX, (COSBase) matrixArray);
    }
}
