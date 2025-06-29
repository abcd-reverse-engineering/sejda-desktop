package org.sejda.sambox.pdmodel.font;

import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.fontbox.FontBoxFont;
import org.apache.fontbox.util.BoundingBox;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.ResourceCache;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.encoding.DictionaryEncoding;
import org.sejda.sambox.pdmodel.font.encoding.Encoding;
import org.sejda.sambox.pdmodel.font.encoding.GlyphList;
import org.sejda.sambox.util.Matrix;
import org.sejda.sambox.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDType3Font.class */
public class PDType3Font extends PDSimpleFont {
    private static final Logger LOG = LoggerFactory.getLogger(PDType3Font.class);
    private PDResources resources;
    private COSDictionary charProcs;
    private Matrix fontMatrix;
    private BoundingBox fontBBox;
    private final ResourceCache resourceCache;

    public PDType3Font(COSDictionary fontDictionary) throws IOException {
        this(fontDictionary, null);
    }

    public PDType3Font(COSDictionary fontDictionary, ResourceCache resourceCache) throws IOException {
        super(fontDictionary);
        this.resourceCache = resourceCache;
        readEncoding();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public String getName() {
        return this.dict.getNameAsString(COSName.NAME);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    protected final void readEncoding() {
        COSBase encodingBase = this.dict.getDictionaryObject(COSName.ENCODING);
        if (encodingBase instanceof COSName) {
            COSName encodingName = (COSName) encodingBase;
            this.encoding = Encoding.getInstance(encodingName);
            if (this.encoding == null) {
                LOG.warn("Unknown encoding: {}", encodingName.getName());
            }
        } else if (encodingBase instanceof COSDictionary) {
            this.encoding = new DictionaryEncoding((COSDictionary) encodingBase);
        }
        this.glyphList = GlyphList.getAdobeGlyphList();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    protected Encoding readEncodingFromFont() {
        throw new UnsupportedOperationException("not supported for Type 3 fonts");
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    protected Boolean isFontSymbolic() {
        return false;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public GeneralPath getPath(String name) {
        throw new UnsupportedOperationException("not supported for Type 3 fonts");
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public boolean hasGlyph(String name) {
        return Objects.nonNull(getCharProcs().getDictionaryObject(COSName.getPDFName(name), COSStream.class));
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public FontBoxFont getFontBoxFont() {
        throw new UnsupportedOperationException("not supported for Type 3 fonts");
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public Vector getDisplacement(int code2) throws IOException {
        return getFontMatrix().transform(new Vector(getWidth(code2), 0.0f));
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public float getWidth(int code2) throws IOException {
        int firstChar = this.dict.getInt(COSName.FIRST_CHAR, -1);
        int lastChar = this.dict.getInt(COSName.LAST_CHAR, -1);
        List<Float> widths = getWidths();
        if (!widths.isEmpty() && code2 >= firstChar && code2 <= lastChar) {
            if (code2 - firstChar >= widths.size()) {
                return 0.0f;
            }
            return ((Float) Optional.ofNullable(widths.get(code2 - firstChar)).orElse(Float.valueOf(0.0f))).floatValue();
        }
        PDFontDescriptor fd = getFontDescriptor();
        if (Objects.nonNull(fd)) {
            return fd.getMissingWidth();
        }
        return getWidthFromFont(code2);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getWidthFromFont(int code2) throws IOException {
        PDType3CharProc charProc = getCharProc(code2);
        if (charProc == null || charProc.getContentStream().getLength() == 0) {
            return 0.0f;
        }
        return charProc.getWidth();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isEmbedded() {
        return true;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getHeight(int code2) {
        PDFontDescriptor desc = getFontDescriptor();
        if (desc != null) {
            PDRectangle bbox = desc.getFontBoundingBox();
            float retval = 0.0f;
            if (bbox != null) {
                retval = bbox.getHeight() / 2.0f;
            }
            if (retval == 0.0f) {
                retval = desc.getCapHeight();
            }
            if (retval == 0.0f) {
                retval = desc.getAscent();
            }
            if (retval == 0.0f) {
                retval = desc.getXHeight();
                if (retval > 0.0f) {
                    retval -= desc.getDescent();
                }
            }
            return retval;
        }
        return 0.0f;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    protected byte[] encode(int unicode) {
        throw new UnsupportedOperationException("Not implemented: Type3");
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public int readCode(InputStream in) throws IOException {
        return in.read();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public Matrix getFontMatrix() {
        if (this.fontMatrix == null) {
            COSArray matrix = this.dict.getCOSArray(COSName.FONT_MATRIX);
            this.fontMatrix = checkFontMatrixValues(matrix) ? Matrix.createMatrix(matrix) : super.getFontMatrix();
        }
        return this.fontMatrix;
    }

    private boolean checkFontMatrixValues(COSArray matrix) {
        if (matrix == null || matrix.size() != 6) {
            return false;
        }
        for (COSBase element : matrix.toList()) {
            if (!(element instanceof COSNumber)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isDamaged() {
        return false;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont, org.sejda.sambox.pdmodel.font.PDFont
    public boolean isStandard14() {
        return false;
    }

    public PDResources getResources() {
        COSDictionary resourcesDict;
        if (this.resources == null && (resourcesDict = (COSDictionary) this.dict.getDictionaryObject(COSName.RESOURCES, COSDictionary.class)) != null) {
            this.resources = new PDResources(resourcesDict, this.resourceCache);
        }
        return this.resources;
    }

    public PDRectangle getFontBBox() {
        return (PDRectangle) Optional.ofNullable((COSArray) this.dict.getDictionaryObject(COSName.FONT_BBOX, COSArray.class)).map(PDRectangle::new).orElse(null);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public BoundingBox getBoundingBox() {
        if (this.fontBBox == null) {
            this.fontBBox = generateBoundingBox();
        }
        return this.fontBBox;
    }

    private BoundingBox generateBoundingBox() {
        PDRectangle rect = getFontBBox();
        if (Objects.nonNull(rect)) {
            if (rect.getLowerLeftX() == 0.0f && rect.getLowerLeftY() == 0.0f && rect.getUpperRightX() == 0.0f && rect.getUpperRightY() == 0.0f) {
                COSDictionary cp = getCharProcs();
                if (Objects.nonNull(cp)) {
                    for (COSName name : cp.keySet()) {
                        COSStream stream = (COSStream) cp.getDictionaryObject(name, COSStream.class);
                        if (Objects.nonNull(stream)) {
                            PDType3CharProc charProc = new PDType3CharProc(this, stream);
                            PDRectangle glyphBBox = charProc.getGlyphBBox();
                            if (Objects.nonNull(glyphBBox)) {
                                rect.setLowerLeftX(Math.min(rect.getLowerLeftX(), glyphBBox.getLowerLeftX()));
                                rect.setLowerLeftY(Math.min(rect.getLowerLeftY(), glyphBBox.getLowerLeftY()));
                                rect.setUpperRightX(Math.max(rect.getUpperRightX(), glyphBBox.getUpperRightX()));
                                rect.setUpperRightY(Math.max(rect.getUpperRightY(), glyphBBox.getUpperRightY()));
                            }
                        }
                    }
                }
            }
            return new BoundingBox(rect.getLowerLeftX(), rect.getLowerLeftY(), rect.getUpperRightX(), rect.getUpperRightY());
        }
        LOG.warn("FontBBox missing, returning empty rectangle");
        return new BoundingBox();
    }

    public COSDictionary getCharProcs() {
        if (this.charProcs == null) {
            this.charProcs = (COSDictionary) this.dict.getDictionaryObject(COSName.CHAR_PROCS, COSDictionary.class);
        }
        return this.charProcs;
    }

    public PDType3CharProc getCharProc(int code2) {
        Encoding encoding = getEncoding();
        if (Objects.nonNull(encoding)) {
            String name = encoding.getName(code2);
            return (PDType3CharProc) Optional.ofNullable(getCharProcs()).map(d -> {
                return (COSStream) d.getDictionaryObject(COSName.getPDFName(name), COSStream.class);
            }).map(s -> {
                return new PDType3CharProc(this, s);
            }).orElse(null);
        }
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isOriginalEmbeddedMissing() {
        return false;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isMappingFallbackUsed() {
        return false;
    }
}
