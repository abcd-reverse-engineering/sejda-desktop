package org.sejda.model.parameter;

import jakarta.validation.constraints.NotNull;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.model.pdf.page.PredefinedSetOfPages;
import org.sejda.model.validation.constraint.NotAllowed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/SimpleSplitParameters.class */
public class SimpleSplitParameters extends AbstractSplitByPageParameters {

    @NotAllowed(disallow = {PredefinedSetOfPages.NONE})
    @NotNull
    private PredefinedSetOfPages setOfPages;

    public SimpleSplitParameters(PredefinedSetOfPages setOfPages) {
        this.setOfPages = setOfPages;
    }

    @Override // org.sejda.model.pdf.page.PagesSelection
    public Set<Integer> getPages(int upperLimit) {
        return this.setOfPages.getPages(upperLimit);
    }

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append(this.setOfPages).toString();
    }

    @Override // org.sejda.model.parameter.AbstractSplitByPageParameters, org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.setOfPages).toHashCode();
    }

    @Override // org.sejda.model.parameter.AbstractSplitByPageParameters, org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SimpleSplitParameters)) {
            return false;
        }
        SimpleSplitParameters parameter = (SimpleSplitParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.setOfPages, parameter.setOfPages).isEquals();
    }

    public PredefinedSetOfPages getSetOfPages() {
        return this.setOfPages;
    }
}
