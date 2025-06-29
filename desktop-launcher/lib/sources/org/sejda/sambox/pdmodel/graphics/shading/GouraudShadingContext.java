package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/GouraudShadingContext.class */
abstract class GouraudShadingContext extends TriangleBasedShadingContext {
    private List<ShadedTriangle> triangleList;

    protected GouraudShadingContext(PDShading shading, ColorModel colorModel, AffineTransform xform, Matrix matrix) throws IOException {
        super(shading, colorModel, xform, matrix);
        this.triangleList = new ArrayList();
    }

    final void setTriangleList(List<ShadedTriangle> triangleList) {
        this.triangleList = triangleList;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.TriangleBasedShadingContext
    protected Map<Point, Integer> calcPixelTable(Rectangle deviceBounds) throws IOException {
        Map<Point, Integer> map = new HashMap<>();
        super.calcPixelTable(this.triangleList, map, deviceBounds);
        return map;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.TriangleBasedShadingContext, org.sejda.sambox.pdmodel.graphics.shading.ShadingContext
    public void dispose() {
        this.triangleList = null;
        super.dispose();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.TriangleBasedShadingContext
    protected boolean isDataEmpty() {
        return this.triangleList.isEmpty();
    }
}
