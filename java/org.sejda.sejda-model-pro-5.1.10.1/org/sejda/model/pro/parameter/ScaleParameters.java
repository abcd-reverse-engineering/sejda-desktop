package org.sejda.model.pro.parameter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.scale.ScaleType;

public class ScaleParameters extends MultiplePdfSourceMultipleOutputParameters {
   public final @Positive double scale;
   private @NotNull ScaleType scaleType;

   public ScaleParameters(double scale) {
      this.scaleType = ScaleType.CONTENT;
      this.scale = scale;
   }

   public ScaleType getScaleType() {
      return this.scaleType;
   }

   public void setScaleType(ScaleType scaleType) {
      this.scaleType = scaleType;
   }

   public int hashCode() {
      return (new HashCodeBuilder()).appendSuper(super.hashCode()).append(this.scale).append(this.scaleType).toHashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof ScaleParameters) {
         ScaleParameters parameter = (ScaleParameters)other;
         return (new EqualsBuilder()).appendSuper(super.equals(other)).append(this.scale, parameter.scale).append(this.scaleType, parameter.scaleType).isEquals();
      } else {
         return false;
      }
   }
}
