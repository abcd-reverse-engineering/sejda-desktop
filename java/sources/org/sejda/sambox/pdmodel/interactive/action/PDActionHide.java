package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSBoolean;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionHide.class */
public class PDActionHide extends PDAction {
    public static final String SUB_TYPE = "Hide";

    public PDActionHide() {
        setSubType(SUB_TYPE);
    }

    public PDActionHide(COSDictionary a) {
        super(a);
    }

    public COSBase getT() {
        return this.action.getDictionaryObject(COSName.T);
    }

    public void setT(COSBase t) {
        this.action.setItem(COSName.T, t);
    }

    public boolean getH() {
        return this.action.getBoolean(COSName.H, true);
    }

    public void setH(boolean h) {
        this.action.setItem(COSName.H, (COSBase) COSBoolean.valueOf(h));
    }
}
