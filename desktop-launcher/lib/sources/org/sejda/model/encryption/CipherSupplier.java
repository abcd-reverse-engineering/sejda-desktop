package org.sejda.model.encryption;

import javax.crypto.Cipher;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/encryption/CipherSupplier.class */
public interface CipherSupplier {
    Cipher get(int mode);
}
