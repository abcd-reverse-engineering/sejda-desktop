package org.sejda.model.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.optimization.OptimizationPolicy;
import org.sejda.model.parameter.base.DiscardableOutlineTaskParameters;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.parameter.base.OptimizableOutputTaskParameters;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PagesSelection;
import org.sejda.model.pdf.page.PredefinedSetOfPages;
import org.sejda.model.validation.constraint.HasSelectedPages;
import org.sejda.model.validation.constraint.NotAllowed;

@HasSelectedPages
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/ExtractPagesParameters.class */
public class ExtractPagesParameters extends MultiplePdfSourceMultipleOutputParameters implements PagesSelection, OptimizableOutputTaskParameters, DiscardableOutlineTaskParameters {

    @NotNull
    private OptimizationPolicy optimizationPolicy;
    private boolean discardOutline;

    @NotAllowed(disallow = {PredefinedSetOfPages.ALL_PAGES})
    @NotNull
    private final PredefinedSetOfPages predefinedSetOfPages;

    @Valid
    private final Set<PagesSelection> pageSelection;
    private boolean invertSelection;
    private boolean separateFileForEachRange;

    public ExtractPagesParameters() {
        this.optimizationPolicy = OptimizationPolicy.NO;
        this.discardOutline = false;
        this.pageSelection = new NullSafeSet();
        this.invertSelection = false;
        this.separateFileForEachRange = false;
        this.predefinedSetOfPages = PredefinedSetOfPages.NONE;
    }

    public ExtractPagesParameters(PredefinedSetOfPages predefinedSetOfPages) {
        this.optimizationPolicy = OptimizationPolicy.NO;
        this.discardOutline = false;
        this.pageSelection = new NullSafeSet();
        this.invertSelection = false;
        this.separateFileForEachRange = false;
        this.predefinedSetOfPages = (PredefinedSetOfPages) Optional.ofNullable(predefinedSetOfPages).orElse(PredefinedSetOfPages.NONE);
    }

    public void addPageRange(PagesSelection range) {
        this.pageSelection.add(range);
    }

    public void addAllPageRanges(Collection<PageRange> ranges) {
        this.pageSelection.addAll(ranges);
    }

    public PredefinedSetOfPages getPredefinedSetOfPages() {
        return this.predefinedSetOfPages;
    }

    public boolean hasPageSelection() {
        return !this.pageSelection.isEmpty();
    }

    @Override // org.sejda.model.pdf.page.PagesSelection
    public Set<Integer> getPages(int upperLimit) {
        Set<Integer> pages = new NullSafeSet<>();
        if (this.predefinedSetOfPages != PredefinedSetOfPages.NONE) {
            pages = this.predefinedSetOfPages.getPages(upperLimit);
        } else {
            for (PagesSelection range : this.pageSelection) {
                pages.addAll(range.getPages(upperLimit));
            }
        }
        if (!this.invertSelection) {
            return pages;
        }
        Set<Integer> invertedPages = new NullSafeSet<>();
        for (int i = 1; i <= upperLimit; i++) {
            if (!pages.contains(Integer.valueOf(i))) {
                invertedPages.add(Integer.valueOf(i));
            }
        }
        return invertedPages;
    }

    public List<Set<Integer>> getPagesSets(int upperLimit) {
        ArrayList<Set<Integer>> pages = new ArrayList<>();
        if (this.predefinedSetOfPages == PredefinedSetOfPages.NONE && isSeparateFileForEachRange()) {
            if (this.invertSelection) {
                Set<Integer> currentRange = new NullSafeSet<>();
                int previous = 0;
                Iterator<Integer> it = getPages(upperLimit).iterator();
                while (it.hasNext()) {
                    int current = it.next().intValue();
                    if (currentRange.isEmpty() || current == previous + 1) {
                        currentRange.add(Integer.valueOf(current));
                        previous = current;
                    } else {
                        pages.add(currentRange);
                        currentRange = new NullSafeSet<>();
                        currentRange.add(Integer.valueOf(current));
                        previous = current;
                    }
                }
                if (!currentRange.isEmpty()) {
                    pages.add(currentRange);
                }
            } else {
                for (PagesSelection range : this.pageSelection) {
                    pages.add(range.getPages(upperLimit));
                }
            }
            return pages;
        }
        return Collections.singletonList(getPages(upperLimit));
    }

    @Override // org.sejda.model.parameter.base.OptimizableOutputTaskParameters
    public OptimizationPolicy getOptimizationPolicy() {
        return this.optimizationPolicy;
    }

    @Override // org.sejda.model.parameter.base.OptimizableOutputTaskParameters
    public void setOptimizationPolicy(OptimizationPolicy optimizationPolicy) {
        this.optimizationPolicy = optimizationPolicy;
    }

    @Override // org.sejda.model.parameter.base.DiscardableOutlineTaskParameters
    public boolean discardOutline() {
        return this.discardOutline;
    }

    @Override // org.sejda.model.parameter.base.DiscardableOutlineTaskParameters
    public void discardOutline(boolean discardOutline) {
        this.discardOutline = discardOutline;
    }

    public boolean isInvertSelection() {
        return this.invertSelection;
    }

    public void setInvertSelection(boolean invertSelection) {
        this.invertSelection = invertSelection;
    }

    public boolean isSeparateFileForEachRange() {
        return this.separateFileForEachRange;
    }

    public void setSeparateFileForEachRange(boolean separateFileForEachRange) {
        this.separateFileForEachRange = separateFileForEachRange;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.optimizationPolicy).append(this.discardOutline).append(this.predefinedSetOfPages).append(this.invertSelection).append(this.separateFileForEachRange).append(this.pageSelection).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExtractPagesParameters)) {
            return false;
        }
        ExtractPagesParameters parameter = (ExtractPagesParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.predefinedSetOfPages, parameter.predefinedSetOfPages).append(this.optimizationPolicy, parameter.optimizationPolicy).append(this.discardOutline, parameter.discardOutline).append(this.pageSelection, parameter.pageSelection).append(this.invertSelection, parameter.invertSelection).append(this.separateFileForEachRange, parameter.separateFileForEachRange).isEquals();
    }
}
