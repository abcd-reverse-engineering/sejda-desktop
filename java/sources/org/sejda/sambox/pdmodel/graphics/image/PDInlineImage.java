package org.sejda.sambox.pdmodel.graphics.image;

import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.filter.DecodeResult;
import org.sejda.sambox.filter.Filter;
import org.sejda.sambox.filter.FilterFactory;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/image/PDInlineImage.class */
public final class PDInlineImage implements PDImage {
    private final COSDictionary parameters;
    private final PDResources resources;
    private final byte[] decodedData;

    public PDInlineImage(COSDictionary parameters, byte[] data, PDResources resources) throws IOException {
        this.parameters = parameters;
        this.resources = resources;
        DecodeResult decodeResult = null;
        List<String> filters = getFilters();
        if (filters == null || filters.isEmpty()) {
            this.decodedData = data;
        } else {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            FastByteArrayOutputStream out = new FastByteArrayOutputStream(data.length);
            for (int i = 0; i < filters.size(); i++) {
                out.reset();
                Filter filter = FilterFactory.INSTANCE.getFilter(filters.get(i));
                decodeResult = filter.decode(in, out, parameters, i);
                in = new ByteArrayInputStream(out.toByteArray());
            }
            this.decodedData = out.toByteArray();
        }
        if (decodeResult != null) {
            parameters.addAll(decodeResult.getParameters());
        }
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.parameters;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public int getBitsPerComponent() {
        if (isStencil()) {
            return 1;
        }
        return this.parameters.getInt(COSName.BPC, COSName.BITS_PER_COMPONENT, -1);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setBitsPerComponent(int bitsPerComponent) {
        this.parameters.setInt(COSName.BPC, bitsPerComponent);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public PDColorSpace getColorSpace() throws IOException {
        COSBase cs = this.parameters.getDictionaryObject(COSName.CS, COSName.COLORSPACE);
        if (cs != null) {
            return createColorSpace(cs);
        }
        if (isStencil()) {
            return PDDeviceGray.INSTANCE;
        }
        throw new IOException("could not determine inline image color space");
    }

    private COSBase toLongName(COSBase cs) {
        if (COSName.RGB.equals(cs)) {
            return COSName.DEVICERGB;
        }
        if (COSName.CMYK.equals(cs)) {
            return COSName.DEVICECMYK;
        }
        if (COSName.G.equals(cs)) {
            return COSName.DEVICEGRAY;
        }
        return cs;
    }

    private PDColorSpace createColorSpace(COSBase cs) throws IOException {
        if (cs instanceof COSName) {
            return PDColorSpace.create(toLongName(cs), this.resources);
        }
        if (cs instanceof COSArray) {
            COSArray srcArray = (COSArray) cs;
            if (((COSArray) cs).size() > 1) {
                COSBase csType = srcArray.get(0);
                if (COSName.I.equals(csType) || COSName.INDEXED.equals(csType)) {
                    COSArray dstArray = new COSArray();
                    dstArray.addAll(srcArray);
                    dstArray.set(0, (COSBase) COSName.INDEXED);
                    dstArray.set(1, toLongName(srcArray.get(1)));
                    return PDColorSpace.create(dstArray, this.resources);
                }
                throw new IOException("Illegal type of inline image color space: " + csType);
            }
        }
        throw new IOException("Illegal type of object for inline image color space: " + cs);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setColorSpace(PDColorSpace colorSpace) {
        COSBase base = null;
        if (colorSpace != null) {
            base = colorSpace.getCOSObject();
        }
        this.parameters.setItem(COSName.CS, base);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public int getHeight() {
        return this.parameters.getInt(COSName.H, COSName.HEIGHT, -1);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setHeight(int height) {
        this.parameters.setInt(COSName.H, height);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public int getWidth() {
        return this.parameters.getInt(COSName.W, COSName.WIDTH, -1);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setWidth(int width) {
        this.parameters.setInt(COSName.W, width);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public boolean getInterpolate() {
        return this.parameters.getBoolean(COSName.I, COSName.INTERPOLATE, false);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setInterpolate(boolean value) {
        this.parameters.setBoolean(COSName.I, value);
    }

    public List<String> getFilters() {
        List<String> names = null;
        COSBase filters = this.parameters.getDictionaryObject(COSName.F, COSName.FILTER);
        if (filters instanceof COSName) {
            COSName name = (COSName) filters;
            names = new COSArrayList(name.getName(), name, this.parameters, COSName.FILTER);
        } else if (filters instanceof COSArray) {
            names = COSArrayList.convertCOSNameCOSArrayToList((COSArray) filters);
        }
        return names;
    }

    public void setFilters(List<String> filters) {
        COSBase obj = COSArrayList.convertStringListToCOSNameCOSArray(filters);
        this.parameters.setItem(COSName.F, obj);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setDecode(COSArray decode) {
        this.parameters.setItem(COSName.D, (COSBase) decode);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public COSArray getDecode() {
        return (COSArray) this.parameters.getDictionaryObject(COSName.D, COSName.DECODE, COSArray.class);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public boolean isStencil() {
        return this.parameters.getBoolean(COSName.IM, COSName.IMAGE_MASK, false);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setStencil(boolean isStencil) {
        this.parameters.setBoolean(COSName.IM, isStencil);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public InputStream createInputStream() {
        return new ByteArrayInputStream(this.decodedData);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public ByteBuffer asByteBuffer() {
        return ByteBuffer.wrap(this.decodedData).asReadOnlyBuffer();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public boolean isEmpty() {
        return this.decodedData.length == 0;
    }

    public byte[] getData() {
        return this.decodedData;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public WritableRaster getRawRaster() throws IOException {
        return SampledImageReader.getRawRaster(this);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public BufferedImage getRawImage() throws IOException {
        return getColorSpace().toRawImage(getRawRaster());
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public BufferedImage getImage() throws IOException {
        return SampledImageReader.getRGBImage(this, null);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public BufferedImage getStencilImage(Paint paint) throws IOException {
        if (!isStencil()) {
            throw new IllegalStateException("Image is not a stencil");
        }
        return SampledImageReader.getStencilImage(this, paint);
    }
}
