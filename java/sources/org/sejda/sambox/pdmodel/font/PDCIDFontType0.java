package org.sejda.sambox.pdmodel.font;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.fontbox.FontBoxFont;
import org.apache.fontbox.cff.CFFCIDFont;
import org.apache.fontbox.cff.CFFFont;
import org.apache.fontbox.cff.CFFParser;
import org.apache.fontbox.cff.CFFType1Font;
import org.apache.fontbox.cff.Type2CharString;
import org.apache.fontbox.util.BoundingBox;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDCIDFontType0.class */
public class PDCIDFontType0 extends PDCIDFont {
    private static final Logger LOG = LoggerFactory.getLogger(PDCIDFontType0.class);
    private final CFFCIDFont cidFont;
    private final FontBoxFont t1Font;
    private final Map<Integer, Float> glyphHeights;
    private final boolean isEmbedded;
    private final boolean isDamaged;
    private boolean isOriginalEmbeddedMissing;
    private boolean isMappingFallbackUsed;
    private Float avgWidth;
    private Matrix fontMatrix;
    private final AffineTransform fontMatrixTransform;
    private BoundingBox fontBBox;
    private int[] cid2gid;

    public PDCIDFontType0(COSDictionary fontDictionary, PDType0Font parent) throws IOException {
        CFFCIDFont cFFCIDFont;
        PDStream ff3Stream;
        super(fontDictionary, parent);
        this.glyphHeights = new HashMap();
        this.isOriginalEmbeddedMissing = false;
        this.isMappingFallbackUsed = false;
        this.avgWidth = null;
        this.cid2gid = null;
        PDFontDescriptor fd = getFontDescriptor();
        byte[] bytes = null;
        if (fd != null && (ff3Stream = fd.getFontFile3()) != null) {
            bytes = ff3Stream.toByteArray();
        }
        boolean fontIsDamaged = false;
        CFFCIDFont cFFCIDFont2 = null;
        if (bytes != null && bytes.length > 0 && (bytes[0] & 255) == 37) {
            LOG.warn("Found PFB but expected embedded CFF font " + fd.getFontName());
            fontIsDamaged = true;
        } else if (bytes != null) {
            CFFParser cffParser = new CFFParser();
            try {
                cFFCIDFont2 = (CFFFont) cffParser.parse(bytes, new FF3ByteSource()).get(0);
            } catch (IOException e) {
                LOG.error("Can't read the embedded CFF font " + fd.getFontName(), e);
                fontIsDamaged = true;
            }
        }
        if (cFFCIDFont2 != null) {
            if (cFFCIDFont2 instanceof CFFCIDFont) {
                this.cidFont = cFFCIDFont2;
                this.t1Font = null;
            } else {
                this.cidFont = null;
                this.t1Font = cFFCIDFont2;
            }
            this.cid2gid = readCIDToGIDMap();
            this.isEmbedded = true;
            this.isDamaged = false;
        } else {
            this.isOriginalEmbeddedMissing = true;
            CIDFontMapping mapping = FontMappers.instance().getCIDFont(getBaseFont(), getFontDescriptor(), getCIDSystemInfo());
            if (mapping.isCIDFont()) {
                CFFCIDFont font = mapping.getFont().getCFF().getFont();
                if (font instanceof CFFCIDFont) {
                    this.cidFont = font;
                    this.t1Font = null;
                    cFFCIDFont = this.cidFont;
                } else {
                    CFFCIDFont cFFCIDFont3 = (CFFType1Font) font;
                    this.cidFont = null;
                    this.t1Font = cFFCIDFont3;
                    cFFCIDFont = cFFCIDFont3;
                }
            } else {
                this.cidFont = null;
                this.t1Font = mapping.getTrueTypeFont();
                cFFCIDFont = this.t1Font;
            }
            if (mapping.isFallback()) {
                LOG.warn("Using fallback " + cFFCIDFont.getName() + " for CID-keyed font " + getBaseFont());
                this.isMappingFallbackUsed = true;
            }
            this.isEmbedded = false;
            this.isDamaged = fontIsDamaged;
        }
        this.fontMatrixTransform = getFontMatrix().createAffineTransform();
        this.fontMatrixTransform.scale(1000.0d, 1000.0d);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public final Matrix getFontMatrix() {
        List<Number> numbers;
        if (this.fontMatrix == null) {
            if (this.cidFont != null) {
                numbers = this.cidFont.getFontMatrix();
            } else {
                try {
                    numbers = this.t1Font.getFontMatrix();
                } catch (IOException e) {
                    return new Matrix(0.001f, 0.0f, 0.0f, 0.001f, 0.0f, 0.0f);
                }
            }
            if (numbers != null && numbers.size() == 6) {
                this.fontMatrix = new Matrix(numbers.get(0).floatValue(), numbers.get(1).floatValue(), numbers.get(2).floatValue(), numbers.get(3).floatValue(), numbers.get(4).floatValue(), numbers.get(5).floatValue());
            } else {
                this.fontMatrix = new Matrix(0.001f, 0.0f, 0.0f, 0.001f, 0.0f, 0.0f);
            }
        }
        return this.fontMatrix;
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDCIDFontType0$FF3ByteSource.class */
    private class FF3ByteSource implements CFFParser.ByteSource {
        private FF3ByteSource() {
        }

        public byte[] getBytes() throws IOException {
            return PDCIDFontType0.this.getFontDescriptor().getFontFile3().toByteArray();
        }
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public BoundingBox getBoundingBox() {
        if (this.fontBBox == null) {
            this.fontBBox = generateBoundingBox();
        }
        return this.fontBBox;
    }

    private BoundingBox generateBoundingBox() {
        PDRectangle bbox;
        if (getFontDescriptor() != null && (bbox = getFontDescriptor().getFontBoundingBox()) != null && (bbox.getLowerLeftX() != 0.0f || bbox.getLowerLeftY() != 0.0f || bbox.getUpperRightX() != 0.0f || bbox.getUpperRightY() != 0.0f)) {
            return new BoundingBox(bbox.getLowerLeftX(), bbox.getLowerLeftY(), bbox.getUpperRightX(), bbox.getUpperRightY());
        }
        if (this.cidFont != null) {
            return this.cidFont.getFontBBox();
        }
        try {
            return this.t1Font.getFontBBox();
        } catch (IOException e) {
            return new BoundingBox();
        }
    }

    public CFFFont getCFFFont() {
        if (this.cidFont != null) {
            return this.cidFont;
        }
        if (this.t1Font instanceof CFFType1Font) {
            return this.t1Font;
        }
        return null;
    }

    public FontBoxFont getFontBoxFont() {
        if (this.cidFont != null) {
            return this.cidFont;
        }
        return this.t1Font;
    }

    public Type2CharString getType2CharString(int cid) throws IOException {
        if (this.cidFont != null) {
            return this.cidFont.getType2CharString(cid);
        }
        if (this.t1Font instanceof CFFType1Font) {
            return this.t1Font.getType2CharString(cid);
        }
        return null;
    }

    private String getGlyphName(int code2) throws IOException {
        String unicodes = this.parent.toUnicode(code2);
        if (unicodes == null) {
            return ".notdef";
        }
        return UniUtil.getUniNameOfCodePoint(unicodes.codePointAt(0));
    }

    @Override // org.sejda.sambox.pdmodel.font.PDVectorFont
    public GeneralPath getPath(int code2) throws IOException {
        int cid = codeToCID(code2);
        if (this.cid2gid != null && this.isEmbedded) {
            cid = this.cid2gid[cid];
        }
        Type2CharString charstring = getType2CharString(cid);
        if (charstring != null) {
            return charstring.getPath();
        }
        if (this.isEmbedded && (this.t1Font instanceof CFFType1Font)) {
            return this.t1Font.getType2CharString(cid).getPath();
        }
        return this.t1Font.getPath(getGlyphName(code2));
    }

    @Override // org.sejda.sambox.pdmodel.font.PDVectorFont
    public boolean hasGlyph(int code2) throws IOException {
        int cid = codeToCID(code2);
        Type2CharString charstring = getType2CharString(cid);
        if (charstring != null) {
            return charstring.getGID() != 0;
        }
        if (this.isEmbedded && (this.t1Font instanceof CFFType1Font)) {
            return this.t1Font.getType2CharString(cid).getGID() != 0;
        }
        return this.t1Font.hasGlyph(getGlyphName(code2));
    }

    @Override // org.sejda.sambox.pdmodel.font.PDCIDFont
    public int codeToCID(int code2) {
        return this.parent.getCMap().toCID(code2);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDCIDFont
    public int codeToGID(int code2) {
        int cid = codeToCID(code2);
        if (this.cidFont != null) {
            return this.cidFont.getCharset().getGIDForCID(cid);
        }
        return cid;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDCIDFont
    public byte[] encode(int unicode) {
        throw new UnsupportedOperationException();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getWidthFromFont(int code2) throws IOException {
        float width;
        int cid = codeToCID(code2);
        if (this.cidFont != null) {
            width = getType2CharString(cid).getWidth();
        } else if (this.isEmbedded && (this.t1Font instanceof CFFType1Font)) {
            width = this.t1Font.getType2CharString(cid).getWidth();
        } else {
            width = this.t1Font.getWidth(getGlyphName(code2));
        }
        Point2D.Float r0 = new Point2D.Float(width, 0.0f);
        this.fontMatrixTransform.transform(r0, r0);
        return (float) r0.getX();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isEmbedded() {
        return this.isEmbedded;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isDamaged() {
        return this.isDamaged;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getHeight(int code2) throws IOException {
        float height;
        int cid = codeToCID(code2);
        if (!this.glyphHeights.containsKey(Integer.valueOf(cid))) {
            height = (float) getType2CharString(cid).getBounds().getHeight();
            this.glyphHeights.put(Integer.valueOf(cid), Float.valueOf(height));
        } else {
            height = this.glyphHeights.get(Integer.valueOf(cid)).floatValue();
        }
        return height;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDCIDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public float getAverageFontWidth() {
        if (this.avgWidth == null) {
            this.avgWidth = Float.valueOf(getAverageCharacterWidth());
        }
        return this.avgWidth.floatValue();
    }

    private float getAverageCharacterWidth() {
        return 500.0f;
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
