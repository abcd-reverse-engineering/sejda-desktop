package org.sejda.sambox.pdmodel.documentinterchange.logicalstructure;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/logicalstructure/PDMarkInfo.class */
public class PDMarkInfo implements COSObjectable {
    private final COSDictionary dictionary;

    public PDMarkInfo() {
        this.dictionary = new COSDictionary();
    }

    public PDMarkInfo(COSDictionary dic) {
        this.dictionary = dic;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dictionary;
    }

    public boolean isMarked() {
        return this.dictionary.getBoolean("Marked", false);
    }

    public void setMarked(boolean value) {
        this.dictionary.setBoolean("Marked", value);
    }

    public boolean usesUserProperties() {
        return this.dictionary.getBoolean(PDUserAttributeObject.OWNER_USER_PROPERTIES, false);
    }

    public void setUserProperties(boolean userProps) {
        this.dictionary.setBoolean(PDUserAttributeObject.OWNER_USER_PROPERTIES, userProps);
    }

    public boolean isSuspect() {
        return this.dictionary.getBoolean("Suspects", false);
    }

    public void setSuspect(boolean suspect) {
        this.dictionary.setBoolean("Suspects", false);
    }
}
