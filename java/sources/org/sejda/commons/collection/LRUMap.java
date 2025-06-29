package org.sejda.commons.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/collection/LRUMap.class */
public class LRUMap<K, V> extends LinkedHashMap<K, V> {
    private final int maxCapacity;

    public LRUMap(int maxCapacity) {
        super(maxCapacity, 0.75f, true);
        this.maxCapacity = maxCapacity;
    }

    @Override // java.util.LinkedHashMap
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > this.maxCapacity;
    }
}
