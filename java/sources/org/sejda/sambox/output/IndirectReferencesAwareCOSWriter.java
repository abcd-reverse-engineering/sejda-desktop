package org.sejda.sambox.output;

import java.io.IOException;
import java.util.Optional;
import org.sejda.io.BufferedCountingChannelWriter;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/IndirectReferencesAwareCOSWriter.class */
class IndirectReferencesAwareCOSWriter extends DefaultCOSWriter {
    final PDFWriteContext context;

    IndirectReferencesAwareCOSWriter(CountingWritableByteChannel channel, PDFWriteContext context) {
        this(new BufferedCountingChannelWriter(channel), context);
    }

    IndirectReferencesAwareCOSWriter(BufferedCountingChannelWriter writer, PDFWriteContext context) {
        super(writer);
        this.context = context;
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter
    void writeValue(COSBase value) throws IOException {
        ((COSBase) Optional.ofNullable(this.context.getIndirectReferenceFor(value)).orElse(value)).accept(this);
    }
}
