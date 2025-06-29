package org.sejda.sambox.pdmodel.font;

import java.util.Optional;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDFontDescriptor.class */
public final class PDFontDescriptor implements COSObjectable {
    private static final int FLAG_FIXED_PITCH = 1;
    private static final int FLAG_SERIF = 2;
    private static final int FLAG_SYMBOLIC = 4;
    private static final int FLAG_SCRIPT = 8;
    private static final int FLAG_NON_SYMBOLIC = 32;
    private static final int FLAG_ITALIC = 64;
    private static final int FLAG_ALL_CAP = 65536;
    private static final int FLAG_SMALL_CAP = 131072;
    private static final int FLAG_FORCE_BOLD = 262144;
    private final COSDictionary dic;
    private float xHeight;
    private float capHeight;
    private int flags;

    PDFontDescriptor() {
        this.xHeight = Float.NEGATIVE_INFINITY;
        this.capHeight = Float.NEGATIVE_INFINITY;
        this.flags = -1;
        this.dic = new COSDictionary();
        this.dic.setItem(COSName.TYPE, (COSBase) COSName.FONT_DESC);
    }

    public PDFontDescriptor(COSDictionary desc) {
        this.xHeight = Float.NEGATIVE_INFINITY;
        this.capHeight = Float.NEGATIVE_INFINITY;
        this.flags = -1;
        this.dic = desc;
    }

    public boolean isFixedPitch() {
        return isFlagBitOn(1);
    }

    public void setFixedPitch(boolean flag) {
        setFlagBit(1, flag);
    }

    public boolean isSerif() {
        return isFlagBitOn(2);
    }

    public void setSerif(boolean flag) {
        setFlagBit(2, flag);
    }

    public boolean isSymbolic() {
        return isFlagBitOn(4);
    }

    public void setSymbolic(boolean flag) {
        setFlagBit(4, flag);
    }

    public boolean isScript() {
        return isFlagBitOn(8);
    }

    public void setScript(boolean flag) {
        setFlagBit(8, flag);
    }

    public boolean isNonSymbolic() {
        return isFlagBitOn(32);
    }

    public void setNonSymbolic(boolean flag) {
        setFlagBit(32, flag);
    }

    public boolean isItalic() {
        return isFlagBitOn(64);
    }

    public void setItalic(boolean flag) {
        setFlagBit(64, flag);
    }

    public boolean isAllCap() {
        return isFlagBitOn(FLAG_ALL_CAP);
    }

    public void setAllCap(boolean flag) {
        setFlagBit(FLAG_ALL_CAP, flag);
    }

    public boolean isSmallCap() {
        return isFlagBitOn(FLAG_SMALL_CAP);
    }

    public void setSmallCap(boolean flag) {
        setFlagBit(FLAG_SMALL_CAP, flag);
    }

    public boolean isForceBold() {
        return isFlagBitOn(FLAG_FORCE_BOLD);
    }

    public void setForceBold(boolean flag) {
        setFlagBit(FLAG_FORCE_BOLD, flag);
    }

    private boolean isFlagBitOn(int bit) {
        return (getFlags() & bit) != 0;
    }

    private void setFlagBit(int bit, boolean value) {
        int flags;
        int flags2 = getFlags();
        if (value) {
            flags = flags2 | bit;
        } else {
            flags = flags2 & (bit ^ (-1));
        }
        setFlags(flags);
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dic;
    }

    public String getFontName() {
        return (String) Optional.ofNullable((COSName) this.dic.getDictionaryObject(COSName.FONT_NAME, COSName.class)).map((v0) -> {
            return v0.getName();
        }).orElse(null);
    }

    public void setFontName(String fontName) {
        COSName name = null;
        if (fontName != null) {
            name = COSName.getPDFName(fontName);
        }
        this.dic.setItem(COSName.FONT_NAME, (COSBase) name);
    }

    public String getFontFamily() {
        return (String) Optional.ofNullable((COSString) this.dic.getDictionaryObject(COSName.FONT_FAMILY, COSString.class)).map((v0) -> {
            return v0.getString();
        }).orElse(null);
    }

    public void setFontFamily(String fontFamily) {
        COSString name = null;
        if (fontFamily != null) {
            name = COSString.parseLiteral(fontFamily);
        }
        this.dic.setItem(COSName.FONT_FAMILY, (COSBase) name);
    }

    public float getFontWeight() {
        return this.dic.getFloat(COSName.FONT_WEIGHT, 0.0f);
    }

    public void setFontWeight(float fontWeight) {
        this.dic.setFloat(COSName.FONT_WEIGHT, fontWeight);
    }

    public String getFontStretch() {
        return (String) Optional.ofNullable((COSName) this.dic.getDictionaryObject(COSName.FONT_STRETCH, COSName.class)).map((v0) -> {
            return v0.getName();
        }).orElse(null);
    }

    public void setFontStretch(String fontStretch) {
        COSName name = null;
        if (fontStretch != null) {
            name = COSName.getPDFName(fontStretch);
        }
        this.dic.setItem(COSName.FONT_STRETCH, (COSBase) name);
    }

    public int getFlags() {
        if (this.flags == -1) {
            this.flags = this.dic.getInt(COSName.FLAGS, 0);
        }
        return this.flags;
    }

    public void setFlags(int flags) {
        this.dic.setInt(COSName.FLAGS, flags);
        this.flags = flags;
    }

    public PDRectangle getFontBoundingBox() {
        return (PDRectangle) Optional.ofNullable((COSArray) this.dic.getDictionaryObject(COSName.FONT_BBOX, COSArray.class)).map(PDRectangle::new).orElse(null);
    }

