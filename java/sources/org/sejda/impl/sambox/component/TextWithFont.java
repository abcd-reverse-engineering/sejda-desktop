package org.sejda.impl.sambox.component;

import org.sejda.sambox.pdmodel.font.PDFont;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/TextWithFont.class */
public class TextWithFont {
    private final PDFont font;
    private final String text;

    public TextWithFont(String text, PDFont font) {
        this.font = font;
        this.text = text;
    }

    public PDFont getFont() {
        return this.font;
    }

    public String getText() {
        return this.text;
    }

    public String toString() {
        return "[" + (this.font == null ? "null" : this.font.getName()) + "] '" + getText() + "'";
    }
}
