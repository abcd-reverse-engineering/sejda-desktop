package org.sejda.sambox.pdmodel.common;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/PDTypedDictionaryWrapper.class */
public class PDTypedDictionaryWrapper extends PDDictionaryWrapper {
    public PDTypedDictionaryWrapper(String type) {
        getCOSObject().setName(COSName.TYPE, type);
    }

    public PDTypedDictionaryWrapper(COSDictionary dictionary) {
        super(dictionary);
    }

    public String getType() {
        return getCOSObject().getNameAsString(COSName.TYPE);
    }
}
