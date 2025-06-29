package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.geom.Point2D;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/Vertex.class */
class Vertex {
    public Point2D point;
    public float[] color;

    Vertex(Point2D p, float[] c) {
        this.point = p;
        this.color = (float[]) c.clone();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (float f : this.color) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(String.format("%3.2f", Float.valueOf(f)));
        }
        return "Vertex{ " + this.point + ", colors=[" + sb + "] }";
    }
}
