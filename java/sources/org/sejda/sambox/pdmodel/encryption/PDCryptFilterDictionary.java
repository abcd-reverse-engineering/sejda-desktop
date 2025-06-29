package org.sejda.sambox.pdmodel.encryption;

import java.util.Objects;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSBoolean;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/encryption/PDCryptFilterDictionary.class */
public class PDCryptFilterDictionary extends PDDictionaryWrapper {
    public PDCryptFilterDictionary() {
    }

    public PDCryptFilterDictionary(COSDictionary dictionary) {
        super(dictionary);
    }

    public void setLength(int length) {
        getCOSObject().setInt(COSName.LENGTH, length);
    }

    public int getLength() {
        return getCOSObject().getInt(COSName.LENGTH, 40);
    }

    public void setCryptFilterMethod(COSName cfm) {
        getCOSObject().setItem(COSName.CFM, (COSBase) cfm);
    }

    public COSName getCryptFilterMethod() {
        return (COSName) getCOSObject().getDictionaryObject(COSName.CFM, COSName.class);
    }

    public boolean isEncryptMetaData() {
        COSBoolean value = (COSBoolean) getCOSObject().getDictionaryObject(COSName.ENCRYPT_META_DATA, COSBoolean.class);
        if (Objects.nonNull(value)) {
            return value.getValue();
        }
        return true;
    }

    public void setEncryptMetaData(boolean encryptMetaData) {
        getCOSObject().setBoolean(COSName.ENCRYPT_META_DATA, encryptMetaData);
    }
}
