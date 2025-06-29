package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.filespecification.FileSpecifications;
import org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionThread.class */
public class PDActionThread extends PDAction {
    public static final String SUB_TYPE = "Thread";

    public PDActionThread() {
        setSubType(SUB_TYPE);
    }

    public PDActionThread(COSDictionary a) {
        super(a);
    }

    public COSBase getD() {
        return this.action.getDictionaryObject(COSName.D);
    }

    public void setD(COSBase d) {
        this.action.setItem(COSName.D, d);
    }

    public PDFileSpecification getFile() {
        return FileSpecifications.fileSpecificationFor(this.action.getDictionaryObject(COSName.F));
    }

    public void setFile(PDFileSpecification fs) {
        this.action.setItem(COSName.F, fs);
    }

    public COSBase getB() {
        return this.action.getDictionaryObject(COSName.B);
    }

    public void setB(COSBase b) {
        this.action.setItem(COSName.B, b);
    }
}
