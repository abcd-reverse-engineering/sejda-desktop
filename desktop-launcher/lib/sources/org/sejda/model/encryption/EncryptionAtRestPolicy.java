package org.sejda.model.encryption;

import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/encryption/EncryptionAtRestPolicy.class */
public interface EncryptionAtRestPolicy {
    InputStream decrypt(InputStream in);

    OutputStream encrypt(OutputStream out);
}
