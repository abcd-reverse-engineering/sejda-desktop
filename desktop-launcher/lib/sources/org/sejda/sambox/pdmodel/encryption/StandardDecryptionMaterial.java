package org.sejda.sambox.pdmodel.encryption;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/encryption/StandardDecryptionMaterial.class */
public class StandardDecryptionMaterial extends DecryptionMaterial {
    private final String password;

    public StandardDecryptionMaterial(String pwd) {
        this.password = pwd;
    }

    public String getPassword() {
        return this.password;
    }
}
