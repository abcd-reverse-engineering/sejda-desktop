package org.sejda.sambox.encryption;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/Algorithm4.class */
class Algorithm4 implements PasswordAlgorithm {
    private final ARC4Engine engine = new ARC4Engine();

    Algorithm4() {
    }

    @Override // org.sejda.sambox.encryption.PasswordAlgorithm
    public byte[] computePassword(EncryptionContext context) {
        context.security.encryption.revision.require(StandardSecurityHandlerRevision.R2, "Algorithm 4 requires a security handler of revision 2");
        return this.engine.encryptBytes(EncryptUtils.ENCRYPT_PADDING, context.key());
    }
}
