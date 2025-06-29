package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;
import java.io.IOException;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/Type5ShadingContext.class */
class Type5ShadingContext extends GouraudShadingContext {
    Type5ShadingContext(PDShadingType5 shading, ColorModel cm, AffineTransform xform, Matrix matrix, Rectangle deviceBounds) throws IOException {
        super(shading, cm, xform, matrix);
        setTriangleList(shading.collectTriangles(xform, matrix));
        createPixelTable(deviceBounds);
    }
}
