package org.sejda.sambox.pdmodel.interactive.annotation.layout;

import org.sejda.sambox.pdmodel.font.PDFont;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/layout/AppearanceStyle.class */
public class AppearanceStyle {
    private PDFont font;
    private float fontSize = 12.0f;
    private float leading = 14.4f;

    PDFont getFont() {
        return this.font;
    }

    public void setFont(PDFont font) {
        this.font = font;
    }

    float getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
        this.leading = fontSize * 1.2f;
    }

    float getLeading() {
        return this.leading;
    }

    void setLeading(float leading) {
        this.leading = leading;
    }
}
