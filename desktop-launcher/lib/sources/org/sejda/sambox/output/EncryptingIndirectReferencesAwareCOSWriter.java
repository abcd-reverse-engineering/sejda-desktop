package org.sejda.sambox.output;

import java.io.IOException;
import java.util.Objects;
import org.sejda.io.BufferedCountingChannelWriter;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/EncryptingIndirectReferencesAwareCOSWriter.class */
class EncryptingIndirectReferencesAwareCOSWriter extends IndirectReferencesAwareCOSWriter {
    EncryptingIndirectReferencesAwareCOSWriter(BufferedCountingChannelWriter writer, PDFWriteContext context) {
        super(writer, context);
    }

    EncryptingIndirectReferencesAwareCOSWriter(CountingWritableByteChannel channel, PDFWriteContext context) {
        super(channel, context);
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public void visit(COSStream value) throws IOException {
        if (Objects.nonNull(this.context.encryptor())) {
            this.context.encryptor().visit(value);
        }
        super.visit(value);
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public void visit(COSString value) throws IOException {
        if (Objects.nonNull(this.context.encryptor())) {
            this.context.encryptor().visit(value);
            value.setForceHexForm(true);
        }
        super.visit(value);
    }
}
