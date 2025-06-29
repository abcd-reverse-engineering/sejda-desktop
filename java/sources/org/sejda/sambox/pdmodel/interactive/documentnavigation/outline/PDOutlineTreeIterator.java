package org.sejda.sambox.pdmodel.interactive.documentnavigation.outline;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/outline/PDOutlineTreeIterator.class */
public class PDOutlineTreeIterator implements Iterator<PDOutlineItem> {
    private final LinkedHashSet<PDOutlineItem> elements = new LinkedHashSet<>();
    private final Queue<PDOutlineItem> queue = new ArrayDeque();

    public PDOutlineTreeIterator(PDDocumentOutline outline) {
        if (Objects.nonNull(outline)) {
            enqueueChildren(outline.children());
        }
    }

    private void enqueueChildren(Iterable<PDOutlineItem> children) {
        for (PDOutlineItem item : children) {
            if (!this.elements.contains(item)) {
                if (item.hasChildren()) {
                    this.elements.add(item);
                    enqueueChildren(item.children());
                } else {
                    this.elements.add(item);
                }
            }
        }
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return !this.elements.isEmpty();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public PDOutlineItem next() {
        PDOutlineItem next = (PDOutlineItem) this.elements.stream().findFirst().orElseThrow(NoSuchElementException::new);
        this.elements.remove(next);
        return next;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
