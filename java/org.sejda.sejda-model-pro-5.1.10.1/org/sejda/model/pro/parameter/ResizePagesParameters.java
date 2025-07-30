package org.sejda.model.pro.parameter;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.PageSize;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PageRangeSelection;
import org.sejda.model.pdf.page.PagesSelection;
import org.sejda.model.scale.Margins;

public class ResizePagesParameters extends MultiplePdfSourceMultipleOutputParameters implements PageRangeSelection, PagesSelection {
   public @Valid Margins margins;
   public PageSize pageSize;
   private final @Valid Set<PageRange> pageSelection = new NullSafeSet();

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
      if (this.pageSelection.isEmpty()) {
         return (new PageRange(1)).getPages(totalNumberOfPage);
      } else {
         Set<Integer> retSet = new NullSafeSet();
         Iterator var3 = this.getPageSelection().iterator();

         while(var3.hasNext()) {
            PageRange range = (PageRange)var3.next();
            retSet.addAll(range.getPages(totalNumberOfPage));
         }

         return retSet;
      }
   }

   public Margins getMargins() {
      return this.margins;
   }

   public void setMargins(Margins margins) {
      this.margins = margins;
   }

   public PageSize getPageSize() {
      return this.pageSize;
   }

   public void setPageSize(PageSize pageSize) {
      this.pageSize = pageSize;
   }

   public int hashCode() {
      return (new HashCodeBuilder()).appendSuper(super.hashCode()).append(this.margins).append(this.pageSelection).append(this.pageSize).toHashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof ResizePagesParameters) {
         ResizePagesParameters parameter = (ResizePagesParameters)other;
         return (new EqualsBuilder()).appendSuper(super.equals(other)).append(this.margins, parameter.margins).append(this.pageSize, parameter.pageSize).append(this.pageSelection, parameter.pageSelection).isEquals();
      } else {
         return false;
      }
   }
}
