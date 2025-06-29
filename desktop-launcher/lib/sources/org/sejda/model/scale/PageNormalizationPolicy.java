package org.sejda.model.scale;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/scale/PageNormalizationPolicy.class */
public enum PageNormalizationPolicy implements FriendlyNamed {
    NONE("none"),
    SAME_WIDTH("same_width"),
    SAME_WIDTH_ORIENTATION_BASED("same_width_orientation_based");

    private final String displayName;

    PageNormalizationPolicy(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
