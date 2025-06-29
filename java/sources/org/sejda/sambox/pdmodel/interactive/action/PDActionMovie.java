package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionMovie.class */
public class PDActionMovie extends PDAction {
    public static final String SUB_TYPE = "Movie";

    public PDActionMovie() {
        setSubType(SUB_TYPE);
    }

    public PDActionMovie(COSDictionary a) {
        super(a);
    }

    @Deprecated
    public String getS() {
        return this.action.getNameAsString(COSName.S);
    }

    @Deprecated
    public void setS(String s) {
        this.action.setName(COSName.S, s);
    }
}
