package org.sejda.sambox.pdmodel;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PageLayout.class */
public enum PageLayout {
    SINGLE_PAGE("SinglePage"),
    ONE_COLUMN("OneColumn"),
    TWO_COLUMN_LEFT("TwoColumnLeft"),
    TWO_COLUMN_RIGHT("TwoColumnRight"),
    TWO_PAGE_LEFT("TwoPageLeft"),
    TWO_PAGE_RIGHT("TwoPageRight");

    private final String value;

    public static PageLayout fromString(String value) {
        for (PageLayout instance : values()) {
            if (instance.value.equals(value)) {
                return instance;
            }
        }
        throw new IllegalArgumentException(value);
    }

    PageLayout(String value) {
        this.value = value;
    }

    public String stringValue() {
        return this.value;
    }
}
