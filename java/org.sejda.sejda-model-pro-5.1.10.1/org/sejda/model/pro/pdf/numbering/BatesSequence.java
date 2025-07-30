package org.sejda.model.pro.pdf.numbering;

import java.text.DecimalFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BatesSequence {
   private long current;
   private final int step;
   private final DecimalFormat decimalFormat;

   public BatesSequence() {
      this(1L, 1, 6);
   }

   public BatesSequence(long startFrom, int step, int digits) {
      this.current = startFrom;
      this.step = step;
      this.decimalFormat = new DecimalFormat(StringUtils.repeat('0', digits));
   }

   public String next() {
      String result = this.decimalFormat.format(this.current);
      this.current += (long)this.step;
      return result;
   }

   public String toString() {
      return (new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)).append("current", this.current).append("step", this.step).append("format", this.decimalFormat).toString();
   }

   public int hashCode() {
      return (new HashCodeBuilder()).append(this.current).append(this.step).append(this.decimalFormat).toHashCode();
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         BatesSequence other = (BatesSequence)obj;
         return (new EqualsBuilder()).append(this.current, other.current).append(this.step, other.step).append(this.decimalFormat, other.decimalFormat).isEquals();
      }
   }
}
