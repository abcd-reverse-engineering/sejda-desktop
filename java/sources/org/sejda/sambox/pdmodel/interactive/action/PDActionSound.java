package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionSound.class */
public class PDActionSound extends PDAction {
    public static final String SUB_TYPE = "Sound";

    public PDActionSound() {
        setSubType("Sound");
    }

    public PDActionSound(COSDictionary a) {
        super(a);
    }

    public String getS() {
        return this.action.getNameAsString(COSName.S);
    }

    public void setS(String s) {
        this.action.setName(COSName.S, s);
    }
}
