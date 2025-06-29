package org.sejda.sambox.pdmodel.graphics.color;

import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ComponentColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Hashtable;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.MissingResourceException;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.ResourceCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDColorSpace.class */
public abstract class PDColorSpace implements COSObjectable {
    private static final Logger LOG = LoggerFactory.getLogger(PDColorSpace.class);
    private final ColorConvertOp colorConvertOp = new ColorConvertOp((RenderingHints) null);
    protected COSArray array;

    public abstract String getName();

    public abstract int getNumberOfComponents();

    public abstract float[] getDefaultDecode(int i);

    public abstract PDColor getInitialColor();

    public abstract float[] toRGB(float[] fArr) throws IOException;

    public abstract BufferedImage toRGBImage(WritableRaster writableRaster) throws IOException;

    public abstract BufferedImage toRawImage(WritableRaster writableRaster) throws IOException;

    public static PDColorSpace create(COSBase colorSpace) throws IOException {
        return create(colorSpace, null, false);
    }

    public static PDColorSpace create(COSBase colorSpace, PDResources resources) throws IOException {
        return create(colorSpace, resources, false);
    }

    public static PDColorSpace create(COSBase colorSpace, PDResources resources, boolean wasDefault) throws IOException {
        ResourceCache cache;
        PDColorSpace existing;
        boolean canCache = (!colorSpace.hasId() || resources == null || resources.getResourceCache() == null) ? false : true;
        if (canCache && (existing = resources.getResourceCache().getColorSpace(colorSpace.id().objectIdentifier)) != null) {
            LOG.trace("Using cached color space for {}", colorSpace.id().objectIdentifier);
            return existing;
        }
        PDColorSpace result = createUncached(colorSpace, resources, wasDefault, 0);
        if (colorSpace.hasId() && resources != null && (cache = resources.getResourceCache()) != null && isAllowedCache(result)) {
            cache.put(colorSpace.id().objectIdentifier, result);
        }
        return result;
    }

    public static boolean isAllowedCache(PDColorSpace colorSpace) {
        return !(colorSpace instanceof PDPattern);
    }

    private static PDColorSpace createUncached(COSBase colorSpace, PDResources resources, boolean wasDefault, int recursionAccumulator) throws IOException {
        if (recursionAccumulator > 4) {
            throw new IOException("Could not create color space, infinite recursion detected");
        }
        if (colorSpace == null) {
            throw new IOException("Invalid color space (null)");
        }
        COSBase colorSpace2 = colorSpace.getCOSObject();
        if (colorSpace2 instanceof COSName) {
            COSName name = (COSName) colorSpace2;
            if (resources != null) {
                COSName defaultName = null;
                if (name.equals(COSName.DEVICECMYK) && resources.hasColorSpace(COSName.DEFAULT_CMYK)) {
                    defaultName = COSName.DEFAULT_CMYK;
                } else if (name.equals(COSName.DEVICERGB) && resources.hasColorSpace(COSName.DEFAULT_RGB)) {
                    defaultName = COSName.DEFAULT_RGB;
                } else if (name.equals(COSName.DEVICEGRAY) && resources.hasColorSpace(COSName.DEFAULT_GRAY)) {
                    defaultName = COSName.DEFAULT_GRAY;
                }
                if (resources.hasColorSpace(defaultName) && !wasDefault) {
                    return resources.getColorSpace(defaultName, true);
                }
            }
            if (name == COSName.DEVICECMYK) {
                return PDDeviceCMYK.INSTANCE;
            }
            if (name == COSName.DEVICERGB) {
                return PDDeviceRGB.INSTANCE;
            }
            if (name == COSName.DEVICEGRAY) {
                return PDDeviceGray.INSTANCE;
            }
            if (name == COSName.PATTERN) {
                return new PDPattern(resources);
            }
            if (resources != null) {
                if (!resources.hasColorSpace(name)) {
                    throw new MissingResourceException("Missing color space: " + name.getName());
                }
                return resources.getColorSpace(name);
            }
            throw new MissingResourceException("Unknown color space: " + name.getName());
        }
        if (colorSpace2 instanceof COSArray) {
            COSArray array = (COSArray) colorSpace2;
            if (array.size() == 0) {
                throw new IOException("Colorspace array is empty");
            }
            COSBase base = array.getObject(0);
            if (!(base instanceof COSName)) {
                throw new IOException("First element in colorspace array must be a name");
            }
            COSName name2 = (COSName) base;
            if (name2 == COSName.CALGRAY) {
                return new PDCalGray(array);
            }
            if (name2 == COSName.CALRGB) {
                return new PDCalRGB(array);
            }
            if (name2 == COSName.DEVICEN) {
                return new PDDeviceN(array);
            }
            if (name2 == COSName.INDEXED) {
                return new PDIndexed(array);
            }
            if (name2 == COSName.SEPARATION) {
                return new PDSeparation(array);
            }
            if (name2 == COSName.ICCBASED) {
                return PDICCBased.create(array, resources);
            }
            if (name2 == COSName.LAB) {
                return new PDLab(array);
            }
            if (name2 == COSName.PATTERN) {
                if (array.size() == 1) {
                    return new PDPattern(resources);
                }
                return new PDPattern(resources, create(array.get(1)));
            }
            if (name2 == COSName.DEVICECMYK || name2 == COSName.DEVICERGB || name2 == COSName.DEVICEGRAY) {
                return createUncached(name2, resources, wasDefault, recursionAccumulator + 1);
            }
            throw new IOException("Invalid color space kind: " + name2);
        }
        if (colorSpace2 instanceof COSDictionary) {
            COSDictionary csAsDic = (COSDictionary) colorSpace2;
            if (csAsDic.containsKey(COSName.COLORSPACE)) {
                LOG.warn("Found invalid color space defined as dictionary {}", csAsDic);
                return createUncached(csAsDic.getDictionaryObject(COSName.COLORSPACE), resources, wasDefault, recursionAccumulator + 1);
            }
        }
        throw new IOException("Expected a name or array but got: " + colorSpace2);
    }

    protected final BufferedImage toRawImage(WritableRaster raster, ColorSpace awtColorSpace) {
        return new BufferedImage(new ComponentColorModel(awtColorSpace, false, false, 1, raster.getDataBuffer().getDataType()), raster, false, (Hashtable) null);
    }

    protected BufferedImage toRGBImageAWT(WritableRaster raster, ColorSpace colorSpace) {
        BufferedImage src = new BufferedImage(new ComponentColorModel(colorSpace, false, false, 1, raster.getDataBuffer().getDataType()), raster, false, (Hashtable) null);
        BufferedImage dest = new BufferedImage(raster.getWidth(), raster.getHeight(), 1);
        if (src.getWidth() == 1 || src.getHeight() == 1) {
            Graphics g2d = dest.getGraphics();
            g2d.drawImage(src, 0, 0, (ImageObserver) null);
            g2d.dispose();
            return dest;
        }
        this.colorConvertOp.filter(src, dest);
        return dest;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.array;
    }
}
