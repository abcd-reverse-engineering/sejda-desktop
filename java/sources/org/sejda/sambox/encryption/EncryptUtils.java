package org.sejda.sambox.encryption;

import java.security.SecureRandom;
import org.bouncycastle.util.Arrays;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/EncryptUtils.class */
final class EncryptUtils {
    public static final byte[] ENCRYPT_PADDING = {40, -65, 78, 94, 78, 117, -118, 65, 100, 0, 78, 86, -1, -6, 1, 8, 46, 46, 0, -74, -48, 104, 62, Byte.MIN_VALUE, 47, 12, -87, -2, 100, 83, 105, 122};

    private EncryptUtils() {
    }

    public static byte[] padOrTruncate(byte[] input) {
        byte[] padded = Arrays.copyOf(input, Math.min(input.length, 32));
        if (padded.length < 32) {
            return Arrays.concatenate(padded, Arrays.copyOf(ENCRYPT_PADDING, 32 - padded.length));
        }
        return padded;
    }

    public static byte[] truncate127(byte[] input) {
        return Arrays.copyOf(input, Math.min(input.length, 127));
    }

    public static byte[] rnd(int length) {
        RequireUtils.requireArg(length > 0, "Cannot generate a negative length byte array");
        SecureRandom random = new SecureRandom();
        byte[] rnd = new byte[length];
        random.nextBytes(rnd);
        return rnd;
    }
}
