package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.Paint;
import java.awt.geom.AffineTransform;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/PDShadingType1.class */
public class PDShadingType1 extends PDShading {
    private COSArray domain;

    public PDShadingType1(COSDictionary shadingDictionary) {
        super(shadingDictionary);
        this.domain = null;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public int getShadingType() {
        return 1;
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

    public COSArray getDomain() {
        if (this.domain == null) {
            this.domain = (COSArray) getCOSObject().getDictionaryObject(COSName.DOMAIN);
        }
        return this.domain;
    }

    public void setDomain(COSArray newDomain) {
        this.domain = newDomain;
        getCOSObject().setItem(COSName.DOMAIN, (COSBase) newDomain);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public Paint toPaint(Matrix matrix) {
        return new Type1ShadingPaint(this, matrix);
    }
}
