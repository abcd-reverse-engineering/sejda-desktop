package org.sejda.sambox.pdmodel.font;

import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.fontbox.cmap.CMap;
import org.apache.fontbox.ttf.CmapLookup;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.fontbox.util.BoundingBox;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.util.Matrix;
import org.sejda.sambox.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDType0Font.class */
public class PDType0Font extends PDFont implements PDVectorFont {
    private static final Logger LOG = LoggerFactory.getLogger(PDType0Font.class);
    private final PDCIDFont descendantFont;
    private CMap cMap;
    private CMap cMapUCS2;
    private boolean isCMapPredefined;
    private boolean isDescendantCJK;
    private PDCIDFontType2Embedder embedder;
    private final Set<Integer> noUnicode;
    private TrueTypeFont ttf;

    public static PDType0Font load(PDDocument doc, File file) throws IOException {
        TrueTypeFont ttf = new TTFParser().parse(file);
        boolean embedSubset = FontUtils.isSubsettingPermitted(ttf);
        return new PDType0Font(doc, ttf, embedSubset, true, false);
    }

    public static PDType0Font load(PDDocument doc, InputStream input) throws IOException {
        TrueTypeFont ttf = new TTFParser().parse(input);
        boolean embedSubset = FontUtils.isSubsettingPermitted(ttf);
        return new PDType0Font(doc, ttf, embedSubset, true, false);
    }

    public static PDType0Font load(PDDocument doc, InputStream input, boolean embedSubset) throws IOException {
        return new PDType0Font(doc, new TTFParser().parse(input), embedSubset, true, false);
    }

    public static PDType0Font load(PDDocument doc, TrueTypeFont ttf, boolean embedSubset) throws IOException {
        return new PDType0Font(doc, ttf, embedSubset, false, false);
    }

    public static PDType0Font loadVertical(PDDocument doc, File file) throws IOException {
        return new PDType0Font(doc, new TTFParser().parse(file), true, true, true);
    }

    public static PDType0Font loadVertical(PDDocument doc, InputStream input) throws IOException {
        return new PDType0Font(doc, new TTFParser().parse(input), true, true, true);
    }

    public static PDType0Font loadVertical(PDDocument doc, InputStream input, boolean embedSubset) throws IOException {
        return new PDType0Font(doc, new TTFParser().parse(input), embedSubset, true, true);
    }

    public static PDType0Font loadVertical(PDDocument doc, TrueTypeFont ttf, boolean embedSubset) throws IOException {
        return new PDType0Font(doc, ttf, embedSubset, false, true);
    }

    public PDType0Font(COSDictionary fontDictionary) throws IOException {
        super(fontDictionary);
        this.noUnicode = new HashSet();
        COSArray descendantFonts = (COSArray) this.dict.getDictionaryObject(COSName.DESCENDANT_FONTS, COSArray.class);
        if (Objects.isNull(descendantFonts)) {
            throw new IOException("Missing descendant font array");
        }
        if (descendantFonts.size() == 0) {
            throw new IOException("Descendant font array is empty");
        }
        COSDictionary descendantFontDict = (COSDictionary) descendantFonts.getObject(0, COSDictionary.class);
        if (Objects.isNull(descendantFontDict)) {
            throw new IOException("Missing descendant font dictionary");
        }
        if (!COSName.FONT.equals(descendantFontDict.getDictionaryObject(COSName.TYPE, COSName.FONT, COSName.class))) {
            throw new IOException("Missing or wrong type in descendant font dictionary");
        }
        this.descendantFont = PDFontFactory.createDescendantFont(descendantFontDict, this);
        readEncoding();
        fetchCMapUCS2();
    }

