package org.sejda.model.pro.parameter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;

public class ConvertToGrayscaleParameters extends MultiplePdfSourceMultipleOutputParameters {
   private boolean convertImages = true;
   private boolean convertTextToBlack = false;
   private boolean compressImages = false;

   public boolean isConvertImages() {
      return this.convertImages;
   }

   public void setConvertImages(boolean convertImages) {
      this.convertImages = convertImages;
   }

   public boolean isConvertTextToBlack() {
      return this.convertTextToBlack;
   }

   public void setConvertTextToBlack(boolean convertTextToBlack) {
      this.convertTextToBlack = convertTextToBlack;
   }

   public boolean isCompressImages() {
      return this.compressImages;
   }

   public void setCompressImages(boolean compressImages) {
      this.compressImages = compressImages;
   }

   public int hashCode() {
      return (new HashCodeBuilder()).appendSuper(super.hashCode()).append(this.convertImages).append(this.convertTextToBlack).append(this.compressImages).toHashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof ConvertToGrayscaleParameters) {
         ConvertToGrayscaleParameters parameter = (ConvertToGrayscaleParameters)other;
         return (new EqualsBuilder()).appendSuper(super.equals(other)).append(this.isConvertImages(), parameter.isConvertImages()).append(this.isConvertTextToBlack(), parameter.isConvertTextToBlack()).append(this.isCompressImages(), parameter.isCompressImages()).isEquals();
      } else {
         return false;
      }
   }
}
