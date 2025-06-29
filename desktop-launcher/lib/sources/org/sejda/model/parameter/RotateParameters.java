package org.sejda.model.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PredefinedSetOfPages;
import org.sejda.model.rotation.Rotation;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/RotateParameters.class */
public class RotateParameters extends MultiplePdfSourceMultipleOutputParameters {

    @Valid
    @NotNull
    private Rotation rotation;

    @NotNull
    private PredefinedSetOfPages predefinedSetOfPages;

    @Valid
    private final Map<PageRange, Rotation> pageSelection;
    private Map<Integer, Map<PageRange, Rotation>> pageSelectionPerSource;

    public RotateParameters(Rotation rotation, PredefinedSetOfPages predefinedSetOfPages) {
        this.rotation = null;
        this.pageSelection = new HashMap();
        this.pageSelectionPerSource = new HashMap();
        this.rotation = rotation;
        this.predefinedSetOfPages = predefinedSetOfPages;
    }

    public RotateParameters(Rotation rotation) {
        this(rotation, PredefinedSetOfPages.NONE);
    }

    public RotateParameters() {
        this(Rotation.DEGREES_0);
    }

    @Deprecated
    public Rotation getRotation() {
        return this.rotation;
    }

    public Rotation getRotation(int page) {
        Rotation defaultRotation = Rotation.DEGREES_0;
        if (this.predefinedSetOfPages.includes(page)) {
            defaultRotation = this.rotation;
        }
        Optional<PageRange> optionalFindFirst = this.pageSelection.keySet().stream().filter(range -> {
            return range.contains(page);
        }).findFirst();
        Map<PageRange, Rotation> map = this.pageSelection;
        Objects.requireNonNull(map);
        return (Rotation) optionalFindFirst.map((v1) -> {
            return r1.get(v1);
        }).orElse(defaultRotation);
    }

    public Rotation getRotation(int sourceIndex, int page) {
        Map<PageRange, Rotation> pageSelection = this.pageSelectionPerSource.get(Integer.valueOf(sourceIndex));
        if (pageSelection != null) {
            for (PageRange range : pageSelection.keySet()) {
                if (range.contains(page)) {
                    return pageSelection.get(range);
                }
            }
        }
        return getRotation(page);
    }

    public void addPageRange(PageRange range) {
        this.pageSelection.put(range, this.rotation);
    }

    public void addPageRange(PageRange range, Rotation rotation) {
        this.pageSelection.put(range, rotation);
    }

    public void addAllPageRanges(Collection<PageRange> ranges) {
        ranges.forEach(this::addPageRange);
    }

    public void addPageRangePerSource(int sourceIndex, PageRange range, Rotation rotation) {
        if (!this.pageSelectionPerSource.containsKey(Integer.valueOf(sourceIndex))) {
            this.pageSelectionPerSource.put(Integer.valueOf(sourceIndex), new HashMap());
        }
        Map<PageRange, Rotation> pageSelection = this.pageSelectionPerSource.get(Integer.valueOf(sourceIndex));
        pageSelection.put(range, rotation);
    }

    public PredefinedSetOfPages getPredefinedSetOfPages() {
        return this.predefinedSetOfPages;
    }

    public Map<PageRange, Rotation> getPageSelection() {
        return this.pageSelection;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.rotation).append(this.predefinedSetOfPages).append(this.pageSelection).append(this.pageSelectionPerSource).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RotateParameters)) {
            return false;
        }
        RotateParameters parameter = (RotateParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.predefinedSetOfPages, parameter.predefinedSetOfPages).append(this.pageSelection, parameter.pageSelection).append(this.rotation, parameter.rotation).append(this.pageSelectionPerSource, parameter.pageSelectionPerSource).isEquals();
    }
}
