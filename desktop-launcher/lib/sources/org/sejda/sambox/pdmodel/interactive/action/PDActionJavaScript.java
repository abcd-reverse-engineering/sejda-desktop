package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionJavaScript.class */
public class PDActionJavaScript extends PDAction {
    public static final String SUB_TYPE = "JavaScript";

    public PDActionJavaScript() {
        setSubType(SUB_TYPE);
    }

    public PDActionJavaScript(String js) {
        this();
        setAction(js);
    }

    public PDActionJavaScript(COSDictionary a) {
        super(a);
    }

    public void setAction(String sAction) {
        this.action.setString("JS", sAction);
    }

    public String getAction() {
        COSBase base = this.action.getDictionaryObject(COSName.JS);
        if (base instanceof COSString) {
            return ((COSString) base).getString();
        }
        if (base instanceof COSStream) {
            return ((COSStream) base).asTextString();
        }
        return null;
    }
}
