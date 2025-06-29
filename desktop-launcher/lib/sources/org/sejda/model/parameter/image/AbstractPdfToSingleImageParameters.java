package org.sejda.model.parameter.image;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.image.ImageColorType;
import org.sejda.model.output.SingleTaskOutput;
import org.sejda.model.parameter.base.SingleOutputTaskParameters;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PageRangeSelection;
import org.sejda.model.pdf.page.PagesSelection;
import org.sejda.model.pdf.page.PredefinedSetOfPages;
import org.sejda.model.validation.constraint.ValidSingleOutput;

@ValidSingleOutput
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/image/AbstractPdfToSingleImageParameters.class */
public abstract class AbstractPdfToSingleImageParameters extends AbstractPdfToImageParameters implements PageRangeSelection, PagesSelection, SingleOutputTaskParameters {

    @Valid
    @NotNull
    private SingleTaskOutput output;

    @Valid
    private final Set<PageRange> pageSelection;

    AbstractPdfToSingleImageParameters(ImageColorType outputImageColorType) {
        super(outputImageColorType);
        this.pageSelection = new NullSafeSet();
    }

    @Override // org.sejda.model.parameter.base.TaskParameters, org.sejda.model.parameter.base.SingleOutputTaskParameters
    public SingleTaskOutput getOutput() {
        return this.output;
    }

    @Override // org.sejda.model.parameter.base.SingleOutputTaskParameters
    public void setOutput(SingleTaskOutput output) {
        this.output = output;
    }

    public void addPageRange(PageRange range) {
        this.pageSelection.add(range);
    }

    public void addAllPageRanges(Collection<PageRange> ranges) {
        this.pageSelection.addAll(ranges);
    }

    @Override // org.sejda.model.pdf.page.PageRangeSelection
    public Set<PageRange> getPageSelection() {
        return Collections.unmodifiableSet(this.pageSelection);
    }

    @Override // org.sejda.model.pdf.page.PagesSelection
    public Set<Integer> getPages(int upperLimit) {
        if (this.pageSelection.isEmpty()) {
            return PredefinedSetOfPages.ALL_PAGES.getPages(upperLimit);
        }
        Set<Integer> retSet = new NullSafeSet<>();
        for (PageRange range : getPageSelection()) {
            retSet.addAll(range.getPages(upperLimit));
        }
        return retSet;
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToImageParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.output).append(this.pageSelection).toHashCode();
    }

    @Override // org.sejda.model.parameter.image.AbstractPdfToImageParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractPdfToSingleImageParameters)) {
            return false;
        }
        AbstractPdfToSingleImageParameters parameter = (AbstractPdfToSingleImageParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.output, parameter.getOutput()).append(this.pageSelection, parameter.getPageSelection()).isEquals();
    }
}
