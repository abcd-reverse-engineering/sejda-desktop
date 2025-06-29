package org.sejda.sambox.encryption;

import java.io.InputStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/EncryptionAlgorithmEngine.class */
interface EncryptionAlgorithmEngine {
    InputStream encryptStream(InputStream inputStream, byte[] bArr);

    byte[] encryptBytes(byte[] bArr, byte[] bArr2);
}
