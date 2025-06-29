package org.sejda.model.pdf.label;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/label/PdfLabelNumberingStyle.class */
public enum PdfLabelNumberingStyle implements FriendlyNamed {
    ARABIC("arabic"),
    UPPERCASE_ROMANS("uroman"),
    LOWERCASE_ROMANS("lroman"),
    UPPERCASE_LETTERS("uletter"),
    LOWERCASE_LETTERS("lletter"),
    EMPTY("empty");

    private final String displayName;

    PdfLabelNumberingStyle(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
