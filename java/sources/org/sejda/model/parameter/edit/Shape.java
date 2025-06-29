package org.sejda.model.parameter.edit;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/edit/Shape.class */
public enum Shape implements FriendlyNamed {
    RECTANGLE("rectangle"),
    ELLIPSE("ellipse");

    private final String displayName;

    Shape(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
