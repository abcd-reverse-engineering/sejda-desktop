package org.sejda.sambox.pdmodel.interactive.annotation;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.graphics.PDLineDashPattern;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDBorderStyleDictionary.class */
public class PDBorderStyleDictionary implements COSObjectable {
    public static final String STYLE_SOLID = "S";
    public static final String STYLE_DASHED = "D";
    public static final String STYLE_BEVELED = "B";
    public static final String STYLE_INSET = "I";
    public static final String STYLE_UNDERLINE = "U";
    private final COSDictionary dictionary;

    public PDBorderStyleDictionary() {
        this.dictionary = new COSDictionary();
    }

    public PDBorderStyleDictionary(COSDictionary dict) {
        this.dictionary = dict;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dictionary;
    }

    public void setWidth(float w) {
        if (w == ((int) w)) {
            getCOSObject().setInt(COSName.W, (int) w);
        } else {
            getCOSObject().setFloat(COSName.W, w);
        }
    }

    public float getWidth() {
        if (getCOSObject().getDictionaryObject(COSName.W) instanceof COSName) {
            return 0.0f;
        }
        return getCOSObject().getFloat(COSName.W, 1.0f);
    }

    public void setStyle(String s) {
        getCOSObject().setName(COSName.S, s);
    }

    public String getStyle() {
        return getCOSObject().getNameAsString(COSName.S, "S");
    }

    public void setDashStyle(COSArray dashArray) {
        getCOSObject().setItem(COSName.D, (COSBase) dashArray);
    }

    public PDLineDashPattern getDashStyle() {
        COSArray d = (COSArray) getCOSObject().getDictionaryObject(COSName.D, COSArray.class);
        if (d == null) {
            d = new COSArray();
            d.add((COSBase) COSInteger.THREE);
            getCOSObject().setItem(COSName.D, (COSBase) d);
        }
        return new PDLineDashPattern(d, 0);
    }
}
