package org.sejda.model.pro.parameter;

import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;

public class ExtractTextParameters extends MultiplePdfSourceMultipleOutputParameters {
   private @NotEmpty String textEncoding = "UTF-8";

   public String getTextEncoding() {
      return this.textEncoding;
   }

   public void setTextEncoding(String textEncoding) {
      this.textEncoding = textEncoding;
   }

   public int hashCode() {
      return (new HashCodeBuilder()).appendSuper(super.hashCode()).append(this.textEncoding).toHashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof ExtractTextParameters) {
         ExtractTextParameters parameter = (ExtractTextParameters)other;
         return (new EqualsBuilder()).appendSuper(super.equals(other)).append(this.textEncoding, parameter.textEncoding).isEquals();
      } else {
         return false;
      }
   }
}
