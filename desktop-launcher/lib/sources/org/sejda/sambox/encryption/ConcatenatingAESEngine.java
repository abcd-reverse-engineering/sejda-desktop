package org.sejda.sambox.encryption;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.security.SecureRandom;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.util.Arrays;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/ConcatenatingAESEngine.class */
public class ConcatenatingAESEngine extends AESEngineNoPadding {
    private final SecureRandom random;

    ConcatenatingAESEngine() {
        super(new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine())));
        this.random = new SecureRandom();
    }

    @Override // org.sejda.sambox.encryption.AESEngineNoPadding, org.sejda.sambox.encryption.EncryptionAlgorithmEngine
    public InputStream encryptStream(InputStream data, byte[] key) {
        return encryptStream(data, key, initializationVector());
    }

    @Override // org.sejda.sambox.encryption.AESEngineNoPadding, org.sejda.sambox.encryption.AESEncryptionAlgorithmEngine
    public InputStream encryptStream(InputStream data, byte[] key, byte[] iv) {
        return new SequenceInputStream(new ByteArrayInputStream(iv), super.encryptStream(data, key, iv));
    }

    @Override // org.sejda.sambox.encryption.AESEngineNoPadding, org.sejda.sambox.encryption.EncryptionAlgorithmEngine
    public byte[] encryptBytes(byte[] data, byte[] key) {
        return encryptBytes(data, key, initializationVector());
    }

    @Override // org.sejda.sambox.encryption.AESEngineNoPadding, org.sejda.sambox.encryption.AESEncryptionAlgorithmEngine
    public byte[] encryptBytes(byte[] data, byte[] key, byte[] iv) {
        return Arrays.concatenate(iv, super.encryptBytes(data, key, iv));
    }

    private byte[] initializationVector() {
        byte[] iv = new byte[16];
        this.random.nextBytes(iv);
        return iv;
    }
}
