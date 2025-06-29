package org.sejda.commons.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/collection/NullSafeSet.class */
public class NullSafeSet<E> implements Set<E> {
    private final Set<E> delegate;

    public NullSafeSet() {
        this.delegate = new LinkedHashSet();
    }

    public NullSafeSet(Set<E> delegate) {
        this.delegate = delegate;
    }

    @Override // java.util.Set, java.util.Collection
    public int size() {
        return this.delegate.size();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object o) {
        return this.delegate.contains(o);
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return this.delegate.iterator();
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return this.delegate.toArray();
    }

    @Override // java.util.Set, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) this.delegate.toArray(tArr);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(E e) {
        if (e != null) {
            return this.delegate.add(e);
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object o) {
        return this.delegate.remove(o);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<?> c) {
        return this.delegate.containsAll(c);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends E> c) {
        boolean retVal = false;
        for (E e : c) {
            if (add(e)) {
                retVal = true;
            }
        }
        return retVal;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<?> c) {
        return this.delegate.retainAll(c);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<?> c) {
        return this.delegate.removeAll(c);
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        this.delegate.clear();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean equals(Object o) {
        return this.delegate.equals(o);
    }

    @Override // java.util.Set, java.util.Collection
    public int hashCode() {
        return this.delegate.hashCode();
    }

    public String toString() {
        return this.delegate.toString();
    }
}
