package org.sejda.model.encryption;

import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/encryption/CipherBasedEncryptionAtRest.class */
public class CipherBasedEncryptionAtRest implements EncryptionAtRestPolicy {
    private CipherSupplier cipherSupplier;

    public CipherBasedEncryptionAtRest(CipherSupplier cipherSupplier) {
        this.cipherSupplier = cipherSupplier;
    }

    @Override // org.sejda.model.encryption.EncryptionAtRestPolicy
    public InputStream decrypt(InputStream in) {
        return new CipherInputStream(in, this.cipherSupplier.get(2));
    }

    @Override // org.sejda.model.encryption.EncryptionAtRestPolicy
    public OutputStream encrypt(OutputStream out) {
        return new CipherOutputStream(out, this.cipherSupplier.get(1));
    }
}
