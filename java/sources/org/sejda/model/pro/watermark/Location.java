package org.sejda.model.pro.watermark;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/watermark/Location.class */
public enum Location implements FriendlyNamed {
    BEHIND("behind"),
    OVER("over");

    private final String displayName;

    Location(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
