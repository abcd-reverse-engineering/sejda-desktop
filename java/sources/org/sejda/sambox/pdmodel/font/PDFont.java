package org.sejda.sambox.pdmodel.font;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.fontbox.afm.FontMetrics;
import org.apache.fontbox.cmap.CMap;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.commons.util.IOUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.font.encoding.GlyphList;
import org.sejda.sambox.util.Matrix;
import org.sejda.sambox.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDFont.class */
public abstract class PDFont implements COSObjectable, PDFontLike, Subsettable {
    private static final Logger LOG = LoggerFactory.getLogger(PDFont.class);
    protected static final Matrix DEFAULT_FONT_MATRIX = new Matrix(0.001f, 0.0f, 0.0f, 0.001f, 0.0f, 0.0f);
    protected final COSDictionary dict;
    private final CMap toUnicodeCMap;
    private final FontMetrics afmStandard14;
    private PDFontDescriptor fontDescriptor;
    private List<Float> widths;
    private float avgFontWidth;
    private float fontWidthOfSpace;
    private final Map<Integer, Float> codeToWidthMap;
    private final Map<String, String> transientMetadata;

    protected abstract float getStandard14Width(int i);

    protected abstract byte[] encode(int i) throws IOException;

    public abstract int readCode(InputStream inputStream) throws IOException;

    public abstract boolean isVertical();

    PDFont() {
        this.fontWidthOfSpace = -1.0f;
        this.transientMetadata = new HashMap();
        this.dict = new COSDictionary();
        this.dict.setItem(COSName.TYPE, (COSBase) COSName.FONT);
        this.toUnicodeCMap = null;
        this.fontDescriptor = null;
        this.afmStandard14 = null;
        this.codeToWidthMap = new HashMap();
    }

    PDFont(String baseFont) {
        this.fontWidthOfSpace = -1.0f;
        this.transientMetadata = new HashMap();
        this.dict = new COSDictionary();
        this.dict.setItem(COSName.TYPE, (COSBase) COSName.FONT);
        this.toUnicodeCMap = null;
        this.afmStandard14 = Standard14Fonts.getAFM(baseFont);
        if (this.afmStandard14 == null) {
            throw new IllegalArgumentException("No AFM for font " + baseFont);
        }
        this.fontDescriptor = PDType1FontEmbedder.buildFontDescriptor(this.afmStandard14);
        this.codeToWidthMap = new ConcurrentHashMap();
    }

    protected PDFont(COSDictionary fontDictionary) throws IOException {
        this.fontWidthOfSpace = -1.0f;
        this.transientMetadata = new HashMap();
        this.dict = fontDictionary;
        this.codeToWidthMap = new HashMap();
        this.afmStandard14 = Standard14Fonts.getAFM(getName());
        this.fontDescriptor = loadFontDescriptor();
        this.toUnicodeCMap = loadUnicodeCmap();
    }

    private PDFontDescriptor loadFontDescriptor() {
        COSDictionary fd = (COSDictionary) this.dict.getDictionaryObject(COSName.FONT_DESC, COSDictionary.class);
        if (Objects.nonNull(fd)) {
            return new PDFontDescriptor(fd);
        }
        if (this.afmStandard14 != null) {
            return PDType1FontEmbedder.buildFontDescriptor(this.afmStandard14);
        }
        return null;
    }

    private CMap loadUnicodeCmap() {
        COSDictionary encodingDict;
        COSBase toUnicode = this.dict.getDictionaryObject(COSName.TO_UNICODE);
        if (toUnicode == null) {
            return null;
        }
        CMap cmap = null;
        try {
            cmap = readCMap(toUnicode);
            if (cmap != null && !cmap.hasUnicodeMappings()) {
                LOG.warn("Invalid ToUnicode CMap in font " + getName());
                String cmapName = cmap.getName() != null ? cmap.getName() : "";
                String ordering = cmap.getOrdering() != null ? cmap.getOrdering() : "";
                COSBase encoding = this.dict.getDictionaryObject(COSName.ENCODING);
                if ((cmapName.contains("Identity") || ordering.contains("Identity") || COSName.IDENTITY_H.equals(encoding) || COSName.IDENTITY_V.equals(encoding)) && ((encodingDict = (COSDictionary) this.dict.getDictionaryObject(COSName.ENCODING, COSDictionary.class)) == null || !encodingDict.containsKey(COSName.DIFFERENCES))) {
                    cmap = CMapManager.getPredefinedCMap(COSName.IDENTITY_H.getName());
                    LOG.warn("Using predefined identity CMap instead");
                }
            }
        } catch (IOException ex) {
            LOG.error("Could not read ToUnicode CMap in font " + getName(), ex);
        }
        return cmap;
    }

