package org.sejda.model.parameter;

import jakarta.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.commons.collection.NullSafeSet;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/SplitByPagesParameters.class */
public class SplitByPagesParameters extends AbstractSplitByPageParameters {

    @NotEmpty
    private final Set<Integer> pages = new NullSafeSet();

    public void addPages(Collection<Integer> pagesToAdd) {
        this.pages.addAll(pagesToAdd);
    }

    public void addPage(Integer page) {
        this.pages.add(page);
    }

    @Override // org.sejda.model.pdf.page.PagesSelection
    public Set<Integer> getPages(int upperLimit) {
        return (Set) this.pages.stream().filter(p -> {
            return p.intValue() <= upperLimit && p.intValue() > 0;
        }).collect(Collectors.toUnmodifiableSet());
    }

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append(this.pages).toString();
    }

    @Override // org.sejda.model.parameter.AbstractSplitByPageParameters, org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.pages).toHashCode();
    }

    @Override // org.sejda.model.parameter.AbstractSplitByPageParameters, org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SplitByPagesParameters)) {
            return false;
        }
        SplitByPagesParameters parameter = (SplitByPagesParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.pages, parameter.pages).isEquals();
    }
}
