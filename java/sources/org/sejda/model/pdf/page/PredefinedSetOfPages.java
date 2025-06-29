package org.sejda.model.pdf.page;

import java.util.SortedSet;
import java.util.TreeSet;
import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/page/PredefinedSetOfPages.class */
public enum PredefinedSetOfPages implements PagesSelection, FriendlyNamed {
    ALL_PAGES("all") { // from class: org.sejda.model.pdf.page.PredefinedSetOfPages.1
        @Override // org.sejda.model.pdf.page.PredefinedSetOfPages, org.sejda.model.pdf.page.PagesSelection
        public SortedSet<Integer> getPages(int totalNumberOfPage) {
            SortedSet<Integer> retSet = new TreeSet<>();
            for (int i = 1; i <= totalNumberOfPage; i++) {
                retSet.add(Integer.valueOf(i));
            }
            return retSet;
        }

        @Override // org.sejda.model.pdf.page.PredefinedSetOfPages
        public boolean includes(int page) {
            return true;
        }
    },
    EVEN_PAGES("even") { // from class: org.sejda.model.pdf.page.PredefinedSetOfPages.2
        @Override // org.sejda.model.pdf.page.PredefinedSetOfPages, org.sejda.model.pdf.page.PagesSelection
        public SortedSet<Integer> getPages(int totalNumberOfPage) {
            SortedSet<Integer> retSet = new TreeSet<>();
            int i = 2;
            while (true) {
                int i2 = i;
                if (i2 <= totalNumberOfPage) {
                    retSet.add(Integer.valueOf(i2));
                    i = i2 + 2;
                } else {
                    return retSet;
                }
            }
        }

        @Override // org.sejda.model.pdf.page.PredefinedSetOfPages
        public boolean includes(int page) {
            return page % 2 == 0;
        }
    },
    ODD_PAGES("odd") { // from class: org.sejda.model.pdf.page.PredefinedSetOfPages.3
        @Override // org.sejda.model.pdf.page.PredefinedSetOfPages, org.sejda.model.pdf.page.PagesSelection
        public SortedSet<Integer> getPages(int totalNumberOfPage) {
            SortedSet<Integer> retSet = new TreeSet<>();
            int i = 1;
            while (true) {
                int i2 = i;
                if (i2 <= totalNumberOfPage) {
                    retSet.add(Integer.valueOf(i2));
                    i = i2 + 2;
                } else {
                    return retSet;
                }
            }
        }

        @Override // org.sejda.model.pdf.page.PredefinedSetOfPages
        public boolean includes(int page) {
            return page % 2 == 1;
        }
    },
    NONE("none") { // from class: org.sejda.model.pdf.page.PredefinedSetOfPages.4
        @Override // org.sejda.model.pdf.page.PredefinedSetOfPages, org.sejda.model.pdf.page.PagesSelection
        public SortedSet<Integer> getPages(int totalNumberOfPage) {
            return new TreeSet();
        }

        @Override // org.sejda.model.pdf.page.PredefinedSetOfPages
        public boolean includes(int page) {
            return false;
        }
    };

    private final String displayName;

    @Override // org.sejda.model.pdf.page.PagesSelection
    public abstract SortedSet<Integer> getPages(int totalNumberOfPage);

    public abstract boolean includes(int page);

    PredefinedSetOfPages(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