    public void setFontBoundingBox(PDRectangle rect) {
        COSArray array = null;
        if (rect != null) {
            array = rect.getCOSObject();
        }
        this.dic.setItem(COSName.FONT_BBOX, (COSBase) array);
    }

    public float getItalicAngle() {
        return this.dic.getFloat(COSName.ITALIC_ANGLE, 0.0f);
    }

    public void setItalicAngle(float angle) {
        this.dic.setFloat(COSName.ITALIC_ANGLE, angle);
    }

    public float getAscent() {
        return this.dic.getFloat(COSName.ASCENT, 0.0f);
    }

    public void setAscent(float ascent) {
        this.dic.setFloat(COSName.ASCENT, ascent);
    }

    public float getDescent() {
        return this.dic.getFloat(COSName.DESCENT, 0.0f);
    }

    public void setDescent(float descent) {
        this.dic.setFloat(COSName.DESCENT, descent);
    }

    public float getLeading() {
        return this.dic.getFloat(COSName.LEADING, 0.0f);
    }

    public void setLeading(float leading) {
        this.dic.setFloat(COSName.LEADING, leading);
    }

    public float getCapHeight() {
        if (this.capHeight == Float.NEGATIVE_INFINITY) {
            this.capHeight = Math.abs(this.dic.getFloat(COSName.CAP_HEIGHT, 0.0f));
        }
        return this.capHeight;
    }

    public void setCapHeight(float capHeight) {
        this.dic.setFloat(COSName.CAP_HEIGHT, capHeight);
        this.capHeight = capHeight;
    }

    public float getXHeight() {
        if (this.xHeight == Float.NEGATIVE_INFINITY) {
            this.xHeight = Math.abs(this.dic.getFloat(COSName.XHEIGHT, 0.0f));
        }
        return this.xHeight;
    }

    public void setXHeight(float xHeight) {
        this.dic.setFloat(COSName.XHEIGHT, xHeight);
        this.xHeight = xHeight;
    }

    public float getStemV() {
        return this.dic.getFloat(COSName.STEM_V, 0.0f);
    }

    public void setStemV(float stemV) {
        this.dic.setFloat(COSName.STEM_V, stemV);
    }

    public float getStemH() {
        return this.dic.getFloat(COSName.STEM_H, 0.0f);
    }

    public void setStemH(float stemH) {
        this.dic.setFloat(COSName.STEM_H, stemH);
    }

    public float getAverageWidth() {
        return this.dic.getFloat(COSName.AVG_WIDTH, 0.0f);
    }

    public void setAverageWidth(float averageWidth) {
        this.dic.setFloat(COSName.AVG_WIDTH, averageWidth);
    }

    public float getMaxWidth() {
        return this.dic.getFloat(COSName.MAX_WIDTH, 0.0f);
    }

    public void setMaxWidth(float maxWidth) {
        this.dic.setFloat(COSName.MAX_WIDTH, maxWidth);
    }

    public boolean hasWidths() {
        return this.dic.containsKey(COSName.WIDTHS) || this.dic.containsKey(COSName.MISSING_WIDTH);
    }

    public boolean hasMissingWidth() {
        return this.dic.containsKey(COSName.MISSING_WIDTH);
    }

    public float getMissingWidth() {
        return this.dic.getFloat(COSName.MISSING_WIDTH, 0.0f);
    }

    public void setMissingWidth(float missingWidth) {
        this.dic.setFloat(COSName.MISSING_WIDTH, missingWidth);
    }

    public String getCharSet() {
        return (String) Optional.ofNullable((COSString) this.dic.getDictionaryObject(COSName.CHAR_SET, COSString.class)).map((v0) -> {
            return v0.getString();
        }).orElse(null);
    }

    public void setCharacterSet(String charSet) {
        COSString name = null;
        if (charSet != null) {
            name = COSString.parseLiteral(charSet);
        }
        this.dic.setItem(COSName.CHAR_SET, (COSBase) name);
    }

    public PDStream getFontFile() {
        return (PDStream) Optional.ofNullable((COSStream) this.dic.getDictionaryObject(COSName.FONT_FILE, COSStream.class)).map(PDStream::new).orElse(null);
    }

    public void setFontFile(PDStream type1Stream) {
        this.dic.setItem(COSName.FONT_FILE, type1Stream);
    }

    public PDStream getFontFile2() {
        return (PDStream) Optional.ofNullable((COSStream) this.dic.getDictionaryObject(COSName.FONT_FILE2, COSStream.class)).map(PDStream::new).orElse(null);
    }

    public void setFontFile2(PDStream ttfStream) {
        this.dic.setItem(COSName.FONT_FILE2, ttfStream);
    }

    public PDStream getFontFile3() {
        return (PDStream) Optional.ofNullable((COSStream) this.dic.getDictionaryObject(COSName.FONT_FILE3, COSStream.class)).map(PDStream::new).orElse(null);
    }

    public void setFontFile3(PDStream stream) {
        this.dic.setItem(COSName.FONT_FILE3, stream);
    }

    public PDStream getCIDSet() {
        return (PDStream) Optional.ofNullable((COSStream) this.dic.getDictionaryObject(COSName.CID_SET, COSStream.class)).map(PDStream::new).orElse(null);
    }

    public void setCIDSet(PDStream stream) {
        this.dic.setItem(COSName.CID_SET, stream);
    }

    public PDPanose getPanose() {
        COSString panose;
        COSDictionary style = (COSDictionary) this.dic.getDictionaryObject(COSName.STYLE, COSDictionary.class);
        if (style != null && (panose = (COSString) style.getDictionaryObject(COSName.PANOSE, COSString.class)) != null) {
            byte[] bytes = panose.getBytes();
            if (bytes.length >= 12) {
                return new PDPanose(bytes);
            }
            return null;
        }
        return null;
    }
}
