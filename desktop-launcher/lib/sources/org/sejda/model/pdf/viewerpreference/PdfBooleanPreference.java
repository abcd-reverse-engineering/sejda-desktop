package org.sejda.model.pdf.viewerpreference;

import org.sejda.model.pdf.MinRequiredVersion;
import org.sejda.model.pdf.PdfVersion;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/viewerpreference/PdfBooleanPreference.class */
public enum PdfBooleanPreference implements MinRequiredVersion {
    HIDE_TOOLBAR(PdfVersion.VERSION_1_2),
    HIDE_MENUBAR(PdfVersion.VERSION_1_2),
    HIDE_WINDOW_UI(PdfVersion.VERSION_1_2),
    FIT_WINDOW(PdfVersion.VERSION_1_2),
    CENTER_WINDOW(PdfVersion.VERSION_1_2),
    DISPLAY_DOC_TITLE(PdfVersion.VERSION_1_4);

    private final PdfVersion minVersion;

    PdfBooleanPreference(PdfVersion minVersion) {
        this.minVersion = minVersion;
    }

    @Override // org.sejda.model.pdf.MinRequiredVersion
    public PdfVersion getMinVersion() {
        return this.minVersion;
    }
}
