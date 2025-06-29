package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.PaintContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.ColorModel;
import java.io.IOException;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/Type1ShadingContext.class */
class Type1ShadingContext extends ShadingContext implements PaintContext {
    private static final Logger LOG = LoggerFactory.getLogger(Type1ShadingContext.class);
    private PDShadingType1 type1ShadingType;
    private AffineTransform rat;
    private final float[] domain;

    Type1ShadingContext(PDShadingType1 shading, ColorModel colorModel, AffineTransform xform, Matrix matrix) throws IOException {
        super(shading, colorModel, xform, matrix);
        this.type1ShadingType = shading;
        if (shading.getDomain() != null) {
            this.domain = shading.getDomain().toFloatArray();
        } else {
            this.domain = new float[]{0.0f, 1.0f, 0.0f, 1.0f};
        }
        try {
            this.rat = shading.getMatrix().createAffineTransform().createInverse();
            this.rat.concatenate(matrix.createAffineTransform().createInverse());
            this.rat.concatenate(xform.createInverse());
        } catch (NoninvertibleTransformException ex) {
            LOG.error(ex.getMessage() + ", matrix: " + matrix, ex);
            this.rat = new AffineTransform();
        }
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.ShadingContext
    public void dispose() {
        super.dispose();
        this.type1ShadingType = null;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.ShadingContext
    public ColorModel getColorModel() {
        return super.getColorModel();
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00f6 A[PHI: r19
  0x00f6: PHI (r19v3 'tmpValues' float[]) = (r19v1 'tmpValues' float[]), (r19v2 'tmpValues' float[]) binds: [B:27:0x00d6, B:28:0x00d9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00d9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00af A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.awt.image.Raster getRaster(int r8, int r9, int r10, int r11) {
        /*
            Method dump skipped, instructions count: 326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.sejda.sambox.pdmodel.graphics.shading.Type1ShadingContext.getRaster(int, int, int, int):java.awt.image.Raster");
    }

    public float[] getDomain() {
        return this.domain;
    }
}
