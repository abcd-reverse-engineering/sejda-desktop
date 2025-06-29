package org.sejda.model.outline;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/outline/OutlineLevelsHandler.class */
public interface OutlineLevelsHandler {
    OutlinePageDestinations getPageDestinationsForLevel(int level);

    OutlineExtractPageDestinations getExtractPageDestinations(int level, boolean includePageAfter);
}
