package org.sejda.model.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.input.PdfSource;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PageRangeSelection;
import org.sejda.model.pdf.page.PagesSelection;
import org.sejda.model.validation.constraint.NoIntersections;

@NoIntersections
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/AddBackPagesParameters.class */
public class AddBackPagesParameters extends MultiplePdfSourceMultipleOutputParameters implements PageRangeSelection, PagesSelection {

    @Min(1)
    private int step = 1;

    @Valid
    private final Set<PageRange> pageSelection = new NullSafeSet();

    @Valid
    @NotNull
    private PdfSource<?> backPagesSource;

    public PdfSource<?> getBackPagesSource() {
        return this.backPagesSource;
    }

    public void setBackPagesSource(PdfSource<?> backPagesSource) {
        this.backPagesSource = backPagesSource;
    }

    @Override // org.sejda.model.pdf.page.PageRangeSelection
    public Set<PageRange> getPageSelection() {
        return Collections.unmodifiableSet(this.pageSelection);
    }

    public void addPageRange(PageRange range) {
        this.pageSelection.add(range);
    }

    public void addAllPageRanges(Collection<PageRange> ranges) {
        this.pageSelection.addAll(ranges);
    }

    @Override // org.sejda.model.pdf.page.PagesSelection
    public Set<Integer> getPages(int totalNumberOfPage) {
        if (this.pageSelection.isEmpty()) {
            return new PageRange(1).getPages(totalNumberOfPage);
        }
        Set<Integer> retSet = new NullSafeSet<>();
        for (PageRange range : getPageSelection()) {
            retSet.addAll(range.getPages(totalNumberOfPage));
        }
        return retSet;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.backPagesSource).append(this.pageSelection).append(this.step).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AddBackPagesParameters)) {
            return false;
        }
        AddBackPagesParameters parameter = (AddBackPagesParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.backPagesSource, parameter.getBackPagesSource()).append(this.pageSelection, parameter.pageSelection).append(this.step, parameter.step).isEquals();
    }
}
