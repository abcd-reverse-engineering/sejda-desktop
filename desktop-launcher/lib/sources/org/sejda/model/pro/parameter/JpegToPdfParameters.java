package org.sejda.model.pro.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.PageOrientation;
import org.sejda.model.PageSize;
import org.sejda.model.parameter.base.MultipleSourceMultipleOutputParameters;
import org.sejda.model.rotation.Rotation;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/JpegToPdfParameters.class */
public class JpegToPdfParameters extends MultipleSourceMultipleOutputParameters {
    private float marginInches;

    @NotNull
    private PageSize pageSize = PageSize.A4;
    private boolean pageSizeMatchImageSize = false;

    @NotNull
    private PageOrientation pageOrientation = PageOrientation.AUTO;
    private boolean mergeOutputs = true;

    @Valid
    private List<Rotation> rotations = new ArrayList();

    public PageSize getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isPageSizeMatchImageSize() {
        return this.pageSizeMatchImageSize;
    }

    public void setPageSizeMatchImageSize(boolean pageSizeMatchImageSize) {
        this.pageSizeMatchImageSize = pageSizeMatchImageSize;
    }

    public PageOrientation getPageOrientation() {
        return this.pageOrientation;
    }

    public void setPageOrientation(PageOrientation pageOrientation) {
        this.pageOrientation = pageOrientation;
    }

    public float getMarginInches() {
        return this.marginInches;
    }

    public void setMarginInches(float marginInches) {
        this.marginInches = marginInches;
    }

    public List<Rotation> getRotations() {
        return this.rotations;
    }

    public void setRotations(List<Rotation> rotations) {
        this.rotations = rotations;
    }

    public boolean isMergeOutputs() {
        return this.mergeOutputs;
    }

    public void setMergeOutputs(boolean mergeOutputs) {
        this.mergeOutputs = mergeOutputs;
    }

    @Override // org.sejda.model.parameter.base.MultipleSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultipleSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JpegToPdfParameters other = (JpegToPdfParameters) o;
        return new EqualsBuilder().appendSuper(super.equals(o)).append(this.pageSizeMatchImageSize, other.pageSizeMatchImageSize).append(this.pageSize, other.pageSize).append(this.pageOrientation, other.pageOrientation).append(this.marginInches, other.marginInches).append(this.rotations, other.rotations).append(this.mergeOutputs, other.mergeOutputs).isEquals();
    }

    @Override // org.sejda.model.parameter.base.MultipleSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultipleSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.pageSize).append(this.pageSizeMatchImageSize).append(this.pageOrientation).append(this.marginInches).append(this.rotations).append(this.mergeOutputs).toHashCode();
    }
}
