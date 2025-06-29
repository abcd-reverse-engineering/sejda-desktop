package org.sejda.sambox.pdmodel.font;

import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.fontbox.FontBoxFont;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.font.encoding.DictionaryEncoding;
import org.sejda.sambox.pdmodel.font.encoding.Encoding;
import org.sejda.sambox.pdmodel.font.encoding.GlyphList;
import org.sejda.sambox.pdmodel.font.encoding.MacRomanEncoding;
import org.sejda.sambox.pdmodel.font.encoding.StandardEncoding;
import org.sejda.sambox.pdmodel.font.encoding.WinAnsiEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDSimpleFont.class */
public abstract class PDSimpleFont extends PDFont {
    private static final Logger LOG = LoggerFactory.getLogger(PDSimpleFont.class);
    protected Encoding encoding;
    protected GlyphList glyphList;
    private Boolean isSymbolic;
    private final Set<Integer> noUnicode;

    protected abstract Encoding readEncodingFromFont() throws IOException;

    public abstract GeneralPath getPath(String str) throws IOException;

    public abstract boolean hasGlyph(String str) throws IOException;

    public abstract FontBoxFont getFontBoxFont();

    PDSimpleFont() {
        this.noUnicode = new HashSet();
    }

    PDSimpleFont(String baseFont) {
        super(baseFont);
        this.noUnicode = new HashSet();
        assignGlyphList(baseFont);
    }

    PDSimpleFont(COSDictionary fontDictionary) throws IOException {
        super(fontDictionary);
        this.noUnicode = new HashSet();
    }

    protected void readEncoding() throws IOException {
        COSBase encodingBase = this.dict.getDictionaryObject(COSName.ENCODING);
        if (encodingBase instanceof COSName) {
            COSName encodingName = (COSName) encodingBase;
            this.encoding = Encoding.getInstance(encodingName);
            if (this.encoding == null) {
                LOG.warn("Unknown encoding: " + encodingName.getName());
                this.encoding = readEncodingFromFont();
            }
        } else if (encodingBase instanceof COSDictionary) {
            COSDictionary encodingDict = (COSDictionary) encodingBase;
            Encoding builtIn = null;
            Boolean symbolic = getSymbolicFlag();
            COSName baseEncoding = encodingDict.getCOSName(COSName.BASE_ENCODING);
            boolean hasValidBaseEncoding = (baseEncoding == null || Encoding.getInstance(baseEncoding) == null) ? false : true;
            if (!hasValidBaseEncoding && Boolean.TRUE.equals(symbolic)) {
                builtIn = readEncodingFromFont();
            }
            if (symbolic == null) {
                symbolic = false;
            }
            this.encoding = new DictionaryEncoding(encodingDict, !symbolic.booleanValue(), builtIn);
        } else {
            this.encoding = readEncodingFromFont();
        }
        String standard14Name = Standard14Fonts.getMappedFontName(getName());
        assignGlyphList(standard14Name);
    }

    public Encoding getEncoding() {
        return this.encoding;
    }

    public GlyphList getGlyphList() {
        return this.glyphList;
    }

    public final boolean isSymbolic() {
        if (this.isSymbolic == null) {
            Boolean result = isFontSymbolic();
            this.isSymbolic = (Boolean) Objects.requireNonNullElse(result, true);
        }
        return this.isSymbolic.booleanValue();
    }

    protected Boolean isFontSymbolic() {
        Boolean result = getSymbolicFlag();
        if (result != null) {
            return result;
        }
        if (isStandard14()) {
            String mappedName = Standard14Fonts.getMappedFontName(getName());
            return Boolean.valueOf(mappedName.equals("Symbol") || mappedName.equals("ZapfDingbats"));
        }
        if (this.encoding == null) {
            if (!(this instanceof PDTrueTypeFont)) {
                throw new IllegalStateException("Encoding should not be null!");
            }
            return true;
        }
        if ((this.encoding instanceof WinAnsiEncoding) || (this.encoding instanceof MacRomanEncoding) || (this.encoding instanceof StandardEncoding)) {
            return false;
        }
        if (this.encoding instanceof DictionaryEncoding) {
            for (String name : ((DictionaryEncoding) this.encoding).getDifferences().values()) {
                if (!".notdef".equals(name) && (!WinAnsiEncoding.INSTANCE.contains(name) || !MacRomanEncoding.INSTANCE.contains(name) || !StandardEncoding.INSTANCE.contains(name))) {
                    return true;
                }
            }
            return false;
        }
        return null;
    }

