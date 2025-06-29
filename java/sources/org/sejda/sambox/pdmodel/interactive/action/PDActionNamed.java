package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLink;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionNamed.class */
public class PDActionNamed extends PDAction {
    public static final String SUB_TYPE = "Named";

    public PDActionNamed() {
        setSubType(SUB_TYPE);
    }

    public PDActionNamed(COSDictionary a) {
        super(a);
    }

    public String getN() {
        return this.action.getNameAsString(PDAnnotationLink.HIGHLIGHT_MODE_NONE);
    }

    public void setN(String name) {
        this.action.setName(PDAnnotationLink.HIGHLIGHT_MODE_NONE, name);
    }
}
