package org.sejda.model.output;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/output/ExistingOutputPolicy.class */
public enum ExistingOutputPolicy implements FriendlyNamed {
    OVERWRITE,
    RENAME,
    SKIP,
    FAIL;

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return name().toLowerCase();
    }
}
