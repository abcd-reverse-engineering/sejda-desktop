package org.sejda.sambox.pdmodel.encryption;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSBoolean;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.cos.DirectCOSObject;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/encryption/PDEncryption.class */
public class PDEncryption extends PDDictionaryWrapper {
    public static final int VERSION0_UNDOCUMENTED_UNSUPPORTED = 0;
    public static final int VERSION1_40_BIT_ALGORITHM = 1;
    public static final int VERSION2_VARIABLE_LENGTH_ALGORITHM = 2;
    public static final int VERSION3_UNPUBLISHED_ALGORITHM = 3;
    public static final int VERSION4_SECURITY_HANDLER = 4;
    public static final int DEFAULT_LENGTH = 40;
    public static final int DEFAULT_VERSION = 0;
    private SecurityHandler securityHandler;

    public PDEncryption() {
    }

    public PDEncryption(COSDictionary dictionary) {
        super(dictionary);
        this.securityHandler = SecurityHandlerFactory.INSTANCE.newSecurityHandlerForFilter(getFilter());
    }

    public SecurityHandler getSecurityHandler() throws IOException {
        if (this.securityHandler == null) {
            throw new IOException("No security handler for filter " + getFilter());
        }
        return this.securityHandler;
    }

    public void setSecurityHandler(SecurityHandler securityHandler) {
        this.securityHandler = securityHandler;
    }

    public boolean hasSecurityHandler() {
        return this.securityHandler == null;
    }

    @Deprecated
    public COSDictionary getCOSDictionary() {
        return getCOSObject();
    }

    public void setFilter(String filter) {
        getCOSObject().setItem(COSName.FILTER, (COSBase) COSName.getPDFName(filter));
    }

    public final String getFilter() {
        return getCOSObject().getNameAsString(COSName.FILTER);
    }

    public String getSubFilter() {
        return getCOSObject().getNameAsString(COSName.SUB_FILTER);
    }

    public void setSubFilter(String subfilter) {
        getCOSObject().setName(COSName.SUB_FILTER, subfilter);
    }

    public void setVersion(int version) {
        getCOSObject().setInt(COSName.V, version);
    }

    public int getVersion() {
        return getCOSObject().getInt(COSName.V, 0);
    }

    public void setLength(int length) {
        getCOSObject().setInt(COSName.LENGTH, length);
    }

    public int getLength() {
        return getCOSObject().getInt(COSName.LENGTH, 40);
    }

    public void setRevision(int revision) {
        getCOSObject().setInt(COSName.R, revision);
    }

    public int getRevision() {
        return getCOSObject().getInt(COSName.R, 0);
    }

    public void setOwnerKey(byte[] o) {
        getCOSObject().setItem(COSName.O, (COSBase) COSString.newInstance(o));
    }

    public byte[] getOwnerKey() {
        byte[] o = null;
        COSString owner = (COSString) getCOSObject().getDictionaryObject(COSName.O);
        if (owner != null) {
            o = owner.getBytes();
        }
        return o;
    }

    public void setUserKey(byte[] u) {
        getCOSObject().setItem(COSName.U, (COSBase) COSString.newInstance(u));
    }

    public byte[] getUserKey() {
        byte[] u = null;
        COSString user = (COSString) getCOSObject().getDictionaryObject(COSName.U);
        if (user != null) {
            u = user.getBytes();
        }
        return u;
    }

    public void setOwnerEncryptionKey(byte[] oe) {
        getCOSObject().setItem(COSName.OE, (COSBase) COSString.newInstance(oe));
    }

    public byte[] getOwnerEncryptionKey() {
        byte[] oe = null;
        COSString ownerEncryptionKey = (COSString) getCOSObject().getDictionaryObject(COSName.OE);
        if (ownerEncryptionKey != null) {
            oe = ownerEncryptionKey.getBytes();
        }
        return oe;
    }

    public void setUserEncryptionKey(byte[] ue) {
        getCOSObject().setItem(COSName.UE, (COSBase) COSString.newInstance(ue));
    }

    public byte[] getUserEncryptionKey() {
        byte[] ue = null;
        COSString userEncryptionKey = (COSString) getCOSObject().getDictionaryObject(COSName.UE);
        if (userEncryptionKey != null) {
            ue = userEncryptionKey.getBytes();
        }
        return ue;
    }

    public void setPermissions(int permissions) {
        getCOSObject().setInt(COSName.P, permissions);
    }

