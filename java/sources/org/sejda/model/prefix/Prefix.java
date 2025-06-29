package org.sejda.model.prefix;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/prefix/Prefix.class */
public enum Prefix implements FriendlyNamed {
    BASENAME("[BASENAME]"),
    TIMESTAMP("[TIMESTAMP]"),
    CURRENTPAGE("[CURRENTPAGE]"),
    FILENUMBER("[FILENUMBER]"),
    BOOKMARK("[BOOKMARK_NAME]"),
    BOOKMARK_STRICT("[BOOKMARK_NAME_STRICT]");

    private final String name;

    Prefix(String name) {
        this.name = name;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.name;
    }
}
