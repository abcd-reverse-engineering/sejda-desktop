package org.sejda.sambox.pdmodel.graphics.pattern;

import java.io.IOException;
import java.io.InputStream;
import org.sejda.sambox.contentstream.PDContentStream;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.ResourceCache;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/pattern/PDTilingPattern.class */
public class PDTilingPattern extends PDAbstractPattern implements PDContentStream {
    public static final int PAINT_COLORED = 1;
    public static final int PAINT_UNCOLORED = 2;
    public static final int TILING_CONSTANT_SPACING = 1;
    public static final int TILING_NO_DISTORTION = 2;
    public static final int TILING_CONSTANT_SPACING_FASTER_TILING = 3;
    private final ResourceCache resourceCache;

    public PDTilingPattern() {
        super(new COSStream());
        getCOSObject().setName(COSName.TYPE, COSName.PATTERN.getName());
        getCOSObject().setInt(COSName.PATTERN_TYPE, 1);
        setResources(new PDResources());
        this.resourceCache = null;
    }

    public PDTilingPattern(COSDictionary resourceDictionary) {
        this(resourceDictionary, null);
    }

    public PDTilingPattern(COSDictionary dictionary, ResourceCache resourceCache) {
        super(dictionary);
        this.resourceCache = resourceCache;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.pattern.PDAbstractPattern
    public int getPatternType() {
        return 1;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.pattern.PDAbstractPattern
    public void setPaintType(int paintType) {
        getCOSObject().setInt(COSName.PAINT_TYPE, paintType);
    }

    public int getPaintType() {
        return getCOSObject().getInt(COSName.PAINT_TYPE, 0);
    }

    public void setTilingType(int tilingType) {
        getCOSObject().setInt(COSName.TILING_TYPE, tilingType);
    }

    public int getTilingType() {
        return getCOSObject().getInt(COSName.TILING_TYPE, 0);
    }

    public void setXStep(float xStep) {
        getCOSObject().setFloat(COSName.X_STEP, xStep);
    }

    public float getXStep() {
        return getCOSObject().getFloat(COSName.X_STEP, 0.0f);
    }

    public void setYStep(float yStep) {
        getCOSObject().setFloat(COSName.Y_STEP, yStep);
    }

    public float getYStep() {
        return getCOSObject().getFloat(COSName.Y_STEP, 0.0f);
    }

    public PDStream getContentStream() {
        return new PDStream((COSStream) getCOSObject());
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public InputStream getContents() throws IOException {
        COSDictionary dict = getCOSObject();
        if (dict instanceof COSStream) {
            return ((COSStream) getCOSObject()).getUnfilteredStream();
        }
        return null;
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public PDResources getResources() {
        COSDictionary resources = (COSDictionary) getCOSObject().getDictionaryObject(COSName.RESOURCES, COSDictionary.class);
        if (resources != null) {
            return new PDResources(resources);
        }
        return null;
    }

    public void setResources(PDResources resources) {
        getCOSObject().setItem(COSName.RESOURCES, resources);
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public PDRectangle getBBox() {
        COSArray array = (COSArray) getCOSObject().getDictionaryObject(COSName.BBOX, COSArray.class);
        if (array != null) {
            return new PDRectangle(array);
        }
        return null;
    }

    public void setBBox(PDRectangle bbox) {
        if (bbox == null) {
            getCOSObject().removeItem(COSName.BBOX);
        } else {
            getCOSObject().setItem(COSName.BBOX, (COSBase) bbox.getCOSObject());
        }
    }
}
