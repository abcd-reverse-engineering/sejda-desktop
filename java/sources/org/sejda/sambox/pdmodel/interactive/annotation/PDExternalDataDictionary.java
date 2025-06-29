package org.sejda.sambox.pdmodel.interactive.annotation;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDExternalDataDictionary.class */
public class PDExternalDataDictionary implements COSObjectable {
    private final COSDictionary dataDictionary;

    public PDExternalDataDictionary() {
        this.dataDictionary = new COSDictionary();
        this.dataDictionary.setName(COSName.TYPE, "ExData");
    }

    public PDExternalDataDictionary(COSDictionary dictionary) {
        this.dataDictionary = dictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dataDictionary;
    }

    public String getType() {
        return getCOSObject().getNameAsString(COSName.TYPE, "ExData");
    }

    public String getSubtype() {
        return getCOSObject().getNameAsString(COSName.SUBTYPE);
    }

    public void setSubtype(String subtype) {
        getCOSObject().setName(COSName.SUBTYPE, subtype);
    }
}
