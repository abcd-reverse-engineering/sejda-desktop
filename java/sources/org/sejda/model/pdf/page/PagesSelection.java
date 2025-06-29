package org.sejda.model.pdf.page;

import java.util.Set;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/page/PagesSelection.class */
public interface PagesSelection {
    public static final PagesSelection LAST_PAGE = (v0) -> {
        return Set.of(v0);
    };

    Set<Integer> getPages(int totalNumberOfPage);
}
