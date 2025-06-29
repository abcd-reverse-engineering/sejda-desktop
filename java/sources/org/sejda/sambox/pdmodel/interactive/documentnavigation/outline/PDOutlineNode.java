package org.sejda.sambox.pdmodel.interactive.documentnavigation.outline;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/outline/PDOutlineNode.class */
public abstract class PDOutlineNode extends PDDictionaryWrapper {
    public PDOutlineNode() {
    }

    public PDOutlineNode(COSDictionary dict) {
        super(dict);
    }

    PDOutlineNode getParent() {
        COSDictionary item = (COSDictionary) getCOSObject().getDictionaryObject(COSName.PARENT, COSDictionary.class);
        if (item != null) {
            if (COSName.OUTLINES.equals(item.getCOSName(COSName.TYPE))) {
                return new PDDocumentOutline(item);
            }
            return new PDOutlineItem(item);
        }
        return null;
    }

    void setParent(PDOutlineNode parent) {
        getCOSObject().setItem(COSName.PARENT, parent);
    }

    public void addLast(PDOutlineItem newChild) {
        requireSingleNode(newChild);
        append(newChild);
        updateParentOpenCountForAddedChild(newChild);
    }

    public void addAtPosition(PDOutlineItem newChild, int index) {
        requireSingleNode(newChild);
        insert(newChild, index);
        updateParentOpenCountForAddedChild(newChild);
    }

    public void addFirst(PDOutlineItem newChild) {
        requireSingleNode(newChild);
        prepend(newChild);
        updateParentOpenCountForAddedChild(newChild);
    }

    void requireSingleNode(PDOutlineItem node) {
        if (node.getNextSibling() != null || node.getPreviousSibling() != null) {
            throw new IllegalArgumentException("A single node with no siblings is required");
        }
    }

    private void append(PDOutlineItem newChild) {
        newChild.setParent(this);
        if (!hasChildren()) {
            setFirstChild(newChild);
        } else {
            PDOutlineItem previousLastChild = getLastChild();
            previousLastChild.setNextSibling(newChild);
            newChild.setPreviousSibling(previousLastChild);
        }
        setLastChild(newChild);
    }

    private void prepend(PDOutlineItem newChild) {
        newChild.setParent(this);
        if (!hasChildren()) {
            setLastChild(newChild);
        } else {
            PDOutlineItem previousFirstChild = getFirstChild();
            newChild.setNextSibling(previousFirstChild);
            previousFirstChild.setPreviousSibling(newChild);
        }
        setFirstChild(newChild);
    }

    private void insert(PDOutlineItem newChild, int index) {
        PDOutlineItem current;
        newChild.setParent(this);
        if (hasChildren()) {
            int currentIndex = 0;
            Iterator<PDOutlineItem> iterator = children().iterator();
            PDOutlineItem prev = null;
            PDOutlineItem next = iterator.next();
            while (true) {
                current = next;
                if (currentIndex >= index || !iterator.hasNext()) {
                    break;
                }
                currentIndex++;
                prev = current;
                next = iterator.next();
            }
            if (currentIndex == index) {
                current.setPreviousSibling(newChild);
                newChild.setNextSibling(current);
                newChild.setPreviousSibling(prev);
                if (prev != null) {
                    prev.setNextSibling(newChild);
                    return;
                } else {
                    setFirstChild(newChild);
                    return;
                }
            }
            if (currentIndex + 1 <= index) {
                newChild.setPreviousSibling(current);
                current.setNextSibling(newChild);
                setLastChild(newChild);
                return;
            }
            throw new RuntimeException("Cannot insert at: currentIndex: " + currentIndex + " index: " + index);
        }
        setFirstChild(newChild);
        setLastChild(newChild);
    }

    void updateParentOpenCountForAddedChild(PDOutlineItem newChild) {
        int delta = 1;
        if (newChild.isNodeOpen()) {
            delta = 1 + newChild.getOpenCount();
        }
        newChild.updateParentOpenCount(delta);
    }

    public boolean hasChildren() {
        return Objects.nonNull(getCOSObject().getDictionaryObject(COSName.FIRST, COSDictionary.class));
    }

    PDOutlineItem getOutlineItem(COSName name) {
        return (PDOutlineItem) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(name, COSDictionary.class)).map(PDOutlineItem::new).orElse(null);
    }

    public PDOutlineItem getFirstChild() {
        return getOutlineItem(COSName.FIRST);
    }

    void setFirstChild(PDOutlineNode outlineNode) {
        getCOSObject().setItem(COSName.FIRST, outlineNode);
    }

    public PDOutlineItem getLastChild() {
        return getOutlineItem(COSName.LAST);
    }

    void setLastChild(PDOutlineNode outlineNode) {
        getCOSObject().setItem(COSName.LAST, outlineNode);
    }

    public int getOpenCount() {
        return getCOSObject().getInt(COSName.COUNT, 0);
    }

    void setOpenCount(int openCount) {
        getCOSObject().setInt(COSName.COUNT, openCount);
    }

    public void openNode() {
        if (!isNodeOpen()) {
            switchNodeCount();
        }
    }

    public void closeNode() {
        if (isNodeOpen()) {
            switchNodeCount();
        }
    }

    private void switchNodeCount() {
        int openCount = getOpenCount();
        setOpenCount(-openCount);
        updateParentOpenCount(-openCount);
    }

    public boolean isNodeOpen() {
        return getOpenCount() > 0;
    }

    void updateParentOpenCount(int delta) {
        PDOutlineNode parent = getParent();
        if (parent != null) {
            if (parent.isNodeOpen()) {
                parent.setOpenCount(parent.getOpenCount() + delta);
                parent.updateParentOpenCount(delta);
            } else {
                parent.setOpenCount(parent.getOpenCount() - delta);
            }
        }
    }

    public Iterable<PDOutlineItem> children() {
        return () -> {
            return new PDOutlineItemIterator(getFirstChild());
        };
    }
}
