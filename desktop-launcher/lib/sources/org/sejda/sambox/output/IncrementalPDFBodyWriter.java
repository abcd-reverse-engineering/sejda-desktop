package org.sejda.sambox.output;

import java.io.IOException;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSDocument;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.input.ExistingIndirectCOSObject;
import org.sejda.sambox.input.IncrementablePDDocument;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/IncrementalPDFBodyWriter.class */
public class IncrementalPDFBodyWriter extends PDFBodyWriter {
    @Override // org.sejda.sambox.output.PDFBodyWriter, java.io.Closeable, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() throws IOException {
        super.close();
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSStream cOSStream) throws IOException {
        super.visit(cOSStream);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSDictionary cOSDictionary) throws IOException {
        super.visit(cOSDictionary);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSArray cOSArray) throws IOException {
        super.visit(cOSArray);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSDocument cOSDocument) throws IOException {
        super.visit(cOSDocument);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter
    public /* bridge */ /* synthetic */ void write(COSDocument cOSDocument) throws IOException {
        super.write(cOSDocument);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter
    public /* bridge */ /* synthetic */ void write(IncrementablePDDocument incrementablePDDocument) throws IOException {
        super.write(incrementablePDDocument);
    }

    IncrementalPDFBodyWriter(PDFWriteContext context, PDFBodyObjectsWriter objectsWriter) {
        super(context, objectsWriter);
    }

    @Override // org.sejda.sambox.output.PDFBodyWriter
    public void onPotentialIndirectObject(COSBase item) throws IOException {
        if (item instanceof ExistingIndirectCOSObject) {
            context().addExistingReference((ExistingIndirectCOSObject) item);
        } else {
            item.accept(this);
        }
    }
}
