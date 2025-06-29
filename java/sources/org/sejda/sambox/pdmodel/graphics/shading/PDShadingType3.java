package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.Paint;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/PDShadingType3.class */
public class PDShadingType3 extends PDShadingType2 {
    public PDShadingType3(COSDictionary shadingDictionary) {
        super(shadingDictionary);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShadingType2, org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public int getShadingType() {
        return 3;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShadingType2, org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public Paint toPaint(Matrix matrix) {
        return new RadialShadingPaint(this, matrix);
    }
}
