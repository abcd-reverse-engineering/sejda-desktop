package org.sejda.sambox.pdmodel.interactive.documentnavigation.outline;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/outline/PDOutlineItemIterator.class */
class PDOutlineItemIterator implements Iterator<PDOutlineItem> {
    private PDOutlineItem currentItem;
    private final PDOutlineItem startingItem;
    private final HashSet<PDOutlineItem> visited = new HashSet<>();

    PDOutlineItemIterator(PDOutlineItem startingItem) {
        this.startingItem = startingItem;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.startingItem == null) {
            return false;
        }
        if (this.currentItem == null) {
            return true;
        }
        PDOutlineItem next = this.currentItem.getNextSibling();
        return (next == null || this.startingItem.equals(this.currentItem.getNextSibling()) || this.visited.contains(next)) ? false : true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public PDOutlineItem next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (this.currentItem == null) {
            this.currentItem = this.startingItem;
        } else {
            this.currentItem = this.currentItem.getNextSibling();
        }
        if (this.visited.contains(this.currentItem)) {
            throw new NoSuchElementException();
        }
        this.visited.add(this.currentItem);
        return this.currentItem;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
