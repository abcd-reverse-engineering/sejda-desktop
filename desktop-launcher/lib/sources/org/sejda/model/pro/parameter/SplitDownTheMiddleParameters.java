package org.sejda.model.pro.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.parameter.ExcludedPagesSelection;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pro.split.SplitDownTheMiddleMode;
import org.sejda.model.repaginate.Repagination;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/SplitDownTheMiddleParameters.class */
public class SplitDownTheMiddleParameters extends MultiplePdfSourceMultipleOutputParameters implements ExcludedPagesSelection {

    @NotNull
    private Repagination repagination = Repagination.NONE;

    @Valid
    public final Set<PageRange> excludedPagesSelection = new NullSafeSet();

    @NotNull
    private SplitDownTheMiddleMode mode = SplitDownTheMiddleMode.AUTO;
    private double ratio = 1.0d;
    private boolean rightToLeft = false;
    private boolean autoDetectExcludedPages = false;

    public Repagination getRepagination() {
        return this.repagination;
    }

    public void setRepagination(Repagination repagination) {
        this.repagination = repagination;
    }

    @Override // org.sejda.model.parameter.ExcludedPagesSelection
    public Set<PageRange> getExcludedPagesSelection() {
        return this.excludedPagesSelection;
    }

    public SplitDownTheMiddleMode getMode() {
        return this.mode;
    }

    public void setMode(SplitDownTheMiddleMode mode) {
        this.mode = mode;
    }

    public double getRatio() {
        return this.ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public boolean isRightToLeft() {
        return this.rightToLeft;
    }

    public void setRightToLeft(boolean rightToLeft) {
        this.rightToLeft = rightToLeft;
    }

    public boolean isAutoDetectExcludedPages() {
        return this.autoDetectExcludedPages;
    }

    public void setAutoDetectExcludedPages(boolean autoDetectExcludedPages) {
        this.autoDetectExcludedPages = autoDetectExcludedPages;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().append(getRepagination()).append(getExcludedPagesSelection()).append(getRatio()).append(getMode()).append(isRightToLeft()).append(isAutoDetectExcludedPages()).appendSuper(super.hashCode()).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SplitDownTheMiddleParameters)) {
            return false;
        }
        SplitDownTheMiddleParameters parameter = (SplitDownTheMiddleParameters) other;
        return new EqualsBuilder().append(getRepagination(), parameter.getRepagination()).append(getExcludedPagesSelection(), parameter.getExcludedPagesSelection()).append(getRatio(), parameter.getRatio()).append(getMode(), parameter.getMode()).append(isRightToLeft(), parameter.isRightToLeft()).append(isAutoDetectExcludedPages(), parameter.isAutoDetectExcludedPages()).appendSuper(super.equals(other)).isEquals();
    }
}
