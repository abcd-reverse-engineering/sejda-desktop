package org.sejda.model.pro.split;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/split/SplitDownTheMiddleMode.class */
public enum SplitDownTheMiddleMode implements FriendlyNamed {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical"),
    AUTO("auto");

    private final String displayName;

    SplitDownTheMiddleMode(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
