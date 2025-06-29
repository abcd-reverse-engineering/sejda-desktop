package org.sejda.sambox.pdmodel.font;

import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.fontbox.FontBoxFont;
import org.apache.fontbox.ttf.CmapSubtable;
import org.apache.fontbox.ttf.CmapTable;
import org.apache.fontbox.ttf.GlyphData;
import org.apache.fontbox.ttf.PostScriptTable;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.fontbox.util.BoundingBox;
import org.sejda.commons.util.IOUtils;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.font.encoding.BuiltInEncoding;
import org.sejda.sambox.pdmodel.font.encoding.Encoding;
import org.sejda.sambox.pdmodel.font.encoding.GlyphList;
import org.sejda.sambox.pdmodel.font.encoding.MacOSRomanEncoding;
import org.sejda.sambox.pdmodel.font.encoding.MacRomanEncoding;
import org.sejda.sambox.pdmodel.font.encoding.StandardEncoding;
import org.sejda.sambox.pdmodel.font.encoding.Type1Encoding;
import org.sejda.sambox.pdmodel.font.encoding.WinAnsiEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDTrueTypeFont.class */
public class PDTrueTypeFont extends PDSimpleFont implements PDVectorFont {
    private static final int START_RANGE_F000 = 61440;
    private static final int START_RANGE_F100 = 61696;
    private static final int START_RANGE_F200 = 61952;
    private CmapSubtable cmapWinUnicode;
    private CmapSubtable cmapWinSymbol;
    private CmapSubtable cmapMacRoman;
    private boolean cmapInitialized;
    private Map<Integer, Integer> gidToCode;
    private final TrueTypeFont ttf;
    private final boolean isEmbedded;
    private final boolean isDamaged;
    private boolean isOriginalEmbeddedMissing;
    private boolean isMappingFallbackUsed;
    private BoundingBox fontBBox;
    private static final Logger LOG = LoggerFactory.getLogger(PDTrueTypeFont.class);
    private static final Map<String, Integer> INVERTED_MACOS_ROMAN = new HashMap(250);

    static {
        Map<Integer, String> codeToName = MacOSRomanEncoding.INSTANCE.getCodeToNameMap();
        for (Map.Entry<Integer, String> entry : codeToName.entrySet()) {
            if (!INVERTED_MACOS_ROMAN.containsKey(entry.getValue())) {
                INVERTED_MACOS_ROMAN.put(entry.getValue(), entry.getKey());
            }
        }
    }

    public static PDTrueTypeFont load(File file, Encoding encoding) throws IOException {
        return new PDTrueTypeFont(new TTFParser().parse(file), encoding, true);
    }

    public static PDTrueTypeFont load(InputStream input, Encoding encoding) throws IOException {
        return new PDTrueTypeFont(new TTFParser().parse(input), encoding, true);
    }

    public static PDTrueTypeFont load(PDDocument doc, TrueTypeFont ttf, Encoding encoding) throws IOException {
        return new PDTrueTypeFont(ttf, encoding, false);
    }

