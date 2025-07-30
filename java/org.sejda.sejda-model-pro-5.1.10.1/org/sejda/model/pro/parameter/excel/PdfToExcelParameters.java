package org.sejda.model.pro.parameter.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;

public class PdfToExcelParameters extends MultiplePdfSourceMultipleOutputParameters {
   private Map<Integer, List<Table>> tables = new HashMap();
   private boolean mergeTablesSpanningMultiplePages = false;
   private boolean csvFormat = false;

   public void addTable(int pageNumber, Table table) {
      if (!this.tables.containsKey(pageNumber)) {
         this.tables.put(pageNumber, new ArrayList());
      }

      ((List)this.tables.get(pageNumber)).add(table);
   }

   public void addTables(int pageNumber, List<Table> tables) {
      Iterator var3 = tables.iterator();

      while(var3.hasNext()) {
         Table table = (Table)var3.next();
         this.addTable(pageNumber, table);
      }

   }

   public List<Table> getTables(int pageNumber) {
      List<Table> result = (List)this.tables.get(pageNumber);
      if (result == null) {
         result = new ArrayList();
      }

      return (List)result;
   }

   public boolean isMergeTablesSpanningMultiplePages() {
      return this.mergeTablesSpanningMultiplePages;
   }

   public void setMergeTablesSpanningMultiplePages(boolean mergeTablesSpanningMultiplePages) {
      this.mergeTablesSpanningMultiplePages = mergeTablesSpanningMultiplePages;
   }

   public boolean isCsvFormat() {
      return this.csvFormat;
   }

   public void setCsvFormat(boolean csvFormat) {
      this.csvFormat = csvFormat;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         PdfToExcelParameters that = (PdfToExcelParameters)o;
         return (new EqualsBuilder()).appendSuper(super.equals(that)).append(this.tables, that.tables).append(this.mergeTablesSpanningMultiplePages, that.mergeTablesSpanningMultiplePages).append(this.csvFormat, that.csvFormat).isEquals();
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (new HashCodeBuilder(17, 37)).appendSuper(super.hashCode()).append(this.tables).append(this.mergeTablesSpanningMultiplePages).append(this.csvFormat).toHashCode();
   }
}
