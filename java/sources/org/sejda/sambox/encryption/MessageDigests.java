package org.sejda.sambox.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/MessageDigests.class */
public final class MessageDigests {
    private MessageDigests() {
    }

    public static MessageDigest md5() {
        return get("MD5");
    }

    public static MessageDigest sha1() {
        return get("SHA-1");
    }

    public static MessageDigest sha256() {
        return get("SHA-256");
    }

    public static MessageDigest sha384() {
        return get("SHA-384");
    }

    public static MessageDigest sha512() {
        return get("SHA-512");
    }

    private static MessageDigest get(String name) {
        try {
            return MessageDigest.getInstance(name);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(e);
        }
    }
}
