package org.sejda.model.optimization;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/optimization/OptimizationPolicy.class */
public enum OptimizationPolicy implements FriendlyNamed {
    YES("yes"),
    NO("no"),
    AUTO("auto");

    private final String displayName;

    OptimizationPolicy(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
