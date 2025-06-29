package org.sejda.sambox.pdmodel.font;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/CIDSystemInfo.class */
public final class CIDSystemInfo {
    private final String registry;
    private final String ordering;
    private final int supplement;

    public CIDSystemInfo(String registry, String ordering, int supplement) {
        this.registry = registry;
        this.ordering = ordering;
        this.supplement = supplement;
    }

    public String getRegistry() {
        return this.registry;
    }

    public String getOrdering() {
        return this.ordering;
    }

    public int getSupplement() {
        return this.supplement;
    }

    public String toString() {
        return getRegistry() + "-" + getOrdering() + "-" + getSupplement();
    }
}
