package org.sejda.impl.sambox.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.sejda.model.pdf.label.PdfLabelNumberingStyle;
import org.sejda.model.pdf.label.PdfPageLabel;
import org.sejda.sambox.pdmodel.common.PDPageLabelRange;
import org.sejda.sambox.pdmodel.common.PDPageLabels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/util/PageLabelUtils.class */
public final class PageLabelUtils {
    private static final Logger LOG = LoggerFactory.getLogger(PageLabelUtils.class);
    private static final Map<PdfLabelNumberingStyle, String> PAGE_NUMBERS_STYLES;

    static {
        Map<PdfLabelNumberingStyle, String> pageNumberStyles = new HashMap<>();
        pageNumberStyles.put(PdfLabelNumberingStyle.ARABIC, "D");
        pageNumberStyles.put(PdfLabelNumberingStyle.LOWERCASE_LETTERS, PDPageLabelRange.STYLE_LETTERS_LOWER);
        pageNumberStyles.put(PdfLabelNumberingStyle.LOWERCASE_ROMANS, PDPageLabelRange.STYLE_ROMAN_LOWER);
        pageNumberStyles.put(PdfLabelNumberingStyle.UPPERCASE_LETTERS, PDPageLabelRange.STYLE_LETTERS_UPPER);
        pageNumberStyles.put(PdfLabelNumberingStyle.UPPERCASE_ROMANS, "R");
        PAGE_NUMBERS_STYLES = Collections.unmodifiableMap(pageNumberStyles);
    }

    private PageLabelUtils() {
    }

    public static PDPageLabels getLabels(Map<Integer, PdfPageLabel> labels, int totalPages) {
        PDPageLabels retLabels = new PDPageLabels();
        for (Map.Entry<Integer, PdfPageLabel> entry : labels.entrySet()) {
            int page = entry.getKey().intValue();
            if (page > 0 && page <= totalPages) {
                PdfPageLabel label = entry.getValue();
                PDPageLabelRange range = new PDPageLabelRange();
                range.setStyle(PAGE_NUMBERS_STYLES.get(label.getNumberingStyle()));
                range.setStart(label.getLogicalPageNumber());
                range.setPrefix(label.getLabelPrefix());
                retLabels.setLabelItem(page - 1, range);
            } else {
                LOG.warn("Page number {} out of rage, {} will be ignored.", Integer.valueOf(page), entry.getValue());
            }
        }
        return retLabels;
    }

    public static PDPageLabels removePages(PDPageLabels pageLabels, List<Integer> pagesToRemove, int totalPages) {
        int prevKey;
        Map<Integer, PDPageLabelRange> labels = new TreeMap<>(pageLabels.getLabels());
        List<Integer> pagesToRemoveSortedLastFirst = new ArrayList<>(pagesToRemove);
        pagesToRemoveSortedLastFirst.sort(Collections.reverseOrder());
        Iterator<Integer> it = pagesToRemoveSortedLastFirst.iterator();
        while (it.hasNext()) {
            int pageToRemove = it.next().intValue();
            Map<Integer, PDPageLabelRange> updatedLabels = new TreeMap<>();
            int pageIndex = pageToRemove - 1;
            Iterator<Integer> it2 = labels.keySet().iterator();
            while (it2.hasNext()) {
                int key = it2.next().intValue();
                if (key <= pageIndex) {
                    updatedLabels.put(Integer.valueOf(key), labels.get(Integer.valueOf(key)));
                } else if (key > pageIndex && (prevKey = key - 1) >= 0) {
                    updatedLabels.put(Integer.valueOf(prevKey), labels.get(Integer.valueOf(key)));
                }
            }
            labels = updatedLabels;
        }
        int newTotalPages = totalPages - pagesToRemove.size();
        PDPageLabels result = new PDPageLabels();
        Iterator<Integer> it3 = labels.keySet().iterator();
        while (it3.hasNext()) {
            int index = it3.next().intValue();
            if (index < newTotalPages) {
                result.setLabelItem(index, labels.get(Integer.valueOf(index)));
            }
        }
        return result;
    }
}
