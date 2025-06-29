package org.sejda.sambox.pdmodel.graphics.color;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDDeviceRGB.class */
public final class PDDeviceRGB extends PDDeviceColorSpace {
    public static final PDDeviceRGB INSTANCE = new PDDeviceRGB();
    private final PDColor initialColor = new PDColor(new float[]{0.0f, 0.0f, 0.0f}, this);

    private PDDeviceRGB() {
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public String getName() {
        return COSName.DEVICERGB.getName();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public int getNumberOfComponents() {
        return 3;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] getDefaultDecode(int bitsPerComponent) {
        return new float[]{0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f};
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public PDColor getInitialColor() {
        return this.initialColor;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] toRGB(float[] value) {
        return value;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public BufferedImage toRGBImage(WritableRaster raster) throws IOException {
        BufferedImage image = new BufferedImage(raster.getWidth(), raster.getHeight(), 1);
        image.setData(raster);
        return image;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public BufferedImage toRawImage(WritableRaster raster) throws IOException {
        return null;
    }
}
