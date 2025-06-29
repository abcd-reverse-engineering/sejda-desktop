package org.sejda.model.pdf.viewerpreference;

import org.sejda.model.FriendlyNamed;
import org.sejda.model.pdf.MinRequiredVersion;
import org.sejda.model.pdf.PdfVersion;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/viewerpreference/PdfDuplex.class */
public enum PdfDuplex implements MinRequiredVersion, FriendlyNamed {
    SIMPLEX("simplex", PdfVersion.VERSION_1_7),
    DUPLEX_FLIP_SHORT_EDGE("duplex_flip_short_edge", PdfVersion.VERSION_1_7),
    DUPLEX_FLIP_LONG_EDGE("duplex_flip_long_edge", PdfVersion.VERSION_1_7);

    private final PdfVersion minVersion;
    private final String displayName;

    PdfDuplex(String displayName, PdfVersion minVersion) {
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
