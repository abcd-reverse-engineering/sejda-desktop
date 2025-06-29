package org.sejda.model.scale;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/scale/ScaleType.class */
public enum ScaleType implements FriendlyNamed {
    PAGE("page"),
    CONTENT("content");

    private final String displayName;

    ScaleType(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
