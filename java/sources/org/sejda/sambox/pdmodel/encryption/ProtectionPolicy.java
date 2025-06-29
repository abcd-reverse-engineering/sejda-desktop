package org.sejda.sambox.pdmodel.encryption;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/encryption/ProtectionPolicy.class */
public abstract class ProtectionPolicy {
    private static final short DEFAULT_KEY_LENGTH = 40;
    private short encryptionKeyLength = 40;

    public void setEncryptionKeyLength(int l) {
        if (l != 40 && l != 128 && l != 256) {
            throw new IllegalArgumentException("Invalid key length '" + l + "' value must be 40, 128 or 256!");
        }
        this.encryptionKeyLength = (short) l;
    }

    public short getEncryptionKeyLength() {
        return this.encryptionKeyLength;
    }
}
