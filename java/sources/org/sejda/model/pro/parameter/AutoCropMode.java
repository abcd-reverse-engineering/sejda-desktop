package org.sejda.model.pro.parameter;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/AutoCropMode.class */
public enum AutoCropMode implements FriendlyNamed {
    AUTOMATIC_CONSISTENT("automatic_consistent"),
    AUTOMATIC_MAXIMUM("automatic_maximum"),
    NONE("none");

    private final String displayName;

    AutoCropMode(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
