package org.sejda.model.outline;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/outline/OutlinePageDestinations.class */
public class OutlinePageDestinations {
    private Map<Integer, String> destinations = new HashMap();

    public void addPage(Integer page, String title) {
        if (page == null) {
            throw new IllegalArgumentException("Unable to add a null page to the destinations.");
        }
        this.destinations.put(page, title);
    }

    public Set<Integer> getPages() {
        return Collections.unmodifiableSet(this.destinations.keySet());
    }

    public String getTitle(Integer page) {
        return this.destinations.get(page);
    }
}