    protected final FontMetrics getStandard14AFM() {
        return this.afmStandard14;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public PDFontDescriptor getFontDescriptor() {
        return this.fontDescriptor;
    }

    protected final void setFontDescriptor(PDFontDescriptor fontDescriptor) {
        this.fontDescriptor = fontDescriptor;
    }

    protected final CMap readCMap(COSBase base) throws IOException {
        if (base instanceof COSName) {
            String name = ((COSName) base).getName();
            return CMapManager.getPredefinedCMap(name);
        }
        if (base instanceof COSStream) {
            InputStream input = null;
            try {
                input = ((COSStream) base).getUnfilteredStream();
                CMap cMap = CMapManager.parseCMap(input);
                IOUtils.closeQuietly(input);
                return cMap;
            } catch (Throwable th) {
                IOUtils.closeQuietly(input);
                throw th;
            }
        }
        throw new IOException("Expected Name or Stream");
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dict;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public Vector getPositionVector(int code2) {
        throw new UnsupportedOperationException("Horizontal fonts have no position vector");
    }

    public Vector getDisplacement(int code2) throws IOException {
        return new Vector(getWidth(code2) / 1000.0f, 0.0f);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getWidth(int code2) throws IOException {
        Float width = this.codeToWidthMap.get(Integer.valueOf(code2));
        if (width != null) {
            return width.floatValue();
        }
        if (this.dict.getDictionaryObject(COSName.WIDTHS) != null || this.dict.containsKey(COSName.MISSING_WIDTH)) {
            int firstChar = this.dict.getInt(COSName.FIRST_CHAR, -1);
            int lastChar = this.dict.getInt(COSName.LAST_CHAR, -1);
            int siz = getWidths().size();
            int idx = code2 - firstChar;
            if (siz > 0 && code2 >= firstChar && code2 <= lastChar && idx < siz) {
                Float width2 = getWidths().get(idx);
                if (width2 == null) {
                    width2 = Float.valueOf(0.0f);
                }
                this.codeToWidthMap.put(Integer.valueOf(code2), width2);
                return width2.floatValue();
            }
            PDFontDescriptor fd = getFontDescriptor();
            if (fd != null) {
                Float width3 = Float.valueOf(fd.getMissingWidth());
                this.codeToWidthMap.put(Integer.valueOf(code2), width3);
                return width3.floatValue();
            }
        }
        if (isStandard14()) {
            Float width4 = Float.valueOf(getStandard14Width(code2));
            this.codeToWidthMap.put(Integer.valueOf(code2), width4);
            return width4.floatValue();
        }
        Float width5 = Float.valueOf(getWidthFromFont(code2));
        this.codeToWidthMap.put(Integer.valueOf(code2), width5);
        return width5.floatValue();
    }

    public final byte[] encode(String text) throws IOException {
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        int iCharCount = 0;
        while (true) {
            int offset = iCharCount;
            if (offset < text.length()) {
                int codePoint = text.codePointAt(offset);
                byte[] bytes = encode(codePoint);
                out.write(bytes);
                iCharCount = offset + Character.charCount(codePoint);
            } else {
                return out.toByteArray();
            }
        }
    }

    public final byte[] encodeLeniently(String text) throws IOException {
        byte[] bytes;
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        int iCharCount = 0;
        while (true) {
            int offset = iCharCount;
            if (offset < text.length()) {
                int codePoint = text.codePointAt(offset);
                try {
                    bytes = encode(codePoint);
                } catch (IllegalArgumentException e) {
                    if (e.getMessage().contains("No glyph") || e.getMessage().contains("is not available in this")) {
                        bytes = new byte[]{(byte) codePoint};
                    } else {
                        throw e;
                    }
                }
                out.write(bytes);
                iCharCount = offset + Character.charCount(codePoint);
            } else {
                return out.toByteArray();
            }
        }
    }

    public float getStringWidth(String text) throws IOException {
        byte[] bytes = encode(text);
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        float width = 0.0f;
        while (true) {
            float width2 = width;
            if (in.available() > 0) {
                int code2 = readCode(in);
                width = width2 + getWidth(code2);
            } else {
                return width2;
            }
        }
    }

    public float getStringWidthLeniently(String text) throws IOException {
        byte[] bytes = encodeLeniently(text);
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        float width = 0.0f;
        while (true) {
            float width2 = width;
            if (in.available() > 0) {
                int code2 = readCode(in);
                width = width2 + getWidth(code2);
            } else {
                return width2;
            }
        }
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getAverageFontWidth() {
        float average;
        if (this.avgFontWidth != 0.0f) {
            average = this.avgFontWidth;
        } else {
            float totalWidth = 0.0f;
            float characterCount = 0.0f;
            COSArray widths = (COSArray) this.dict.getDictionaryObject(COSName.WIDTHS, COSArray.class);
            if (widths != null) {
                for (int i = 0; i < widths.size(); i++) {
                    COSNumber fontWidth = (COSNumber) widths.getObject(i, COSNumber.class);
                    if (Objects.nonNull(fontWidth)) {
                        float floatValue = fontWidth.floatValue();
                        if (floatValue > 0.0f) {
                            totalWidth += floatValue;
                            characterCount += 1.0f;
                        }
                    }
                }
            }
            if (totalWidth > 0.0f) {
                average = totalWidth / characterCount;
            } else {
                average = 0.0f;
            }
            this.avgFontWidth = average;
        }
        return average;
    }

    public String toUnicode(int code2, GlyphList customGlyphList) throws IOException {
        return toUnicode(code2);
    }

    public String toUnicode(int code2) throws IOException {
        if (this.toUnicodeCMap != null) {
            if (this.toUnicodeCMap.getName() != null && this.toUnicodeCMap.getName().startsWith("Identity-") && ((this.dict.getDictionaryObject(COSName.TO_UNICODE) instanceof COSName) || !this.toUnicodeCMap.hasUnicodeMappings())) {
                return String.valueOf((char) code2);
            }
            return this.toUnicodeCMap.toUnicode(code2);
        }
        return null;
    }

    public String getType() {
        return this.dict.getNameAsString(COSName.TYPE);
    }

    public String getSubType() {
        return this.dict.getNameAsString(COSName.SUBTYPE);
    }

    protected final List<Float> getWidths() {
        if (this.widths == null) {
            COSArray array = (COSArray) this.dict.getDictionaryObject(COSName.WIDTHS, COSArray.class);
            if (array != null) {
                this.widths = COSArrayList.convertFloatCOSArrayToList(array);
            } else {
                this.widths = Collections.emptyList();
            }
        }
        return this.widths;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public Matrix getFontMatrix() {
        return DEFAULT_FONT_MATRIX;
    }

    public float getSpaceWidth() {
        if (this.fontWidthOfSpace == -1.0f) {
            try {
                if (this.toUnicodeCMap != null && this.dict.containsKey(COSName.TO_UNICODE)) {
                    int spaceMapping = this.toUnicodeCMap.getSpaceMapping();
                    if (spaceMapping > -1) {
                        this.fontWidthOfSpace = getWidth(spaceMapping);
                    }
                } else {
                    this.fontWidthOfSpace = getWidth(32);
                }
                if (this.fontWidthOfSpace <= 0.0f) {
                    this.fontWidthOfSpace = getWidthFromFont(32);
                    if (this.fontWidthOfSpace <= 0.0f) {
                        this.fontWidthOfSpace = getAverageFontWidth();
                    }
                }
            } catch (Exception e) {
                LOG.error("Can't determine the width of the space character, assuming 250", e);
                this.fontWidthOfSpace = 250.0f;
            }
        }
        return this.fontWidthOfSpace;
    }

    public boolean isStandard14() {
        if (isEmbedded()) {
            return false;
        }
        return Standard14Fonts.containsName(getName());
    }

    public boolean equals(Object other) {
        return (other instanceof PDFont) && ((PDFont) other).getCOSObject() == getCOSObject();
    }

    public int hashCode() {
        return getCOSObject().hashCode();
    }

    public String toString() {
        return getClass().getSimpleName() + " " + getName();
    }

    protected CMap getToUnicodeCMap() {
        return this.toUnicodeCMap;
    }

    public Map<String, String> getTransientMetadata() {
        return this.transientMetadata;
    }
}
