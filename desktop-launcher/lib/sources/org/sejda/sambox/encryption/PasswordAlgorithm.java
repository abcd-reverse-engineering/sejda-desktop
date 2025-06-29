package org.sejda.sambox.encryption;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/PasswordAlgorithm.class */
interface PasswordAlgorithm {
    byte[] computePassword(EncryptionContext encryptionContext);
}
