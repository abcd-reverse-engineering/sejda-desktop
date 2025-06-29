package org.sejda.sambox.pdmodel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.util.ObjectIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDPageTree.class */
public class PDPageTree implements COSObjectable, Iterable<PDPage> {
    private static final Logger LOG = LoggerFactory.getLogger(PDPageTree.class);
    private final COSDictionary root;
    private final PDDocument document;

    public PDPageTree() {
        this.root = new COSDictionary();
        this.root.setItem(COSName.TYPE, (COSBase) COSName.PAGES);
        this.root.setItem(COSName.KIDS, (COSBase) new COSArray());
        this.root.setItem(COSName.COUNT, (COSBase) COSInteger.ZERO);
        this.document = null;
    }

    public PDPageTree(COSDictionary root) {
        this(root, null);
    }

    PDPageTree(COSDictionary root, PDDocument document) {
        RequireUtils.requireNotNullArg(root, "Page tree root cannot be null");
        if (COSName.PAGE.equals(root.getCOSName(COSName.TYPE))) {
            COSArray kids = new COSArray();
            kids.add((COSBase) root);
            this.root = new COSDictionary();
            this.root.setItem(COSName.KIDS, (COSBase) kids);
            this.root.setInt(COSName.COUNT, 1);
        } else {
            this.root = root;
        }
        root.setItem(COSName.TYPE, (COSBase) COSName.PAGES);
        this.document = document;
    }

    public static <T extends COSBase> COSBase getInheritableAttribute(COSDictionary node, COSName key, Class<T> clazz) {
        COSBase result = getInheritableAttribute(node, key);
        if (clazz.isInstance(result)) {
            return result;
        }
        return null;
    }

    public static COSBase getInheritableAttribute(COSDictionary node, COSName key) {
        return getInheritableAttribute(node, key, new HashSet());
    }

    public static COSBase getInheritableAttribute(COSDictionary node, COSName key, Set<String> visitedObjectIds) {
        COSBase value = node.getDictionaryObject(key);
        if (value != null) {
            return value;
        }
        COSDictionary parent = (COSDictionary) node.getDictionaryObject(COSName.PARENT, COSName.P, COSDictionary.class);
        if (parent != node && parent != null) {
            String objId = ObjectIdUtils.getObjectIdOf(node);
            if (!objId.isBlank() && visitedObjectIds.contains(objId)) {
                return null;
            }
            visitedObjectIds.add(objId);
            return getInheritableAttribute(parent, key, visitedObjectIds);
        }
        return null;
    }

    @Override // java.lang.Iterable
    public Iterator<PDPage> iterator() {
        PageIterator iterator = new PageIterator(this.root);
        if (iterator.size() != this.document.getNumberOfPages()) {
            for (int i = 0; i < this.document.getNumberOfPages(); i++) {
                get(i);
            }
            throw new InvalidNumberOfPagesException(iterator.size(), this.document.getNumberOfPages());
        }
        return iterator;
    }

    public Stream<PDPage> stream() {
        return StreamSupport.stream(Spliterators.spliterator(iterator(), getCount(), 272), false);
    }

