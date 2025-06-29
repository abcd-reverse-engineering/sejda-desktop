package org.sejda.model.split;

import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.outline.OutlinePageDestinations;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/split/PageDestinationsSplitPages.class */
public class PageDestinationsSplitPages implements NextOutputStrategy {
    private SplitPages delegate = new SplitPages(new Integer[0]);

    public PageDestinationsSplitPages(OutlinePageDestinations destinations) {
        for (Integer page : destinations.getPages()) {
            this.delegate.add(Integer.valueOf(page.intValue() - 1));
        }
    }

    @Override // org.sejda.model.split.NextOutputStrategy
    public void ensureIsValid() throws TaskExecutionException {
        this.delegate.ensureIsValid();
    }

    @Override // org.sejda.model.split.NextOutputStrategy
    public boolean isOpening(Integer page) {
        return this.delegate.isOpening(page);
    }

    @Override // org.sejda.model.split.NextOutputStrategy
    public boolean isClosing(Integer page) {
        return this.delegate.isClosing(page);
    }
}
