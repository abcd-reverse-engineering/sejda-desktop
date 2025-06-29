package org.sejda.sambox.output;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSDocument;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSVisitor;
import org.sejda.sambox.cos.IndirectCOSObject;
import org.sejda.sambox.cos.IndirectCOSObjectReference;
import org.sejda.sambox.input.ExistingIndirectCOSObject;
import org.sejda.sambox.input.IncrementablePDDocument;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/PDFBodyWriter.class */
class PDFBodyWriter implements COSVisitor, Closeable {
    private PDFWriteContext context;
    PDFBodyObjectsWriter objectsWriter;
    private Queue<IndirectCOSObjectReference> stack = new LinkedList();
    private boolean open = true;

    PDFBodyWriter(PDFWriteContext context, PDFBodyObjectsWriter objectsWriter) {
        RequireUtils.requireNotNullArg(context, "Write context cannot be null");
        RequireUtils.requireNotNullArg(objectsWriter, "Objects writer cannot be null");
        this.context = context;
        this.objectsWriter = objectsWriter;
    }

    PDFWriteContext context() {
        return this.context;
    }

    public void write(IncrementablePDDocument document) throws IOException {
        RequireUtils.requireState(this.open, "The writer is closed");
        document.newIndirects().forEach(o -> {
            this.stack.add(this.context.getOrCreateIndirectReferenceFor(o));
        });
        document.trailer().getCOSObject().accept(this);
        this.stack.addAll(document.replacements());
        startWriting();
    }

    public void write(COSDocument document) throws IOException {
        RequireUtils.requireState(this.open, "The writer is closed");
        document.accept(this);
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSDocument document) throws IOException {
        for (COSName k : Arrays.asList(COSName.ROOT, COSName.ENCRYPT)) {
            Optional.ofNullable(document.getTrailer().getCOSObject().getItem(k)).ifPresent(r -> {
                this.stack.add(this.context.createNonStorableInObjectStreamIndirectReferenceFor(r));
            });
        }
        Optional.ofNullable(document.getTrailer().getCOSObject().getItem(COSName.INFO)).ifPresent(this::createIndirectReferenceIfNeededFor);
        startWriting();
    }

    void startWriting() throws IOException {
        while (!this.stack.isEmpty()) {
            IndirectCOSObjectReference item = this.stack.poll();
            item.getCOSObject().accept(this);
            this.objectsWriter.writeObject(item);
        }
        this.objectsWriter.onWriteCompletion();
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSArray array) throws IOException {
        for (int i = 0; i < array.size(); i++) {
            COSBase item = (COSBase) Optional.ofNullable(array.get(i)).orElse(COSNull.NULL);
            if (shouldBeIndirect(item)) {
                onPotentialIndirectObject(item);
            } else {
                item.accept(this);
            }
        }
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSDictionary value) throws IOException {
        for (COSName key : value.keySet()) {
            COSBase item = (COSBase) Optional.ofNullable(value.getItem(key)).orElse(COSNull.NULL);
            if (shouldBeIndirect(item) || COSName.THREADS.equals(key)) {
                onPotentialIndirectObject(item);
            } else {
                item.accept(this);
            }
        }
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSStream value) throws IOException {
        value.removeItem(COSName.LENGTH);
        if (this.context.hasWriteOption(WriteOption.COMPRESS_STREAMS)) {
            value.addCompression();
        }
        value.indirectLength(Objects.nonNull(this.context.encryptor()));
        if (value.indirectLength()) {
            IndirectCOSObjectReference length = this.context.createNonStorableInObjectStreamIndirectReference();
            value.setItem(COSName.LENGTH, (COSBase) length);
            this.stack.add(length);
        }
        visit((COSDictionary) value);
    }

    private boolean shouldBeIndirect(COSBase item) {
        return (item instanceof ExistingIndirectCOSObject) || (item instanceof COSDictionary) || (item instanceof IndirectCOSObject);
    }

    public void onPotentialIndirectObject(COSBase item) throws IOException {
        createIndirectReferenceIfNeededFor(item);
    }

    final void createIndirectReferenceIfNeededFor(COSBase item) {
        if (!this.context.hasIndirectReferenceFor(item)) {
            this.stack.add(this.context.createIndirectReferenceFor(item));
        }
    }

    public void close() throws IOException {
        this.objectsWriter.close();
        this.context = null;
        this.open = false;
    }
}
