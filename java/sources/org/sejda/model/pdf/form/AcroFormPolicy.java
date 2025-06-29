package org.sejda.model.pdf.form;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/form/AcroFormPolicy.class */
public enum AcroFormPolicy implements FriendlyNamed {
    DISCARD("discard"),
    MERGE("merge"),
    MERGE_RENAMING_EXISTING_FIELDS("merge_renaming"),
    FLATTEN("flatten");

    private final String displayName;

    AcroFormPolicy(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
