package org.sejda.sambox.pdmodel.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/encryption/MessageDigests.class */
final class MessageDigests {
    private MessageDigests() {
    }

    static MessageDigest getMD5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    static MessageDigest getSHA1() {
        try {
            return MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    static MessageDigest getSHA256() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
