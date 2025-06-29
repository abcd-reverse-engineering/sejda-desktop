package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.geom.Point2D;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/CoordinateColorPair.class */
class CoordinateColorPair {
    final Point2D coordinate;
    final float[] color;

    CoordinateColorPair(Point2D p, float[] c) {
        this.coordinate = p;
        this.color = (float[]) c.clone();
    }
}
