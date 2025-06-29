package org.sejda.model.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.SinglePdfSourceSingleOutputParameters;
import org.sejda.model.pdf.label.PdfPageLabel;
import org.sejda.model.validation.constraint.SingleOutputAllowedExtensions;

@SingleOutputAllowedExtensions
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/SetPagesLabelParameters.class */
public class SetPagesLabelParameters extends SinglePdfSourceSingleOutputParameters {

    @Valid
    @NotEmpty
    private final Map<Integer, PdfPageLabel> labels = new HashMap();

    public PdfPageLabel putLabel(Integer page, PdfPageLabel label) {
        return this.labels.put(page, label);
    }

    public Map<Integer, PdfPageLabel> getLabels() {
        return Collections.unmodifiableMap(this.labels);
    }

    @Override // org.sejda.model.parameter.base.SinglePdfSourceSingleOutputParameters, org.sejda.model.parameter.base.SinglePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.labels).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.SinglePdfSourceSingleOutputParameters, org.sejda.model.parameter.base.SinglePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SetPagesLabelParameters)) {
            return false;
        }
        SetPagesLabelParameters parameter = (SetPagesLabelParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(getLabels(), parameter.getLabels()).isEquals();
    }
}
