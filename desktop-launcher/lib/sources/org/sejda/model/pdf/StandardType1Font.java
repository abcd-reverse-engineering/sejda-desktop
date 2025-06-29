package org.sejda.model.pdf;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/StandardType1Font.class */
public enum StandardType1Font implements FriendlyNamed {
    TIMES_ROMAN("Times-Roman"),
    TIMES_BOLD("Times-Bold"),
    TIMES_ITALIC("Times-Italic"),
    TIMES_BOLD_ITALIC("Times-BoldItalic"),
    HELVETICA("Helvetica"),
    HELVETICA_BOLD("Helvetica-Bold"),
    HELVETICA_OBLIQUE("Helvetica-Oblique"),
    HELVETICA_BOLD_OBLIQUE("Helvetica-BoldOblique"),
    CURIER("Courier"),
    CURIER_BOLD("Courier-Bold"),
    CURIER_OBLIQUE("Courier-Oblique"),
    CURIER_BOLD_OBLIQUE("Courier-BoldOblique"),
    SYMBOL("Symbol"),
    ZAPFDINGBATS("ZapfDingbats");

    private final String displayName;

    StandardType1Font(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
