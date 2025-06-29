package org.sejda.model.pdf.headerfooter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/headerfooter/Numbering.class */
public class Numbering {
    public static final Numbering NULL = new Numbering(NumberingStyle.EMPTY, 1);

    @NotNull
    private NumberingStyle numberingStyle;

    @Min(1)
    private int logicalPageNumber;

    public Numbering(NumberingStyle numberingStyle, int logicalPageNumber) {
        if (numberingStyle == null) {
            throw new IllegalArgumentException("Input numbering style cannot be null.");
        }
        this.numberingStyle = numberingStyle;
        this.logicalPageNumber = logicalPageNumber;
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.numberingStyle).append(this.logicalPageNumber).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Numbering other = (Numbering) obj;
        return new EqualsBuilder().append(this.numberingStyle, other.numberingStyle).append(this.logicalPageNumber, other.logicalPageNumber).isEquals();
    }

    public NumberingStyle getNumberingStyle() {
        return this.numberingStyle;
    }

    public int getLogicalPageNumber() {
        return this.logicalPageNumber;
    }

    public String styledLabelFor(int pageNumber) {
        return this.numberingStyle.toStyledString(pageNumber).trim();
    }
}
