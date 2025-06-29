package org.sejda.sambox.pdmodel.documentinterchange.markedcontent;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.graphics.optionalcontent.PDOptionalContentGroup;
import org.sejda.sambox.pdmodel.graphics.optionalcontent.PDOptionalContentMembershipDictionary;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/markedcontent/PDPropertyList.class */
public class PDPropertyList implements COSObjectable {
    protected final COSDictionary dict;

    public static PDPropertyList create(COSDictionary dict) {
        COSBase item = dict.getItem(COSName.TYPE);
        if (COSName.OCG.equals(item)) {
            return new PDOptionalContentGroup(dict);
        }
        if (COSName.OCMD.equals(item)) {
            return new PDOptionalContentMembershipDictionary(dict);
        }
        return new PDPropertyList(dict);
    }

    protected PDPropertyList() {
        this.dict = new COSDictionary();
    }

    protected PDPropertyList(COSDictionary dict) {
        this.dict = dict;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dict;
    }
}
