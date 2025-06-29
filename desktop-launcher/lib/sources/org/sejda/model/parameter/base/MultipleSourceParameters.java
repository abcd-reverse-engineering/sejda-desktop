package org.sejda.model.parameter.base;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.input.Source;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/MultipleSourceParameters.class */
public abstract class MultipleSourceParameters extends AbstractPdfOutputParameters implements MultipleSourceTaskParameter {

    @Valid
    @NotEmpty
    private final List<Source<?>> sourceList = new ArrayList();

    public void addSources(Collection<Source<?>> inputs) {
        this.sourceList.addAll(inputs);
    }

    @Override // org.sejda.model.parameter.base.MultipleSourceTaskParameter
    public void addSource(Source<?> input) {
        this.sourceList.add(input);
    }

    public void removeAllSources() {
        this.sourceList.clear();
    }

    @Override // org.sejda.model.parameter.base.MultipleSourceTaskParameter
    public List<Source<?>> getSourceList() {
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
        if (!(other instanceof MultipleSourceParameters)) {
            return false;
        }
        MultipleSourceParameters parameter = (MultipleSourceParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.sourceList, parameter.getSourceList()).isEquals();
    }
}
