package org.sejda.commons.collection;

import java.util.Collection;
import java.util.LinkedList;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/collection/CircularLinkedList.class */
public class CircularLinkedList<E> extends LinkedList<E> {
    private int maxCapacity;

    public CircularLinkedList(int maxCapacity) {
        setMaxCapacity(maxCapacity);
    }

    public void setMaxCapacity(int maxCapacity) {
        RequireUtils.requireArg(maxCapacity > 0, "Max capacity must be a positive value");
        this.maxCapacity = maxCapacity;
        houseKeep();
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public boolean isFull() {
        return size() >= this.maxCapacity;
    }

    @Override // java.util.LinkedList, java.util.Deque
    public void addFirst(E e) {
        makeRoom();
        super.addFirst(e);
    }

    @Override // java.util.LinkedList, java.util.Deque
    public void addLast(E e) {
        makeRoom();
        super.addLast(e);
    }

    @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
    public boolean add(E e) {
        makeRoom();
        return super.add(e);
    }

    @Override // java.util.LinkedList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque
    public boolean addAll(Collection<? extends E> c) {
        boolean ret = super.addAll(c);
        houseKeep();
        return ret;
    }

    @Override // java.util.LinkedList, java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean ret = super.addAll(index, c);
        houseKeep();
        return ret;
    }

    @Override // java.util.LinkedList, java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public void add(int i, E e) {
        super.add(i, e);
        houseKeep();
    }

    private void makeRoom() {
        while (isFull()) {
            pollFirst();
        }
    }

    private void houseKeep() {
        while (size() > this.maxCapacity) {
            pollFirst();
        }
    }
}
