package org.sejda.sambox.encryption;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.bouncycastle.util.Arrays;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/Algorithm2B.class */
class Algorithm2B implements Algorithm2AHash {
    private static final BigInteger THREE = new BigInteger("3");
    private static final Map<Integer, MessageDigest> HASHES = new HashMap();
    private final AESEncryptionAlgorithmEngine aes128;
    private final byte[] u;

    static {
        HASHES.put(0, MessageDigests.sha256());
        HASHES.put(1, MessageDigests.sha384());
        HASHES.put(2, MessageDigests.sha512());
    }

    Algorithm2B(byte[] u) {
        this.aes128 = AESEngineNoPadding.cbc();
        this.u = (byte[]) Optional.ofNullable(u).orElse(new byte[0]);
    }

    Algorithm2B() {
        this.aes128 = AESEngineNoPadding.cbc();
        this.u = new byte[0];
    }

    @Override // org.sejda.sambox.encryption.Algorithm2AHash
    public byte[] computeHash(byte[] input, byte[] password) {
        byte[] k = Arrays.copyOf(HASHES.get(0).digest(input), 32);
        byte[] e = new byte[0];
        int round = 0;
        while (true) {
            if (round < 64 || (e[e.length - 1] & 255) > round - 32) {
                byte[] k1Element = Arrays.concatenate(password, k, this.u);
                byte[] k1 = new byte[0];
                for (int i = 0; i < 64; i++) {
                    k1 = Arrays.concatenate(k1, k1Element);
                }
                e = this.aes128.encryptBytes(k1, Arrays.copyOf(k, 16), Arrays.copyOfRange(k, 16, 32));
                k = HASHES.get(Integer.valueOf(new BigInteger(1, Arrays.copyOf(e, 16)).mod(THREE).intValue())).digest(e);
                round++;
            } else {
                return Arrays.copyOf(k, 32);
            }
        }
    }
}
