package org.sejda.model.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.input.PdfSource;
import org.sejda.model.output.MultipleTaskOutput;
import org.sejda.model.parameter.base.AbstractParameters;
import org.sejda.model.parameter.base.MultiplePdfSourceTaskParameters;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/UnpackParameters.class */
public class UnpackParameters extends AbstractParameters implements MultiplePdfSourceTaskParameters {

    @Valid
    @NotNull
    private final MultipleTaskOutput output;

    @Valid
    @NotEmpty
    private final List<PdfSource<?>> sourceList = new ArrayList();

    public UnpackParameters(MultipleTaskOutput output) {
        this.output = output;
    }

    @Override // org.sejda.model.parameter.base.TaskParameters, org.sejda.model.parameter.base.SingleOutputTaskParameters
    public MultipleTaskOutput getOutput() {
        return this.output;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceTaskParameters
    public void addSource(PdfSource<?> input) {
        this.sourceList.add(input);
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceTaskParameters
    public List<PdfSource<?>> getSourceList() {
        return Collections.unmodifiableList(this.sourceList);
    }

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.output).append(this.sourceList).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UnpackParameters)) {
            return false;
        }
        UnpackParameters parameter = (UnpackParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.output, parameter.getOutput()).append(this.sourceList, parameter.getSourceList()).isEquals();
    }
}
