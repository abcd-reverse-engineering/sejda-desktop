package org.sejda.model.repaginate;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/repaginate/Repagination.class */
public enum Repagination implements FriendlyNamed {
    LAST_FIRST("last-first"),
    NONE("none");

    private final String displayName;

    Repagination(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
