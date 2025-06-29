package org.sejda.sambox.pdmodel.font;

import org.apache.fontbox.FontBoxFont;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/FontMapping.class */
public class FontMapping<T extends FontBoxFont> {
    private final T font;
    private final boolean isFallback;

    public FontMapping(T font, boolean isFallback) {
        this.font = font;
        this.isFallback = isFallback;
    }

    public T getFont() {
        return this.font;
    }

    public boolean isFallback() {
        return this.isFallback;
    }
}