    public int getPermissions() {
        return getCOSObject().getInt(COSName.P, 0);
    }

    public boolean isEncryptMetaData() {
        boolean encryptMetaData = true;
        COSBase value = getCOSObject().getDictionaryObject(COSName.ENCRYPT_META_DATA);
        if (value instanceof COSBoolean) {
            encryptMetaData = ((COSBoolean) value).getValue();
        }
        return encryptMetaData;
    }

    public void setRecipients(byte[][] recipients) {
        COSArray array = new COSArray();
        for (byte[] recipient : recipients) {
            array.add((COSBase) COSString.newInstance(recipient));
        }
        getCOSObject().setItem(COSName.RECIPIENTS, (COSBase) DirectCOSObject.asDirectObject(array));
    }

    public int getRecipientsLength() {
        COSArray array = (COSArray) getCOSObject().getItem(COSName.RECIPIENTS);
        return array.size();
    }

    public COSString getRecipientStringAt(int i) {
        COSArray array = (COSArray) getCOSObject().getItem(COSName.RECIPIENTS);
        return (COSString) array.get(i);
    }

    public PDCryptFilterDictionary getStdCryptFilterDictionary() {
        return getCryptFilterDictionary(COSName.STD_CF);
    }

    public PDCryptFilterDictionary getDefaultCryptFilterDictionary() {
        return getCryptFilterDictionary(COSName.DEFAULT_CRYPT_FILTER);
    }

    public PDCryptFilterDictionary getCryptFilterDictionary(COSName cryptFilterName) {
        COSDictionary stdCryptFilterDictionary;
        COSDictionary cryptFilterDictionary = (COSDictionary) getCOSObject().getDictionaryObject(COSName.CF, COSDictionary.class);
        if (cryptFilterDictionary != null && (stdCryptFilterDictionary = (COSDictionary) cryptFilterDictionary.getDictionaryObject(cryptFilterName, COSDictionary.class)) != null) {
            return new PDCryptFilterDictionary(stdCryptFilterDictionary);
        }
        return null;
    }

    public void setCryptFilterDictionary(COSName cryptFilterName, PDCryptFilterDictionary cryptFilterDictionary) {
        COSDictionary cfDictionary = (COSDictionary) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(COSName.CF, COSDictionary.class)).orElseGet(COSDictionary::new);
        getCOSObject().setItem(COSName.CF, (COSBase) DirectCOSObject.asDirectObject(cfDictionary));
        cfDictionary.setItem(cryptFilterName, (COSBase) DirectCOSObject.asDirectObject(cryptFilterDictionary.getCOSObject()));
    }

    public void setStdCryptFilterDictionary(PDCryptFilterDictionary cryptFilterDictionary) {
        setCryptFilterDictionary(COSName.STD_CF, cryptFilterDictionary);
    }

    public void setDefaultCryptFilterDictionary(PDCryptFilterDictionary defaultFilterDictionary) {
        setCryptFilterDictionary(COSName.DEFAULT_CRYPT_FILTER, defaultFilterDictionary);
    }

    public COSName getStreamFilterName() {
        COSName stmF = (COSName) getCOSObject().getDictionaryObject(COSName.STM_F);
        if (stmF == null) {
            stmF = COSName.IDENTITY;
        }
        return stmF;
    }

    public void setStreamFilterName(COSName streamFilterName) {
        getCOSObject().setItem(COSName.STM_F, (COSBase) streamFilterName);
    }

    public COSName getStringFilterName() {
        COSName strF = (COSName) getCOSObject().getDictionaryObject(COSName.STR_F);
        if (strF == null) {
            strF = COSName.IDENTITY;
        }
        return strF;
    }

    public void setStringFilterName(COSName stringFilterName) {
        getCOSObject().setItem(COSName.STR_F, (COSBase) stringFilterName);
    }

    public void setPerms(byte[] perms) {
        getCOSObject().setItem(COSName.PERMS, (COSBase) COSString.newInstance(perms));
    }

    public byte[] getPerms() {
        COSString permsCosString = (COSString) getCOSObject().getDictionaryObject(COSName.PERMS, COSString.class);
        if (Objects.nonNull(permsCosString)) {
            return permsCosString.getBytes();
        }
        return null;
    }

    public void removeV45filters() {
        getCOSObject().setItem(COSName.CF, (COSBase) null);
        getCOSObject().setItem(COSName.STM_F, (COSBase) null);
        getCOSObject().setItem(COSName.STR_F, (COSBase) null);
    }
}
