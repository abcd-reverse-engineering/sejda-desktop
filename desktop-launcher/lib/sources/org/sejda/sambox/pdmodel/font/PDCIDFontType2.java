package org.sejda.sambox.pdmodel.font;

import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.fontbox.cff.Type2CharString;
import org.apache.fontbox.cmap.CMap;
import org.apache.fontbox.ttf.CmapLookup;
import org.apache.fontbox.ttf.GlyphData;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.fontbox.util.BoundingBox;
import org.sejda.commons.util.ReflectionUtils;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDCIDFontType2.class */
public class PDCIDFontType2 extends PDCIDFont {
    private static final Logger LOG = LoggerFactory.getLogger(PDCIDFontType2.class);
    private final TrueTypeFont ttf;
    private final int[] cid2gid;
    private final HashMap<Integer, Integer> gid2cid;
    private final boolean isEmbedded;
    private final boolean isDamaged;
    private boolean isOriginalEmbeddedMissing;
    private boolean isMappingFallbackUsed;
    private final CmapLookup cmap;
    private Matrix fontMatrix;
    private BoundingBox fontBBox;
    private final Set<Integer> noMapping;
    private Map<String, Integer> invertedUnicodeCmap;

    public PDCIDFontType2(COSDictionary fontDictionary, PDType0Font parent) throws IOException {
        this(fontDictionary, parent, null);
    }

    public PDCIDFontType2(COSDictionary fontDictionary, PDType0Font parent, TrueTypeFont trueTypeFont) throws IOException {
        super(fontDictionary, parent);
        this.gid2cid = new HashMap<>();
        this.isOriginalEmbeddedMissing = false;
        this.isMappingFallbackUsed = false;
        this.noMapping = new HashSet();
        this.invertedUnicodeCmap = null;
        PDFontDescriptor fd = getFontDescriptor();
        if (trueTypeFont != null) {
            this.ttf = trueTypeFont;
            this.isEmbedded = true;
            this.isDamaged = false;
        } else {
            boolean fontIsDamaged = false;
            TrueTypeFont ttfFont = null;
            PDStream stream = null;
            if (fd != null) {
                stream = fd.getFontFile2();
                stream = stream == null ? fd.getFontFile3() : stream;
                if (stream == null) {
                    stream = fd.getFontFile();
                }
            }
            if (stream != null) {
                try {
                    OTFParser otfParser = new OTFParser(true);
                    TrueTypeFont trueTypeFont2 = otfParser.parse(stream.createInputStream());
                    ttfFont = trueTypeFont2;
                    if (trueTypeFont2.isPostScript()) {
                        fontIsDamaged = true;
                        LOG.warn("Found CFF/OTF but expected embedded TTF font " + fd.getFontName());
                    }
                } catch (IOException | NullPointerException e) {
                    fontIsDamaged = true;
                    LOG.warn("Could not read embedded OTF for font " + getBaseFont(), e);
                }
            }
            this.isEmbedded = ttfFont != null;
            this.isDamaged = fontIsDamaged;
            if (ttfFont == null) {
                this.isOriginalEmbeddedMissing = true;
                ttfFont = findFontOrSubstitute();
            }
            this.ttf = ttfFont;
        }
        this.cmap = this.ttf.getUnicodeCmapLookup(false);
        this.cid2gid = readCIDToGIDMap();
        if (this.cid2gid != null) {
            for (int cid = 0; cid < this.cid2gid.length; cid++) {
                int gid = this.cid2gid[cid];
                if (gid != 0) {
                    this.gid2cid.put(Integer.valueOf(gid), Integer.valueOf(cid));
                }
            }
        }
    }

