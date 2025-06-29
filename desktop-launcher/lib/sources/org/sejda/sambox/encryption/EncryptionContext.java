package org.sejda.sambox.encryption;

import java.util.Objects;
import java.util.Optional;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/EncryptionContext.class */
public final class EncryptionContext {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptionContext.class);
    public final StandardSecurity security;
    private byte[] documentId;
    private byte[] key;

    public EncryptionContext(StandardSecurity security) {
        RequireUtils.requireNotNullArg(security, "Cannot create an encryption context with a null security");
        this.security = security;
    }

    public void documentId(byte[] documentId) {
        this.documentId = documentId;
    }

    byte[] documentId() {
        return this.documentId;
    }

    void key(byte[] key) {
        this.key = key;
    }

    byte[] key() {
        return this.key;
    }

    public GeneralEncryptionAlgorithm encryptionAlgorithm() {
        return this.security.encryption.encryptionAlgorithm(this);
    }

    public static GeneralEncryptionAlgorithm encryptionAlgorithmFromEncryptionDictionary(COSDictionary enc, byte[] encryptionKey) {
        if (Objects.nonNull(enc)) {
            if (Objects.nonNull(encryptionKey)) {
                COSName filter = enc.getCOSName(COSName.FILTER);
                if (COSName.STANDARD.equals(filter)) {
                    int revision = enc.getInt(COSName.R);
                    switch (revision) {
                        case 2:
                        case 3:
                            break;
                        case 4:
                            COSName cryptFilterMethod = (COSName) Optional.ofNullable((COSDictionary) enc.getDictionaryObject(COSName.CF, COSDictionary.class)).map(d -> {
                                return (COSDictionary) d.getDictionaryObject(COSName.STD_CF, COSDictionary.class);
                            }).map(d2 -> {
                                return d2.getCOSName(COSName.CFM);
                            }).orElse(COSName.NONE);
                            if (!COSName.V2.equals(cryptFilterMethod)) {
                                if (!COSName.AESV2.equals(cryptFilterMethod)) {
                                    LOG.warn("Unable to determine encryption algorithm");
                                    break;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        case 5:
                        case 6:
                            break;
                        default:
                            LOG.warn("Unsupported or invalid standard security handler revision number {}", enc.getDictionaryObject(COSName.R));
                            break;
                    }
                    return Algorithm1.withARC4Engine(encryptionKey);
                }
                LOG.warn("Unsupported encryption filter {}", filter);
                return null;
            }
            LOG.warn("Empty encryption key");
            return null;
        }
        return null;
    }
}
