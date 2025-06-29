package org.sejda.sambox.encryption;

import java.security.MessageDigest;
import org.bouncycastle.util.Arrays;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/Algorithm5.class */
class Algorithm5 implements PasswordAlgorithm {
    private final MessageDigest digest = MessageDigests.md5();
    private final ARC4Engine engine = new ARC4Engine();

    Algorithm5() {
    }

    @Override // org.sejda.sambox.encryption.PasswordAlgorithm
    public byte[] computePassword(EncryptionContext context) {
        context.security.encryption.revision.requireAtLeast(StandardSecurityHandlerRevision.R3, "Algorithm 5 requires a security handler of revision 3 or greater");
        this.digest.reset();
        this.digest.update(EncryptUtils.ENCRYPT_PADDING);
        byte[] encrypted = this.engine.encryptBytes(Arrays.copyOf(this.digest.digest(context.documentId()), 16), context.key());
        byte[] bArr = new byte[context.key().length];
        for (int i = 1; i < 20; i++) {
            byte[] iterationKey = Arrays.copyOf(context.key(), context.key().length);
            for (int j = 0; j < iterationKey.length; j++) {
                iterationKey[j] = (byte) (iterationKey[j] ^ ((byte) i));
            }
            encrypted = this.engine.encryptBytes(encrypted, iterationKey);
        }
        return Arrays.concatenate(Arrays.copyOf(encrypted, 16), Arrays.copyOf(EncryptUtils.ENCRYPT_PADDING, 16));
    }
}
