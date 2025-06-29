package org.sejda.sambox.pdmodel.documentinterchange.logicalstructure;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.PDPage;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/logicalstructure/PDMarkedContentReference.class */
public class PDMarkedContentReference implements COSObjectable {
    public static final String TYPE = "MCR";
    private final COSDictionary dictionary;

    public PDMarkedContentReference(COSDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dictionary;
    }

    public PDMarkedContentReference() {
        this.dictionary = new COSDictionary();
        this.dictionary.setName(COSName.TYPE, TYPE);
    }

    public PDPage getPage() {
        COSDictionary pg = (COSDictionary) getCOSObject().getDictionaryObject(COSName.PG);
        if (pg != null) {
            return new PDPage(pg);
        }
        return null;
    }

    public void setPage(PDPage page) {
        getCOSObject().setItem(COSName.PG, page);
    }

    public int getMCID() {
        return getCOSObject().getInt(COSName.MCID);
    }

    public void setMCID(int mcid) {
        getCOSObject().setInt(COSName.MCID, mcid);
    }

    public String toString() {
        return "mcid=" + getMCID();
    }
}
