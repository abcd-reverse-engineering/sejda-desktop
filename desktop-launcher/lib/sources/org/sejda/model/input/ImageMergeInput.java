package org.sejda.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.model.PageOrientation;
import org.sejda.model.PageSize;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/ImageMergeInput.class */
public class ImageMergeInput implements MergeInput {

    @NotNull
    @Valid
    private Source<?> source;

    @NotNull
    private PageSize pageSize = PageSize.A4;
    private boolean shouldPageSizeMatchImageSize = false;
    private PageOrientation pageOrientation = PageOrientation.AUTO;

    public ImageMergeInput(Source<?> source) {
        this.source = source;
    }

    public Source<?> getSource() {
        return this.source;
    }

    public PageSize getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isShouldPageSizeMatchImageSize() {
        return this.shouldPageSizeMatchImageSize;
    }

    public void setShouldPageSizeMatchImageSize(boolean shouldPageSizeMatchImageSize) {
        this.shouldPageSizeMatchImageSize = shouldPageSizeMatchImageSize;
    }

    public PageOrientation getPageOrientation() {
        return this.pageOrientation;
    }

    public void setPageOrientation(PageOrientation pageOrientation) {
        this.pageOrientation = pageOrientation;
    }

    public String toString() {
        return new ToStringBuilder(this).append(this.source).append(this.pageSize).append(this.shouldPageSizeMatchImageSize).append(this.pageOrientation).toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.source).append(this.pageSize).append(this.shouldPageSizeMatchImageSize).append(this.pageOrientation).toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ImageMergeInput)) {
            return false;
        }
        ImageMergeInput input = (ImageMergeInput) other;
        return new EqualsBuilder().append(this.source, input.getSource()).append(this.pageOrientation, input.pageOrientation).append(this.pageSize, input.pageSize).append(this.shouldPageSizeMatchImageSize, input.shouldPageSizeMatchImageSize).isEquals();
    }
}
