package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;
import java.io.IOException;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/Type4ShadingContext.class */
class Type4ShadingContext extends GouraudShadingContext {
    private static final Logger LOG = LoggerFactory.getLogger(Type4ShadingContext.class);

    Type4ShadingContext(PDShadingType4 shading, ColorModel cm, AffineTransform xform, Matrix matrix, Rectangle deviceBounds) throws IOException {
        super(shading, cm, xform, matrix);
        LOG.debug("Type4ShadingContext");
        int bitsPerFlag = shading.getBitsPerFlag();
        LOG.debug("bitsPerFlag: " + bitsPerFlag);
        setTriangleList(shading.collectTriangles(xform, matrix));
        createPixelTable(deviceBounds);
    }
}
