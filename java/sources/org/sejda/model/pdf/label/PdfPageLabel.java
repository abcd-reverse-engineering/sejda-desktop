package org.sejda.model.pdf.label;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/label/PdfPageLabel.class */
public final class PdfPageLabel {

    @NotNull
    private String labelPrefix;

    @NotNull
    private PdfLabelNumberingStyle numberingStyle;

    @Min(1)
    private int logicalPageNumber;

    private PdfPageLabel(String labelPrefix, PdfLabelNumberingStyle numberingStyle, int logicalPageNumber) {
        this.labelPrefix = labelPrefix;
        this.numberingStyle = numberingStyle;
        this.logicalPageNumber = logicalPageNumber;
    }

    public String getLabelPrefix() {
        return this.labelPrefix;
    }

    public PdfLabelNumberingStyle getNumberingStyle() {
        return this.numberingStyle;
    }

    public int getLogicalPageNumber() {
        return this.logicalPageNumber;
    }

    public String toString() {
        return new ToStringBuilder(this).append(this.labelPrefix).append(this.numberingStyle).append("logicalPageNumber", this.logicalPageNumber).toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.labelPrefix).append(this.numberingStyle).append(this.logicalPageNumber).toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PdfPageLabel)) {
            return false;
        }
        PdfPageLabel pageLabel = (PdfPageLabel) other;
        return new EqualsBuilder().append(this.labelPrefix, pageLabel.getLabelPrefix()).append(this.numberingStyle, pageLabel.getNumberingStyle()).append(this.logicalPageNumber, pageLabel.getLogicalPageNumber()).isEquals();
    }

    public static PdfPageLabel newInstanceWithoutLabel(PdfLabelNumberingStyle numberingStyle, int logicalPageNumber) {
        return newInstanceWithLabel("", numberingStyle, logicalPageNumber);
    }

    public static PdfPageLabel newInstanceWithLabel(String label, PdfLabelNumberingStyle numberingStyle, int logicalPageNumber) {
        RequireUtils.requireArg(logicalPageNumber > 0, "Input page number must be positive");
        RequireUtils.requireNotNullArg(label, "Input label cannot be null");
        RequireUtils.requireNotNullArg(numberingStyle, "Input numbering style cannot be null");
        return new PdfPageLabel(label, numberingStyle, logicalPageNumber);
    }
}
