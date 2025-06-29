package org.sejda.model.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.input.MergeInput;
import org.sejda.model.outline.CatalogPageLabelsPolicy;
import org.sejda.model.outline.OutlinePolicy;
import org.sejda.model.pdf.form.AcroFormPolicy;
import org.sejda.model.rotation.Rotation;
import org.sejda.model.scale.PageNormalizationPolicy;
import org.sejda.model.toc.ToCPolicy;
import org.sejda.model.validation.constraint.SingleOutputAllowedExtensions;

@SingleOutputAllowedExtensions
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/MergeParameters.class */
public class MergeParameters extends BaseMergeParameters<MergeInput> {
    private boolean blankPageIfOdd = false;

    @NotNull
    private OutlinePolicy outlinePolicy = OutlinePolicy.RETAIN;

    @NotNull
    private AcroFormPolicy acroFormPolicy = AcroFormPolicy.MERGE_RENAMING_EXISTING_FIELDS;

    @NotNull
    private CatalogPageLabelsPolicy catalogPageLabelsPolicy = CatalogPageLabelsPolicy.DISCARD;

    @NotNull
    private ToCPolicy tocPolicy = ToCPolicy.NONE;
    private boolean filenameFooter = false;
    private PageNormalizationPolicy pageNormalizationPolicy = PageNormalizationPolicy.NONE;
    private boolean firstInputCoverTitle = false;

    @Valid
    private List<Rotation> rotations = new ArrayList();

    public boolean isBlankPageIfOdd() {
        return this.blankPageIfOdd;
    }

    public void setBlankPageIfOdd(boolean blankPageIfOdd) {
        this.blankPageIfOdd = blankPageIfOdd;
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

    public ToCPolicy getTableOfContentsPolicy() {
        return (ToCPolicy) Optional.ofNullable(this.tocPolicy).orElse(ToCPolicy.NONE);
    }

    public void setTableOfContentsPolicy(ToCPolicy tocPolicy) {
        this.tocPolicy = tocPolicy;
    }

    public boolean isFilenameFooter() {
        return this.filenameFooter;
    }

    public void setFilenameFooter(boolean filenameFooter) {
        this.filenameFooter = filenameFooter;
    }

    @Deprecated
    public boolean isNormalizePageSizes() {
        return this.pageNormalizationPolicy == PageNormalizationPolicy.SAME_WIDTH_ORIENTATION_BASED;
    }

    @Deprecated
    public void setNormalizePageSizes(boolean normalizePageSizes) {
        PageNormalizationPolicy pageNormalizationPolicy;
        if (normalizePageSizes) {
            pageNormalizationPolicy = PageNormalizationPolicy.SAME_WIDTH_ORIENTATION_BASED;
        } else {
            pageNormalizationPolicy = PageNormalizationPolicy.NONE;
        }
        this.pageNormalizationPolicy = pageNormalizationPolicy;
    }

    public PageNormalizationPolicy getPageNormalizationPolicy() {
        return this.pageNormalizationPolicy;
    }

    public void setPageNormalizationPolicy(PageNormalizationPolicy pageNormalizationPolicy) {
        this.pageNormalizationPolicy = pageNormalizationPolicy;
    }

    public CatalogPageLabelsPolicy getCatalogPageLabelsPolicy() {
        return this.catalogPageLabelsPolicy;
    }

    public void setCatalogPageLabelsPolicy(CatalogPageLabelsPolicy catalogPageLabelsPolicy) {
        this.catalogPageLabelsPolicy = catalogPageLabelsPolicy;
    }

    public boolean isFirstInputCoverTitle() {
        return this.firstInputCoverTitle;
    }

    public void setFirstInputCoverTitle(boolean firstInputCoverTitle) {
        this.firstInputCoverTitle = firstInputCoverTitle;
    }

    public List<Rotation> getRotations() {
        return this.rotations;
    }

    public void setRotations(List<Rotation> rotations) {
        this.rotations = rotations;
    }

    public Rotation getRotation(int index) {
        if (index >= this.rotations.size()) {
            return Rotation.DEGREES_0;
        }
        return this.rotations.get(index);
    }

    @Override // org.sejda.model.parameter.BaseMergeParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.acroFormPolicy).append(this.blankPageIfOdd).append(this.outlinePolicy).append(this.tocPolicy).append(this.filenameFooter).append(this.pageNormalizationPolicy).append(this.catalogPageLabelsPolicy).append(this.firstInputCoverTitle).append(this.rotations).toHashCode();
    }

    @Override // org.sejda.model.parameter.BaseMergeParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MergeParameters)) {
            return false;
        }
        MergeParameters params = (MergeParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.acroFormPolicy, params.getAcroFormPolicy()).append(this.blankPageIfOdd, params.isBlankPageIfOdd()).append(this.outlinePolicy, params.getOutlinePolicy()).append(this.tocPolicy, params.getTableOfContentsPolicy()).append(this.filenameFooter, params.isFilenameFooter()).append(this.pageNormalizationPolicy, params.getPageNormalizationPolicy()).append(this.catalogPageLabelsPolicy, params.catalogPageLabelsPolicy).append(this.firstInputCoverTitle, params.firstInputCoverTitle).append(this.rotations, params.rotations).isEquals();
    }
}
