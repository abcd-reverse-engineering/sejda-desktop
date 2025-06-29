package org.sejda.model.pdf.viewerpreference;

import org.sejda.model.FriendlyNamed;
import org.sejda.model.pdf.MinRequiredVersion;
import org.sejda.model.pdf.PdfVersion;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/viewerpreference/PdfPrintScaling.class */
public enum PdfPrintScaling implements MinRequiredVersion, FriendlyNamed {
    NONE(PdfVersion.VERSION_1_6, "none"),
    APP_DEFAULT(PdfVersion.VERSION_1_6, "app_default");

    private final PdfVersion minVersion;
    private final String displayName;

    PdfPrintScaling(PdfVersion minVersion, String displayName) {
        this.minVersion = minVersion;
        this.displayName = displayName;
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
