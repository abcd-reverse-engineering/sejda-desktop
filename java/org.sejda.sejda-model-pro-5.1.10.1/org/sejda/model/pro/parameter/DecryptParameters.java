package org.sejda.model.pro.parameter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;

public class DecryptParameters extends MultiplePdfSourceMultipleOutputParameters {
   public int hashCode() {
      return (new HashCodeBuilder()).appendSuper(super.hashCode()).toHashCode();
   }

   public boolean equals(Object other) {
      return !(other instanceof DecryptParameters) ? false : (new EqualsBuilder()).appendSuper(super.equals(other)).isEquals();
   }
}
