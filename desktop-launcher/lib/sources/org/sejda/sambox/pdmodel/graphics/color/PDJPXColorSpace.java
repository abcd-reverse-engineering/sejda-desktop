package org.sejda.sambox.pdmodel.graphics.color;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDJPXColorSpace.class */
public final class PDJPXColorSpace extends PDColorSpace {
    private final ColorSpace awtColorSpace;

    public PDJPXColorSpace(ColorSpace colorSpace) {
        this.awtColorSpace = colorSpace;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public String getName() {
        return "JPX";
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public int getNumberOfComponents() {
        return this.awtColorSpace.getNumComponents();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] getDefaultDecode(int bitsPerComponent) {
        int n = getNumberOfComponents();
        float[] decode = new float[n * 2];
        for (int i = 0; i < n; i++) {
            decode[i * 2] = this.awtColorSpace.getMinValue(i);
            decode[(i * 2) + 1] = this.awtColorSpace.getMaxValue(i);
        }
        return decode;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public PDColor getInitialColor() {
        throw new UnsupportedOperationException("JPX color spaces don't support drawing");
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] toRGB(float[] value) {
        throw new UnsupportedOperationException("JPX color spaces don't support drawing");
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public BufferedImage toRGBImage(WritableRaster raster) throws IOException {
        return toRGBImageAWT(raster, this.awtColorSpace);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public BufferedImage toRawImage(WritableRaster raster) {
        return toRawImage(raster, this.awtColorSpace);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace, org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        throw new UnsupportedOperationException("JPX color spaces don't have COS objects");
    }
}
