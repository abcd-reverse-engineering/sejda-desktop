package org.sejda.sambox.encryption;

import java.io.InputStream;
import java.util.Objects;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/AESEngineNoPadding.class */
class AESEngineNoPadding implements AESEncryptionAlgorithmEngine {
    private final BufferedBlockCipher cipher;

    AESEngineNoPadding(BufferedBlockCipher cipher) {
        this.cipher = cipher;
    }

    @Override // org.sejda.sambox.encryption.AESEncryptionAlgorithmEngine
    public InputStream encryptStream(InputStream data, byte[] key, byte[] iv) {
        init(key, iv);
        return new CipherInputStream(data, this.cipher);
    }

    @Override // org.sejda.sambox.encryption.EncryptionAlgorithmEngine
    public InputStream encryptStream(InputStream data, byte[] key) {
        return encryptStream(data, key, null);
    }

    @Override // org.sejda.sambox.encryption.AESEncryptionAlgorithmEngine
    public byte[] encryptBytes(byte[] data, byte[] key, byte[] iv) {
        init(key, iv);
        try {
            byte[] buf = new byte[this.cipher.getOutputSize(data.length)];
            int len = this.cipher.processBytes(data, 0, data.length, buf, 0);
            return Arrays.copyOf(buf, len + this.cipher.doFinal(buf, len));
        } catch (DataLengthException | IllegalStateException | InvalidCipherTextException e) {
            throw new EncryptionException((Throwable) e);
        }
    }

    @Override // org.sejda.sambox.encryption.EncryptionAlgorithmEngine
    public byte[] encryptBytes(byte[] data, byte[] key) {
        return encryptBytes(data, key, null);
    }

    private void init(byte[] key, byte[] iv) {
        this.cipher.reset();
        if (Objects.nonNull(iv)) {
            this.cipher.init(true, new ParametersWithIV(new KeyParameter(key), iv));
        } else {
            this.cipher.init(true, new KeyParameter(key));
        }
    }

    static AESEngineNoPadding cbc() {
        return new AESEngineNoPadding(new BufferedBlockCipher(new CBCBlockCipher(new AESFastEngine())));
    }

    static AESEngineNoPadding ecb() {
        return new AESEngineNoPadding(new BufferedBlockCipher(new AESFastEngine()));
    }
}
