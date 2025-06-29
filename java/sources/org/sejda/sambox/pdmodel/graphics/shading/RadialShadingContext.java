package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.ColorModel;
import java.io.IOException;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBoolean;
import org.sejda.sambox.pdmodel.common.function.PDFunction;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/RadialShadingContext.class */
public class RadialShadingContext extends ShadingContext implements PaintContext {
    private static final Logger LOG = LoggerFactory.getLogger(RadialShadingContext.class);
    private PDShadingType3 radialShadingType;
    private final float[] coords;
    private final float[] domain;
    private final boolean[] extend;
    private final double x1x0;
    private final double y1y0;
    private final double r1r0;
    private final double r0pow2;
    private final float d1d0;
    private final double denom;
    private final int factor;
    private final int[] colorTable;
    private AffineTransform rat;

    public RadialShadingContext(PDShadingType3 shading, ColorModel colorModel, AffineTransform xform, Matrix matrix, Rectangle deviceBounds) throws IOException {
        super(shading, colorModel, xform, matrix);
        this.radialShadingType = shading;
        this.coords = shading.getCoords().toFloatArray();
        if (this.radialShadingType.getDomain() != null) {
            this.domain = shading.getDomain().toFloatArray();
        } else {
            this.domain = new float[]{0.0f, 1.0f};
        }
        COSArray extendValues = shading.getExtend();
        if (extendValues != null) {
            this.extend = new boolean[2];
            this.extend[0] = ((COSBoolean) extendValues.getObject(0)).getValue();
            this.extend[1] = ((COSBoolean) extendValues.getObject(1)).getValue();
        } else {
            this.extend = new boolean[]{false, false};
        }
        this.x1x0 = this.coords[3] - this.coords[0];
        this.y1y0 = this.coords[4] - this.coords[1];
        this.r1r0 = this.coords[5] - this.coords[2];
        this.r0pow2 = Math.pow(this.coords[2], 2.0d);
        this.denom = (Math.pow(this.x1x0, 2.0d) + Math.pow(this.y1y0, 2.0d)) - Math.pow(this.r1r0, 2.0d);
        this.d1d0 = this.domain[1] - this.domain[0];
        try {
            this.rat = matrix.createAffineTransform().createInverse();
            this.rat.concatenate(xform.createInverse());
        } catch (NoninvertibleTransformException ex) {
            LOG.warn(ex.getMessage() + ", matrix: " + matrix + ", using fallback to identity transform with a minimal scale to avoid singularity");
            this.rat = new AffineTransform();
            this.rat.scale(1.0E-6d, 1.0E-6d);
        }
        AffineTransform shadingToDevice = (AffineTransform) xform.clone();
        shadingToDevice.concatenate(matrix.createAffineTransform());
        double dist = Math.sqrt(Math.pow(deviceBounds.getMaxX() - deviceBounds.getMinX(), 2.0d) + Math.pow(deviceBounds.getMaxY() - deviceBounds.getMinY(), 2.0d));
        this.factor = (int) Math.ceil(dist);
        this.colorTable = calcColorTable();
    }

    private int[] calcColorTable() throws IOException {
        int[] map = new int[this.factor + 1];
        if (this.factor == 0 || this.d1d0 == 0.0f) {
            float[] values = this.radialShadingType.evalFunction(this.domain[0]);
            map[0] = convertToRGB(values);
        } else {
            for (int i = 0; i <= this.factor; i++) {
                float t = this.domain[0] + ((this.d1d0 * i) / this.factor);
                float[] values2 = this.radialShadingType.evalFunction(t);
                map[i] = convertToRGB(values2);
            }
        }
        return map;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.ShadingContext
    public void dispose() {
        super.dispose();
        this.radialShadingType = null;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.ShadingContext
    public ColorModel getColorModel() {
        return super.getColorModel();
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x019e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.awt.image.Raster getRaster(int r8, int r9, int r10, int r11) {
        /*
            Method dump skipped, instructions count: 533
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.sejda.sambox.pdmodel.graphics.shading.RadialShadingContext.getRaster(int, int, int, int):java.awt.image.Raster");
    }

    private float[] calculateInputValues(double x, double y) {
        double p = (((-(x - this.coords[0])) * this.x1x0) - ((y - this.coords[1]) * this.y1y0)) - (this.coords[2] * this.r1r0);
        double q = (Math.pow(x - this.coords[0], 2.0d) + Math.pow(y - this.coords[1], 2.0d)) - this.r0pow2;
        double root = Math.sqrt((p * p) - (this.denom * q));
        float root1 = (float) (((-p) + root) / this.denom);
        float root2 = (float) (((-p) - root) / this.denom);
        if (this.denom < 0.0d) {
            return new float[]{root1, root2};
        }
        return new float[]{root2, root1};
    }

    public float[] getCoords() {
        return this.coords;
    }

    public float[] getDomain() {
        return this.domain;
    }

    public boolean[] getExtend() {
        return this.extend;
    }

    public PDFunction getFunction() throws IOException {
        return this.radialShadingType.getFunction();
    }
}
