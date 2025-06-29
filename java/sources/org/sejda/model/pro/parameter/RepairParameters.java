package org.sejda.model.pro.parameter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/RepairParameters.class */
public class RepairParameters extends MultiplePdfSourceMultipleOutputParameters {
    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RepairParameters)) {
            return false;
        }
        return new EqualsBuilder().appendSuper(super.equals(other)).isEquals();
    }
}
