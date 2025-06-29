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

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/AxialShadingContext.class */
public class AxialShadingContext extends ShadingContext implements PaintContext {
    private static final Logger LOG = LoggerFactory.getLogger(AxialShadingContext.class);
    private PDShadingType2 axialShadingType;
    private final float[] coords;
    private final float[] domain;
    private final boolean[] extend;
    private final double x1x0;
    private final double y1y0;
    private final float d1d0;
    private final double denom;
    private final int factor;
    private final int[] colorTable;
    private AffineTransform rat;

    public AxialShadingContext(PDShadingType2 shading, ColorModel colorModel, AffineTransform xform, Matrix matrix, Rectangle deviceBounds) throws IOException {
        super(shading, colorModel, xform, matrix);
        this.axialShadingType = shading;
        this.coords = shading.getCoords().toFloatArray();
        if (shading.getDomain() != null) {
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
        this.x1x0 = this.coords[2] - this.coords[0];
        this.y1y0 = this.coords[3] - this.coords[1];
        this.d1d0 = this.domain[1] - this.domain[0];
        this.denom = Math.pow(this.x1x0, 2.0d) + Math.pow(this.y1y0, 2.0d);
        try {
            this.rat = matrix.createAffineTransform().createInverse();
            this.rat.concatenate(xform.createInverse());
        } catch (NoninvertibleTransformException ex) {
            LOG.error(ex.getMessage() + ", matrix: " + matrix, ex);
            LOG.error(ex.getMessage(), ex);
            this.rat = new AffineTransform();
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
            float[] values = this.axialShadingType.evalFunction(this.domain[0]);
            map[0] = convertToRGB(values);
        } else {
            for (int i = 0; i <= this.factor; i++) {
                float t = this.domain[0] + ((this.d1d0 * i) / this.factor);
                float[] values2 = this.axialShadingType.evalFunction(t);
                map[i] = convertToRGB(values2);
            }
        }
        return map;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.ShadingContext
    public void dispose() {
        super.dispose();
        this.axialShadingType = null;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.ShadingContext
    public ColorModel getColorModel() {
        return super.getColorModel();
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00fb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.awt.image.Raster getRaster(int r9, int r10, int r11, int r12) {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.sejda.sambox.pdmodel.graphics.shading.AxialShadingContext.getRaster(int, int, int, int):java.awt.image.Raster");
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
        return this.axialShadingType.getFunction();
    }
}
