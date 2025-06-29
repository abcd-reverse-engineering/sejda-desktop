package org.sejda.model.pdf.viewerpreference;

import org.sejda.model.FriendlyNamed;
import org.sejda.model.pdf.MinRequiredVersion;
import org.sejda.model.pdf.PdfVersion;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/viewerpreference/PdfPageLayout.class */
public enum PdfPageLayout implements MinRequiredVersion, FriendlyNamed {
    SINGLE_PAGE("singlepage", PdfVersion.VERSION_1_2),
    ONE_COLUMN("onecolumn", PdfVersion.VERSION_1_2),
    TWO_COLUMN_LEFT("twocolumnl", PdfVersion.VERSION_1_2),
    TWO_COLUMN_RIGHT("twocolumnr", PdfVersion.VERSION_1_2),
    TWO_PAGE_LEFT("twopagel", PdfVersion.VERSION_1_5),
    TWO_PAGE_RIGHT("twopager", PdfVersion.VERSION_1_5);

    private final PdfVersion minVersion;
    private final String displayName;

    PdfPageLayout(String displayName, PdfVersion minVersion) {
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
