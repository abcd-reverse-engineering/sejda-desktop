package org.sejda.model.pro.pdf.collection;

import org.sejda.model.FriendlyNamed;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.StandardStructureTypes;
import org.sejda.sambox.pdmodel.interactive.measurement.PDNumberFormatDictionary;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/pdf/collection/InitialView.class */
public enum InitialView implements FriendlyNamed {
    DETAILS("details", "D"),
    TILES("tiles", PDNumberFormatDictionary.FRACTIONAL_DISPLAY_TRUNCATE),
    HIDDEN("hidden", StandardStructureTypes.H);

    private final String displayName;
    public final String value;

    InitialView(String displayName, String value) {
        this.displayName = displayName;
        this.value = value;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
