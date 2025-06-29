package org.sejda.sambox.encryption;

import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/Algorithm1A.class */
class Algorithm1A implements GeneralEncryptionAlgorithm {
    private AESEncryptionAlgorithmEngine engine;
    private final byte[] key;

    Algorithm1A(byte[] key, AESEncryptionAlgorithmEngine engine) {
        this(key);
        RequireUtils.requireNotNullArg(engine, "Enecryption engine cannot be null");
        this.engine = engine;
    }

    Algorithm1A(byte[] key) {
        this.engine = new ConcatenatingAESEngine();
        RequireUtils.requireNotNullArg(key, "Encryption key cannot be null");
        RequireUtils.requireArg(key.length == 32, "General encryption algorithm 1.A requires a 32 bytes key");
        this.key = key;
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSString value) {
        if (value.encryptable()) {
            value.setValue(this.engine.encryptBytes(value.getBytes(), this.key));
        }
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSStream value) {
        if (value.encryptable()) {
            value.setEncryptor(i -> {
                return this.engine.encryptStream(i, this.key);
            });
        }
    }

    @Override // org.sejda.sambox.encryption.GeneralEncryptionAlgorithm
    public void setCurrentCOSObjectKey(COSObjectKey key) {
    }

    public String toString() {
        return "Algorithm1A with engine " + this.engine;
    }
}
