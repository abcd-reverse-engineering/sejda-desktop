package org.sejda.sambox.pdmodel.common.filespecification;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/filespecification/PDSimpleFileSpecification.class */
public class PDSimpleFileSpecification implements PDFileSpecification {
    private COSString file;

    public PDSimpleFileSpecification() {
        this.file = COSString.parseLiteral("");
    }

    public PDSimpleFileSpecification(COSString fileName) {
        this.file = fileName;
    }

    @Override // org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification
    public String getFile() {
        return this.file.getString();
    }

    @Override // org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification
    public void setFile(String fileName) {
        this.file = COSString.parseLiteral(fileName);
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.file;
    }
}
