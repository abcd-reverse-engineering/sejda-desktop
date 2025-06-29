package org.sejda.model;

import java.awt.Rectangle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/TopLeftRectangularBox.class */
public class TopLeftRectangularBox {
    final int left;
    final int top;
    final int width;
    final int height;

    public TopLeftRectangularBox(int left, int top, int width, int height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    public TopLeftRectangularBox(Rectangle r) {
        this(r.x, r.y, r.width, r.height);
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("left", this.left).append("top", this.top).append("width", this.width).append("height", this.height).toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.left).append(this.top).append(this.width).append(this.height).toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TopLeftRectangularBox)) {
            return false;
        }
        TopLeftRectangularBox instance = (TopLeftRectangularBox) other;
        return new EqualsBuilder().append(this.left, instance.left).append(this.top, instance.top).append(this.width, instance.width).append(this.height, instance.height).isEquals();
    }

    public Rectangle asRectangle() {
        return new Rectangle(this.left, this.top, this.width, this.height);
    }

    public TopLeftRectangularBox intersection(TopLeftRectangularBox other) {
        return new TopLeftRectangularBox(asRectangle().intersection(other.asRectangle()));
    }

    public TopLeftRectangularBox withPadding(int padding) {
        return new TopLeftRectangularBox(this.left - padding, this.top - padding, this.width + (2 * padding), this.height + (2 * padding));
    }

    public boolean containsPoint(float x, float y) {
        return ((float) this.left) <= x && ((float) (this.left + this.width)) >= x && ((float) this.top) <= y && ((float) (this.top + this.height)) >= y;
    }

    public int getLeft() {
        return this.left;
    }

    public int getTop() {
        return this.top;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
