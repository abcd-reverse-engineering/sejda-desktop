package org.sejda.model.pro.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.input.Source;
import org.sejda.model.output.SingleTaskOutput;
import org.sejda.model.parameter.base.AbstractPdfOutputParameters;
import org.sejda.model.parameter.base.MultipleSourceTaskParameter;
import org.sejda.model.parameter.base.SingleOutputTaskParameters;
import org.sejda.model.pro.pdf.collection.InitialView;
import org.sejda.model.validation.constraint.SingleOutputAllowedExtensions;

@SingleOutputAllowedExtensions
/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/AttachmentsCollectionParameters.class */
public class AttachmentsCollectionParameters extends AbstractPdfOutputParameters implements SingleOutputTaskParameters, MultipleSourceTaskParameter {

    @NotNull
    private InitialView initialView = InitialView.TILES;

    @Valid
    @NotEmpty
    private final List<Source<?>> sourceList = new ArrayList();

    @Valid
    @NotNull
    private SingleTaskOutput output;

    public void addSources(Collection<? extends Source<?>> inputs) {
        this.sourceList.addAll(inputs);
    }

    @Override // org.sejda.model.parameter.base.MultipleSourceTaskParameter
    public void addSource(Source<?> input) {
        this.sourceList.add(input);
    }

    @Override // org.sejda.model.parameter.base.MultipleSourceTaskParameter
    public List<Source<?>> getSourceList() {
        return Collections.unmodifiableList(this.sourceList);
    }

    public InitialView getInitialView() {
        return this.initialView;
    }

    public void setInitialView(InitialView initialView) {
        this.initialView = initialView;
    }

    @Override // org.sejda.model.parameter.base.TaskParameters, org.sejda.model.parameter.base.SingleOutputTaskParameters
    public SingleTaskOutput getOutput() {
        return this.output;
    }

    @Override // org.sejda.model.parameter.base.SingleOutputTaskParameters
    public void setOutput(SingleTaskOutput output) {
        this.output = output;
    }

    @Override // org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.initialView).append(this.output).append(this.sourceList).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AttachmentsCollectionParameters)) {
            return false;
        }
        AttachmentsCollectionParameters parameter = (AttachmentsCollectionParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.initialView, parameter.getInitialView()).append(this.output, parameter.output).append(this.sourceList, parameter.getSourceList()).isEquals();
    }
}
