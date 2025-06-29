package org.sejda.model;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/PageOrientation.class */
public enum PageOrientation implements FriendlyNamed {
    PORTRAIT("portrait"),
    LANDSCAPE("landscape"),
    AUTO("auto");

    private final String name;

    PageOrientation(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return getName();
    }
}
