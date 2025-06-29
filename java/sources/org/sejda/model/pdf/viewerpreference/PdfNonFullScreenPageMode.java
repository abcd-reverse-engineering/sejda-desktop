package org.sejda.model.pdf.viewerpreference;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/viewerpreference/PdfNonFullScreenPageMode.class */
public enum PdfNonFullScreenPageMode implements FriendlyNamed {
    USE_NONE("nfsnone"),
    USE_OUTLINES("nfsoutlines"),
    USE_THUMNS("nfsthumbs"),
    USE_OC("nfsocontent");

    private final String displayName;

    PdfNonFullScreenPageMode(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
