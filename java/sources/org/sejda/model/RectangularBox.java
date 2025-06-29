package org.sejda.model;

import java.awt.Point;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.commons.util.RequireUtils;
import org.sejda.model.rotation.Rotation;
import org.sejda.model.validation.constraint.ValidCoordinates;

@ValidCoordinates
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/RectangularBox.class */
public final class RectangularBox {
    private int bottom;
    private int left;
    private int top;
    private int right;

    public RectangularBox(int bottom, int left, int top, int right) {
        RequireUtils.requireArg(top > bottom, "Top must be greater then bottom");
        RequireUtils.requireArg(right > left, "Right must be greater then left");
        this.bottom = bottom;
        this.left = left;
        this.top = top;
        this.right = right;
    }

    public int getBottom() {
        return this.bottom;
    }

    public int getLeft() {
        return this.left;
    }

    public int getTop() {
        return this.top;
    }

    public int getRight() {
        return this.right;
    }

    public void rotate(Rotation desiredRotation) {
        if (desiredRotation != null) {
            Rotation rotationRotateClockwise = Rotation.DEGREES_0;
            while (true) {
                Rotation currentRotation = rotationRotateClockwise;
                if (!currentRotation.equals(desiredRotation)) {
                    switchTopRight();
                    switchBottomLeft();
                    rotationRotateClockwise = currentRotation.rotateClockwise();
                } else {
                    return;
                }
            }
        }
    }

    private void switchTopRight() {
        int tmp = this.top;
        this.top = this.right;
        this.right = tmp;
    }

    private void switchBottomLeft() {
        int tmp = this.bottom;
        this.bottom = this.left;
        this.left = tmp;
    }

    public String toString() {
        return new ToStringBuilder(this).append("right", this.right).append("top", this.top).append("left", this.left).append("bottom", this.bottom).toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.top).append(this.right).append(this.left).append(this.bottom).toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RectangularBox)) {
            return false;
        }
        RectangularBox instance = (RectangularBox) other;
        return new EqualsBuilder().append(this.top, instance.getTop()).append(this.right, instance.getRight()).append(this.left, instance.getLeft()).append(this.bottom, instance.getBottom()).isEquals();
    }

    public static RectangularBox newInstance(int bottom, int left, int top, int right) {
        return new RectangularBox(bottom, left, top, right);
    }

    public static RectangularBox newInstanceFromPoints(Point bottomLeft, Point topRight) {
        if (bottomLeft == null || topRight == null) {
            throw new IllegalArgumentException("null point is not allowed.");
        }
        return newInstance(bottomLeft.y, bottomLeft.x, topRight.y, topRight.x);
    }

    public TopLeftRectangularBox toTopLeftRectangularBox() {
        return new TopLeftRectangularBox(this.left, this.top, this.right - this.left, this.bottom - this.top);
    }
}
