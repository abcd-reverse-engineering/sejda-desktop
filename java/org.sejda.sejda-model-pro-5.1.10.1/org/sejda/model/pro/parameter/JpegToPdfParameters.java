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

public class JpegToPdfParameters extends MultipleSourceMultipleOutputParameters {
   private @NotNull PageSize pageSize;
   private boolean pageSizeMatchImageSize;
   private @NotNull PageOrientation pageOrientation;
   private float marginInches;
   private boolean mergeOutputs;
   private @Valid List<Rotation> rotations;

   public JpegToPdfParameters() {
      this.pageSize = PageSize.A4;
      this.pageSizeMatchImageSize = false;
      this.pageOrientation = PageOrientation.AUTO;
      this.mergeOutputs = true;
      this.rotations = new ArrayList();
   }

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

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         JpegToPdfParameters other = (JpegToPdfParameters)o;
         return (new EqualsBuilder()).appendSuper(super.equals(o)).append(this.pageSizeMatchImageSize, other.pageSizeMatchImageSize).append(this.pageSize, other.pageSize).append(this.pageOrientation, other.pageOrientation).append(this.marginInches, other.marginInches).append(this.rotations, other.rotations).append(this.mergeOutputs, other.mergeOutputs).isEquals();
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (new HashCodeBuilder(17, 37)).appendSuper(super.hashCode()).append(this.pageSize).append(this.pageSizeMatchImageSize).append(this.pageOrientation).append(this.marginInches).append(this.rotations).append(this.mergeOutputs).toHashCode();
   }
}
