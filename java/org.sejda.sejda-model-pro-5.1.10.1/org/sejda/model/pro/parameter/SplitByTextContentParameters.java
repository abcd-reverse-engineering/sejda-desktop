package org.sejda.model.pro.parameter;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.model.optimization.OptimizationPolicy;
import org.sejda.model.parameter.base.DiscardableOutlineTaskParameters;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.parameter.base.OptimizableOutputTaskParameters;

public class SplitByTextContentParameters extends MultiplePdfSourceMultipleOutputParameters implements OptimizableOutputTaskParameters, DiscardableOutlineTaskParameters {
   private final @NotNull TopLeftRectangularBox textArea;
   private String startsWith = "";
   private String endsWith = "";
   private @NotNull OptimizationPolicy optimizationPolicy;
   private boolean discardOutline;

   public SplitByTextContentParameters(TopLeftRectangularBox textArea) {
      this.optimizationPolicy = OptimizationPolicy.NO;
      this.discardOutline = false;
      this.textArea = textArea;
   }

   public TopLeftRectangularBox getTextArea() {
      return this.textArea;
   }

   public String getStartsWith() {
      return this.startsWith;
   }

   public String getEndsWith() {
      return this.endsWith;
   }

   public void setStartsWith(String startsWith) {
      this.startsWith = startsWith;
   }

   public void setEndsWith(String endsWith) {
      this.endsWith = endsWith;
   }

   public OptimizationPolicy getOptimizationPolicy() {
      return this.optimizationPolicy;
   }

   public void setOptimizationPolicy(OptimizationPolicy optimizationPolicy) {
      this.optimizationPolicy = optimizationPolicy;
   }

   public boolean discardOutline() {
      return this.discardOutline;
   }

   public void discardOutline(boolean discardOutline) {
      this.discardOutline = discardOutline;
   }

   public String toString() {
      return (new ToStringBuilder(this)).appendSuper(super.toString()).append(this.textArea).append(this.startsWith).append(this.endsWith).toString();
   }

   public int hashCode() {
      return (new HashCodeBuilder()).appendSuper(super.hashCode()).append(this.optimizationPolicy).append(this.discardOutline).append(this.textArea).toHashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof SplitByTextContentParameters) {
         SplitByTextContentParameters parameter = (SplitByTextContentParameters)other;
         return (new EqualsBuilder()).appendSuper(super.equals(other)).append(this.optimizationPolicy, parameter.optimizationPolicy).append(this.discardOutline, parameter.discardOutline).append(this.textArea, parameter.textArea).append(this.startsWith, parameter.startsWith).append(this.endsWith, parameter.endsWith).isEquals();
      } else {
         return false;
      }
   }
}
