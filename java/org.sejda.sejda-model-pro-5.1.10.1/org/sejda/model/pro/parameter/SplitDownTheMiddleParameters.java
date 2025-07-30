package org.sejda.model.pro.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.parameter.ExcludedPagesSelection;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pro.split.SplitDownTheMiddleMode;
import org.sejda.model.repaginate.Repagination;

public class SplitDownTheMiddleParameters extends MultiplePdfSourceMultipleOutputParameters implements ExcludedPagesSelection {
   private @NotNull Repagination repagination;
   public final @Valid Set<PageRange> excludedPagesSelection;
   private @NotNull SplitDownTheMiddleMode mode;
   private double ratio;
   private boolean rightToLeft;
   private boolean autoDetectExcludedPages;

   public SplitDownTheMiddleParameters() {
      this.repagination = Repagination.NONE;
      this.excludedPagesSelection = new NullSafeSet();
      this.mode = SplitDownTheMiddleMode.AUTO;
      this.ratio = 1.0;
      this.rightToLeft = false;
      this.autoDetectExcludedPages = false;
   }

   public Repagination getRepagination() {
      return this.repagination;
   }

   public void setRepagination(Repagination repagination) {
      this.repagination = repagination;
   }

   public Set<PageRange> getExcludedPagesSelection() {
      return this.excludedPagesSelection;
   }

   public SplitDownTheMiddleMode getMode() {
      return this.mode;
   }

   public void setMode(SplitDownTheMiddleMode mode) {
      this.mode = mode;
   }

   public double getRatio() {
      return this.ratio;
   }

   public void setRatio(double ratio) {
      this.ratio = ratio;
   }

   public boolean isRightToLeft() {
      return this.rightToLeft;
   }

   public void setRightToLeft(boolean rightToLeft) {
      this.rightToLeft = rightToLeft;
   }

   public boolean isAutoDetectExcludedPages() {
      return this.autoDetectExcludedPages;
   }

   public void setAutoDetectExcludedPages(boolean autoDetectExcludedPages) {
      this.autoDetectExcludedPages = autoDetectExcludedPages;
   }

   public int hashCode() {
      return (new HashCodeBuilder()).append(this.getRepagination()).append(this.getExcludedPagesSelection()).append(this.getRatio()).append(this.getMode()).append(this.isRightToLeft()).append(this.isAutoDetectExcludedPages()).appendSuper(super.hashCode()).toHashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof SplitDownTheMiddleParameters) {
         SplitDownTheMiddleParameters parameter = (SplitDownTheMiddleParameters)other;
         return (new EqualsBuilder()).append(this.getRepagination(), parameter.getRepagination()).append(this.getExcludedPagesSelection(), parameter.getExcludedPagesSelection()).append(this.getRatio(), parameter.getRatio()).append(this.getMode(), parameter.getMode()).append(this.isRightToLeft(), parameter.isRightToLeft()).append(this.isAutoDetectExcludedPages(), parameter.isAutoDetectExcludedPages()).appendSuper(super.equals(other)).isEquals();
      } else {
         return false;
      }
   }
}
