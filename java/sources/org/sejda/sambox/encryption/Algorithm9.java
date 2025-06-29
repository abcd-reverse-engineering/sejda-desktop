package org.sejda.sambox.encryption;

import java.util.Objects;
import org.bouncycastle.util.Arrays;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/Algorithm9.class */
public class Algorithm9 implements PasswordAlgorithm {
    private final byte[] ownerValidationSalt;
    private final byte[] ownerKeySalt;
    private final byte[] u;
    private final Algorithm2AHash hashAlgo;
    private final AESEngineNoPadding engine;

    Algorithm9(Algorithm2AHash hashAlgo, byte[] u, byte[] ownerValidationSalt, byte[] ownerKeySalt) {
        this.engine = AESEngineNoPadding.cbc();
        Objects.requireNonNull(hashAlgo);
        Objects.requireNonNull(u);
        RequireUtils.requireArg(u.length == 48, "Generated U string must be 48 bytes long");
        this.hashAlgo = hashAlgo;
        this.u = u;
        this.ownerValidationSalt = ownerValidationSalt;
        this.ownerKeySalt = ownerKeySalt;
    }

    Algorithm9(Algorithm2AHash hashAlgo, byte[] u) {
        this(hashAlgo, u, EncryptUtils.rnd(8), EncryptUtils.rnd(8));
    }

    @Override // org.sejda.sambox.encryption.PasswordAlgorithm
    public byte[] computePassword(EncryptionContext context) {
        context.security.encryption.revision.requireAtLeast(StandardSecurityHandlerRevision.R5, "Algorithm 9 requires a security handler of revision 5 or greater");
        byte[] ownerBytes = context.security.getOwnerPasswordUTF();
        return Arrays.concatenate(this.hashAlgo.computeHash(Arrays.concatenate(ownerBytes, this.ownerValidationSalt, this.u), ownerBytes), this.ownerValidationSalt, this.ownerKeySalt);
    }

    public byte[] computeOE(EncryptionContext context) {
        context.security.encryption.revision.requireAtLeast(StandardSecurityHandlerRevision.R5, "Algorithm 8 requires a security handler of revision 5 or greater");
        byte[] ownerBytes = context.security.getOwnerPasswordUTF();
        byte[] key = this.hashAlgo.computeHash(Arrays.concatenate(ownerBytes, this.ownerKeySalt, this.u), ownerBytes);
        return Arrays.copyOf(this.engine.encryptBytes(context.key(), key), 32);
    }
}
