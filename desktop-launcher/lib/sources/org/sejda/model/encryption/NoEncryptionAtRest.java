package org.sejda.model.encryption;

import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/encryption/NoEncryptionAtRest.class */
public class NoEncryptionAtRest implements EncryptionAtRestPolicy {
    public static final NoEncryptionAtRest INSTANCE = new NoEncryptionAtRest();

    @Override // org.sejda.model.encryption.EncryptionAtRestPolicy
    public InputStream decrypt(InputStream in) {
        return in;
    }

    @Override // org.sejda.model.encryption.EncryptionAtRestPolicy
    public OutputStream encrypt(OutputStream out) {
        return out;
    }
}
