package org.sejda.model.scale;

import jakarta.validation.constraints.PositiveOrZero;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/scale/Margins.class */
public class Margins {
    public static final Margins ZERO = new Margins(0.0d, 0.0d);

    @PositiveOrZero
    public final double top;

    @PositiveOrZero
    public final double right;

    @PositiveOrZero
    public final double bottom;

    @PositiveOrZero
    public final double left;

    public Margins(double top, double right, double bottom, double left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    public Margins(double topBottom, double rightLeft) {
        this(topBottom, rightLeft, topBottom, rightLeft);
    }

    public static double inchesToPoints(double inches) {
        return 72.0d * inches;
    }

    public static double pointsToInches(double points) {
        return points / 72.0d;
    }

    public Margins rotate() {
        return new Margins(this.left, this.top, this.right, this.bottom);
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("top", this.top).append("right", this.right).append("bottom", this.bottom).append("left", this.left).toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.top).append(this.right).append(this.bottom).append(this.left).toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Margins)) {
            return false;
        }
        Margins instance = (Margins) other;
        return new EqualsBuilder().append(this.top, instance.top).append(this.right, instance.right).append(this.bottom, instance.bottom).append(this.left, instance.left).isEquals();
    }
}
