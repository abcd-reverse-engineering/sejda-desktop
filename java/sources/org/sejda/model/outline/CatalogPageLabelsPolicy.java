package org.sejda.model.outline;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/outline/CatalogPageLabelsPolicy.class */
public enum CatalogPageLabelsPolicy implements FriendlyNamed {
    DISCARD("discard"),
    RETAIN("retain");

    private final String displayName;

    CatalogPageLabelsPolicy(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
