package org.sejda.model.pdf.viewerpreference;

import org.sejda.model.FriendlyNamed;
import org.sejda.model.pdf.MinRequiredVersion;
import org.sejda.model.pdf.PdfVersion;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/viewerpreference/PdfPageMode.class */
public enum PdfPageMode implements MinRequiredVersion, FriendlyNamed {
    USE_NONE("none", PdfVersion.VERSION_1_2),
    USE_OUTLINES("outlines", PdfVersion.VERSION_1_2),
    USE_THUMBS("thumbs", PdfVersion.VERSION_1_2),
    FULLSCREEN("fullscreen", PdfVersion.VERSION_1_2),
    USE_OC("ocontent", PdfVersion.VERSION_1_5),
    USE_ATTACHMENTS("attachments", PdfVersion.VERSION_1_6);

    private final PdfVersion minVersion;
    private final String displayName;

    PdfPageMode(String displayName, PdfVersion minVersion) {
        this.displayName = displayName;
        this.minVersion = minVersion;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }

    @Override // org.sejda.model.pdf.MinRequiredVersion
    public PdfVersion getMinVersion() {
        return this.minVersion;
    }
}
