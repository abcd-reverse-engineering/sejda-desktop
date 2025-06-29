package org.sejda.model.pro.pdf.encryption;

import org.sejda.model.FriendlyNamed;
import org.sejda.model.pdf.MinRequiredVersion;
import org.sejda.model.pdf.PdfVersion;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/pdf/encryption/PdfEncryption.class */
public enum PdfEncryption implements MinRequiredVersion, FriendlyNamed {
    STANDARD_ENC_128("rc4_128", PdfVersion.VERSION_1_2),
    AES_ENC_128("aes_128", PdfVersion.VERSION_1_6),
    AES_ENC_256("aes_256", PdfVersion.VERSION_1_7);

    private final PdfVersion minVersion;
    private final String displayName;

    PdfEncryption(String displayName, PdfVersion minVersion) {
        this.displayName = displayName;
        this.minVersion = minVersion;
    }

    @Override // org.sejda.model.pdf.MinRequiredVersion
    public PdfVersion getMinVersion() {
        return this.minVersion;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