    private TrueTypeFont findFontOrSubstitute() throws IOException {
        TrueTypeFont ttfFont;
        CIDFontMapping mapping = FontMappers.instance().getCIDFont(getBaseFont(), getFontDescriptor(), getCIDSystemInfo());
        if (mapping.isCIDFont()) {
            ttfFont = (TrueTypeFont) mapping.getFont();
        } else {
            ttfFont = mapping.getTrueTypeFont();
        }
        if (mapping.isFallback()) {
            LOG.warn("Using fallback font " + ttfFont.getName() + " for CID-keyed TrueType font " + getBaseFont());
            this.isMappingFallbackUsed = true;
        }
        return ttfFont;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public Matrix getFontMatrix() {
        if (this.fontMatrix == null) {
            this.fontMatrix = new Matrix(0.001f, 0.0f, 0.0f, 0.001f, 0.0f, 0.0f);
        }
        return this.fontMatrix;
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
            if (Objects.nonNull(bbox) && (Float.compare(bbox.getLowerLeftX(), 0.0f) != 0 || Float.compare(bbox.getLowerLeftY(), 0.0f) != 0 || Float.compare(bbox.getUpperRightX(), 0.0f) != 0 || Float.compare(bbox.getUpperRightY(), 0.0f) != 0)) {
                return new BoundingBox(bbox.getLowerLeftX(), bbox.getLowerLeftY(), bbox.getUpperRightX(), bbox.getUpperRightY());
            }
        }
        return this.ttf.getFontBBox();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDCIDFont
    public int codeToCID(int code2) {
        String unicode;
        CMap cMap = this.parent.getCMap();
        if (!cMap.hasCIDMappings() && cMap.hasUnicodeMappings() && (unicode = cMap.toUnicode(code2)) != null) {
            return unicode.codePointAt(0);
        }
        return cMap.toCID(code2);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDCIDFont
    public int codeToGID(int code2) throws IOException {
        if (!this.isEmbedded) {
            if (this.cid2gid != null && !this.isDamaged) {
                LOG.warn("Using non-embedded GIDs in font " + getName());
                int cid = codeToCID(code2);
                if (cid < this.cid2gid.length) {
                    return this.cid2gid[cid];
                }
                return 0;
            }
            String unicode = this.parent.toUnicode(code2);
            if (unicode == null) {
                if (!this.noMapping.contains(Integer.valueOf(code2))) {
                    this.noMapping.add(Integer.valueOf(code2));
                    LOG.warn("Failed to find a character mapping for " + code2 + " in " + getName());
                }
                return codeToCID(code2);
            }
            if (unicode.length() > 1) {
                LOG.warn("Trying to map multi-byte character using 'cmap', result will be poor");
            }
            return this.cmap.getGlyphId(unicode.codePointAt(0));
        }
        int cid2 = codeToCID(code2);
        if (this.cid2gid != null) {
            if (cid2 < this.cid2gid.length) {
                return this.cid2gid[cid2];
            }
            return 0;
        }
        if (cid2 < this.ttf.getNumberOfGlyphs()) {
            return cid2;
        }
        return 0;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getHeight(int code2) throws IOException {
        return (this.ttf.getHorizontalHeader().getAscender() + (-this.ttf.getHorizontalHeader().getDescender())) / this.ttf.getUnitsPerEm();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getWidthFromFont(int code2) throws IOException {
        int gid = codeToGID(code2);
        float width = this.ttf.getAdvanceWidth(gid);
        int unitsPerEM = this.ttf.getUnitsPerEm();
        if (unitsPerEM != 1000) {
            width *= 1000.0f / unitsPerEM;
        }
        return width;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDCIDFont
    public byte[] encode(int unicode) {
        int gid;
        int cid = -1;
        if (this.isEmbedded) {
            if (this.parent.getCMap().getName().startsWith("Identity-")) {
                if (this.cmap != null && (gid = this.cmap.getGlyphId(unicode)) != 0) {
                    cid = this.gid2cid.getOrDefault(Integer.valueOf(gid), -1).intValue();
                    if (cid == -1) {
                        cid = gid;
                    }
                }
            } else if (this.parent.getCMapUCS2() != null) {
                cid = this.parent.getCMapUCS2().toCID(unicode);
            }
            if (cid == -1) {
                cid = lookupInInvertedUnicodeCmap(unicode);
            }
            if (cid == -1) {
                cid = 0;
            }
        } else {
            cid = this.cmap.getGlyphId(unicode);
        }
        if (cid == 0) {
            throw new IllegalArgumentException(String.format("No glyph for U+%04X (%c) in font %s", Integer.valueOf(unicode), Character.valueOf((char) unicode), getName()));
        }
        return new byte[]{(byte) ((cid >> 8) & 255), (byte) (cid & 255)};
    }

    private Map<String, Integer> generateInvertedUnicodeCmap() {
        CMap cMap = this.parent.getToUnicodeCMap();
        if (cMap != null) {
            Field charToUnicodeField = ReflectionUtils.findField(CMap.class, "charToUnicode");
            ReflectionUtils.makeAccessible(charToUnicodeField);
            Map<Integer, String> charToUnicode = (Map) ReflectionUtils.getField(charToUnicodeField, cMap);
            if (charToUnicode != null) {
                Map<String, Integer> invertedUnicodeCmap = new HashMap<>(charToUnicode.size());
                for (Integer code2 : charToUnicode.keySet()) {
                    invertedUnicodeCmap.put(charToUnicode.get(code2), code2);
                }
                return invertedUnicodeCmap;
            }
        }
        return new HashMap();
    }

    private int lookupInInvertedUnicodeCmap(int unicode) {
        if (this.invertedUnicodeCmap == null) {
            this.invertedUnicodeCmap = generateInvertedUnicodeCmap();
        }
        String s = new String(Character.toChars(unicode));
        if (this.invertedUnicodeCmap.containsKey(s)) {
            return this.invertedUnicodeCmap.get(s).intValue();
        }
        return -1;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isEmbedded() {
        return this.isEmbedded;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isDamaged() {
        return this.isDamaged;
    }

    public TrueTypeFont getTrueTypeFont() {
        return this.ttf;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDVectorFont
    public GeneralPath getPath(int code2) throws IOException {
        if ((this.ttf instanceof OpenTypeFont) && this.ttf.isPostScript()) {
            int cid = codeToGID(code2);
            Type2CharString charstring = this.ttf.getCFF().getFont().getType2CharString(cid);
            return charstring.getPath();
        }
        int gid = codeToGID(code2);
        GlyphData glyph = this.ttf.getGlyph().getGlyph(gid);
        if (glyph != null) {
            return glyph.getPath();
        }
        return new GeneralPath();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDVectorFont
    public boolean hasGlyph(int code2) throws IOException {
        return codeToGID(code2) != 0;
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
