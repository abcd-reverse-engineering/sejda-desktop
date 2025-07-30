package org.sejda.model.pro.parameter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pro.nup.PageOrder;

public class NupParameters extends MultiplePdfSourceMultipleOutputParameters {
   private final @Min(2L) int n;
   private final @NotNull PageOrder pageOrder;
   private boolean preservePageSize;
   private boolean rightToLeft;

   public NupParameters(int n) {
      this(n, PageOrder.HORIZONTAL);
   }

   public NupParameters(int n, PageOrder pageOrder) {
      this.preservePageSize = false;
      this.rightToLeft = false;
      this.n = n;
      this.pageOrder = pageOrder;
   }

   public int getN() {
      return this.n;
   }

   public PageOrder getPageOrder() {
      return this.pageOrder;
   }

   public void setPreservePageSize(boolean preservePageSize) {
      this.preservePageSize = preservePageSize;
   }

   public boolean isPreservePageSize() {
      return this.preservePageSize;
   }

   public boolean isRightToLeft() {
      return this.rightToLeft;
   }

   public void setRightToLeft(boolean rightToLeft) {
      this.rightToLeft = rightToLeft;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         NupParameters that = (NupParameters)o;
         return (new EqualsBuilder()).appendSuper(super.equals(o)).append(this.n, that.n).append(this.pageOrder, that.pageOrder).append(this.preservePageSize, that.preservePageSize).append(this.rightToLeft, that.rightToLeft).isEquals();
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (new HashCodeBuilder(17, 37)).appendSuper(super.hashCode()).append(this.n).append(this.pageOrder).append(this.preservePageSize).append(this.rightToLeft).toHashCode();
   }
}
