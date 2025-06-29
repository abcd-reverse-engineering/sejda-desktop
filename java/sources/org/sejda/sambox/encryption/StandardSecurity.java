package org.sejda.sambox.encryption;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import org.sejda.sambox.pdmodel.encryption.AccessPermission;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/StandardSecurity.class */
public class StandardSecurity {
    public final String ownerPassword;
    public final String userPassword;
    public final AccessPermission permissions;
    public final StandardSecurityEncryption encryption;
    public final boolean encryptMetadata;

    public StandardSecurity(String ownerPassword, String userPassword, StandardSecurityEncryption encryption, boolean encryptMetadata) {
        this(ownerPassword, userPassword, encryption, new AccessPermission(), encryptMetadata);
    }

    public StandardSecurity(String ownerPassword, String userPassword, StandardSecurityEncryption encryption, AccessPermission permissions, boolean encryptMetadata) {
        Objects.requireNonNull(encryption, "Encryption algorithm cannot be null");
        this.ownerPassword = Objects.toString(ownerPassword, "");
        this.userPassword = Objects.toString(userPassword, "");
        this.encryption = encryption;
        this.permissions = (AccessPermission) Optional.ofNullable(permissions).orElseGet(AccessPermission::new);
        this.encryptMetadata = encryptMetadata || StandardSecurityEncryption.ARC4_128.equals(encryption);
    }

    byte[] getUserPasswordUTF() {
        return EncryptUtils.truncate127(this.userPassword.getBytes(StandardCharsets.UTF_8));
    }

    byte[] getOwnerPasswordUTF() {
        return EncryptUtils.truncate127(this.ownerPassword.getBytes(StandardCharsets.UTF_8));
    }

    byte[] getUserPassword() {
        return this.userPassword.getBytes(StandardCharsets.ISO_8859_1);
    }

    byte[] getOwnerPassword() {
        return this.ownerPassword.getBytes(StandardCharsets.ISO_8859_1);
    }
}
