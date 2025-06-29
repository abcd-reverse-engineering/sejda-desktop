package org.sejda.model.pdf.transition;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/transition/PdfPageTransition.class */
public final class PdfPageTransition {

    @NotNull
    private PdfPageTransitionStyle style;

    @Min(1)
    private int transitionDuration;

    @Min(1)
    private int displayDuration;

    private PdfPageTransition(PdfPageTransitionStyle style, int transitionDuration, int displayDuration) {
        this.style = style;
        this.transitionDuration = transitionDuration;
        this.displayDuration = displayDuration;
    }

    public PdfPageTransitionStyle getStyle() {
        return this.style;
    }

    public int getTransitionDuration() {
        return this.transitionDuration;
    }

    public int getDisplayDuration() {
        return this.displayDuration;
    }

    public String toString() {
        return new ToStringBuilder(this).append(this.style).append("transitionDuration", this.transitionDuration).append("displayDuration", this.displayDuration).toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.style).append(this.transitionDuration).append(this.displayDuration).toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PdfPageTransition)) {
            return false;
        }
        PdfPageTransition transition = (PdfPageTransition) other;
        return new EqualsBuilder().append(this.style, transition.getStyle()).append(this.transitionDuration, transition.getTransitionDuration()).append(this.displayDuration, transition.getDisplayDuration()).isEquals();
    }

    public static PdfPageTransition newInstance(PdfPageTransitionStyle style, int transitionDuration, int displayDuration) {
        RequireUtils.requireArg(displayDuration > 0, "Input display duration must be positive");
        RequireUtils.requireArg(transitionDuration > 0, "Input transition duration must be positive");
        RequireUtils.requireNotNullArg(style, "Input style cannot be null");
        return new PdfPageTransition(style, transitionDuration, displayDuration);
    }
}
