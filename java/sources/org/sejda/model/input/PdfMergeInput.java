package org.sejda.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PageRangeSelection;
import org.sejda.model.pdf.page.PagesSelection;
import org.sejda.model.validation.constraint.NoIntersections;

@NoIntersections
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/PdfMergeInput.class */
public class PdfMergeInput implements PageRangeSelection, PagesSelection, MergeInput {

    @NotNull
    @Valid
    private PdfSource<?> source;

    @Valid
    private final Set<PageRange> pageSelection = new NullSafeSet();

    public PdfMergeInput(PdfSource<?> source, Set<PageRange> pageSelection) {
        this.source = source;
        this.pageSelection.addAll(pageSelection);
    }

    public PdfMergeInput(PdfSource<?> source) {
        this.source = source;
    }

    public PdfSource<?> getSource() {
        return this.source;
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

    public boolean isAllPages() {
        return this.pageSelection.isEmpty();
    }

    @Override // org.sejda.model.pdf.page.PagesSelection
    public Set<Integer> getPages(int totalNumberOfPage) {
        Set<Integer> retSet = new NullSafeSet<>();
        if (isAllPages()) {
            for (int i = 1; i <= totalNumberOfPage; i++) {
                retSet.add(Integer.valueOf(i));
            }
        } else {
            for (PageRange range : getPageSelection()) {
                retSet.addAll(range.getPages(totalNumberOfPage));
            }
        }
        return retSet;
    }

    public String toString() {
        return new ToStringBuilder(this).append(this.source).append(this.pageSelection).toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.source).append(this.pageSelection).toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PdfMergeInput)) {
            return false;
        }
        PdfMergeInput input = (PdfMergeInput) other;
        return new EqualsBuilder().append(this.source, input.getSource()).append(this.pageSelection, input.pageSelection).isEquals();
    }
}
