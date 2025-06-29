package org.sejda.sambox.pdmodel.common;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/PDDictionaryWrapper.class */
public class PDDictionaryWrapper implements COSObjectable {
    private final COSDictionary dictionary;

    public PDDictionaryWrapper() {
        this.dictionary = new COSDictionary();
    }

    public PDDictionaryWrapper(COSDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public final COSDictionary getCOSObject() {
        return this.dictionary;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PDDictionaryWrapper) {
            return this.dictionary.equals(((PDDictionaryWrapper) obj).dictionary);
        }
        return false;
    }

    public int hashCode() {
        return this.dictionary.hashCode();
    }
}
