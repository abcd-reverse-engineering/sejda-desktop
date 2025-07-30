package org.sejda.model.pro.parameter;

import jakarta.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.RectangularBox;
import org.sejda.model.parameter.ExcludedPagesSelection;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.form.AcroFormPolicy;
import org.sejda.model.pdf.page.PageRange;

public class CropParameters extends MultiplePdfSourceMultipleOutputParameters implements ExcludedPagesSelection {
   private final Set<RectangularBox> cropAreas = new NullSafeSet();
   private final Map<Integer, Set<RectangularBox>> pageCropAreas = new HashMap();
   private final Set<RectangularBox> oddPageCropAreas = new NullSafeSet();
   private final Set<RectangularBox> evenPageCropAreas = new NullSafeSet();
   private AutoCropMode autoCropMode;
   private @NotNull AcroFormPolicy acroFormPolicy;
   public final Set<PageRange> excludedPagesSelection;

   public CropParameters() {
      this.autoCropMode = AutoCropMode.NONE;
      this.acroFormPolicy = AcroFormPolicy.MERGE;
      this.excludedPagesSelection = new NullSafeSet();
   }

   public Set<PageRange> getExcludedPagesSelection() {
      return this.excludedPagesSelection;
   }

   public void clearCropAreas() {
      this.cropAreas.clear();
      this.pageCropAreas.clear();
      this.oddPageCropAreas.clear();
      this.evenPageCropAreas.clear();
   }

   public void addCropArea(int pageNumber, RectangularBox area) {
      if (!this.pageCropAreas.containsKey(pageNumber)) {
         this.pageCropAreas.put(pageNumber, new NullSafeSet());
      }

      ((Set)this.pageCropAreas.get(pageNumber)).add(area);
   }

   public Set<RectangularBox> getCropAreas(int pageNumber) {
      if (this.pageCropAreas.containsKey(pageNumber)) {
         return (Set)this.pageCropAreas.get(pageNumber);
      } else {
         if (pageNumber % 2 == 0) {
            if (!this.evenPageCropAreas.isEmpty()) {
               return this.evenPageCropAreas;
            }
         } else if (!this.oddPageCropAreas.isEmpty()) {
            return this.oddPageCropAreas;
         }

         return this.cropAreas;
      }
   }

   public void addOddPageCropArea(RectangularBox area) {
      this.oddPageCropAreas.add(area);
   }

   public void addEvenPageCropArea(RectangularBox area) {
      this.evenPageCropAreas.add(area);
   }

   public void addCropArea(RectangularBox area) {
      this.cropAreas.add(area);
   }

   public AcroFormPolicy getAcroFormPolicy() {
      return this.acroFormPolicy;
   }

   public void setAcroFormPolicy(AcroFormPolicy acroFormPolicy) {
      this.acroFormPolicy = acroFormPolicy;
   }

   public AutoCropMode getAutoCropMode() {
      return this.autoCropMode;
   }

   public void setAutoCropMode(AutoCropMode autoCropMode) {
      this.autoCropMode = autoCropMode;
   }

   public int hashCode() {
      return (new HashCodeBuilder()).appendSuper(super.hashCode()).append(this.cropAreas).append(this.acroFormPolicy).append(this.excludedPagesSelection).append(this.pageCropAreas).append(this.oddPageCropAreas).append(this.evenPageCropAreas).append(this.autoCropMode).toHashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof CropParameters) {
         CropParameters parameter = (CropParameters)other;
         return (new EqualsBuilder()).appendSuper(super.equals(other)).append(this.cropAreas, parameter.cropAreas).append(this.acroFormPolicy, parameter.acroFormPolicy).append(this.excludedPagesSelection, parameter.excludedPagesSelection).append(this.pageCropAreas, parameter.pageCropAreas).append(this.oddPageCropAreas, parameter.oddPageCropAreas).append(this.evenPageCropAreas, parameter.evenPageCropAreas).append(this.autoCropMode, parameter.autoCropMode).isEquals();
      } else {
         return false;
      }
   }
}
