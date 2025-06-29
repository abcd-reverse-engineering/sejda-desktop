package org.sejda.sambox.pdmodel.font;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDCIDSystemInfo.class */
public final class PDCIDSystemInfo implements COSObjectable {
    private final COSDictionary dictionary;

    PDCIDSystemInfo(String registry, String ordering, int supplement) {
        this.dictionary = new COSDictionary();
        this.dictionary.setString(COSName.REGISTRY, registry);
        this.dictionary.setString(COSName.ORDERING, ordering);
        this.dictionary.setInt(COSName.SUPPLEMENT, supplement);
    }

    PDCIDSystemInfo(COSDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String getRegistry() {
        return this.dictionary.getNameAsString(COSName.REGISTRY);
    }

    public String getOrdering() {
        return this.dictionary.getNameAsString(COSName.ORDERING);
    }

    public int getSupplement() {
        return this.dictionary.getInt(COSName.SUPPLEMENT);
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.dictionary;
    }

    public String toString() {
        return getRegistry() + "-" + getOrdering() + "-" + getSupplement();
    }
}
