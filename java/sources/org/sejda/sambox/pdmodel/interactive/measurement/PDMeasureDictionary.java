package org.sejda.sambox.pdmodel.interactive.measurement;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/measurement/PDMeasureDictionary.class */
public class PDMeasureDictionary implements COSObjectable {
    public static final String TYPE = "Measure";
    private final COSDictionary measureDictionary;

    protected PDMeasureDictionary() {
        this.measureDictionary = new COSDictionary();
        getCOSObject().setName(COSName.TYPE, TYPE);
    }

    public PDMeasureDictionary(COSDictionary dictionary) {
        this.measureDictionary = dictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.measureDictionary;
    }

    public String getType() {
        return TYPE;
    }

    public String getSubtype() {
        return getCOSObject().getNameAsString(COSName.SUBTYPE, PDRectlinearMeasureDictionary.SUBTYPE);
    }

    protected void setSubtype(String subtype) {
        getCOSObject().setName(COSName.SUBTYPE, subtype);
    }
}
