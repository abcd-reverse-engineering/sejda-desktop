package org.sejda.sambox.encryption;

import java.util.Objects;
import org.bouncycastle.util.Arrays;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/Algorithm8.class */
class Algorithm8 implements PasswordAlgorithm {
    private final byte[] userValidationSalt;
    private final byte[] userKeySalt;
    private final Algorithm2AHash hashAlgo;
    private final AESEngineNoPadding engine;

    Algorithm8(Algorithm2AHash hashAlgo) {
        this(hashAlgo, EncryptUtils.rnd(8), EncryptUtils.rnd(8));
    }

    Algorithm8(Algorithm2AHash hashAlgo, byte[] userValidationSalt, byte[] userKeySalt) {
        this.engine = AESEngineNoPadding.cbc();
        Objects.requireNonNull(hashAlgo);
        this.hashAlgo = hashAlgo;
        this.userValidationSalt = userValidationSalt;
        this.userKeySalt = userKeySalt;
    }

    @Override // org.sejda.sambox.encryption.PasswordAlgorithm
    public byte[] computePassword(EncryptionContext context) {
        context.security.encryption.revision.requireAtLeast(StandardSecurityHandlerRevision.R5, "Algorithm 8 requires a security handler of revision 5 or greater");
        byte[] userPassword = context.security.getUserPasswordUTF();
        return Arrays.concatenate(this.hashAlgo.computeHash(Arrays.concatenate(userPassword, this.userValidationSalt), userPassword), this.userValidationSalt, this.userKeySalt);
    }

    public byte[] computeUE(EncryptionContext context) {
        context.security.encryption.revision.requireAtLeast(StandardSecurityHandlerRevision.R5, "Algorithm 8 requires a security handler of revision 5 or greater");
        byte[] userPassword = context.security.getUserPasswordUTF();
        byte[] key = this.hashAlgo.computeHash(Arrays.concatenate(userPassword, this.userKeySalt), userPassword);
        return Arrays.copyOf(this.engine.encryptBytes(context.key(), key), 32);
    }
}