    protected final Boolean getSymbolicFlag() {
        if (getFontDescriptor() != null) {
            return Boolean.valueOf(getFontDescriptor().isSymbolic());
        }
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public String toUnicode(int code2) throws IOException {
        return toUnicode(code2, GlyphList.getAdobeGlyphList());
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public String toUnicode(int code2, GlyphList customGlyphList) throws IOException, NumberFormatException {
        GlyphList unicodeGlyphList;
        if (this.glyphList == GlyphList.getAdobeGlyphList()) {
            unicodeGlyphList = customGlyphList;
        } else {
            unicodeGlyphList = this.glyphList;
        }
        String unicode = super.toUnicode(code2);
        if (unicode != null) {
            return unicode;
        }
        String name = null;
        if (this.encoding != null) {
            name = this.encoding.getName(code2);
            String unicode2 = unicodeGlyphList.toUnicode(name);
            if (unicode2 != null) {
                return unicode2;
            }
        }
        if (LOG.isWarnEnabled() && !this.noUnicode.contains(Integer.valueOf(code2))) {
            this.noUnicode.add(Integer.valueOf(code2));
            if (name != null) {
                LOG.warn("No Unicode mapping for " + name + " (" + code2 + ") in font " + getName());
                return null;
            }
            LOG.warn("No Unicode mapping for character code " + code2 + " in font " + getName());
            return null;
        }
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public boolean isVertical() {
        return false;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    protected final float getStandard14Width(int code2) {
        if (getStandard14AFM() != null) {
            String nameInAFM = getEncoding().getName(code2);
            if (".notdef".equals(nameInAFM)) {
                return 250.0f;
            }
            if ("nbspace".equals(nameInAFM)) {
                nameInAFM = "space";
            } else if ("sfthyphen".equals(nameInAFM)) {
                nameInAFM = "hyphen";
            }
            return getStandard14AFM().getCharacterWidth(nameInAFM);
        }
        throw new IllegalStateException("No AFM");
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public boolean isStandard14() {
        Encoding encoding = getEncoding();
        if (encoding instanceof DictionaryEncoding) {
            DictionaryEncoding dictionary = (DictionaryEncoding) encoding;
            if (dictionary.getDifferences().size() > 0) {
                Encoding baseEncoding = dictionary.getBaseEncoding();
                if (Objects.isNull(baseEncoding)) {
                    return false;
                }
                for (Map.Entry<Integer, String> entry : dictionary.getDifferences().entrySet()) {
                    if (!entry.getValue().equals(baseEncoding.getName(entry.getKey().intValue()))) {
                        return false;
                    }
                }
            }
        }
        return super.isStandard14();
    }

    @Override // org.sejda.sambox.pdmodel.font.Subsettable
    public void addToSubset(int codePoint) {
        throw new UnsupportedOperationException();
    }

    @Override // org.sejda.sambox.pdmodel.font.Subsettable
    public void subset() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // org.sejda.sambox.pdmodel.font.Subsettable
    public boolean willBeSubset() {
        return false;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean hasExplicitWidth(int code2) throws IOException {
        int firstChar;
        return this.dict.containsKey(COSName.WIDTHS) && code2 >= (firstChar = this.dict.getInt(COSName.FIRST_CHAR, -1)) && code2 - firstChar < getWidths().size();
    }

    private void assignGlyphList(String baseFont) {
        if ("ZapfDingbats".equals(baseFont)) {
            this.glyphList = GlyphList.getZapfDingbats();
        } else {
            this.glyphList = GlyphList.getAdobeGlyphList();
        }
    }
}
