package org.sejda.sambox.encryption;

import java.security.MessageDigest;
import java.util.Arrays;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/Algorithm2.class */
class Algorithm2 {
    private static final byte[] NO_METADATA = {-1, -1, -1, -1};
    private final MessageDigest digest = MessageDigests.md5();
    private final Algorithm3 algo = new Algorithm3();

    Algorithm2() {
    }

    byte[] computeEncryptionKey(EncryptionContext context) {
        this.digest.reset();
        this.digest.update(EncryptUtils.padOrTruncate(context.security.getUserPassword()));
        this.digest.update(this.algo.computePassword(context));
        int permissions = context.security.permissions.getPermissionBytes();
        this.digest.update((byte) permissions);
        this.digest.update((byte) (permissions >>> 8));
        this.digest.update((byte) (permissions >>> 16));
        this.digest.update((byte) (permissions >>> 24));
        this.digest.update(context.documentId());
        if (StandardSecurityHandlerRevision.R4.compareTo(context.security.encryption.revision) <= 0 && !context.security.encryptMetadata) {
            this.digest.update(NO_METADATA);
        }
        byte[] hash = this.digest.digest();
        if (StandardSecurityHandlerRevision.R3.compareTo(context.security.encryption.revision) <= 0) {
            for (int i = 0; i < 50; i++) {
                this.digest.update(hash, 0, context.security.encryption.revision.length);
                hash = this.digest.digest();
            }
        }
        return Arrays.copyOf(hash, context.security.encryption.revision.length);
    }
}
