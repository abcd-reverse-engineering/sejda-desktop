package org.sejda.sambox.pdmodel.interactive.form;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDFieldTree.class */
public class PDFieldTree implements Iterable<PDField> {
    private final PDAcroForm acroForm;

    public PDFieldTree(PDAcroForm acroForm) {
        RequireUtils.requireNotNullArg(acroForm, "root cannot be null");
        this.acroForm = acroForm;
    }

    @Override // java.lang.Iterable
    public Iterator<PDField> iterator() {
        return new FieldIterator(this.acroForm);
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDFieldTree$FieldIterator.class */
    private static final class FieldIterator implements Iterator<PDField> {
        private final Queue<PDField> queue = new ArrayDeque();

        private FieldIterator(PDAcroForm form) {
            for (PDField field : form.getFields()) {
                enqueueKids(field);
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public PDField next() {
            return this.queue.remove();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void enqueueKids(PDField node) {
            if (node instanceof PDNonTerminalField) {
                List<PDField> kids = ((PDNonTerminalField) node).getChildren();
                for (PDField kid : kids) {
                    enqueueKids(kid);
                }
            }
            this.queue.add(node);
        }
    }

    public Stream<PDField> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new PreOrderIterator(this.acroForm), 272), false);
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDFieldTree$PreOrderIterator.class */
    private static final class PreOrderIterator implements Iterator<PDField> {
        private final Deque<PDField> queue = new ArrayDeque();

        private PreOrderIterator(PDAcroForm form) {
            for (PDField field : form.getFields()) {
                enqueueKids(field);
            }
        }

        private void enqueueKids(PDField node) {
            this.queue.add(node);
            if (node instanceof PDNonTerminalField) {
                List<PDField> kids = ((PDNonTerminalField) node).getChildren();
                for (PDField kid : kids) {
                    enqueueKids(kid);
                }
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public PDField next() {
            return this.queue.remove();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
