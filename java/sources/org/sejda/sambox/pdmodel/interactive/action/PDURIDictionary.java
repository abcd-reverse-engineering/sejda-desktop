package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDURIDictionary.class */
public class PDURIDictionary implements COSObjectable {
    private final COSDictionary uriDictionary;

    public PDURIDictionary() {
        this.uriDictionary = new COSDictionary();
    }

    public PDURIDictionary(COSDictionary dictionary) {
        this.uriDictionary = dictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.uriDictionary;
    }

    public String getBase() {
        return getCOSObject().getString("Base");
    }

    public void setBase(String base) {
        getCOSObject().setString("Base", base);
    }
}
