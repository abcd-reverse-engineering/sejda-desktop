package org.sejda.sambox.pdmodel.graphics.color;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDDeviceCMYK.class */
public class PDDeviceCMYK extends PDDeviceColorSpace {
    public static PDDeviceCMYK INSTANCE = new PDDeviceCMYK();
    private ICC_ColorSpace awtColorSpace;
    private final PDColor initialColor = new PDColor(new float[]{0.0f, 0.0f, 0.0f, 1.0f}, this);
    private volatile boolean initDone = false;
    private boolean usePureJavaCMYKConversion = false;

    protected PDDeviceCMYK() {
    }

    protected void init() throws IOException {
        if (this.initDone) {
            return;
        }
        synchronized (this) {
            if (this.initDone) {
                return;
            }
            ICC_Profile iccProfile = getICCProfile();
            if (iccProfile == null) {
                throw new IOException("Default CMYK color profile could not be loaded");
            }
            this.awtColorSpace = new ICC_ColorSpace(iccProfile);
            this.awtColorSpace.toRGB(new float[]{0.0f, 0.0f, 0.0f, 0.0f});
            this.usePureJavaCMYKConversion = System.getProperty("org.sejda.sambox.rendering.UsePureJavaCMYKConversion") != null;
            this.initDone = true;
        }
    }

    protected ICC_Profile getICCProfile() throws IOException {
        InputStream is = PDDeviceCMYK.class.getResourceAsStream("/org/sejda/sambox/resources/icc/ISOcoated_v2_300_bas.icc");
        try {
            RequireUtils.requireIOCondition(Objects.nonNull(is), "Error loading " + "/org/sejda/sambox/resources/icc/ISOcoated_v2_300_bas.icc");
            ICC_Profile iCC_Profile = ICC_Profile.getInstance(new BufferedInputStream(is));
            if (is != null) {
                is.close();
            }
            return iCC_Profile;
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public String getName() {
        return COSName.DEVICECMYK.getName();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public int getNumberOfComponents() {
        return 4;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] getDefaultDecode(int bitsPerComponent) {
        return new float[]{0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f};
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public PDColor getInitialColor() {
        return this.initialColor;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] toRGB(float[] value) throws IOException {
        init();
        return this.awtColorSpace.toRGB(value);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public BufferedImage toRawImage(WritableRaster raster) throws IOException {
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public BufferedImage toRGBImage(WritableRaster raster) throws IOException {
        init();
        return toRGBImageAWT(raster, this.awtColorSpace);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    protected BufferedImage toRGBImageAWT(WritableRaster raster, ColorSpace colorSpace) {
        if (this.usePureJavaCMYKConversion) {
            BufferedImage dest = new BufferedImage(raster.getWidth(), raster.getHeight(), 1);
            ColorSpace destCS = dest.getColorModel().getColorSpace();
            WritableRaster destRaster = dest.getRaster();
            float[] srcValues = new float[4];
            float[] lastValues = {-1.0f, -1.0f, -1.0f, -1.0f};
            float[] destValues = new float[3];
            int startX = raster.getMinX();
            int startY = raster.getMinY();
            int endX = raster.getWidth() + startX;
            int endY = raster.getHeight() + startY;
            for (int x = startX; x < endX; x++) {
                for (int y = startY; y < endY; y++) {
                    raster.getPixel(x, y, srcValues);
                    if (!Arrays.equals(lastValues, srcValues)) {
                        lastValues[0] = srcValues[0];
                        srcValues[0] = srcValues[0] / 255.0f;
                        lastValues[1] = srcValues[1];
                        srcValues[1] = srcValues[1] / 255.0f;
                        lastValues[2] = srcValues[2];
                        srcValues[2] = srcValues[2] / 255.0f;
                        lastValues[3] = srcValues[3];
                        srcValues[3] = srcValues[3] / 255.0f;
                        destValues = destCS.fromCIEXYZ(colorSpace.toCIEXYZ(srcValues));
                        for (int k = 0; k < destValues.length; k++) {
                            destValues[k] = destValues[k] * 255.0f;
                        }
                    }
                    destRaster.setPixel(x, y, destValues);
                }
            }
            return dest;
        }
        return super.toRGBImageAWT(raster, colorSpace);
    }
}
