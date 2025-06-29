package org.sejda.model.parameter.image;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.image.ImageColorType;
import org.sejda.model.image.ImageType;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PageRangeSelection;
import org.sejda.model.pdf.page.PagesSelection;
import org.sejda.model.pdf.page.PredefinedSetOfPages;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/image/AbstractPdfToMultipleImageParameters.class */
public abstract class AbstractPdfToMultipleImageParameters extends MultiplePdfSourceMultipleOutputParameters implements PageRangeSelection, PagesSelection, PdfToImageParameters {
    public static final int DEFAULT_DPI = 72;

    @NotNull
    private ImageColorType outputImageColorType;

    @Min(1)
    private int resolutionInDpi = 72;

    @Valid
    private final Set<PageRange> pageSelection = new NullSafeSet();

    @NotNull
    public abstract ImageType getOutputImageType();

    AbstractPdfToMultipleImageParameters(ImageColorType outputImageColorType) {
        this.outputImageColorType = outputImageColorType;
    }

    @Override // org.sejda.model.parameter.image.PdfToImageParameters
    public ImageColorType getOutputImageColorType() {
        return this.outputImageColorType;
    }

    @Override // org.sejda.model.parameter.image.PdfToImageParameters
    public void setOutputImageColorType(ImageColorType outputImageColorType) {
        this.outputImageColorType = outputImageColorType;
    }

    @Override // org.sejda.model.parameter.image.PdfToImageParameters
    public int getResolutionInDpi() {
        return this.resolutionInDpi;
    }

    @Override // org.sejda.model.parameter.image.PdfToImageParameters
    public void setResolutionInDpi(int resolutionInDpi) {
        this.resolutionInDpi = resolutionInDpi;
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

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractPdfToMultipleImageParameters that = (AbstractPdfToMultipleImageParameters) o;
        return new EqualsBuilder().appendSuper(super.equals(o)).append(this.resolutionInDpi, that.resolutionInDpi).append(this.outputImageColorType, that.outputImageColorType).append(this.pageSelection, that.pageSelection).isEquals();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.resolutionInDpi).append(this.outputImageColorType).append(this.pageSelection).toHashCode();
    }
}
