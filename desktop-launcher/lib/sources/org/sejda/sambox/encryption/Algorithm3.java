package org.sejda.sambox.encryption;

import java.security.MessageDigest;
import java.util.Optional;
import org.bouncycastle.util.Arrays;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/Algorithm3.class */
class Algorithm3 implements PasswordAlgorithm {
    private final MessageDigest digest = MessageDigests.md5();
    private final ARC4Engine engine = new ARC4Engine();

    Algorithm3() {
    }

    @Override // org.sejda.sambox.encryption.PasswordAlgorithm
    public byte[] computePassword(EncryptionContext context) {
        byte[] ownerBytes = context.security.getOwnerPassword();
        byte[] userBytes = context.security.getUserPassword();
        byte[] padded = EncryptUtils.padOrTruncate((byte[]) Optional.of(ownerBytes).filter(p -> {
            return p.length > 0;
        }).orElseGet(() -> {
            return userBytes;
        }));
        byte[] paddedUser = EncryptUtils.padOrTruncate(userBytes);
        this.digest.reset();
        byte[] arc4Key = this.digest.digest(padded);
        if (StandardSecurityHandlerRevision.R3.compareTo(context.security.encryption.revision) <= 0) {
            for (int i = 0; i < 50; i++) {
                this.digest.update(arc4Key, 0, context.security.encryption.revision.length);
                arc4Key = Arrays.copyOf(this.digest.digest(), context.security.encryption.revision.length);
            }
            byte[] encrypted = this.engine.encryptBytes(paddedUser, arc4Key);
            byte[] bArr = new byte[arc4Key.length];
            for (int i2 = 1; i2 < 20; i2++) {
                byte[] iterationKey = Arrays.copyOf(arc4Key, arc4Key.length);
                for (int j = 0; j < iterationKey.length; j++) {
                    iterationKey[j] = (byte) (iterationKey[j] ^ ((byte) i2));
                }
                encrypted = this.engine.encryptBytes(encrypted, iterationKey);
            }
            return encrypted;
        }
        return this.engine.encryptBytes(paddedUser, arc4Key);
    }
}
