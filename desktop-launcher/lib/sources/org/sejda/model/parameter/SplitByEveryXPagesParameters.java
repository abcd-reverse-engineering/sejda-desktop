package org.sejda.model.parameter;

import jakarta.validation.constraints.Min;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.commons.collection.NullSafeSet;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/SplitByEveryXPagesParameters.class */
public class SplitByEveryXPagesParameters extends AbstractSplitByPageParameters {

    @Min(1)
    private int step;

    public SplitByEveryXPagesParameters(int step) {
        this.step = 1;
        this.step = step;
    }

    @Override // org.sejda.model.pdf.page.PagesSelection
    public Set<Integer> getPages(int upperLimit) {
        Set<Integer> pages = new NullSafeSet<>();
        int i = this.step;
        while (true) {
            int i2 = i;
            if (i2 <= upperLimit) {
                pages.add(Integer.valueOf(i2));
                i = i2 + this.step;
            } else {
                return pages;
            }
        }
    }

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append(this.step).toString();
    }

    @Override // org.sejda.model.parameter.AbstractSplitByPageParameters, org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.step).toHashCode();
    }

    @Override // org.sejda.model.parameter.AbstractSplitByPageParameters, org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SplitByEveryXPagesParameters)) {
            return false;
        }
        SplitByEveryXPagesParameters parameter = (SplitByEveryXPagesParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.step, parameter.step).isEquals();
    }
}
