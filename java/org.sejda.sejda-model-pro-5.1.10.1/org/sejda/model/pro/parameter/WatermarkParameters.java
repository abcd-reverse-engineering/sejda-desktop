package org.sejda.model.pro.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PageRangeSelection;
import org.sejda.model.pdf.page.PagesSelection;
import org.sejda.model.pro.watermark.Watermark;

public class WatermarkParameters extends MultiplePdfSourceMultipleOutputParameters implements PageRangeSelection, PagesSelection {
   private final @Valid Set<PageRange> pageSelection = new NullSafeSet();
   private @Valid @NotNull List<Watermark> watermarks = new ArrayList();

   public WatermarkParameters(Watermark watermark) {
      this.addWatermark(watermark);
   }

   public WatermarkParameters(Collection<Watermark> watermarks) {
      this.addWatermarks(watermarks);
   }

   public WatermarkParameters() {
   }

   public Set<PageRange> getPageSelection() {
      return Collections.unmodifiableSet(this.pageSelection);
   }

   public void addPageRange(PageRange range) {
      this.pageSelection.add(range);
   }

   public void addAllPageRanges(Collection<PageRange> ranges) {
      this.pageSelection.addAll(ranges);
   }

   public Set<Integer> getPages(int totalNumberOfPage) {
      return (Set)(this.pageSelection.isEmpty() ? (new PageRange(1)).getPages(totalNumberOfPage) : (Set)this.getPageSelection().stream().flatMap((r) -> {
         return r.getPages(totalNumberOfPage).stream();
      }).collect(NullSafeSet::new, NullSafeSet::add, NullSafeSet::addAll));
   }

   public List<Watermark> getWatermarks() {
      return Collections.unmodifiableList(this.watermarks);
   }

   public void addWatermark(Watermark watermark) {
      this.watermarks.add(watermark);
   }

   public void addWatermarks(Collection<Watermark> watermarks) {
      this.watermarks.addAll(watermarks);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         WatermarkParameters that = (WatermarkParameters)o;
         return (new EqualsBuilder()).appendSuper(super.equals(o)).append(this.pageSelection, that.pageSelection).append(this.watermarks, that.watermarks).isEquals();
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (new HashCodeBuilder(17, 37)).appendSuper(super.hashCode()).append(this.pageSelection).append(this.watermarks).toHashCode();
   }
}
