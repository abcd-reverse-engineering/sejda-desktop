package org.sejda.model.pro.parameter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.input.FileIndexAndPage;
import org.sejda.model.input.MergeInput;
import org.sejda.model.outline.OutlinePolicy;
import org.sejda.model.parameter.BaseMergeParameters;
import org.sejda.model.pdf.form.AcroFormPolicy;
import org.sejda.model.rotation.Rotation;
import org.sejda.model.validation.constraint.SingleOutputAllowedExtensions;

@SingleOutputAllowedExtensions
/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/CombineReorderParameters.class */
public class CombineReorderParameters extends BaseMergeParameters<MergeInput> {

    @NotEmpty
    private List<FileIndexAndPage> pages = new ArrayList();

    @NotNull
    private AcroFormPolicy acroFormPolicy = AcroFormPolicy.MERGE_RENAMING_EXISTING_FIELDS;

    @NotNull
    private OutlinePolicy outlinePolicy = OutlinePolicy.RETAIN;

    public void addPage(int fileIndex, int page) {
        this.pages.add(new FileIndexAndPage(fileIndex, page));
    }

    public void addPage(int fileIndex, int page, Rotation rotation) {
        this.pages.add(new FileIndexAndPage(fileIndex, page, rotation));
    }

    public List<FileIndexAndPage> getPages() {
        return this.pages;
    }

    public AcroFormPolicy getAcroFormPolicy() {
        return this.acroFormPolicy;
    }

    public void setAcroFormPolicy(AcroFormPolicy acroFormPolicy) {
        this.acroFormPolicy = acroFormPolicy;
    }

    public OutlinePolicy getOutlinePolicy() {
        return this.outlinePolicy;
    }

    public void setOutlinePolicy(OutlinePolicy outlinePolicy) {
        this.outlinePolicy = outlinePolicy;
    }

    @Override // org.sejda.model.parameter.BaseMergeParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.pages).append(this.acroFormPolicy).append(this.outlinePolicy).toHashCode();
    }

    @Override // org.sejda.model.parameter.BaseMergeParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CombineReorderParameters other = (CombineReorderParameters) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.pages, other.pages).append(this.acroFormPolicy, other.acroFormPolicy).append(this.outlinePolicy, other.outlinePolicy).isEquals();
    }
}