    public PDTrueTypeFont(COSDictionary fontDictionary) throws IOException {
        super(fontDictionary);
        this.cmapWinUnicode = null;
        this.cmapWinSymbol = null;
        this.cmapMacRoman = null;
        this.cmapInitialized = false;
        this.isOriginalEmbeddedMissing = false;
        this.isMappingFallbackUsed = false;
        TrueTypeFont ttfFont = null;
        boolean fontIsDamaged = false;
        if (getFontDescriptor() != null) {
            PDFontDescriptor fd = super.getFontDescriptor();
            PDStream ff2Stream = fd.getFontFile2();
            if (ff2Stream != null) {
                InputStream is = null;
                try {
                    TTFParser ttfParser = new TTFParser(true);
                    is = ff2Stream.createInputStream();
                    ttfFont = ttfParser.parse(is);
                } catch (IOException e) {
                    LOG.warn("Could not read embedded TTF for font " + getBaseFont(), e);
                    fontIsDamaged = true;
                    IOUtils.closeQuietly(is);
                } catch (NullPointerException e2) {
                    LOG.warn("Could not read embedded TTF for font " + getBaseFont(), e2);
                    fontIsDamaged = true;
                    IOUtils.closeQuietly(is);
                }
            }
        }
        this.isEmbedded = ttfFont != null;
        this.isDamaged = fontIsDamaged;
        if (ttfFont == null) {
            this.isOriginalEmbeddedMissing = true;
            FontMapping<TrueTypeFont> mapping = FontMappers.instance().getTrueTypeFont(getBaseFont(), getFontDescriptor());
            ttfFont = (TrueTypeFont) mapping.getFont();
            if (mapping.isFallback()) {
                LOG.warn("Using fallback font '" + ttfFont + "' for '" + getBaseFont() + "'");
                this.isMappingFallbackUsed = true;
            }
        }
        this.ttf = ttfFont;
        readEncoding();
    }

    public PDTrueTypeFont(COSDictionary fontDictionary, TrueTypeFont ttf) throws IOException {
        super(fontDictionary);
        this.cmapWinUnicode = null;
        this.cmapWinSymbol = null;
        this.cmapMacRoman = null;
        this.cmapInitialized = false;
        this.isOriginalEmbeddedMissing = false;
        this.isMappingFallbackUsed = false;
        this.ttf = (TrueTypeFont) Objects.requireNonNull(ttf);
        this.isEmbedded = true;
        this.isDamaged = false;
        readEncoding();
    }

