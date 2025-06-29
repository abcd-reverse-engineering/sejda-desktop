package org.sejda.impl.sambox.component.excel;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/excel/DataTableUtils.class */
public class DataTableUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DataTableUtils.class);

    private DataTableUtils() {
    }

    public static List<DataTable> mergeTablesSpanningMultiplePages(List<DataTable> dataTables) {
        List<DataTable> results = new ArrayList<>();
        DataTable current = null;
        for (DataTable dt : dataTables) {
            if (current != null) {
                if (current.hasSameHeaderBlanksIgnoredAs(dt)) {
                    addBlankColumnsToMatchHeaders(current, dt);
                }
                if (current.hasSameColumnCountAs(dt)) {
                    current = current.mergeWith(dt);
                } else {
                    results.add(current);
                    current = dt;
                }
            } else {
                current = dt;
            }
        }
        if (current != null) {
            results.add(current);
        }
        return results;
    }

    public static List<DataTable> mergeComplementaryColumns(List<DataTable> dataTables) {
        List<DataTable> results = new ArrayList<>();
        for (DataTable dt : dataTables) {
            results.add(mergeComplementaryColumns(dt));
        }
        return results;
    }

    public static void addBlankColumnsToMatchHeaders(DataTable a, DataTable b) {
        if (!a.hasSameHeaderBlanksIgnoredAs(b)) {
            throw new RuntimeException("Only works when tables have same headers (blanks ignored)");
        }
        List<String> aHeaderRow = a.headerRow();
        List<String> bHeaderRow = b.headerRow();
        int aa = 0;
        int bb = 0;
        while (aa < aHeaderRow.size() && bb < bHeaderRow.size()) {
            String aCol = aHeaderRow.get(aa).trim();
            String bCol = bHeaderRow.get(bb).trim();
            if (aCol.equals(bCol)) {
                aa++;
                bb++;
            } else if (aCol.isEmpty()) {
                b.addBlankColumn(bb);
            } else if (bCol.isEmpty()) {
                a.addBlankColumn(aa);
            } else {
                throw new RuntimeException("Should not happen");
            }
        }
    }

    static DataTable mergeComplementaryColumns(DataTable dataTable) {
        DataTable result = dataTable;
        boolean again = true;
        while (again) {
            again = false;
            int c = 0;
            while (true) {
                if (c < result.getColumnsCount() - 1) {
                    List<String> column1 = result.getColumn(c);
                    List<String> column2 = result.getColumn(c + 1);
                    if (!areComplementary(column1, column2)) {
                        c++;
                    } else {
                        LOG.debug("Merging complementary columns {} and {}", Integer.valueOf(c), Integer.valueOf(c + 1));
                        result = result.mergeColumns(c, c + 1);
                        again = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static boolean areComplementary(List<String> column1, List<String> column2) {
        if (column1.size() != column2.size()) {
            return false;
        }
        for (int i = 0; i < column1.size(); i++) {
            String v1 = column1.get(i);
            String v2 = column2.get(i);
            if (!v1.trim().isEmpty() && !v2.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
