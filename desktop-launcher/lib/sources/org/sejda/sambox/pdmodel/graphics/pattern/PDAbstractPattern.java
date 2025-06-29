package org.sejda.sambox.pdmodel.graphics.pattern;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.ResourceCache;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/pattern/PDAbstractPattern.class */
public abstract class PDAbstractPattern implements COSObjectable {
    public static final int TYPE_TILING_PATTERN = 1;
    public static final int TYPE_SHADING_PATTERN = 2;
    private final COSDictionary patternDictionary;

    public abstract int getPatternType();

    public static PDAbstractPattern create(COSDictionary resourceDictionary) throws IOException {
        return create(resourceDictionary, null);
    }

    public static PDAbstractPattern create(COSDictionary resourceDictionary, ResourceCache resourceCache) throws IOException {
        PDAbstractPattern pattern;
        int patternType = resourceDictionary.getInt(COSName.PATTERN_TYPE, 0);
        switch (patternType) {
            case 1:
                pattern = new PDTilingPattern(resourceDictionary, resourceCache);
                break;
            case 2:
                pattern = new PDShadingPattern(resourceDictionary);
                break;
            default:
                throw new IOException("Error: Unknown pattern type " + patternType);
        }
        return pattern;
    }

    public PDAbstractPattern() {
        this.patternDictionary = new COSDictionary();
        this.patternDictionary.setName(COSName.TYPE, COSName.PATTERN.getName());
    }

    public PDAbstractPattern(COSDictionary resourceDictionary) {
        this.patternDictionary = resourceDictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.patternDictionary;
    }

    public void setPaintType(int paintType) {
        this.patternDictionary.setInt(COSName.PAINT_TYPE, paintType);
    }

    public String getType() {
        return COSName.PATTERN.getName();
    }

    public void setPatternType(int patternType) {
        this.patternDictionary.setInt(COSName.PATTERN_TYPE, patternType);
    }

    public Matrix getMatrix() {
        return Matrix.createMatrix(getCOSObject().getDictionaryObject(COSName.MATRIX));
    }

    public void setMatrix(AffineTransform transform) {
        COSArray matrix = new COSArray();
        double[] values = new double[6];
        transform.getMatrix(values);
        for (double v : values) {
            matrix.add((COSBase) new COSFloat((float) v));
        }
        getCOSObject().setItem(COSName.MATRIX, (COSBase) matrix);
    }
}
