package org.sejda.sambox.pdmodel.font;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/FontMappers.class */
public final class FontMappers {
    private static FontMapper INSTANCE;

    private FontMappers() {
    }

    public static FontMapper instance() {
        if (INSTANCE == null) {
            INSTANCE = new FontMapperImpl();
        }
        return INSTANCE;
    }
}