    private PDType0Font(PDDocument document, TrueTypeFont ttf, boolean embedSubset, boolean closeTTF, boolean vertical) throws IllegalStateException, IOException {
        this.noUnicode = new HashSet();
        if (vertical) {
            ttf.enableVerticalSubstitutions();
        }
        this.embedder = new PDCIDFontType2Embedder(document, this.dict, ttf, embedSubset, this, vertical);
        this.descendantFont = this.embedder.getCIDFont();
        readEncoding();
        fetchCMapUCS2();
        if (closeTTF) {
            if (embedSubset) {
                this.ttf = ttf;
                document.registerTrueTypeFontForClosing(ttf);
            } else {
                ttf.close();
            }
        }
    }

    @Override // org.sejda.sambox.pdmodel.font.Subsettable
    public void addToSubset(int codePoint) {
        if (!willBeSubset()) {
            throw new IllegalStateException("This font was created with subsetting disabled");
        }
        this.embedder.addToSubset(codePoint);
    }

    @Override // org.sejda.sambox.pdmodel.font.Subsettable
    public void subset() throws IOException {
        if (!willBeSubset()) {
            throw new IllegalStateException("This font was created with subsetting disabled");
        }
        this.embedder.subset();
        if (Objects.nonNull(this.ttf)) {
            this.ttf.close();
            this.ttf = null;
        }
    }

    @Override // org.sejda.sambox.pdmodel.font.Subsettable
    public boolean willBeSubset() {
        return this.embedder != null && this.embedder.needsSubset();
    }

    private void readEncoding() throws IOException {
        COSBase encoding = this.dict.getDictionaryObject(COSName.ENCODING);
        if (encoding instanceof COSName) {
            COSName encodingName = (COSName) encoding;
            this.cMap = CMapManager.getPredefinedCMap(encodingName.getName());
            this.isCMapPredefined = true;
        } else if (encoding != null) {
            this.cMap = readCMap(encoding);
            if (this.cMap == null) {
                throw new IOException("Missing required CMap");
            }
            if (!this.cMap.hasCIDMappings()) {
                LOG.warn("Invalid Encoding CMap in font " + getName());
            }
        }
        PDCIDSystemInfo ros = this.descendantFont.getCIDSystemInfo();
        if (ros != null) {
            String ordering = ros.getOrdering();
            this.isDescendantCJK = "Adobe".equals(ros.getRegistry()) && ("GB1".equals(ordering) || "CNS1".equals(ordering) || "Japan1".equals(ordering) || "Korea1".equals(ordering));
        }
    }

    private void fetchCMapUCS2() throws IOException {
        COSName name = this.dict.getCOSName(COSName.ENCODING);
        if ((this.isCMapPredefined && name != COSName.IDENTITY_H && name != COSName.IDENTITY_V) || this.isDescendantCJK) {
            String strName = null;
            if (this.isDescendantCJK) {
                PDCIDSystemInfo cidSystemInfo = this.descendantFont.getCIDSystemInfo();
                if (cidSystemInfo != null) {
                    strName = cidSystemInfo.getRegistry() + "-" + cidSystemInfo.getOrdering() + "-" + cidSystemInfo.getSupplement();
                }
            } else if (name != null) {
                strName = name.getName();
            }
            if (strName != null) {
                try {
                    CMap prdCMap = CMapManager.getPredefinedCMap(strName);
                    String ucs2Name = prdCMap.getRegistry() + "-" + prdCMap.getOrdering() + "-UCS2";
                    this.cMapUCS2 = CMapManager.getPredefinedCMap(ucs2Name);
                } catch (IOException ex) {
                    LOG.warn("Could not get {} UC2 map for font {}", new Object[]{strName, getName(), ex});
                }
            }
        }
    }

    public String getBaseFont() {
        return this.dict.getNameAsString(COSName.BASE_FONT);
    }

    public PDCIDFont getDescendantFont() {
        return this.descendantFont;
    }

    public CMap getCMap() {
        return this.cMap;
    }