    public final String getBaseFont() {
        return this.dict.getNameAsString(COSName.BASE_FONT);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    protected Encoding readEncodingFromFont() throws IOException, NumberFormatException {
        if (!isEmbedded() && getStandard14AFM() != null) {
            return new Type1Encoding(getStandard14AFM());
        }
        if (getSymbolicFlag() != null && !getSymbolicFlag().booleanValue()) {
            return StandardEncoding.INSTANCE;
        }
        String standard14Name = Standard14Fonts.getMappedFontName(getName());
        if (isStandard14() && !standard14Name.equals("Symbol") && !standard14Name.equals("ZapfDingbats")) {
            return StandardEncoding.INSTANCE;
        }
        PostScriptTable post = this.ttf.getPostScript();
        Map<Integer, String> codeToName = new HashMap<>();
        for (int code2 = 0; code2 <= 256; code2++) {
            int gid = codeToGID(code2);
            if (gid > 0) {
                String name = null;
                if (post != null) {
                    name = post.getName(gid);
                }
                if (name == null) {
                    name = Integer.toString(gid);
                }
                codeToName.put(Integer.valueOf(code2), name);
            }
        }
        return new BuiltInEncoding(codeToName);
    }

    private PDTrueTypeFont(TrueTypeFont ttf, Encoding encoding, boolean closeTTF) throws IOException {
        this.cmapWinUnicode = null;
        this.cmapWinSymbol = null;
        this.cmapMacRoman = null;
        this.cmapInitialized = false;
        this.isOriginalEmbeddedMissing = false;
        this.isMappingFallbackUsed = false;
        PDTrueTypeFontEmbedder embedder = new PDTrueTypeFontEmbedder(this.dict, ttf, encoding);
        this.encoding = encoding;
        this.ttf = ttf;
        setFontDescriptor(embedder.getFontDescriptor());
        this.isEmbedded = true;
        this.isDamaged = false;
        this.glyphList = GlyphList.getAdobeGlyphList();
        if (closeTTF) {
            ttf.close();
        }
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public int readCode(InputStream in) throws IOException {
        return in.read();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public String getName() {
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
            if (Objects.nonNull(bbox)) {
                return new BoundingBox(bbox.getLowerLeftX(), bbox.getLowerLeftY(), bbox.getUpperRightX(), bbox.getUpperRightY());
            }
        }
        return this.ttf.getFontBBox();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isDamaged() {
        return this.isDamaged;
    }

    public TrueTypeFont getTrueTypeFont() {
        return this.ttf;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getWidthFromFont(int code2) throws IOException, NumberFormatException {
        int gid = codeToGID(code2);
        float width = this.ttf.getAdvanceWidth(gid);
        float unitsPerEM = this.ttf.getUnitsPerEm();
        if (unitsPerEM != 1000.0f) {
            width *= 1000.0f / unitsPerEM;
        }
        return width;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getHeight(int code2) throws IOException, NumberFormatException {
        int gid = codeToGID(code2);
        GlyphData glyph = this.ttf.getGlyph().getGlyph(gid);
        if (glyph != null) {
            return glyph.getBoundingBox().getHeight();
        }
        return 0.0f;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    protected byte[] encode(int unicode) throws IOException {
        if (Objects.nonNull(this.encoding)) {
            if (!this.encoding.contains(getGlyphList().codePointToName(unicode))) {
                throw new IllegalArgumentException(String.format("U+%04X is not available in this font's encoding: %s", Integer.valueOf(unicode), this.encoding.getEncodingName()));
            }
            String name = getGlyphList().codePointToName(unicode);
            Map<String, Integer> inverted = this.encoding.getNameToCodeMap();
            if (!this.ttf.hasGlyph(name)) {
                String uniName = UniUtil.getUniNameOfCodePoint(unicode);
                if (!this.ttf.hasGlyph(uniName)) {
                    throw new IllegalArgumentException(String.format("No glyph for U+%04X in font %s", Integer.valueOf(unicode), getName()));
                }
            }
            Integer code2 = inverted.get(name);
            if (code2 == null) {
                throw new IllegalArgumentException(String.format("No glyph for U+%04X in font %s", Integer.valueOf(unicode), getName()));
            }
            return new byte[]{(byte) code2.intValue()};
        }
        String name2 = getGlyphList().codePointToName(unicode);
        if (!this.ttf.hasGlyph(name2)) {
            throw new IllegalArgumentException(String.format("No glyph for U+%04X in font %s", Integer.valueOf(unicode), getName()));
        }
        int gid = this.ttf.nameToGID(name2);
        Integer code3 = getGIDToCode().get(Integer.valueOf(gid));
        if (code3 == null) {
            throw new IllegalArgumentException(String.format("U+%04X is not available in this font's Encoding", Integer.valueOf(unicode)));
        }
        return new byte[]{(byte) code3.intValue()};
    }

    protected Map<Integer, Integer> getGIDToCode() throws IOException, NumberFormatException {
        if (this.gidToCode != null) {
            return this.gidToCode;
        }
        this.gidToCode = new HashMap();
        for (int code2 = 0; code2 <= 255; code2++) {
            int gid = codeToGID(code2);
            if (!this.gidToCode.containsKey(Integer.valueOf(gid))) {
                this.gidToCode.put(Integer.valueOf(gid), Integer.valueOf(code2));
            }
        }
        return this.gidToCode;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isEmbedded() {
        return this.isEmbedded;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDVectorFont
    public GeneralPath getPath(int code2) throws IOException, NumberFormatException {
        int gid = codeToGID(code2);
        GlyphData glyph = this.ttf.getGlyph().getGlyph(gid);
        if (glyph == null) {
            return new GeneralPath();
        }
        return glyph.getPath();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public GeneralPath getPath(String name) throws NumberFormatException, IOException {
        int gid = this.ttf.nameToGID(name);
        if (gid == 0) {
            try {
                gid = Integer.parseInt(name);
                if (gid > this.ttf.getNumberOfGlyphs()) {
                    gid = 0;
                }
            } catch (NumberFormatException e) {
                gid = 0;
            }
        }
        if (gid == 0) {
            return new GeneralPath();
        }
        GlyphData glyph = this.ttf.getGlyph().getGlyph(gid);
        if (glyph != null) {
            return glyph.getPath();
        }
        return new GeneralPath();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public boolean hasGlyph(String name) throws IOException {
        int gid = this.ttf.nameToGID(name);
        return gid != 0;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public FontBoxFont getFontBoxFont() {
        return this.ttf;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDVectorFont
    public boolean hasGlyph(int code2) throws IOException {
        return codeToGID(code2) != 0;
    }

    public int codeToGID(int code2) throws IOException, NumberFormatException {
        Integer macCode;
        String unicode;
        extractCmapTable();
        int gid = 0;
        if (!isSymbolic()) {
            String name = this.encoding.getName(code2);
            if (".notdef".equals(name)) {
                return 0;
            }
            if (this.cmapWinUnicode != null && (unicode = GlyphList.getAdobeGlyphList().toUnicode(name)) != null) {
                int uni = unicode.codePointAt(0);
                gid = this.cmapWinUnicode.getGlyphId(uni);
            }
            if (gid == 0 && this.cmapMacRoman != null && (macCode = INVERTED_MACOS_ROMAN.get(name)) != null) {
                gid = this.cmapMacRoman.getGlyphId(macCode.intValue());
            }
            if (gid == 0) {
                gid = this.ttf.nameToGID(name);
            }
        } else {
            if (0 == 0 && this.cmapWinUnicode != null) {
                if ((this.encoding instanceof WinAnsiEncoding) || (this.encoding instanceof MacRomanEncoding)) {
                    String name2 = this.encoding.getName(code2);
                    if (".notdef".equals(name2)) {
                        return 0;
                    }
                    String unicode2 = GlyphList.getAdobeGlyphList().toUnicode(name2);
                    if (unicode2 != null) {
                        int uni2 = unicode2.codePointAt(0);
                        gid = this.cmapWinUnicode.getGlyphId(uni2);
                    }
                } else {
                    gid = this.cmapWinUnicode.getGlyphId(code2);
                }
            }
            if (this.cmapWinSymbol != null) {
                gid = this.cmapWinSymbol.getGlyphId(code2);
                if (code2 >= 0 && code2 <= 255) {
                    if (gid == 0) {
                        gid = this.cmapWinSymbol.getGlyphId(code2 + START_RANGE_F000);
                    }
                    if (gid == 0) {
                        gid = this.cmapWinSymbol.getGlyphId(code2 + START_RANGE_F100);
                    }
                    if (gid == 0) {
                        gid = this.cmapWinSymbol.getGlyphId(code2 + START_RANGE_F200);
                    }
                }
            }
            if (gid == 0 && this.cmapMacRoman != null) {
                gid = this.cmapMacRoman.getGlyphId(code2);
            }
        }
        return gid;
    }

    private void extractCmapTable() throws IOException {
        if (this.cmapInitialized) {
            return;
        }
        CmapTable cmapTable = this.ttf.getCmap();
        if (cmapTable != null) {
            CmapSubtable[] cmaps = cmapTable.getCmaps();
            for (CmapSubtable cmap : cmaps) {
                if (3 == cmap.getPlatformId()) {
                    if (1 == cmap.getPlatformEncodingId()) {
                        this.cmapWinUnicode = cmap;
                    } else if (0 == cmap.getPlatformEncodingId()) {
                        this.cmapWinSymbol = cmap;
                    }
                } else if (1 == cmap.getPlatformId() && 0 == cmap.getPlatformEncodingId()) {
                    this.cmapMacRoman = cmap;
                } else if (0 == cmap.getPlatformId() && 0 == cmap.getPlatformEncodingId()) {
                    this.cmapWinUnicode = cmap;
                } else if (0 == cmap.getPlatformId() && 3 == cmap.getPlatformEncodingId()) {
                    this.cmapWinUnicode = cmap;
                }
            }
        }
        this.cmapInitialized = true;
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
