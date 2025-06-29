package org.sejda.model.pro.parameter;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.PageSize;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PageRangeSelection;
import org.sejda.model.pdf.page.PagesSelection;
import org.sejda.model.scale.Margins;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/ResizePagesParameters.class */
public class ResizePagesParameters extends MultiplePdfSourceMultipleOutputParameters implements PageRangeSelection, PagesSelection {

    @Valid
    public Margins margins;
    public PageSize pageSize;

    @Valid
    private final Set<PageRange> pageSelection = new NullSafeSet();

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

    public Margins getMargins() {
        return this.margins;
    }

    public void setMargins(Margins margins) {
        this.margins = margins;
    }

    public PageSize getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.margins).append(this.pageSelection).append(this.pageSize).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ResizePagesParameters)) {
            return false;
        }
        ResizePagesParameters parameter = (ResizePagesParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.margins, parameter.margins).append(this.pageSize, parameter.pageSize).append(this.pageSelection, parameter.pageSelection).isEquals();
    }
}