    public CMap getCMapUCS2() {
        return this.cMapUCS2;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public PDFontDescriptor getFontDescriptor() {
        return this.descendantFont.getFontDescriptor();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public Matrix getFontMatrix() {
        return this.descendantFont.getFontMatrix();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public boolean isVertical() {
        return this.cMap != null && this.cMap.getWMode() == 1;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getHeight(int code2) throws IOException {
        return this.descendantFont.getHeight(code2);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    protected byte[] encode(int unicode) throws IOException {
        return this.descendantFont.encode(unicode);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean hasExplicitWidth(int code2) throws IOException {
        return this.descendantFont.hasExplicitWidth(code2);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public float getAverageFontWidth() {
        return this.descendantFont.getAverageFontWidth();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public Vector getPositionVector(int code2) {
        return this.descendantFont.getPositionVector(code2).scale(-0.001f);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public Vector getDisplacement(int code2) throws IOException {
        if (isVertical()) {
            return new Vector(0.0f, this.descendantFont.getVerticalDisplacementVectorY(code2) / 1000.0f);
        }
        return super.getDisplacement(code2);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public float getWidth(int code2) throws IOException {
        return this.descendantFont.getWidth(code2);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    protected float getStandard14Width(int code2) {
        throw new UnsupportedOperationException("not supported");
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getWidthFromFont(int code2) throws IOException {
        return this.descendantFont.getWidthFromFont(code2);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isEmbedded() {
        return this.descendantFont.isEmbedded();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public String toUnicode(int code2) throws IOException {
        TrueTypeFont font;
        int gid;
        String unicode = super.toUnicode(code2);
        if (unicode != null) {
            return unicode;
        }
        if ((this.isCMapPredefined || this.isDescendantCJK) && this.cMapUCS2 != null) {
            int cid = codeToCID(code2);
            return this.cMapUCS2.toUnicode(cid);
        }
        if ((this.descendantFont instanceof PDCIDFontType2) && (font = ((PDCIDFontType2) this.descendantFont).getTrueTypeFont()) != null) {
            try {
                CmapLookup cmap = font.getUnicodeCmapLookup(false);
                if (cmap != null) {
                    if (this.descendantFont.isEmbedded()) {
                        gid = this.descendantFont.codeToGID(code2);
                    } else {
                        gid = this.descendantFont.codeToCID(code2);
                    }
                    List<Integer> codes = cmap.getCharCodes(gid);
                    if (codes != null && !codes.isEmpty()) {
                        return Character.toString((char) codes.get(0).intValue());
                    }
                }
            } catch (IOException | NullPointerException e) {
                LOG.warn("get unicode from font cmap fail", e);
            }
        }
        if (LOG.isWarnEnabled() && !this.noUnicode.contains(Integer.valueOf(code2))) {
            String cid2 = "CID+" + codeToCID(code2);
            LOG.warn("No Unicode mapping for " + cid2 + " (" + code2 + ") in font " + getName());
            this.noUnicode.add(Integer.valueOf(code2));
            return null;
        }
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public String getName() {
        return getBaseFont();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public BoundingBox getBoundingBox() throws IOException {
        return this.descendantFont.getBoundingBox();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public int readCode(InputStream in) throws IOException {
        if (this.cMap == null) {
            throw new IOException("required cmap is null");
        }
        return this.cMap.readCode(in);
    }

    public int codeToCID(int code2) {
        return this.descendantFont.codeToCID(code2);
    }

    public int codeToGID(int code2) throws IOException {
        return this.descendantFont.codeToGID(code2);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public boolean isStandard14() {
        return false;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isDamaged() {
        return this.descendantFont.isDamaged();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isOriginalEmbeddedMissing() {
        return this.descendantFont.isOriginalEmbeddedMissing();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isMappingFallbackUsed() {
        return this.descendantFont.isMappingFallbackUsed();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public String toString() {
        String descendant = null;
        if (getDescendantFont() != null) {
            descendant = getDescendantFont().getClass().getSimpleName();
        }
        return getClass().getSimpleName() + "/" + descendant + ", PostScript name: " + getBaseFont();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDVectorFont
    public GeneralPath getPath(int code2) throws IOException {
        return this.descendantFont.getPath(code2);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDVectorFont
    public boolean hasGlyph(int code2) throws IOException {
        return this.descendantFont.hasGlyph(code2);
    }
}
