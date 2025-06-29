package org.sejda.sambox.pdmodel.documentinterchange.prepress;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.graphics.PDLineDashPattern;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/prepress/PDBoxStyle.class */
public class PDBoxStyle implements COSObjectable {
    public static final String GUIDELINE_STYLE_SOLID = "S";
    public static final String GUIDELINE_STYLE_DASHED = "D";
    private final COSDictionary dictionary;

    public PDBoxStyle() {
        this.dictionary = new COSDictionary();
    }

    public PDBoxStyle(COSDictionary dic) {
        this.dictionary = dic;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dictionary;
    }

    public PDColor getGuidelineColor() {
        COSArray colorValues = (COSArray) this.dictionary.getDictionaryObject(COSName.C);
        if (colorValues == null) {
            colorValues = new COSArray();
            colorValues.add((COSBase) COSInteger.ZERO);
            colorValues.add((COSBase) COSInteger.ZERO);
            colorValues.add((COSBase) COSInteger.ZERO);
            this.dictionary.setItem(COSName.C, (COSBase) colorValues);
        }
        return new PDColor(colorValues.toFloatArray(), PDDeviceRGB.INSTANCE);
    }

    public void setGuideLineColor(PDColor color) {
        if (color != null) {
            this.dictionary.setItem(COSName.C, (COSBase) color.toComponentsCOSArray());
        }
    }

    public float getGuidelineWidth() {
        return this.dictionary.getFloat(COSName.W, 1.0f);
    }

    public void setGuidelineWidth(float width) {
        this.dictionary.setFloat(COSName.W, width);
    }

    public String getGuidelineStyle() {
        return this.dictionary.getNameAsString(COSName.S, "S");
    }

    public void setGuidelineStyle(String style) {
        this.dictionary.setName(COSName.S, style);
    }

    public PDLineDashPattern getLineDashPattern() {
        COSArray d = (COSArray) this.dictionary.getDictionaryObject(COSName.D);
        if (d == null) {
            d = new COSArray();
            d.add((COSBase) COSInteger.THREE);
            this.dictionary.setItem(COSName.D, (COSBase) d);
        }
        COSArray lineArray = new COSArray();
        lineArray.add((COSBase) d);
        PDLineDashPattern pattern = new PDLineDashPattern(lineArray, 0);
        return pattern;
    }

    public void setLineDashPattern(COSArray dashArray) {
        COSArray array = null;
        if (dashArray != null) {
            array = dashArray;
        }
        this.dictionary.setItem(COSName.D, (COSBase) array);
    }
}
