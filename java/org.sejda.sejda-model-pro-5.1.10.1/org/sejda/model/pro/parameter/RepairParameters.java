package org.sejda.model.pro.parameter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;

public class RepairParameters extends MultiplePdfSourceMultipleOutputParameters {
   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof RepairParameters) ? false : (new EqualsBuilder()).appendSuper(super.equals(other)).isEquals();
      }
   }
}
