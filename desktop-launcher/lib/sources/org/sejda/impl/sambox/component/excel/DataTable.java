package org.sejda.impl.sambox.component.excel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/excel/DataTable.class */
public class DataTable {
    private static final Logger LOG = LoggerFactory.getLogger(DataTable.class);
    private final List<List<String>> data = new ArrayList();
    private final TreeSet<Integer> pageNumbers = new TreeSet<>();

    public DataTable(int pageNumber) {
        this.pageNumbers.add(Integer.valueOf(pageNumber));
    }

    public DataTable(Collection<Integer> pageNumbers) {
        this.pageNumbers.addAll(pageNumbers);
    }

    public DataTable addRow(String... dataRow) {
        List<String> row = new ArrayList<>();
        row.addAll(Arrays.asList(dataRow));
        addRow(row);
        return this;
    }

    public void addRow(List<String> dataRow) {
        this.data.add(dataRow);
    }

    public void addRows(List<List<String>> dataRows) {
        dataRows.forEach(this::addRow);
    }

    public List<String> headerRow() {
        return this.data.get(0);
    }

    public List<String> headerRowIgnoreBlanks() {
        return (List) this.data.get(0).stream().filter(s -> {
            return !s.trim().isEmpty();
        }).collect(Collectors.toList());
    }

    public boolean hasSameHeaderAs(DataTable other) {
        String thisHeader = String.join("", headerRowIgnoreBlanks()).trim();
        String otherHeader = String.join("", other.headerRowIgnoreBlanks()).trim();
        LOG.debug("Comparing header columns: '{}' and '{}'", thisHeader, otherHeader);
        return thisHeader.equalsIgnoreCase(otherHeader);
    }

    public boolean hasSameHeaderBlanksIgnoredAs(DataTable other) {
        return headerRowIgnoreBlanks().equals(other.headerRowIgnoreBlanks());
    }

    public boolean hasSameColumnCountAs(DataTable other) {
        LOG.debug("Comparing header columns size: {} and {}", Integer.valueOf(headerRow().size()), Integer.valueOf(other.headerRow().size()));
        return other.headerRow().size() == headerRow().size();
    }

    public List<List<String>> getData() {
        return this.data;
    }

    public TreeSet<Integer> getPageNumbers() {
        return this.pageNumbers;
    }

    public DataTable mergeWith(DataTable other) {
        TreeSet<Integer> resultPageNumbers = new TreeSet<>();
        resultPageNumbers.addAll(this.pageNumbers);
        resultPageNumbers.addAll(other.pageNumbers);
        DataTable result = new DataTable(resultPageNumbers);
        result.addRows(this.data);
        List<List<String>> otherData = other.data;
        if (hasSameHeaderAs(other)) {
            otherData.remove(0);
        }
        result.addRows(otherData);
        return result;
    }

    private boolean hasConsecutivePages() {
        Integer prev = null;
        Iterator<Integer> it = this.pageNumbers.iterator();
        while (it.hasNext()) {
            Integer current = it.next();
            if (prev != null && prev.intValue() != current.intValue() - 1) {
                return false;
            }
            prev = current;
        }
        return true;
    }

    public String getPagesAsString() {
        StringBuilder sb = new StringBuilder();
        if (this.pageNumbers.size() > 1) {
            sb.append("Pages ");
            if (this.pageNumbers.size() > 2 && hasConsecutivePages()) {
                sb.append(this.pageNumbers.first()).append("-").append(this.pageNumbers.last());
                return sb.toString();
            }
            int i = 0;
            Iterator<Integer> it = this.pageNumbers.iterator();
            while (it.hasNext()) {
                Integer pageNumber = it.next();
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(pageNumber);
                i++;
            }
        } else {
            sb.append("Page ").append(this.pageNumbers.iterator().next());
        }
        return sb.toString();
    }

    public boolean hasData() {
        return this.data.size() > 0;
    }

    public int getColumnsCount() {
        int result = 0;
        for (List<String> row : this.data) {
            result = Math.max(row.size(), result);
        }
        return result;
    }

    public int getRowsCount() {
        return this.data.size();
    }

    public List<String> getColumn(int c) {
        List<String> result = new ArrayList<>(getRowsCount());
        for (List<String> row : this.data) {
            result.add(getOrEmpty(row, c));
        }
        return result;
    }

    public List<String> getRow(int r) {
        return this.data.get(r);
    }

    public DataTable mergeColumns(int c1, int c2) {
        DataTable result = new DataTable(getPageNumbers());
        for (List<String> row : this.data) {
            List<String> newRow = new ArrayList<>(row.size() - 1);
            for (int c = 0; c < row.size(); c++) {
                if (c != c2) {
                    if (c == c1) {
                        String newValue = (getOrEmpty(row, c1) + " " + getOrEmpty(row, c2)).trim();
                        newRow.add(newValue);
                    } else {
                        newRow.add(getOrEmpty(row, c));
                    }
                }
            }
            result.addRow(newRow);
        }
        return result;
    }

    public void addBlankColumn(int index) {
        for (List<String> row : this.data) {
            row.add(index, "");
        }
    }

    private static String getOrEmpty(List<String> list, int index) {
        if (list.size() <= index) {
            return "";
        }
        return list.get(index);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int totalWidth = 0;
        int colCount = getColumnsCount();
        List<Integer> colWidths = new ArrayList<>();
        for (int i = 0; i < colCount; i++) {
            List<String> col = getColumn(i);
            int colWidth = 0;
            for (int j = 0; j < col.size(); j++) {
                colWidth = Math.max(colWidth, col.get(j).length());
            }
            colWidths.add(Integer.valueOf(colWidth));
            totalWidth += colWidth;
        }
        String line = "+" + StringUtils.repeat("-", (totalWidth + colCount) - 1) + "+";
        for (int i2 = 0; i2 < getRowsCount(); i2++) {
            List<String> row = getRow(i2);
            sb.append("\n").append(line).append("\n");
            for (int j2 = 0; j2 < colWidths.size(); j2++) {
                String cellPadded = StringUtils.rightPad("", colWidths.get(j2).intValue());
                if (j2 < row.size()) {
                    cellPadded = org.sejda.core.support.util.StringUtils.isolateRTLIfRequired(StringUtils.rightPad(row.get(j2), colWidths.get(j2).intValue()));
                }
                sb.append("|").append(cellPadded);
            }
            sb.append("|");
        }
        sb.append("\n").append(line).append("\n");
        return sb.toString();
    }
}
