package org.sejda.model.pro.watermark;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.input.Source;
import org.sejda.model.validation.constraint.PositiveDimensions;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/watermark/Watermark.class */
public class Watermark {
    private Source<?> source;

    @NotNull
    private Location location;

    @Max(100)
    @Min(0)
    private int opacity;

    @PositiveDimensions
    private Dimension dimension;

    @NotNull
    private Point2D position;
    private int rotationDegrees;
    private String name;

    public Watermark(Source<?> source, Location location, int opacity, Dimension dimension, Point2D position, int rotationDegrees, String name) {
        this.location = Location.BEHIND;
        this.opacity = 100;
        this.position = new Point();
        this.rotationDegrees = 0;
        this.name = "";
        this.source = source;
        this.location = location;
        this.opacity = opacity;
        this.dimension = dimension;
        this.position = position;
        this.rotationDegrees = rotationDegrees;
        this.name = name;
    }

    public Watermark(Source<?> source) {
        this.location = Location.BEHIND;
        this.opacity = 100;
        this.position = new Point();
        this.rotationDegrees = 0;
        this.name = "";
        this.source = source;
    }

    public Watermark() {
        this.location = Location.BEHIND;
        this.opacity = 100;
        this.position = new Point();
        this.rotationDegrees = 0;
        this.name = "";
    }

    public Source<?> getSource() {
        return this.source;
    }

    public void setSource(Source<?> source) {
        this.source = source;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getOpacity() {
        return this.opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public int getRotationDegrees() {
        return this.rotationDegrees;
    }

    public void setRotationDegrees(int rotationDegrees) {
        this.rotationDegrees = rotationDegrees;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Watermark watermark = (Watermark) o;
        return new EqualsBuilder().append(this.opacity, watermark.opacity).append(this.rotationDegrees, watermark.rotationDegrees).append(this.source, watermark.source).append(this.location, watermark.location).append(this.dimension, watermark.dimension).append(this.position, watermark.position).append(this.name, watermark.name).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.source).append(this.location).append(this.opacity).append(this.dimension).append(this.position).append(this.rotationDegrees).append(this.name).toHashCode();
    }
}
