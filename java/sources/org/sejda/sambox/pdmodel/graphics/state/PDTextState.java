package org.sejda.sambox.pdmodel.graphics.state;

import org.sejda.sambox.pdmodel.font.PDFont;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/state/PDTextState.class */
public class PDTextState implements Cloneable {
    private PDFont font;
    private float fontSize;
    private float characterSpacing = 0.0f;
    private float wordSpacing = 0.0f;
    private float horizontalScaling = 100.0f;
    private float leading = 0.0f;
    private RenderingMode renderingMode = RenderingMode.FILL;
    private float rise = 0.0f;
    private boolean knockout = true;

    public float getCharacterSpacing() {
        return this.characterSpacing;
    }

    public void setCharacterSpacing(float value) {
        this.characterSpacing = value;
    }

    public float getWordSpacing() {
        return this.wordSpacing;
    }

    public void setWordSpacing(float value) {
        this.wordSpacing = value;
    }

    public float getHorizontalScaling() {
        return this.horizontalScaling;
    }

    public void setHorizontalScaling(float value) {
        this.horizontalScaling = value;
    }

    public float getLeading() {
        return this.leading;
    }

    public void setLeading(float value) {
        this.leading = value;
    }

    public PDFont getFont() {
        return this.font;
    }

    public void setFont(PDFont value) {
        this.font = value;
    }

    public float getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(float value) {
        this.fontSize = value;
    }

    public RenderingMode getRenderingMode() {
        return this.renderingMode;
    }

    public void setRenderingMode(RenderingMode renderingMode) {
        this.renderingMode = renderingMode;
    }

    public float getRise() {
        return this.rise;
    }

    public void setRise(float value) {
        this.rise = value;
    }

    public boolean getKnockoutFlag() {
        return this.knockout;
    }

    public void setKnockoutFlag(boolean value) {
        this.knockout = value;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public PDTextState m521clone() {
        try {
            return (PDTextState) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
