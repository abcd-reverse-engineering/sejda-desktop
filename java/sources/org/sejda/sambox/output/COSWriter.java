package org.sejda.sambox.output;

import java.io.Closeable;
import java.io.IOException;
import org.sejda.commons.util.IOUtils;
import org.sejda.io.BufferedCountingChannelWriter;
import org.sejda.sambox.cos.COSVisitor;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/COSWriter.class */
public interface COSWriter extends COSVisitor, Closeable {
    BufferedCountingChannelWriter writer();

    default void writeComplexObjectSeparator() throws IOException {
        writer().writeEOL();
    }

    default void writeDictionaryItemsSeparator() throws IOException {
        writer().writeEOL();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    default void close() throws IOException {
        IOUtils.close(writer());
    }
}
