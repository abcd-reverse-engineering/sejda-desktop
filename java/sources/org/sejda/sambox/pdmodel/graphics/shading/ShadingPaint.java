package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.Paint;
import org.sejda.sambox.pdmodel.graphics.shading.PDShading;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/ShadingPaint.class */
public abstract class ShadingPaint<TPDShading extends PDShading> implements Paint {
    protected final TPDShading shading;
    protected final Matrix matrix;

    ShadingPaint(TPDShading shading, Matrix matrix) {
        this.shading = shading;
        this.matrix = matrix;
    }

    public TPDShading getShading() {
        return this.shading;
    }

    public Matrix getMatrix() {
        return this.matrix;
    }
}