    public Stream<COSDictionary> streamNodes() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new NodesIterator(this.root), 272), false);
    }

    private List<COSDictionary> getKids(COSDictionary node) {
        COSArray kids = (COSArray) node.getDictionaryObject(COSName.KIDS, COSArray.class);
        if (Objects.nonNull(kids)) {
            return (List) kids.stream().map((v0) -> {
                return v0.getCOSObject();
            }).filter(i -> {
                return i != COSNull.NULL;
            }).filter((v0) -> {
                return Objects.nonNull(v0);
            }).filter(n -> {
                return n instanceof COSDictionary;
            }).map(n2 -> {
                return (COSDictionary) n2;
            }).collect(Collectors.toList());
        }
        return new ArrayList();
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDPageTree$PageIterator.class */
    private final class PageIterator implements Iterator<PDPage> {
        private final Queue<COSDictionary> queue = new ArrayDeque();

        private PageIterator(COSDictionary node) {
            enqueueKids(node);
        }

        private void enqueueKids(COSDictionary node) {
            if (PDPageTree.isPageTreeNode(node)) {
                PDPageTree.this.getKids(node).forEach(this::enqueueKids);
            } else {
                this.queue.add(node);
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public PDPage next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            COSDictionary next = this.queue.poll();
            PDPageTree.sanitizeType(next);
            ResourceCache resourceCache = PDPageTree.this.document != null ? PDPageTree.this.document.getResourceCache() : null;
            return new PDPage(next, resourceCache);
        }

        public int size() {
            return this.queue.size();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDPageTree$NodesIterator.class */
    private final class NodesIterator implements Iterator<COSDictionary> {
        private final Queue<COSDictionary> queue = new ArrayDeque();

        private NodesIterator(COSDictionary node) {
            enqueueKids(node);
        }

        private void enqueueKids(COSDictionary node) {
            this.queue.add(node);
            if (PDPageTree.isPageTreeNode(node)) {
                PDPageTree.this.getKids(node).forEach(this::enqueueKids);
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public COSDictionary next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return this.queue.poll();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public PDPage get(int index) {
        PageAndPageTreeParent res = get(index + 1, this.root, 0, null, new HashSet());
        COSDictionary dict = res.node;
        sanitizeType(dict);
        ResourceCache resourceCache = this.document != null ? this.document.getResourceCache() : null;
        return new PDPage(dict, resourceCache, res.parent);
    }

    private static void sanitizeType(COSDictionary dictionary) {
        if (Objects.isNull(dictionary.getCOSName(COSName.TYPE))) {
            LOG.warn("Missing required 'Page' type for page");
            dictionary.setName(COSName.TYPE, COSName.PAGE.getName());
        }
        COSName type = dictionary.getCOSName(COSName.TYPE);
        if (!COSName.PAGE.equals(type)) {
            LOG.error("Expected 'Page' but found '{}'", type.getName());
            dictionary.setName(COSName.TYPE, COSName.PAGE.getName());
        }
    }

    private PageAndPageTreeParent get(int pageNum, COSDictionary node, int encountered, COSDictionary pageTreeParent, Set<COSDictionary> visited) throws Throwable {
        RequireUtils.require(pageNum >= 0, () -> {
            return new PageNotFoundException("Index out of bounds: " + pageNum + " in " + getSourcePath(), pageNum, getSourcePath());
        });
        RequireUtils.require(visited.add(node), () -> {
            return new IllegalStateException("Possible recursion found when searching for page " + pageNum);
        });
        if (isPageTreeNode(node)) {
            int count = node.getInt(COSName.COUNT, 0);
            if (pageNum <= encountered + count) {
                for (COSDictionary kid : getKids(node)) {
                    if (isPageTreeNode(kid)) {
                        int kidCount = kid.getInt(COSName.COUNT, 0);
                        if (pageNum <= encountered + kidCount) {
                            return get(pageNum, kid, encountered, node, visited);
                        }
                        encountered += kidCount;
                    } else {
                        encountered++;
                        if (pageNum == encountered) {
                            return get(pageNum, kid, encountered, node, visited);
                        }
                    }
                }
                throw new PageNotFoundException("Unable to find page " + pageNum + " in " + getSourcePath(), pageNum, getSourcePath());
            }
            throw new PageNotFoundException("Index out of bounds: " + pageNum + " in " + getSourcePath(), pageNum, getSourcePath());
        }
        if (encountered == pageNum) {
            return new PageAndPageTreeParent(node, pageTreeParent);
        }
        throw new PageNotFoundException("Unable to find page " + pageNum + " in " + getSourcePath(), pageNum, getSourcePath());
    }

    private String getSourcePath() {
        return (String) Optional.ofNullable(getCOSObject().id()).map(i -> {
            return i.ownerIdentifier;
        }).orElse("Unknown");
    }

    public static boolean isPageTreeNode(COSDictionary node) {
        return Objects.nonNull(node) && (node.getCOSName(COSName.TYPE) == COSName.PAGES || node.containsKey(COSName.KIDS));
    }

    public int indexOf(PDPage page) {
        SearchContext context = new SearchContext(page);
        if (findPage(context, this.root)) {
            return context.index;
        }
        return -1;
    }

    private boolean findPage(SearchContext context, COSDictionary node) {
        for (COSDictionary kid : getKids(node)) {
            if (context.found) {
                break;
            }
            if (isPageTreeNode(kid)) {
                findPage(context, kid);
            } else {
                context.visitPage(kid);
            }
        }
        return context.found;
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDPageTree$SearchContext.class */
    private static final class SearchContext {
        private final COSDictionary searched;
        private int index = -1;
        private boolean found;

        private SearchContext(PDPage page) {
            this.searched = page.getCOSObject();
        }

        private void visitPage(COSDictionary current) {
            this.index++;
            this.found = this.searched.equals(current);
        }
    }

    public int getCount() {
        return this.root.getInt(COSName.COUNT, 0);
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.root;
    }

    public void remove(int index) {
        PageAndPageTreeParent res = get(index + 1, this.root, 0, null, new HashSet());
        remove(res.node, res.parent);
    }

    public void remove(PDPage page) {
        remove(page.getCOSObject());
    }

    private void remove(COSDictionary node) {
        remove(node, null);
    }

    private void remove(COSDictionary node, COSDictionary knownParent) {
        COSDictionary parent = (COSDictionary) node.getDictionaryObject(COSName.PARENT, COSName.P, COSDictionary.class);
        if (parent == null) {
            parent = knownParent;
        }
        COSArray kids = (COSArray) parent.getDictionaryObject(COSName.KIDS, COSArray.class);
        if (kids.removeObject(node)) {
            parent.setInt(COSName.COUNT, parent.getInt(COSName.COUNT) - 1);
            COSDictionary node2 = parent;
            do {
                node2 = (COSDictionary) node2.getDictionaryObject(COSName.PARENT, COSName.P, COSDictionary.class);
                if (node2 != null) {
                    node2.setInt(COSName.COUNT, node2.getInt(COSName.COUNT) - 1);
                }
            } while (node2 != null);
        }
    }

    public void add(PDPage page) {
        COSDictionary node = page.getCOSObject();
        node.setItem(COSName.PARENT, (COSBase) this.root);
        COSArray kids = (COSArray) this.root.getDictionaryObject(COSName.KIDS, COSArray.class);
        kids.add((COSBase) node);
        do {
            node = (COSDictionary) node.getDictionaryObject(COSName.PARENT, COSName.P);
            if (node != null) {
                node.setInt(COSName.COUNT, node.getInt(COSName.COUNT) + 1);
            }
        } while (node != null);
    }

    public void insertBefore(PDPage newPage, PDPage nextPage) {
        COSDictionary nextPageDict = nextPage.getCOSObject();
        COSDictionary parentDict = (COSDictionary) nextPageDict.getDictionaryObject(COSName.PARENT, COSDictionary.class);
        if (nextPage.getPageTreeParent() != null) {
            parentDict = nextPage.getPageTreeParent();
        }
        COSArray kids = (COSArray) parentDict.getDictionaryObject(COSName.KIDS, COSArray.class);
        boolean found = false;
        int i = 0;
        while (true) {
            if (i >= kids.size()) {
                break;
            }
            COSDictionary pageDict = (COSDictionary) kids.getObject(i);
            if (!pageDict.equals(nextPage.getCOSObject())) {
                i++;
            } else {
                kids.add(i, (COSBase) newPage.getCOSObject());
                newPage.getCOSObject().setItem(COSName.PARENT, (COSBase) parentDict);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("attempted to insert before orphan page");
        }
        increaseParents(parentDict);
    }

    public void insertAfter(PDPage newPage, PDPage prevPage) {
        COSDictionary prevPageDict = prevPage.getCOSObject();
        COSDictionary parentDict = (COSDictionary) prevPageDict.getDictionaryObject(COSName.PARENT, COSDictionary.class);
        if (prevPage.getPageTreeParent() != null) {
            parentDict = prevPage.getPageTreeParent();
        }
        COSArray kids = (COSArray) parentDict.getDictionaryObject(COSName.KIDS, COSArray.class);
        boolean found = false;
        int i = 0;
        while (true) {
            if (i >= kids.size()) {
                break;
            }
            COSDictionary pageDict = (COSDictionary) kids.getObject(i);
            if (!pageDict.equals(prevPage.getCOSObject())) {
                i++;
            } else {
                kids.add(i + 1, (COSBase) newPage.getCOSObject());
                newPage.getCOSObject().setItem(COSName.PARENT, (COSBase) parentDict);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("attempted to insert before orphan page");
        }
        increaseParents(parentDict);
    }

    private void increaseParents(COSDictionary parentDict) {
        do {
            int cnt = parentDict.getInt(COSName.COUNT);
            parentDict.setInt(COSName.COUNT, cnt + 1);
            parentDict = (COSDictionary) parentDict.getDictionaryObject(COSName.PARENT);
        } while (parentDict != null);
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDPageTree$PageAndPageTreeParent.class */
    public static class PageAndPageTreeParent {
        public final COSDictionary node;
        public final COSDictionary parent;

        public PageAndPageTreeParent(COSDictionary node, COSDictionary parent) {
            this.node = node;
            this.parent = parent;
        }
    }
}
