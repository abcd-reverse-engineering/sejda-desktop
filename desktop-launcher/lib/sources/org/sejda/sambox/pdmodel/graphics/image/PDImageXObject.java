package org.sejda.sambox.pdmodel.graphics.image;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.ImageObserver;
import java.awt.image.ImagingOpException;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.filter.DecodeResult;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDMetadata;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.sejda.sambox.pdmodel.graphics.color.PDJPXColorSpace;
import org.sejda.sambox.util.filetypedetector.FileType;
import org.sejda.sambox.util.filetypedetector.FileTypeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/image/PDImageXObject.class */
public final class PDImageXObject extends PDXObject implements PDImage {
    private static final Logger LOG = LoggerFactory.getLogger(PDImageXObject.class);
    private SoftReference<BufferedImage> cachedImage;
    private PDColorSpace colorSpace;
    private PDResources resources;

    public static PDImageXObject createThumbnail(COSStream cosStream) throws IOException {
        PDStream pdStream = new PDStream(cosStream);
        return new PDImageXObject(pdStream, null);
    }

    public PDImageXObject() throws IOException {
        this(new PDStream(), null);
    }

    public PDImageXObject(InputStream encodedStream, COSBase cosFilter, int width, int height, int bitsPerComponent, PDColorSpace initColorSpace) throws IOException {
        super(createRawStream(encodedStream), COSName.IMAGE);
        getCOSObject().setItem(COSName.FILTER, cosFilter);
        this.resources = null;
        this.colorSpace = null;
        setBitsPerComponent(bitsPerComponent);
        setWidth(width);
        setHeight(height);
        setColorSpace(initColorSpace);
    }

