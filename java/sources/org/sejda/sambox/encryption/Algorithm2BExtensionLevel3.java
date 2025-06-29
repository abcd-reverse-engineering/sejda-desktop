package org.sejda.sambox.encryption;

import java.security.MessageDigest;
import org.bouncycastle.util.Arrays;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/Algorithm2BExtensionLevel3.class */
class Algorithm2BExtensionLevel3 implements Algorithm2AHash {
    private final MessageDigest digest = MessageDigests.sha256();

    Algorithm2BExtensionLevel3() {
    }

    @Override // org.sejda.sambox.encryption.Algorithm2AHash
    public byte[] computeHash(byte[] input, byte[] password) {
        return Arrays.copyOf(this.digest.digest(input), 32);
    }
}
