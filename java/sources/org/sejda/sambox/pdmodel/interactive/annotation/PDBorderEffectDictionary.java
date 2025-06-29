package org.sejda.sambox.pdmodel.interactive.annotation;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDBorderEffectDictionary.class */
public class PDBorderEffectDictionary implements COSObjectable {
    public static final String STYLE_SOLID = "S";
    public static final String STYLE_CLOUDY = "C";
    private final COSDictionary dictionary;

    public PDBorderEffectDictionary() {
        this.dictionary = new COSDictionary();
    }

    public PDBorderEffectDictionary(COSDictionary dict) {
        this.dictionary = dict;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dictionary;
    }

    public void setIntensity(float i) {
        getCOSObject().setFloat("I", i);
    }

    public float getIntensity() {
        return getCOSObject().getFloat("I", 0.0f);
    }

    public void setStyle(String s) {
        getCOSObject().setName("S", s);
    }

    public String getStyle() {
        return getCOSObject().getNameAsString("S", "S");
    }
}
