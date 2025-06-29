package org.sejda.sambox.encryption;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/Algorithm10.class */
class Algorithm10 {
    private final AESEngineNoPadding engine = AESEngineNoPadding.ecb();

    Algorithm10() {
    }

    byte[] computePerms(EncryptionContext context) {
        byte[] perms = EncryptUtils.rnd(16);
        perms[0] = (byte) context.security.permissions.getPermissionBytes();
        perms[1] = (byte) (context.security.permissions.getPermissionBytes() >>> 8);
        perms[2] = (byte) (context.security.permissions.getPermissionBytes() >>> 16);
        perms[3] = (byte) (context.security.permissions.getPermissionBytes() >>> 24);
        perms[4] = -1;
        perms[5] = -1;
        perms[6] = -1;
        perms[7] = -1;
        perms[8] = (byte) (context.security.encryptMetadata ? 84 : 70);
        perms[9] = 97;
        perms[10] = 100;
        perms[11] = 98;
        return this.engine.encryptBytes(perms, context.key());
    }
}
