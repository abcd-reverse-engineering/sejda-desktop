package org.sejda.model.pdf.viewerpreference;

import org.sejda.model.FriendlyNamed;
import org.sejda.model.pdf.MinRequiredVersion;
import org.sejda.model.pdf.PdfVersion;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/viewerpreference/PdfDirection.class */
public enum PdfDirection implements MinRequiredVersion, FriendlyNamed {
    LEFT_TO_RIGHT("l2r", PdfVersion.VERSION_1_3),
    RIGHT_TO_LEFT("r2l", PdfVersion.VERSION_1_3);

    private final PdfVersion minVersion;
    private final String displayName;

    PdfDirection(String displayName, PdfVersion minVersion) {
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
