package org.sejda.sambox.encryption;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.cos.DirectCOSObject;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/StandardSecurityEncryption.class */
public enum StandardSecurityEncryption {
    ARC4_128(StandardSecurityHandlerRevision.R3, 2) { // from class: org.sejda.sambox.encryption.StandardSecurityEncryption.1
        @Override // org.sejda.sambox.encryption.StandardSecurityEncryption
        public COSDictionary generateEncryptionDictionary(EncryptionContext context) {
            COSDictionary encryptionDictionary = super.generateEncryptionDictionary(context);
            encryptionDictionary.setItem(COSName.O, (COSBase) StandardSecurityEncryption.pwdString(new Algorithm3().computePassword(context)));
            encryptionDictionary.setItem(COSName.U, (COSBase) StandardSecurityEncryption.pwdString(new Algorithm5().computePassword(context)));
            return encryptionDictionary;
        }

        @Override // org.sejda.sambox.encryption.StandardSecurityEncryption
        public GeneralEncryptionAlgorithm encryptionAlgorithm(EncryptionContext context) {
            context.key(new Algorithm2().computeEncryptionKey(context));
            return Algorithm1.withARC4Engine(context.key());
        }
    },
    AES_128(StandardSecurityHandlerRevision.R4, 4) { // from class: org.sejda.sambox.encryption.StandardSecurityEncryption.2
        @Override // org.sejda.sambox.encryption.StandardSecurityEncryption
        public COSDictionary generateEncryptionDictionary(EncryptionContext context) {
            COSDictionary encryptionDictionary = super.generateEncryptionDictionary(context);
            encryptionDictionary.setItem(COSName.O, (COSBase) StandardSecurityEncryption.pwdString(new Algorithm3().computePassword(context)));
            encryptionDictionary.setItem(COSName.U, (COSBase) StandardSecurityEncryption.pwdString(new Algorithm5().computePassword(context)));
            encryptionDictionary.setBoolean(COSName.ENCRYPT_META_DATA, context.security.encryptMetadata);
            COSDictionary standardCryptFilterDictionary = new COSDictionary();
            standardCryptFilterDictionary.setItem(COSName.CFM, (COSBase) COSName.AESV2);
            standardCryptFilterDictionary.setItem(COSName.AUTEVENT, (COSBase) COSName.DOC_OPEN);
            standardCryptFilterDictionary.setInt(COSName.LENGTH, this.revision.length);
            COSDictionary cryptFilterDictionary = new COSDictionary();
            cryptFilterDictionary.setItem(COSName.STD_CF, (COSBase) DirectCOSObject.asDirectObject(standardCryptFilterDictionary));
            encryptionDictionary.setItem(COSName.CF, (COSBase) DirectCOSObject.asDirectObject(cryptFilterDictionary));
            encryptionDictionary.setItem(COSName.STM_F, (COSBase) COSName.STD_CF);
            encryptionDictionary.setItem(COSName.STR_F, (COSBase) COSName.STD_CF);
            return encryptionDictionary;
        }

        @Override // org.sejda.sambox.encryption.StandardSecurityEncryption
        public GeneralEncryptionAlgorithm encryptionAlgorithm(EncryptionContext context) {
            context.key(new Algorithm2().computeEncryptionKey(context));
            return Algorithm1.withAESEngine(context.key());
        }
    },
    AES_256(StandardSecurityHandlerRevision.R6, 5) { // from class: org.sejda.sambox.encryption.StandardSecurityEncryption.3
        @Override // org.sejda.sambox.encryption.StandardSecurityEncryption
        public COSDictionary generateEncryptionDictionary(EncryptionContext context) {
            COSDictionary encryptionDictionary = super.generateEncryptionDictionary(context);
            encryptionDictionary.setInt(COSName.R, this.revision.revisionNumber);
            Algorithm8 algo8 = new Algorithm8(new Algorithm2B());
            byte[] u = algo8.computePassword(context);
            encryptionDictionary.setItem(COSName.U, (COSBase) StandardSecurityEncryption.pwdString(u));
            encryptionDictionary.setItem(COSName.UE, (COSBase) StandardSecurityEncryption.pwdString(algo8.computeUE(context)));
            Algorithm9 algo9 = new Algorithm9(new Algorithm2B(u), u);
            encryptionDictionary.setItem(COSName.O, (COSBase) StandardSecurityEncryption.pwdString(algo9.computePassword(context)));
            encryptionDictionary.setItem(COSName.OE, (COSBase) StandardSecurityEncryption.pwdString(algo9.computeOE(context)));
            encryptionDictionary.setBoolean(COSName.ENCRYPT_META_DATA, context.security.encryptMetadata);
            encryptionDictionary.setItem(COSName.PERMS, (COSBase) StandardSecurityEncryption.pwdString(new Algorithm10().computePerms(context)));
            COSDictionary standardCryptFilterDictionary = new COSDictionary();
            standardCryptFilterDictionary.setItem(COSName.CFM, (COSBase) COSName.AESV3);
            standardCryptFilterDictionary.setItem(COSName.AUTEVENT, (COSBase) COSName.DOC_OPEN);
            standardCryptFilterDictionary.setInt(COSName.LENGTH, this.revision.length);
            COSDictionary cryptFilterDictionary = new COSDictionary();
            cryptFilterDictionary.setItem(COSName.STD_CF, (COSBase) DirectCOSObject.asDirectObject(standardCryptFilterDictionary));
            encryptionDictionary.setItem(COSName.CF, (COSBase) DirectCOSObject.asDirectObject(cryptFilterDictionary));
            encryptionDictionary.setItem(COSName.STM_F, (COSBase) COSName.STD_CF);
            encryptionDictionary.setItem(COSName.STR_F, (COSBase) COSName.STD_CF);
            return encryptionDictionary;
        }

        @Override // org.sejda.sambox.encryption.StandardSecurityEncryption
        public GeneralEncryptionAlgorithm encryptionAlgorithm(EncryptionContext context) {
            context.key(EncryptUtils.rnd(32));
            return new Algorithm1A(context.key());
        }
    };

    public final int version;
    public final StandardSecurityHandlerRevision revision;

    public abstract GeneralEncryptionAlgorithm encryptionAlgorithm(EncryptionContext encryptionContext);

    StandardSecurityEncryption(StandardSecurityHandlerRevision revision, int version) {
        this.revision = revision;
        this.version = version;
    }

    public COSDictionary generateEncryptionDictionary(EncryptionContext context) {
        COSDictionary encryptionDictionary = new COSDictionary();
        encryptionDictionary.setItem(COSName.FILTER, (COSBase) COSName.STANDARD);
        encryptionDictionary.setInt(COSName.V, this.version);
        encryptionDictionary.setInt(COSName.LENGTH, this.revision.length * 8);
        encryptionDictionary.setInt(COSName.R, this.revision.revisionNumber);
        encryptionDictionary.setInt(COSName.P, context.security.permissions.getPermissionBytes());
        return encryptionDictionary;
    }

    private static COSString pwdString(byte[] raw) {
        COSString string = new COSString(raw);
        string.encryptable(false);
        string.setForceHexForm(true);
        return string;
    }
}
