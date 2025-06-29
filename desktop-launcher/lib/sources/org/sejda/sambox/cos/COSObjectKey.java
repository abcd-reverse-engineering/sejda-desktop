package org.sejda.sambox.cos;

import java.util.Comparator;
import java.util.Objects;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSObjectKey.class */
public final class COSObjectKey implements Comparable<COSObjectKey> {
    private final long number;
    private final int generation;

    public COSObjectKey(long number, int generation) {
        this.number = number;
        this.generation = generation;
    }

    public int generation() {
        return this.generation;
    }

    public long objectNumber() {
        return this.number;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof COSObjectKey)) {
            return false;
        }
        COSObjectKey other = (COSObjectKey) obj;
        return this.number == other.number && this.generation == other.generation;
    }

    public int hashCode() {
        return Long.valueOf((this.number << 4) + this.generation).hashCode();
    }

    public String toString() {
        return String.format("on=%d, gn=%d", Long.valueOf(this.number), Integer.valueOf(this.generation));
    }

    @Override // java.lang.Comparable
    public int compareTo(COSObjectKey other) {
        return Objects.compare(this, other, Comparator.comparing((v0) -> {
            return v0.objectNumber();
        }).thenComparingInt((v0) -> {
            return v0.generation();
        }));
    }
}
