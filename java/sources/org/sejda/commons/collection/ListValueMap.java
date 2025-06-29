package org.sejda.commons.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/collection/ListValueMap.class */
public final class ListValueMap<K, V> {
    private final Map<K, List<V>> map = new HashMap();

    public void clear() {
        this.map.clear();
    }

    public List<V> put(K key, V value) {
        List<V> list = this.map.get(key);
        if (list == null) {
            list = new ArrayList();
        }
        list.add(value);
        return this.map.put(key, list);
    }

    public boolean remove(K key, V value) {
        List<V> list = this.map.get(key);
        if (list != null && !list.isEmpty()) {
            return list.remove(value);
        }
        return false;
    }

    public List<V> get(K key) {
        List<V> list = this.map.get(key);
        if (list != null) {
            return list;
        }
        return Collections.emptyList();
    }

    public int size() {
        int retVal = 0;
        for (Map.Entry<K, List<V>> entry : this.map.entrySet()) {
            retVal += entry.getValue().size();
        }
        return retVal;
    }
}
