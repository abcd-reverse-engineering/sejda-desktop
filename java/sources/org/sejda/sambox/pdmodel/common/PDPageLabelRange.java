package org.sejda.sambox.pdmodel.common;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/PDPageLabelRange.class */
public class PDPageLabelRange implements COSObjectable {
    private COSDictionary root;
    private static final COSName KEY_START = COSName.ST;
    private static final COSName KEY_PREFIX = COSName.P;
    private static final COSName KEY_STYLE = COSName.S;
    public static final String STYLE_DECIMAL = "D";
    public static final String STYLE_ROMAN_UPPER = "R";
    public static final String STYLE_ROMAN_LOWER = "r";
    public static final String STYLE_LETTERS_UPPER = "A";
    public static final String STYLE_LETTERS_LOWER = "a";

    public PDPageLabelRange() {
        this(new COSDictionary());
    }

    public PDPageLabelRange(COSDictionary dict) {
        this.root = dict;
    }

    public PDPageLabelRange(String style, String prefix, Integer start) {
        this();
        if (style != null) {
            setStyle(style);
        }
        if (prefix != null) {
            setPrefix(prefix);
        }
        if (start != null) {
            setStart(start.intValue());
        }
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.root;
    }

    public String getStyle() {
        return this.root.getNameAsString(KEY_STYLE);
    }

    public void setStyle(String style) {
        if (style != null) {
            this.root.setName(KEY_STYLE, style);
        } else {
            this.root.removeItem(KEY_STYLE);
        }
    }

    public int getStart() {
        return this.root.getInt(KEY_START, 1);
    }

    public boolean hasStart() {
        return this.root.getInt(KEY_START) != -1;
    }

    public void setStart(int start) {
        if (start <= 0) {
            throw new IllegalArgumentException("The page numbering start value must be a positive integer");
        }
        this.root.setInt(KEY_START, start);
    }

    public String getPrefix() {
        return this.root.getString(KEY_PREFIX);
    }

    public void setPrefix(String prefix) {
        if (prefix != null) {
            this.root.setString(KEY_PREFIX, prefix);
        } else {
            this.root.removeItem(KEY_PREFIX);
        }
    }
}