    private static COSStream createRawStream(InputStream rawInput) throws IOException {
        COSStream stream = new COSStream();
        OutputStream output = stream.createFilteredStream();
        try {
            IOUtils.copy(rawInput, output);
            if (output != null) {
                output.close();
            }
            return stream;
        } catch (Throwable th) {
            if (output != null) {
                try {
                    output.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public PDImageXObject(PDStream stream, PDResources resources) throws IOException {
        super(stream, COSName.IMAGE);
        this.resources = resources;
        List<COSName> filters = stream.getFilters();
        if (filters != null && !filters.isEmpty() && COSName.JPX_DECODE.equals(filters.get(filters.size() - 1))) {
            DecodeResult decodeResult = stream.getCOSObject().getDecodeResult();
            stream.getCOSObject().addAll(decodeResult.getParameters());
            this.colorSpace = decodeResult.getJPXColorSpace();
        }
    }

    public static PDImageXObject createFromFile(String imagePath) throws IOException {
        return createFromFile(new File(imagePath));
    }

    public static PDImageXObject createFromFile(File file) throws IOException {
        RequireUtils.requireNotNullArg(file, "Cannot create image from a null file");
        SeekableSource source = SeekableSources.seekableSourceFrom(file);
        try {
            PDImageXObject pDImageXObjectCreateFromSeekableSource = createFromSeekableSource(source, file.getName());
            if (source != null) {
                source.close();
            }
            return pDImageXObjectCreateFromSeekableSource;
        } catch (Throwable th) {
            if (source != null) {
                try {
                    source.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static PDImageXObject createFromSeekableSource(SeekableSource source, String filename) throws IOException {
        return createFromSeekableSource(source, filename, 0);
    }

    public static BufferedImage read(InputStream inputStream, int number) throws IOException {
        if (number == 0) {
            return ImageIO.read(inputStream);
        }
        BufferedImage image = null;
        ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        if (readers.hasNext()) {
            ImageReader reader = readers.next();
            reader.setInput(iis);
            try {
                image = reader.read(number);
                reader.dispose();
                iis.close();
            } catch (Throwable th) {
                reader.dispose();
                iis.close();
                throw th;
            }
        }
        return image;
    }

    public static PDImageXObject createFromSeekableSource(SeekableSource source, String filename, int number) throws IOException {
        FileType fileType = FileTypeDetector.detectFileType(source);
        if (fileType.equals(FileType.JPEG)) {
            return JPEGFactory.createFromSeekableSource(source);
        }
        if (fileType.equals(FileType.TIFF)) {
            try {
                return CCITTFactory.createFromSeekableSource(source, number);
            } catch (IOException ex) {
                LOG.warn("Reading as TIFF failed using CCITTFactory, falling back to ImageIO: {}", ex.getMessage());
            }
        }
        try {
            BufferedImage image = read(source.asNewInputStream(), number);
            if (image == null) {
                LOG.warn(String.format("Could not read image format: %s type: %s", filename, fileType));
                throw new UnsupportedImageFormatException(fileType, filename, null);
            }
            return LosslessFactory.createFromImage(image);
        } catch (Exception e) {
            LOG.warn(String.format("An error occurred while reading image: %s type: %s", filename, fileType), e);
            throw new UnsupportedImageFormatException(fileType, filename, e);
        }
    }

    public PDMetadata getMetadata() {
        COSStream cosStream = (COSStream) getCOSObject().getDictionaryObject(COSName.METADATA, COSStream.class);
        if (cosStream != null) {
            return new PDMetadata(cosStream);
        }
        return null;
    }

    public void setMetadata(PDMetadata meta) {
        getCOSObject().setItem(COSName.METADATA, meta);
    }

    public int getStructParent() {
        return getCOSObject().getInt(COSName.STRUCT_PARENT);
    }

    public void setStructParent(int key) {
        getCOSObject().setInt(COSName.STRUCT_PARENT, key);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public BufferedImage getImage() throws IOException {
        BufferedImage image;
        BufferedImage cached;
        if (this.cachedImage != null && (cached = this.cachedImage.get()) != null) {
            return cached;
        }
        PDImageXObject softMask = getSoftMask();
        PDImageXObject mask = getMask();
        if (softMask != null) {
            image = applyMask(SampledImageReader.getRGBImage(this, getColorKeyMask()), softMask.getOpaqueImage(), softMask.getInterpolate(), true, extractMatte(softMask));
        } else if (mask != null && mask.isStencil()) {
            image = applyMask(SampledImageReader.getRGBImage(this, getColorKeyMask()), mask.getOpaqueImage(), mask.getInterpolate(), false, null);
        } else {
            image = SampledImageReader.getRGBImage(this, getColorKeyMask());
        }
        this.cachedImage = new SoftReference<>(image);
        return image;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public BufferedImage getRawImage() throws IOException {
        return getColorSpace().toRawImage(getRawRaster());
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public WritableRaster getRawRaster() throws IOException {
        return SampledImageReader.getRawRaster(this);
    }

    private float[] extractMatte(PDImageXObject softMask) throws IOException {
        float[] matte = (float[]) Optional.ofNullable((COSArray) softMask.getCOSObject().getDictionaryObject(COSName.MATTE, COSArray.class)).map((v0) -> {
            return v0.toFloatArray();
        }).orElse(null);
        if (Objects.nonNull(matte)) {
            PDColorSpace colorSpace = getColorSpace();
            if (colorSpace instanceof PDJPXColorSpace) {
                colorSpace = PDDeviceRGB.INSTANCE;
            }
            if (matte.length < colorSpace.getNumberOfComponents()) {
                LOG.error("Image /Matte entry not long enough for colorspace, skipped");
                return null;
            }
            return colorSpace.toRGB(matte);
        }
        return null;
    }

    public BufferedImage getImageWithoutMasks() throws IOException {
        return SampledImageReader.getRGBImage(this, getColorKeyMask());
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public BufferedImage getStencilImage(Paint paint) throws IOException {
        if (!isStencil()) {
            throw new IllegalStateException("Image is not a stencil");
        }
        return SampledImageReader.getStencilImage(this, paint);
    }

    public BufferedImage getOpaqueImage() throws IOException {
        return SampledImageReader.getRGBImage(this, null);
    }

    private BufferedImage applyMask(BufferedImage image, BufferedImage mask, boolean interpolateMask, boolean isSoft, float[] matte) {
        int offset;
        if (mask == null) {
            return image;
        }
        int width = Math.max(image.getWidth(), mask.getWidth());
        int height = Math.max(image.getHeight(), mask.getHeight());
        if (mask.getWidth() < width || mask.getHeight() < height) {
            mask = scaleImage(mask, width, height, 10, interpolateMask);
        } else if (mask.getType() != 10) {
            mask = scaleImage(mask, width, height, 10, false);
        }
        if (image.getWidth() < width || image.getHeight() < height) {
            image = scaleImage(image, width, height, 2, getInterpolate());
        } else if (image.getType() != 2) {
            image = scaleImage(image, width, height, 2, false);
        }
        WritableRaster raster = image.getRaster();
        WritableRaster alpha = mask.getRaster();
        if (!isSoft && raster.getDataBuffer().getSize() == alpha.getDataBuffer().getSize()) {
            DataBuffer dst = raster.getDataBuffer();
            DataBuffer src = alpha.getDataBuffer();
            int i = 0;
            for (int c = dst.getSize(); c > 0; c--) {
                dst.setElem(i, (dst.getElem(i) & 16777215) | ((src.getElem(i) ^ (-1)) << 24));
                i++;
            }
        } else if (matte == null) {
            int[] samples = new int[width];
            for (int y = 0; y < height; y++) {
                alpha.getSamples(0, y, width, 1, 0, samples);
                if (!isSoft) {
                    for (int x = 0; x < width; x++) {
                        int i2 = x;
                        samples[i2] = samples[i2] ^ (-1);
                    }
                }
                raster.setSamples(0, y, width, 1, 3, samples);
            }
        } else {
            int[] alphas = new int[width];
            int[] pixels = new int[4 * width];
            int m0 = Math.round(8355840.0f * matte[0]) * 255;
            int m1 = Math.round(8355840.0f * matte[1]) * 255;
            int m2 = Math.round(8355840.0f * matte[2]) * 255;
            int m0h = (m0 / 255) + 16384;
            int m1h = (m1 / 255) + 16384;
            int m2h = (m2 / 255) + 16384;
            for (int y2 = 0; y2 < height; y2++) {
                raster.getPixels(0, y2, width, 1, pixels);
                alpha.getSamples(0, y2, width, 1, 0, alphas);
                int offset2 = 0;
                for (int x2 = 0; x2 < width; x2++) {
                    int a = alphas[x2];
                    if (a == 0) {
                        offset = offset2 + 3;
                    } else {
                        int i3 = offset2;
                        int i4 = offset2;
                        int offset3 = offset2 + 1;
                        pixels[i3] = clampColor(((((pixels[i4] * 8355840) - m0) / a) + m0h) >> 15);
                        int offset4 = offset3 + 1;
                        pixels[offset3] = clampColor(((((pixels[offset3] * 8355840) - m1) / a) + m1h) >> 15);
                        offset = offset4 + 1;
                        pixels[offset4] = clampColor(((((pixels[offset4] * 8355840) - m2) / a) + m2h) >> 15);
                    }
                    int i5 = offset;
                    offset2 = offset + 1;
                    pixels[i5] = a;
                }
                raster.setPixels(0, y2, width, 1, pixels);
            }
        }
        return image;
    }

    private int clampColor(float color) {
        if (color < 0.0f) {
            return 0;
        }
        if (color > 255.0f) {
            return 255;
        }
        return Math.round(color);
    }

    private static BufferedImage scaleImage(BufferedImage image, int width, int height, int type, boolean interpolate) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        boolean largeScale = width * height > 9000000 * (type == 10 ? 3 : 1);
        boolean interpolate2 = interpolate & ((imgWidth == width && imgHeight == height) ? false : true);
        BufferedImage image2 = new BufferedImage(width, height, type);
        if (interpolate2) {
            AffineTransform af = AffineTransform.getScaleInstance(width / imgWidth, height / imgHeight);
            AffineTransformOp afo = new AffineTransformOp(af, largeScale ? 2 : 3);
            try {
                afo.filter(image, image2);
                return image2;
            } catch (ImagingOpException e) {
                LOG.warn(e.getMessage(), e);
            }
        }
        Graphics2D g = image2.createGraphics();
        if (interpolate2) {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, largeScale ? RenderingHints.VALUE_INTERPOLATION_BILINEAR : RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, largeScale ? RenderingHints.VALUE_RENDER_DEFAULT : RenderingHints.VALUE_RENDER_QUALITY);
        }
        g.drawImage(image, 0, 0, width, height, 0, 0, imgWidth, imgHeight, (ImageObserver) null);
        g.dispose();
        return image2;
    }

    public boolean hasMask() {
        COSStream cosStream = (COSStream) getCOSObject().getDictionaryObject(COSName.MASK, COSStream.class);
        return cosStream != null;
    }

    public PDImageXObject getMask() throws IOException {
        COSStream cosStream = (COSStream) getCOSObject().getDictionaryObject(COSName.MASK, COSStream.class);
        if (cosStream != null) {
            return new PDImageXObject(new PDStream(cosStream), null);
        }
        return null;
    }

    public COSArray getColorKeyMask() {
        return (COSArray) getCOSObject().getDictionaryObject(COSName.MASK, COSArray.class);
    }

    public boolean hasSoftMask() {
        COSStream cosStream = (COSStream) getCOSObject().getDictionaryObject(COSName.SMASK, COSStream.class);
        return cosStream != null;
    }

    public PDImageXObject getSoftMask() throws IOException {
        COSStream cosStream = (COSStream) getCOSObject().getDictionaryObject(COSName.SMASK, COSStream.class);
        if (cosStream != null) {
            return new PDImageXObject(new PDStream(cosStream), null);
        }
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public int getBitsPerComponent() {
        if (isStencil()) {
            return 1;
        }
        return getCOSObject().getInt(COSName.BITS_PER_COMPONENT, COSName.BPC);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setBitsPerComponent(int bpc) {
        getCOSObject().setInt(COSName.BITS_PER_COMPONENT, bpc);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public PDColorSpace getColorSpace() throws IOException {
        if (this.colorSpace == null) {
            COSBase cosBase = getCOSObject().getDictionaryObject(COSName.COLORSPACE, COSName.CS);
            if (cosBase != null) {
                this.colorSpace = PDColorSpace.create(cosBase, this.resources);
            } else {
                if (isStencil()) {
                    return PDDeviceGray.INSTANCE;
                }
                throw new IOException("could not determine color space");
            }
        }
        return this.colorSpace;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public InputStream createInputStream() throws IOException {
        return getStream().createInputStream();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public ByteBuffer asByteBuffer() throws IOException {
        return getStream().getCOSObject().getUnfilteredByteBuffer();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public boolean isEmpty() throws IOException {
        return getStream().getCOSObject().isEmpty();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setColorSpace(PDColorSpace cs) {
        getCOSObject().setItem(COSName.COLORSPACE, cs != null ? cs.getCOSObject() : null);
        this.colorSpace = null;
        this.cachedImage = null;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public int getHeight() {
        return getCOSObject().getInt(COSName.HEIGHT);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setHeight(int h) {
        getCOSObject().setInt(COSName.HEIGHT, h);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public int getWidth() {
        return getCOSObject().getInt(COSName.WIDTH);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setWidth(int w) {
        getCOSObject().setInt(COSName.WIDTH, w);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public boolean getInterpolate() {
        return getCOSObject().getBoolean(COSName.INTERPOLATE, false);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setInterpolate(boolean value) {
        getCOSObject().setBoolean(COSName.INTERPOLATE, value);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setDecode(COSArray decode) {
        getCOSObject().setItem(COSName.DECODE, (COSBase) decode);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public COSArray getDecode() {
        COSBase decode = getCOSObject().getDictionaryObject(COSName.DECODE);
        if (decode instanceof COSArray) {
            return (COSArray) decode;
        }
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public boolean isStencil() {
        return getCOSObject().getBoolean(COSName.IMAGE_MASK, false);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.image.PDImage
    public void setStencil(boolean isStencil) {
        getCOSObject().setBoolean(COSName.IMAGE_MASK, isStencil);
    }

    public PDPropertyList getOptionalContent() {
        return (PDPropertyList) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(COSName.OC, COSDictionary.class)).map(PDPropertyList::create).orElse(null);
    }

    public void setOptionalContent(PDPropertyList oc) {
        getCOSObject().setItem(COSName.OC, oc);
    }
}
