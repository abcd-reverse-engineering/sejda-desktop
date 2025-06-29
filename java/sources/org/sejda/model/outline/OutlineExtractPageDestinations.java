package org.sejda.model.outline;

import java.util.ArrayList;
import java.util.List;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/outline/OutlineExtractPageDestinations.class */
public class OutlineExtractPageDestinations {
    public final List<OutlineItemBoundaries> sections = new ArrayList();

    public void add(int startPage, String title, int endPage) {
        this.sections.add(new OutlineItemBoundaries(startPage, title, endPage));
    }

    /* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/outline/OutlineExtractPageDestinations$OutlineItemBoundaries.class */
    public static class OutlineItemBoundaries {
        public int startPage;
        public final String title;
        public final int endPage;

        public OutlineItemBoundaries(int startPage, String title, int endPage) {
            this.startPage = startPage;
            this.title = title;
            this.endPage = endPage;
        }
    }
}
