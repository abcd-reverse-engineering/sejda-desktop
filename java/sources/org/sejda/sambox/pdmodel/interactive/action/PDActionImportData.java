package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.filespecification.FileSpecifications;
import org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionImportData.class */
public class PDActionImportData extends PDAction {
    public static final String SUB_TYPE = "ImportData";

    public PDActionImportData() {
        setSubType(SUB_TYPE);
    }

    public PDActionImportData(COSDictionary a) {
        super(a);
    }

    public PDFileSpecification getFile() {
        return FileSpecifications.fileSpecificationFor(this.action.getDictionaryObject(COSName.F));
    }

    public void setFile(PDFileSpecification fs) {
        this.action.setItem(COSName.F, fs);
    }
}
