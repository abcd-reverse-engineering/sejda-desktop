package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/PDShadingType6.class */
public class PDShadingType6 extends PDMeshBasedShadingType {
    public PDShadingType6(COSDictionary shadingDictionary) {
        super(shadingDictionary);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShadingType4, org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public int getShadingType() {
        return 6;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShadingType4, org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public Paint toPaint(Matrix matrix) {
        return new Type6ShadingPaint(this, matrix);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDMeshBasedShadingType
    protected Patch generatePatch(Point2D[] points, float[][] color) {
        return new CoonsPatch(points, color);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDMeshBasedShadingType, org.sejda.sambox.pdmodel.graphics.shading.PDShadingType4, org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType, org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public Rectangle2D getBounds(AffineTransform xform, Matrix matrix) throws IOException {
        return getBounds(xform, matrix, 12);
    }
}
