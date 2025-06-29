package org.sejda.model.parameter;

import java.util.Collection;
import java.util.Set;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.pdf.page.PageRange;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/ExcludedPagesSelection.class */
public interface ExcludedPagesSelection {
    Set<PageRange> getExcludedPagesSelection();

    default void addExcludedPage(Integer page) {
        addExcludedPageRange(new PageRange(page.intValue(), page.intValue()));
    }

    default void addExcludedPageRange(PageRange range) {
        getExcludedPagesSelection().add(range);
    }

    default void addAllExcludedPageRanges(Collection<PageRange> ranges) {
        getExcludedPagesSelection().addAll(ranges);
    }

    default Set<Integer> getExcludedPages(int upperLimit) {
        Set<Integer> pages = new NullSafeSet<>();
        for (PageRange range : getExcludedPagesSelection()) {
            pages.addAll(range.getPages(upperLimit));
        }
        return pages;
    }
}
