package org.sejda.model.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.input.MergeInput;
import org.sejda.model.input.PdfMergeInput;
import org.sejda.model.output.SingleTaskOutput;
import org.sejda.model.parameter.base.AbstractPdfOutputParameters;
import org.sejda.model.parameter.base.SingleOutputTaskParameters;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/BaseMergeParameters.class */
public abstract class BaseMergeParameters<T extends MergeInput> extends AbstractPdfOutputParameters implements SingleOutputTaskParameters {

    @Valid
    @NotEmpty
    private List<T> inputList = new ArrayList();

    @Valid
    @NotNull
    private SingleTaskOutput output;

    @Override // org.sejda.model.parameter.base.TaskParameters, org.sejda.model.parameter.base.SingleOutputTaskParameters
    public SingleTaskOutput getOutput() {
        return this.output;
    }

    @Override // org.sejda.model.parameter.base.SingleOutputTaskParameters
    public void setOutput(SingleTaskOutput output) {
        this.output = output;
    }

    public List<T> getInputList() {
        return Collections.unmodifiableList(this.inputList);
    }

    public void setInputList(List<T> inputList) {
        this.inputList = inputList;
    }

    public List<PdfMergeInput> getPdfInputList() {
        return (List) this.inputList.stream().map(input -> {
            return (PdfMergeInput) input;
        }).collect(Collectors.toUnmodifiableList());
    }

    public void addInput(T input) {
        this.inputList.add(input);
    }

    @Override // org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.inputList).append(this.output).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BaseMergeParameters)) {
            return false;
        }
        BaseMergeParameters<?> params = (BaseMergeParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.inputList, params.inputList).append(this.output, params.getOutput()).isEquals();
    }
}
