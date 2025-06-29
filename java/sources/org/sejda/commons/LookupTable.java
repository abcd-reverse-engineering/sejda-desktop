package org.sejda.commons;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/LookupTable.class */
public class LookupTable<I> {
    private final Map<I, I> oldToNew = new LinkedHashMap();

    public void addLookupEntry(I keyItem, I valueItem) {
        RequireUtils.requireNotNullArg(keyItem, "Cannot map a null key");
        RequireUtils.requireNotNullArg(valueItem, "Cannot map a null item");
        this.oldToNew.put(keyItem, valueItem);
    }

    public void clear() {
        this.oldToNew.clear();
    }

    public boolean isEmpty() {
        return this.oldToNew.isEmpty();
    }

    public I lookup(I item) {
        return this.oldToNew.get(item);
    }

    public boolean hasLookupFor(I item) {
        return this.oldToNew.containsKey(item);
    }

    public Collection<I> values() {
        return this.oldToNew.values();
    }

    public I first() {
        if (!isEmpty()) {
            return this.oldToNew.values().iterator().next();
        }
        return null;
    }

    public Set<I> keys() {
        return this.oldToNew.keySet();
    }
}
