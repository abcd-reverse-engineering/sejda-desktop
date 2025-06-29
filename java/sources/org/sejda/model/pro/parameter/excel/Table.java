package org.sejda.model.pro.parameter.excel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.TopLeftRectangularBox;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/excel/Table.class */
public class Table {
    private List<TopLeftRectangularBox> rows = new ArrayList();
    private List<TopLeftRectangularBox> columns = new ArrayList();

    public Table() {
    }

    public Table(List<TopLeftRectangularBox> rows, List<TopLeftRectangularBox> columns) {
        this.rows.addAll(rows);
        this.columns.addAll(columns);
    }

    public void addRows(TopLeftRectangularBox... rows) {
        this.rows.addAll(Arrays.asList(rows));
    }

    public void addColumns(TopLeftRectangularBox... columns) {
        this.columns.addAll(Arrays.asList(columns));
    }

    public List<TopLeftRectangularBox> getRows() {
        return this.rows;
    }

    public List<TopLeftRectangularBox> getColumns() {
        return this.columns;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Table table = (Table) o;
        return new EqualsBuilder().append(this.rows, table.rows).append(this.columns, table.columns).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.rows).append(this.columns).toHashCode();
    }
}
