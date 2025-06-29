package org.sejda.sambox.pdmodel.graphics.color;

import java.awt.color.CMMException;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.color.ProfileDataException;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRange;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDICCBased.class */
public final class PDICCBased extends PDCIEBasedColorSpace {
    private static final Logger LOG = LoggerFactory.getLogger(PDICCBased.class);
    private final PDStream stream;
    private ICC_Profile iccProfile;
    private PDColorSpace alternateColorSpace;
    private ICC_ColorSpace awtColorSpace;
    private PDColor initialColor;
    private static final boolean IS_KCMS;
    private int numberOfComponents = -1;
    private boolean isRGB = false;
    private boolean useOnlyAlternateColorSpace = Boolean.getBoolean("org.sejda.sambox.rendering.UseAlternateInsteadOfICCColorSpace");

    static {
        String cmmProperty = System.getProperty("sun.java2d.cmm");
        boolean result = false;
        if (!isMinJdk8()) {
            result = true;
        } else if ("sun.java2d.cmm.kcms.KcmsServiceProvider".equals(cmmProperty)) {
            try {
                Class.forName("sun.java2d.cmm.kcms.KcmsServiceProvider");
                result = true;
            } catch (ClassNotFoundException e) {
            }
        }
        IS_KCMS = result;
    }

    public PDICCBased() {
        this.array = new COSArray();
        this.array.add((COSBase) COSName.ICCBASED);
        this.stream = new PDStream();
        this.array.add((COSObjectable) this.stream);
    }

    @Deprecated
    public PDICCBased(COSArray iccArray) throws IOException {
        RequireUtils.requireIOCondition(iccArray.size() >= 2, "ICCBased colorspace array must have two elements");
        RequireUtils.requireIOCondition(iccArray.getObject(1) instanceof COSStream, "ICCBased colorspace array must have a stream as second element");
        this.array = iccArray;
        this.stream = new PDStream((COSStream) iccArray.getObject(1));
        loadICCProfile();
    }

