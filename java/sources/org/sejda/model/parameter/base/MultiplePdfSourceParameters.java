package org.sejda.model.parameter.base;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.input.PdfSource;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/MultiplePdfSourceParameters.class */
public abstract class MultiplePdfSourceParameters extends AbstractPdfOutputParameters implements MultiplePdfSourceTaskParameters {

    @Valid
    @NotEmpty
    private final List<PdfSource<?>> sourceList = new ArrayList();

    public void addSources(Collection<PdfSource<?>> inputs) {
        this.sourceList.addAll(inputs);
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceTaskParameters
    public void addSource(PdfSource<?> input) {
        this.sourceList.add(input);
    }

    public void removeAllSources() {
        this.sourceList.clear();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceTaskParameters
    public List<PdfSource<?>> getSourceList() {
        return Collections.unmodifiableList(this.sourceList);
    }

    @Override // org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.sourceList).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MultiplePdfSourceParameters)) {
            return false;
        }
        MultiplePdfSourceParameters parameter = (MultiplePdfSourceParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.sourceList, parameter.getSourceList()).isEquals();
    }
}
