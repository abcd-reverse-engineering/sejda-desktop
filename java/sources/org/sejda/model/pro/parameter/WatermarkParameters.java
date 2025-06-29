package org.sejda.model.pro.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PageRangeSelection;
import org.sejda.model.pdf.page.PagesSelection;
import org.sejda.model.pro.watermark.Watermark;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/WatermarkParameters.class */
public class WatermarkParameters extends MultiplePdfSourceMultipleOutputParameters implements PageRangeSelection, PagesSelection {

    @Valid
    private final Set<PageRange> pageSelection = new NullSafeSet();

    @Valid
    @NotNull
    private List<Watermark> watermarks = new ArrayList();

    public WatermarkParameters(Watermark watermark) {
        addWatermark(watermark);
    }

    public WatermarkParameters(Collection<Watermark> watermarks) {
        addWatermarks(watermarks);
    }

    public WatermarkParameters() {
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
        return (Set) getPageSelection().stream().flatMap(r -> {
            return r.getPages(totalNumberOfPage).stream();
        }).collect(NullSafeSet::new, (v0, v1) -> {
            v0.add(v1);
        }, (v0, v1) -> {
            v0.addAll(v1);
        });
    }

    public List<Watermark> getWatermarks() {
        return Collections.unmodifiableList(this.watermarks);
    }

    public void addWatermark(Watermark watermark) {
        this.watermarks.add(watermark);
    }

    public void addWatermarks(Collection<Watermark> watermarks) {
        this.watermarks.addAll(watermarks);
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WatermarkParameters that = (WatermarkParameters) o;
        return new EqualsBuilder().appendSuper(super.equals(o)).append(this.pageSelection, that.pageSelection).append(this.watermarks, that.watermarks).isEquals();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.pageSelection).append(this.watermarks).toHashCode();
    }
}