    public static PDICCBased create(COSArray iccArray, PDResources resources) throws IOException {
        RequireUtils.requireIOCondition(iccArray.size() >= 2, "ICCBased colorspace array must have two elements");
        RequireUtils.requireIOCondition(iccArray.getObject(1) instanceof COSStream, "ICCBased colorspace array must have a stream as second element");
        COSBase base = iccArray.get(1);
        boolean canCache = (!base.hasId() || resources == null || resources.getResourceCache() == null) ? false : true;
        if (canCache) {
            PDColorSpace space = resources.getResourceCache().getColorSpace(base.id().objectIdentifier);
            if (space instanceof PDICCBased) {
                return (PDICCBased) space;
            }
        }
        PDICCBased space2 = new PDICCBased(iccArray);
        if (canCache) {
            resources.getResourceCache().put(base.id().objectIdentifier, space2);
        }
        return space2;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public String getName() {
        return COSName.ICCBASED.getName();
    }

    public PDStream getPDStream() {
        return this.stream;
    }

    private void loadICCProfile() throws IOException {
        if (this.useOnlyAlternateColorSpace) {
            try {
                fallbackToAlternateColorSpace(null);
                return;
            } catch (IOException e) {
                LOG.warn("Error initializing alternate color space: " + e.getLocalizedMessage());
            }
        }
        try {
            try {
                InputStream input = this.stream.createInputStream();
                synchronized (LOG) {
                    ICC_Profile profile = ICC_Profile.getInstance(input);
                    if (is_sRGB(profile)) {
                        this.isRGB = true;
                        this.awtColorSpace = ColorSpace.getInstance(1000);
                        this.iccProfile = this.awtColorSpace.getProfile();
                    } else {
                        ICC_Profile profile2 = ensureDisplayProfile(profile);
                        this.awtColorSpace = new ICC_ColorSpace(profile2);
                        this.iccProfile = profile2;
                    }
                    float[] initial = new float[getNumberOfComponents()];
                    for (int c = 0; c < initial.length; c++) {
                        initial[c] = Math.max(0.0f, getRangeForComponent(c).getMin());
                    }
                    this.initialColor = new PDColor(initial, this);
                    this.awtColorSpace.toRGB(new float[getNumberOfComponents()]);
                    if (!IS_KCMS) {
                        new ComponentColorModel(this.awtColorSpace, false, false, 1, 0);
                    }
                }
                IOUtils.closeQuietly(input);
            } catch (ProfileDataException | IOException | ArrayIndexOutOfBoundsException | IllegalArgumentException | CMMException e2) {
                fallbackToAlternateColorSpace(e2);
                IOUtils.closeQuietly(null);
            }
        } catch (Throwable th) {
            IOUtils.closeQuietly(null);
            throw th;
        }
    }

    private void fallbackToAlternateColorSpace(Exception e) throws IOException {
        this.awtColorSpace = null;
        this.alternateColorSpace = getAlternateColorSpace();
        if (this.alternateColorSpace.equals(PDDeviceRGB.INSTANCE)) {
            this.isRGB = true;
        }
        if (e != null) {
            LOG.warn("Can't read embedded ICC profile (" + e.getLocalizedMessage() + "), using alternate color space: " + this.alternateColorSpace.getName());
        }
        this.initialColor = this.alternateColorSpace.getInitialColor();
    }

    private boolean is_sRGB(ICC_Profile profile) {
        byte[] bytes = Arrays.copyOfRange(profile.getData(1751474532), 52, 59);
        String deviceModel = new String(bytes, StandardCharsets.US_ASCII).trim();
        return deviceModel.equals("sRGB");
    }

    private static ICC_Profile ensureDisplayProfile(ICC_Profile profile) {
        if (profile.getProfileClass() != 1) {
            byte[] profileData = profile.getData();
            if (profileData[64] == 0) {
                LOG.debug("ICC profile is Perceptual, ignoring, treating as Display class");
                intToBigEndian(1835955314, profileData, 12);
                return ICC_Profile.getInstance(profileData);
            }
        }
        return profile;
    }

    private static void intToBigEndian(int value, byte[] array, int index) {
        array[index] = (byte) (value >> 24);
        array[index + 1] = (byte) (value >> 16);
        array[index + 2] = (byte) (value >> 8);
        array[index + 3] = (byte) value;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] toRGB(float[] value) throws IOException {
        if (this.isRGB) {
            return value;
        }
        if (this.awtColorSpace != null) {
            return this.awtColorSpace.toRGB(clampColors(this.awtColorSpace, value));
        }
        return this.alternateColorSpace.toRGB(value);
    }

    private float[] clampColors(ICC_ColorSpace cs, float[] value) {
        float[] result = new float[value.length];
        for (int i = 0; i < value.length; i++) {
            float minValue = cs.getMinValue(i);
            float maxValue = cs.getMaxValue(i);
            result[i] = value[i] < minValue ? minValue : value[i] > maxValue ? maxValue : value[i];
        }
        return result;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDCIEBasedColorSpace, org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public BufferedImage toRGBImage(WritableRaster raster) throws IOException {
        if (this.awtColorSpace != null) {
            try {
                return toRGBImageAWT(raster, this.awtColorSpace);
            } catch (CMMException e) {
                LOG.error("Failure using embedded ICC profile ({}), using alternate color space: {}", e.getMessage(), this.alternateColorSpace);
            }
        }
        return this.alternateColorSpace.toRGBImage(raster);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDCIEBasedColorSpace, org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public BufferedImage toRawImage(WritableRaster raster) throws IOException {
        if (this.awtColorSpace == null) {
            return this.alternateColorSpace.toRawImage(raster);
        }
        return toRawImage(raster, this.awtColorSpace);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public int getNumberOfComponents() {
        int numIccComponents;
        if (this.numberOfComponents < 0) {
            this.numberOfComponents = this.stream.getCOSObject().getInt(COSName.N);
            if (this.iccProfile != null && (numIccComponents = this.iccProfile.getNumComponents()) != this.numberOfComponents) {
                LOG.warn("Using {} components from ICC profile info instead of {} components from /N entry", Integer.valueOf(numIccComponents), Integer.valueOf(this.numberOfComponents));
                this.numberOfComponents = numIccComponents;
            }
        }
        return this.numberOfComponents;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public float[] getDefaultDecode(int bitsPerComponent) {
        if (this.awtColorSpace != null) {
            int n = getNumberOfComponents();
            float[] decode = new float[n * 2];
            for (int i = 0; i < n; i++) {
                decode[i * 2] = this.awtColorSpace.getMinValue(i);
                decode[(i * 2) + 1] = this.awtColorSpace.getMaxValue(i);
            }
            return decode;
        }
        return this.alternateColorSpace.getDefaultDecode(bitsPerComponent);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace
    public PDColor getInitialColor() {
        return this.initialColor;
    }

    public PDColorSpace getAlternateColorSpace() throws IOException {
        COSArray alternateArray;
        COSName csName;
        COSBase alternate = this.stream.getCOSObject().getDictionaryObject(COSName.ALTERNATE);
        if (alternate == null) {
            alternateArray = new COSArray();
            int numComponents = getNumberOfComponents();
            switch (numComponents) {
                case 1:
                    csName = COSName.DEVICEGRAY;
                    break;
                case 2:
                default:
                    throw new IOException("Unknown color space number of components:" + numComponents);
                case 3:
                    csName = COSName.DEVICERGB;
                    break;
                case 4:
                    csName = COSName.DEVICECMYK;
                    break;
            }
            alternateArray.add((COSBase) csName);
        } else if (alternate instanceof COSArray) {
            alternateArray = (COSArray) alternate;
        } else if (alternate instanceof COSName) {
            alternateArray = new COSArray();
            alternateArray.add(alternate);
        } else {
            throw new IOException("Error: expected COSArray or COSName and not " + alternate.getClass().getName());
        }
        return PDColorSpace.create(alternateArray);
    }

    public PDRange getRangeForComponent(int n) {
        COSArray rangeArray = (COSArray) this.stream.getCOSObject().getDictionaryObject(COSName.RANGE);
        if (rangeArray == null || rangeArray.size() < getNumberOfComponents() * 2) {
            return new PDRange();
        }
        return new PDRange(rangeArray, n);
    }

    public COSStream getMetadata() {
        return (COSStream) this.stream.getCOSObject().getDictionaryObject(COSName.METADATA, COSStream.class);
    }

    public int getColorSpaceType() {
        if (this.iccProfile != null) {
            return this.iccProfile.getColorSpaceType();
        }
        switch (this.alternateColorSpace.getNumberOfComponents()) {
            case 1:
                return 6;
            case 2:
            default:
                return -1;
            case 3:
                return 5;
            case 4:
                return 9;
        }
    }

    @Deprecated
    public void setNumberOfComponents(int n) {
        this.numberOfComponents = n;
        this.stream.getCOSObject().setInt(COSName.N, n);
    }

    public void setAlternateColorSpaces(List<PDColorSpace> list) {
        COSArray altArray = null;
        if (list != null) {
            altArray = COSArrayList.converterToCOSArray(list);
        }
        this.stream.getCOSObject().setItem(COSName.ALTERNATE, (COSBase) altArray);
    }

    public void setRangeForComponent(PDRange range, int n) {
        COSArray rangeArray = (COSArray) this.stream.getCOSObject().getDictionaryObject(COSName.RANGE);
        if (rangeArray == null) {
            rangeArray = new COSArray();
            this.stream.getCOSObject().setItem(COSName.RANGE, (COSBase) rangeArray);
        }
        while (rangeArray.size() < (n + 1) * 2) {
            rangeArray.add((COSBase) new COSFloat(0.0f));
            rangeArray.add((COSBase) new COSFloat(1.0f));
        }
        rangeArray.set(n * 2, (COSBase) new COSFloat(range.getMin()));
        rangeArray.set((n * 2) + 1, (COSBase) new COSFloat(range.getMax()));
    }

    public void setMetadata(COSStream metadata) {
        this.stream.getCOSObject().setItem(COSName.METADATA, (COSBase) metadata);
    }

    boolean isSRGB() {
        return this.isRGB;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDCIEBasedColorSpace
    public String toString() {
        return getName() + "{numberOfComponents: " + getNumberOfComponents() + "}";
    }

    private static boolean isMinJdk8() throws NumberFormatException {
        String version = System.getProperty("java.specification.version");
        StringTokenizer st = new StringTokenizer(version, ".");
        try {
            int major = Integer.parseInt(st.nextToken());
            int minor = 0;
            if (st.hasMoreTokens()) {
                minor = Integer.parseInt(st.nextToken());
            }
            return major > 1 || (major == 1 && minor >= 8);
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
