package org.sejda.sambox.pdmodel.documentinterchange.logicalstructure;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/logicalstructure/PDParentTreeValue.class */
public class PDParentTreeValue implements COSObjectable {
    COSObjectable obj;

    public PDParentTreeValue(COSArray obj) {
        this.obj = obj;
    }

    public PDParentTreeValue(COSDictionary obj) {
        this.obj = obj;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.obj.getCOSObject();
    }

    public String toString() {
        return this.obj.toString();
    }
}
