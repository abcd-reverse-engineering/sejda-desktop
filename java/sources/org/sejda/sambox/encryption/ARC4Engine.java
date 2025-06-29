package org.sejda.sambox.encryption;

import java.io.InputStream;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.params.KeyParameter;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/ARC4Engine.class */
class ARC4Engine implements EncryptionAlgorithmEngine {
    private final StreamCipher cipher = new RC4Engine();

    @Override // org.sejda.sambox.encryption.EncryptionAlgorithmEngine
    public InputStream encryptStream(InputStream data, byte[] key) {
        init(key);
        return new CipherInputStream(data, this.cipher);
    }

    @Override // org.sejda.sambox.encryption.EncryptionAlgorithmEngine
    public byte[] encryptBytes(byte[] data, byte[] key) {
        init(key);
        byte[] out = new byte[data.length];
        this.cipher.processBytes(data, 0, data.length, out, 0);
        return out;
    }

    private void init(byte[] key) {
        this.cipher.init(true, new KeyParameter(key));
    }
}
