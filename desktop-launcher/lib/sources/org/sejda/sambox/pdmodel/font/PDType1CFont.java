package org.sejda.sambox.pdmodel.font;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.fontbox.EncodedFont;
import org.apache.fontbox.FontBoxFont;
import org.apache.fontbox.cff.CFFFont;
import org.apache.fontbox.cff.CFFParser;
import org.apache.fontbox.cff.CFFType1Font;
import org.apache.fontbox.util.BoundingBox;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.font.encoding.Encoding;
import org.sejda.sambox.pdmodel.font.encoding.StandardEncoding;
import org.sejda.sambox.pdmodel.font.encoding.Type1Encoding;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDType1CFont.class */
public class PDType1CFont extends PDSimpleFont {
    private static final Logger LOG = LoggerFactory.getLogger(PDType1CFont.class);
    private final Map<String, Float> glyphHeights;
    private Float avgWidth;
    private Matrix fontMatrix;
    private final AffineTransform fontMatrixTransform;
    private final CFFType1Font cffFont;
    private final FontBoxFont genericFont;
    private final boolean isEmbedded;
    private final boolean isDamaged;
    private boolean isOriginalEmbeddedMissing;
    private boolean isMappingFallbackUsed;
    private BoundingBox fontBBox;

    public PDType1CFont(COSDictionary fontDictionary) throws IOException {
        PDStream ff3Stream;
        super(fontDictionary);
        this.glyphHeights = new HashMap();
        this.avgWidth = null;
        this.isOriginalEmbeddedMissing = false;
        this.isMappingFallbackUsed = false;
        PDFontDescriptor fd = getFontDescriptor();
        byte[] bytes = null;
        if (fd != null && (ff3Stream = fd.getFontFile3()) != null) {
            bytes = ff3Stream.toByteArray();
            if (bytes.length == 0) {
                LOG.error("Invalid data for embedded Type1C font " + getName());
                bytes = null;
            }
        }
        boolean fontIsDamaged = false;
        CFFType1Font cffEmbedded = null;
        if (bytes != null) {
            try {
                CFFParser cffParser = new CFFParser();
                CFFType1Font cFFType1Font = (CFFFont) cffParser.parse(bytes, new FF3ByteSource()).get(0);
                if (cFFType1Font instanceof CFFType1Font) {
                    cffEmbedded = cFFType1Font;
                } else {
                    LOG.error("Expected CFFType1Font, got {}", cFFType1Font.getClass().getSimpleName());
                    fontIsDamaged = true;
                }
            } catch (IOException e) {
                LOG.error("Can't read the embedded Type1C font " + getName(), e);
                fontIsDamaged = true;
            }
        }
        this.isDamaged = fontIsDamaged;
        this.cffFont = cffEmbedded;
        if (this.cffFont != null) {
            this.genericFont = this.cffFont;
            this.isEmbedded = true;
        } else {
            this.isOriginalEmbeddedMissing = true;
            FontMapping<FontBoxFont> mapping = FontMappers.instance().getFontBoxFont(getBaseFont(), fd);
            this.genericFont = mapping.getFont();
            if (mapping.isFallback()) {
                LOG.warn("Using fallback font " + this.genericFont.getName() + " for " + getBaseFont());
                this.isMappingFallbackUsed = true;
            }
            this.isEmbedded = false;
        }
        readEncoding();
        this.fontMatrixTransform = getFontMatrix().createAffineTransform();
        this.fontMatrixTransform.scale(1000.0d, 1000.0d);
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDType1CFont$FF3ByteSource.class */
    private class FF3ByteSource implements CFFParser.ByteSource {
        private FF3ByteSource() {
        }

        public byte[] getBytes() throws IOException {
            return PDType1CFont.this.getFontDescriptor().getFontFile3().toByteArray();
        }
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public FontBoxFont getFontBoxFont() {
        return this.genericFont;
    }

    public final String getBaseFont() {
        return this.dict.getNameAsString(COSName.BASE_FONT);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public GeneralPath getPath(String name) throws IOException {
        if (name.equals(".notdef") && !isEmbedded() && !isStandard14()) {
            return new GeneralPath();
        }
        if ("sfthyphen".equals(name)) {
            return this.genericFont.getPath("hyphen");
        }
        if ("nbspace".equals(name)) {
            if (!hasGlyph("space")) {
                return new GeneralPath();
            }
            return this.genericFont.getPath("space");
        }
        return this.genericFont.getPath(name);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public boolean hasGlyph(String name) throws IOException {
        return this.genericFont.hasGlyph(name);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public final String getName() {
        return getBaseFont();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public BoundingBox getBoundingBox() throws IOException {
        if (this.fontBBox == null) {
            this.fontBBox = generateBoundingBox();
        }
        return this.fontBBox;
    }

    private BoundingBox generateBoundingBox() throws IOException {
        if (getFontDescriptor() != null) {
            PDRectangle bbox = getFontDescriptor().getFontBoundingBox();
            if (Objects.nonNull(bbox) && (bbox.getLowerLeftX() != 0.0f || bbox.getLowerLeftY() != 0.0f || bbox.getUpperRightX() != 0.0f || bbox.getUpperRightY() != 0.0f)) {
                return new BoundingBox(bbox.getLowerLeftX(), bbox.getLowerLeftY(), bbox.getUpperRightX(), bbox.getUpperRightY());
            }
        }
        return this.genericFont.getFontBBox();
    }

    public String codeToName(int code2) {
        return getEncoding().getName(code2);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    protected Encoding readEncodingFromFont() throws IOException {
        if (!isEmbedded() && getStandard14AFM() != null) {
            return new Type1Encoding(getStandard14AFM());
        }
        if (this.genericFont instanceof EncodedFont) {
            return Type1Encoding.fromFontBox(this.genericFont.getEncoding());
        }
        return StandardEncoding.INSTANCE;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public int readCode(InputStream in) throws IOException {
        return in.read();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public final Matrix getFontMatrix() {
        if (this.fontMatrix == null) {
            List<Number> numbers = null;
            try {
                numbers = this.genericFont.getFontMatrix();
            } catch (IOException e) {
                this.fontMatrix = DEFAULT_FONT_MATRIX;
            }
            if (numbers != null && numbers.size() == 6) {
                this.fontMatrix = new Matrix(numbers.get(0).floatValue(), numbers.get(1).floatValue(), numbers.get(2).floatValue(), numbers.get(3).floatValue(), numbers.get(4).floatValue(), numbers.get(5).floatValue());
            } else {
                return super.getFontMatrix();
            }
        }
        return this.fontMatrix;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isDamaged() {
        return this.isDamaged;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getWidthFromFont(int code2) throws NumberFormatException, IOException {
        String name = codeToName(code2);
        float width = this.genericFont.getWidth(getNameInFont(name));
        Point2D.Float r0 = new Point2D.Float(width, 0.0f);
        this.fontMatrixTransform.transform(r0, r0);
        return (float) r0.getX();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isEmbedded() {
        return this.isEmbedded;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getHeight(int code2) throws IOException {
        float height;
        String name = codeToName(code2);
        if (!this.glyphHeights.containsKey(name)) {
            if (this.cffFont == null) {
                LOG.warn("No embedded CFF font, returning height 0");
                return 0.0f;
            }
            height = (float) this.cffFont.getType1CharString(name).getBounds().getHeight();
            this.glyphHeights.put(name, Float.valueOf(height));
        } else {
            height = this.glyphHeights.get(name).floatValue();
        }
        return height;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    protected byte[] encode(int unicode) throws NumberFormatException, IOException {
        String name = getGlyphList().codePointToName(unicode);
        if (!this.encoding.contains(name)) {
            throw new IllegalArgumentException(String.format("U+%04X ('%s') is not available in this font's encoding: %s", Integer.valueOf(unicode), name, this.encoding.getEncodingName()));
        }
        String nameInFont = getNameInFont(name);
        Map<String, Integer> inverted = this.encoding.getNameToCodeMap();
        if (nameInFont.equals(".notdef") || !this.genericFont.hasGlyph(nameInFont)) {
            throw new IllegalArgumentException(String.format("No glyph for U+%04X in font %s", Integer.valueOf(unicode), getName()));
        }
        int code2 = inverted.get(name).intValue();
        return new byte[]{(byte) code2};
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public float getStringWidth(String string) throws IOException {
        if (this.cffFont == null) {
            LOG.warn("No embedded CFF font, returning width 0");
            return 0.0f;
        }
        float width = 0.0f;
        for (int i = 0; i < string.length(); i++) {
            int codePoint = string.codePointAt(i);
            String name = getGlyphList().codePointToName(codePoint);
            width += this.cffFont.getType1CharString(name).getWidth();
        }
        return width;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public float getAverageFontWidth() {
        if (this.avgWidth == null) {
            this.avgWidth = Float.valueOf(getAverageCharacterWidth());
        }
        return this.avgWidth.floatValue();
    }

    public CFFType1Font getCFFType1Font() {
        return this.cffFont;
    }

    private float getAverageCharacterWidth() {
        return 500.0f;
    }

    private String getNameInFont(String name) throws NumberFormatException, IOException {
        if (isEmbedded() || this.genericFont.hasGlyph(name)) {
            return name;
        }
        String unicodes = getGlyphList().toUnicode(name);
        if (unicodes != null && unicodes.length() == 1) {
            String uniName = UniUtil.getUniNameOfCodePoint(unicodes.codePointAt(0));
            if (this.genericFont.hasGlyph(uniName)) {
                return uniName;
            }
            return ".notdef";
        }
        return ".notdef";
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isOriginalEmbeddedMissing() {
        return this.isOriginalEmbeddedMissing;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isMappingFallbackUsed() {
        return this.isMappingFallbackUsed;
    }
}
