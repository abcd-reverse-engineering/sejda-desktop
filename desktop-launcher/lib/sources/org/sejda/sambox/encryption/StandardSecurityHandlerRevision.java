package org.sejda.sambox.encryption;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/StandardSecurityHandlerRevision.class */
public enum StandardSecurityHandlerRevision {
    R2(5, 2),
    R3(16, 3),
    R4(16, 4),
    R5(32, 5),
    R6(32, 6);

    public final int length;
    public final int revisionNumber;

    StandardSecurityHandlerRevision(int length, int revisionNumber) {
        this.length = length;
        this.revisionNumber = revisionNumber;
    }

    public void require(StandardSecurityHandlerRevision rev, String message) {
        require(this == rev, message);
    }

    public void requireAtLeast(StandardSecurityHandlerRevision rev, String message) {
        require(rev.compareTo(this) <= 0, message);
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new EncryptionException(message);
        }
    }
}
