package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.Paint;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/PDShadingType2.class */
public class PDShadingType2 extends PDShading {
    private COSArray coords;
    private COSArray domain;
    private COSArray extend;

    public PDShadingType2(COSDictionary shadingDictionary) {
        super(shadingDictionary);
        this.coords = null;
        this.domain = null;
        this.extend = null;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public int getShadingType() {
        return 2;
    }

    public COSArray getExtend() {
        if (this.extend == null) {
            this.extend = (COSArray) getCOSObject().getDictionaryObject(COSName.EXTEND);
        }
        return this.extend;
    }

    public void setExtend(COSArray newExtend) {
        this.extend = newExtend;
        getCOSObject().setItem(COSName.EXTEND, (COSBase) newExtend);
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

    public COSArray getCoords() {
        if (this.coords == null) {
            this.coords = (COSArray) getCOSObject().getDictionaryObject(COSName.COORDS);
        }
        return this.coords;
    }

    public void setCoords(COSArray newCoords) {
        this.coords = newCoords;
        getCOSObject().setItem(COSName.COORDS, (COSBase) newCoords);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public Paint toPaint(Matrix matrix) {
        return new AxialShadingPaint(this, matrix);
    }
}
