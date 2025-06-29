package org.sejda.model.pro.parameter.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/excel/PdfToExcelParameters.class */
public class PdfToExcelParameters extends MultiplePdfSourceMultipleOutputParameters {
    private Map<Integer, List<Table>> tables = new HashMap();
    private boolean mergeTablesSpanningMultiplePages = false;
    private boolean csvFormat = false;

    public void addTable(int pageNumber, Table table) {
        if (!this.tables.containsKey(Integer.valueOf(pageNumber))) {
            this.tables.put(Integer.valueOf(pageNumber), new ArrayList());
        }
        this.tables.get(Integer.valueOf(pageNumber)).add(table);
    }

    public void addTables(int pageNumber, List<Table> tables) {
        for (Table table : tables) {
            addTable(pageNumber, table);
        }
    }

    public List<Table> getTables(int pageNumber) {
        List<Table> result = this.tables.get(Integer.valueOf(pageNumber));
        if (result == null) {
            result = new ArrayList();
        }
        return result;
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

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PdfToExcelParameters that = (PdfToExcelParameters) o;
        return new EqualsBuilder().appendSuper(super.equals(that)).append(this.tables, that.tables).append(this.mergeTablesSpanningMultiplePages, that.mergeTablesSpanningMultiplePages).append(this.csvFormat, that.csvFormat).isEquals();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.tables).append(this.mergeTablesSpanningMultiplePages).append(this.csvFormat).toHashCode();
    }
}
