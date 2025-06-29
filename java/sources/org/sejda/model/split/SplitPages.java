package org.sejda.model.split;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.sejda.model.exception.TaskExecutionException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/split/SplitPages.class */
public class SplitPages implements NextOutputStrategy {
    private Set<Integer> closingPages;
    private Set<Integer> openingPages;

    public SplitPages(Collection<Integer> pages) {
        this((Integer[]) pages.toArray(new Integer[pages.size()]));
    }

    public SplitPages(Integer... pages) {
        this.closingPages = new HashSet();
        this.openingPages = new HashSet();
        this.openingPages.add(1);
        for (Integer page : pages) {
            add(page);
        }
    }

    void add(Integer page) {
        this.closingPages.add(page);
        this.openingPages.add(Integer.valueOf(page.intValue() + 1));
    }

    @Override // org.sejda.model.split.NextOutputStrategy
    public void ensureIsValid() throws TaskExecutionException {
        if (this.closingPages.isEmpty()) {
            throw new TaskExecutionException("Unable to split, no page number given.");
        }
    }

    @Override // org.sejda.model.split.NextOutputStrategy
    public boolean isOpening(Integer page) {
        return this.openingPages.contains(page);
    }

    @Override // org.sejda.model.split.NextOutputStrategy
    public boolean isClosing(Integer page) {
        return this.closingPages.contains(page);
    }
}
