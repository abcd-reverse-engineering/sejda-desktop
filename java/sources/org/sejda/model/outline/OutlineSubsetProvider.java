package org.sejda.model.outline;

import org.sejda.model.exception.TaskException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/outline/OutlineSubsetProvider.class */
public interface OutlineSubsetProvider<T> {
    void startPage(int startPage);

    T getOutlineUntillPage(int endPage) throws TaskException;

    T getOutlineUntillPageWithOffset(int endPage, int offset) throws TaskException;

    T getOutlineWithOffset(int offset);
}
