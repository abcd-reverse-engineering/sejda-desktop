package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.filespecification.FileSpecifications;
import org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionSubmitForm.class */
public class PDActionSubmitForm extends PDAction {
    public static final String SUB_TYPE = "SubmitForm";

    public PDActionSubmitForm() {
        setSubType(SUB_TYPE);
    }

    public PDActionSubmitForm(COSDictionary a) {
        super(a);
    }

    public PDFileSpecification getFile() {
        return FileSpecifications.fileSpecificationFor(this.action.getDictionaryObject(COSName.F));
    }

    public void setFile(PDFileSpecification fs) {
        this.action.setItem(COSName.F, fs);
    }

    public COSArray getFields() {
        COSBase retval = this.action.getDictionaryObject(COSName.FIELDS);
        if (retval instanceof COSArray) {
            return (COSArray) retval;
        }
        return null;
    }

    public void setFields(COSArray array) {
        this.action.setItem(COSName.FIELDS, (COSBase) array);
    }

    public int getFlags() {
        return this.action.getInt(COSName.FLAGS, 0);
    }

    public void setFlags(int flags) {
        this.action.setInt(COSName.FLAGS, flags);
    }
}
