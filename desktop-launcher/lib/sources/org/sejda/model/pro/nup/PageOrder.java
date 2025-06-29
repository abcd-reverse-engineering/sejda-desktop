package org.sejda.model.pro.nup;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/nup/PageOrder.class */
public enum PageOrder implements FriendlyNamed {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical");

    private final String displayName;

    PageOrder(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
