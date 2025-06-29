package org.sejda.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/PageSize.class */
public class PageSize {
    private final float width;
    private final float height;
    private final String name;
    private static final float POINTS_PER_INCH = 72.0f;
    private static final float POINTS_PER_MM = 2.8346457f;
    public static final PageSize LETTER = new PageSize(612.0f, 792.0f, "Letter");
    public static final PageSize LEGAL = new PageSize(612.0f, 1008.0f, "Legal");
    public static final PageSize LEDGER = new PageSize(792.0f, 1224.0f, "Ledger");
    public static final PageSize TABLOID = new PageSize(1224.0f, 792.0f, "Tabloid");
    public static final PageSize EXECUTIVE = new PageSize(522.0f, 759.60004f, "Executive");
    public static final PageSize A0 = new PageSize(2383.937f, 3370.3938f, "A0");
    public static final PageSize A1 = new PageSize(1683.7795f, 2383.937f, "A1");
    public static final PageSize A2 = new PageSize(1190.5513f, 1683.7795f, "A2");
    public static final PageSize A3 = new PageSize(841.8898f, 1190.5513f, "A3");
    public static final PageSize A4 = new PageSize(595.27563f, 841.8898f, "A4");
    public static final PageSize A5 = new PageSize(419.52756f, 595.27563f, "A5");
    public static final PageSize A6 = new PageSize(297.63782f, 419.52756f, "A6");

    public PageSize(float width, float height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public PageSize(float width, float height) {
        this(width, height, null);
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public PageSize rotate() {
        return new PageSize(this.height, this.width);
    }

    public boolean isLandscape() {
        return getWidth() > getHeight();
    }

    public PageSize toPortrait() {
        if (isLandscape()) {
            return rotate();
        }
        return this;
    }

    public PageSize toLandscape() {
        if (!isLandscape()) {
            return rotate();
        }
        return this;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PageSize)) {
            return false;
        }
        PageSize instance = (PageSize) other;
        return new EqualsBuilder().append(this.width, instance.width).append(this.height, instance.height).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.width).append(this.height).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("widthInches", this.width).append("heightInches", this.height).append("widthInches", this.width).append("height", this.height).toString();
    }

    public static PageSize fromInches(float widthInch, float heightInch) {
        return new PageSize(widthInch * POINTS_PER_INCH, heightInch * POINTS_PER_INCH);
    }

    public static PageSize fromMillimeters(float widthInch, float heightInch) {
        return new PageSize(widthInch * POINTS_PER_MM, heightInch * POINTS_PER_MM);
    }
}
